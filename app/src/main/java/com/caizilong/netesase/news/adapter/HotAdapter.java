package com.caizilong.netesase.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.caizilong.netesase.R;
import com.caizilong.netesase.news.bean.Hot;
import com.caizilong.netesase.news.bean.HotDetail;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by caizilong on 2018/1/15.
 */

public class HotAdapter extends BaseAdapter {

    ArrayList<HotDetail> hotArrayList;

    DisplayImageOptions imageOptions;

    LayoutInflater minflater;

    public HotAdapter(ArrayList<HotDetail> hotArrayList, Context context) {
        this.hotArrayList = hotArrayList;
        this.minflater = LayoutInflater.from(context);

        // 建造者模式
        imageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();



    }

    @Override
    public int getCount() {
        return hotArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHoder hoder;

        HotDetail detail = hotArrayList.get(position);

        if (convertView == null){

            convertView = minflater.inflate(R.layout.item_hot, parent, false);

            hoder = new ViewHoder();
            hoder.icon = convertView.findViewById(R.id.img);
            hoder.title = convertView.findViewById(R.id.title);
            hoder.source = convertView.findViewById(R.id.source);
            hoder.reply_count = convertView.findViewById(R.id.reply_count);
            hoder.special = convertView.findViewById(R.id.special);

            convertView.setTag(hoder);

        }else {

            hoder = (ViewHoder) convertView.getTag();

        }
        initView(hoder,detail);

        return convertView;
    }


    public void initView(ViewHoder viewHoder, HotDetail detail){

        viewHoder.title.setText(detail.getTitle());
        viewHoder.source.setText(detail.getSource());
        viewHoder.reply_count.setText(detail.getReplyCount() + "跟帖");

        ImageLoader.getInstance().displayImage(detail.getImg(), viewHoder.icon, imageOptions);

    }




    class ViewHoder{

        ImageView icon;

        TextView title;

        TextView source;

        TextView reply_count;
        TextView special;

    }


}
