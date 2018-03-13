package neil.com.bizandemo.di.component;

import android.app.Activity;

import dagger.Component;
import neil.com.bizandemo.di.module.ActivityModule;
import neil.com.bizandemo.di.scope.ActivityScope;
import neil.com.bizandemo.ui.app.SplashActivity;

/**
 * Created by neil on 2018/3/12 0012.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(SplashActivity splashActivity);

}
