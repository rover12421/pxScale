package com.rover12421.pxscale.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rover12421 on 12/17/14.
 */
public class TestListAdapter extends BaseAdapter {

    private int[] icon_ids = new int[]{
            R.drawable.game_all,
            R.drawable.game_hand,
            R.drawable.game_remote,
            R.drawable.game_somatic
    };

    private List<String> list = new ArrayList<>();

    private Context context;

    public TestListAdapter(Context context) {
        this.context = context;
    }

    public void add(String str) {
        list.add(str);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String info = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.main_left, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_icon);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text_title);
            convertView.setTag(viewHolder);
            ScaleView.scaleView(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setImageResource(icon_ids[position%icon_ids.length]);
        viewHolder.textView.setText(info);

        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
