package com.hdvision.gadleaderboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hdvision.gadleaderboard.ui.main.LeanerHourHolderFragment;
import com.hdvision.gadleaderboard.ui.main.LeanerSkillHolderFragment;
import com.hdvision.gadleaderboard.ui.main.SectionsPagerAdapter;
import com.hdvision.gadleaderboard.utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabs);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LeanerHourHolderFragment(), getString(R.string.tab_title_1));
        adapter.addFragment(new LeanerSkillHolderFragment(), getString(R.string.tab_title_2));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        Button submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SubmitActivity.class);
            startActivity(intent);
        });

        initImageLoader();
    }

    /**
     * init universal image loader
     */
    private void initImageLoader() {
        UniversalImageLoader imageLoader = new UniversalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(imageLoader.getConfig());
    }
}