package com.hernandazevedo.androidmp3recorder;

public class AndroidLameEncoderBuilder {


    public enum Mode {
        STEREO, JSTEREO, MONO, DEFAULT
        //DUAL_CHANNEL not supported
    }

    public enum VbrMode {
        VBR_OFF, VBR_RH, VBR_MTRH, VBR_ABR, VBR_DEFAUT
    }


    public int inSampleRate;
    public int outSampleRate;
    public int outBitrate;
    public int outChannel;
    public int quality;
    public int vbrQuality;
    public int abrMeanBitrate;
    public int lowpassFreq;
    public int highpassFreq;
    public float scaleInput;
    public Mode mode;
    public VbrMode vbrMode;

    public String id3tagTitle;
    public String id3tagArtist;
    public String id3tagAlbum;
    public String id3tagComment;
    public String id3tagYear;

    public AndroidLameEncoderBuilder() {

        this.id3tagTitle = null;
        this.id3tagAlbum = null;
        this.id3tagArtist = null;
        this.id3tagComment = null;
        this.id3tagYear = null;

        this.inSampleRate = 44100;

        //default 0, Lame picks best according to compression
        this.outSampleRate = 0;

        this.outChannel = 2;
        this.outBitrate = 128;
        this.scaleInput = 1;

        this.quality = 5;
        this.mode = Mode.DEFAULT;
        this.vbrMode = VbrMode.VBR_OFF;
        this.vbrQuality = 5;
        this.abrMeanBitrate = 128;

        //default =0, Lame chooses
        this.lowpassFreq = 0;
        this.highpassFreq = 0;
    }

    public AndroidLameEncoderBuilder setQuality(int quality) {
        this.quality = quality;
        return this;
    }

    public AndroidLameEncoderBuilder setInSampleRate(int inSampleRate) {
        this.inSampleRate = inSampleRate;
        return this;
    }

    public AndroidLameEncoderBuilder setOutSampleRate(int outSampleRate) {
        this.outSampleRate = outSampleRate;
        return this;
    }

    public AndroidLameEncoderBuilder setOutBitrate(int bitrate) {
        this.outBitrate = bitrate;
        return this;
    }

    public AndroidLameEncoderBuilder setOutChannels(int channels) {
        this.outChannel = channels;
        return this;
    }

    public AndroidLameEncoderBuilder setId3tagTitle(String title) {
        this.id3tagTitle = title;
        return this;
    }

    public AndroidLameEncoderBuilder setId3tagArtist(String artist) {
        this.id3tagArtist = artist;
        return this;
    }

    public AndroidLameEncoderBuilder setId3tagAlbum(String album) {
        this.id3tagAlbum = album;
        return this;
    }

    public AndroidLameEncoderBuilder setId3tagComment(String comment) {
        this.id3tagComment = comment;
        return this;
    }

    public AndroidLameEncoderBuilder setId3tagYear(String year) {
        this.id3tagYear = year;
        return this;
    }

    public AndroidLameEncoderBuilder setScaleInput(float scaleAmount) {
        this.scaleInput = scaleAmount;
        return this;
    }

    public AndroidLameEncoderBuilder setMode(Mode mode) {
        this.mode = mode;
        return this;
    }

    public AndroidLameEncoderBuilder setVbrMode(VbrMode mode) {
        this.vbrMode = mode;
        return this;
    }

    public AndroidLameEncoderBuilder setVbrQuality(int quality) {
        this.vbrQuality = quality;
        return this;
    }

    public AndroidLameEncoderBuilder setAbrMeanBitrate(int bitrate) {
        this.abrMeanBitrate = bitrate;
        return this;
    }

    public AndroidLameEncoderBuilder setLowpassFreqency(int freq) {
        this.lowpassFreq = freq;
        return this;
    }

    public AndroidLameEncoderBuilder setHighpassFreqency(int freq) {
        this.highpassFreq = freq;
        return this;
    }

    public AndroidLameEncoder build() {
        return new AndroidLameEncoder(this);
    }

}
