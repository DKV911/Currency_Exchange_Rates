package denk.myapplication;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApp extends Application {
    public GetAPI getAPI;
    public static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        initRetrofit();
    }

    public static MyApp getInstance(){
        return instance;
    }
    public void initRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bank.gov.ua/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getAPI = retrofit.create(GetAPI.class);
    }
}
