import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.xin.dropdownlistview.R;

import java.util.logging.Handler;

/**
 * DropDownListViewDemo 
 *
 * @author Trinea 2013-6-1 
 */
public class MainActivity extends Activity {

    protected static final String TAG = "MainActivity";

    private ZListView listView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ZListView) findViewById(R.id.listview);
        listView.setXListViewListener(new IXListViewListener() {

            @Override
            public void onRefresh() {

                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        listView.stopRefresh();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {

                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        listView.stopLoadMore();
                    }
                }, 1000);

            }
        });

        listView.setPullLoadEnable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(MainActivity.this, "onItemClick=" + position,
                        Toast.LENGTH_SHORT).show();

            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(MainActivity.this,
                        "onItemLongClick=" + position, Toast.LENGTH_SHORT)
                        .show();
                return true;
            }
        });

        listView.setAdapter(new ListViewAdapter(this));

    }

}