package com.ashish.errorhandling.presentation.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ashish.errorhandling.R;
import com.ashish.errorhandling.commons.ProgressDialog;
import com.ashish.errorhandling.domain.executor.impl.ThreadExecutor;
import com.ashish.errorhandling.presentation.models.PersonalDetailsModel;
import com.ashish.errorhandling.presentation.presenters.PersonalDetailsPresenter;
import com.ashish.errorhandling.presentation.presenters.impl.PersonalDetailsPresenterImpl;
import com.ashish.errorhandling.storage.UserRepositoryImpl;
import com.ashish.errorhandling.threading.MainThreadImpl;
import com.ashish.errorhandling.utils.CodeUtil;
import com.ashish.errorhandling.utils.NavigationUtil;

public class PersonalDetailsActivity extends AppCompatActivity implements PersonalDetailsPresenter.View {

    private TextView tvAge, tvGender, tvAddress, tvBloodGroup, tvHeight, tvWeight;

    private PersonalDetailsPresenter personalDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        initComponent();
        init();
    }

    private void initComponent() {
        tvAge = findViewById(R.id.tv_age);
        tvGender = findViewById(R.id.tv_gender);
        tvAddress = findViewById(R.id.tv_address);
        tvBloodGroup = findViewById(R.id.tv_bloodgroup);
        tvHeight = findViewById(R.id.tv_height);
        tvWeight = findViewById(R.id.tv_weight);
    }

    private void init() {
        personalDetailsPresenter = new PersonalDetailsPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new UserRepositoryImpl()
        );
    }

    @Override
    public void updatePersonalInformation(PersonalDetailsModel personalDetailsModel) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(personalDetailsModel.getUserName());
        }
        tvAge.setText(getString(R.string.age_text, personalDetailsModel.getAge()));
        tvGender.setText(getString(R.string.gender_text, personalDetailsModel.getGender()));
        tvAddress.setText(getString(R.string.address_text, personalDetailsModel.getAddress()));
        tvBloodGroup.setText(getString(R.string.bloodgroup_text, personalDetailsModel.getBloodGroup()));
        tvHeight.setText(getString(R.string.height_text, personalDetailsModel.getHeight()));
        tvWeight.setText(getString(R.string.weight_text, personalDetailsModel.getWeight()));
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
        CodeUtil.showToast(this, message);
    }
}
