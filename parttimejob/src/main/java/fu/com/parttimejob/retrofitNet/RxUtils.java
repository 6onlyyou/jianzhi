package fu.com.parttimejob.retrofitNet;

import android.text.TextUtils;
import android.util.Log;

import java.util.concurrent.Callable;

import fu.com.parttimejob.bean.ResponseBean;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yuanGang on 2018/3/5.
 */
public class RxUtils {
    public static <T> Single<T> wrapRestCall(final Observable<ResponseBean<T>> call) {
        return call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBean<T>, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(ResponseBean<T> tResponseBean) throws Exception {
                        Log.i("RxUtils", "apply: " + tResponseBean.toString());
                        if ("0".equals(tResponseBean.getState())) {
                            if (tResponseBean.getData() == null) {
                                return Observable.empty();
                            } else {
                                return Observable.just(tResponseBean.getData());
                            }
                        } else {
                            if (!TextUtils.isEmpty(tResponseBean.getMsg()))
                                return Observable.error(new Exception(tResponseBean.getMsg()));
                            else
                                return Observable.error(new Exception(tResponseBean.getState()));
                        }
                    }
                }, new Function<Throwable, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
                        Log.e("API ERROR", throwable.toString());
                        return Observable.error(throwable);
                    }
                }, new Callable<ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> call() throws Exception {
                        return Observable.empty();
                    }
                }).singleOrError();
    }
}
