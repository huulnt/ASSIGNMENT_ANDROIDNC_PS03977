package ps03977.edu.poly.com.assignment_androidnc_ps03977.library;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ps03977.edu.poly.com.assignment_androidnc_ps03977.fragment.RegisterFragment;
import ps03977.edu.poly.com.assignment_androidnc_ps03977.fragment.CourseFragment;

/**
 * Created by nhan2 on 6/30/2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final  List<String> mFragmentTitleList  = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentList.add(new CourseFragment());
        mFragmentList.add(new RegisterFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
