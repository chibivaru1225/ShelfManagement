package ZOA.Android.ShelfManagement;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityShelfSend extends AppCompatActivity implements WifiP2pManager.ActionListener {

    private TaskHttpPost task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf_send);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            task = new TaskHttpPost();
            task.setActionListener(this);
            task.execute(ShelfStatus.GetShelfStatusArray());
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess() {
        ShelfStatus.ClearShelfStatusList();
        finish();
    }

    @Override
    public void onFailure(int reason) {
        finish();
    }
}
