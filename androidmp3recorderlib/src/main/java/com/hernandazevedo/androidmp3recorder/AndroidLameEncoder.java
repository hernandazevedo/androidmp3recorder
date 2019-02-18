package com.hernandazevedo.androidmp3recorder;

public class AndroidLameEncoder {

    static {
        System.loadLibrary("androidmp3recorder");
    }

    public AndroidLameEncoder() {
        initializeDefault();
    }

    public AndroidLameEncoder(AndroidLameEncoderBuilder builder) {
        initialize(builder);
    }

    private void initialize(AndroidLameEncoderBuilder builder) {
        initialize(builder.inSampleRate, builder.outChannel, builder.outSampleRate,
                builder.outBitrate, builder.scaleInput, getIntForMode(builder.mode), getIntForVbrMode(builder.vbrMode), builder.quality, builder.vbrQuality, builder.abrMeanBitrate,
                builder.lowpassFreq, builder.highpassFreq, builder.id3tagTitle, builder.id3tagArtist,
                builder.id3tagAlbum, builder.id3tagYear, builder.id3tagComment);
    }

    public int encode(short[] buffer_l, short[] buffer_r,
                      int samples, byte[] mp3buf) {

        return lameEncode(buffer_l, buffer_r, samples, mp3buf);
    }

    public int encodeBufferInterLeaved(short[] pcm, int samples,
                                       byte[] mp3buf) {
        return encodeBufferInterleaved(pcm, samples, mp3buf);
    }

    public int flush(byte[] mp3buf) {
        return lameFlush(mp3buf);
    }

    public void close() {
        lameClose();
    }


    ///////////NATIVE
    private static native void initializeDefault();

    private static native void initialize(int inSamplerate, int outChannel,
                                          int outSamplerate, int outBitrate, float scaleInput, int mode, int vbrMode,
                                          int quality, int vbrQuality, int abrMeanBitrate, int lowpassFreq, int highpassFreq, String id3tagTitle,
                                          String id3tagArtist, String id3tagAlbum, String id3tagYear,
                                          String id3tagComment);

    private native static int lameEncode(short[] buffer_l, short[] buffer_r,
                                         int samples, byte[] mp3buf);


    private native static int encodeBufferInterleaved(short[] pcm, int samples,
                                                      byte[] mp3buf);


    private native static int lameFlush(byte[] mp3buf);


    private native static void lameClose();


    ////UTILS
    private static int getIntForMode(AndroidLameEncoderBuilder.Mode mode) {
        switch (mode) {
            case STEREO:
                return 0;
            case JSTEREO:
                return 1;
            case MONO:
                return 3;
            case DEFAULT:
                return 4;
        }
        return -1;
    }

    private static int getIntForVbrMode(AndroidLameEncoderBuilder.VbrMode mode) {
        switch (mode) {
            case VBR_OFF:
                return 0;
            case VBR_RH:
                return 2;
            case VBR_ABR:
                return 3;
            case VBR_MTRH:
                return 4;
            case VBR_DEFAUT:
                return 6;
        }
        return -1;
    }
}
