package my.test.gloria.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import my.test.gloria.mvp.view.MapView;
import my.test.gloria.ui.App;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {

    @Inject
    Router router;

    private String cityName;
    private double lat;
    private double lon;

    public MapPresenter() {
        App.getInstance().getAppComponent().inject(this);
    }

    public void onBackPressed() {
        router.exit();
    }

    public void onResume() {
    }

    public void onMapReady() {
        getViewState().addPositionOnMap(cityName, lat, lon);
    }

    public void onStop() {
    }

    public void setCurrentPosition(String cityName, double lat, double lon) {
        this.cityName = cityName;
        this.lat = lat;
        this.lon = lon;
    }
}
