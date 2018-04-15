package neil.com.bizandemo.mvp.presenter.recommend;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import neil.com.bizandemo.base.BaseSubscriber;
import neil.com.bizandemo.base.RxPresenter;
import neil.com.bizandemo.bean.recommend.Recommend;
import neil.com.bizandemo.mvp.contract.home.RecommendContract;
import neil.com.bizandemo.network.helper.RetrofitHelper;
import neil.com.bizandemo.rx.RxUtils;
import neil.com.bizandemo.utils.JsonUtils;

/**
 * 推荐
 * Created by neil on 2018/4/13 0013.
 */
public class RecommendPresenter extends RxPresenter<RecommendContract.View>
        implements RecommendContract.Presenter<RecommendContract.View> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public RecommendPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getRecommendData() {
        BaseSubscriber<List<Recommend>> subscriber = Flowable.just(JsonUtils.readJson("recommend.json"))
                .map(new Function<String, List<Recommend>>() {
                    @Override
                    public List<Recommend> apply(String result) throws Exception {
                        Gson gson = new Gson();
                        JsonObject object = new JsonParser().parse(result).getAsJsonObject();
                        JsonArray array = object.getAsJsonArray("data");
                        List<Recommend> recommends = new ArrayList<>();
                        for (int i = 0; i < array.size(); i++) {
                            JsonElement jsonElement = array.get(i);
                            recommends.add(gson.fromJson(jsonElement, Recommend.class));
                        }
                        return recommends;
                    }
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<Recommend>>(mView) {
                    @Override
                    public void onSuccess(List<Recommend> recommends) {
                        mView.showRecommend(recommends);
                    }
                });
        addSubscribe(subscriber);
    }
}
