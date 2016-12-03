package immediate.shopdiscounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import immediate.shopdiscounts.api.Interactor;
import immediate.shopdiscounts.api.Item;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Interactor i = new Interactor();
        try {
            List<Item> res = i.execute().get();
            Log.d("MA", "Ok;");
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }
}
