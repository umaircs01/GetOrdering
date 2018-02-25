package com.dev.androidapp.view.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Space;

import com.bumptech.glide.Glide;
import com.dev.androidapp.R;
import com.dev.androidapp.model.PlaceInfo;
import com.dev.androidapp.model.pojo.Favourite;
import com.dev.androidapp.model.pojo.Location;
import com.dev.androidapp.model.pojo.RestaurantData;
import com.dev.androidapp.model.pojo.WorkInfo;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

import static com.dev.androidapp.RestaurantApp.realm;


/**
 * Created by Experiments on 29-Mar-17.
 */

public class RestaurantsAdapter extends BaseRecyclerAdapter<RestaurantData, RestaurantsAdapter.ViewHolder> {


    private RestaurantsListener restaurantsListener;
    @Nullable
    private PlaceInfo currentPlace;

    public RestaurantsAdapter(Context context, List<RestaurantData> models, RestaurantsListener restaurantsListener) {
        super(context, models);
        this.restaurantsListener = restaurantsListener;
    }

    @Override
    protected int provideLayout() {
        return R.layout.row_restaurant;
    }

    @Override
    protected ViewHolder generateViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bind(final ViewHolder holder, final RestaurantData model, final int position) {
        Glide.with(getContext())
                .load(model.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(holder.ivImage);

        //holder.tvLocation.setText(model.getLocation().getAddress());
        //holder.tvWebsite.setText(model.getContactInfo().getWebsite());
        holder.tvViews.setText(String.valueOf(model.getViews()) + (model.getViews()==1?" view":" views"));

        if (position == getModels().size() - 1) {
            holder.spaceBottom.setVisibility(View.VISIBLE);
        } else {
            holder.spaceBottom.setVisibility(View.GONE);
        }
        if (currentPlace == null || currentPlace.getLocation() == null) {
            holder.tvDistance.setVisibility(View.INVISIBLE);
        } else {
            holder.tvDistance.setVisibility(View.VISIBLE);
            android.location.Location dst = new android.location.Location("");
            dst.setLatitude(model.getLocation().getLat());
            dst.setLongitude(model.getLocation().getLng());
            float distanceTo = currentPlace.getLocation().distanceTo(dst) / 1000;
            model.setDistance(distanceTo);
            holder.tvDistance.setText(model.getFormattedDistance() + " km");
        }

        if (model.getWorkInfo().isIsOpen()) {
            holder.tvStatus.setText("Open");
            holder.tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.md_green_700));
        } else {
            holder.tvStatus.setText("Close");
            holder.tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.md_red_700));
        }
        //holder.tvPhone.setText(model.getContactInfo().getPhone());
        holder.tvType.setText(TextUtils.join(" | ", model.getBusinessType()));
        holder.tvCuisine.setText(model.getFoodType());
        holder.tvName.setText(model.getTradingName());
        if(model.isFavourite()){
            holder.favIcon.setImageResource(R.drawable.ic_fav);
        }
        else {
            holder.favIcon.setImageResource(R.drawable.ic_fav_hollow);
        }

        holder.tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantsListener.onTypeClick(model.getBusinessType());
            }
        });
        holder.tvCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantsListener.onCuisineClick(model.getFoodType());
            }
        });
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantsListener.onNameClick(model);
            }
        });
        holder.cvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantsListener.onImageClick(model.getImageUrl());
            }
        });

        holder.tvDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantsListener.onLocationClick(model.getLocation());
            }
        });
        holder.ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantsListener.onAddressClick(model.getLocation());
            }
        });
        holder.ivWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantsListener.onWebsiteClick(model.getContactInfo().getWebsite());
            }
        });
        holder.ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantsListener.onPhoneClick(model.getContactInfo().getPhone());
            }
        });
        holder.tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantsListener.onOpenCloseClick(model.getWorkInfo());
            }
        });
        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!model.isFavourite())
                {
                    holder.favIcon.setImageResource(R.drawable.ic_fav);
                    addToFavourites(model);
                }
                else {
                    holder.favIcon.setImageResource(R.drawable.ic_fav_hollow);
                    removeFavourite(model);
                }
            }
        });

    }

    private void addToFavourites(RestaurantData data){
        realm.beginTransaction();
        data.setFavourite(true);
        Favourite model = realm.createObject(Favourite.class);
        model.setId(data.getRestaurantId());
        model.setFavObject(new Gson().toJson(data));
        realm.commitTransaction();
        notifyDataSetChanged();
    }

    private void removeFavourite(RestaurantData data){
        data.setFavourite(false);
        realm.beginTransaction();
        RealmResults<Favourite> model = realm.where(Favourite.class).equalTo("Id",data.getRestaurantId()).findAll();
        if(!model.isEmpty()) {
            model.get(0).removeFromRealm();
        }
        realm.commitTransaction();
        notifyDataSetChanged();
    }

    public void setCurrentPlace(@Nullable PlaceInfo currentPlace) {
        this.currentPlace = currentPlace;
    }

    static class ViewHolder extends BaseRecyclerAdapter.BaseViewHolder {
        @BindView(R.id.ivImage)
        AppCompatImageView ivImage;
        @BindView(R.id.cvImage)
        CardView cvImage;
        @BindView(R.id.tvName)
        AppCompatTextView tvName;
        @BindView(R.id.tvStatus)
        AppCompatTextView tvStatus;
        @BindView(R.id.tvType)
        AppCompatTextView tvType;
        @BindView(R.id.tvDistance)
        AppCompatTextView tvDistance;
        @BindView(R.id.tvCuisine)
        AppCompatTextView tvCuisine;
        @BindView(R.id.tvViews)
        AppCompatTextView tvViews;
        @BindView(R.id.ivPhone)
        AppCompatImageView ivPhone;
        @BindView(R.id.ivWebsite)
        AppCompatImageView ivWebsite;
        @BindView(R.id.ivLocation)
        AppCompatImageView ivLocation;
        @BindView(R.id.spaceBottom)
        Space spaceBottom;
        @BindView(R.id.favIcon)
        ImageView favIcon;

        Boolean flag;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            flag = false;

        }
    }

        public interface RestaurantsListener {

            void onWebsiteClick(String url);

            void onPhoneClick(String phone);

            void onLocationClick(Location location);

            void onAddressClick(Location latLng);

            void onOpenCloseClick(WorkInfo workInfo);

            void onImageClick(String imageUrl);

            void onTypeClick(List<String> businessType);

            void onCuisineClick(String foodType);

            void onNameClick(RestaurantData restaurantData);

        }
}
