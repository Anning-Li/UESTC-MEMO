package com.starstudio.projectdemo;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;


import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.huawei.hms.mlsdk.common.MLApplication;
import com.starstudio.projectdemo.Custom.HideInputActivity;
import com.starstudio.projectdemo.databinding.ActivityMainBinding;
import com.starstudio.projectdemo.journal.api.HmsWeatherService;
import com.starstudio.projectdemo.utils.ContextHolder;
import com.starstudio.projectdemo.utils.FileUtil;
import com.starstudio.projectdemo.utils.RequestPermission;
import com.starstudio.projectdemo.utils.SharedPreferencesUtils;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends HideInputActivity {

    private ActivityMainBinding binding;
    private RequestPermission permissionRequest;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 进行权限申请的操作
        RequestPermission.init(this);
        permissionRequest = RequestPermission.getInstance();
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        // 本次申请的权限是必要的，即没用通过则会导致APP无法使用
        permissionRequest.checkPermissions(RequestPermission.CODE_MUST, permissions);
        permissionRequest.requestLocationPermission();   // 单独申请定位权限

        // init
        {
            ContextHolder.init(this);
            // 进行文件操作
            FileUtil.init(this);
            // 天气
            HmsWeatherService.init(this);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        configView();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void configView() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        // 将BottomNavigationView与NavController绑定
        NavigationUI.setupWithNavController(binding.bottomNavMain, navController);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RequestPermission.CODE_MUST)
            for (int res : grantResults) {
                if (res != 0) {
                    Toast.makeText(this, "相关权限未通过,会导致部分功能无法使用~", Toast.LENGTH_SHORT).show();
                }
            }
    }

    private void config(){
        MLApplication.getInstance().setApiKey("CgB6e3x9c2tIlXQZdvRg9VeCfngxvAwbW5FpKsYs/7eW39cdgYZ90pxu2gM85yEp+f2zCFSTXy4CebF3cdcULMzc");
    }


}