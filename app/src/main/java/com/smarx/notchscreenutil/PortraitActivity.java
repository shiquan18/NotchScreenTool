package com.smarx.notchscreenutil;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.smarx.notchlib.INotchScreen;
import com.smarx.notchlib.NotchScreenManager;

public class PortraitActivity extends AppCompatActivity {

    private static final String TAG = "portrait_notch";
    private NotchScreenManager notchScreenManager = NotchScreenManager.getInstance();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置Activity全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_portrait);

        // 一个默认会被顶部居中刘海遮挡的TextView
        textView = findViewById(R.id.textView);

        // 隐藏ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // 支持显示到刘海区域
        notchScreenManager.setDisplayInNotch(this);
        // 获取刘海屏信息
        notchScreenManager.getNotchInfo(this, new INotchScreen.NotchScreenCallback() {
            @Override
            public void onResult(INotchScreen.NotchScreenInfo notchScreenInfo) {
                Log.i(TAG, "Is this screen notch? " + notchScreenInfo.hasNotch);
                if (notchScreenInfo.hasNotch) {
                    for (Rect rect : notchScreenInfo.notchRects) {
                        Log.i(TAG, "notch screen Rect =  " + rect.toShortString());
                        // 将被遮挡的TextView下移
                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
                        layoutParams.topMargin = rect.bottom;
                        textView.setLayoutParams(layoutParams);
                    }
                }
            }
        });
    }

}
