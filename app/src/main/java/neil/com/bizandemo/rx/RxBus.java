package neil.com.bizandemo.rx;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Rxbus 事件管理
 *
 * @author neil
 * @date 2018/3/13
 */
public class RxBus {

    private static volatile RxBus sRxBus; // 线程安全,带同步块功能,不会像synchronized造成线程阻塞
    private final FlowableProcessor<Object> mBus;

    public RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (sRxBus == null) {
            synchronized (RxBus.class) {
                if (sRxBus == null) {
                    sRxBus = new RxBus();
                }
            }
        }
        return sRxBus;
    }

    /**
     * 发送一个新事件,发送数据
     *
     * @param object
     */
    public void post(Object object) {
        mBus.onNext(object);
    }

    /**
     * 根据传递的eventType 类型返回特定类型(eventType)的被观察者
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

    /**
     * 默认事件订阅
     *
     * @param eventType
     * @param act
     * @param <T>
     * @return
     */
    public <T> Disposable toDefaultFlowable(Class<T> eventType, Consumer<T> act) {
        return mBus.ofType(eventType).compose(RxUtils.<T>rxSchedulerHelper()).subscribe(act);
    }

}
