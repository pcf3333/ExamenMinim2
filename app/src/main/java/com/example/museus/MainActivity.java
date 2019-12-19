package com.example.museus;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private View mProgressView;
    private Button accesButton;
    private TextView loadingText;
    private TextView usernameText;
    private TextView passwordText;

    private UserLoginTask tascaLogIn = null;

    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressView = findViewById(R.id.login_progress);
        accesButton = findViewById(R.id.welcomeButton);
        usernameText=findViewById(R.id.usernameText);
        passwordText=findViewById(R.id.passwordText);
        loadingText=findViewById(R.id.loadingText);

        //Entrem el susuari
//        sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("username", "user");
//        editor.putString("password","dsamola");
//        editor.putBoolean("registered",false);
//        editor.apply();

        showAlreadyLogged();

    }

    public void goToMuseums(View view){
        tryLogin();
    }

    //Si ja esta registrat entra directe
    private void showAlreadyLogged(){
        sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        String user = sharedPref.getString("username", "");
        String pssw = sharedPref.getString("password", "");
        Boolean registered = sharedPref.getBoolean("resgistered",false);

        if (user.equals("user") && pssw.equals("dsamola") && registered) {
            newIntent();
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgressBar(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            accesButton.setVisibility(show ? View.GONE : View.VISIBLE);
            accesButton.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    accesButton.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            passwordText.setVisibility(show ? View.GONE : View.VISIBLE);
            passwordText.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    passwordText.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            usernameText.setVisibility(show ? View.GONE : View.VISIBLE);
            usernameText.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    usernameText.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });


            loadingText.setVisibility(show ? View.VISIBLE : View.GONE);
            loadingText.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingText.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            allVisible();
        }
    }

    private void allVisible(){
        mProgressView.setVisibility(true ? View.VISIBLE : View.GONE);
        accesButton.setVisibility(true ? View.GONE : View.VISIBLE);
        passwordText.setVisibility(true ? View.GONE : View.VISIBLE);
        usernameText.setVisibility(true ? View.GONE : View.VISIBLE);
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String LogUser;
        private final String LogPassword;

        UserLoginTask(String username, String password) {
            LogUser = username;
            LogPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // 2 segons de parada perque es vegi la barra de loading
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            return true;


        }

        @Override
        protected void onPostExecute(final Boolean success) {
            tascaLogIn = null;
            showProgressBar(false);

            if (success) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("registered", true);
                editor.putString("username", this.LogUser);
                editor.putString("password", this.LogPassword);
                editor.apply();

                newIntent();
            } else {
                passwordText.setError("Només hi ha 1 usuari amb accés a la aplicació (user,dsamola)");
                passwordText.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            tascaLogIn = null;
            showProgressBar(false);
        }
    }


    private void tryLogin() {
        if (tascaLogIn != null) {
            return;
        }

        usernameText.setError(null);
        passwordText.setError(null);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        boolean stop = false;
        View focusView = null;

        //Si la contra i el usuari es incorrecte no seguim endavant
        if (TextUtils.isEmpty(username)) {
            usernameText.setError("El nom d'usuari no pot estar buit!");
            focusView = usernameText;
            stop = true;
        }

        if (!isPasswordValid(password)) {
            passwordText.setError("La contrasenya es massa curta (longitud mínima 5)");
            focusView = passwordText;
            stop = true;
        }



        if (stop) {
            focusView.requestFocus();
        } else {
            //ensenyem la progress bar i fem log in
            showProgressBar(true);
            tascaLogIn = new UserLoginTask(username, password);
            tascaLogIn.execute((Void) null);
        }
    }

    //Obrir la nova activity amb tots els museus
    private void newIntent(){
        Intent intent = new Intent(this, MuseumsActivity.class);
        startActivity(intent);
    }


    private boolean isPasswordValid(String password) {
        if (password.length()>=5){
            return true;
        }
        else{
            return false;
        }
    }
}
