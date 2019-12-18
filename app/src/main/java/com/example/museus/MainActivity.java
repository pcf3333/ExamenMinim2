package com.example.museus;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private View mProgressView;
    private Button accesButton;
    private TextView welcomeText;
    private TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressView = findViewById(R.id.login_progress);
        accesButton = findViewById(R.id.welcomeButton);
        welcomeText=findViewById(R.id.welcomeText);
        loadingText=findViewById(R.id.loadingText);
    }

    public void goToMuseums(View view){
        showProgress(true);
        newIntent();
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
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

            welcomeText.setVisibility(show ? View.GONE : View.VISIBLE);
            welcomeText.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    welcomeText.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            accesButton.setVisibility(show ? View.GONE : View.VISIBLE);
            welcomeText.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    //Open the new activity where we show the api response
    private void newIntent(){
        Intent intent = new Intent(this, MuseumsActivity.class);
        startActivity(intent);
    }
}
