package ZOA.Android.ShelfManagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ZOA.Android.ShelfManagement.Basic.Util;
import ZOA.Android.ShelfManagement.R;
import ZOA.Android.ShelfManagement.Basic.ShelfStatus;

public class ActivityNotStandard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_standard);
        setTitle(ShelfStatus.GetLatestShelfStatus().GetShohinCode());

        Util.SetShelfStatus(this, findViewById(R.id.nsShelf), ShelfStatus.GetLatestShelfStatus());
    }

    public void nsBtnA_Click(View v) {
        ShelfStatus.GetLatestShelfStatus().SetSelectStatus(ShelfStatus.SelectStatus.PopRemove);
        startActivity(new Intent(getApplication(), ActivityNextOperation.class));
        finish();
    }

    public void nsBtnB_Click(View v) {
        ShelfStatus.GetLatestShelfStatus().SetSelectStatus(ShelfStatus.SelectStatus.Transfer);
        startActivity(new Intent(getApplication(), ActivityNextOperation.class));
        finish();
    }
}
