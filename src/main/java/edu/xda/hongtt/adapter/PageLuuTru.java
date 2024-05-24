package edu.xda.hongtt.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.xda.hongtt.fragment.KhoChatFragment;
import edu.xda.hongtt.fragment.KhoSuKienFragment;


public class PageLuuTru extends FragmentPagerAdapter {
    public PageLuuTru(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new KhoChatFragment();
            case 1:
                return new KhoSuKienFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Tin nhắn ";
            case 1:
                return "Sự kiện";
            default:
                return null;
        }
    }
}
