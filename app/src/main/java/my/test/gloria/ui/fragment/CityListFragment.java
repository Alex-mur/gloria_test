package my.test.gloria.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import my.test.gloria.R;
import my.test.gloria.mvp.presenter.CityListPresenter;
import my.test.gloria.mvp.view.CityListView;
import my.test.gloria.ui.adapter.CityListRVAdapter;

public class CityListFragment extends MvpAppCompatFragment implements CityListView {

    @InjectPresenter
    CityListPresenter presenter;

    @BindView((R.id.fragment_city_list__rv_cities))
    RecyclerView rvCities;
    @BindView(R.id.fragment_city_list__tv_loading)
    TextView tvLoading;

    private Unbinder unbinder;
    private CityListRVAdapter citiesAdapter;

    public CityListFragment() {
    }

    public static CityListFragment getInstance() {
        return new CityListFragment();
    }

    @ProvidePresenter
    public CityListPresenter provideCityListPresenter() {
        return new CityListPresenter(AndroidSchedulers.mainThread());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRVCities();
        return view;
    }

    private void initRVCities() {
        rvCities.setLayoutManager(new LinearLayoutManager(getContext()));
        citiesAdapter = new CityListRVAdapter(presenter.cityListRVPresenter);
        rvCities.setAdapter(citiesAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void updateList() {
        citiesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingMessage() {
        tvLoading.setText(R.string.loading_text);
        tvLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMessage(String text) {
        tvLoading.setText(text);
        tvLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMessage() {
        tvLoading.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
