package com.hernandazevedo.androidmp3recorder.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hernandazevedo.androidmp3recorder.MP3Recorder;

import java.io.File;

import androidmp3recorder.hernandazevedo.com.androidmp3recorder.R;

public class MainActivity extends AppCompatActivity {

    private static final int RECORD_AUDIO_REQUEST_CODE = 321;
    private MP3Recorder mRecorder = new MP3Recorder(new File(Environment.getExternalStorageDirectory(),"mp3record.mp3"));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button recordButton = (Button) findViewById(R.id.record_btn);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecord();
            }
        });
        Button stopButton = (Button) findViewById(R.id.stop_btn);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecord();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRecord();
    }

    public void startRecord(){
        try{
            if(isPermissionGranted())
                mRecorder.start();
            else
                requestPermissionToRecordAudio();
        }catch (Exception e) {
            Log.e("Main", e.getMessage(), e);
        }
    }

    public void stopRecord(){
        try{
            mRecorder.stop();
        }catch (Exception e) {
            Log.e("Main", e.getMessage(), e);
        }
    }

    public void requestPermissionToRecordAudio() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                RECORD_AUDIO_REQUEST_CODE);
    }

    public boolean isPermissionGranted(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }else {
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        int grantResultCount = 0;
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (permission.equals(Manifest.permission.RECORD_AUDIO) && grantResult == PackageManager.PERMISSION_GRANTED) {
                        grantResultCount = grantResultCount + 1;
                }
                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE) && grantResult == PackageManager.PERMISSION_GRANTED) {
                        grantResultCount = grantResultCount + 1;
                }
                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResult == PackageManager.PERMISSION_GRANTED) {
                        grantResultCount = grantResultCount + 1;
                }
            }
        }

        if(grantResultCount >= 3) {
            startRecord();
        }
    }
}
