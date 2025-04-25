package com.example.natourapp.auth_controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.regions.Regions;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Collections;

public class Cognito {
    private final CognitoUserPool cognitoUserPool;
    private String userPassword;
    private final CognitoUserAttributes cognitoUserAttributes;
    private final Context context;

    public Cognito(Context context) {
        String clientId = "AWS_CLIENT_ID";
        String userPoolID ="AWS_USER_POOL_ID";
        Regions cognitoRegion = Regions.EU_CENTRAL_1;
        cognitoUserAttributes = new CognitoUserAttributes();
        cognitoUserPool = new CognitoUserPool(context,userPoolID,clientId,null,cognitoRegion);
        this.context = context;
    }


    public void signUpInBackground(String userId, String userPassword,SignUpHandler signUpHandler) {
        cognitoUserPool.signUpInBackground(userId, userPassword, cognitoUserAttributes, null, signUpHandler);
    }

    public void login(String username,String userPassword,AuthenticationHandler authenticationHandler) {
        CognitoUser cognitoUser = cognitoUserPool.getUser(username);
        this.userPassword = userPassword;
        cognitoUser.getSessionInBackground(authenticationHandler);
    }

    public void addAttribute(String key, String value) {
        cognitoUserAttributes.addAttribute(key,value);
    }

    public void signOutCognito(){
        cognitoUserPool.getUser().signOut();
        Log.d("cognito Logout ","logout effettuato con cognito");
    }

    public void signOutFacebook(){
        LoginManager.getInstance().logOut();
        Log.d("facebook Logout ","logout effettuato con facebook");
    }

    public void signOutGoogle(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(context,googleSignInOptions);
        googleSignInClient.signOut();
        Log.d("google Logout ","logout effettuato con google");
    }

    public void loginWithFacebook(AppCompatActivity activity,FacebookCallback<LoginResult> facebookCallback){
        CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,facebookCallback);
        LoginManager.getInstance().logInWithReadPermissions(activity, callbackManager, Collections.singletonList("email"));
        Log.d("facebook Login ","login effettuato con facebook");
    }

    public void loginWithGoogle(ActivityResultLauncher<Intent> googleSignInResult,Intent intent){
        googleSignInResult.launch(intent);
        Log.d("google Login ","login effettuato con google");
    }


    public void logOut(){
        String provider = SharedPrefApp.getInstance().getProvider(context);
        switch (provider){
            case "facebook":signOutFacebook(); break;
            case "cognito":signOutCognito(); break;
            case "google":signOutGoogle(); break;
        }
    }

    public String getUserPassword() {
        return userPassword;
    }

    public static class SharedPrefApp {

        SharedPreferences sharepreferences;

        public static SharedPrefApp instance = null;

        public static SharedPrefApp getInstance() {

            if (instance == null) {
                synchronized (SharedPrefApp.class) {
                    instance = new SharedPrefApp();
                }
            }
            return instance;
        }

        public void saveUserLoginState(Context context, Boolean isLoggedin,String provider,String username,Boolean admin) {
            sharepreferences = PreferenceManager
                    .getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharepreferences.edit();
            editor.putBoolean("LOGGED", isLoggedin);
            editor.putString("PROVIDER",provider);
            editor.putString("USERNAME",username);
            editor.putBoolean("ADMIN",admin);
            editor.apply();
        }

        public boolean getIsLogged(Context context) {
            sharepreferences = PreferenceManager
                    .getDefaultSharedPreferences(context);
            return sharepreferences.getBoolean("LOGGED", false);
        }

        public String getProvider(Context context){
            sharepreferences = PreferenceManager
                    .getDefaultSharedPreferences(context);
            return sharepreferences.getString("PROVIDER", "");
        }

        public String getUsername(Context context){
            sharepreferences = PreferenceManager
                    .getDefaultSharedPreferences(context);
            return sharepreferences.getString("USERNAME", "");
        }

        public boolean getIsAdmin(Context context){
            sharepreferences = PreferenceManager.getDefaultSharedPreferences(context);
            return sharepreferences.getBoolean("ADMIN",false);
        }

    }


}
