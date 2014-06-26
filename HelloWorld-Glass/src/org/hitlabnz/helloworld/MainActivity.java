package org.hitlabnz.helloworld;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public class MainActivity extends Activity {

	public final static String TAG = MainActivity.class.getSimpleName();
	public final String InputFileName = "eng.txt";
	public final String pref_language = "en-us";
	public final static int SPEECH_PROMPT = 1;
	public static int BACKGROUND_RECORDER = 2;
	
	public final static int AUDIO_METHOD = BACKGROUND_RECORDER;
	public boolean STARTED_LISTENING = false;
	
	public ArrayList<String> wordList = new ArrayList<String>();
	
	private static final String APPLICATION_NAME = "QCRIGlass/1.0";

	/** API Key for the registered developer project for your application. */
	private static final String API_KEY = "AIzaSyA_4Wk5XsirNZq13yL6PQGq6c-wLQZf3-c";

	/** Global instance of the JSON factory. */
	//private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	//private static HttpTransport httpTransport;

	@SuppressWarnings("unused")
	//private static Translate client;

	
	SpeechRecognizer sr;
	TextView textView;
	GestureDetector gestureDetector;
	TextToSpeech tts;
	String results = "recording audio";
	Intent intent;
	BufferedReader reader;
	String OutputFile = "results.txt";
	BufferedWriter writer;
	String curWord;
	String sourceLanguage = "en";
	String targetLanguage = "ar";
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//To prevent the screen from going to sleep
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		readResults();
		
		// get text view inflated from the layout resource
		textView = (TextView) findViewById(R.id.textView1);
		textView.setText("Tap to begin");

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
		
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {

	    Log.i(TAG, "on pause called");
	    if(sr!=null){
	        //sr.stopListening();
	        //sr.cancel();
	        sr.destroy();              

	    }
	    sr = null;

	    super.onPause();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_android:
			//showMessageAndSpeak("Hello Android!");
			return true;
		case R.id.menu_item_glass:
			//showMessageAndSpeak("Hello Glass!");
			return true;
		case R.id.menu_item_to_me:
			//startListening();
			return true;
		case R.id.menu_item_camera:
			//startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 200);
			break;
		case R.id.menu_item_reset:
			//showMessageAndSpeak("Hello world!");
			//this.findViewById(R.id.backgroundLayout).setBackgroundDrawable(null);
			return true;
		case R.id.menu_item_about:
			//startActivity(new Intent(this, InfoActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void writeToFile(String result){
		Log.d(TAG, "Writing results to file " + result);
         FileOutputStream fos = null;
		try {
			fos = openFileOutput(OutputFile,Context.MODE_APPEND);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         try {
			fos.write(result.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         try {
			fos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readResults(){
		Log.d(TAG, "Reading results");
		int c;
		String temp = "";
		try {
			FileInputStream fin = openFileInput(OutputFile);
			while((c = fin.read()) != -1){
				temp = temp + Character.toString((char) c);
			}
			fin.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(TAG, temp);
	}
	
	public void initiateReader(){
		try {
			reader = new BufferedReader(
			        new InputStreamReader(getAssets().open(InputFileName)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void pullWords(){
		String line;
		try {
			while((line = reader.readLine()) != null ){
				wordList.add(line);
				Log.d(TAG, line);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void displayWord(){
		Random randomGenerator = new Random();
		int randInt = randomGenerator.nextInt(wordList.size());
		curWord = wordList.get(randInt);
		textView.setText(curWord);
	}
	
	private class WordTranslateTask extends AsyncTask<String, String, String>{
	
		@Override
		protected String doInBackground(String...strings ) {
			Log.d(TAG, "Translate word");
			String inputLine = "";
			String result = "";
			URL url;
			try {
				url = new URL("https://www.googleapis.com/language/translate/v2?key=" + API_KEY + "&source=" + sourceLanguage + "&target=" + targetLanguage + "&q=" + strings[0]);
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
			Log.d(TAG, result);
			return result;
	        
		}
		
		protected void onPostExecute(String s){
			Log.d(TAG, "Post execute");
			String translation = "";
			try {
				JSONObject jsonObj = new JSONObject(s);
				translation = jsonObj.getJSONObject("data").getJSONArray("translations").getJSONObject(0).getString("translatedText");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Log.d(TAG, "Translation " + translation);
			this.publishProgress(translation);
			return;
		}
		
		protected String onProgress(String s){
			Log.d(TAG, "Onprogress");
			return s;
			
		}
		
		
	}
	/*
	public String translateTest(String text){
		try {
		      // initialize the transport
		      //httpTransport = GoogleNetHttpTransport.newTrustedTransport();

		      // set up global Translate instance
		      //client = new Translate.Builder(httpTransport, JSON_FACTORY, null)
		         // .setGoogleClientRequestInitializer(new TranslateRequestInitializer(API_KEY))
		         // .setApplicationName(APPLICATION_NAME).build();

			
		      client.execute(text, Language, Language.ARABIC);
		      Log.d(TAG, "Success! Now add code here.");

		    } catch (Throwable t) {
		      t.printStackTrace();
		    }
		return text;

	}
	*/
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_CENTER:
			Log.d(TAG, "Tapped (DPAD_CENTER)");
			initiateReader();
			pullWords();
			displayWord();
			if(!STARTED_LISTENING){
				startListening();
				STARTED_LISTENING = true;
				
			}
			//openOptionsMenu(); // open the option menu on tap
			return true; // return true if you handled this event
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//Log.d(TAG, String.format("Motion ACTION_DOWN: %.1f / %.1f", event.getX(), event.getY()));
			// return true if you handled this event
			break;
		case MotionEvent.ACTION_MOVE:
			//Log.d(TAG, String.format("Motion ACTION_MOVE: %.1f / %.1f", event.getX(), event.getY()));
			// return true if you handled this event
			break;
		case MotionEvent.ACTION_UP:
			//Log.d(TAG, String.format("Motion ACTION_UP: %.1f / %.1f", event.getX(), event.getY()));
			// return true if you handled this event
			break;
		}

		if(gestureDetector.onMotionEvent(event))
			return true;

		return super.onGenericMotionEvent(event);
	}

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

	@SuppressWarnings("static-access")
	private void startListening() {
		//Log.d(TAG, "Entered startListening");
		// open system voice recognizer
		//Log.d(TAG, "Printing results");
		sr = SpeechRecognizer.createSpeechRecognizer(this.getApplicationContext());
		sr.setRecognitionListener(new RecognitionListener(){
			@Override
		    public void onResults(Bundle b) { 
		    	ArrayList<String> recognized = b.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		    	Log.d(TAG, "Results" + recognized.toString());
		    	results = "";
		    	curWord = curWord.toLowerCase();
		    	String isCorrect = "false";
		    	for(int i = 0; i< recognized.size(); i++){
		    		
		    		String tempword = recognized.get(i);
		    		if(i==0){
		    			
		    			AsyncTask task = new WordTranslateTask().execute(tempword);
		    			try {
							String translation = (String) task.get();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
		    			//translateTest(tempword);
		    		}
		    		tempword = tempword.toLowerCase();
		    		if(curWord.equals(tempword)){
		    			isCorrect = "true";
		    		}
		    		results = results + tempword + " ";
		    	}
		    	writeToFile(curWord + " " + results + isCorrect + "\n" );
		    	float[] scores = b.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
		    	if(scores!=null){
		    		Log.d(TAG, "Scores " + scores[0]);
		    	}
		    	//sr.destroy();
		    	displayWord();
		    	sr.destroy();
		    	startListening();
		    }

			@Override
			public void onReadyForSpeech(Bundle params) {
				Log.d(TAG, "Ready for speech");	
			}

			@Override
			public void onBeginningOfSpeech() {
				Log.d(TAG, "Started Speaking");
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
				Log.d(TAG, "Speech ended");
				//sr.stopListening();
				//sr.cancel();
				//sr.destroy();
				//startListening();
			}

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
		            //startListening();
		            break;
		        case SpeechRecognizer.ERROR_CLIENT: 
		            Log.d(TAG,"Client error"); 
		            break;
		        case SpeechRecognizer.ERROR_SPEECH_TIMEOUT: 
		            Log.d(TAG, " speech time out") ; 
		            break;
		        case SpeechRecognizer.ERROR_NO_MATCH: 
		            Log.d(TAG, " no match") ; 
		            //startListening();
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
		//intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true);
		intent.putExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES, true);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, pref_language);
		//intent.putExtra(RecognizerIntent.EXTRA_RESULTS, true);
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,2);
		
		//intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
		
		
		if(sr.isRecognitionAvailable(this)){
			Log.d(TAG, "Speech recognition available");
		}
		else{
			Log.d(TAG, "Speech recognition not available");
		}
		Log.d(TAG, "Started Speech recognition module");
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "Entered onActivityResult");
		if (requestCode == 100) {
			if(resultCode == Activity.RESULT_OK) {
				// get results from the voice recognizer
				List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				//showMessageAndSpeak( results.get(0));
				Toast.makeText(this, "Gotcha. Speak again!", Toast.LENGTH_SHORT).show();
				Log.d(TAG, "Successfully got activity result " + results.toString() );
			} else {
				Toast.makeText(this, "Cannot get your name!", Toast.LENGTH_SHORT).show();
				
				// play a system sound
				AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				audio.playSoundEffect(Sounds.ERROR);
				
				Log.wtf(TAG, "Activity might got canceled?");
			}
		super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	private void showMessageAndSpeak(String message) {
		textView.setText(message);
		startListening();
		//tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
	}
	
}
