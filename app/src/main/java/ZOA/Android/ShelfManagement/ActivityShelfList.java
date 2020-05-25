package ZOA.Android.ShelfManagement;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActivityShelfList extends AppCompatActivity {

    private AdapterShelfList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf_list);

        RecyclerView rv = findViewById(R.id.ShelfListView);
        adapter = new AdapterShelfList(ShelfStatus.GetShelfStatusList());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        rv.addItemDecoration(itemDecoration);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        ShelfStatus.ClearShelfStatusList();
        finish();
    }
}