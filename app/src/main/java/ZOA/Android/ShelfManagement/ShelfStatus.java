package ZOA.Android.ShelfManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class ShelfStatus {

    private ItemStatus itemstatus;
    private SelectStatus selectstatus;
    private String ipaddress;
    private String stream;
    private boolean issend;

    private String jancode;
    private String itemname;
    private String jyotai;
    private int zaiko;
    private int hattyu;
    private int zaihatsu;
    private int iso;
    private int syubai1;
    private int syubai2;
    private int syubai3;
    private int arari;

    public ShelfStatus() {
        itemstatus = ItemStatus.NONE;
        selectstatus = SelectStatus.NONE;
        jancode = "";
        itemname = "";
        jyotai = "";
        issend = true;
        //ipaddress =

        zaiko = 0;
        hattyu = 0;
        zaihatsu = 0;
        iso = 0;
        syubai1 = 0;
        syubai2 = 0;
        syubai3 = 0;
        arari = 0;
    }

    public ShelfStatus(ItemStatus status) {
        itemstatus = status;
        selectstatus = SelectStatus.NONE;
        jancode = "";
        itemname = "";
        jyotai = "";
        issend = true;

        zaiko = 0;
        hattyu = 0;
        zaihatsu = 0;
        iso = 0;
        syubai1 = 0;
        syubai2 = 0;
        syubai3 = 0;
        arari = 0;
    }

    public void SetShelfStatus(int zaiko, int orderpoint) {
        this.zaiko = zaiko;
        this.hattyu = orderpoint;
    }

    public void SetItemStatus(ItemStatus status) {
        this.itemstatus = status;
    }

    public ItemStatus GetItemStatus() {

        if (this.hattyu == 0) {
            if (this.zaiko <= 0) {
                System.out.println("hattyu == 0 && zaiko <= 0 : hattyu=" + hattyu + " zaiko=" + zaiko);
                return ItemStatus.NotStandard;
            } else if (this.zaiko > 0) {
                System.out.println("hattyu == 0 && zaiko > 0 : hattyu=" + hattyu + " zaiko=" + zaiko);
                return ItemStatus.OnStock;
            }
        } else if (this.hattyu > 0) {
            if (this.zaiko > 0) {
                System.out.println("hattyu > 0 && zaiko > 0 : hattyu=" + hattyu + " zaiko=" + zaiko);
                return ItemStatus.OnStock;
            } else if (this.zaiko <= 0) {
                System.out.println("hattyu > 0 && zaiko <= 0 : hattyu=" + hattyu + " zaiko=" + zaiko);
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

    public void SetIsSend(boolean send) {
        this.issend = send;
    }

    public boolean GetIsSend() {
        return this.issend;
    }


    public void SetJANCode(String jan) {
        this.jancode = jan;
    }

    public String GetJANCode() {
        return this.jancode;
    }

    public void SetItemName(String itemname) {
        this.itemname = itemname;
    }

    public String GetItemName() {
        return this.itemname;
    }

    public void SetJyotai(String jyotai) {
        this.jyotai = jyotai;
    }

    public String GetJyotai() {
        return this.jyotai;
    }

    public void SetHattyu(int op) {
        this.hattyu = op;
    }

    public int GetHattyu() {
        return this.hattyu;
    }

    public void SetZaiko(int za) {
        this.zaiko = za;
    }

    public int GetZaiko() {
        return this.zaiko;
    }

    public void SetZaihatsu(int za) {
        this.zaihatsu = za;
    }

    public int GetZaihatsu() {
        return this.zaihatsu;
    }

    public void SetIso(int is) {
        this.iso = is;
    }

    public int GetIso() {
        return this.iso;
    }

    public void SetSyubai1(int sy) {
        this.syubai1 = sy;
    }

    public int GetSyubai1() {
        return this.syubai1;
    }

    public void SetSyubai2(int sy) {
        this.syubai2 = sy;
    }

    public int GetSyubai2() {
        return this.syubai2;
    }

    public void SetSyubai3(int sy) {
        this.syubai3 = sy;
    }

    public int GetSyubai3() {
        return this.syubai3;
    }

    public void SetArari(int ar) {
        this.arari = ar;
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
//        ShelfStatus.ItemStatus istatus = ShelfStatus.ItemStatus.NONE;
//        String itemname = "";
//        Random random = new Random();
//        int randomValue = random.nextInt(100);
//
//        switch (randomValue % 3) {
//            case 0:
//                istatus = ShelfStatus.ItemStatus.OnStock;
//                break;
//            case 1:
//                istatus = ShelfStatus.ItemStatus.Standard;
//                break;
//            case 2:
//                istatus = ShelfStatus.ItemStatus.NotStandard;
//                break;
//        }
//
//        switch (randomValue % 10) {
//            case 0:
//                itemname = "REWF18AW";
//                break;
//            case 1:
//                itemname = "FTJ161AMUSASHIBK";
//                break;
//            case 2:
//                itemname = "ES60WB";
//                break;
//            case 3:
//                itemname = "HP484DRGX";
//                break;
//            case 4:
//                itemname = "S531FABQ229T";
//                break;
//            case 5:
//                itemname = "GF6310SCXR026JP";
//                break;
//            case 6:
//                itemname = "MW792JA";
//                break;
//            case 7:
//                itemname = "MODERN15A10RAS061JP";
//                break;
//            case 8:
//                itemname = "PCNM750RAG";
//                break;
//            case 9:
//                itemname = "GEFORCERTX2080TIVENTUSGP";
//                break;
//        }

        //System.out.println("ステータス");
//        status.SetItemStatus(istatus);
//        status.SetItemName(itemname);
        //status.stream = stream.toString();


        //return status;
    }

    public static ShelfStatus ParseJsonString(String jsontext) {
        System.out.println("call:ParseJsonString");
        System.out.println("jsontext:" + jsontext);

        ShelfStatus status = new ShelfStatus();

        try {
            JSONObject json = new JSONObject(jsontext);

            //ここでjsonをShelfStatusにパーサする
            status.SetJANCode(json.getString("JANCode"));
            System.out.println("JANCode:" + status.GetJANCode());

            status.SetItemName(json.getString("ShohinCode"));
            System.out.println("ShohinCode:" + status.GetItemName());

            status.SetJyotai(json.getString("Jyotai"));
            System.out.println("Jyotai:" + status.GetJyotai());

            status.SetHattyu(json.getInt("Hattyu"));
            System.out.println("Hattyu:" + status.GetHattyu());

            status.SetZaiko(json.getInt("Zaiko"));
            System.out.println("Zaiko:" + status.GetZaiko());

            status.SetZaihatsu(json.getInt("ZaiHatsu"));
            System.out.println("ZaiHatsu:" + status.GetZaihatsu());

            status.SetIso(json.getInt("Iso"));
            System.out.println("Iso:" + status.GetIso());

            status.SetSyubai1(json.getInt("Syubai1"));
            System.out.println("Syubai1:" + status.GetSyubai1());

            status.SetSyubai2(json.getInt("Syubai2"));
            System.out.println("Syubai2:" + status.GetSyubai2());

            status.SetSyubai3(json.getInt("Syubai3"));
            System.out.println("Syubai3:" + status.GetSyubai3());

            status.SetArari(json.getInt("Arari"));
            System.out.println("Arari:" + status.GetArari());

        } catch (JSONException jex) {
            System.out.println(jex.getMessage());
            return null;
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
}
