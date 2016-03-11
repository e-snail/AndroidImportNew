package roff.importnew.rx.githubdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import roff.importnew.R;
import roff.importnew.rx.githubdemo.adapter.CardAdapter;
import roff.importnew.rx.githubdemo.model.Github;
import roff.importnew.rx.githubdemo.service.GithubService;
import roff.importnew.rx.githubdemo.service.ServiceFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wuyongbo on 16-3-10.
 */
public class GithubDemo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.github_demon);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CardAdapter mCardAdapter = new CardAdapter();
        mRecyclerView.setAdapter(mCardAdapter);

        Button bClear = (Button) findViewById(R.id.button_clear);
        Button bFetch = (Button) findViewById(R.id.button_fetch);
        bClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardAdapter.clear();
            }
        });

        bFetch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GithubService service = ServiceFactory.createRetrofitService(GithubService.class, GithubService.SERVICE_ENDPOINT);
                for (String login : Data.githubList) {
                    service.getUser(login)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Github>() {
                                @Override
                                public void onCompleted() {
                                    Log.d("GithubDemo", "onCompleted");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("GithubDemo", e.getMessage());
                                }

                                @Override
                                public void onNext(Github github) {
                                    mCardAdapter.addData(github);
                                }
                            });

                }
            }
        });
    }
}
