package com.dev.androidapp.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.dev.androidapp.R;
import com.dev.androidapp.api.RestClient;
import com.dev.androidapp.api.ServerConfig;
import com.dev.androidapp.model.PlaceInfo;
import com.dev.androidapp.model.pojo.Address;
import com.dev.androidapp.model.pojo.BusinessType;
import com.dev.androidapp.model.pojo.Category;
import com.dev.androidapp.model.pojo.Distance;
import com.dev.androidapp.model.pojo.Favourite;
import com.dev.androidapp.model.pojo.GetRestaurantsRequest;
import com.dev.androidapp.model.pojo.GetRestaurantsResponse;
import com.dev.androidapp.model.pojo.Location;
import com.dev.androidapp.model.pojo.Query;
import com.dev.androidapp.model.pojo.RestaurantData;
import com.dev.androidapp.model.pojo.SearchRequest;
import com.dev.androidapp.model.pojo.ViewCountRequest;
import com.dev.androidapp.model.pojo.ViewCountResponse;
import com.dev.androidapp.model.pojo.WorkInfo;
import com.dev.androidapp.util.Constants;
import com.dev.androidapp.util.Utils;
import com.dev.androidapp.view.activities.BaseActivity;
import com.dev.androidapp.view.activities.MainActivity;
import com.dev.androidapp.view.activities.MapsActivity;
import com.dev.androidapp.view.activities.PlaceDetectorActivity;
import com.dev.androidapp.view.adapters.RestaurantsAdapter;
import com.github.chrisbanes.photoview.PhotoView;
import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.dev.androidapp.RestaurantApp.realm;

/**
 * Created by Experiments on 29-Mar-17.
 */
public class HomeFragment extends BaseFragment implements RestaurantsAdapter.RestaurantsListener {

    private static final String TAG = "HomeFragment";
    @BindView(R.id.rvRestaurnats)
    RecyclerView rvRestaurants;
    @BindView(R.id.tvFilterName)
    TextView tvFilterName;
    @BindView(R.id.llFilterByName)
    LinearLayout llFilterByName;
    @BindView(R.id.tvFilterDistance)
    TextView tvFilterDistance;
    @BindView(R.id.llFilterByDistance)
    LinearLayout llFilterByDistance;
    @BindView(R.id.tvFilterAddress)
    TextView tvFilterAddress;
    @BindView(R.id.llFilterByAddress)
    LinearLayout llFilterByAddress;
    @BindView(R.id.tvFilterCategory)
    TextView tvFilterCategory;
    @BindView(R.id.llFilterByCategory)
    LinearLayout llFilterByCategory;
    @BindView(R.id.tvFilterOption)
    TextView tvFilterOption;
    @BindView(R.id.llFilterByOption)
    LinearLayout llFilterByOption;
    @BindView(R.id.tvSearch)
    AppCompatTextView tvSearch;
    @BindView(R.id.tvSortBy)
    AppCompatTextView tvSortBy;
    @BindView(R.id.etName)
    AppCompatEditText etName;
    @BindView(R.id.rbLessThan5)
    AppCompatRadioButton rbLessThan5;
    @BindView(R.id.rbLessThan10)
    AppCompatRadioButton rbLessThan10;
    @BindView(R.id.rbLessThan25)
    AppCompatRadioButton rbLessThan25;
    @BindView(R.id.rbGreaterThan25)
    AppCompatRadioButton rbGreaterThan25;
    @BindView(R.id.rgDistance)
    RadioGroup rgDistance;
    @BindView(R.id.etState)
    AppCompatEditText etState;
    @BindView(R.id.etCategory)
    AppCompatEditText etCategory;
    @BindView(R.id.cbCoffeeshop)
    AppCompatCheckBox cbCoffeeShop;
    @BindView(R.id.cbCasualdining)
    AppCompatCheckBox cbCasualDining;
    @BindView(R.id.cbFineDining)
    AppCompatCheckBox cbFineDining;
    @BindView(R.id.cbTakeAway)
    AppCompatCheckBox cbTakeAway;
    @BindView(R.id.cbPubnBar)
    AppCompatCheckBox cbPubAndBar;
    @BindView(R.id.cbOpen)
    AppCompatCheckBox cbOpen;
    @BindView(R.id.cbLiveEntertainment)
    AppCompatCheckBox cbLiveEntertainment;
    @BindView(R.id.cbKidsFriendly)
    AppCompatCheckBox cbKidsFriendly;
    @BindView(R.id.cbPlayArea)
    AppCompatCheckBox cbPlayArea;
    @BindView(R.id.cbOutsideArea)
    AppCompatCheckBox cbOutsideArea;
    @BindView(R.id.cbCocktailbar)
    AppCompatCheckBox cbCocktailbar;
    @BindView(R.id.cbLounge)
    AppCompatCheckBox cbLounge;
    @BindView(R.id.cbLivesport)
    AppCompatCheckBox cbLivesport;
    @BindView(R.id.cbGambling)
    AppCompatCheckBox cbGambling;
    @BindView(R.id.cbPetfriendly)
    AppCompatCheckBox cbPetfriendly;
    @BindView(R.id.cbWifi)
    AppCompatCheckBox cbWifi;
    @BindView(R.id.tvNoResults)
    AppCompatTextView tvNoResult;
    @BindView(R.id.tvSaveSearch)
    LinearLayout tvSaveSearch;
    @BindView(R.id.tvSaveSearchText)
    AppCompatTextView tvSaveSearchText;
    @BindView(R.id.llSearchBox)
    LinearLayout llSearchBox;
    private RestaurantsAdapter adapter;

    private static final int SORT_BY_DISTANCE = 0;
    private static final int SORT_BY_NAME = 1;
    private static final int SORT_BY_STATUS = 2;
    private int currentSortBy = SORT_BY_DISTANCE;

    private int[] arrDistance = new int[2];
    private boolean wasSearchPerformed;
    private LinearLayout[] llFilters;
    @Nullable
    private SearchRequest mSearchRequest;

    public static HomeFragment newInstance(@Nullable SearchRequest model) {
        Bundle args = new Bundle();
        if (model != null) {
            args.putParcelable(Constants.BundleExtras.SAVED_SEARCH_REQUEST, model);
        }
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        llFilters = new LinearLayout[]{
                llFilterByName, llFilterByOption, llFilterByCategory, llFilterByAddress, llFilterByDistance
        };

        arrDistance[0] = 0;
        arrDistance[1] = 10;
        final List<RestaurantData> restaurants = new ArrayList<>();
        adapter = new RestaurantsAdapter(getContext(), restaurants, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRestaurants.setLayoutManager(layoutManager);
        rvRestaurants.setAdapter(adapter);
        rvRestaurants.setNestedScrollingEnabled(false);

        rgDistance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbLessThan5:
                        arrDistance[0] = 0;
                        arrDistance[1] = 10;
                        break;
                    case R.id.rbLessThan10:
                        arrDistance[0] = 10;
                        arrDistance[1] = 25;
                        break;
                    case R.id.rbLessThan25:
                        arrDistance[0] = 25;
                        arrDistance[1] = 50;
                        break;
                    case R.id.rbGreaterThan25:
                        arrDistance[0] = 50;
                        arrDistance[1] = 2000;
                        break;
                }
            }
        });
        rbLessThan5.setChecked(true);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                setVisibilityOfNoResults();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setVisibilityOfNoResults();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                setVisibilityOfNoResults();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(Constants.BundleExtras.SAVED_SEARCH_REQUEST)) {
            SearchRequest searchRequest = bundle.getParcelable(Constants.BundleExtras.SAVED_SEARCH_REQUEST);
            onFromSearchHistory(searchRequest);
        } else {
            // loadRestaurants();
        }
    }

    private void onFromSearchHistory(SearchRequest searchRequest) {
        List<Category> categories = SugarRecord.findWithQuery(Category.class, "SELECT * FROM CATEGORY WHERE REQUEST_ID = ? ", String.valueOf(searchRequest.getId()));
        if (categories != null && !categories.isEmpty()) {
            etCategory.setText(TextUtils.join(",", categories));
            llFilterByCategory.setVisibility(VISIBLE);
        }
        List<Query> queries = SugarRecord.findWithQuery(Query.class, "SELECT * FROM QUERY WHERE REQUEST_ID = ? ", String.valueOf(searchRequest.getId()));
        if (queries != null && !queries.isEmpty()) {
            etName.setText(TextUtils.join(",", queries));
            llFilterByName.setVisibility(VISIBLE);
        }
        List<Address> address = SugarRecord.findWithQuery(Address.class, "SELECT * FROM ADDRESS WHERE REQUEST_ID = ? ", String.valueOf(searchRequest.getId()));
        if (address != null && !address.isEmpty()) {
            etState.setText(TextUtils.join(",", address));
            llFilterByAddress.setVisibility(VISIBLE);
        }
        List<BusinessType> businessTypes = SugarRecord.findWithQuery(BusinessType.class, "SELECT * FROM BUSINESS_TYPE WHERE REQUEST_ID = ? ", String.valueOf(searchRequest.getId()));

        if (businessTypes != null && !businessTypes.isEmpty()) {
//            if (businessTypes.toString().equals(cbFastFood.getText().toString())) {
//                cbFastFood.setChecked(true);
//            }
//            if (businessTypes.toString().equals(cbRestaurant.getText().toString())) {
//                cbRestaurant.setChecked(true);
//            }
            llFilterByOption.setVisibility(VISIBLE);
        }
        cbOpen.setChecked(searchRequest.isOpen());
        cbTakeAway.setChecked(searchRequest.isTakeAway());

        List<Distance> distances = Distance.find(Distance.class, "SEARCH_REQUEST_ID=?", String.valueOf(searchRequest.getId()));
        if (distances != null && !distances.isEmpty()) {
            Distance distance = distances.get(0);
            long moreThan = distance.getMoreThan();
            long lessThan = distance.getLessThan();
            if (moreThan > 0 && lessThan <= 10) {
                rbLessThan5.setChecked(true);
            } else if (moreThan >= 10 && lessThan <= 25) {
                rbLessThan10.setChecked(true);
            } else if (moreThan >= 25 && lessThan <= 50) {
                rbLessThan25.setChecked(true);
            } else if (moreThan >= 50) {
                rbGreaterThan25.setChecked(true);
            }
            llFilterByDistance.setVisibility(VISIBLE);
        }
        callSearchApi(searchRequest);
    }

    private void setVisibilityOfNoResults() {
        if (adapter.getModels().isEmpty()) {
            if (wasSearchPerformed) {
                tvNoResult.setVisibility(VISIBLE);
            } else {
                tvNoResult.setVisibility(GONE);
            }
        } else {
            tvNoResult.setVisibility(GONE);
        }
    }

    public void onGotLocationFirstTime(PlaceInfo placeInfo) {
        Log.d(TAG, "onGotLocationFirstTime() called with: placeInfo = [" + placeInfo + "]");
        adapter.setCurrentPlace(placeInfo);
        sortItems(adapter.getModels());
        adapter.notifyDataSetChanged();
    }

    private void loadRestaurants() {
        final BaseActivity activity = (BaseActivity) getActivity();
        GetRestaurantsRequest request = new GetRestaurantsRequest();
//        List<String> queries = new ArrayList<>();
//
//        String names = etName.getText().toString();
//        if (!TextUtils.isEmpty(names)) {
//            Collections.addAll(queries, names.split(","));
//        }
//        request.setQueries(queries);
//
//        List<String> addresses = new ArrayList<>();
//        String address = etState.getText().toString();
//        if (!TextUtils.isEmpty(address)) {
//            Collections.addAll(addresses, names.split(","));
//        }
//        request.setAddress(addresses);
//
//        List<String> categories = new ArrayList<>();
//        String category = etCategory.getText().toString();
//        if (!TextUtils.isEmpty(category)) {
//            Collections.addAll(categories, names.split(","));
//        }
//        request.setCategories(categories);
//
//        List<String> businessTypes = new ArrayList<>();
//        if (cbFastFood.isChecked()) {
//            businessTypes.add(cbFastFood.getText().toString());
//        }
//        if (cbRestaurant.isChecked()) {
//            businessTypes.add(cbRestaurant.getText().toString());
//        }
//        request.setBusinessTypes(businessTypes);
//
//        Distance distance = new Distance();
//        distance.setLessThan(arrDistance[0]);
//        distance.setMoreThan(arrDistance[1]);
//        request.setDistance(distance);

        activity.showProgress(getString(R.string.loading));
        Call<GetRestaurantsResponse> call = RestClient.getInstance().getWebServices().getRestaurants(
                request
        );
        call.enqueue(new Callback<GetRestaurantsResponse>() {
            @Override
            public void onResponse(Call<GetRestaurantsResponse> call, Response<GetRestaurantsResponse> response) {
                if (isDetached()) {
                    return;
                }
                if (!response.isSuccessful()) {
                    activity.showMessage(getString(R.string.unknow_error));
                    activity.dismissProgress();
                    return;
                }
                GetRestaurantsResponse restaurantsResponse = response.body();
                if (restaurantsResponse.getSuccess() != ServerConfig.SUCCESS_RESPONSE) {
                    activity.showMessage(getString(R.string.unknow_error));
                    activity.dismissProgress();
                    return;
                }

                List<RestaurantData> apiData = restaurantsResponse.getData();
                List<RestaurantData> adapterModels = adapter.getModels();
                sortItems(apiData);
               /* for (RestaurantData apiModel : apiData) {
                    List<RestaurantData> dbData = SugarRecord.find(RestaurantData.class, "RESTAURANT_ID=?", apiModel.getRestaurantId());
                    if (dbData == null || dbData.isEmpty()) {
                        //apiModel.setViews(1);
                        long savedId = SugarRecord.save(apiModel);
                        apiModel.getWorkInfo().setRestaurantId();
                        //continue;
                    }
                    //RestaurantData dbModel = dbData.get(0);
                    //int newViewsCount = dbModel.getViews() + 1;
                    //dbModel.setViews(newViewsCount);
                    // apiModel.setViews(newViewsCount);
                    //SugarRecord.save(dbModel);
                }*/
                adapterModels.clear();
                adapterModels.addAll(apiData);
                adapter.notifyDataSetChanged();
                activity.dismissProgress();
            }

            @Override
            public void onFailure(Call<GetRestaurantsResponse> call, Throwable t) {
                if (isDetached()) {
                    return;
                }
                t.printStackTrace();
                activity.showMessage(getString(R.string.unknow_error));
            }
        });
    }

    private void callSearchApi(SearchRequest searchRequest) {
        final BaseActivity activity = (BaseActivity) getActivity();
        activity.showProgress(getString(R.string.loading));
        Call<GetRestaurantsResponse> searchRestaurants = RestClient.getInstance().getWebServices().searchRestaurants(searchRequest);
        searchRestaurants.enqueue(new Callback<GetRestaurantsResponse>() {
            @Override
            public void onResponse(Call<GetRestaurantsResponse> call, Response<GetRestaurantsResponse> response) {
                if (isDetached()) {
                    return;
                }
                if (!response.isSuccessful()) {
                    activity.showMessage(getString(R.string.unknow_error));
                    activity.dismissProgress();
                    return;
                }

                GetRestaurantsResponse restaurantsResponse = response.body();
                if (restaurantsResponse.getSuccess() != ServerConfig.SUCCESS_RESPONSE) {
                    activity.showMessage(getString(R.string.unknow_error));
                    activity.dismissProgress();
                    return;
                }

                List<RestaurantData> apiData = restaurantsResponse.getData();
                List<RestaurantData> adapterModels = adapter.getModels();
                sortItems(apiData);
                setFavourites(apiData);
                adapterModels.clear();
                adapterModels.addAll(apiData);
                adapter.notifyDataSetChanged();
                activity.dismissProgress();
            }

            @Override
            public void onFailure(Call<GetRestaurantsResponse> call, Throwable t) {
                if (isDetached()) {
                    return;
                }
                t.printStackTrace();
                activity.dismissProgress();
                activity.showMessage(getString(R.string.unknow_error));
            }
        });
    }

    private void setFavourites(List<RestaurantData> data){
        realm.beginTransaction();
        RealmResults<Favourite> model = realm.where(Favourite.class).findAll();
        if(!model.isEmpty()) {
            for(int i = 0; i < data.size(); i++){
                for(int y = 0; y < model.size(); y++){
                    if(data.get(i).getRestaurantId().equalsIgnoreCase(model.get(i).getId())){
                        data.get(i).setFavourite(true);
                    }
                }
            }
        }
        realm.commitTransaction();
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void dispose() {
        adapter = null;
    }

/*    @OnClick(R.id.fabFilter)
    public void onFilterClick() {
        FilterFragment filterFragment = new FilterFragment();
        filterFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FilterDialog);
        filterFragment.show(getActivity().getSupportFragmentManager(), "Filter");
    }*/

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

    @OnClick({R.id.tvSortBy, R.id.tvSaveSearchText, R.id.tvClearSearch, R.id.tvSearch, R.id.tvFilterName, R.id.tvFilterDistance, R.id.tvFilterAddress, R.id.tvFilterCategory, R.id.tvFilterOption})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvClearSearch:
                clearTextsRecursive(llSearchBox);
                mSearchRequest = new SearchRequest();
                break;
            case R.id.tvSearch:
                int expandedFilters = 0;
                for (LinearLayout llFilter : llFilters) {
                    if (llFilter.getVisibility() == VISIBLE) {
                        expandedFilters += 1;
                    }
                }

                if (expandedFilters <= 1 || llFilterByDistance.getVisibility()==GONE) {
                    showMinTowSearchRequiredDialog();
                    return;
                }
                if (!wasSearchPerformed) {
                    wasSearchPerformed = true;
                    rvRestaurants.setVisibility(VISIBLE);
                    tvSortBy.setVisibility(VISIBLE);
                    tvSaveSearch.setVisibility(VISIBLE);
                }
                ((BaseActivity) getActivity()).hideKeyBoard();
                searchRestaurants();
                //collapseALl();
                // getDialog().dismiss();
                break;
            case R.id.tvSortBy:
                showSortByDialog();
                break;
            case R.id.tvSaveSearchText:
                showSaveSearchDialog();
                break;
            case R.id.tvFilterName:
                llFilterByName.setVisibility(llFilterByName.getVisibility() == VISIBLE ? GONE : VISIBLE);
                break;
            case R.id.tvFilterDistance:
                llFilterByDistance.setVisibility(llFilterByDistance.getVisibility() == VISIBLE ? GONE : VISIBLE);
                break;
            case R.id.tvFilterAddress:
                llFilterByAddress.setVisibility(llFilterByAddress.getVisibility() == VISIBLE ? GONE : VISIBLE);
                break;
            case R.id.tvFilterCategory:
                llFilterByCategory.setVisibility(llFilterByCategory.getVisibility() == VISIBLE ? GONE : VISIBLE);
                break;
            case R.id.tvFilterOption:
                llFilterByOption.setVisibility(llFilterByOption.getVisibility() == VISIBLE ? GONE : VISIBLE);
                break;
            // case R.id.tvFilterRating:
            //llFilterByRating.setVisibility(llFilterByRating.getVisibility() == VISIBLE ? GONE : VISIBLE);
            //     break;
        }
    }

    private void clearTextsRecursive(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof RadioGroup) {
                rbLessThan5.setChecked(true);
            } else if (view instanceof ViewGroup) {
                clearTextsRecursive((ViewGroup) view);
            } else if (view instanceof EditText) {
                ((EditText) view).setText(null);
            } else if (view instanceof CheckBox) {
                ((CheckBox) view).setChecked(false);
            }
        }
    }

    private void showSaveSearchDialog() {
        if (mSearchRequest == null) return;
        new MaterialDialog.Builder(getContext())
                .title("Save Current Search")
                .autoDismiss(true)
                .input("Give it some name", null, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (mSearchRequest == null) return;
                        BaseActivity activity = (BaseActivity) getActivity();
                        activity.showProgress("Saving Data");
                        mSearchRequest.setSearchName(input.toString());
                        saveSearchRequest(mSearchRequest);
                        activity.dismissProgress();
                        activity.showMessage("Search has been added to history");
                    }
                })
                .inputType(InputType.TYPE_CLASS_TEXT)
                .positiveText(R.string.save)
                .negativeText(R.string.cancel)
                .show();
    }

    private void saveSearchRequest(SearchRequest searchRequest) {
        long savedId = SugarRecord.save(searchRequest);
        Distance distance = searchRequest.getDistance();
        if (distance != null) {
            distance.setSearchRequestId(savedId);
            SugarRecord.save(distance);
        }

        for (String businessType : searchRequest.getBusinessTypes()) {
            SugarRecord.save(new BusinessType(savedId, businessType));
        }

        for (String category : searchRequest.getCategories()) {
            SugarRecord.save(new Category(savedId, category));
        }

        for (String query : searchRequest.getQueries()) {
            SugarRecord.save(new Query(savedId, query));
        }

        for (String address : searchRequest.getAddress()) {
            SugarRecord.save(new Address(savedId, address));
        }
    }

    private void searchRestaurants() {

        mSearchRequest = new SearchRequest();
        List<String> queries = new ArrayList<>();

        if (llFilterByName.getVisibility() == VISIBLE) {
            String names = etName.getText().toString();
            if (!TextUtils.isEmpty(names)) {
                Collections.addAll(queries, names.split(","));
            }
            mSearchRequest.setQueries(queries);
        }
        if (llFilterByAddress.getVisibility() == VISIBLE) {
            List<String> addresses = new ArrayList<>();
            String address = etState.getText().toString();
            if (!TextUtils.isEmpty(address)) {
                Collections.addAll(addresses, address.split(","));
            }
            mSearchRequest.setAddress(addresses);
        }
        if (llFilterByCategory.getVisibility() == VISIBLE) {
            List<String> categories = new ArrayList<>();
            String category = etCategory.getText().toString();
            if (!TextUtils.isEmpty(category)) {
                Collections.addAll(categories, category.split(","));
            }
            mSearchRequest.setCategories(categories);
        }

        if (llFilterByOption.getVisibility() == VISIBLE) {
            List<String> businessTypes = new ArrayList<>();
            if (cbCasualDining.isChecked()) {
                businessTypes.add(cbCasualDining.getText().toString());
            }
            if (cbCocktailbar.isChecked()) {
                businessTypes.add(cbCocktailbar.getText().toString());
            }
            if (cbCoffeeShop.isChecked()) {
                businessTypes.add(cbCoffeeShop.getText().toString());
            }
            if (cbFineDining.isChecked()) {
                businessTypes.add(cbFineDining.getText().toString());
            }
            if (cbGambling.isChecked()) {
                businessTypes.add(cbGambling.getText().toString());
            }
            if (cbKidsFriendly.isChecked()) {
                businessTypes.add(cbKidsFriendly.getText().toString());
            }
            if (cbLiveEntertainment.isChecked()) {
                businessTypes.add(cbLiveEntertainment.getText().toString());
            }
            if (cbLivesport.isChecked()) {
                businessTypes.add(cbLivesport.getText().toString());
            }
            if (cbLounge.isChecked()) {
                businessTypes.add(cbLounge.getText().toString());
            }
            if (cbOpen.isChecked()) {
                businessTypes.add(cbOpen.getText().toString());
            }
            if (cbOutsideArea.isChecked()) {
                businessTypes.add(cbOutsideArea.getText().toString());
            }
            if (cbPetfriendly.isChecked()) {
                businessTypes.add(cbPetfriendly.getText().toString());
            }
            if (cbPlayArea.isChecked()) {
                businessTypes.add(cbPlayArea.getText().toString());
            }
            if (cbPubAndBar.isChecked()) {
                businessTypes.add(cbPubAndBar.getText().toString());
            }
            if (cbTakeAway.isChecked()) {
                businessTypes.add(cbTakeAway.getText().toString());
            }
            if (cbWifi.isChecked()) {
                businessTypes.add(cbWifi.getText().toString());
            }
            mSearchRequest.setBusinessTypes(businessTypes);

            mSearchRequest.setOpen(cbOpen.isChecked());
            mSearchRequest.setTakeAway(cbTakeAway.isChecked());
        }

        mSearchRequest.setTimestamp(System.currentTimeMillis());
        mSearchRequest.setFormattedTime(Utils.getCurrentTime());

        // TODO: 10-Apr-17
        mSearchRequest.setSearchName("Search " + mSearchRequest.getTimestamp());

        Distance distance = new Distance();
        PlaceInfo currentPlace = ((PlaceDetectorActivity) getActivity()).currentPlace;
        if (currentPlace != null && currentPlace.getLocation() != null) {
            android.location.Location location = currentPlace.getLocation();
            distance.setLatitude(location.getLatitude());
            distance.setLongitude(location.getLongitude());
//            distance.setLatitude(-26.416711);
//            distance.setLongitude(28.465283);
        }
        distance.setMoreThan(arrDistance[0]);
        distance.setLessThan(arrDistance[1]);
        mSearchRequest.setDistance(distance);

        callSearchApi(mSearchRequest);
    }

    private void showMinTowSearchRequiredDialog() {
        new MaterialDialog.Builder(getContext())
                .title("Alert!")
                .content("Distance and one more search option must be selected.")
                .autoDismiss(true)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .show();
    }


    private void showSortByDialog() {
        CharSequence[] options = new CharSequence[]{
                "Distance",
                "Name",
                "Open/Close",
        };
        new MaterialDialog.Builder(getContext())
                .title("Sort By")
                .items(options)
                .autoDismiss(true)
                .itemsCallbackSingleChoice(currentSortBy, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        currentSortBy = which;
                        sortItems(adapter.getModels());
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .negativeText(R.string.cancel)
                .show();
    }

    private void sortItems(List<RestaurantData> items) {
        switch (currentSortBy) {
            case SORT_BY_DISTANCE:
                Collections.sort(items, new Comparator<RestaurantData>() {
                    @Override
                    public int compare(RestaurantData o1, RestaurantData o2) {
                        return o1.getDistance() > o2.getDistance() ? 1 :
                                o1.getDistance() < o2.getDistance() ? -1 : 0;
                    }
                });
                break;
            case SORT_BY_NAME:
                Collections.sort(items, new Comparator<RestaurantData>() {
                    @Override
                    public int compare(RestaurantData o1, RestaurantData o2) {
                        return o1.getTradingName().compareTo(o2.getTradingName());
                    }
                });
                break;
            case SORT_BY_STATUS:
                Collections.sort(items, new Comparator<RestaurantData>() {
                    @Override
                    public int compare(RestaurantData o1, RestaurantData o2) {
                        return (o1.getWorkInfo().isIsOpen() && !o2.getWorkInfo().isIsOpen()) ? -1 :
                                ((o1.getWorkInfo().isIsOpen() && o2.getWorkInfo().isIsOpen())) ? 0 : 1;
                    }
                });
                break;
        }
    }

    @Override
    public void onDestroyView() {
        llFilters = null;
        super.onDestroyView();
    }

    private void collapseALl() {
        for (LinearLayout llFilter : llFilters) {
            llFilter.setVisibility(GONE);
        }
    }
}
