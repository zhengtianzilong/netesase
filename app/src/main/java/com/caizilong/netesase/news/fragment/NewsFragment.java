package com.caizilong.netesase.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.caizilong.netesase.R;
import com.caizilong.netesase.news.adapter.NewsAdapter;
import com.caizilong.netesase.news.bean.FragmentInfo;
import com.caizilong.netesase.news.news_inner.HotFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

/**
 * Created by caizilong on 2018/1/12.
 */

public class NewsFragment extends Fragment {

    ArrayList<FragmentInfo> mFragmentInfos;

    NewsAdapter mNewsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFragmentInfos = new ArrayList<>();
        FrameLayout layout = (FrameLayout) getActivity().findViewById(R.id.tabs);

        layout.addView(View.inflate(getActivity(), R.layout.include_tab,null));

        SmartTabLayout smartTabLayout = (SmartTabLayout)getActivity().findViewById(R.id.smart_tab);

        ViewPager viewPager = (ViewPager)getActivity().findViewById(R.id.viewpager);


        String[] stringArray = getResources().getStringArray(R.array.news_title);

        for (int i = 0; i < stringArray.length; i++) {
            FragmentInfo info;
            if (i == 0){
                info = new FragmentInfo(new HotFragment(), stringArray[i]);
            }else {
                info = new FragmentInfo(new EmptyFragment(), stringArray[i]);
            }

            mFragmentInfos.add(info);


        }
        mNewsAdapter = new NewsAdapter(getFragmentManager(), mFragmentInfos);

        viewPager.setAdapter(mNewsAdapter);
        smartTabLayout.setViewPager(viewPager);

    }
}
