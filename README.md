# CredentialManagerSignInApp
This repository contains sample source code to implement Authentication/SignIn 
using Google's CredentialManager. 
Note that "One Tap" SignIn for Android will be deprecated soon. 
This sample uses new SignIn using CredentialManager as described in 
https://developer.android.com/training/sign-in/credential-manager

This sample code does following

1. Provides a "Signin with Google" button in MainActivity.kt
2. Upon Click, user is authenticated and signed-in to the app
3. Toasts are displayed for Success or Failure cases

Note: Before launching the app, please make sure to make a note of important points
Below given are not all the steps but a few items to remember.
1. Setup your Firebase API console https://console.cloud.google.com/apis/credentials
2. Create new project in console for your App and configure as directed during setup
3. Go to Firebase console -> APIs & Service -> Credentials -> Create Credentials ->
Create 2 OAuth client ids -> Android Client id and Web Client id 
4. Replace my dummy-client-id with your actual Client id from console
   Make sure to use Client Id in App from Web Client (As per my experience, if i use client id from Android client then 
   method GetCredential throws NoCredentialException)
   Generate SHA-1 using steps mentioned in https://support.google.com/cloud/answer/6158849?hl=en_GB#installedapplications&android
   Save
   Now you can add your client-id in strings.xml and use with building GetSignInWithGoogleOption
5. Make sure configure correct Keystore for debug and release before installing the app on device   
6. For some reason, it never worked for me on Emulator so i always ran the app on actual device
