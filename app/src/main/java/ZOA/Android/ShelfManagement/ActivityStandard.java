package ZOA.Android.ShelfManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityStandard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standard);
        setTitle(ShelfStatus.GetLatestShelfStatus().GetItemName());
    }

    public void stBtnA_Click(View v) {
        ShelfStatus.GetLatestShelfStatus().SetSelectStatus(ShelfStatus.SelectStatus.OPIncrease);
        startActivity(new Intent(getApplication(), ActivityNextOperation.class));
        finish();
    }
}
