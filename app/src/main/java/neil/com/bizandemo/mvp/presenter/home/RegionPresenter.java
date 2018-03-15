package neil.com.bizandemo.mvp.presenter.home;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import neil.com.bizandemo.base.BaseListSubscriber;
import neil.com.bizandemo.base.RxPresenter;
import neil.com.bizandemo.bean.region.Region;
import neil.com.bizandemo.bean.region.RegionTagType;
import neil.com.bizandemo.mvp.contract.home.RegionContract;
import neil.com.bizandemo.network.helper.RetrofitHelper;
import neil.com.bizandemo.network.response.HttpResponse;
import neil.com.bizandemo.rx.RxUtils;
import neil.com.bizandemo.utils.JsonUtils;

/**
 * 描述:首页分区 Presenter
 *
 * @author neil
 * @date 2018/3/14
 */
public class RegionPresenter extends RxPresenter<RegionContract.View> implements RegionContract.Presenter<RegionContract.View> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public RegionPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getRegionData() {
        BaseListSubscriber<Region> subscriber = Flowable.just(JsonUtils.readJson("region.json"))
                .flatMap(new Function<String, Publisher<HttpResponse<List<Region>>>>() {
                    @Override
                    public Publisher<HttpResponse<List<Region>>> apply(String ss) throws Exception {
                        Gson gson = new Gson();
                        JsonObject object = new JsonParser().parse(ss).getAsJsonObject();
                        JsonArray array = object.getAsJsonArray("data");
                        List<RegionTagType> regionTypes = new ArrayList<>();
                        for (JsonElement jsonElement : array) {
                            regionTypes.add(gson.fromJson(jsonElement, RegionTagType.class));
                        }
                        mView.showRegionType(regionTypes);
                        // 拿到分区数据后 在进行网络请求获取分区
                        return mRetrofitHelper.getRegion();
                    }
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseListSubscriber<Region>(mView) {
                    @Override
                    public void onSuccess(List<Region> regions) {
                        mView.showRegion(regions);
                    }
                });
        addSubscribe(subscriber);
    }
}
