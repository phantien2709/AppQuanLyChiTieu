package edu.xda.hongtt.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.PageLSXoaAdapter;


public class LichSuXoaFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_lich_su_xoa, null);
        TabLayout tabLayout = v.findViewById(R.id.main_tabXoaChi);
        ViewPager viewPager = v.findViewById(R.id.main_viewpagerChi);
        PageLSXoaAdapter pageLSXoaAdapter = new PageLSXoaAdapter(getChildFragmentManager());
        viewPager.setAdapter(pageLSXoaAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        return v;
    }
}