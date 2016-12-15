package immediate.shopdiscounts.api;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import immediate.shopdiscounts.MainActivity;


public class Interactor extends AsyncTask<Void, Void, List<Item>> {

    private MainActivity mMainActivity;

    public Interactor(MainActivity mainActivity)
    {
        mMainActivity = mainActivity;
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Item> items = new ArrayList<>();
        try {
            URLConnection conn = new URL("http://shopdiscounts.herokuapp.com/all_dixy_items").openConnection();
            conn.setUseCaches(false);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String s;

            do {
                s = reader.readLine();
                if (s != null) {
                    sb.append(s);
                }
            } while (s != null);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        String jsonResponse = sb.toString();

        try {
            JSONArray array = new JSONArray(jsonResponse);
            int len = array.length();

            for (int i = 0; i < len; i++) {
                items.add(Item.fromJSONObject(array.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }

    @Override
    protected void onPostExecute(List<Item> items) {
        mMainActivity.adapter.addAll(items);
    }
}
