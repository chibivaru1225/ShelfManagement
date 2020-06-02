package ZOA.Android.ShelfManagement.Activity;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import ZOA.Android.ShelfManagement.JSON.StatusJSONParent;
import ZOA.Android.ShelfManagement.R;
import ZOA.Android.ShelfManagement.Basic.ShelfStatus;
import ZOA.Android.ShelfManagement.Task.TaskHttpPost;

public class ActivityShelfSend extends AppCompatActivity implements WifiP2pManager.ActionListener {

    private TaskHttpPost task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf_send);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(ShelfStatus.GetShelfStatusList().size() <= 0)
            finish();

        try {

            StatusJSONParent data = StatusJSONParent.ConvertStatusJSON(ShelfStatus.GetShelfStatusList());
            Gson gson = new Gson();
            String json = gson.toJson(data);
            ShelfStatus.ClearShelfStatusList();
            OpenGMail(json);
//            task = new TaskHttpPost();
//            task.setActionListener(this);
//            task.execute(ShelfStatus.GetShelfStatusArray());
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


    public void OpenGMail(String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        //String[] strTo = {"zoa.system@gmail.com"};
        //String[] strCc = {"arai.tadao@zoa.co.jp", "matumori@zoa.co.jp"};
        String[] strCc = {"arai.tadao@zoa.co.jp"};

        //intent.putExtra(Intent.EXTRA_EMAIL, strTo);
        intent.putExtra(Intent.EXTRA_EMAIL, strCc);
        intent.putExtra(Intent.EXTRA_SUBJECT, "jsonテスト");
        intent.putExtra(Intent.EXTRA_TEXT, body);

//        Uri attachments = Uri.parse(image_path);
//        intent.putExtra(Intent.EXTRA_STREAM, attachments);

        intent.setType("message/rfc822");

        intent.setPackage("com.google.android.gm");

        startActivity(intent);
    }
}
