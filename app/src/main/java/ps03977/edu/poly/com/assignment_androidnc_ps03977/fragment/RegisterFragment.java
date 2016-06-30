package ps03977.edu.poly.com.assignment_androidnc_ps03977.fragment;


import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import java.sql.SQLClientInfoException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ps03977.edu.poly.com.assignment_androidnc_ps03977.R;
import ps03977.edu.poly.com.assignment_androidnc_ps03977.adapter.CourseAdapter;
import ps03977.edu.poly.com.assignment_androidnc_ps03977.model.Course;
import ps03977.edu.poly.com.assignment_androidnc_ps03977.sqllite.CourseDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    View view;
    FloatingActionButton fab;

    ArrayList<Course> courseList;
    EditText ed_name_course, ed_time_start, ed_time_end, ed_time_set, ed_slot, ed_note;
    private int mYear, mMonth, mDay, mHour, mMinute, mSeccon;
    ImageView img_date_start, img_date_end, img_date_set;
    private CourseAdapter courseAdapter;

    CourseDao dao;

    Course crtam;
    CircularProgressButton circularProgressButton;
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        ed_name_course = (EditText) rootView.findViewById(R.id.ed_name_course);
        ed_time_start = (EditText) rootView.findViewById(R.id.ed_datestart);
        ed_time_end = (EditText) rootView.findViewById(R.id.ed_dateend);
        ed_time_set = (EditText) rootView.findViewById(R.id.ed_timeset);
        ed_slot = (EditText) rootView.findViewById(R.id.ed_slot);
        ed_note = (EditText) rootView.findViewById(R.id.ed_note);

        courseList = new ArrayList<Course>();


        dao = new CourseDao(getActivity());

        img_date_start = (ImageView) rootView.findViewById(R.id.img_startdate);
        img_date_end = (ImageView) rootView.findViewById(R.id.img_enddate);
        img_date_set = (ImageView) rootView.findViewById(R.id.img_dateset);


        ed_time_start.setText(getDateTime());
        ed_time_end.setText(getDateTime());
        ed_time_set.setText(getTime());

        final Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        img_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpq = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        ed_time_start.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                dpq.show();
            }
        });
        img_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpq = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        ed_time_end.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                dpq.show();
            }
        });
        img_date_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                ed_time_set.setText((hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + " : "
                                        + (minute < 10 ? "0" + minute : minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        ed_name_course.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String name = ed_name_course.getText().toString();
                if (name.isEmpty()){
                    ed_name_course.setError("Tên khóa học không được để trống!");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = ed_name_course.getText().toString();
                if (name.isEmpty()){
                    ed_name_course.setError("Tên khóa học không được để trống!");
                    ed_name_course.startAnimation(shake);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed_slot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = ed_slot.getText().toString();
                if (name.isEmpty()){
                    ed_slot.setError("Tên khóa học không được để trống!");
                    ed_slot.startAnimation(shake);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = ed_slot.getText().toString();
                if (name.isEmpty()){
                    ed_slot.setError("Tên khóa học không được để trống!");
                }
            }
        });
        circularProgressButton = (CircularProgressButton) rootView.findViewById(R.id.btnWithText);
        circularProgressButton.setIndeterminateProgressMode(true);
        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (ed_name_course.getText().toString().isEmpty()) {
                        ed_name_course.setError("Tên khóa học không được để trống!");
                        simulateErrorProgress(circularProgressButton);
                        ed_name_course.startAnimation(shake);
                    } else if (ed_time_start.getText().toString().isEmpty()) {
                        ed_time_start.setError("Thời gian bắt đầu không được để trống!");
                        simulateErrorProgress(circularProgressButton);
                        ed_time_start.startAnimation(shake);
                    } else if (ed_time_end.getText().toString().isEmpty()) {
                        ed_time_end.setError("Thời gian kết thúc không được để trống!");
                        simulateErrorProgress(circularProgressButton);
                        ed_time_end.startAnimation(shake);
                    } else if (ed_time_set.getText().toString().isEmpty()) {
                        ed_time_set.setError("Thời gian học không được để trống!");
                        simulateErrorProgress(circularProgressButton);
                        ed_time_set.startAnimation(shake);
                    } else if (ed_slot.getText().toString().isEmpty()) {
                        ed_slot.setError("Buổi bắt đầu không được để trống!");
                        simulateErrorProgress(circularProgressButton);
                        ed_slot.startAnimation(shake);
                    }
                    Course cr = new Course();
                    simulateSuccessProgress(circularProgressButton);
                    cr.course = ed_name_course.getText().toString();
                    cr.timestart = ed_time_start.getText().toString();
                    cr.timeend = ed_time_end.getText().toString();
                    cr.timeset = ed_time_set.getText().toString();
                    cr.slot = Integer.parseInt(ed_slot.getText().toString());
                    cr.notes = ed_note.getText().toString();


                    dao.AddCourse(cr);
                    if (circularProgressButton.getText().toString().equalsIgnoreCase(String.valueOf(R.string.Complete))){
                        simulateNormalProgress(circularProgressButton);
                    }
                } catch (Exception e) {
                    SQLClientInfoException exception = new SQLClientInfoException();
                    Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "" + exception, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }


    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }

    private void simulateNormalProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99) {
                    button.setProgress(1);
                }
            }
        });
        widthAnimation.start();
    }

    private void simulateErrorProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99) {
                    button.setProgress(-1);
                }
            }
        });
        widthAnimation.start();
    }

    public void AddCourse() {

        final Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        try {
            if (ed_name_course.getText().toString().isEmpty()) {
                ed_name_course.setError("Tên khóa học không được để trống!");
                ed_name_course.startAnimation(shake);
            } else if (ed_time_start.getText().toString().isEmpty()) {
                ed_time_start.setError("Thời gian bắt đầu không được để trống!");
                ed_time_start.startAnimation(shake);
            } else if (ed_time_end.getText().toString().isEmpty()) {
                ed_time_end.setError("Thời gian kết thúc không được để trống!");
                ed_time_end.startAnimation(shake);
            } else if (ed_time_set.getText().toString().isEmpty()) {
                ed_time_set.setError("Thời gian học không được để trống!");
                ed_time_set.startAnimation(shake);
            } else if (ed_slot.getText().toString().isEmpty()) {
                ed_slot.setError("Buổi bắt đầu không được để trống!");
                ed_slot.startAnimation(shake);
            }
            Course cr = new Course();

            cr.course = ed_name_course.getText().toString();
            cr.timestart = ed_time_start.getText().toString();
            cr.timeend = ed_time_end.getText().toString();
            cr.timeset = ed_time_set.getText().toString();
            cr.slot = Integer.parseInt(ed_slot.getText().toString());
            cr.notes = ed_note.getText().toString();

            dao.AddCourse(cr);
//            SQLClientInfoException exception = new SQLClientInfoException();
//            Toast.makeText(getActivity(), "Thêm " + ed_name_course.getText().toString() + "thành công!", Toast.LENGTH_SHORT).show();
//            Toast.makeText(getActivity(),""+ exception, Toast.LENGTH_LONG).show();

            show();
        } catch (Exception e) {
            SQLClientInfoException exception = new SQLClientInfoException();
            Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "" + exception, Toast.LENGTH_SHORT).show();
        }
    }

    public void show() {
        courseList = dao.getCourse();
        courseAdapter = new CourseAdapter(getActivity(), courseList);
        courseAdapter.notifyDataSetChanged();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}
