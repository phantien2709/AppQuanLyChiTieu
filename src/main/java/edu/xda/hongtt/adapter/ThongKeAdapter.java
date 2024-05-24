package edu.xda.hongtt.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.xda.hongtt.fragment.HomNayFragment;
import edu.xda.hongtt.fragment.NamFragment;
import edu.xda.hongtt.fragment.ThangFragment;

public class ThongKeAdapter extends FragmentPagerAdapter {
    public ThongKeAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomNayFragment();
            case 1:
                return new ThangFragment();
            case 2:
                return new NamFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Hôm nay";
            case 1:
                return "Tháng";
            case 2:
                return "Năm";
            default:
                return null;
        }
    }
}
