package com.example.learning.controller.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.learning.R;
//import com.example.learning.controller.R;
//import com.example.learning.controller.databinding.ActivityLoginBinding;
import com.example.learning.controller.MainActivity;
import com.example.learning.databinding.ActivityLoginBinding;
import com.example.learning.fragment.ScoreFragment;
import com.example.learning.model.User;
import com.example.learning.utils.RetrofitInterface;
import com.example.learning.utils.Serializer;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private String BASE_URL = "http://192.168.88.16:1010";
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button buttonSave;
    private final String filename = "log";
    private boolean saveScore = false;
    private int idTheme;
    private int score;
    private int idUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            saveScore = true;
            idTheme = extras.getInt("idtheme");
            score = extras.getInt("score");
        }
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        usernameEditText = binding.username;
        passwordEditText = binding.password;
        loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        buttonSave = this.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.VERBOSE, "SAVESCORE", "====SAVED");
                //saveScore();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.VERBOSE, "LOG", "====I'M LOGGED");
                login();
                if(saveScore){
                    Log.println(Log.VERBOSE, "LOG", "====I'M LOGGED and SCORE IS SAVED");
                    //ScoreFragment.saveScore(score, idTheme, idUser, LoginActivity.this.retrofitInterface);
                }
                //loadingProgressBar.setVisibility(View.VISIBLE);
                /*loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());*/
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void login(){
        String email = usernameEditText.getText().toString();
        String mdp = passwordEditText.getText().toString();
        Log.println(Log.VERBOSE, "LOG", "=====EMAIL "+ email +"==PASSWORD "+mdp);
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("mdp", mdp);

        Call<User> call = retrofitInterface.login(map);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200){
                    User userlog = response.body();
                    idUser = new Integer(userlog.get_id());
                    Log.println(Log.VERBOSE, "LOG", "=====USER LOGGED "+userlog.getEmail()+"/"+userlog.getMdp());
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Connecté", Toast.LENGTH_LONG).show();
                    Serializer.serialize(filename, userlog, LoginActivity.this);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else if(response.code() == 201){
                    Log.println(Log.VERBOSE, "LOG", "=====LOG FAILED");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }




}