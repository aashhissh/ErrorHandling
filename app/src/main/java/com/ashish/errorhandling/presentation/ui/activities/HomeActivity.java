package com.ashish.errorhandling.presentation.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ashish.errorhandling.R;
import com.ashish.errorhandling.commons.ProgressDialog;
import com.ashish.errorhandling.domain.executor.impl.ThreadExecutor;
import com.ashish.errorhandling.presentation.models.WelcomeModel;
import com.ashish.errorhandling.presentation.presenters.HomePresenter;
import com.ashish.errorhandling.presentation.presenters.impl.HomePresenterImpl;
import com.ashish.errorhandling.storage.UserRepositoryImpl;
import com.ashish.errorhandling.threading.MainThreadImpl;
import com.ashish.errorhandling.utils.NavigationUtil;

public class HomeActivity extends AppCompatActivity implements HomePresenter.View, View.OnClickListener {

    private TextView tvWelcomeText;
    private Button btViewPersonalDetails;

    private HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initComponent();
        init();
    }

    private void initComponent() {
        // init welcome message
        tvWelcomeText = findViewById(R.id.tv_welcome_text);

        //init view personal details button
        btViewPersonalDetails = findViewById(R.id.bt_view_personal_details);
        btViewPersonalDetails.setOnClickListener(this);
    }

    private void init() {
        homePresenter = new HomePresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new UserRepositoryImpl()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        homePresenter.resume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_view_personal_details:
                NavigationUtil.startPersonalDetailsActivity(this);
                finish();
                break;
        }
    }

    @Override
    public void updateWelcomeMessage(WelcomeModel welcomeModel) {
        tvWelcomeText.setText(
                getString(
                        R.string.home_message,
                        welcomeModel.getUserName(),
                        welcomeModel.getLastLoginTime(),
                        welcomeModel.getDevice()
                )
        );
    }

    @Override
    public void onTokenRevoked() {
        NavigationUtil.startLoginActivity(this);
        finish();
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

    }
}
