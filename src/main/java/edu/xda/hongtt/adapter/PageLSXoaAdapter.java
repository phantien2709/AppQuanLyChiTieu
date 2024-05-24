package edu.xda.hongtt.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.xda.hongtt.fragment.KhoanChiFragment;
import edu.xda.hongtt.fragment.LSXoaLoaiChiFragment;
import edu.xda.hongtt.fragment.LSXoaLoaiThuFragment;
import edu.xda.hongtt.fragment.LichSuXoaChiFragment;
import edu.xda.hongtt.fragment.LichSuXoaThuFragment;
import edu.xda.hongtt.fragment.LoaiChiFragment;

public class PageLSXoaAdapter extends FragmentPagerAdapter {

    public PageLSXoaAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LSXoaLoaiChiFragment();
            case 1:
                return new LichSuXoaChiFragment();
            case 2:
                return new LSXoaLoaiThuFragment();
            case 3:
                return new LichSuXoaThuFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "loại chi";
            case 1:
                return "khoản chi";
            case 2:
                return "loại thu";
            case 3:
                return "khoản thu";
            default:
                return null;
        }
    }
}
