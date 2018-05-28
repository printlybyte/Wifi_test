package com.lgd.wifi_test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lgd.wifi_test.utils.CommonUtils;
import com.lgd.wifi_test.wifi.WifiManagerr;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_wifi_ssid)
    EditText mMainWifiSsid;
    @BindView(R.id.main_wifi_password)
    EditText mMainWifiPassword;
    @BindView(R.id.main_wifi_confim)
    Button mMainWifiConfim;
    private CommonUtils mContentUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContentUtils = CommonUtils.getCmdManagerInstance();


    }

    @butterknife.OnClick({R.id.main_wifi_ssid, R.id.main_wifi_password, R.id.main_wifi_confim})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.main_wifi_ssid:
                break;
            case R.id.main_wifi_password:
                break;
            case R.id.main_wifi_confim:
                String ssid = mMainWifiSsid.getText().toString().trim();
                String pass = mMainWifiPassword.getText().toString().trim();
                if (TextUtils.isEmpty(ssid) || TextUtils.isEmpty(pass)) {
                    mContentUtils.showToast(this, R.string.main_toast_ssidorpassword);
                    return;
                }
                if (pass.length() < 8) {
                    mContentUtils.showToast(this, R.string.main_toast_password_length);
                    return;
                }


                boolean isC = mContentUtils.isCheckedWifiConnect(this, ssid);
                if (isC) {
                    mContentUtils.showToast(this, R.string.main_toast_wifi_connected);
                    return;
                }


                WifiConfiguration wifiConfiguration = WifiManagerr.createWifiConfig(ssid, pass, WifiManagerr.WifiCipherType.WIFICIPHER_WPA);
                boolean is = WifiManagerr.addNetWork(wifiConfiguration, this);


                break;
        }
    }
    //权限请求码
    private static final int PERMISSION_REQUEST_CODE = 0;
    //两个危险权限需要动态申请
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    /**
     * 检查是否已经授予权限
     *
     * @return
     */
    private boolean checkPermission() {
        for (String permission : NEEDED_PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                NEEDED_PERMISSIONS, PERMISSION_REQUEST_CODE);
    }
}
