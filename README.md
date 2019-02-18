# androidmp3recorder
This project is an Android Library that acts as a Wrapper over Lame mp3 encoder for audio recording.

## Getting Started
To use the project see the project sample(module app) that demonstrates the use of the class MP3Recorder.
To this library in your android projects you only need to specify the repository and implementation of the library in your gradle configuration as follows.

## Gradle Configuration
### Inside your root build.gradle

 ```
 allprojects {
    repositories { 
        google()
        jcenter()
        //adds the library repository       
        maven {
             url "https://packagecloud.io/hernandazevedo/androidmp3recorder/maven2"
        }
       
    }
}
```

### In your app build.gradle

```
implementation 'com.hernandazevedo:androidmp3recorderlib:1.0.0'
```
