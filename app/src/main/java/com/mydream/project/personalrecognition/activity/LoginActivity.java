package com.mydream.project.personalrecognition.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mydream.project.personalrecognition.R;


/**
 * 用户登录界面
 */
public class LoginActivity extends BaseActivity{
    // UI references.
    private TextInputLayout usernameTextInput, passwordTextInput;
    private AutoCompleteTextView usernameACTextView;
    private EditText passwordEditText;
    private View mProgressView;
    private Button loginButton;
    private ProgressBar loadingProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initView();
        initEvent();
    }

    @Override
    protected void initData() {
        super.initData();
        mContext = LoginActivity.this;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        loginButton.setOnClickListener(this);
    }

    private void initView(){
        usernameTextInput = (TextInputLayout)findViewById(R.id.til_loginActivity_username);
        passwordTextInput = (TextInputLayout)findViewById(R.id.til_loginActivity_password);
        usernameACTextView = (AutoCompleteTextView)findViewById(R.id.atv_loginActivity_username);
        passwordEditText = (EditText)findViewById(R.id.et_loginActivity_password);
        loginButton = (Button)findViewById(R.id.bt_loginActivity_login);


        usernameTextInput.setHint(getString(R.string.login_username));
        passwordTextInput.setHint(getString(R.string.login_password));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.bt_loginActivity_login:
                //登录
                hideKeyboard();
                String username = usernameACTextView.getText().toString().trim();
                if(!(null != username && !username.equals(""))){
                    usernameTextInput.setError(getString(R.string.login_username_empty));
                    break;
                }else if(usernameTextInput.isErrorEnabled()){
                    usernameTextInput.setError("");
                }
                String password = passwordEditText.getText().toString().trim();
                if(!(null != password && !password.equals(""))){
                    passwordTextInput.setError(getString(R.string.login_password_empty));
                    break;
                }else if(passwordTextInput.isErrorEnabled()){
                    passwordTextInput.setError("");
                }
                Intent loginIntent = new Intent(mContext, HomeActivity.class);
                startActivity(loginIntent);
                finish();
                break;

        }
    }

    /**
     * 隐藏键盘
     */
    private void hideKeyboard(){
        View view = getCurrentFocus();
        if(null != view){
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

    }






    /**
     * Shows the progress UI and hides the login form.
     */
    /*@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loadingProgressBar.setVisibility(show ? View.GONE : View.VISIBLE);
            loadingProgressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingProgressBar.setVisibility(show ? View.GONE : View.VISIBLE);
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
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }*/







    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    /*public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }*/
}

