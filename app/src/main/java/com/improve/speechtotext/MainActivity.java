package com.improve.speechtotext;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView resultText;
    ImageButton mic;
    ActivityResultLauncher<Intent> activityResultLauncherForText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerActivityForText();

        resultText = findViewById(R.id.textView);
        mic = findViewById(R.id.imageButton);

        mic.setOnClickListener(v -> {
            convertSpeech();
            });

    }

    public void convertSpeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        //startActivityForResult(intent, 1);
        activityResultLauncherForText.launch(intent);
    }

/*
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null)
       {
            ArrayList<String> speakResults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            resultText.setText(speakResults.get(0));
        }
    }
*/

    public void registerActivityForText()
    {
        activityResultLauncherForText = registerForActivityResult(new ActivityResultContracts
                        .StartActivityForResult(), result -> {

                    int resultCode = result.getResultCode();
                    Intent data = result.getData();

                    if (resultCode == RESULT_OK && data != null)
                    {
                        ArrayList<String> speakResults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        resultText.setText(speakResults.get(0));
                    }

                    else
                    {
                        resultText.setText("No Speech Detected");
                    }
        });
    }
}    