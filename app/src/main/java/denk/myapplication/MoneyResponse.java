package denk.myapplication;

import com.google.gson.annotations.SerializedName;

public class MoneyResponse {

    @SerializedName("r030")
    public int id;

    @SerializedName("txt")
    public String currencyName;

    @SerializedName("rate")
    public Double currencyRate;

    @SerializedName("cc")
    public String currencyShortName;

    @SerializedName("exchangedate")
    public String exchangeDate;

}

