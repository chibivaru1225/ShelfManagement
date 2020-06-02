package ZOA.Android.ShelfManagement.Basic;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ZOA.Android.ShelfManagement.R;

import static android.provider.Settings.System.getString;


public class ShelfStatus {

    private ItemStatus itemstatus;
    private SelectStatus selectstatus;
    private ErrorStatus errorstatus;
    private String jsontxt;
    private boolean issend;

    private String jancode;
    private String shohinban;
    private String shohincode;
    private String mise;
    private String jyotai;

    private int jzaiko;
    private int jhatten;
    private int jzaihatsu;
    private int jiso;
    private int jsyubai1;
    private int jsyubai2;
    private int jsyubai3;

    private int zzaiko;
    private int zhatten;
    private int zzaihatsu;
    private int ziso;
    private int zsyubai1;
    private int zsyubai2;
    private int zsyubai3;

    private int arari;

    public ShelfStatus() {
        itemstatus = ItemStatus.NONE;
        errorstatus = ErrorStatus.NONE;
        selectstatus = SelectStatus.NONE;
        jancode = "";
        shohincode = "";
        jyotai = "";
        issend = true;
        //ipaddress =

        jzaiko = 0;
        jhatten = 0;
        jzaihatsu = 0;
        jiso = 0;
        jsyubai1 = 0;
        jsyubai2 = 0;
        jsyubai3 = 0;

        zzaiko = 0;
        zhatten = 0;
        zzaihatsu = 0;
        ziso = 0;
        zsyubai1 = 0;
        zsyubai2 = 0;
        zsyubai3 = 0;

        arari = 0;
    }

    public ShelfStatus(ItemStatus istatus, ErrorStatus estatus) {
        itemstatus = istatus;
        errorstatus = estatus;
        selectstatus = SelectStatus.NONE;
        jancode = "";
        shohincode = "";
        jyotai = "";
        issend = true;

        jzaiko = 0;
        jhatten = 0;
        jzaihatsu = 0;
        jiso = 0;
        jsyubai1 = 0;
        jsyubai2 = 0;
        jsyubai3 = 0;

        zzaiko = 0;
        zhatten = 0;
        zzaihatsu = 0;
        ziso = 0;
        zsyubai1 = 0;
        zsyubai2 = 0;
        zsyubai3 = 0;

        arari = 0;
    }


    public ItemStatus GetItemStatus() {

        if (this.jhatten == 0) {
            if (this.jzaiko <= 0) {
                System.out.println("jhattyu == 0 && jzaiko <= 0 : jhattyu=" + jhatten + " jzaiko=" + jzaiko);
                return ItemStatus.NotStandard;
            } else if (this.jzaiko > 0) {
                System.out.println("jhattyu == 0 && jzaiko > 0 : jhattyu=" + jhatten + " jzaiko=" + jzaiko);
                return ItemStatus.OnStock;
            }
        } else if (this.jhatten > 0) {
            if (this.jzaiko > 0) {
                System.out.println("jhattyu > 0 && jzaiko > 0 : jhattyu=" + jhatten + " jzaiko=" + jzaiko);
                return ItemStatus.OnStock;
            } else if (this.jzaiko <= 0) {
                System.out.println("jhattyu > 0 && jzaiko <= 0 : jhattyu=" + jhatten + " jzaiko=" + jzaiko);
                return ItemStatus.Standard;
            }
        }

        return this.itemstatus;
    }

    public void SetSelectStatus(SelectStatus status) {
        this.selectstatus = status;
    }

    public SelectStatus GetSelectStatus() {
        return this.selectstatus;
    }

    public void SetErrorStatus(ErrorStatus status) {
        this.errorstatus = status;
    }

    public ErrorStatus GetErrorStatus() {
        return this.errorstatus;
    }

    public void SetIsSend(boolean send) {
        this.issend = send;
    }

    public boolean GetIsSend() {
        return this.issend;
    }


    public String GetJANCode() {
        return this.jancode;
    }

    public String GetShohinCode() {
        return this.shohincode;
    }

    public int GetJHattyu() {
        return this.jhatten;
    }


    private static ArrayList<ShelfStatus> shelfstatuslist = new ArrayList<>();

    private static ShelfStatus lateststatus;

    private static ArrayList<String> keys = new ArrayList<>();

    public static ArrayList<ShelfStatus> GetShelfStatusList() {
        return shelfstatuslist;
    }

    public static ShelfStatus[] GetShelfStatusArray() {
        ShelfStatus[] ss = new ShelfStatus[shelfstatuslist.size()];
        shelfstatuslist.toArray(ss);
        return ss;
    }

    public static void ClearShelfStatusList() {
        shelfstatuslist.clear();
    }

    public static void AddShelfStatusList() {
        shelfstatuslist.add(lateststatus);
        lateststatus = new ShelfStatus();
    }

    public static void SetShelfListIsSendAll(boolean send) {
        for (ShelfStatus status : shelfstatuslist) {
            status.SetIsSend(send);
        }
    }


    public static void SetShelfStatus(ShelfStatus status) {
        lateststatus = status;
    }

    public static ShelfStatus GetLatestShelfStatus() {
        return lateststatus;
    }


    public static void SetNewKey(String key) {
        if (HasKey(key) == false) {
            keys.add(key);
        }
    }

    public static boolean HasKey(String key) {
        return keys.contains(key);
    }

    public static ShelfStatus AnalysisInputStream(InputStream stream, String encoding) {
        System.out.println("call:AnalysisInputStream");
        System.out.println("encoding:" + encoding);
        //ShelfStatus status = new ShelfStatus();
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, encoding));
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            return ShelfStatus.ParseJsonString(builder.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static String ParseInfoString(String base) {
        try {
            ShelfStatus status = ShelfStatus.GetLatestShelfStatus();

            Object[] args = {
                    status.shohinban, status.jyotai, status.arari, status.mise,
                    status.jzaiko, status.jzaihatsu, status.jiso, status.jhatten, status.jsyubai1, status.jsyubai2, status.jsyubai3,
                    status.zzaiko, status.zzaihatsu, status.ziso, status.zhatten, status.zsyubai1, status.zsyubai2, status.zsyubai3
            };

            String after = String.format(base, args);
            System.out.println(after);

            return after;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());

            return "";
        }
    }

    public static ShelfStatus ParseJsonString(String jsontext) {
        System.out.println("call:ParseJsonString");
        System.out.println("jsontext:" + jsontext);

        ShelfStatus status = new ShelfStatus();
        status.jsontxt = jsontext;

        try {
            JSONObject json = new JSONObject(status.jsontxt);

            //ここでjsonをShelfStatusにパーサする
            status.jancode = json.getString("JANCode");
            System.out.println("JANCode:" + status.jancode);

            status.shohinban = json.getString("ShohinBan");
            System.out.println("ShohinBan:" + status.shohinban);

            status.shohincode = json.getString("ShohinCode");
            System.out.println("ShohinCode:" + status.shohincode);

            status.mise = json.getString("Mise");
            System.out.println("Mise:" + status.mise);

            status.jyotai = json.getString("Jyotai");
            System.out.println("Jyotai:" + status.jyotai);


            status.jhatten = json.getInt("JHatten");
            System.out.println("JHatten:" + status.jhatten);

            status.jzaiko = json.getInt("JZaiko");
            System.out.println("JZaiko:" + status.jzaiko);

            status.jzaihatsu = json.getInt("JZaiHatsu");
            System.out.println("JZaiHatsu:" + status.jzaihatsu);

            status.jiso = json.getInt("JIso");
            System.out.println("JIso:" + status.jiso);

            status.jsyubai1 = json.getInt("JSyubai1");
            System.out.println("JSyubai1:" + status.jsyubai1);

            status.jsyubai2 = json.getInt("JSyubai2");
            System.out.println("JSyubai2:" + status.jsyubai2);

            status.jsyubai3 = json.getInt("JSyubai3");
            System.out.println("JSyubai3:" + status.jsyubai3);


            status.zhatten = json.getInt("ZHatten");
            System.out.println("ZHatten:" + status.zhatten);

            status.zzaiko = json.getInt("ZZaiko");
            System.out.println("ZZaiko:" + status.zzaiko);

            status.zzaihatsu = json.getInt("ZZaiHatsu");
            System.out.println("ZZaiHatsu:" + status.zzaihatsu);

            status.ziso = json.getInt("ZIso");
            System.out.println("ZIso:" + status.ziso);

            status.zsyubai1 = json.getInt("ZSyubai1");
            System.out.println("ZSyubai1:" + status.zsyubai1);

            status.zsyubai2 = json.getInt("ZSyubai2");
            System.out.println("ZSyubai2:" + status.zsyubai2);

            status.zsyubai3 = json.getInt("ZSyubai3");
            System.out.println("ZSyubai3:" + status.zsyubai3);


            status.arari = json.getInt("Arari");
            System.out.println("Arari:" + status.arari);

        } catch (JSONException jex) {
            System.out.println(jex.getMessage());
            status.SetErrorStatus(ErrorStatus.JsonParseError);
        }

        return status;
    }


    public static String GetItemStatusText(ItemStatus status) {
        switch (status) {
            case OnStock:
                return "在庫あり";
            case Standard:
                return "定番品";
            case NotStandard:
                return "削定番品";
            default:
                return "";
        }
    }

    public static String GetSelectStatusText(SelectStatus status) {
        switch (status) {
            case PopRemove:
                return "POPを撤去";
            case Transfer:
                return "移送を依頼する";
            case Display:
                return "陳列(または在庫check)指示";
            case PopCreate:
                return "POP作成";
            case OPIncrease:
                return "発点増申請";
            default:
                return "";
        }
    }

    public static String GetSelectStatusJsonValue(SelectStatus status) {
        switch (status) {
            case PopRemove:
                return "POP撤去";
            case Transfer:
                return "移送依頼";
            case Display:
                return "陳列指示";
            case PopCreate:
                return "POP作成";
            case OPIncrease:
                return "発点増申請";
            default:
                return "";
        }
    }

    public enum ItemStatus {
        OnStock,
        Standard,
        NotStandard,
        NONE,
    }

    public enum SelectStatus {
        PopRemove,
        Transfer,
        Display,
        PopCreate,
        OPIncrease,
        NONE,
    }

    public enum ErrorStatus {
        HttpGetError,
        JsonParseError,
        NONE,
    }
}
