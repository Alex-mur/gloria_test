package my.test.gloria.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MapView extends MvpView {
    void addPositionOnMap(String title, double lat, double lon);
}
