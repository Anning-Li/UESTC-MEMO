package com.starstudio.projectdemo;

import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.SUNNY;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.starstudio.projectdemo.databinding.FragmentJournalBinding;
import com.starstudio.projectdemo.journal.activity.JournalEditActivity;
import com.starstudio.projectdemo.journal.adapter.PagerAdapter;
import com.starstudio.projectdemo.journal.api.HmsWeatherService;
import com.starstudio.projectdemo.utils.HandlerHelper;
import com.starstudio.projectdemo.utils.OtherUtil;

import org.jetbrains.annotations.NotNull;

public class JournalFragment extends Fragment {
    private final String[] TAGS = {"日记", "相册"};
    private FragmentJournalBinding binding;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // 开启fragment的toolbar右侧的菜单
        setHasOptionsMenu(true);

        binding = FragmentJournalBinding.inflate(inflater, container, false);
        configView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding.pager.setAdapter(new PagerAdapter(this));
        // 将ViewPager2与TabLayout绑定起来
        new TabLayoutMediator(binding.tabLayout, binding.pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                tab.setText(TAGS[position]);
            }
        }).attach();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // 用来构建appbar最右侧的按钮菜单
    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_journal, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == R.id.journal_add) {
            Intent intent = new Intent(getActivity(), JournalEditActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    // 该方法用来配置toolbar
    private void configView() {
        binding.toolbarJournal.titleText.setText(R.string.toolbar_journal);
        // 将Fragment的toolbar添加上
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbarJournal.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.toolbarJournal.dateAndWeather.dayText.setText(OtherUtil.getSystemDay());
        binding.toolbarJournal.dateAndWeather.weekText.setText(OtherUtil.getSystemWeekEng());
        binding.toolbarJournal.dateAndWeather.monthText.setText(OtherUtil.getSystemMonthEng());
        int weatherId = HmsWeatherService.getWeather();
        binding.toolbarJournal.dateAndWeather.toolbarWeather.setBackground(getContext().getDrawable(OtherUtil.weatherId2Mipmap.getOrDefault(weatherId, SUNNY)));
    }
}
