package neil.com.bizandemo.base;

import android.text.TextUtils;

import java.net.SocketTimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;
import neil.com.bizandemo.network.exception.ApiException;
import neil.com.bizandemo.network.response.HttpResponse;
import neil.com.bizandemo.utils.LogUtils;
import retrofit2.HttpException;

/**
 * 统一处理订阅者
 *
 * @author neil
 * @date 2018/3/13
 */
public abstract class BaseObjectSubscriber<T> extends ResourceSubscriber<HttpResponse<T>> {

    private BaseContract.BaseView mView;
    private String mMsg;

    public BaseObjectSubscriber(BaseContract.BaseView mView) {
        this.mView = mView;
    }

    public abstract void onSuccess(T t);

    public void onFailure(int code, String message) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 网络判断
    }

    @Override
    public void onNext(HttpResponse<T> response) {
        if (mView == null) {
            return;
        }
        mView.complete();
        if (response.code == 0) {
            if (response.data != null) {
                onSuccess(response.data);
            }
            if (response.result != null) {
                onSuccess(response.result);
            }
        } else {
            //可以不处理任何东西
            onFailure(response.code, response.message);
        }
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
}
