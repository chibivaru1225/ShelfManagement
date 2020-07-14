package ZOA.Android.ShelfManagement.Activity;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ZOA.Android.ShelfManagement.R;
import ZOA.Android.ShelfManagement.Basic.ShelfStatus;
import ZOA.Android.ShelfManagement.Task.TaskHttpGet;

public class ActivityWait extends AppCompatActivity implements WifiP2pManager.ActionListener {

    private TaskHttpGet task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            Intent i = getIntent();
            String key = i.getStringExtra("key");

            task = new TaskHttpGet();
            task.setActionListener(this);
            task.execute(key);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess() {
        ShelfStatus latest = task.GetShelfStatus();
        ShelfStatus.SetShelfStatus(task.GetShelfStatus());

        switch (latest.GetItemStatus())
        {
            case OnStock:
                startActivity(new Intent(getApplication(), ActivityOnStock.class));
                break;
            case OnArrival:
                startActivity(new Intent(getApplication(), ActivityStandard.class));
                break;
            case NotArrival:
                startActivity(new Intent(getApplication(), ActivityNotStandard.class));
                break;
            case NONE:
                Toast.makeText(this, "FAILURE Status", Toast.LENGTH_SHORT).show();
                break;
        }

        finish();
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
                Toast.makeText(this, "FAILURE HttpGet", Toast.LENGTH_SHORT).show();
                break;
        }
        finish();
    }
}
