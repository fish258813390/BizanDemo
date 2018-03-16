package neil.com.bizandemo.base;

import android.text.TextUtils;

import java.net.SocketTimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;
import neil.com.bizandemo.network.exception.ApiException;
import neil.com.bizandemo.utils.AppUtils;
import neil.com.bizandemo.utils.LogUtils;
import neil.com.bizandemo.utils.NetworkUtils;
import retrofit2.HttpException;

/**
 * 统一处理订阅者
 *
 * @author neil
 * @date 2018/3/13
 */
public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {

    private BaseContract.BaseView mView;
    private String mMsg;

    public BaseSubscriber(BaseContract.BaseView mView) {
        this.mView = mView;
    }

    public abstract void onSuccess(T t);

    @Override
    protected void onStart() {
        super.onStart();
        // 判断有无网络
        if (!NetworkUtils.isConnected(AppUtils.getAppContext())) {
            // 没有网络
        } else {

        }
    }

    @Override
    public void onNext(T t) {
        if (mView == null) {
            return;
        }
        mView.complete();
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        mView.complete(); // 完成操作
        if (mMsg != null && !TextUtils.isEmpty(mMsg)) {
            mView.showError(mMsg);
        } else if (e instanceof ApiException) {
            mView.showError(e.toString());
        } else if (e instanceof SocketTimeoutException) {
            mView.showError("服务器响应超时ヽ(≧Д≦)ノ");
        } else if (e instanceof HttpException) {
            mView.showError("数据加载失败ヽ(≧Д≦)ノ");
        } else {
            mView.showError("未知错误ヽ(≧Д≦)ノ");
            LogUtils.e("MYERROR:" + e.toString());
        }
    }

    @Override
    public void onComplete() {

    }

    public void onFailure(int code, String message) {

    }
}
