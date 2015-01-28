package com.example.danco.homework2.h253danco.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danco.homework2.h253danco.R;

import java.util.List;

/**
 * Created by danco on 1/27/15.
 */
public class DrawerListAdapter extends BaseAdapter {

    private List<String> sections;

    public DrawerListAdapter(List<String> sections) {
        super();

        this.sections = sections;
    }


    @Override
    public int getCount() {
        return sections.size();
    }


    @Override
    public Object getItem(int position) {
        return sections.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.drawer_list, parent, false);
            // Using a view holder as adapter views are recycled. Only need to create a view if the
            // convert view is null
            view.setTag(new ViewHolder(view));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.title.setText(sections.get(position));
        holder.icon.setImageResource(R.drawable.ic_action_action_accessibility);

        return view;
    }


    /* package */ static class ViewHolder {
        final TextView title;
        final ImageView icon;

        ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.section_label);
            icon = (ImageView) view.findViewById(R.id.drawer_item_icon);
        }
    }

}
