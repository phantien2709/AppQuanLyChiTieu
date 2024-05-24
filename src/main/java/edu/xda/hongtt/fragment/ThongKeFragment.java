package edu.xda.hongtt.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.xda.hongtt.R;
import edu.xda.hongtt.adapter.ThongKeAdapter;

public class ThongKeFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_thong_ke, null);
        TabLayout tabLayout = v.findViewById(R.id.main_tabThongKe);
        final ViewPager viewPager = v.findViewById(R.id.main_viewpagerThongKe);
        ThongKeAdapter thongKeAdapter = new ThongKeAdapter(getChildFragmentManager());
        viewPager.setAdapter(thongKeAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        return v;
    }

}
