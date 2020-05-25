package ZOA.Android.ShelfManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityNotStandard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_standard);
        setTitle(ShelfStatus.GetLatestShelfStatus().GetItemName());
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
