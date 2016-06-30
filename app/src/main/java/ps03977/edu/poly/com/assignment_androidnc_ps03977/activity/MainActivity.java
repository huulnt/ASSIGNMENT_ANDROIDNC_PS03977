package ps03977.edu.poly.com.assignment_androidnc_ps03977.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import ps03977.edu.poly.com.assignment_androidnc_ps03977.R;

public class MainActivity extends AppCompatActivity {
    CardView cv_course, cv_maps,cv_news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cv_course = (CardView) findViewById(R.id.cv_course);
        cv_maps = (CardView) findViewById(R.id.cv_maps);
        cv_news = (CardView) findViewById(R.id.cv_news);
        cv_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MyCourse.class);
                startActivity(i);
            }
        });
        cv_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });
        cv_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(i);
            }
        });
    }
}
