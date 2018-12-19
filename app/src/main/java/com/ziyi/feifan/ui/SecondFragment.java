package com.ziyi.feifan.ui;/**
 * Created by dell on 2017/4/5/0005.
 */

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.ziyi.feifan.R;
import com.ziyi.feifan.base.BaseFragment;
import com.ziyi.feifan.ui.view.GlideImageLoader;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ziyi.feifan.ui.view.PopupMenuUtil.dip2px;


/**
 * created by LiChengalin at 2017/4/5/0005
 */
public class SecondFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;
    private String[] titles = {"全部", "新闻", "车评","改装","新能源"};
    public static SecondFragment newInstance() {

        Bundle bundle = new Bundle();

        SecondFragment fragment = new SecondFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            List list = new ArrayList();
            URL uri = new URL( "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543915704854&di=9dd0ba34261886e06df6df31d5a25bc8&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F19%2F99%2F40%2F99y58PIC9DW_1024.jpg");
            URL uri1 = new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543915704849&di=8b16d25c1213ea010ff0b11cd7255ffa&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F17%2F66%2F84%2F01c58PIC6uk_1024.jpg");
            list.add(uri);
            list.add(uri1);
            banner.setImages(list).setImageLoader(new GlideImageLoader()).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        initweiw();
        return view;
    }

    private void initweiw() {


        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        viewpager.setAdapter(myPagerAdapter);

        tablayout.setupWithViewPager(viewpager);
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
            }
        }

//        reflex(tablayout);
        TabLayout.Tab tabAt = tablayout.getTabAt(0);
        View title = tabAt.getCustomView();
        ((TextView) title).setTextSize(16);
        ((TextView) title).setTextColor(ContextCompat.getColor(getContext(), R.color.black_ff));
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //在这里可以设置选中状态下  tab字体显示样式
                viewpager.setCurrentItem(tab.getPosition());
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTextSize(16);
                    ((TextView) view).setTextColor(ContextCompat.getColor(getContext(), R.color.black_ff));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTextSize(14);
                    ((TextView) view).setTextColor(ContextCompat.getColor(getContext(), R.color.colortest));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        reflex(tablayout);
    }
    /**
     * 自定义Tab的View
     * @param currentPosition
     * @return
     */
    private View getTabView(int currentPosition) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_tab, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_item_textview);
        textView.setText(titles[currentPosition]);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public void reflex(final TabLayout tabLayout){
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = dip2px(tabLayout.getContext(), 10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width+dp10 ;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            TestFragment testFragment = new TestFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", titles[position]);
            testFragment.setArguments(bundle);
            return testFragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }

}
