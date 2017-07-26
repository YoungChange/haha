package com.moma.app.news.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moma.app.news.R;

import zhou.widget.RichText;

/**
 * Created by moma on 17-7-26.
 */

public class NewsDetailFragment extends Fragment {

    private TextView mDetailTitle;
    private TextView mDetailTime;
    private RichText mDetailBody;
    private ImageView mDetailImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_news_detail, container, false);

        mDetailTitle = (TextView) view.findViewById(R.id.news_detail_title);
        mDetailTime = (TextView) view.findViewById(R.id.news_detail_time);
        mDetailBody = (RichText) view.findViewById(R.id.news_detail_body);
        mDetailImage = (ImageView) view.findViewById(R.id.news_detail_image);


        return view;
    }
}