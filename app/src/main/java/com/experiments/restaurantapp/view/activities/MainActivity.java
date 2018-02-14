package com.experiments.restaurantapp.view.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.experiments.restaurantapp.R;
import com.experiments.restaurantapp.model.PlaceInfo;
import com.experiments.restaurantapp.util.Constants;
import com.experiments.restaurantapp.view.fragment.ContactUsFragment;
import com.experiments.restaurantapp.view.fragment.FavouritesFragment;
import com.experiments.restaurantapp.view.fragment.HomeFragment;
import com.experiments.restaurantapp.view.fragment.LocationSettingsFragment;
import com.experiments.restaurantapp.view.fragment.SavedSearchFragment;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.thefinestartist.finestwebview.FinestWebView;

import butterknife.BindView;

public class MainActivity extends PlaceDetectorActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {

    private static final int PLACE_PICKER_REQUEST = 5656;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private HomeFragment homeFragment;

    @Override
    protected int provideLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        setSupportActionBar(toolbar);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        navView.getMenu().findItem(R.id.nav_home).setChecked(true);
        manageNavigation(R.id.nav_home);
        //startService(new Intent(this,Maverick.class));
    }

    @Override
    protected void dispose() {
        super.dispose();
        getSupportFragmentManager().removeOnBackStackChangedListener(this);
        navView.setNavigationItemSelectedListener(null);
    }

    @Override
    protected void onPlaceUpdated() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onFirstTimeLocationGot(PlaceInfo currentPlace) {
        if (homeFragment != null && homeFragment.isVisible()) {
            homeFragment.onGotLocationFirstTime(currentPlace);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        manageNavigation(id);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void manageNavigation(int id) {
        switch (id) {
            case R.id.nav_home:
                navigateToHome();
                break;
            case R.id.nav_location_settings:
                navigateToLocationSettings();
                break;
            case R.id.nav_terms:
                navigateToTerms();
                break;
            case R.id.nav_website:
                navigateToWebsite();
                break;
            case R.id.nav_favourite:
                navigateToFavourite();
                break;
            case R.id.nav_history:
                navigateToHistory();
                break;
            case R.id.nav_contact:
                navigateToContact();
                break;
            case R.id.nav_faq:
                navigateToFaq();
                break;
            case R.id.nav_copyrights:
                navigateToCopyrights();
                break;
        }
    }



    private void navigateToHome() {
        if (homeFragment == null || !homeFragment.isAdded()) {
            homeFragment = HomeFragment.newInstance(null);
            addReplaceFragment(R.id.container_home, homeFragment, true, false);
        }
        else{
            addReplaceFragment(R.id.container_home, homeFragment, false, true);
        }
    }

    private void navigateToLocationSettings() {
        addReplaceFragment(R.id.container_home, LocationSettingsFragment.newInstance(), true, true);
    }

    private void navigateToTerms() {
        openWebView(Constants.Urls.TERMS_URL);
    }

    private void navigateToWebsite() {
        openWebView(Constants.Urls.TAKKI_WEBSITE);
    }


    private void navigateToContact() {
        addReplaceFragment(R.id.container_home, ContactUsFragment.newInstance(), true, true);
    }

    private void navigateToFaq() {
        openWebView(Constants.Urls.FAQ_URL);
    }
    private void navigateToCopyrights() {
        openWebView(Constants.Urls.COPYRIGHT_URL);
    }
    public void openWebView(String url) {
        new FinestWebView.Builder(this)
                .toolbarColor(ContextCompat.getColor(this, R.color.primary))
                .statusBarColor(ContextCompat.getColor(this, R.color.primary_dark))
                .titleColor(ContextCompat.getColor(this, R.color.md_white_1000))
                .urlColor(ContextCompat.getColor(this, R.color.md_white_1000))
                .progressBarColor(ContextCompat.getColor(this, R.color.primary))
                .menuColor(ContextCompat.getColor(this, R.color.md_white_1000))
                .iconDefaultColor(ContextCompat.getColor(this, R.color.md_white_1000))
                .show(url);
    }

    private void navigateToFavourite() {
        addReplaceFragment(R.id.container_home, FavouritesFragment.newInstance(), true, true);
    }

    private void navigateToHistory() {
        addReplaceFragment(R.id.container_home, SavedSearchFragment.newInstance(), true, true);
    }

    @Override
    public void onBackStackChanged() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_home);
        if (fragment instanceof HomeFragment) {
            getSupportActionBar().setTitle("Home");
            navView.getMenu().findItem(R.id.nav_home).setChecked(true);
        } else if (fragment instanceof LocationSettingsFragment) {
            getSupportActionBar().setTitle("Location Settings");
            navView.getMenu().findItem(R.id.nav_location_settings).setChecked(true);
        } else if (fragment instanceof SavedSearchFragment) {
            getSupportActionBar().setTitle("Search History");
            navView.getMenu().findItem(R.id.nav_history).setChecked(true);
        }
        else if (fragment instanceof ContactUsFragment) {
            getSupportActionBar().setTitle("Contact Us");
            navView.getMenu().findItem(R.id.nav_contact).setChecked(true);
        }
        else if (fragment instanceof FavouritesFragment) {
            getSupportActionBar().setTitle("Favourites");
            navView.getMenu().findItem(R.id.nav_favourite).setChecked(true);
        }
    }

    @Override
    protected void onUserDenied() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PLACE_PICKER_REQUEST:
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(this, data);
                    android.location.Location location = new android.location.Location("");
                    location.setLatitude(place.getLatLng().latitude);
                    location.setLatitude(place.getLatLng().longitude);
                    currentPlace.setLocation(location);
                    onFirstTimeLocationGot(currentPlace);
                }
                break;
        }
    }

}
