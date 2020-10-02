package denk.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MoneyAdapter extends RecyclerView.Adapter<MoneyAdapter.MoneyViewHolder> {

    List<MoneyResponse> moneyList;
    public MoneyAdapter(List<MoneyResponse> moneyList) {
        this.moneyList = moneyList;
    }

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int layoutId = R.layout.exchange_rate;

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutId,parent,false);

        return new MoneyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
        holder.bind(moneyList.get(position));
    }

    @Override
    public int getItemCount() {
        return moneyList.size();
    }

    public class MoneyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_full_title, tv_abbreviation, tv_rate, tv_date;

        public MoneyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_full_title = itemView.findViewById(R.id.tv_full_title);
            tv_abbreviation = itemView.findViewById(R.id.tv_abbreviation);
            tv_rate = itemView.findViewById(R.id.tv_rate);
            tv_date = itemView.findViewById(R.id.tv_date);
        }

        public void bind(MoneyResponse item){
            tv_rate.setText(item.currencyRate);
            tv_abbreviation.setText(item.currencyShortName);
            tv_full_title.setText(item.currencyName);
            tv_date.setText(item.exchangeDate);
        }
    }
}
