package ZOA.Android.ShelfManagement.Task;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ZOA.Android.ShelfManagement.Basic.ShelfStatus;
import ZOA.Android.ShelfManagement.Basic.Util;

public class TaskHttpPost extends AsyncTask<String, Integer, Boolean> {

    private WifiP2pManager.ActionListener listener;
    private String filepath;
    private ShelfStatus status;
    private String result;

    private static TaskHttpPost instance;

    public static TaskHttpPost GetInstance() {
        if (instance == null)
            instance = new TaskHttpPost();
        return instance;
    }

    public String GetResult() {
        return this.result;
    }

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
    protected Boolean doInBackground(String... params) {
        if (this.listener instanceof AppCompatActivity == false || this.listener == null)
            return false;

        AppCompatActivity act = (AppCompatActivity) this.listener;
        //String fileName = params[0];

        try {
            Thread.sleep(1000);

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Util.PostURL);
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create()
//                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            ArrayList<NameValuePair> pair = new ArrayList<NameValuePair>();

            pair.add(new BasicNameValuePair("appname", "webhnctl"));
            pair.add(new BasicNameValuePair("prgname", "receiveTanaMenteData"));
            pair.add(new BasicNameValuePair("arguments", "sendData"));
            pair.add(new BasicNameValuePair("sendData", ShelfStatus.ConvertCSVString()));
            pair.add(new BasicNameValuePair("submit", "送信"));

//            builder.addTextBody("appname", "webhnctl");
//            builder.addTextBody("prgname", "uploadtest");
//            builder.addTextBody("arguments", "uploadfile");
//            builder.addPart("uploadfile", new FileBody(act.getFileStreamPath(fileName)))
//            builder.addTextBody("submit", "送信");

//            builder.setCharset(Charset.forName("Shift-JIS"));
//            builder.addTextBody("appname", "webhnctl");
//            builder.addTextBody("prgname", "receiveTanaMenteData");
//            builder.addTextBody("arguments", "sendData");
//            builder.addTextBody("sendData", new String(ShelfStatus.ConvertCSVString().getBytes("Shift-JIS"), "Shift-JIS"));
//            builder.addTextBody("submit", "送信");

//            HttpEntity entity = builder.build();

            httpPost.setEntity(new UrlEncodedFormEntity(pair,"Shift-JIS"));
//            httpPost.setEntity(entity);
            httpPost.setHeader("User-Agent", "ZOA.Android.ShelfManagement");
            httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpPost.setHeader("Accept-Language", "ja,en-US;q=0.7,en;q=0.3");
            httpPost.setHeader("Accept-Encoding", "gzip, deflate");
            httpPost.setHeader("Referer", "http://webhn/uploadtest.html");
            httpPost.setHeader("Connection", "keep-alive");
            //httpPost.setHeader("Accept-Charset", "Shift-JIS");
            httpPost.setHeader("Cache-Control", "no-cache");

            HttpParams httpParams = httpClient.getParams();
//            接続確立のタイムアウトを設定（単位：ms）
            HttpConnectionParams.setConnectionTimeout(httpParams, 1000 * 10);
//            接続後のタイムアウトを設定（単位：ms）
            HttpConnectionParams.setSoTimeout(httpParams, 1000 * 10 * 2);


            //HttpUriRequest request = RequestBuilder.post(Util.PostURL).setEntity(entity).build();

            System.out.println("doInBackground:" + httpPost.getRequestLine());
            System.out.println("doInBackground:" + Arrays.toString(httpPost.getAllHeaders()));
            //System.out.println("doInBackground:" + httpPost.getEntity().getContent());

            //System.out.println("doInBackground:" + entity.toString());
            HttpResponse response = httpClient.execute(httpPost);

            //System.out.println("res:" + response.getEntity().getContent());

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "shift_jis"));
            String line;
            String html = "";

            while ((line = reader.readLine()) != null) {
                System.out.println("Response:" + line);
                html += line;
            }

            this.result = html;
            System.out.println(html);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
