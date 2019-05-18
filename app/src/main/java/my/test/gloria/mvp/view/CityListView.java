package my.test.gloria.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface CityListView extends MvpView {
    void updateList();

    void showLoadingMessage();

    void showErrorMessage(String text);

    void hideMessage();
}
