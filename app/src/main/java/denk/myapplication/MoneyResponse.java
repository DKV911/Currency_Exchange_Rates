package denk.myapplication;

import com.google.gson.annotations.SerializedName;

public class MoneyResponse {

    @SerializedName("r030")
    public String id;

    @SerializedName("txt")
    public String currencyName;

    @SerializedName("rate")
    public String currencyRate;

    @SerializedName("cc")
    public String currencyShortName;

    @SerializedName("exchangedate")
    public String exchangeDate;

    public MoneyResponse(String id, String currencyName, String currencyRate, String currencyShortName, String exchangeDate) {
        this.id = id;
        this.currencyName = currencyName;
        this.currencyRate = currencyRate;
        this.currencyShortName = currencyShortName;
        this.exchangeDate = exchangeDate;
    }
}

