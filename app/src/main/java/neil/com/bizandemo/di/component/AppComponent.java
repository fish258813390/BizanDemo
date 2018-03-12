package neil.com.bizandemo.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import neil.com.bizandemo.di.module.AppModule;

/**
 * Created by neil on 2018/3/12 0012.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Context getContext();
}
