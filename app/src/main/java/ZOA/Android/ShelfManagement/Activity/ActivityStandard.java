package ZOA.Android.ShelfManagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ZOA.Android.ShelfManagement.R;
import ZOA.Android.ShelfManagement.Basic.ShelfStatus;

public class ActivityStandard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        setTitle(ShelfStatus.GetLatestShelfStatus().GetShohinCode());

        TextView txtA = findViewById(R.id.stTxtA);
        TextView txtB = findViewById(R.id.stTxtB);
        txtA.setText(String.format(getString(R.string.stTxtA), ShelfStatus.GetLatestShelfStatus().GetJHattyu()));
        txtB.setText(ShelfStatus.ParseInfoString(getString(R.string.dtTxtA)));
    }

    public void stBtnA_Click(View v) {
        ShelfStatus.GetLatestShelfStatus().SetSelectStatus(ShelfStatus.SelectStatus.OPIncrease);
        startActivity(new Intent(getApplication(), ActivityNextOperation.class));
        finish();
    }
}
