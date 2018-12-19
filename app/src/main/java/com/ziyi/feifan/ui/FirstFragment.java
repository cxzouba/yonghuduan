package com.ziyi.feifan.ui;/**
 * Created by dell on 2017/4/5/0005.
 */

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;
import com.ziyi.feifan.R;
import com.ziyi.feifan.base.BaseFragment;
import com.ziyi.feifan.ui.view.GlideImageLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * created by LiChengalin at 2017/4/5/0005
 */
public class FirstFragment extends BaseFragment {

    public static FirstFragment newInstance() {

        Bundle bundle = new Bundle();

        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        Banner banner = (Banner) view.findViewById(R.id.banner);
        try {
            List list = new ArrayList();
            URL uri = new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545113474427&di=7ad646b2afabe1f310e9bb39e0de4798&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F17%2F01%2F15%2F51V58PICNre_1024.jpg");
            URL uri1 = new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545113474425&di=e8c8256ff4dc43ad1db4899277ebd00a&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F18%2F51%2F05%2F562dc3971a2aa_1024.jpg");
            list.add(uri);
            list.add(uri1);
            banner.setImages(list).setImageLoader(new GlideImageLoader()).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return view;
    }
}
