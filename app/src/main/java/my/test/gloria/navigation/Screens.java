package my.test.gloria.navigation;

import android.support.v4.app.Fragment;

import my.test.gloria.ui.fragment.CityListFragment;
import my.test.gloria.ui.fragment.MapFragment;
import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static class CityListScreen extends SupportAppScreen {
        public CityListScreen() {
        }

        @Override
        public Fragment getFragment() {
            return CityListFragment.getInstance();
        }
    }

    public static class MapScreen extends SupportAppScreen {

        private String city;
        private double lat;
        private double lon;

        public MapScreen(String city, double lat, double lon) {
            this.city = city;
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        public Fragment getFragment() {
            return MapFragment.getInstance(city, lat, lon);
        }
    }
}
