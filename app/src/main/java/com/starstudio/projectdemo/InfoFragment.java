package com.starstudio.projectdemo;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import com.starstudio.projectdemo.databinding.FragmentInfoBinding;
import com.starstudio.projectdemo.utils.FileUtil;
import com.starstudio.projectdemo.utils.OtherUtil;
import com.starstudio.projectdemo.utils.SharedPreferencesUtils;

import org.jetbrains.annotations.NotNull;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class InfoFragment extends Fragment {

    FragmentInfoBinding binding;
    private static final int PICTURE = 1;
    private static final int REQUEST_CODE_PERMISSION = 10;
    private SharedPreferencesUtils mSharedPreferencesUtils;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        mSharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        configToolbar();
        initView();
        setAllOnClick();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    // 该方法用来配置toolbar
    private void configToolbar() {
        binding.toolbarInfo.titleText.setText(R.string.toolbar_info);
        // 将Fragment的toolbar添加上
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbarInfo.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    //页面初始化部分控件
    private void initView(){
        circleImage(FileUtil.getImageContentUri(getContext(), new File(mSharedPreferencesUtils.readString(SharedPreferencesUtils.Key.KEY_IVINFO.toString()))));
        if(mSharedPreferencesUtils.readString(binding.etInfoName.getId() + "") != ""){
            binding.etInfoName.setText(mSharedPreferencesUtils.readString(binding.etInfoName.getId() + ""));
        }
    }

    //该方法用来将用户头像设置为圆形
    private void circleImage(Uri uri){
        RequestOptions options = new RequestOptions()
                .error(R.drawable.logo)
                .placeholder(R.drawable.logo)
                .transforms(new CircleCrop());

        Glide.with(getContext())
                .load(uri)
                .apply(options)
                .into(binding.ivInfo);
    }


    //设置控件的点击事件
    private void setAllOnClick(){
        binding.tvResetPic.setOnClickListener(v->{
            //判断是否获取存储权限权限
            if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED){
                // 已获得权限
                openPicture();
            }else{
                //未获得权限,进行申请
                InfoFragment.this.requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
            }

        });

        binding.etInfoName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                Log.e(getClass().getSimpleName(), "收起键盘的Keycode为: " + keyCode);

                //当点击键盘回车时，隐藏光标和键盘，同时保存修改后的内容
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    binding.etInfoName.clearFocus();
                    InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(binding.etInfoName.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    mSharedPreferencesUtils.putString(binding.etInfoName.getId() + "",binding.etInfoName.getText().toString());
                }
                return false;
            }
        });
    }

    // 打开系统图库选择图片
    private void openPicture() {
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, PICTURE);
    }


    //请求权限后的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION: {
                if(ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED){
                    openPicture();
                }else{
                    showWaringDialog();
                }
            }
            return;
        }
    }

    //提示
    private void showWaringDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this.getContext())
                .setTitle("警告！")
                .setMessage("如不打开读写权限, 则无法设置用户头像")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    //startActivityForResult的回调方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //打开系统相册后执行此处
            case PICTURE:
                if(requestCode != RESULT_OK && data != null){
                    Bitmap bitmap = FileUtil.sampleImage(FileUtil.getFilePathFromContentUri(Uri.parse(data.getDataString()), getContext().getContentResolver()));
//                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                    mSharedPreferencesUtils.putString(SharedPreferencesUtils.Key.KEY_IVINFO.toString(),saveBitmap(bitmap));
//                    circleImage(data.getData());
                    circleImage(FileUtil.getImageContentUri(getContext(), new File(mSharedPreferencesUtils.readString(SharedPreferencesUtils.Key.KEY_IVINFO.toString()))));
                }
                break;
        }
    }

    public String saveBitmap(Bitmap bitmap) {
        String FILE_STORAGE = getContext().getExternalFilesDir("").getPath();
        File file = new File(FILE_STORAGE + "/picture");
        if (!file.exists())
            file.mkdir();
        String PICTURE_FILE = file.getAbsolutePath();
        File save = new File(PICTURE_FILE + "/" + "avater");
        if (!save.exists())
            save.mkdir();   // 如果该类别的文件夹不存在，则先创建
        save = new File(save.getAbsolutePath(), new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date()) + ".jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(save));
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return save.getAbsolutePath();
    }


}
