package com.experiments.restaurantapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.experiments.restaurantapp.R;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by Experiments on 12-Mar-17.
 */

public class IntroActivity extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFlowAnimation();
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("Lorem ipsum", "Lorem ipsum dolor sit amet, pro summo tollit noluisse ei, in pro ignota eripuit praesent, iudico feugiat detracto duo an. Solet alienum mei ne, mucius tincidunt et sit, in zril.", R.drawable.intro_ic_1, ContextCompat.getColor(this, R.color.md_blue_grey_700)));
        addSlide(AppIntroFragment.newInstance("Lorem ipsum", "Lorem ipsum dolor sit amet, augue fuisset vel ei, no nam nisl nobis viderer, has ei case dicit atomorum. Detraxit partiendo rationibus sed id. Quaeque placerat tractatos vim ut nisl.", R.drawable.intro_ic_2, ContextCompat.getColor(this, R.color.md_blue_700)));
        addSlide(AppIntroFragment.newInstance("Lorem ipsum", "Lorem ipsum dolor sit amet, id sit vocent expetenda repudiandae. No tale illud sed, solet populo id vel, vel at erant solet sensibus. Duo noster minimum omittantur in, saperet propriae.", R.drawable.intro_ic_3, ContextCompat.getColor(this, R.color.md_teal_700)));
        addSlide(AppIntroFragment.newInstance("Lorem ipsum", "Lorem ipsum dolor sit amet, persius delectus per eu, euismod tibique vis eu. Ut sea elitr utamur. Ne mandamus percipitur pro, usu ea lorem pertinax suscipiantur. In quo scripserit temporibus.", R.drawable.intro_ic_4, ContextCompat.getColor(this, R.color.md_brown_700)));
        // Hide Skip/Done button.
        showSkipButton(false);
    }


    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        navigateToHome();
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(intent);
    }

}
