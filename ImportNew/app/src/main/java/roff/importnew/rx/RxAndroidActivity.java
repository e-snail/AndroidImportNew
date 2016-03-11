package roff.importnew.rx;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import roff.importnew.R;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.functions.Func0;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

/**
 * Created by wuyongbo on 16-3-10.
 */
public class RxAndroidActivity extends Activity {
    private static final String TAG = "RxAndroidSamples";

    private Handler backgroundHandler;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rxandroid_activity);

        BackgroundThread backgroundThread = new BackgroundThread();
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());

        findViewById(R.id.button_run_scheduler).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onRunSchedulerExampleButtonClicked();
            }
        });
    }

    void onRunSchedulerExampleButtonClicked() {
        sampleObservable()
                .subscribeOn(HandlerScheduler.from(backgroundHandler))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError()");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext() with string " + s);
                    }
                });
    }

    static Observable<String> sampleObservable() {
        return rx.Observable.defer(new Func0<rx.Observable<String>>() {
            @Override
            public rx.Observable<String> call() {
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }

    static class BackgroundThread extends HandlerThread {
        BackgroundThread() {
            super("SchedulerSample-BackgroundThread", THREAD_PRIORITY_BACKGROUND);
        }
    }
}
