package ps03977.edu.poly.com.assignment_androidnc_ps03977.fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
public class CourseFragment extends Fragment {
    View view;
    FloatingActionButton fab;

    ArrayList<Course> courseList;
    EditText ed_name_course, ed_time_start, ed_time_end, ed_time_set, ed_slot, ed_note;
    private int mYear, mMonth, mDay, mHour, mMinute, mSeccon;
    ImageView img_date_start, img_date_end, img_date_set;
    private CourseAdapter courseAdapter;
    Button btn_update;

    CourseDao dao;

    Course crtam;
    ListView lv_course;

    public CourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_course, container, false);
        dao = new CourseDao(getActivity());

        ed_name_course = (EditText) rootView.findViewById(R.id.ed_name_course);
        ed_time_start = (EditText) rootView.findViewById(R.id.ed_datestart);
        ed_time_end = (EditText) rootView.findViewById(R.id.ed_dateend);
        ed_time_set = (EditText) rootView.findViewById(R.id.ed_timeset);
        ed_slot = (EditText) rootView.findViewById(R.id.ed_slot);
        ed_note = (EditText) rootView.findViewById(R.id.ed_note);

        courseList = new ArrayList<Course>();


        lv_course = (ListView) rootView.findViewById(R.id.lv_course);
        img_date_start = (ImageView) rootView.findViewById(R.id.img_startdate);
        img_date_end = (ImageView) rootView.findViewById(R.id.img_enddate);
        img_date_set = (ImageView) rootView.findViewById(R.id.img_dateset);

        btn_update = (Button) rootView.findViewById(R.id.btn_update);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        registerForContextMenu(lv_course);

       /* fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustiomDialog();
            }
        });
*/
        // Inflate the layout for this fragment
        String date = getDateTime();
        Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
        show();
        return rootView;
    }

    public void show() {
        courseList = dao.getCourse();
        courseAdapter = new CourseAdapter(getActivity(), courseList);
        lv_course.setAdapter(courseAdapter);
        courseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context, menu);
        menu.setHeaderTitle("Quản lý Khóa học");
        menu.setHeaderIcon(R.drawable.people);
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int vitri = info.position;

        final   Course cr = courseList.get(vitri);

        crtam = cr;

        switch (item.getItemId()) {
            case R.id.item_sua:
                dao.EditCourse(cr);
                EditDialog();
                show();
                break;
            case R.id.item_xoa:

                AlertDialog.Builder bui = new AlertDialog.Builder(getActivity());
                bui.setIcon(R.drawable.ic_error);
                bui.setTitle("Bạn muốn xóa !");
                bui.setMessage("Bạn muốn xóa khóa học này?");

                bui.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.DelCourse(cr);
                        show();
                        Toast.makeText(getActivity(), "Xóa thành công khóa học số : " + cr.id + "!", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                bui.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                bui.show();
                break;
            case R.id.item_xoa_all:
                AlertDialog.Builder bui2 = new AlertDialog.Builder(getActivity());
                bui2.setIcon(R.drawable.ic_error);
                bui2.setTitle("Bạn muốn xóa !");
                bui2.setMessage("Bạn muốn xóa tất cả khóa học?");

                bui2.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dao.DelAllData();
                        show();
                    }
                });
                bui2.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
                bui2.show();
                break;

        }
        return super.onContextItemSelected(item);
    }

    public void looi()
    {
        CustiomDialog();
    }

    public void CustiomDialog() {
        AlertDialog.Builder bui = new AlertDialog.Builder(getActivity());
        final LayoutInflater inf = getActivity().getLayoutInflater();
        view = inf.inflate(R.layout.add_course, null);

        ed_name_course = (EditText) view.findViewById(R.id.ed_name_course);
        ed_time_start = (EditText) view.findViewById(R.id.ed_datestart);
        ed_time_end = (EditText) view.findViewById(R.id.ed_dateend);
        ed_time_set = (EditText) view.findViewById(R.id.ed_timeset);
        ed_slot = (EditText) view.findViewById(R.id.ed_slot);
        ed_note = (EditText) view.findViewById(R.id.ed_note);

        img_date_start = (ImageView) view.findViewById(R.id.img_startdate);
        img_date_end = (ImageView) view.findViewById(R.id.img_enddate);
        img_date_set = (ImageView) view.findViewById(R.id.img_dateset);
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
        ed_time_start.setText(getDateTime());
        ed_time_end.setText(getDateTime());
        ed_time_set.setText(getTime());
        final Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        bui.setView(view);
        bui.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                    SQLClientInfoException exception = new SQLClientInfoException();
                    Toast.makeText(getActivity(), "Thêm " + ed_name_course.getText().toString() + "thành công!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),""+ exception, Toast.LENGTH_SHORT).show();

                    show();
                } catch (Exception e) {
                    SQLClientInfoException exception = new SQLClientInfoException();
                    Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "" + exception, Toast.LENGTH_SHORT).show();
                    CustiomDialog();
                }
            }
        });
        bui.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        bui.show();
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


    public void EditDialog() {
        AlertDialog.Builder bui = new AlertDialog.Builder(getActivity());
        LayoutInflater inf = getActivity().getLayoutInflater();
        view = inf.inflate(R.layout.add_course, null);

        TextView title = (TextView) view.findViewById(R.id.txt_name_add_student);
        title.setText("Sửa khóa học");
        ed_name_course = (EditText) view.findViewById(R.id.ed_name_course);
        ed_time_start = (EditText) view.findViewById(R.id.ed_datestart);
        ed_time_end = (EditText) view.findViewById(R.id.ed_dateend);
        ed_time_set = (EditText) view.findViewById(R.id.ed_timeset);
        ed_slot = (EditText) view.findViewById(R.id.ed_slot);
        ed_note = (EditText) view.findViewById(R.id.ed_note);

        ed_name_course.setText(crtam.course + "");
        ed_time_start.setText(crtam.timestart + "");
        ed_time_end.setText(crtam.timeend + "");
        ed_time_set.setText(crtam.timeset + "");
        ed_slot.setText(String.valueOf(crtam.slot) + "");
        ed_note.setText(crtam.notes + "");

        img_date_start = (ImageView) view.findViewById(R.id.img_startdate);
        img_date_end = (ImageView) view.findViewById(R.id.img_enddate);
        img_date_set = (ImageView) view.findViewById(R.id.img_dateset);
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
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpq = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        ed_time_set.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                dpq.show();
            }
        });
        bui.setView(view);
        bui.setNegativeButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Course cr = new Course();

                cr.id = crtam.id;
                cr.course = ed_name_course.getText().toString();
                cr.timestart = ed_time_start.getText().toString();
                cr.timeend = ed_time_end.getText().toString();
                cr.timeset = ed_time_set.getText().toString();
                cr.slot = Integer.parseInt(ed_slot.getText().toString());
                cr.notes = ed_note.getText().toString();

                dao.EditCourse(cr);

                show();
                Toast.makeText(getActivity(), "Sửa " + crtam.id + " thành công!", Toast.LENGTH_SHORT).show();

            }
        }).setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        bui.show();
    }

    

}
