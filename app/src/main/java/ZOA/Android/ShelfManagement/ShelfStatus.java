package ZOA.Android.ShelfManagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class ShelfStatus {

    private ItemStatus itemstatus;
    private SelectStatus selectstatus;
    private int zaiko;
    private int orderpoint;
    private String ipaddress;
    private String stream;
    private String itemname;
    private boolean issend;

    public ShelfStatus() {
        itemstatus = ItemStatus.NONE;
        selectstatus = SelectStatus.NONE;
        zaiko = 0;
        orderpoint = 0;
        itemname = "";
        issend = true;
        //ipaddress =
    }

    public ShelfStatus(ItemStatus status) {
        this.itemstatus = status;
        this.selectstatus = SelectStatus.NONE;
        zaiko = 0;
        orderpoint = 0;
        itemname = "";
        issend = true;
    }

    public void SetShelfStatus(int zaiko, int orderpoint) {
        this.zaiko = zaiko;
        this.orderpoint = orderpoint;

        if (orderpoint == 0) {
            if (zaiko <= 0) {
                itemstatus = ItemStatus.NotStandard;
            } else if (zaiko > 0) {
                itemstatus = ItemStatus.OnStock;
            }
        } else if (orderpoint > 0) {
            if (zaiko > 0) {
                itemstatus = ItemStatus.OnStock;
            } else if (zaiko <= 0) {
                itemstatus = ItemStatus.Standard;
            }
        }
    }

    public void SetItemStatus(ItemStatus status) {
        this.itemstatus = status;
    }

    public ItemStatus GetItemStatus() {
        return this.itemstatus;
    }

    public void SetSelectStatus(SelectStatus status) {
        this.selectstatus = status;
    }

    public SelectStatus GetSelectStatus() {
        return this.selectstatus;
    }

    public void SetItemName(String itemname) {
        this.itemname = itemname;
    }

    public String GetItemName() {
        return this.itemname;
    }

    public void SetIsSend(boolean send) {
        this.issend = send;
    }

    public boolean GetIsSend() {
        return this.issend;
    }





    private static ArrayList<ShelfStatus> shelfstatuslist = new ArrayList<>();

    private static ShelfStatus lateststatus;

    private static ArrayList<String> keys = new ArrayList<>();

    public static ArrayList<ShelfStatus> GetShelfStatusList() {
        return shelfstatuslist;
    }

    public static void ClearShelfStatusList() {
        shelfstatuslist.clear();
    }

    public static void AddShelfStatusList() {
        shelfstatuslist.add(lateststatus);
        lateststatus = new ShelfStatus();
    }

    public static void SetShelfListIsSendAll(boolean send)
    {
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
//        if (keys == null) {
//            keys = new ArrayList<>();
//        }

        if (HasKey(key) == false) {
            keys.add(key);
        }
    }

    public static boolean HasKey(String key) {
        return keys.contains(key);
    }

    public static ShelfStatus AnalysisInputStream(String key, InputStream stream, String encoding) {
        ShelfStatus status = new ShelfStatus();

        ShelfStatus.ItemStatus istatus = ShelfStatus.ItemStatus.NONE;
        String itemname = "";
        Random random = new Random();
        int randomValue = random.nextInt(100);

        switch (randomValue % 3) {
            case 0:
                istatus = ShelfStatus.ItemStatus.OnStock;
                break;
            case 1:
                istatus = ShelfStatus.ItemStatus.Standard;
                break;
            case 2:
                istatus = ShelfStatus.ItemStatus.NotStandard;
                break;
        }

        switch (randomValue % 10) {
            case 0:
                itemname = "REWF18AW";
                break;
            case 1:
                itemname = "FTJ161AMUSASHIBK";
                break;
            case 2:
                itemname = "ES60WB";
                break;
            case 3:
                itemname = "HP484DRGX";
                break;
            case 4:
                itemname = "S531FABQ229T";
                break;
            case 5:
                itemname = "GF6310SCXR026JP";
                break;
            case 6:
                itemname = "MW792JA";
                break;
            case 7:
                itemname = "MODERN15A10RAS061JP";
                break;
            case 8:
                itemname = "PCNM750RAG";
                break;
            case 9:
                itemname = "GEFORCERTX2080TIVENTUSGP";
                break;
        }

        //System.out.println("ステータス");
        status.SetItemStatus(istatus);
        status.SetItemName(itemname);
        //status.stream = stream.toString();

        return status;
    }

    public static String GetItemStatusText(ItemStatus status) {
        switch (status) {
            case OnStock:
                return "在庫あり";
            case Standard:
                return "削定番品";
            case NotStandard:
                return "定番品";
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
