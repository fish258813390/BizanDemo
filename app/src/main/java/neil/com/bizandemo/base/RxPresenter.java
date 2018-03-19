package neil.com.bizandemo.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import neil.com.bizandemo.rx.RxBus;

/**
 * 基于 Rx的Presenter 封装，控制订阅的生命周期
 * 绑定View、解绑View
 *
 * @author neil
 * @date 2018/3/13
 */
public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected T mView;
    private CompositeDisposable mCompositeDisposable; // 控制事件订阅分发,可以取消单个订阅

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    /**
     * 取消 订阅
     */
    private void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    /**
     * 删除 指定订阅
     *
     * @param disposable 单个订阅对象(Observable 和 Observer 形成订阅)
     * @return
     */
    protected boolean remove(Disposable disposable) {
        return mCompositeDisposable != null && mCompositeDisposable.remove(disposable);
    }

    /**
     * 添加发射源
     *
     * @param disposable
     */
    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected <K> void addRxBusSubscribe(Class<K> eventType, Consumer<K> act) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(RxBus.getInstance().toDefaultFlowable(eventType, act));
    }

}
