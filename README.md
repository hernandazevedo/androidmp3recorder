[![Build Status](https://app.bitrise.io/app/c0d86791484e4813/status.svg?token=_ar7s0p-wdj7duBixe3OxQ&branch=master)](https://app.bitrise.io/app/c0d86791484e4813)



# androidmp3recorder
This project is an Android Library that acts as a Wrapper over Lame mp3 encoder for audio recording.

## Getting Started
To use the project see the project sample(module app) that demonstrates the use of the class MP3Recorder.
To add this library in your android projects you just need to specify the repository and implementation of the library in your gradle configuration as follows.

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

License
This project is licensed under the Apache License 2.0 - see the LICENSE file for details
