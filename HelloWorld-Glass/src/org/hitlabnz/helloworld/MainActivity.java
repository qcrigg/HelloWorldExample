package org.hitlabnz.helloworld;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public class MainActivity extends Activity {

	public final static String TAG = MainActivity.class.getSimpleName();
	
	public final String InputFileName = "eng.txt"; //eng.txt, temporarily changing to ar.txt
	public final String InputFileName_ar = "ar.txt";
	
	public final String pref_language = "en-us";
	
	public final static int SPEECH_PROMPT = 1;
	public static int BACKGROUND_RECORDER = 2;
	
	public final static int AUDIO_METHOD = BACKGROUND_RECORDER;
	
	public boolean STARTED_LISTENING = false;
	
	public ArrayList<String> wordList = new ArrayList<String>(); // Contains the English words from the word list. 
	public ArrayList<String> wordList_ar = new ArrayList<String>(); // Contains the Arabic words from the word list.
	
	private static final String APPLICATION_NAME = "QCRIGlass/1.0";

	/** API Key for the registered developer project for your application. */
	private static final String API_KEY = "AIzaSyA_4Wk5XsirNZq13yL6PQGq6c-wLQZf3-c";
	
	SpeechRecognizer sr;
	TextView textView;
	GestureDetector gestureDetector;
	TextToSpeech tts;
	MediaPlayer mediaPlayer;
	String results = "recording audio";
	Intent intent;
	BufferedReader reader;
	String OutputFile = "results.txt"; // Text file where the data(results) gets stored. 
	BufferedWriter writer;
	String sourceLanguage = "en"; // en - English
	String targetLanguage = "ar"; // ar - Arabic
	
	//Variables being stored in database
	public String translated_word = "'init'";
	public String original_word = "'init'";
	public String recognized_word = "'init'";
	public String original_translation = "'init'";
	public String recognition_correct = "0";
	public String translation_correct = "0";
	public String translation_time = "0";
	public String recognition_time = "0";
	public String audioFile = "";
	
	//This is the only thing you need to modify when testing with different users. Change the id
	// and install the apk on the Glass again. Be careful about the format - "'<user_id>'", as
	//it is required for the POST request to be accepted by the server.
	public String USER_ID = "";
	int USER_ID_FLAG = 0; 
	int wordPos = 0;
	List<Integer> ordering = new ArrayList<Integer>();
	//boolean mPlayerCreated = false;
	boolean translatedAudio = false;
	/*This is used to get the name of the person, 
	since it needs to be asked only at the beginning of the application, flag is being used.*/

	/*
	 * This function starts up the application functionality and starts the speech recognition engine. 
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//To prevent the screen from going to sleep
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		// get text view inflated from the layout resource
		textView = (TextView) findViewById(R.id.textView1);
		textView.setText("Hello there! Tap to Start, say your name when prompted and then read every word.");

		// create gesture detector
		gestureDetector = createGestureDetector();
		
		// create tts
		tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if(status == TextToSpeech.ERROR)
					Toast.makeText(getApplicationContext(), "TTS not working.", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	/*
	 * This function destroys the speech recognition system and closes the application functionality. 
	 */
	@Override
	protected void onDestroy() {
		Log.d(TAG, "closing application");
		
		// shutdown tts
		tts.shutdown();
		
		//shut down speech recognition
		
		if(sr!=null){
			//sr.stopListening();
			//sr.cancel();
			sr.destroy();
			Log.d(TAG, "Destroyed sr");
		}
		sr = null;
		
		if(mediaPlayer != null)
		{
			mediaPlayer.release();
			mediaPlayer = null;
		}
		super.onDestroy();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*
	 * Opens the file to be read.
	 */
	public void initiateReader(String fileName){
		
		try {
			reader = new BufferedReader(
			        new InputStreamReader(getAssets().open(fileName), "UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Pulls the words from each line of the file. 
	 */
	public void pullWords(ArrayList<String> wordList){
		String line;
		try {
			while((line = reader.readLine()) != null ){
				wordList.add(line);
				//Log.d(TAG, line);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/* randomize the list of words to be picked */
	public void randomOrder()
	{
		for (int i =0 ; i < wordList.size(); i++)
			ordering.add(i);
		Collections.shuffle(ordering);
	}
	
	/*
	 * Displays each word on the glass.
	 */
	public void displayWord(){
		//Random randomGenerator = new Random();
		//int randInt = randomGenerator.nextInt(wordList.size());
		//Log.d(TAG,"new word? " + translatedAudio + " " + mediaPlayer.isPlaying());
		if(!translatedAudio && (mediaPlayer == null || !mediaPlayer.isPlaying()))
		{
			if(wordPos == wordList.size())
			{
				Collections.shuffle(ordering);
				wordPos = 0;
			}
			int pos = ordering.get(wordPos);
			original_word = wordList.get(pos);
			original_translation = wordList_ar.get(pos);

			/*AsyncTask getTTS = new GetTTS().execute();

		try {
			String result= (String) getTTS.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		playAudio();*/

			/*try {
				if(translatedAudio)
				{
					TimeUnit.SECONDS.sleep(2);
					//translatedAudio = false;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			textView.setText(original_word);
			tts.speak(original_word, TextToSpeech.QUEUE_FLUSH, null);
			Log.d(TAG,"display " + original_word);
			wordPos++;
		}
		else
		{
			try {
				TimeUnit.SECONDS.sleep(1);
				if(mediaPlayer.isPlaying())
					translatedAudio = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			displayWord();
		}

	}
	
	public void playAudio()
	{

		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {           
			public void onCompletion(MediaPlayer mp) {
				//Log.d(TAG,"audio Complete");
				mp.stop();
				mp.reset();
				mp.release();
				translatedAudio = false;
			}
		});

		String filename = "http://192.168.1.107/glass/"+audioFile;
		//Log.d(TAG, filename);
		try {
			mediaPlayer.setDataSource(filename);
			mediaPlayer.prepare();
			mediaPlayer.start();
			//mPlayerCreated = true;
			audioFile = "";
			//textView.setText(original_word);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
/*	private class GetTTS extends AsyncTask<String, String, String>{

		protected void onPreExecute(){
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... strings) {

			HttpClient client = new DefaultHttpClient();
			String query = "";
			try {
				query = URLEncoder.encode(original_word, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HttpGet get = new HttpGet("http://192.168.1.107/glass/get_tts.php?ORIGINAL_WORD="+query);
			String responseString = "";
			try {
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseString = client.execute(get,responseHandler);

				if(responseString.contains(".mp3"))
					audioFile = responseString;

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return responseString;
		}
		
	}*/
	
	/*
	 * Function that stores the data obtained in an online SQL database.
	 */
	private class StoreToDatabase extends AsyncTask<String, String, String>{

		protected void onPreExecute(){
			super.onPreExecute();
			Log.d(TAG, "Storing to database");
		}
		@Override
		protected String doInBackground(String... strings) {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://192.168.1.107/glass/add_data.php");//192.168.1.107
            
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			//Log.d(TAG, "Original word " + original_word);
            params.add(new BasicNameValuePair("ORIGINAL_WORD", "'" + original_word + "'"));
            Log.d(TAG, "Recognized word " + recognized_word);
            params.add(new BasicNameValuePair("RECOGNIZED_WORD", "'" + recognized_word + "'"));
            //Log.d(TAG, "Original translation " + original_translation);
            params.add(new BasicNameValuePair("ORIGINAL_TRANSLATION", "'" + original_translation + "'"));
            //Log.d(TAG, "Translated word " + translated_word);
            params.add(new BasicNameValuePair("TRANSLATED_WORD", "'" + translated_word + "'" ));
            params.add(new BasicNameValuePair("RECOGNITION_CORRECT", recognition_correct));
            params.add(new BasicNameValuePair("TRANSLATION_CORRECT", translation_correct ));
            params.add(new BasicNameValuePair("TRANSLATION_TIME", translation_time ));
            params.add(new BasicNameValuePair("RECOGNITION_TIME", recognition_time ));
            params.add(new BasicNameValuePair("user_id", USER_ID));
			
            try {
				post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
				//Log.d(TAG, "Post " +  post.getParams().getParameter("ORIGINAL_TRANSLATION").toString());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				
				String responseString = EntityUtils.toString(entity, "UTF-8");
				//Log.d(TAG, " SQL INSERT " + responseString);
				
				if(responseString.contains(".mp3"))
					audioFile = responseString;
				
				playAudio();
				
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return "stored to db";
		}
	}
	
	/*
	 * The function that translates the given word into Arabic using google translate API.
	 */
	private class WordTranslateTask extends AsyncTask<String, String, String>{
		
		@Override
		protected String doInBackground(String...strings ) {
			//Log.d(TAG, "Translate word");
			String inputLine = "";
			String result = "";
			URL url;
			String query;
			try {
				query = URLEncoder.encode(strings[0], "UTF-8");
				url = new URL("https://www.googleapis.com/language/translate/v2?key=" + API_KEY + "&source=" + sourceLanguage + "&target=" + targetLanguage + "&q=" + query);
				//url = new URL("https://www.googleapis.com/language/translate/v2?key=AIzaSyA_4Wk5XsirNZq13yL6PQGq6c-wLQZf3-c&source=en&target=ar&q=Hello%20world");
				URLConnection yc = url.openConnection();
		        BufferedReader in = new BufferedReader(
		                                new InputStreamReader(
		                                yc.getInputStream()));

		        try {
					while ((inputLine = in.readLine()) != null) 
						if(inputLine !=  null){
							//System.out.println(inputLine);
							//Log.d(TAG, inputLine);
							result += inputLine;
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        in.close();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//Log.d(TAG, result);
			
			String translation = "";
			try {
				JSONObject jsonObj = new JSONObject(result);
				translation = jsonObj.getJSONObject("data").getJSONArray("translations").getJSONObject(0).getString("translatedText");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Log.d(TAG, "Translation " + translation);
			
			return translation; 
		}
		
		protected void onPostExecute(String s){
			this.publishProgress(s);
			return;
		}

	}
	
	/*
	 * Acts as the main function that calls out to other functions as required such as to display a new word 
	 * or to make the speech recognition start listening.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_CENTER:
			Log.d(TAG, "Tapped (DPAD_CENTER)");
			initiateReader(InputFileName);
			pullWords(wordList);
			initiateReader(InputFileName_ar);
			pullWords(wordList_ar);
			randomOrder();
			if(USER_ID_FLAG==1){
				displayWord();
			}
			if(!STARTED_LISTENING){
				startListening();
				STARTED_LISTENING = true;
				
			}
			//openOptionsMenu(); // open the option menu on tap
			return true; // return true if you handled this event
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/*
	 *  Function that deals with gestures used by the user. 
	 */

	private GestureDetector createGestureDetector() {
		GestureDetector gestureDetector = new GestureDetector(this);
		gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
			@Override
			public boolean onGesture(Gesture gesture) {
				if (gesture == Gesture.TAP) {
					//Log.d(TAG, "Gesture.TAP");
					// return true if you handled this event
				} else if (gesture == Gesture.TWO_TAP) {
					//Log.d(TAG, "Gesture.TWO_TAP");
					// return true if you handled this event
				} else if (gesture == Gesture.SWIPE_RIGHT) {
					//Log.d(TAG, "Gesture.SWIPE_RIGHT");
					// return true if you handled this event
				} else if (gesture == Gesture.SWIPE_LEFT) {
					//Log.d(TAG, "Gesture.SWIPE_LEFT");
					// return true if you handled this event
				}

				return false;
			}
		});
		gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
			@Override
			public void onFingerCountChanged(int previousCount, int currentCount) {
				//Log.d(TAG, String.format("Finger prev:%d curr:%d", previousCount, currentCount));
			}
		});
		gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
			@Override
			public boolean onScroll(float displacement, float delta, float velocity) {
				//Log.d(TAG, String.format("Scroll dis:%.1f delta:%.1f vel:%.1f", displacement, delta, velocity));
				return false; // return true if you handled this event
			}
		});

		return gestureDetector;
	}
	
	private void makeToast(String x){
		Toast.makeText(this, x,Toast.LENGTH_SHORT ).show();
	}
	/*
	 * Function that deals with voice recognition.
	 * It checks if the received word is the same word as the one given and also gets the name of the user.  
	 */
	@SuppressWarnings("static-access")
	private void startListening() {
		if(USER_ID_FLAG == 0){
			textView = (TextView) findViewById(R.id.textView1);
			textView.setText("Please say your full name!");
    	}
		final long startTime = System.currentTimeMillis();
		sr = SpeechRecognizer.createSpeechRecognizer(this.getApplicationContext());
		sr.setRecognitionListener(new RecognitionListener(){
			@Override
		    public void onResults(Bundle b) {
				final long resultsTime = System.currentTimeMillis();
				recognition_time = Long.toString(resultsTime - startTime);
		    	ArrayList<String> recognized = b.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		    	if(USER_ID_FLAG == 0){
		    		for(int i = 0; i< recognized.size(); i++){
			    		String tempword = recognized.get(i);
			    		recognized_word = tempword;
			    		USER_ID = USER_ID + " " + tempword;
		    		}
		    		USER_ID = "'"+ USER_ID +"'"; 
		    		USER_ID_FLAG = 1;

		    	}
		    	else {
			    	//Log.d(TAG, "Results" + recognized.toString());
			    	results = "";
			    	original_word = original_word.toLowerCase();
			    	for(int i = 0; i< recognized.size(); i++){
			    		String tempword = recognized.get(i);
			    		recognized_word = tempword;
			    		if(i==0){
			    			tempword = tempword.toLowerCase();
				    		if(original_word.equals(tempword)){
				    			recognition_correct = "1";
				    		}
				    		else{
				    			recognition_correct = "0";
				    		}
			    			String translation = "";
			    			final long startTimeTranslation = System.currentTimeMillis();
			    			AsyncTask task = new WordTranslateTask().execute(original_word);
			    			try {
			    				translation = (String) task.get();
			    				translated_word = translation;
			    				final long endTimeTranslation = System.currentTimeMillis();
			    				//textView.setText(translated_word);
			    				translation_time = Long.toString(endTimeTranslation - startTimeTranslation);
			    				//Log.d(TAG, "Original translation " + original_translation);
			    				//Log.d(TAG, "Translated word " + translated_word);
			    				if(translated_word.equals(original_translation)){
			    					translation_correct = "1";
			    				}
			    				else{
			    					translation_correct = "0";
			    				}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    			//Log.d(TAG, "translation " + translation);
			    			AsyncTask storeTask = new StoreToDatabase().execute();
			    			translatedAudio = true;
			    			
			    			/*try {
								String result= (String) storeTask.get();
								Log.d(TAG, result);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
			    			
			    		}
			    		
			    		results = results + tempword + " ";
			    	}
		    	}
		    	
		    	//Get confidence scores
		    	float[] scores = b.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
		    	if(scores!=null){
		    		Log.d(TAG, "Scores " + scores[0]);
		    	}
		    	
		    	sr.destroy();	
		    	displayWord();
		    	startListening();
		    	
		    }

			@Override
			public void onReadyForSpeech(Bundle params) {
				//Log.d(TAG, "Ready for speech");	
			}

			@Override
			public void onBeginningOfSpeech() {
				//Log.d(TAG, "Started Speaking");
			}

			@Override
			public void onRmsChanged(float rmsdB) {
				
			}

			@Override
			public void onBufferReceived(byte[] buffer) {
				Log.d(TAG, "Buffer received");	
			}

			@Override
			public void onEndOfSpeech() {
				final long endOfSpeechTime = System.currentTimeMillis();
				Log.d(TAG, "Speech ended");
			}

			/*
			 * Function that deals with any runtime error. 
			 */
			@Override
			public void onError(int error) {
				Log.d(TAG, "error " + error);
				sr.startListening(intent);
				switch (error) {
		        case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:                
		            Log.d(TAG, " network timeout"); 
		            break;
		        case SpeechRecognizer.ERROR_NETWORK: 
		            Log.d(TAG,  " network error") ;
		            return;
		        case SpeechRecognizer.ERROR_AUDIO: 
		            Log.d(TAG, " audio error"); 
		            break;
		        case SpeechRecognizer.ERROR_SERVER: 
		            Log.d(TAG, "Server error"); 
		            break;
		        case SpeechRecognizer.ERROR_CLIENT: 
		            Log.d(TAG,"Client error"); 
		            break;
		        case SpeechRecognizer.ERROR_SPEECH_TIMEOUT: 
		            Log.d(TAG, " speech time out") ; 
		            break;
		        case SpeechRecognizer.ERROR_NO_MATCH: 
		            Log.d(TAG, " no match") ; 
		            break;
		        case SpeechRecognizer.ERROR_RECOGNIZER_BUSY: 
		            Log.d(TAG, " recogniser busy") ; 
		            //sr.stopListening();
		            //sr.cancel();
		            //sr.destroy();
		            //sr = null;
		            //startListening();
		            break;
		        case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS: 
		            Log.d(TAG, " insufficient permissions") ; 
		            break;

		        }
			}
			
			/*
			 * ...
			 */
			
			@Override
			public void onPartialResults(Bundle partialResults) {
				ArrayList<String> recognized = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
				//System.out.println(recognized.toString());
				//Log.d(TAG, "Partial resutls received" + recognized.toString());
			}

			@Override
			public void onEvent(int eventType, Bundle params) {
				Log.d(TAG, "Event " + eventType);
			}
		});
		
		
		intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES, true);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, pref_language);
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
		
		//intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
		
		
		if(sr.isRecognitionAvailable(this)){
			Log.d(TAG, "Speech recognition available");
		}
		else{
			Log.d(TAG, "Speech recognition not available");
		}
		//Log.d(TAG, "Started Speech recognition module");
		//textView.setText("Recording Audio");
		
		//If you want to continuously record in the background
		if(AUDIO_METHOD == BACKGROUND_RECORDER){
			sr.startListening(intent);
		}
		
		//If you want to give the user a prompt when they are speaking
		else{
		//intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
		this.startActivityForResult(intent, 100);
		}
	}
	
	/*
	 * Function displays text on the screen and then reads it out loud.
	 */
	/*private void showMessageAndSpeak(String message) {
		textView.setText(message);
		startListening();
		tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
	}*/
	
}
