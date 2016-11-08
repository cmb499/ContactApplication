package com.assignment.contactassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Careena on 10/24/16.
 */
public class CustomAdapter extends ArrayAdapter<ContactBean> {

    TextView listItem;
    CheckBox checkBox;

    public CustomAdapter(Context context, int resource, ArrayList<ContactBean> cbList) {
         super(context, R.layout.double_row, cbList);
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.double_row, parent, false);

         listItem = (TextView) customView.findViewById(R.id.list_item);
        checkBox = (CheckBox) customView.findViewById(R.id.checkBox);


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getItem(position).setDeleteRequested(!getItem(position).isDeleteRequested());

                System.out.println("3403 ---- check box clicked " + getItem(position).getName() + "  " + getItem(position).isDeleteRequested());
            }
        });


        String name = getItem(position).getName();
        listItem.setText(name);

        return customView;

    }


    public ContactBean onClickList(int position){
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("3403 ---- list item clicked");

            }
        });
        return getItem(position);
    }



}
