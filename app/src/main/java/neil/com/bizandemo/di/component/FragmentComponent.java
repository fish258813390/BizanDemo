package neil.com.bizandemo.di.component;

import android.app.Activity;

import dagger.Component;
import neil.com.bizandemo.di.module.FragmentModule;
import neil.com.bizandemo.di.scope.FragmentScope;
import neil.com.bizandemo.ui.home.RegionFragment;

/**
 * Created by neil on 2018/3/12 0012.
 */
@FragmentScope
@Component(dependencies = AppComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(RegionFragment regionFragment);
}
