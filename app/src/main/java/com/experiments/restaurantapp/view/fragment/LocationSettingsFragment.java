package com.experiments.restaurantapp.view.fragment;

import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.experiments.restaurantapp.R;
import com.experiments.restaurantapp.helper.Config;
import com.experiments.restaurantapp.helper.PrefEntity;
import com.experiments.restaurantapp.helper.PrefManager;
import com.experiments.restaurantapp.util.Constants;

import butterknife.BindView;

/**
 * Created by Experiments on 29-Mar-17.
 */

public class LocationSettingsFragment extends BaseFragment {
    @BindView(R.id.switchLocationUpdates)
    SwitchCompat switchLocationUpdates;
    @BindView(R.id.rbHighAccuracy)
    AppCompatRadioButton rbHighAccuracy;
    @BindView(R.id.rbMidAccuracy)
    AppCompatRadioButton rbMidAccuracy;
    @BindView(R.id.rbLowAccuracy)
    AppCompatRadioButton rbLowAccuracy;
    @BindView(R.id.rgAccuracy)
    RadioGroup rgAccuracy;
    @BindView(R.id.rbOnOffLocation)
    LinearLayout rbOnOffLocation;

    public static LocationSettingsFragment newInstance() {
        LocationSettingsFragment fragment = new LocationSettingsFragment();
        return fragment;
    }

    @Override
    protected void init() {
        final int prefAccuracy = PrefManager.getPrefInteger(getContext(), Config.Preferences.LOCATION_ACCURACY);
        switch (prefAccuracy) {
            case Constants.LocationConfig.BALANCED_ACCURACY:
                rbMidAccuracy.setChecked(true);
                break;
            case Constants.LocationConfig.LOW_ACCURACY:
                rbLowAccuracy.setChecked(true);
                break;
            default:
                rbHighAccuracy.setChecked(true);
                break;
        }

        rgAccuracy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int accuracy = prefAccuracy;
                switch (checkedId) {
                    case R.id.rbHighAccuracy:
                        accuracy = Constants.LocationConfig.HIGH_ACCURACY;
                        break;
                    case R.id.rbMidAccuracy:
                        accuracy = Constants.LocationConfig.BALANCED_ACCURACY;
                        break;
                    case R.id.rbLowAccuracy:
                        accuracy = Constants.LocationConfig.LOW_ACCURACY;
                        break;
                }
                PrefManager.setPrefInteger(getContext(), Config.Preferences.LOCATION_ACCURACY, accuracy);
            }
        });


        boolean locationOn = !(PrefManager.getPrefBoolean(getContext(), PrefEntity.IS_LOCATION_UPDATES_OFF));
        switchLocationUpdates.setChecked(locationOn);

        switchLocationUpdates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrefManager.setPrefBoolean(getContext(), PrefEntity.IS_LOCATION_UPDATES_OFF, !isChecked);
            }
        });
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_location_settings;
    }

    @Override
    protected void dispose() {

    }

}
