package ZOA.Android.ShelfManagement.Basic;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.Layout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import ZOA.Android.ShelfManagement.R;

import static android.content.Context.WIFI_SERVICE;

public class Util {
    public static String IPAddress;
    public static String GetURL = "http://webhn.zoa.local/Magic94Scripts/MGRQISPI94.dll?APPNAME=WEBHNCTL&PRGNAME=GetBhtItemInfo&ARGUMENTS=-N";
    public static String PostURL= "";

    public static void SetShelfStatus(AppCompatActivity activity, ConstraintLayout ir)
    {
        TextView sb = ir.findViewById(R.id.shelfTxtSb);
        TextView jy = ir.findViewById(R.id.shelfTxtJy);
        TextView ar = ir.findViewById(R.id.shelfTxtAr);
        TextView mi = ir.findViewById(R.id.shelfTxtJMise);

        TextView jza = ir.findViewById(R.id.shelfTxtJZaiko);
        TextView jzh = ir.findViewById(R.id.shelfTxtJZaiHatsu);
        TextView jis = ir.findViewById(R.id.shelfTxtJIso);
        TextView jha = ir.findViewById(R.id.shelfTxtJHatten);
        TextView js1 = ir.findViewById(R.id.shelfTxtJSyubai1);
        TextView js2 = ir.findViewById(R.id.shelfTxtJSyubai2);
        TextView js3 = ir.findViewById(R.id.shelfTxtJSyubai3);

        TextView zza = ir.findViewById(R.id.shelfTxtZZaiko);
        TextView zzh = ir.findViewById(R.id.shelfTxtZZaiHatsu);
        TextView zis = ir.findViewById(R.id.shelfTxtZIso);
        TextView zha = ir.findViewById(R.id.shelfTxtZHatten);
        TextView zs1 = ir.findViewById(R.id.shelfTxtZSyubai1);
        TextView zs2 = ir.findViewById(R.id.shelfTxtZSyubai2);
        TextView zs3 = ir.findViewById(R.id.shelfTxtZSyubai3);


        sb.setText(String.format(activity.getString(R.string.dtSb), ShelfStatus.GetLatestShelfStatus().GetShohinBan()));
        jy.setText(String.format(activity.getString(R.string.dtJy), ShelfStatus.GetLatestShelfStatus().GetJyotai()));
        ar.setText(String.format(activity.getString(R.string.dtAr), ShelfStatus.GetLatestShelfStatus().GetArari()));
        mi.setText(String.format(activity.getString(R.string.dtMi), ShelfStatus.GetLatestShelfStatus().GetMise()));

        jza.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetJZaiko()));
        jzh.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetJZaiHatsu()));
        jis.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetJIso()));
        jha.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetJHatten()));
        js1.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetJSyubai1()));
        js2.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetJSyubai2()));
        js3.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetJSyubai3()));

        zza.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetZZaiko()));
        zzh.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetZZaiHatsu()));
        zis.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetZIso()));
        zha.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetZHatten()));
        zs1.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetZSyubai1()));
        zs2.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetZSyubai2()));
        zs3.setText(String.format(activity.getString(R.string.dtNum), ShelfStatus.GetLatestShelfStatus().GetZSyubai3()));
    }
}
