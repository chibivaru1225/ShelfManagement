package ZOA.Android.ShelfManagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ZOA.Android.ShelfManagement.R;
import ZOA.Android.ShelfManagement.Basic.ShelfStatus;

public class ActivityNotStandard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_standard);
        setTitle(ShelfStatus.GetLatestShelfStatus().GetItemName());

        TextView txtB = findViewById(R.id.nsTxtB);
        TextView txtC = findViewById(R.id.nsTxtC);
        txtB.setText(String.format(getString(R.string.nsTxtB), ShelfStatus.GetLatestShelfStatus().GetSyubai1()));
        txtC.setText(String.format(getString(R.string.nsTxtC), ShelfStatus.GetLatestShelfStatus().GetArari()));
        //txtB.setText();
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
