package com.caizilong.netesase;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.caizilong.netesase.news.fragment.EmptyFragment;
import com.caizilong.netesase.news.fragment.MineFragment;
import com.caizilong.netesase.news.fragment.NewsFragment;
import com.caizilong.netesase.news.fragment.ReadingFragment;
import com.caizilong.netesase.news.fragment.TopicFragment;
import com.caizilong.netesase.news.fragment.VedioFragment;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTabHost tabHost = (FragmentTabHost)findViewById(R.id.tab_Host);

        // 1 绑定
        tabHost.setup(this, getSupportFragmentManager(), R.id.content);


        String[] tabtitles = getResources().getStringArray(R.array.tab_title);

        int[] icons = new int[]{R.drawable.news_selector, R.drawable.reading_selector, R.drawable.topic_selector, R.drawable.vedio_selector, R.drawable.mine_selector};

        Class[] classes = new Class[]{NewsFragment.class, TopicFragment.class, ReadingFragment.class, VedioFragment.class, MineFragment.class};;

        for (int i = 0; i < tabtitles.length; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec("" + i);

            tabSpec.setIndicator(getEveryView(this, tabtitles, icons, i));

            tabHost.addTab(tabSpec, classes[i], null);
        }


//        // 2 生成不同的标签 tag详单与是这个标签的id
//        TabHost.TabSpec one = tabHost.newTabSpec("1");
//
////        one.setIndicator("one");
////
////        one.setIndicator(getEveryView(this));
//
//        TabHost.TabSpec two = tabHost.newTabSpec("2");
//
//        two.setIndicator("two");
//
//        TabHost.TabSpec three = tabHost.newTabSpec("3");
//
//        three.setIndicator("three");
//
//        tabHost.addTab(one, NewsFragment.class, null);
//        tabHost.addTab(two, EmptyFragment.class, null);
//        tabHost.addTab(three, EmptyFragment.class, null);


    }


    public View getEveryView( Context context, String[] titles, int[] icons , int index){

        View titleView  = LayoutInflater.from(context).inflate(R.layout.item_title, null);

        TextView title = (TextView) titleView.findViewById(R.id.item_title);
        ImageView icon = (ImageView) titleView.findViewById(R.id.imageview);
        title.setText(titles[index]);
        icon.setImageResource(icons[index]);

        return titleView;




    }


}
