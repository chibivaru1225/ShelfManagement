package ZOA.Android.ShelfManagement.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ZOA.Android.ShelfManagement.Basic.ShelfStatus;
import ZOA.Android.ShelfManagement.Basic.Util;
import ZOA.Android.ShelfManagement.R;

public class ActivityModeSelect extends AppCompatActivity {

    private static Activity parent;
    private static ShelfStatus status;

    public static void SetParentActivity(Activity p) {
        parent = p;
    }

    public static void SetShelfStatus(ShelfStatus s) {
        status = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Shelf:ActivityModeSelect:onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);
        setTitle(status.GetShohinCode());

        Util.SetShelfStatus(this, findViewById(R.id.msShelf), status);

        TextView txtA = findViewById(R.id.msTxtA);
        txtA.setText(ShelfStatus.GetItemStatusText(status.GetItemStatus()));

        Button btnA = findViewById(R.id.msBtnA);
        Button btnB = findViewById(R.id.msBtnB);
        Button btnC = findViewById(R.id.msBtnC);
        Button btnD = findViewById(R.id.msBtnD);
        Button btnE = findViewById(R.id.msBtnE);
        Button btnF = findViewById(R.id.msBtnF);
        Button btnG = findViewById(R.id.msBtnG);

        if (parent instanceof ActivityShelfList) {
            btnA.setTypeface(Typeface.DEFAULT, status.GetSelectStatus() == ShelfStatus.SelectStatus.PopRemove ? Typeface.BOLD_ITALIC : Typeface.NORMAL);
            btnB.setTypeface(Typeface.DEFAULT, status.GetSelectStatus() == ShelfStatus.SelectStatus.PopCreate ? Typeface.BOLD_ITALIC : Typeface.NORMAL);
            btnC.setTypeface(Typeface.DEFAULT, status.GetSelectStatus() == ShelfStatus.SelectStatus.Display ? Typeface.BOLD_ITALIC : Typeface.NORMAL);
            btnD.setTypeface(Typeface.DEFAULT, status.GetSelectStatus() == ShelfStatus.SelectStatus.Transfer ? Typeface.BOLD_ITALIC : Typeface.NORMAL);
            btnE.setTypeface(Typeface.DEFAULT, status.GetSelectStatus() == ShelfStatus.SelectStatus.OPIncrease ? Typeface.BOLD_ITALIC : Typeface.NORMAL);
            btnF.setTypeface(Typeface.DEFAULT, status.GetSelectStatus() == ShelfStatus.SelectStatus.OPDecrease ? Typeface.BOLD_ITALIC : Typeface.NORMAL);
            btnG.setTypeface(Typeface.DEFAULT, status.GetSelectStatus() == ShelfStatus.SelectStatus.NONE ? Typeface.BOLD_ITALIC : Typeface.NORMAL);
        }

        btnA.setOnClickListener((buttonView) -> SelectButton_Click(ShelfStatus.SelectStatus.PopRemove));
        btnB.setOnClickListener((buttonView) -> SelectButton_Click(ShelfStatus.SelectStatus.PopCreate));
        btnC.setOnClickListener((buttonView) -> SelectButton_Click(ShelfStatus.SelectStatus.Display));
        btnD.setOnClickListener((buttonView) -> SelectButton_Click(ShelfStatus.SelectStatus.Transfer));
        btnE.setOnClickListener((buttonView) -> SelectButton_Click(ShelfStatus.SelectStatus.OPIncrease));
        btnF.setOnClickListener((buttonView) -> SelectButton_Click(ShelfStatus.SelectStatus.OPDecrease));
        btnG.setOnClickListener((buttonView) -> SelectButton_Click(ShelfStatus.SelectStatus.NONE));

        SetButtonEnable();
    }

    private void SetButtonEnable() {
        Button btnPopOff = findViewById(R.id.msBtnA);
        Button btnPopPrint = findViewById(R.id.msBtnB);
        Button btnCheck = findViewById(R.id.msBtnC);
        Button btnIrai = findViewById(R.id.msBtnD);
        Button btnInc = findViewById(R.id.msBtnE);
        Button btnDec = findViewById(R.id.msBtnF);

        switch (status.GetItemStatus()) {
            case OnStock:
                btnPopOff.setEnabled(false);
                btnPopPrint.setEnabled(true);
                btnCheck.setEnabled(true);
                btnIrai.setEnabled(false);
                btnInc.setEnabled(true);
                btnDec.setEnabled(true);
                break;
            case OnArrival:
                btnPopOff.setEnabled(false);
                btnPopPrint.setEnabled(false);
                btnCheck.setEnabled(false);
                btnIrai.setEnabled(false);
                btnInc.setEnabled(true);
                btnDec.setEnabled(false);
                break;
            case NotArrival:
                btnPopOff.setEnabled(true);
                btnPopPrint.setEnabled(false);
                btnCheck.setEnabled(false);
                btnIrai.setEnabled(true);
                btnInc.setEnabled(false);
                btnDec.setEnabled(false);
                break;
            default:
                btnPopOff.setEnabled(false);
                btnPopPrint.setEnabled(false);
                btnCheck.setEnabled(false);
                btnIrai.setEnabled(false);
                btnInc.setEnabled(false);
                btnDec.setEnabled(false);
                break;
        }
    }

    private void SelectButton_Click(ShelfStatus.SelectStatus shelfstatus) {
        status.SetSelectStatus(shelfstatus);

        if (parent instanceof ActivityReader) {
            startActivity(new Intent(getApplication(), ActivityNextOperation.class));
        } else if (parent instanceof ActivityShelfList) {
            //TODO:なんかする
            //TODO:なんもせんでもええかも
        }

        finish();
    }
}
