package com.experiments.restaurantapp.view.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.experiments.base.Maverick;
import com.experiments.restaurantapp.R;
import com.experiments.restaurantapp.util.Constants;
import com.experiments.restaurantapp.util.Utils;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.tvVersion)
    TextView appVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FullScreencall();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        appVersion.setText(getString(R.string.splash_app_version, Utils.getAppVersionName()));
        getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                proceedToTheNextActivity();
            }
        }, Constants.Delays.SPLASH_DELAY);
    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void dispose() {

    }

    private void proceedToTheNextActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(intent);
    }

}
