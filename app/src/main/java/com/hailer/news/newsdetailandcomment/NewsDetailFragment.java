package com.hailer.news.newsdetailandcomment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hailer.news.R;
import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.news.NewsContract;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.socks.library.KLog;
import com.zzhoujay.richtext.RichText;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moma on 17-8-23.
 */

@ActivityFragmentInject(contentViewId = R.layout.fragment_news_detail)
public class NewsDetailFragment extends Fragment implements View.OnClickListener {

    protected View mFragmentRootView;
    private NewsDetailAddCommentContract.Presenter mPresenter;
    protected int mContentViewId;

    private TextView mDetailTitle;
    private TextView mDetailTime;
    private TextView mDetailBody;

    RelativeLayout normalLayout;
    LinearLayout netErrorLayout;


    private static final String ARGS_POST_ID = "args_PostId";
    private String mPostId;
    private String mPostUrl;
    private String mPostTitle;

    public static NewsDetailFragment newInstance(String postId) {
        Bundle args = new Bundle();
        args.putString(ARGS_POST_ID,postId);
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPostId = getArguments().getString(ARGS_POST_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mFragmentRootView == null) {
            if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
                ActivityFragmentInject annotation = getClass()
                        .getAnnotation(ActivityFragmentInject.class);
                mContentViewId = annotation.contentViewId();

            } else {
                throw new RuntimeException(
                        "Class must add annotations of ActivityFragmentInitParams.class");
            }

            try{
                mFragmentRootView = inflater.inflate(mContentViewId, container, false);
            }catch (Exception e){
                throw e;
            }
        }

        normalLayout = mFragmentRootView.findViewById(R.id.normal_layout);
        netErrorLayout = mFragmentRootView.findViewById(R.id.net_error_layout);

        mDetailTitle = mFragmentRootView.findViewById(R.id.news_detail_title);
        mDetailTime = mFragmentRootView.findViewById(R.id.news_detail_time);
        mDetailBody = mFragmentRootView.findViewById(R.id.news_detail_body);

        Button retryButton = mFragmentRootView.findViewById(R.id.retry_button);
        retryButton.setOnClickListener(this);
//        initView();
        mPresenter.getNewsDetail(mPostId);

        return mFragmentRootView;
    }


    public void setPresenter(@NonNull NewsDetailAddCommentContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.retry_button:
                mPresenter.getNewsDetail(mPostId);
                break;
        }
    }

    public void handleError(){
        normalLayout.setVisibility(View.GONE);
        netErrorLayout.setVisibility(View.VISIBLE);
    }

    public void showDetail(NewsDetail data){
        KLog.e("---showDetail---");

        normalLayout.setVisibility(View.VISIBLE);
        netErrorLayout.setVisibility(View.GONE);

        mDetailTitle.setText(data.getTitle());
        mDetailTime.setText(data.getDate());
        mPostUrl =  data.getUrl();
        mPostTitle = data.getTitle();

        String content = data.getContent();
        if (!TextUtils.isEmpty(content)) {
            RichText.fromHtml(content)
                    //.autoPlay(true)
                    .into(mDetailBody);
        }
    }
}
