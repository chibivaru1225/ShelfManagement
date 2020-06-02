package ZOA.Android.ShelfManagement.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.asreader.asbarcode.AsBarcode;
import com.asreader.asbarcode.AsBarcodeScanFragment;
import com.asreader.asbarcode.AsFocusPointer;
import com.asreader.asbarcode.AsPointer;
import com.asreader.asbarcode.AsPointerInterface;
import com.asreader.asbarcode.AsPointerManager;
import com.asreader.asbarcode.AsPointerManagerInterface;
import com.asreader.asbarcode.CertifiedSDKInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import ZOA.Android.ShelfManagement.R;
import ZOA.Android.ShelfManagement.Basic.ShelfStatus;

public class ActivityReader extends AppCompatActivity implements AsPointerManagerInterface, AsPointerInterface, CertifiedSDKInterface {

    private AsBarcode asBarcode;
    private AsPointerManager mAsPointerManager;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private AsBarcodeScanFragment mAsBarcodeScanView;
    private Button mScanButton;
    private boolean open;
    private boolean createview;
    private boolean lock;

    private SharedPreferences pre;
    private SharedPreferences.Editor edi;

    private SimpleDateFormat sdf;

    public ActivityReader() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        asBarcode = AsBarcode.getInstance();

        mAsBarcodeScanView = asBarcode.getScanFragment();
        mAsPointerManager = AsPointerManager.getInstance();
        open = false;
        createview = false;
        lock = false;
    }

    @Override
    public void receivedAsPointerIsConnected(Boolean aBoolean) {

    }

    @Override
    public void receivedAsPointerLaserOn(Boolean aBoolean) {

    }

    @Override
    public void receivedAsPointerBattery(AsPointerInterface.RemainingBatteryPower remainingBatteryPower) {

    }

    @Override
    public void whenAsPointerLEDIsCanUsed() {

    }

    @Override
    public void whenAsPointerManufacturerNameReceived() {

    }

    @Override
    public void whenAsPointerHardwareVersionReceived() {

    }

    @Override
    public void whenAsPointerFirmwareVersionReceived() {

    }

    @Override
    public void whenSearchedAsPointerList(ArrayList<AsPointer> arrayList) {

    }

    @Override
    public synchronized Boolean continueScanWhenReceivedScanData(HashMap<String, String> hashMap, Boolean aBoolean) throws CameraAccessException {
        asBarcode.stopScan();
        synchronized (this) {
            try {
                if (hashMap != null) {
                    for (String key : hashMap.keySet()) {
                        System.out.println("item:" + key);
                        Intent i = new Intent(getApplication(), ActivityWait.class);
                        i.putExtra("key", key);

                        if (ShelfStatus.HasKey(key) == false) {
                            startActivity(i);
                        }

                        break;
                    }
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                lock = false;
                finish();
            }
        }
        return true;
    }

    @Override
    public void whenCertifySucceeded(Boolean aBoolean, CertifiedSDKInterface.CertifiedType certifiedType) {

    }

    @Override
    public void whenGetCapabilities() {

    }

    @Override
    public void onResume() {
        super.onResume();

        if (ActivityNextOperation.GetBackMenu() == true) {
            finish();
        }

        setInitConfig();

        if (createview == false) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
                return;
            }
            android.app.FragmentManager manager = this.getFragmentManager();

            android.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frameLayout, mAsBarcodeScanView, "fragment");
            transaction.commit();

            createview = true;
        }

        try {
            if (open == false) {
                open = true;
                asBarcode.openCamera(this);
            } else {
                asBarcode.reloadCamera(this);
            }
            asBarcode.setTimeout(0);
            asBarcode.startScan();
//
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

//    public void onClick(View v) {
//
//        try {
//            asBarcode.setTimeout(0);
//            asBarcode.startScan();
//
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }

    // 復帰時に取り直し動かせるようにするため共通化
    private void setInitConfig() {
        asBarcode.setSymbogogiesArray(null);

        asBarcode.setDouble1Dcode(false);
        asBarcode.setCheckComposite(false);

        asBarcode.addSymbogogies(AsBarcode.Symbogogie.Code39);
        asBarcode.addSymbogogies(AsBarcode.Symbogogie.Code93);
        asBarcode.addSymbogogies(AsBarcode.Symbogogie.Code128);
        asBarcode.addSymbogogies(AsBarcode.Symbogogie.EAN2);
        asBarcode.addSymbogogies(AsBarcode.Symbogogie.EAN5);
        asBarcode.addSymbogogies(AsBarcode.Symbogogie.EAN8);
        asBarcode.addSymbogogies(AsBarcode.Symbogogie.EAN13);
        asBarcode.addSymbogogies(AsBarcode.Symbogogie.ITF);
        asBarcode.addSymbogogies(AsBarcode.Symbogogie.ISBN10);
        asBarcode.addSymbogogies(AsBarcode.Symbogogie.ISBN13);
        asBarcode.addSymbogogies(AsBarcode.Symbogogie.UPCA);
        asBarcode.addSymbogogies(AsBarcode.Symbogogie.NW7);

//        int position = 100;
//        int height = 100;
        AsFocusPointer asFocusPointer = new AsFocusPointer();
        asFocusPointer.setY(400);

        asBarcode.setTargetDistanceType(AsBarcode.TargetDistanceType.TargetDistanceType_Normal);

        asBarcode.setScanningPosition(asFocusPointer);
        asBarcode.setScanningAreaHeight(100);

        asBarcode.setFullScreenScan(false);

//        asBarcode.setVerifyCount(2);
//        asBarcode.setFrequencyLimit((float)(2 * 10));

        //mTimeout = Float.parseFloat(sharedPreferences.getString(AsCameraConstants.pref_key_timeout, AsCameraConstants.default_key_timeout));
//        asBarcode.setTimeout(0);
//        asBarcode.setAutoExposure(true);

        asBarcode.setReportBNR(true);
    }
}
