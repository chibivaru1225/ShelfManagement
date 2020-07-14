package ZOA.Android.ShelfManagement.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import ZOA.Android.ShelfManagement.Adapter.AdapterShelfList;
import ZOA.Android.ShelfManagement.Basic.Util;
import ZOA.Android.ShelfManagement.Interface.InterfaceShelfList;
import ZOA.Android.ShelfManagement.R;
import ZOA.Android.ShelfManagement.Basic.ShelfStatus;
import ZOA.Android.ShelfManagement.Task.TaskHttpPost;

public class ActivityShelfList extends AppCompatActivity implements WifiP2pManager.ActionListener, InterfaceShelfList {

    private AdapterShelfList adapter;
    private static boolean isSending;
    private String latestfile;
    private int position;
    private LinearLayoutManager llm;
    private ProgressDialog pdialog;
    private TaskHttpPost post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf_list);

        RecyclerView rv = findViewById(R.id.ShelfListView);
        adapter = new AdapterShelfList(ShelfStatus.GetShelfStatusList(), this);
        llm = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        rv.addItemDecoration(itemDecoration);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        findViewById(R.id.slBtnC).setEnabled(ShelfStatus.GetNotNoneShelfStatusCount() > 0 && !isSending);
        position = 0;

        pdialog = new ProgressDialog(this);
        pdialog.setTitle("データ送信中");
        pdialog.setMessage("しばらくお待ちください");
        pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Button b = findViewById(R.id.slBtnC);
        findViewById(R.id.slBtnC).setEnabled(ShelfStatus.GetNotNoneShelfStatusCount() > 0 && !isSending);

        if (adapter != null) {
            llm.scrollToPosition(position);
            adapter.notifyDataSetChanged();
        }
    }

    public void slBtnA_Click(View v) {
        ShelfStatus.SetShelfListIsSendAll(true);
        adapter.notifyDataSetChanged();
    }

    public void slBtnB_Click(View v) {
        ShelfStatus.SetShelfListIsSendAll(false);
        adapter.notifyDataSetChanged();
    }

    public void slBtnC_Click(View v) {
        if (this.isSending == false) {
            pdialog.show();
//            Toast.makeText(this, "送信中", Toast.LENGTH_SHORT).show();
            SetIsSending(true);
            //latestfile = CreateCSVData();
            post = new TaskHttpPost();
            post.setActionListener(this);
            //post.execute(latestfile);
            post.execute();
        }
//        startActivity(new Intent(getApplication(), ActivityShelfSend.class));
//        ShelfStatus.ClearShelfStatusList();
////        finish();
    }

    private String CreateCSVData() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss");
        String filename = new StringBuilder(Util.IPAddress.replace('.', '_')).append("-")
                .append(format.format(new Date())).append(".csv").toString();
        //filename = "Test.csv";
        String output = ShelfStatus.ConvertCSVString();
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE | Context.MODE_APPEND);

            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "shift_jis");
            writer.write(output);
            writer.close();

//            outputStream.write(output.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("CreateCSVData:" + filename);
        return filename;
    }

    private void SetIsSending(boolean sending) {
        this.isSending = sending;
        findViewById(R.id.slBtnC).setEnabled(ShelfStatus.GetNotNoneShelfStatusCount() > 0 && !sending);
    }

    @Override
    public void onSuccess() {
        pdialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(post.GetResult() + "件 送信しました");
        builder.setTitle("送信完了");
        builder.show();

        //deleteFile(latestfile);
        SetIsSending(false);
        ShelfStatus.ClearShelfStatusList();
        adapter.notifyDataSetChanged();
        //Toast.makeText(this, "送信完了", Toast.LENGTH_SHORT).show();
//        finish();
    }

    @Override
    public void onFailure(int reason) {
        pdialog.dismiss();
        //deleteFile(latestfile);
        SetIsSending(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("時間をおいて再送信してください");
        builder.setTitle("送信失敗");
        builder.show();
        //Toast.makeText(this, "送信失敗" + System.lineSeparator() + "時間をおいて再送信してください", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OpenNewActivity(ShelfStatus status) {
        ActivityModeSelect.SetShelfStatus(status);
        ActivityModeSelect.SetParentActivity(this);
        position = llm.findFirstCompletelyVisibleItemPosition();
        startActivity(new Intent(getApplication(), ActivityModeSelect.class));
    }
}