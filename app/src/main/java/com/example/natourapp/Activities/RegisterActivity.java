package com.example.natourapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.InvalidParameterException;
import com.amazonaws.services.cognitoidentityprovider.model.InvalidPasswordException;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;
import com.amazonaws.services.cognitoidentityprovider.model.UsernameExistsException;

import com.example.natourapp.R;
import com.example.natourapp.auth_controller.Cognito;
import com.example.natourapp.entities.Utente;
import com.example.natourapp.services.http.Richiesta;
import com.example.natourapp.services.http.richiestaUtente.RichiesteUtente;
import com.microsoft.windowsazure.messaging.BuildConfig;
import com.microsoft.windowsazure.messaging.notificationhubs.NotificationHub;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "RegisterActivity:";
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private DatePicker birthdate;
    private RegisterHandler registerHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Cognito cognito = new Cognito(this);
        findEditText();

        registerHandler = new RegisterHandler();
        registerHandler.setErrorEmail(email);

        findViewById(R.id.RegistratiButton).setOnClickListener(view -> {
            Log.d(TAG,CLASSNAME + "bottone registrati cliccato");
            setCustomFields();
            if(registerHandler.check(password,confirmPassword,birthdate)) {
                cognito.addAttribute("email", email.getText().toString());
                cognito.addAttribute("birthdate", registerHandler.convertDate(birthdate));
                cognito.signUpInBackground(username.getText().toString(), password.getText().toString(), registerHandler);
            }
        });
    }

    public void findEditText(){
        email = findViewById(R.id.EmailEditText);
        username = findViewById(R.id.UsernameRegisterEditText);
        password = findViewById(R.id.PasswordRegisterEditText);
        confirmPassword = findViewById(R.id.ConfermaPasswordEditText);
        birthdate = findViewById(R.id.datePicker);
    }

    public void setCustomFields(){
        password.setBackgroundResource(R.drawable.text_field_custom);
        confirmPassword.setBackgroundResource(R.drawable.text_field_custom);
        username.setBackgroundResource(R.drawable.text_field_custom);
    }

    private class RegisterHandler implements SignUpHandler{
        private boolean emailState;

        @Override
        public void onSuccess(CognitoUser user, SignUpResult signUpResult) {
            NotificationHub.start(getApplication(), "HUBNAME","CONNECTION_STRING_NOTIFICATION_HUB");
            String notificationTag = username.getText().toString();
            NotificationHub.addTag(notificationTag);
            Richiesta<Utente> richiesteUtente = new RichiesteUtente();
            Future<String> futureRespUtente = Executors.newSingleThreadExecutor().submit(()->richiesteUtente.add(new Utente(username.getText().toString())));
            String response = "";
            try {
                response = futureRespUtente.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG,CLASSNAME +"risposta:"+ response);
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.putExtra("confirmation_state",signUpResult.getUserConfirmed());
            intent.putExtra("response_register",response);
            startActivity(intent);
        }

        @Override
        public void onFailure(Exception exception) {
            if (exception instanceof UsernameExistsException) {
                Toast.makeText(RegisterActivity.this, "nome utente già esistente!", Toast.LENGTH_SHORT).show();
                Log.e(TAG,CLASSNAME + exception.getMessage());
            }
            if(exception instanceof InvalidPasswordException){
                password.setBackgroundResource(R.drawable.edit_text_error_state);
                password.setError("La password richiede una lettera maiuscola, una minuscola e un numero");
                Log.e(TAG,CLASSNAME + exception.getMessage());
                Toast.makeText(RegisterActivity.this, "Pasword sbagliata", Toast.LENGTH_SHORT).show();
            }

            if(exception instanceof InvalidParameterException){
                username.setBackgroundResource(R.drawable.edit_text_error_state);
                Log.e(TAG,CLASSNAME + exception.getMessage());
                Toast.makeText(RegisterActivity.this, "Username non valido", Toast.LENGTH_SHORT).show();
            }
            Log.e(TAG,CLASSNAME + exception.getMessage());
        }

        private boolean checkBirthdateConstraint(int year, int month, int day) {
            LocalDate today = LocalDate.now();
            LocalDate birthday = LocalDate.of(year,month + 1,day);
            Log.d("local date",birthday.toString());
            Period period = Period.between(birthday,today);
            return  period.getYears() >= 14;
        }

        private boolean checkBirthdate(int year,int month,int day){
            Log.d(TAG,CLASSNAME + "Controllo età utente");
            if(!checkBirthdateConstraint(year,month,day)){
                Toast.makeText(RegisterActivity.this, "Per utilizzare l'app devi avere 14 anni", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }

        private String convertDate(DatePicker datePicker) {
            LocalDate birthday = LocalDate.of(datePicker.getYear(),datePicker.getMonth() + 1,datePicker.getDayOfMonth());
            return birthday.toString();
        }

        private boolean checkConfirmPassword(EditText passwordField, EditText confirmPasswordField) {
            if(!passwordField.getText().toString().equals(confirmPasswordField.getText().toString())){
                password.setBackgroundResource(R.drawable.edit_text_error_state);
                confirmPassword.setBackgroundResource(R.drawable.edit_text_error_state);
                confirmPassword.setError("La password non corrisponde");
                return false;
            }
            return true;
        }

        private boolean validateEmail(CharSequence email) {
            return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        }

        private void setErrorEmail(EditText email) {
            email.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(!validateEmail(email.getText())) {
                        email.setBackgroundResource(R.drawable.edit_text_error_state);
                        email.setError("Email non valida");
                        emailState=false;
                    } else {
                        email.setBackgroundResource(R.drawable.text_field_custom);
                        emailState=true;
                    }
                }
            });
        }

        private boolean check(EditText password,EditText confirmPassword, DatePicker birthdate){
            return checkConfirmPassword(password,confirmPassword)
                    & checkBirthdate(birthdate.getYear(),birthdate.getMonth(),birthdate.getDayOfMonth())
                    & emailState;
        }
    }


}