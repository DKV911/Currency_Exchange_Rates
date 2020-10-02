package denk.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetAPI {
    @GET("NBUStatService/v1/statdirectory/exchange?json")
    Call<List<MoneyResponse>> getExchangesRate();

    @GET("NBUStatService/v1/statdirectory/exchange?date=20201002")
    Call<List<MoneyResponse>> getExchangesRateForDate();


}
