package ZOA.Android.ShelfManagement.Task;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ZOA.Android.ShelfManagement.Basic.ShelfStatus;
import ZOA.Android.ShelfManagement.Basic.Util;

public class TaskHttpGet extends AsyncTask<String, Integer, ShelfStatus> {

    private WifiP2pManager.ActionListener listener;
    private ShelfStatus status;

    public TaskHttpGet() {
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
    protected ShelfStatus doInBackground(String... itemid) {
        // 使用するサーバーのURLに合わせる
        //String urlSt = "http://webhn.zoa.local/Magic94Scripts/MGRQISPI94.dll?APPNAME=WEBHNCTL&PRGNAME=itemSearchByJAN&ARGUMENTS=-N";
        //String urlSt = "http://webhn.zoa.local/Magic94Scripts/MGRQISPI94.dll?APPNAME=WEBHNCTL&PRGNAME=GetBhtItemInfo&ARGUMENTS=-N";

        HttpURLConnection httpConn = null;
        String urlfull = Util.GetURL + itemid[0];
        InputStream in;
        String encoding;
        //ShelfStatus.SetNewKey(itemid[0]);

        try {
            // URL設定
            URL url = new URL(urlfull);

            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            //httpConn.setConnectTimeout(0);
            //httpConn.setReadTimeout(0);
            httpConn.connect();

            int responseCode = httpConn.getResponseCode();

            System.out.println("code:" + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 通信に成功した
                // テキストを取得する
                in = httpConn.getInputStream();
                encoding = httpConn.getContentEncoding();
                System.out.println("getencoding:" + httpConn.getContentEncoding());
                if (null == encoding) {
                    encoding = "SHIFT-JIS";
                }

                return ShelfStatus.AnalysisInputStream(in, encoding);

            } else {
                return new ShelfStatus(ShelfStatus.ItemStatus.NONE, ShelfStatus.ErrorStatus.HttpGetError);
            }
//            try (// POSTデータ送信処理
//                 OutputStream outStream = httpConn.getOutputStream()) {
//                outStream.write(word.getBytes(StandardCharsets.UTF_8));
//                outStream.flush();
//            } catch (IOException e) {
//                // POST送信エラー
//                e.printStackTrace();
//            }
//
//            final int status = httpConn.getResponseCode();
//            if (status == HttpURLConnection.HTTP_OK) {
//                // レスポンスを受け取る処理等
//                result = "HTTP_OK";
//            } else {
//                result = "status=" + String.valueOf(status);
//            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ShelfStatus(ShelfStatus.ItemStatus.NONE, ShelfStatus.ErrorStatus.HttpGetError);
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }

        //return ShelfStatus.AnalysisInputStream(itemid[0], in, encoding);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected void onPostExecute(ShelfStatus result) {
        super.onPostExecute(result);
        this.status = result;

        if (listener != null) {
            if (status != null) {
                System.out.println("ItemStatus:" + status.GetItemStatus());
                System.out.println("ItemName:" + status.GetShohinCode() + ":eof");
                if (status.GetItemStatus() != ShelfStatus.ItemStatus.NONE && !status.GetShohinCode().isEmpty()) {
                    System.out.println("Success");
                    listener.onSuccess();
                } else {
                    switch (status.GetErrorStatus())
                    {
                        case HttpGetError:
                            listener.onFailure(1);
                            break;
                        case JsonParseError:
                            listener.onFailure(2);
                            break;
                        default:
                            listener.onFailure(0);
                            break;
                    }
                }
            } else {
                listener.onFailure(0);
            }
        }
        // ...
    }

    public ShelfStatus GetShelfStatus() {
        return this.status;
    }
}
