package my.test.gloria.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import my.test.gloria.mvp.model.api.IDataSource;
import my.test.gloria.mvp.model.entity.CitiesResponse;
import my.test.gloria.mvp.model.entity.City;
import my.test.gloria.mvp.presenter.list.ICityListRVPresenter;
import my.test.gloria.mvp.view.CityListView;
import my.test.gloria.mvp.view.listItems.CityListRVItemView;
import my.test.gloria.navigation.Screens;
import my.test.gloria.ui.App;
import ru.terrakok.cicerone.Router;


@InjectViewState
public class CityListPresenter extends MvpPresenter<CityListView> {

    public CityListRVPresenter cityListRVPresenter;
    @Inject
    IDataSource dataSource;
    @Inject
    Router router;
    private Scheduler mainThreadScheduler;
    private List<City> citiesList;


    public CityListPresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
        cityListRVPresenter = new CityListRVPresenter();
        App.getInstance().getAppComponent().inject(this);
        loadData();
    }

    public void onStart() {
    }

    private void loadData() {
        dataSource.getCities().subscribeOn(Schedulers.io())
                .observeOn(mainThreadScheduler)
                .subscribe(new SingleObserver<CitiesResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getViewState().showLoadingMessage();
                    }

                    @Override
                    public void onSuccess(CitiesResponse citiesResponse) {
                        citiesList = new ArrayList<>();
                        citiesList.addAll(citiesResponse.cities);
                        Collections.sort(citiesList, (o1, o2) -> o1.city_id > o2.city_id ? 1 : 0);
                        getViewState().updateList();
                        getViewState().hideMessage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showErrorMessage(e.getMessage());
                    }
                });
    }

    public class CityListRVPresenter implements ICityListRVPresenter {

        PublishSubject<CityListRVItemView> clickSubject;

        @SuppressLint("CheckResult")
        CityListRVPresenter() {
            clickSubject = PublishSubject.create();
            clickSubject.subscribe(result -> {
                City city = citiesList.get(result.getPos());
                router.navigateTo(new Screens.MapScreen(city.city_name, city.city_latitude, city.city_longitude));
            });
        }

        @Override
        public PublishSubject<CityListRVItemView> getClickSubject() {
            return clickSubject;
        }

        @Override
        public void bindView(CityListRVItemView rowView) {
            City city = citiesList.get(rowView.getPos());
            rowView.setCityId(city.city_id);
            rowView.setCityName(city.city_name);
        }

        @Override
        public int getItemsCount() {
            if (citiesList == null) {
                return 0;
            } else {
                return citiesList.size();
            }
        }
    }
}
