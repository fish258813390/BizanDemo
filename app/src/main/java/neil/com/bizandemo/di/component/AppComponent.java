package neil.com.bizandemo.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import neil.com.bizandemo.di.module.ApiModule;
import neil.com.bizandemo.di.module.AppModule;
import neil.com.bizandemo.network.helper.RetrofitHelper;

/**
 * Created by neil on 2018/3/12 0012.
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    Context getContext();

    // 这里有个关键点，就是子组件(ActivityComponent)需要这个里面的某个实例的时候，这里需要使用一个接口，将需要的实例做一个返回动作。这里是RetrofitHelper这一行。
    RetrofitHelper getRetrofitHelper();
}
