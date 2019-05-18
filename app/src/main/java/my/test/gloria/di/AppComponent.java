package my.test.gloria.di;


import javax.inject.Singleton;

import dagger.Component;
import my.test.gloria.di.modules.ApiModule;
import my.test.gloria.di.modules.AppModule;
import my.test.gloria.di.modules.CiceroneModule;
import my.test.gloria.mvp.presenter.CityListPresenter;
import my.test.gloria.mvp.presenter.MapPresenter;
import my.test.gloria.ui.activity.MainActivity;


@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        CiceroneModule.class
})

public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(CityListPresenter cityListPresenter);

    void inject(MapPresenter mapPresenter);
}
