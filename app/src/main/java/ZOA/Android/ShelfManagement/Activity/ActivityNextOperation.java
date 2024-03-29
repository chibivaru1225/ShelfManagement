package ZOA.Android.ShelfManagement.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ZOA.Android.ShelfManagement.R;
import ZOA.Android.ShelfManagement.Basic.ShelfStatus;

public class ActivityNextOperation extends AppCompatActivity {

    private static boolean backmenu = false;

    public static boolean GetBackMenu() {
        return backmenu;
    }

    public static void SetBackMenu(boolean back) {
        backmenu = back;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_operation);
        //setTitle(getString(R.string.app_name) + " Item:" + ShelfStatus.GetLatestShelfStatus().GetItemName());
        //setTitle(ShelfStatus.GetLatestShelfStatus().GetItemName());
        //task.execute()
    }

    @Override
    public void onResume() {
        super.onResume();
        backmenu = false;
    }

    public void noBtnA_Click(View v) {
        backmenu = false;
        ShelfStatus.AddShelfStatusList();
        finish();
    }

    public void noBtnB_Click(View v) {
        backmenu = true;
        ShelfStatus.AddShelfStatusList();
        finish();
    }
}