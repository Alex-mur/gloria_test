package my.test.gloria.ui;

import android.app.Application;

import my.test.gloria.di.AppComponent;
import my.test.gloria.di.DaggerAppComponent;
import my.test.gloria.di.modules.AppModule;

public class App extends Application {
    private static App instance;
    private AppComponent appComponent;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
