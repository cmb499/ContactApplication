package com.assignment.contactassignment;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by Careena on 10/23/16.
 */
public class ContactProfile extends Fragment {

    //TextView descView;
    TextView nameDesc;
    TextView phoneDesc;
    ListView relListView;
    String nameLocal;
    int phoneLocal;
    ArrayList<ContactBean> relListLocal;
    ArrayList<String> displayRelList;
    ArrayAdapter<String> relAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contact_profile_layout, container, false);
        phoneDesc = (TextView) view.findViewById(R.id.phoneDesc);
        nameDesc = (TextView) view.findViewById(R.id.nameDesc);
        relListView = (ListView) view.findViewById(R.id.relationListProfile);

        //problem
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && getActivity().getClass().getSimpleName().toString().equals("MainActivity")) {
            System.out.println("899 crashed with activity " + getActivity().getClass().getSimpleName().toString());
            displayLocalData();
        }

        return  view;
    }


    public void setLocalData(ContactBean cbItem){

         nameLocal = cbItem.getName();
         phoneLocal = cbItem.getContact();
         relListLocal = cbItem.getRelationList();
    }

    public void displayLocalData(){
        displayRelList = new ArrayList<>();

        if(relListLocal != null) {

            for (int i = 0; i < relListLocal.size(); i++) {
                displayRelList.add(relListLocal.get(i).getName());
            }

        }
        relAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, displayRelList);
        relListView.setAdapter(relAdapter);
        setOnClickListenerMeth();
        nameDesc.setText(nameLocal);
        phoneDesc.setText(String.valueOf(phoneLocal));

    }

        public void changeData(ContactBean cbItem){

            //return when changed from port to land
            if (getActivity().getClass().getSimpleName().toString().equals("ContactProfileActivity")) {
                ContactProfileActivity cp = (ContactProfileActivity) getActivity();
                cp.sendContactToMain = cbItem;
            }

                displayRelList = new ArrayList<>();
                relListLocal = cbItem.getRelationList();
                for (int i = 0; i < relListLocal.size(); i++) {
                    displayRelList.add(relListLocal.get(i).getName());
                }
                relAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, displayRelList);
                relListView.setAdapter(relAdapter);
                setOnClickListenerMeth();

                nameLocal = cbItem.getName();
                phoneLocal = cbItem.getContact();
                nameDesc.setText(nameLocal);
                phoneDesc.setText(String.valueOf(phoneLocal));

    }

    public void setOnClickListenerMeth(){
        relListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Intent intent = new Intent(getActivity(), ContactProfileActivity.class);
                    intent.putExtra("cbItem", (Serializable) relListLocal.get(position));
                    intent.putExtra("uniqID", "fromCPActivity");
                    startActivity(intent);
                } else {
                    ContactBean newContact = relListLocal.get(position);
                    setLocalData(newContact);
                    displayLocalData();
                }
            }
        });
    }

}
