package neil.com.bizandemo.mvp.contract.home;

import java.util.List;

import neil.com.bizandemo.base.BaseContract;
import neil.com.bizandemo.bean.region.Region;
import neil.com.bizandemo.bean.region.RegionTagType;

/**
 * 分区
 *
 * @author neil
 * @date 2018/3/14
 */
public interface RegionContract {

    interface View extends BaseContract.BaseView {

        void showRegion(List<Region> regions);

        void showRegionType(List<RegionTagType> regionTagTypes);

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        // 获取分区数据
        void getRegionData();

    }

}
