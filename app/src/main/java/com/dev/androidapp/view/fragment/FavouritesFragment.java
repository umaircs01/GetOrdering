package com.dev.androidapp.view.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.dev.androidapp.R;
import com.dev.androidapp.api.RestClient;
import com.dev.androidapp.model.GsonDeserializeExclusion;
import com.dev.androidapp.model.pojo.Favourite;
import com.dev.androidapp.model.pojo.Location;
import com.dev.androidapp.model.pojo.RestaurantData;
import com.dev.androidapp.model.pojo.ViewCountRequest;
import com.dev.androidapp.model.pojo.ViewCountResponse;
import com.dev.androidapp.model.pojo.WorkInfo;
import com.dev.androidapp.view.activities.MainActivity;
import com.dev.androidapp.view.activities.MapsActivity;
import com.dev.androidapp.view.adapters.RestaurantsAdapter;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.androidapp.RestaurantApp.realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends BaseFragment implements RestaurantsAdapter.RestaurantsListener{

    RecyclerView favRestaurants;
    RestaurantsAdapter adapter;
    List<RestaurantData> restaurantDataList = new ArrayList<>();

    public FavouritesFragment() {
        // Required empty public constructor
    }

    public static FavouritesFragment newInstance() {
        FavouritesFragment fragment = new FavouritesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favourites, container, false);
        initViews(v);
        populateRestaurants();
        return v;
    }

    public void initViews(View view){
        favRestaurants = (RecyclerView) view.findViewById(R.id.fav_restaurants);
        favRestaurants.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapter = new RestaurantsAdapter(getContext(), restaurantDataList, this);
        favRestaurants.setAdapter(adapter);
    }

    public void populateRestaurants(){
        List<Favourite> restaurants = realm.allObjects(Favourite.class);
        Gson gson = new GsonBuilder()
                .addDeserializationExclusionStrategy(new GsonDeserializeExclusion())
                .create();
        for(Favourite fav : restaurants){
            RestaurantData data = gson.fromJson(fav.getFavObject(),RestaurantData.class);
            restaurantDataList.add(data);
        }
    }

    @Override
    protected void init() {

    }

    @Override
    protected int provideLayout() {
        return 0;
    }

    @Override
    protected void dispose() {

    }

    @Override
    public void onWebsiteClick(String url) {
        ((MainActivity) getActivity()).openWebView(url);
    }

    @Override
    public void onPhoneClick(String phone) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationClick(Location latLng) {
        Intent intent = MapsActivity.newIntent(getActivity(), latLng);
        startActivity(intent);
    }

    @Override
    public void onAddressClick(final Location location) {
        new MaterialDialog.Builder(getContext())
                .title("Restaurant Address")
                .items(location.getAddress())
                .positiveText("Show On Maps")
                .negativeText("Close")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = MapsActivity.newIntent(getActivity(), location);
                        startActivity(intent);
                    }
                })
                .autoDismiss(true)
                .show();
    }

    @Override
    public void onOpenCloseClick(WorkInfo workInfo) {
        List<String> items = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        for (int i = 0; i < 7; i++) {
            items.add(simpleDateFormat.format(new Date(calendar.getTimeInMillis())) + " : " + workInfo.getWorkingHours());
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }

        new MaterialDialog.Builder(getContext())
                .title("Operating Hours")
                .items(items)
                .autoDismiss(true)
                .positiveText("Close")
                .show();
    }

    @Override
    public void onImageClick(String imageUrl) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_photo_view, null, false);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.ivPhotoView);
        Glide.with(getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(photoView);
        new MaterialDialog.Builder(getContext())
                .title("Image")
                .autoDismiss(true)
                .customView(view, true)
                .positiveText("Close")
                .show();
    }

    @Override
    public void onTypeClick(List<String> businessType) {
        new MaterialDialog.Builder(getContext())
                .title("Options")
                .items(businessType)
                .autoDismiss(true)
                .positiveText("Close")
                .show();
    }

    @Override
    public void onCuisineClick(String foodType) {
        new MaterialDialog.Builder(getContext())
                .title("Cuisine")
                .items(foodType)
                .positiveText("Close")
                .show();
    }

    @Override
    public void onNameClick(final RestaurantData model) {
        new MaterialDialog.Builder(getContext())
                .title("Restaurant Name")
                .items(model.getTradingName().toUpperCase())
                .autoDismiss(true)
                .positiveText("View Menu")
                .negativeText("Close")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        final MainActivity activity = (MainActivity) getActivity();
                        Call<ViewCountResponse> call = RestClient.getInstance().getWebServices().increaseViewCount(
                                new ViewCountRequest(model.getRestaurantId())
                        );
                        call.enqueue(new Callback<ViewCountResponse>() {
                            @Override
                            public void onResponse(Call<ViewCountResponse> call, Response<ViewCountResponse> response) {
                                if (isDetached()) {
                                    return;
                                }
                                activity.dismissProgress();
                                if (!response.isSuccessful()) {
                                    activity.showMessage(getString(R.string.unknown_error));
                                    return;
                                }

                                ViewCountResponse viewCountResponse = response.body();
                                if (viewCountResponse.getSuccess() == 1) {
                                    model.setViews(model.getViews() + 1);
                                    adapter.notifyItemChanged(adapter.getModels().indexOf(model));
                                    activity.openWebView(model.getMenuUrl());
                                } else {
                                    activity.showMessage(viewCountResponse.getErrorMessage());
                                }

                            }

                            @Override
                            public void onFailure(Call<ViewCountResponse> call, Throwable t) {
                                if (isDetached()) {
                                    return;
                                }
                                activity.dismissProgress();
                                activity.showMessage(getString(R.string.unknown_error));
                                t.printStackTrace();
                            }
                        });
                    }
                })
                .show();
    }

}
