package my.test.gloria.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import my.test.gloria.R;
import my.test.gloria.navigation.Screens;
import my.test.gloria.ui.App;
import my.test.gloria.ui.fragment.MapFragment;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Screen;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

public class MainActivity extends AppCompatActivity {

    @Inject
    NavigatorHolder navigatorHolder;

    private Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this);
        initNavigation(savedInstanceState);
    }

    private void initNavigation(Bundle savedInstanceState) {
        navigator = new SupportAppNavigator(this, R.id.activity_main__frame);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main__frame);
        if (savedInstanceState == null && fragment == null) {
            changeScreen(new Screens.CityListScreen());
        }
    }

    private void changeScreen(Screen screen) {
        Command[] commands = {new Replace(screen)};
        navigator.applyCommands(commands);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main__frame);
        if (fragment instanceof MapFragment) {
            ((MapFragment) fragment).onBackPressed();
        } else super.onBackPressed();
    }
}
