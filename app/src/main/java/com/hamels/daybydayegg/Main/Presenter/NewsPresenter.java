package com.hamels.daybydayegg.Main.Presenter;

import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Main.Contract.NewsContract;
import com.hamels.daybydayegg.Repository.RepositoryManager;


public class NewsPresenter extends BasePresenter<NewsContract.View> implements NewsContract.Presenter {
    public static final String TAG = NewsPresenter.class.getSimpleName();

    public NewsPresenter(NewsContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }
}
