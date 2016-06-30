package ps03977.edu.poly.com.assignment_androidnc_ps03977.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ps03977.edu.poly.com.assignment_androidnc_ps03977.model.Course;

/**
 * Created by nhan2 on 6/30/2016.
 */
public class CourseDao {
    private SQLiteDatabase db;
    private SQLiteDatabase dr;

    public static final String DB_NAME = "AndroidNC";
    public static final String ID = "id";
    public static final String COURSE = "course";
    public static final String TIMESTART = "timestart";
    public static final String TIMEND = "timeend";
    public static final String TIMESET = "timeset";
    public static final String SLOT = "slot";
    public static final String NOTES = "notes";

    public CourseDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        dr = dbHelper.getReadableDatabase();
    }


    public long AddCourse(Course cr) {
        ContentValues values = new ContentValues();

        values.put(COURSE, cr.course);
        values.put(TIMESTART, cr.timestart);
        values.put(TIMEND, cr.timeend);
        values.put(TIMESET, cr.timeset);
        values.put(SLOT, cr.slot);
        values.put(NOTES, cr.notes);

        return db.insert("fcourse", null, values);
    }

    public long EditCourse(Course cr) {
        ContentValues values = new ContentValues();

        values.put(ID, cr.id);
        values.put(COURSE, cr.course);
        values.put(TIMESTART, cr.timestart);
        values.put(TIMEND, cr.timeend);
        values.put(TIMESET, cr.timeset);
        values.put(SLOT, cr.slot);
        values.put(NOTES, cr.notes);

        return  db.update("fcourse", values, "id=?", new String[] { String.valueOf(cr.id) });

    }
    public long DelAllData(){
        return db.delete("fcourse", null, null);
    }

    public long DelCourse(Course course) {
        return db.delete("fcourse", "id=?", new String[]{String.valueOf(course.id)});
    }

    public ArrayList<Course> getCourse() {
        ArrayList<Course> cr = new ArrayList<Course>();
        String sql = "select * from fcourse";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToNext()) {
            do {
                Course course = new Course();
                course.id = c.getInt(c.getColumnIndex("id"));
                course.course = c.getString(c.getColumnIndex("course"));
                course.timestart = c.getString(c.getColumnIndex("timestart"));
                course.timeend = c.getString(c.getColumnIndex("timeend"));
                course.timeset = c.getString(c.getColumnIndex("timeset"));
                course.slot = c.getInt(c.getColumnIndex("slot"));
                course.notes = c.getString(c.getColumnIndex("notes"));

                cr.add(course);
            } while (c.moveToNext());
        }
        return cr;
    }
}
