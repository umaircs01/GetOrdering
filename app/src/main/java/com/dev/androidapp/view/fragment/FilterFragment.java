package com.dev.androidapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.androidapp.R;
import com.dev.androidapp.view.activities.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Experiments on 29-Mar-17.
 */

public class FilterFragment extends Fragment {

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
//    @BindView(R.id.tvFilterRating)
//    TextView tvFilterRating;
//    @BindView(R.id.llFilterByRating)
//    LinearLayout llFilterByRating;
    @BindView(R.id.ivClose)
    AppCompatImageView ivClose;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(STYLE_NORMAL,R.style.FilterDialog);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        Window window = getDialog().getWindow();
//        if (window == null) {
//            return;
//        }
//        window.setGravity(Gravity.END | Gravity.BOTTOM);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
//        int width = displayMetrics.widthPixels;
//        // Calculate ActionBar height
//        TypedValue tv = new TypedValue();
//        int actionBarHeight = 0;
//        if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
//        {
//             actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
//        }
//        window.setLayout(width * 92 / 100, height+actionBarHeight/3);
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      //  getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @OnClick({R.id.ivClose,R.id.tvSearch, R.id.tvFilterName, R.id.tvFilterDistance, R.id.tvFilterAddress, R.id.tvFilterCategory, R.id.tvFilterOption})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSearch:
                ((BaseActivity) getActivity()).hideKeyBoard();
                collapseALl();
               // getDialog().dismiss();
                break;
            case R.id.ivClose:
              //  getDialog().dismiss();
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

    private void collapseALl() {
        llFilterByName.setVisibility(GONE);
        llFilterByDistance.setVisibility(GONE);
        llFilterByAddress.setVisibility(GONE);
        llFilterByCategory.setVisibility(GONE);
        llFilterByOption.setVisibility(GONE);
        //llFilterByRating.setVisibility(GONE);
    }
}
