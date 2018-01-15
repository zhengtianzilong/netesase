package com.caizilong.netesase.news.news_inner;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.caizilong.netesase.R;
import com.caizilong.netesase.news.adapter.HotAdapter;
import com.caizilong.netesase.news.adapter.NewsAdapter;
import com.caizilong.netesase.news.bean.Banner;
import com.caizilong.netesase.news.bean.Hot;
import com.caizilong.netesase.news.bean.HotDetail;
import com.caizilong.netesase.util.Constant;
import com.caizilong.netesase.util.HttpRespon;
import com.caizilong.netesase.util.HttpUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/14.
 */

public class HotFragment extends Fragment {

    private static final String TAG = "HotFragment";

    ListView mListView;

    HotAdapter hotAdapter;

    // 放置轮播图
    ArrayList<Banner> mBanners;

    // 新闻数据
    ArrayList<HotDetail> mhotDetails;

    MyHandler myHandler;

    private static final int INIT_SUCCESS = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_hot, container, false);

        mListView = (ListView) inflate.findViewById(R.id.listview);

        return inflate;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBanners = new ArrayList<>();
        HttpUtil util = HttpUtil.getInstance();
        mhotDetails = new ArrayList<>();
        myHandler = new MyHandler(this);


        util.getDate(Constant.HOT_URL, new HttpRespon<Hot>(Hot.class) {
            @Override
            public void error(String msg) {

                Log.i(TAG, "success: "+ msg);

            }

            @Override
            public void success(Hot result) {
                if (null != result && null != result.getT1348647909107()){

                List<HotDetail> details = result.getT1348647909107();
//                // 取出第0位轮播图数据
//                HotDetail tmp_banner = details.get(0);
//                List<Banner> banners = tmp_banner.getAds();
//                // 获取轮播图片成功
//                mBanners.addAll(banners);
//                // 删除轮播图片
//                details.remove(0);

                // 列表数据加载完成
                mhotDetails.addAll(details);

                myHandler.sendEmptyMessage(INIT_SUCCESS);
            }

                Log.i(TAG, "success: "+ mhotDetails.toString());

            }
        });



    }


    private void initData(){
        hotAdapter = new HotAdapter(mhotDetails, getActivity());

        mListView.setAdapter(hotAdapter);

    }


    static class MyHandler extends Handler{

        WeakReference<HotFragment> hotFragmentWeakReference;

        public MyHandler(HotFragment hotFragment) {

            this.hotFragmentWeakReference = new WeakReference<HotFragment>(hotFragment);

        }


        @Override
        public void handleMessage(Message msg) {

            HotFragment hotFragment = hotFragmentWeakReference.get();

            if (hotFragment == null){
                return;
            }

            switch (msg.what){
                case INIT_SUCCESS:

                    hotFragment.initData();

                    break;
            }


        }
    }


}
