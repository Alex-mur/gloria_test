package my.test.gloria.mvp.presenter.list;

import io.reactivex.subjects.PublishSubject;
import my.test.gloria.mvp.view.listItems.CityListRVItemView;

public interface ICityListRVPresenter {
    PublishSubject<CityListRVItemView> getClickSubject();

    void bindView(CityListRVItemView rowView);

    int getItemsCount();
}
