package neil.com.bizandemo.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * App 注解网络层
 * RetrofitHelper 依赖注入
 * Created by neil on 2018/3/13 0013.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AppUrl {
}
