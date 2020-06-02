package ZOA.Android.ShelfManagement.Task;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;

import com.google.gson.Gson;

import ZOA.Android.ShelfManagement.JSON.StatusJSONParent;
import ZOA.Android.ShelfManagement.Basic.ShelfStatus;

public class TaskHttpPost extends AsyncTask<ShelfStatus, Integer, Boolean> {


    private WifiP2pManager.ActionListener listener;
    private ShelfStatus status;

    public TaskHttpPost() {
        this.listener = null;
    }

    public boolean setActionListener(WifiP2pManager.ActionListener listener) {
        try {
            this.listener = listener;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected Boolean doInBackground(ShelfStatus... shelfStatus) {
        StatusJSONParent data = StatusJSONParent.ConvertStatusJSON(ShelfStatus.GetShelfStatusList());


        try {
            Gson gson = new Gson();
            String json = gson.toJson(data);


        } catch (Exception e) {

        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if (result) {
            listener.onSuccess();
        } else {
            listener.onFailure(0);
        }
    }
}
