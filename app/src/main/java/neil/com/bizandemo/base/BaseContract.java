package neil.com.bizandemo.base;

/**
 * 基础契约类,用来管理 presenter 和 view
 *
 * @author neil
 * @date 2018/3/13
 */
public interface BaseContract {

    interface BaseView {

        /**
         * 请求出错回调
         *
         * @param msg
         */
        void showError(String msg);

        /**
         * 请求完成回调
         */
        void complete();
    }

    interface BasePresenter<T> {

        /**
         * 绑定View
         *
         * @param view
         */
        void attachView(T view);

        /**
         * 解绑View
         */
        void detachView();

    }

}
