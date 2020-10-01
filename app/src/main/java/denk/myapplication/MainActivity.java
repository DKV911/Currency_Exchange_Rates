package denk.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View loader = findViewById(R.id.loader);
        final RecyclerView currencyRecyclerView = findViewById(R.id.rv_item);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        currencyRecyclerView.setLayoutManager(layoutManager);
        currencyRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        MyApp.getInstance().getAPI.getExchangesRate().enqueue(new Callback<List<MoneyResponse>>() {
            @Override
            public void onResponse(Call<List<MoneyResponse>> call, Response<List<MoneyResponse>> response) {
                loader.setVisibility(View.GONE);

                if (response.code() == 200 && response.isSuccessful()) {
                    List<MoneyResponse> moneyResponseList = response.body();
                    MoneyAdapter adapter = new MoneyAdapter(moneyResponseList);
                    currencyRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<MoneyResponse>> call, Throwable t) {
                loader.setVisibility(View.GONE);
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "FAILURE" + t.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
            }
        });
    }
}