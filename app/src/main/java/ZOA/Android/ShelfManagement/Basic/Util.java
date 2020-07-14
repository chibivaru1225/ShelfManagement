package ZOA.Android.ShelfManagement.Basic;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.apache.commons.codec.StringEncoder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.InputStreamBody;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import ZOA.Android.ShelfManagement.R;

public class Util {
    public static String IPAddress;
    public static String BaseURL = "http://webhn.local.zoa.co.jp:60001/magic94Scripts/mgrqispi94.dll";
    public static String GetURL = BaseURL + "?APPNAME=WEBHNCTL&PRGNAME=GetBhtItemInfo&ARGUMENTS=-N";
    //public static String GetURL = "http://webhn.zoa.local/Magic94Scripts/MGRQISPI94.dll?APPNAME=WEBHNCTL&PRGNAME=GetBhtItemInfo&ARGUMENTS=-N";
    public static String PostURL = BaseURL;
    //public static String PostURL = "http://webhn.local.zoa.co.jp:60001/magic94Scripts/mgrqispi94.dll";
    //public static String LF = System.lineSeparator();
    public static String LF = "\r\n";

    public static void SetShelfStatus(AppCompatActivity activity, ConstraintLayout ir, ShelfStatus status) {
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


        sb.setText(String.format(activity.getString(R.string.dtSb), status.GetShohinBan()));
        jy.setText(String.format(activity.getString(R.string.dtJy), status.GetJyotai()));
        ar.setText(String.format(activity.getString(R.string.dtAr), status.GetArari()));
        mi.setText(String.format(activity.getString(R.string.dtMi), status.GetMise()));

        jza.setText(String.format(activity.getString(R.string.dtNum), status.GetJZaiko()));
        jzh.setText(String.format(activity.getString(R.string.dtNum), status.GetJZaiHatsu()));
        jis.setText(String.format(activity.getString(R.string.dtNum), status.GetJIso()));
        jha.setText(String.format(activity.getString(R.string.dtNum), status.GetJHatten()));
        js1.setText(String.format(activity.getString(R.string.dtNum), status.GetJSyubai1()));
        js2.setText(String.format(activity.getString(R.string.dtNum), status.GetJSyubai2()));
        js3.setText(String.format(activity.getString(R.string.dtNum), status.GetJSyubai3()));

        zza.setText(String.format(activity.getString(R.string.dtNum), status.GetZZaiko()));
        zzh.setText(String.format(activity.getString(R.string.dtNum), status.GetZZaiHatsu()));
        zis.setText(String.format(activity.getString(R.string.dtNum), status.GetZIso()));
        zha.setText(String.format(activity.getString(R.string.dtNum), status.GetZHatten()));
        zs1.setText(String.format(activity.getString(R.string.dtNum), status.GetZSyubai1()));
        zs2.setText(String.format(activity.getString(R.string.dtNum), status.GetZSyubai2()));
        zs3.setText(String.format(activity.getString(R.string.dtNum), status.GetZSyubai3()));
    }
}

class KnownSizeInputStreamBody extends InputStreamBody {
    private final long contentLength;

    public KnownSizeInputStreamBody(InputStream in, long contentLength, ContentType contentType) {
        super(in, contentType);
        this.contentLength = contentLength;
    }

    @Override
    public long getContentLength() {
        return contentLength;
    }
}