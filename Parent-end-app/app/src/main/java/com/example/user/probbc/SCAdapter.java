package com.example.user.probbc;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Harshad Shinde on 29-01-2016.
 */

public class SCAdapter extends BaseAdapter{
    private final Activity context;
    private  ArrayList<StudentStatusModel> list;

    public SCAdapter(Context c,ArrayList<StudentStatusModel> list) {
        context = (Activity) c;
        this.list = list;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.row_layout, null, true);

            ImageView pic = (ImageView) rowView.findViewById(R.id.pic);
            TextView name = (TextView) rowView.findViewById(R.id.name);
            TextView status = (TextView) rowView.findViewById(R.id.s_childStatus);





        name.setText(list.get(position).getStudentName());
        status.setText(list.get(position).getStudentStatus());

        return rowView;
    }
}
