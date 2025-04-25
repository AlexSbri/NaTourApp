package com.example.natourapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.exceptions.CognitoAuthenticationFailedException;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.util.CognitoJWTParser;
import com.amazonaws.services.cognitoidentityprovider.model.UserNotConfirmedException;
import com.example.natourapp.R;
import com.example.natourapp.auth_controller.Cognito;
import com.example.natourapp.entities.Utente;
import com.example.natourapp.services.http.Richiesta;
import com.example.natourapp.services.http.richiestaUtente.RichiesteUtente;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "LoginActivity:";
    private Cognito cognito;
    private EditText usernameEmail;
    private EditText password;
    private Intent intentGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cognito = new Cognito(this);
        usernameEmail = findViewById(R.id.UsernameEditText);
        password = findViewById(R.id.PasswordEditText);
        googleLoginManagerInit();

        ImageButton buttonFacebookLogin = findViewById(R.id.FacebookImageButton);
        ImageButton buttonGoogleLogin = findViewById(R.id.GoogleImageButton);

        buttonGoogleLogin.setOnClickListener(v -> cognito.loginWithGoogle(googleLoginResult,intentGoogle));

        buttonFacebookLogin.setOnClickListener(v -> cognito.loginWithFacebook(this,facebookCallback));

        TextView registratiLabelButton=findViewById(R.id.RegistratiTextView);
        registratiLabelButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            Log.d(TAG,CLASSNAME + "label registrati cliccato");
            startActivity(intent);
        });

        Button loginButton = findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(view -> {
            cognito.login(usernameEmail.getText().toString(),password.getText().toString(),new AuthHandler());
            Log.d(TAG,CLASSNAME + "bottone login cliccato");
        });
    }


    private class AuthHandler implements AuthenticationHandler{
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            JSONObject jsonObject = CognitoJWTParser.getPayload(userSession.getIdToken().getJWTToken());
            String admin = "user";
            try {
                admin = jsonObject.getString("cognito:groups");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Cognito.SharedPrefApp.getInstance().saveUserLoginState(LoginActivity.this,true,"cognito",userSession.getUsername(), admin.equals("[\"admin\"]"));
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Log.d(TAG,CLASSNAME + "login effettuato con cognito");
            startActivity(intent);
            finish();
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
            String userPassword=cognito.getUserPassword();
            AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId,userPassword,null);
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);
            authenticationContinuation.continueTask();

        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {

        }

        @Override
        public void onFailure(Exception exception) {
            if(exception instanceof UserNotConfirmedException) {
                Toast.makeText(LoginActivity.this, "l'utente non ha confermato l'email", Toast.LENGTH_SHORT).show();
                Log.e(TAG + CLASSNAME + "Not confirmed",exception.getMessage());
            } else if (exception instanceof CognitoAuthenticationFailedException) {
                usernameEmail.setBackgroundResource(R.drawable.edit_text_error_state);
                password.setBackgroundResource(R.drawable.edit_text_error_state);
                Log.e(TAG + CLASSNAME + "Errore autenticazione",exception.getMessage());
                Toast.makeText(LoginActivity.this, "username o password errati!", Toast.LENGTH_SHORT).show();
            } else {
                usernameEmail.setBackgroundResource(R.drawable.edit_text_error_state);
                password.setBackgroundResource(R.drawable.edit_text_error_state);
                Log.e(TAG + CLASSNAME + "Errore sconosciuto",exception.getMessage());
                Toast.makeText(LoginActivity.this, "username o password errati!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(LoginResult loginResult) {
            ProfileTracker profileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(@Nullable Profile oldProfile, @Nullable Profile currProfile) {
                    if(oldProfile!=null){
                        Profile.setCurrentProfile(oldProfile);
                        Log.d("OLDPROF",oldProfile.toString());
                    }else if(currProfile!=null){
                        Profile.setCurrentProfile(currProfile);
                        Log.d("CURRPROF",currProfile.toString());
                    }
                }
            };
            Profile profile = Profile.getCurrentProfile();
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            //intent.putExtra("username",profile.getName());
            Cognito.SharedPrefApp.getInstance().saveUserLoginState(LoginActivity.this,true,"facebook", profile.getName(),false);
            Richiesta<Utente> richiesteUtente = new RichiesteUtente();
            Future<String> futureRespUtente = Executors.newSingleThreadExecutor().submit(()->richiesteUtente.add(new Utente(profile.getName())));
            try {
                String response = futureRespUtente.get();
                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG,CLASSNAME + "login effettuato con facebook");
            startActivity(intent);
            finish();
        }

        @Override
        public void onCancel() {
            Log.d("facebook on cancel","cancel");
        }

        @Override
        public void onError(@NonNull FacebookException e) {
            Log.e(TAG,CLASSNAME + "login con facebook non effettuato");
        }
    };

    ActivityResultLauncher<Intent> googleLoginResult = this.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
    result -> {

        if(result.getResultCode() == Activity.RESULT_OK){
            Intent data = result.getData();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount googleSignInAccount = task.getResult();
            Cognito.SharedPrefApp.getInstance().saveUserLoginState(LoginActivity.this,true,"google",googleSignInAccount.getDisplayName(),false);
            Richiesta<Utente> richiesteUtente = new RichiesteUtente();
            Future<String> futureRespUtente = Executors.newSingleThreadExecutor().submit(()->richiesteUtente.add(new Utente(googleSignInAccount.getDisplayName())));
            try {
                String response = futureRespUtente.get();
                if(response != null) Log.d(TAG,CLASSNAME + response);
                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            Log.d(TAG,CLASSNAME + "login effettuato con google");
            startActivity(intent);
            finish();
        }else Log.e(TAG,CLASSNAME + "login non effettuato " + result.describeContents());
    });

    public void googleLoginManagerInit(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(LoginActivity.this,googleSignInOptions);
        intentGoogle = googleSignInClient.getSignInIntent();
    }

}