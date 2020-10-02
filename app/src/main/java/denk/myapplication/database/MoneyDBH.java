package denk.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import denk.myapplication.database.MoneyDbPattern.MoneyTable;

public class MoneyDBH extends SQLiteOpenHelper {
    private static final String DB_NAME = "moneys.db";
    private static final int DB_VERSION = 1;

    public MoneyDBH(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MoneyTable.TABLE_NAME + "(" +
                MoneyTable.Cols.COLUMN_ID + ", " +
                MoneyTable.Cols.COLUMN_R030_ID + ", " +
                MoneyTable.Cols.COLUMN_NAME + ", " +
                MoneyTable.Cols.COLUMN_RATE + ", " +
                MoneyTable.Cols.COLUMN_SHORT_NAME + ", " +
                MoneyTable.Cols.COLUMN_DATE  +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
