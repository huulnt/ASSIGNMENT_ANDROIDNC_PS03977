package ps03977.edu.poly.com.assignment_androidnc_ps03977.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nhan2 on 6/30/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";
    public static final String DB_NAME = "AndroidNC";
    public static final int DB_VERSION = 1;

    public static final String COURSE = "course";
    public static final String TIMESTART= "timestart";
    public static final String TIMEND = "timeend";
    public static final String TIMESET= "timeset";
    public static final String SLOT= "slot";
    public static final String NOTES= "notes";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table fcourse (" +
                "id integer primary key autoincrement, " +
                "course text not null," +
                "timestart text not null," +
                "timeend text not null," +
                "timeset text not null," +
                "slot int not null," +
                "notes text" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS fcourse";
        db.execSQL(sql);
        onCreate(db);
    }
}
