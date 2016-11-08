package com.assignment.contactassignment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Careena on 10/23/16.
 */
public class ContactList extends Fragment implements AdapterView.OnItemClickListener{

    ListView list;
//    ArrayList<String> cbList;
    ArrayList<ContactBean> cbClassList;
    Communicator communicator;
   // ArrayAdapter adapter;
    Button addButton;
    Button deleteButton;
    CustomAdapter customAdapter;
    boolean itemClicked;
    static boolean boxChecked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contact_list_layout, container, false);
         return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        ContactBean contact1 = new ContactBean("Harley", 732, 1);
//        ContactBean contact2 = new ContactBean("Joker", 73211, 2);
//        ContactBean contact3 = new ContactBean("SuperGuy", 1732, 3);
//        ContactBean contact4 = new ContactBean("BatBoy", 732, 4);

        ArrayList<ContactBean> relArray1 = new ArrayList<ContactBean>();
        ArrayList<ContactBean> relArray2 = new ArrayList<ContactBean>();
        ArrayList<ContactBean> relArray3 = new ArrayList<ContactBean>();
        ArrayList<ContactBean> relArray4 = new ArrayList<ContactBean>();




        ContactBean contact1 = new ContactBean("Harley", 732 );
        ContactBean contact2 = new ContactBean("Joker", 73211);
        ContactBean contact3 = new ContactBean("SuperGuy", 1732);
        ContactBean contact4 = new ContactBean("BatBoy", 732);

        relArray1.add(contact2);
        relArray1.add(contact3);
        relArray1.add(contact4);
        contact1.setRelationList(relArray1);


        relArray2.add(contact3);
        relArray2.add(contact4);
        contact2.setRelationList(relArray2);
        contact3.setRelationList(relArray2);
        contact4.setRelationList(relArray2);

        cbClassList = new ArrayList<ContactBean>();
        cbClassList.add(contact1);
        cbClassList.add(contact2);
       // cbClassList.add(contact3);
       // cbClassList.add(contact4);

//        cbList = new ArrayList<String>();
//        cbList.add(contact1.getName());
//        cbList.add(contact2.getName());
       // cbList.add(contact3.getName());
        //cbList.add(contact4.getName());


        //if this is written in onCreate ---- use view.findViewById
        list = (ListView) getActivity().findViewById(R.id.listView);
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.titles, android.R.layout.simple_list_item_1);
        // adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice, cbList);

        customAdapter = new CustomAdapter(getActivity(), R.layout.double_row, cbClassList);

       // list.setAdapter(adapter);
       // list.setOnItemClickListener(this);

        list.setAdapter(customAdapter);
        list.setOnItemClickListener(this);



//        customAdapter.listItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("listItemClicked " + customAdapter.listItem);
//            }
//        });

        addButton = (Button) getActivity().findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Careena Add Button");
                communicator.add(cbClassList);
            }
        });

        deleteButton = (Button) getActivity().findViewById(R.id.removeButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("3403 ---- d delete Button");

                for (int i=0; i < cbClassList.size(); i++){
                    System.out.println("3403 ---- d delete Button " + cbClassList.get(i).isDeleteRequested());
                    if(cbClassList.get(i).isDeleteRequested()){
                        System.out.println("3403 ---- delete requested");

                        ContactBean cbDelete = cbClassList.get(i);
                        cbClassList.remove(i);
                        cbDelete = null;

                    }
                }

                list.setAdapter(customAdapter);
            }
        });


    }


    public void addNewItem(ContactBean contactItem){
        cbClassList.add(contactItem);
        list.setAdapter(customAdapter);


    }

    public void setCommunicator(Communicator c){
        this.communicator = c;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (int i = 0; i < cbClassList.size(); i++) {
            cbClassList.get(i).setDeleteRequested(false);
        }
    }

    @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ContactBean cbItem = customAdapter.getItem(position);
        System.out.println("3403 ---- item clicked");
        communicator.respondForProfile(cbItem);
    }

}
