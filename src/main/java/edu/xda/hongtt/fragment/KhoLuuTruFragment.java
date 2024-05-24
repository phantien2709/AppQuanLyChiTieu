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
import edu.xda.hongtt.adapter.PageLuuTru;

public class KhoLuuTruFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_kho_luu_tru, container, false);

        TabLayout tabLayout = view.findViewById(R.id.main_tabKhoLuuTru);
        ViewPager viewPager = view.findViewById(R.id.main_viewpagerKhoLuuTru);
        PageLuuTru pageLuuTru = new PageLuuTru(getChildFragmentManager());
        viewPager.setAdapter(pageLuuTru);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
       return view;
    }
}