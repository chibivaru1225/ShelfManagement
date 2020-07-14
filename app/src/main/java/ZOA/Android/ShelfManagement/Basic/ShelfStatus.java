package ZOA.Android.ShelfManagement.Basic;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


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
        shohinban = "";
        shohincode = "";
        mise = "";
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
        shohinban = "";
        shohincode = "";
        mise = "";
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

        if (this.jzaiko <= 0) {
            if (this.jzaihatsu <= 0 && this.jiso <= 0 && this.jhatten <= 0) {
                return ItemStatus.OnArrival;
            } else {
                return ItemStatus.NotArrival;
            }
        } else if (this.jzaiko > 0) {
            return ItemStatus.OnStock;
        }

//        if (this.jhatten == 0) {
//            if (this.jzaiko <= 0) {
//                System.out.println("jhattyu == 0 && jzaiko <= 0 : jhattyu=" + jhatten + " jzaiko=" + jzaiko);
//                return ItemStatus.NotArrival;
//            } else if (this.jzaiko > 0) {
//                System.out.println("jhattyu == 0 && jzaiko > 0 : jhattyu=" + jhatten + " jzaiko=" + jzaiko);
//                return ItemStatus.OnStock;
//            }
//        } else if (this.jhatten > 0) {
//            if (this.jzaiko > 0) {
//                System.out.println("jhattyu > 0 && jzaiko > 0 : jhattyu=" + jhatten + " jzaiko=" + jzaiko);
//                return ItemStatus.OnStock;
//            } else if (this.jzaiko <= 0) {
//                System.out.println("jhattyu > 0 && jzaiko <= 0 : jhattyu=" + jhatten + " jzaiko=" + jzaiko);
//                return ItemStatus.OnArrival;
//            }
//        }

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

    public String GetShohinBan() {
        return this.shohinban;
    }

    public String GetShohinCode() {
        return this.shohincode;
    }

    public String GetJyotai() {
        return this.jyotai;
    }

    public String GetMise() {
        return this.mise;
    }


    public int GetJZaiko() {
        return this.jzaiko;
    }

    public int GetJZaiHatsu() {
        return this.jzaihatsu;
    }

    public int GetJIso() {
        return this.jiso;
    }

    public int GetJHatten() {
        return this.jhatten;
    }

    public int GetJSyubai1() {
        return this.jsyubai1;
    }

    public int GetJSyubai2() {
        return this.jsyubai2;
    }

    public int GetJSyubai3() {
        return this.jsyubai3;
    }


    public int GetZZaiko() {
        return this.zzaiko;
    }

    public int GetZZaiHatsu() {
        return this.zzaihatsu;
    }

    public int GetZIso() {
        return this.ziso;
    }

    public int GetZHatten() {
        return this.zhatten;
    }

    public int GetZSyubai1() {
        return this.zsyubai1;
    }

    public int GetZSyubai2() {
        return this.zsyubai2;
    }

    public int GetZSyubai3() {
        return this.zsyubai3;
    }


    public int GetArari() {
        return this.arari;
    }


    private static ArrayList<ShelfStatus> shelfstatuslist = new ArrayList<>();

    private static ShelfStatus lateststatus;

    private static ArrayList<String> keys = new ArrayList<>();

    public static ArrayList<ShelfStatus> GetShelfStatusList() {
        return shelfstatuslist;
    }

    public static int GetNotNoneShelfStatusCount() {
        int r = 0;

        for (ShelfStatus status : shelfstatuslist) {
            if (status.GetSelectStatus() != SelectStatus.NONE)
                r++;
        }

        return r;
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

    public static String ConvertCSVString() {
        String txt = new String();
        StringBuilder builder = new StringBuilder();

        for (ShelfStatus status : ShelfStatus.GetShelfStatusArray()) {
            //何もしない、は送らない
            if (status.GetSelectStatus() == SelectStatus.NONE)
                continue;

//            txt += "\"" + Util.IPAddress + "\",";
//            txt += "\"" + status.jancode + "\",";
//            txt += "\"" + ShelfStatus.GetSelectStatusCSVValue(status.GetSelectStatus()) + "\",";
//            txt += "\"" + ShelfStatus.GetSelectStatusCSVBikoValue(status.GetSelectStatus()) + "\"" + Util.LF;

            //txt += Util.IPAddress + ",";
            txt += status.jancode + ",";
            txt += ShelfStatus.GetSelectStatusCSVValue(status.GetSelectStatus()) + ",";
            txt += ShelfStatus.GetSelectStatusCSVBikoValue(status.GetSelectStatus()) + Util.LF;

            builder.append("\"" + Util.IPAddress + "\",");
            builder.append("\"" + status.jancode + "\",");
            builder.append("\"" + ShelfStatus.GetSelectStatusCSVValue(status.GetSelectStatus()) + "\",");
            builder.append("\"" + ShelfStatus.GetSelectStatusCSVBikoValue(status.GetSelectStatus()) + "\"" + Util.LF);
        }

        System.out.println("ConvertCSVString:" + txt);
        System.out.println("ConvertCSVStringBuilder:" + builder.toString());
        return txt;
    }


    public static ShelfStatus ParseJsonString(String jsontext) {
        System.out.println("call:ParseJsonString");
        System.out.println("jsontext:" + jsontext);

        ShelfStatus status = new ShelfStatus();
        status.jsontxt = jsontext;

        try {
            JSONObject json = new JSONObject(status.jsontxt);

            //ここでjsonをShelfStatusにパーサする
            if (json.has("JANCode")) {
                status.jancode = json.getString("JANCode");
                System.out.println("JANCode:" + status.jancode);
            }

            if (json.has("ShohinBan")) {
                status.shohinban = json.getString("ShohinBan");
                System.out.println("ShohinBan:" + status.shohinban);
            }

            if (json.has("ShohinCode")) {
                status.shohincode = json.getString("ShohinCode");
                System.out.println("ShohinCode:" + status.shohincode);
            }

            if (json.has("Mise")) {
                status.mise = json.getString("Mise");
                System.out.println("Mise:" + status.mise);
            }

            if (json.has("Jyotai")) {
                status.jyotai = json.getString("Jyotai");
                System.out.println("Jyotai:" + status.jyotai);
            }


            if (json.has("JHatten")) {
                status.jhatten = json.getInt("JHatten");
                System.out.println("JHatten:" + status.jhatten);
            }

            if (json.has("JZaiko")) {
                status.jzaiko = json.getInt("JZaiko");
                System.out.println("JZaiko:" + status.jzaiko);
            }

            if (json.has("JZaiHatsu")) {
                status.jzaihatsu = json.getInt("JZaiHatsu");
                System.out.println("JZaiHatsu:" + status.jzaihatsu);
            }

            if (json.has("JIso")) {
                status.jiso = json.getInt("JIso");
                System.out.println("JIso:" + status.jiso);
            }

            if (json.has("JSyubai1")) {
                status.jsyubai1 = json.getInt("JSyubai1");
                System.out.println("JSyubai1:" + status.jsyubai1);
            }

            if (json.has("JSyubai2")) {
                status.jsyubai2 = json.getInt("JSyubai2");
                System.out.println("JSyubai2:" + status.jsyubai2);
            }

            if (json.has("JSyubai3")) {
                status.jsyubai3 = json.getInt("JSyubai3");
                System.out.println("JSyubai3:" + status.jsyubai3);
            }


            if (json.has("ZHatten")) {
                status.zhatten = json.getInt("ZHatten");
                System.out.println("ZHatten:" + status.zhatten);
            }

            if (json.has("ZZaiko")) {
                status.zzaiko = json.getInt("ZZaiko");
                System.out.println("ZZaiko:" + status.zzaiko);
            }

            if (json.has("ZZaiHatsu")) {
                status.zzaihatsu = json.getInt("ZZaiHatsu");
                System.out.println("ZZaiHatsu:" + status.zzaihatsu);
            }

            if (json.has("ZIso")) {
                status.ziso = json.getInt("ZIso");
                System.out.println("ZIso:" + status.ziso);
            }

            if (json.has("ZSyubai1")) {
                status.zsyubai1 = json.getInt("ZSyubai1");
                System.out.println("ZSyubai1:" + status.zsyubai1);
            }

            if (json.has("ZSyubai2")) {
                status.zsyubai2 = json.getInt("ZSyubai2");
                System.out.println("ZSyubai2:" + status.zsyubai2);
            }

            if (json.has("ZSyubai3")) {
                status.zsyubai3 = json.getInt("ZSyubai3");
                System.out.println("ZSyubai3:" + status.zsyubai3);
            }


            if (json.has("Arari")) {
                status.arari = json.getInt("Arari");
                System.out.println("Arari:" + status.arari);
            }

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
            case OnArrival:
                return "入荷予定あり";
            case NotArrival:
                return "入荷予定なし";
            default:
                return "";
        }
    }

    public static String GetSelectStatusText(SelectStatus status) {
        switch (status) {
            case PopRemove:
                return "POPを外す";
            case Transfer:
                return "補充依頼する";
            case Display:
                return "陳列･在庫チェック";
            case PopCreate:
                return "POP出力";
            case OPIncrease:
                return "発点増申請";
            case OPDecrease:
                return "発点減申請";
            default:
                return "何もしない";
        }
    }

    public static String GetSelectStatusCSVValue(SelectStatus status) {
        switch (status) {
            case PopRemove:
                return "ＰＯＰを外す";
            case Transfer:
                return "補充依頼する";
            case Display:
                return "陳列在庫ﾁｪｯｸ";
            case PopCreate:
                return "ＰＯＰ出力";
            case OPIncrease:
            case OPDecrease:
                return "発注点変更";
            default:
                return "";
        }
    }

    public static String GetSelectStatusCSVValueB(SelectStatus status) {
        switch (status) {
            case PopRemove:
                return "1";
            case Transfer:
                return "2";
            case Display:
                return "3";
            case PopCreate:
                return "4";
            case OPIncrease:
            case OPDecrease:
                return "5";
            default:
                return "0";
        }
    }

    public static String GetSelectStatusCSVBikoValue(SelectStatus status) {
        switch (status) {
            case OPIncrease:
                return "増やす";
            case OPDecrease:
                return "減らす";
            default:
                return "";
        }
    }

    public static String GetSelectStatusCSVBikoValueB(SelectStatus status) {
        switch (status) {
            case OPIncrease:
                return "1";
            case OPDecrease:
                return "2";
            default:
                return "0";
        }
    }

    public static String GetSelectStatusJsonValue(SelectStatus status) {
        switch (status) {
            case PopRemove:
                return "POPを外す";
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
        OnArrival,
        NotArrival,
        NONE,
    }

    public enum SelectStatus {
        PopRemove,
        Transfer,
        Display,
        PopCreate,
        OPIncrease,
        OPDecrease,
        NONE,
    }

    public enum ErrorStatus {
        HttpGetError,
        JsonParseError,
        NONE,
    }
}
