package ZOA.Android.ShelfManagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;

import ZOA.Android.ShelfManagement.R;
import ZOA.Android.ShelfManagement.Basic.Util;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.IPAddress = getWifiIPAddress(this);
    }

    public void mnBtnA_Click(View v) {
        ActivityNextOperation.SetBackMenu(false);
        startActivity(new Intent(getApplication(), ActivityReader.class));
    }

    public void mnBtnB_Click(View v) {
        startActivity(new Intent(getApplication(), ActivityShelfList.class));
    }


    // Wi-FiインターフェースのIPアドレスを取得する。
    // 注意：インターフェース2つとか、もろもろの異常系は考慮していない
    private static String getWifiIPAddress(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        int ipAddr = info.getIpAddress();
        String ipString = String.format("%01d.%01d.%01d.%01d",
                (ipAddr >> 0) & 0xff, (ipAddr >> 8) & 0xff, (ipAddr >> 16) & 0xff, (ipAddr >> 24) & 0xff);
        return ipString;
    }
}
