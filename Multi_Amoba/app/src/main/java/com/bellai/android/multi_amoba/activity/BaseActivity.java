package com.bellai.android.multi_amoba.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by adam.bellai on 2016. 12. 21..
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ProgressBar progressBar;

    public abstract void initialize();

    public void initProgressbar() {
        progressBar = new ProgressBar(this);
        progressBar.setVisibility(View.GONE);
    }

    protected void goToNewActivity(Class<?> klass) {
        Intent intent = new Intent(this, klass);
        startActivity(intent);
    }

    protected void goToNewActivity(Class<?> klass, int extraData) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(this, klass);
        intent.putExtra(MainActivity.MODE, extraData);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }
}
