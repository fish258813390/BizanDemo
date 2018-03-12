package neil.com.bizandemo.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import neil.com.bizandemo.di.scope.ActivityScope;

/**
 * Activity 模型
 * Created by neil on 2018/3/12 0012.
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
