package org.hitlabnz.helloworld;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.glass.media.CameraManager;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public class MainActivity extends Activity {

	public final static String TAG = MainActivity.class.getSimpleName();

	TextView textView;
	GestureDetector gestureDetector;
	TextToSpeech tts;
	SoundPool soundPool;
	int soundID;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
		
		// play opening sound
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundID = soundPool.load(this, R.raw.bell, 1);
		soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool arg0, int arg1, int arg2) {
				soundPool.play(soundID, 1, 1, 1, 0, 1);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// shutdown tts
		tts.shutdown();
		
		super.onDestroy();
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
			//onSayHelloToMe();
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_CENTER:
			Log.d(TAG, "Tapped (DPAD_CENTER)");
			onSayHelloToMe();
			//openOptionsMenu(); // open the option menu on tap
			return true; // return true if you handled this event
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.d(TAG, String.format("Motion ACTION_DOWN: %.1f / %.1f", event.getX(), event.getY()));
			// return true if you handled this event
			break;
		case MotionEvent.ACTION_MOVE:
			Log.d(TAG, String.format("Motion ACTION_MOVE: %.1f / %.1f", event.getX(), event.getY()));
			// return true if you handled this event
			break;
		case MotionEvent.ACTION_UP:
			Log.d(TAG, String.format("Motion ACTION_UP: %.1f / %.1f", event.getX(), event.getY()));
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
					Log.d(TAG, "Gesture.TAP");
					// return true if you handled this event
				} else if (gesture == Gesture.TWO_TAP) {
					Log.d(TAG, "Gesture.TWO_TAP");
					// return true if you handled this event
				} else if (gesture == Gesture.SWIPE_RIGHT) {
					Log.d(TAG, "Gesture.SWIPE_RIGHT");
					// return true if you handled this event
				} else if (gesture == Gesture.SWIPE_LEFT) {
					Log.d(TAG, "Gesture.SWIPE_LEFT");
					// return true if you handled this event
				}

				return false;
			}
		});
		gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
			@Override
			public void onFingerCountChanged(int previousCount, int currentCount) {
				Log.d(TAG, String.format("Finger prev:%d curr:%d", previousCount, currentCount));
			}
		});
		gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
			@Override
			public boolean onScroll(float displacement, float delta, float velocity) {
				Log.d(TAG, String.format("Scroll dis:%.1f delta:%.1f vel:%.1f", displacement, delta, velocity));
				return false; // return true if you handled this event
			}
		});

		return gestureDetector;
	}

	private void onSayHelloToMe() {
		// open system voice recognizer
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
		this.startActivityForResult(intent, 1000);
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
				Log.d(TAG, "Successfully got activity result");
			} else {
				Toast.makeText(this, "Cannot get your name!", Toast.LENGTH_SHORT).show();
				
				// play a system sound
				AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				audio.playSoundEffect(Sounds.ERROR);
				
				Log.wtf(TAG, "Activity might got canceled?");
			}
		} else if (requestCode == 200) {
			if(resultCode == Activity.RESULT_OK) {
				String picturePath = data.getStringExtra(CameraManager.EXTRA_THUMBNAIL_FILE_PATH);
				processPictureWhenReady(picturePath); // the file might not be ready right away
				Toast.makeText(this, "Loading the picture!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Failed to take a picture!", Toast.LENGTH_SHORT).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void showMessageAndSpeak(String message) {
		textView.setText(message);
		onSayHelloToMe();
		//tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	private void processPictureWhenReady(final String picturePath) {
	    final File pictureFile = new File(picturePath);

	    if (pictureFile.exists()) {
	        // The picture is ready; process it.
	    	RelativeLayout layout = (RelativeLayout)findViewById(R.id.backgroundLayout);
	    	layout.setBackgroundDrawable(Drawable.createFromPath(picturePath));
	    } else {
	        // The file does not exist yet. Before starting the file observer, you
	        // can update your UI to let the user know that the application is
	        // waiting for the picture (for example, by displaying the thumbnail
	        // image and a progress indicator).

	        final File parentDirectory = pictureFile.getParentFile();
	        FileObserver observer = new FileObserver(parentDirectory.getPath()) {
	            // Protect against additional pending events after CLOSE_WRITE is handled.
	            private boolean isFileWritten;

	            @Override
	            public void onEvent(int event, String path) {
	                if (!isFileWritten) {
	                    // For safety, make sure that the file that was created in
	                    // the directory is actually the one that we're expecting.
	                    File affectedFile = new File(parentDirectory, path);
	                    isFileWritten = (event == FileObserver.CLOSE_WRITE
	                            && affectedFile.equals(pictureFile));

	                    if (isFileWritten) {
	                        stopWatching();

	                        // Now that the file is ready, recursively call
	                        // processPictureWhenReady again (on the UI thread).
	                        runOnUiThread(new Runnable() {
	                            @Override
	                            public void run() {
	                                processPictureWhenReady(picturePath);
	                            }
	                        });
	                    }
	                }
	            }
	        };
	        observer.startWatching();
	    }
	}
	
}
