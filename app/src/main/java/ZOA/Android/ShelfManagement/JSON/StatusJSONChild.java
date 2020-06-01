package ZOA.Android.ShelfManagement.JSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusJSONChild {
    @SerializedName("JANCode")
    @Expose
    public String jancode;

    @SerializedName("Select")
    @Expose
    public String select;

    @SerializedName("Biko")
    @Expose
    public String biko;
}
