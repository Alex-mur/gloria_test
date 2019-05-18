package my.test.gloria.mvp.model.api;

import io.reactivex.Single;
import my.test.gloria.mvp.model.entity.CitiesResponse;
import retrofit2.http.GET;


public interface IDataSource {

    @GET("taxik/api/test")
    Single<CitiesResponse> getCities();
}
