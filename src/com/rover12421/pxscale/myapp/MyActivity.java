package com.rover12421.pxscale.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MyActivity extends Activity {

    private ListView listView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ScaleView.init(this);
        ScaleView.scaleView(findViewById(R.id.layout_root));

        ListView listView = (ListView) findViewById(R.id.listview);
        TestListAdapter adapter = new TestListAdapter(this);
        listView.setAdapter(adapter);

        adapter.add("xxxxxxxxxxxxxxxxxxxxxxx");
        adapter.add("1111111111111111111");
        adapter.add("22222222222222222222222222");
        adapter.add("3333333333333333333333");
        adapter.add("444444444444444444444");
        adapter.add("555555555555555555555555");
        adapter.add("66666666666666666666666666");
        adapter.add("777777777777777777777777777");
        adapter.add("888888888888888888888888888");
        adapter.add("999999999999999999999999999");
        adapter.add("00000000000000000000000000000");
    }

}
