package ZOA.Android.ShelfManagement;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;

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
        synchronized (this)
        {
            try {
                wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
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
