package denk.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    static StringBuilder selectedDate = new StringBuilder();
    static StringBuilder dateWithDot = new StringBuilder();
    DatePickerDialog.OnDateSetListener listener;
    MoneyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint("SimpleDateFormat") final String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        final View loader = findViewById(R.id.loader);
        final RecyclerView currencyRecyclerView = findViewById(R.id.rv_item);
        final FloatingActionButton btn_calendar = findViewById(R.id.btn_calendar);

        new MoneyBase(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        currencyRecyclerView.setLayoutManager(layoutManager);
        currencyRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        if (MoneyBase.checkToGet(dateToGetFromDB(currentDate))) {
            Toast.makeText(this, "LOADING FROM DATABASE\n" + dateToGetFromDB(currentDate), Toast.LENGTH_LONG).show();
            adapter = new MoneyAdapter(MoneyBase.getsMoneyFromBase(dateToGetFromDB(currentDate)));
            loader.setVisibility(View.INVISIBLE);
            btn_calendar.setVisibility(View.VISIBLE);
            currencyRecyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this,"DOWNLOADING FROM THE INTERNET\n"+dateToGetFromDB(currentDate),Toast.LENGTH_LONG).show();
            MyApp.getInstance().getAPI.getExchangesRateForDate(currentDate).enqueue(new Callback<List<MoneyResponse>>() {
                @Override
                public void onResponse(Call<List<MoneyResponse>> call, Response<List<MoneyResponse>> response) {
                    loader.setVisibility(View.INVISIBLE);
                    btn_calendar.setVisibility(View.VISIBLE);

                    if (response.code() == 200 && response.isSuccessful()) {
                        List<MoneyResponse> moneyResponseList = response.body();
                        adapter = new MoneyAdapter(moneyResponseList);
                        currencyRecyclerView.setAdapter(adapter);

                        assert moneyResponseList != null;
                        for (MoneyResponse item : moneyResponseList) {
                            MoneyBase.addMoneyToBase(item);
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<MoneyResponse>> call, Throwable t) {
                    loader.setVisibility(View.INVISIBLE);
                    btn_calendar.setVisibility(View.VISIBLE);
                    t.printStackTrace();
                    Toast.makeText(MainActivity.this, "FAILURE\n" + t.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
                }

            });
        }
        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (!selectedDate.toString().isEmpty())
                    selectedDate.delete(0, selectedDate.length());
                selectedDate.append(year);
                if (month + 1 < 10) {
                    selectedDate.append("0");
                }
                selectedDate.append(month + 1);
                if (dayOfMonth < 10) {
                    selectedDate.append("0");
                }
                selectedDate.append(dayOfMonth);

                loader.setVisibility(View.VISIBLE);
                btn_calendar.setVisibility(View.INVISIBLE);

                if (MoneyBase.checkToGet(dateToGetFromDB(selectedDate.toString()))) {
                    Toast.makeText(MainActivity.this, "LOADING FROM DATABASE\n" + dateToGetFromDB(selectedDate.toString()), Toast.LENGTH_LONG).show();
                    adapter = new MoneyAdapter(MoneyBase.getsMoneyFromBase(dateToGetFromDB(selectedDate.toString())));
                    loader.setVisibility(View.INVISIBLE);
                    btn_calendar.setVisibility(View.VISIBLE);
                    currencyRecyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this,"DOWNLOADING FROM THE INTERNET\n"+dateToGetFromDB(selectedDate.toString()),Toast.LENGTH_LONG).show();
                    MyApp.getInstance().getAPI.getExchangesRateForDate(selectedDate.toString()).enqueue(new Callback<List<MoneyResponse>>() {
                        @Override
                        public void onResponse(Call<List<MoneyResponse>> call, Response<List<MoneyResponse>> response) {

                            loader.setVisibility(View.INVISIBLE);
                            btn_calendar.setVisibility(View.VISIBLE);

                            if (response.code() == 200 && response.isSuccessful()) {
                                List<MoneyResponse> moneyResponseList = response.body();
                                adapter = new MoneyAdapter(moneyResponseList);
                                currencyRecyclerView.setAdapter(adapter);

                                for (MoneyResponse item : moneyResponseList) {
                                    MoneyBase.addMoneyToBase(item);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<MoneyResponse>> call, Throwable t) {
                            loader.setVisibility(View.INVISIBLE);
                            btn_calendar.setVisibility(View.VISIBLE);
                            t.printStackTrace();
                            Toast.makeText(MainActivity.this, "FAILURE\n" + t.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        };
    }

    public void showDatePickerDialog(View view) {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, listener,
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public static String dateToGetFromDB(String date) {
        if (!dateWithDot.toString().isEmpty())
            dateWithDot.delete(0, dateWithDot.length());
        dateWithDot.append(date.substring(6, 8));
        dateWithDot.append(".");
        dateWithDot.append(date.substring(4, 6));
        dateWithDot.append(".");
        dateWithDot.append(date.substring(0, 4));
        return dateWithDot.toString();
    }
}
