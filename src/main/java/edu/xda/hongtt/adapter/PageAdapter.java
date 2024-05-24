package edu.xda.hongtt.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.xda.hongtt.fragment.KhoanThuFragment;
import edu.xda.hongtt.fragment.LoaiThuFragment;

public class PageAdapter extends FragmentPagerAdapter {
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new KhoanThuFragment();
            case 1:
                return new LoaiThuFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Khoản Thu";
            case 1:
                return "Loại Thu";
            default:
                return null;
        }
    }
}