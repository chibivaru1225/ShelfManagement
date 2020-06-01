package ZOA.Android.ShelfManagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ZOA.Android.ShelfManagement.R;
import ZOA.Android.ShelfManagement.Basic.ShelfStatus;

public class ActivityOnStock extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onstock);
        //setTitle(getString(R.string.app_name) + " Item:" + ShelfStatus.GetLatestShelfStatus().GetItemName());
        setTitle(ShelfStatus.GetLatestShelfStatus().GetItemName());
        //task.execute()
    }

    public void osBtnA_Click(View v) {
        ShelfStatus.GetLatestShelfStatus().SetSelectStatus(ShelfStatus.SelectStatus.Display);
        startActivity(new Intent(getApplication(), ActivityNextOperation.class));
        finish();
    }

    public void osBtnB_Click(View v) {
        ShelfStatus.GetLatestShelfStatus().SetSelectStatus(ShelfStatus.SelectStatus.PopCreate);
        startActivity(new Intent(getApplication(), ActivityNextOperation.class));
        finish();
    }
}
