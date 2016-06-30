package ps03977.edu.poly.com.assignment_androidnc_ps03977.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ps03977.edu.poly.com.assignment_androidnc_ps03977.R;
import ps03977.edu.poly.com.assignment_androidnc_ps03977.model.Course;

/**
 * Created by nhan2 on 6/30/2016.
 */
public class CourseAdapter extends ArrayAdapter<Course> {
    private Activity activity;
    private List<Course> courseList;
    Course tc;


    public CourseAdapter(Activity activity, List<Course> courseList) {
        super(activity, R.layout.list_course_item, courseList);
        this.activity = activity;
        this.courseList = courseList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View inf = inflater.inflate(R.layout.list_course_item, null, true);
        TextView tv_name, tv_start_date, tv_end_date, tv_set_time, tv_slot, tv_note;

        tc = courseList.get(position);

        tv_name = (TextView) inf.findViewById(R.id.tv_name);
        tv_start_date = (TextView) inf.findViewById(R.id.tv_dateStart);
        tv_end_date = (TextView) inf.findViewById(R.id.tv_dateEnd);
        tv_set_time = (TextView) inf.findViewById(R.id.tv_timeset);
        tv_slot = (TextView) inf.findViewById(R.id.tv_slot);
        tv_note = (TextView) inf.findViewById(R.id.tv_note);

        tv_name.setText("Tên khóa học: " + tc.course);
        tv_start_date.setText("Thời gian BD: " + tc.timestart);
        tv_end_date.setText("Thời gian KT: " + tc.timeend);
        tv_set_time.setText("Time: " +tc.timeset);
        tv_slot.setText("Buổi: " + String.valueOf(tc.slot));
        tv_note.setText("Ghi Chú: " +tc.notes);




        return inf;
    }
}
