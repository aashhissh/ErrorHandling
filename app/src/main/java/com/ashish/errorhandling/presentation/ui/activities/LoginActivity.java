package com.ashish.errorhandling.presentation.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ashish.errorhandling.R;
import com.ashish.errorhandling.commons.ProgressDialog;
import com.ashish.errorhandling.domain.executor.impl.ThreadExecutor;
import com.ashish.errorhandling.presentation.presenters.LoginPresenter;
import com.ashish.errorhandling.presentation.presenters.impl.LoginPresenterImpl;
import com.ashish.errorhandling.storage.UserRepositoryImpl;
import com.ashish.errorhandling.threading.MainThreadImpl;
import com.ashish.errorhandling.utils.CodeUtil;
import com.ashish.errorhandling.utils.NavigationUtil;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.View, View.OnClickListener{

    private EditText etUserName, etPassword;
    private Button btLogin;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponent();
        init();
    }

    private void initComponent() {
        // init entry fields
        etUserName = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        //init login button
        btLogin = findViewById(R.id.bt_login);
        btLogin.setOnClickListener(this);
    }

    private void init() {
        loginPresenter = new LoginPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new UserRepositoryImpl()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                if(CodeUtil.isTextEmpty(etUserName.getText().toString())) {
                    showError(getString(R.string.please_enter_username));
                }

                if(CodeUtil.isTextEmpty(etPassword.getText().toString())) {
                    showError(getString(R.string.please_enter_password));
                }

                loginPresenter.onLoginClicked(
                        etUserName.getText().toString(),
                        etPassword.getText().toString()
                );
                break;
        }
    }

    @Override
    public void showProgress() {
        ProgressDialog.createDialog(this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        ProgressDialog.removeDialog(this);
    }

    @Override
    public void showError(String message) {
        CodeUtil.showToast(this, message);
    }

    @Override
    public void startHomeActivity() {
        NavigationUtil.startHomeActivity(this);
        finish();
    }
}
