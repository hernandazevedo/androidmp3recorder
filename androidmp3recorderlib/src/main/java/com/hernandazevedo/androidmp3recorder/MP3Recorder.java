package com.hernandazevedo.androidmp3recorder;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import java.io.File;
import java.io.IOException;

public class MP3Recorder {
	private static final int DEFAULT_AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
	public static final int DEFAULT_SAMPLING_RATE = 44100;
	private static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
	private static final int DEFAULT_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
	private static final int DEFAULT_LAME_IN_CHANNEL = 1;
	private static final int DEFAULT_LAME_MP3_BIT_RATE = 32;

	private static final int FRAME_COUNT = 160;
	private AudioRecord mAudioRecord = null;
	private int mBufferSize;
	private short[] mPCMBuffer;
	private EncodeWorkerThread mEncodeThread;
	private boolean mIsRecording = false;
	private File mRecordFile;

	public MP3Recorder(File recordFile) {
		mRecordFile = recordFile;
	}

	public void start() throws IOException {
		if (mIsRecording) {
			return;
		}
		mIsRecording = true;
	    initAudioRecorder();
		mAudioRecord.startRecording();
		new Thread() {
			@Override
			public void run() {
				android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
				while (mIsRecording) {
					int readSize = mAudioRecord.read(mPCMBuffer, 0, mBufferSize);
					if (readSize > 0) {
						mEncodeThread.addTask(mPCMBuffer, readSize);
						calculateRealVolume(mPCMBuffer, readSize);
					}
				}

				mAudioRecord.stop();
				mAudioRecord.release();
				mAudioRecord = null;

				mEncodeThread.sendStopMessage();
			}

			private void calculateRealVolume(short[] buffer, int readSize) {
				double sum = 0;
				for (int i = 0; i < readSize; i++) {
				    sum += buffer[i] * buffer[i]; 
				} 
				if (readSize > 0) {
					double amplitude = sum / readSize;
					mVolume = (int) Math.sqrt(amplitude);
				}
			}
		}.start();
	}
	private int mVolume;

	public int getRealVolume() {
		return mVolume;
	}

	public int getVolume(){
		if (mVolume >= MAX_VOLUME) {
			return MAX_VOLUME;
		}
		return mVolume;
	}
	private static final int MAX_VOLUME = 2000;

	public int getMaxVolume(){
		return MAX_VOLUME;
	}
	public void stop(){
		mIsRecording = false;
	}

	public boolean isRecording() {
		return mIsRecording;
	}

	private void initAudioRecorder() throws IOException {
		mBufferSize = AudioRecord.getMinBufferSize(DEFAULT_SAMPLING_RATE,
				DEFAULT_CHANNEL_CONFIG, DEFAULT_AUDIO_FORMAT);

        mAudioRecord = new AudioRecord(
                DEFAULT_AUDIO_SOURCE,
                DEFAULT_SAMPLING_RATE,
                DEFAULT_CHANNEL_CONFIG,
                DEFAULT_AUDIO_FORMAT, mBufferSize * 2);
		
		mPCMBuffer = new short[DEFAULT_SAMPLING_RATE * 2 * 5];

		AndroidLameEncoder androidLameEncoder = new AndroidLameEncoderBuilder()
				.setInSampleRate(DEFAULT_SAMPLING_RATE)
				.setOutChannels(DEFAULT_LAME_IN_CHANNEL)
				.setOutBitrate(DEFAULT_LAME_MP3_BIT_RATE)
				.setOutSampleRate(DEFAULT_SAMPLING_RATE)
				.build();

		mEncodeThread = new EncodeWorkerThread(mRecordFile, mPCMBuffer.length, androidLameEncoder);
		mEncodeThread.start();
		mAudioRecord.setRecordPositionUpdateListener(mEncodeThread, mEncodeThread.getHandler());
		mAudioRecord.setPositionNotificationPeriod(FRAME_COUNT);
	}
}