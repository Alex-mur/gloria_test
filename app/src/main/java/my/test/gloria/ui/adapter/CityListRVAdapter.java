package my.test.gloria.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.test.gloria.R;
import my.test.gloria.mvp.presenter.list.ICityListRVPresenter;
import my.test.gloria.mvp.view.listItems.CityListRVItemView;

public class CityListRVAdapter extends RecyclerView.Adapter<CityListRVAdapter.ViewHolder> {

    ICityListRVPresenter presenter;

    public CityListRVAdapter(ICityListRVPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parentViewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(parentViewGroup.getContext())
                .inflate(R.layout.fragment_city_list_rv_item, parentViewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RxView.clicks(holder.itemView)
                .map(obj -> holder)
                .subscribe(presenter.getClickSubject());
        holder.pos = position;
        presenter.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemsCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CityListRVItemView {

        int pos = 0;

        @BindView(R.id.fragment_city_list_rv_item__tv_city_id)
        TextView tvCityId;
        @BindView(R.id.fragment_city_list_rv_item__tv_city_name)
        TextView tvCityName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public int getPos() {
            return pos;
        }

        @Override
        public void setCityId(int cityId) {
            tvCityId.setText(String.valueOf(cityId));
        }

        @Override
        public void setCityName(String cityName) {
            tvCityName.setText(cityName);
        }
    }

}
