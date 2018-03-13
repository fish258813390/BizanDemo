package neil.com.bizandemo.backgrservice;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;

import neil.com.bizandemo.utils.NetworkUtils;

/**
 * 网络状态监听服务
 *
 * @author neil
 * @date 2018/3/13
 */
public class NetworkService extends Service {

    private final static int GRAY_SERVICE_ID = 1001;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager == null) {
                networkBroadcast(context, intent, -1);
                return;
            }
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info == null) {
                networkBroadcast(context, intent, -1);
                return;
            }
            int type = info.getType();
            switch (type) {
                case ConnectivityManager.TYPE_WIFI:
                    networkBroadcast(context, intent, 1);
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    networkBroadcast(context, intent, 2);
                    break;
                default:
                    break;
            }
        }
    };

    private void networkBroadcast(Context context, Intent intent, int netState) {
        intent.setAction(NetworkUtils.NET_BROADCAST_ACTION);
        intent.putExtra(NetworkUtils.NET_STATE_NAME, netState);
        context.sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

}
