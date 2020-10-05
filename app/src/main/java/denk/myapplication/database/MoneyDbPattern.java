package denk.myapplication.database;

public class MoneyDbPattern {
    public static final class MoneyTable {
        public static final String TABLE_NAME = "exchange_rates";

        public static final class Cols {
            public static final String COLUMN_ID = "_id";
            public static final String COLUMN_R030_ID = "r030";
            public static final String COLUMN_NAME = "name";
            public static final String COLUMN_RATE = "rate";
            public static final String COLUMN_SHORT_NAME = "short_name";
            public static final String COLUMN_DATE = "date";
        }
    }
}
