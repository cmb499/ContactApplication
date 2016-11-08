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
 * Created by Careena on 10/29/16.
 */
public class RelationAdapter extends ArrayAdapter<ContactBean> {

    TextView listItem;
     CheckBox checkBox;


    public RelationAdapter(Context context, int resource, ArrayList<ContactBean> cbList) {
        super(context, R.layout.double_row, cbList);
    }



    public void myFunc(){

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("3403 ---- list box clicked");
            }
        });

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("3403 ---- list item clicked");

            }
        });

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView = inflater.inflate(R.layout.double_row, parent, false);

        listItem = (TextView) customView.findViewById(R.id.list_item);
         checkBox = (CheckBox) customView.findViewById(R.id.checkBox);


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // System.out.println("3403  item in relation  " + getItem(position).getName());

                int originalPosition = position;

                ContactBean contactBeanLocal = getItem(position);

                if(!getItem(position).isChecked()) {
                    getItem(position).setChecked(true);
                   // remove(getItem(position));
                   // insert(contactBeanLocal, 0);
                    //checkBox.setChecked(true);
                    System.out.println("7777  1");
                }
                else{
                    getItem(position).setChecked(false);
                    System.out.println("7777  2");
                }


                //checkBox.setChecked(!checkBox.isChecked());
                // System.out.println("3403 ---- check box clicked " + getItem(position).getName() + "  " + getItem(position).isChecked());


            }
        });


        String name = getItem(position).getName();
        listItem.setText(name);

        return customView;

    }






}
