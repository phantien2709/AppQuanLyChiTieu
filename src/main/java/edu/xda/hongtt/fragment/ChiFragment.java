package edu.xda.hongtt.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.PageChiAdapter;

public class ChiFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chi,null);
        final TabLayout tabLayout = v.findViewById(R.id.main_tabChi);
        final ViewPager viewPager = v.findViewById(R.id.main_viewpagerChi);
        PageChiAdapter pageAdapter = new PageChiAdapter(getChildFragmentManager());
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        return v;
    }
}
