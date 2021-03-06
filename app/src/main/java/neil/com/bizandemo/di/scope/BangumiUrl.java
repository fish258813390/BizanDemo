package neil.com.bizandemo.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 番剧 api单例注解
 * Created by neil on 2018/3/16 0016.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface BangumiUrl {
}
