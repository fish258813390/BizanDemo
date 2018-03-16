package neil.com.bizandemo.network.helper;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import neil.com.bizandemo.network.support.ApiConstants;
import neil.com.bizandemo.utils.AppUtils;
import neil.com.bizandemo.utils.FileUtils;
import neil.com.bizandemo.utils.LogUtils;
import neil.com.bizandemo.utils.NetworkUtils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 描述:okHttp 帮助类
 * 全局统一使用的OkHttpClient工具，okhttp版本：okhttp3
 * Created by neil on 2018/3/13 0013.
 */
public class OkHttpHelper {

    private static final long DEFAULT_READ_TIMEOUT_MILLIS = 30 * 1000; // 读取时间
    private static final long DEFAULT_WRITE_TIMEOUT_MILLIS = 30 * 1000; // 写入时间
    private static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = 30 * 1000; // 超时时间

    private static final long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 20 * 1024 * 1024; // 最大缓存 设置20M
    private static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7; // 长缓存有效期为7天

    private static volatile OkHttpHelper sInstance;

    private OkHttpClient mOkHttpClient;
    private Context mContext = AppUtils.getAppContext();

    private OkHttpHelper() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);  // 包含header、body数据

        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
//                .cache()  // 设置缓存
//                .retryOnConnectionFailure(true) // 失败重发
//                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
//                .addInterceptor(mRewriteCacheControlInterceptor)
//                .addInterceptor(loggingInterceptor) //http数据log，日志中打印出HTTP请求&响应数据
                .addInterceptor(mLoggingInterceptor) //http数据log，日志中打印出HTTP请求&响应数据
                .addInterceptor(new UserAgentInterceptor())
                .build();

    }

    public static OkHttpHelper getInstance() {
        if (sInstance == null) {
            synchronized (OkHttpHelper.class) {
                if (sInstance == null) {
                    sInstance = new OkHttpHelper();
                }
            }
        }
        return sInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 设置缓存路径
     */
    public void setCache(Context context) {
        File baseDir = context.getApplicationContext().getCacheDir();
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, "CopyCache");
            mOkHttpClient.newBuilder().cache((new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE)));
        }
    }

    /**
     * 获取缓存路径
     */
    private Cache getCache() {
        Cache cache = null;
        String path = FileUtils.createRootPath(mContext);
        final File baseDir = new File(path);
        final File cacheDir = new File(baseDir, "CopyCache");
        cache = new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
        return cache;
    }

    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = chain -> {
        Request request = chain.request();
        // Logger.d(NetworkUtils.isAvailable(mContext));
        if (!NetworkUtils.isConnected(mContext)) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetworkUtils.isConnected(mContext)) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                    .removeHeader("Pragma").build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                    .removeHeader("Pragma").build();
        }
    };

    /**
     * 添加UA拦截器，B站请求API需要加上UA才能正常使用
     */
    private static class UserAgentInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", ApiConstants.COMMON_UA_STR)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }


    private static final Interceptor mLoggingInterceptor = new Interceptor() {

        String TAG = "LoggerInterceptor";
        private String content;

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            LogUtils.e(TAG, "request:" + request.url());
            Headers headers = request.headers();
            for (int i = 0; i < headers.size(); i++) {
                String headerName = headers.name(i);
                String headerValue = headers.get(headerName);
                LogUtils.e(TAG, "Header----------->Name:" + headerName + "------------>Value:" + headerValue + "\n");
            }
            RequestBody requestBody = request.body();
            if (requestBody instanceof FormBody) {
                HashMap<String, Object> rootMap = new HashMap<>();
                for (int i = 0; i < ((FormBody) requestBody).size(); i++) {
                    rootMap.put(((FormBody) requestBody).encodedName(i), getValueDecode(((FormBody) requestBody).encodedValue(i)));
                }
                LogUtils.e(TAG, "params : " + new Gson().toJson(rootMap));
            }
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            okhttp3.MediaType mediaType = response.body().contentType();
            ResponseBody originalBody = response.body();
            if (null != originalBody) {
                content = originalBody.string();
            }
            LogUtils.e(TAG, "response body:" + content);
            try {

            } catch (Exception e) {
                LogUtils.e("Exception", e.getMessage());
                e.printStackTrace();
            }
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t1);
            LogUtils.e(TAG, "time : " + " (" + tookMs + "ms" + ')');
            return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
        }
    };


    /**
     * 解决中文乱码结果集
     *
     * @param value
     * @return
     */
    private static String getValueDecode(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

}
