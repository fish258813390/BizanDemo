package neil.com.bizandemo.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * App 模型
 * Created by neil on 2018/3/12 0012.
 */
@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    public Context provideContext(){
        return mContext;
    }
}
