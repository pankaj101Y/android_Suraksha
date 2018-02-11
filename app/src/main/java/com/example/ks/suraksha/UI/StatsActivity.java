package com.example.ks.suraksha.UI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ks.suraksha.R;
import com.example.ks.suraksha.Utility.Stats;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
    private ViewPager graphViewer=null;
    private FragmentAdapter fragmentAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        graphViewer=findViewById(R.id.graph_viewer);
        fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());
        graphViewer.setAdapter(fragmentAdapter);

    }

    private static class FragmentAdapter extends FragmentPagerAdapter {
        FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new GenderFragment();

                case 1:
                    return new AadharFragment();

                case 2:
                    return new EnrollmentFragment();
            }
            return null;
        }
        @Override
        public int getCount() {
            return 3;
        }

    }
}
