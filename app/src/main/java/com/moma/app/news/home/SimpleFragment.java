package com.moma.app.news.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moma.app.news.base.BaseFragment;
import com.moma.app.news.R;

/**
 * Created by moma on 17-7-17.
 */

public class SimpleFragment extends BaseFragment {

    public static final String ARGS_ID = "args_id";
    public static final String ARGS_NAME = "args_name";

    private int tagId;
    private String tagName;

    /**
     *
     * @param tId 第几个tab
     * @param tName tab的title
     * @return
     */
    public static SimpleFragment newInstance(int tId,String tName) {
        Bundle args = new Bundle();

        args.putInt(ARGS_ID, tId);
        args.putString(ARGS_NAME,tName);
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tagId = getArguments().getInt(ARGS_ID);
        tagName = getArguments().getString(ARGS_NAME);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_fragment_layout,container,false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("第"+tagId+"页:"+tagName);
        return view;
    }
}
