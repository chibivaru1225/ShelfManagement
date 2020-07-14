package ZOA.Android.ShelfManagement.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.net.wifi.p2p.WifiP2pManager;
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
import ZOA.Android.ShelfManagement.Task.TaskHttpGet;

public class ActivityReader extends AppCompatActivity implements AsPointerManagerInterface, AsPointerInterface, CertifiedSDKInterface, WifiP2pManager.ActionListener {

    private AsBarcode asBarcode;
    private AsPointerManager mAsPointerManager;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private AsBarcodeScanFragment mAsBarcodeScanView;
    private Button mScanButton;
    private boolean open;
    private boolean createview;
    private boolean backoperationing;
    private TaskHttpGet task;

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
        backoperationing = false;
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
//    public synchronized Boolean continueScanWhenReceivedScanData(HashMap<String, String> hashMap, Boolean aBoolean) throws CameraAccessException {
//        Log.d("Test", "continueScanWhenReceivedScanData");
//        return false;
//    }

    public synchronized Boolean continueScanWhenReceivedScanData(HashMap<String, String> hashMap, Boolean aBoolean) throws CameraAccessException {
        System.out.println("Shelf:continueScanWhenReceivedScanData");
        try {
            if (hashMap != null && backoperationing == false) {
                for (String key : hashMap.keySet()) {
                    startBackGroundWorker(key);
                    break;
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }

        return false;
    }

    @Override
    public void whenCertifySucceeded(Boolean aBoolean, CertifiedSDKInterface.CertifiedType certifiedType) {

    }

    @Override
    public void whenGetCapabilities() {

    }

    @Override
    public void onResume() {
        if (ActivityNextOperation.GetBackMenu() == true) {
            finish();
        }

        super.onResume();
        System.out.println("Shelf:onResume");

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
            System.out.println("Shelf:onResume2");
            asBarcode.setTimeout(0);
            asBarcode.startScan();

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

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

//        asBarcode.setVerifyCount(1);

//        asBarcode.setVerifyCount(2);
//        asBarcode.setFrequencyLimit((float)(2 * 10));

        //mTimeout = Float.parseFloat(sharedPreferences.getString(AsCameraConstants.pref_key_timeout, AsCameraConstants.default_key_timeout));
//        asBarcode.setTimeout(0);
//        asBarcode.setAutoExposure(true);

        asBarcode.setReportBNR(true);
    }

    private void startBackGroundWorker(String jancode)
    {
        backoperationing = true;
        this.task = new TaskHttpGet();
        task.setActionListener(this);
        task.execute(jancode);
    }

    @Override
    public void onSuccess() {
        asBarcode.stopScan();
        ShelfStatus latest = task.GetShelfStatus();
        ShelfStatus.SetShelfStatus(task.GetShelfStatus());

        switch (latest.GetItemStatus())
        {
//            case OnStock:
//                startActivity(new Intent(getApplication(), ActivityOnStock.class));
//                break;
//            case OnArrival:
//                startActivity(new Intent(getApplication(), ActivityStandard.class));
//                break;
//            case NotArrival:
//                startActivity(new Intent(getApplication(), ActivityNotStandard.class));
//                break;
            case NONE:
                Toast.makeText(this, "FAILURE Status", Toast.LENGTH_SHORT).show();
                break;
            default:
                ActivityModeSelect.SetParentActivity(this);
                ActivityModeSelect.SetShelfStatus(ShelfStatus.GetLatestShelfStatus());
                startActivity(new Intent(getApplication(), ActivityModeSelect.class));
                break;
        }
        backoperationing = false;
    }

    @Override
    public void onFailure(int reason) {
        switch (reason) {
            case 0:
                Toast.makeText(this, "存在しない商品です", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "FAILURE HttpGet", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "データの変換に失敗しました", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "未知のエラーです", Toast.LENGTH_SHORT).show();
                break;
        }
        asBarcode.startScan();
        backoperationing = false;
    }
}
