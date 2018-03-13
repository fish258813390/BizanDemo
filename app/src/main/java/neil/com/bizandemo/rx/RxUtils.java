package neil.com.bizandemo.rx;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 通用的 Rx工具类
 * 1. Rx线程转换
 *
 * @author neil
 * @date 2018/3/13
 */

public class RxUtils {

    /**
     * 统一线程管理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {

        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

//        return new FlowableTransformer<T, T>() {
//            @Override
//            public Publisher<T> apply(Flowable<T> upstream) {
//                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//            }
//        };
    }

    /**
     * 生成Flowable 被观察者对象
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER);

//        return Flowable.create(new FlowableOnSubscribe<T>() {
//            @Override
//            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
//                try {
//                    emitter.onNext(t);
//                    emitter.onComplete();
//                } catch (Exception e) {
//                    emitter.onError(e);
//                }
//            }
//        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 生成Flowable 被观察者对象
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Flowable<List<T>> createData(final List<T> t) {
        return Flowable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }

        }, BackpressureStrategy.BUFFER);
    }


//    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
//        //  compose简化线程 统一处理线程
//        return new ObservableTransformer<T, T>() {
//            @Override
//            public ObservableSource apply(Observable upstream) {
//                return upstream.subscribeOn(Schedulers.io()) // 自身在哪个调度器上执行
//                        .observeOn(AndroidSchedulers.mainThread()); // 一个观察者在哪个调度器上订阅observable
//            }
//        };
//    }

}
