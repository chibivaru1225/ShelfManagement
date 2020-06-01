package ZOA.Android.ShelfManagement.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ZOA.Android.ShelfManagement.Basic.ShelfStatus;
import ZOA.Android.ShelfManagement.Basic.Util;

public class StatusJSONParent {
    @SerializedName("IP")
    @Expose
    public String ip;

    @SerializedName("Items")
    @Expose
    public ArrayList<StatusJSONChild> items;

    public static StatusJSONParent ConvertStatusJSON(ArrayList<ShelfStatus> status) {
        StatusJSONParent parent = new StatusJSONParent();
        parent.ip = Util.IPAddress;

        ArrayList<StatusJSONChild> cl = new ArrayList<StatusJSONChild>();

        for (ShelfStatus key : status) {
            if (key.GetIsSend() == false)
                continue;

            StatusJSONChild child = new StatusJSONChild();

            child.jancode = key.GetJANCode();
            child.select = ShelfStatus.GetSelectStatusJsonValue(key.GetSelectStatus());
            child.biko = "なし";

            cl.add(child);
        }

        parent.items = cl;
        return parent;
    }
}
