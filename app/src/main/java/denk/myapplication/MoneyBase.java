package denk.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import denk.myapplication.database.MoneyDBH;
import denk.myapplication.database.MoneyDbPattern;

import static denk.myapplication.database.MoneyDbPattern.MoneyTable;

public class MoneyBase {
    public static MoneyBase sMoneyBase;

    public static List<MoneyResponse> mMoney;
    public static Context mContext;
    public static SQLiteDatabase mDatabase;

    private static final String[] projection = {
            MoneyTable.Cols.COLUMN_R030_ID,
            MoneyTable.Cols.COLUMN_NAME,
            MoneyTable.Cols.COLUMN_RATE,
            MoneyTable.Cols.COLUMN_SHORT_NAME,
            MoneyTable.Cols.COLUMN_DATE
    };

    public MoneyBase(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new MoneyDBH(mContext).getWritableDatabase();
//        mMoney = new ArrayList<>();
    }

    public static List<MoneyResponse> getsMoneyFromBase(){
        Cursor cursor = mDatabase.query(MoneyTable.TABLE_NAME,projection,null,
                null,null,null,null);
        List <MoneyResponse> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(MoneyTable.Cols.COLUMN_R030_ID));
            String currencyName = cursor.getString(cursor.getColumnIndexOrThrow(MoneyTable.Cols.COLUMN_NAME));
            String currencyRate = cursor.getString(cursor.getColumnIndexOrThrow(MoneyTable.Cols.COLUMN_RATE));
            String currencyShortName = cursor.getString(cursor.getColumnIndexOrThrow(MoneyTable.Cols.COLUMN_SHORT_NAME));
            String exchangeDate = cursor.getString(cursor.getColumnIndexOrThrow(MoneyTable.Cols.COLUMN_DATE));
            list.add(new MoneyResponse(id,currencyName,currencyRate,currencyShortName,exchangeDate)); }
        cursor.close();
        return list;
    }

    private static ContentValues getContentValues(MoneyResponse item){
        ContentValues moneyContentValues = new ContentValues();
        moneyContentValues.put(MoneyTable.Cols.COLUMN_ID,(item.exchangeDate+item.id).toString());
        moneyContentValues.put(MoneyTable.Cols.COLUMN_R030_ID, item.id);
        moneyContentValues.put(MoneyTable.Cols.COLUMN_NAME, item.currencyName.toString());
        moneyContentValues.put(MoneyTable.Cols.COLUMN_RATE, item.currencyRate.toString());
        moneyContentValues.put(MoneyTable.Cols.COLUMN_SHORT_NAME, item.currencyShortName.toString());
        moneyContentValues.put(MoneyTable.Cols.COLUMN_DATE, item.exchangeDate.toString());
        return moneyContentValues;
    }

    public static void addMoneyToBase(MoneyResponse c){
        ContentValues value = getContentValues(c);
        mDatabase.insert(MoneyTable.TABLE_NAME,null,value);
    }
}
