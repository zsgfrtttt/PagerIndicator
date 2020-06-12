package com.csz.indicator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.indicator.library.IndicatorAdapter;
import com.indicator.library.IndicatorContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private IndicatorContainer container;
    private ViewPager viewPager;

    private String[] text = {"text1", "text2", "fragment","java", "c++", "python", "golang"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final List<String> list = Arrays.asList(text);
        final List<Fragment> fragments = new ArrayList<>();
        for (String s : list) {
            fragments.add(TextFragment.newInstance(s));
        }

        container = findViewById(R.id.indicator_wrap);
        viewPager = findViewById(R.id.vp);
        container.bind(viewPager).setAdapter(new IndicatorAdapter() {
            @NonNull
            @Override
            public List<String> getTitle() {
                return list;
            }
        });
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }
}