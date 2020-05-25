package ZOA.Android.ShelfManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mnBtnA_Click(View v) {
        ActivityNextOperation.SetBackMenu(false);
        startActivity(new Intent(getApplication(), ActivityReader.class));
    }

    public void mnBtnB_Click(View v) {
        startActivity(new Intent(getApplication(), ActivityShelfList.class));
    }
}
