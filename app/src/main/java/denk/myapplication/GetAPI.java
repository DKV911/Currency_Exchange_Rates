package denk.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetAPI {
//    @GET("NBUStatService/v1/statdirectory/exchange?json")
//    Call<List<MoneyResponse>> getExchangesRate();

    @GET("NBUStatService/v1/statdirectory/exchange?date&json")
    Call<List<MoneyResponse>> getExchangesRateForDate(@Query("date") String date);



}
