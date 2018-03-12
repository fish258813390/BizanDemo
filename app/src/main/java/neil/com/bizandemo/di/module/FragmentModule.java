package neil.com.bizandemo.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;
import neil.com.bizandemo.di.scope.FragmentScope;

/**
 * fragment 模型
 * Created by neil on 2018/3/12 0012.
 */
@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity proviceActivity() {
        return mFragment.getActivity();
    }
}
