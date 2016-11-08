package com.assignment.contactassignment;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Careena on 10/24/16.
 */
public class ContactDetails extends Fragment implements Serializable{

    EditText nameEdit;
    EditText phoneEdit;
    ListView relationEdit;

    String nameReceived = "";
    int phoneReceived = 0;
    ArrayList<ContactBean> classRelList;
    ArrayList<ContactBean> addRelationItems; //will be added to contact
    RelationAdapter relAdapter; //ArrayAdapter<String> relAdapter;
    ContactBean contactItemSend;
    DetailsCommunicator detailsCommunicator;
    Communicator communicator;
    Button addContactDetails;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.contact_details_layout, container, false);
        nameEdit = (EditText) view.findViewById(R.id.nameEdit);
        phoneEdit = (EditText) view.findViewById(R.id.phoneEdit);
        relationEdit = (ListView) view.findViewById(R.id.relationListDetails);
        addContactDetails = (Button) view.findViewById(R.id.addEdit);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE &&  getActivity().getClass().getSimpleName().toString().equals("MainActivity")) {
            System.out.println("757575" + getActivity().getClass().getSimpleName().toString());

            //MainActivity mainActivity = (MainActivity) getActivity();
            //System.out.println(" 757575 " );
           // classRelList = mainActivity.currentCBList;
            displayRelationList();

        }

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

                addContactDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumString = phoneEdit.getText().toString();
                String name = nameEdit.getText().toString();

                if(phoneNumString.equals("") || name.equals("")){
                    Toast.makeText(getActivity(),"Name and Phone Number are required fields!",Toast.LENGTH_SHORT).show();
                                }
                else {
                    int phoneNum = Integer.parseInt(phoneNumString);

                    contactItemSend = new ContactBean(name, phoneNum);
                    addRelationItems = new ArrayList<>();
                    for (int i=0; i < classRelList.size(); i++) {
                        if (classRelList.get(i).isChecked()) {
                            addRelationItems.add(classRelList.get(i));
                            classRelList.get(i).getRelationList().add(contactItemSend);

                        }

                    }

                    contactItemSend.setRelationList(addRelationItems);

                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        detailsCommunicator.sendNewContact(contactItemSend);
                    } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        communicator.respondWithContactLand(contactItemSend);
                        nameEdit.setText("");
                        phoneEdit.setText("");
                        displayRelationList();

                    }
                }
            }
        });

    }

    public  void setTextView(String name, int phone){
        System.out.println("SUCCESS in cd " + name);
        nameReceived = name;
        phoneReceived = phone;
        System.out.println("SUCCESS in cd " + nameReceived);
    }

    public void setRelationList(ArrayList<ContactBean> cbList){

        ContactDetailsActivity cd = (ContactDetailsActivity) getActivity();
        cd.currRelation = cbList;

        classRelList = new ArrayList<>();
        for (int i = 0; i < cbList.size(); i++) {
            classRelList.add(cbList.get(i));

        }
    }
    public void displayRelationList(){

        nameEdit.setText(nameReceived);
        phoneEdit.setText(Integer.toString(phoneReceived));

        if(classRelList != null) {
            relAdapter = new RelationAdapter(getActivity(), R.layout.double_row, classRelList);
            relationEdit.setAdapter(relAdapter);
            setOnClickListenerMeth();
        }
        }



    public void setContactDetailsCommunicator(DetailsCommunicator c){
        this.detailsCommunicator = c;

    }

    public void setLandCommunicator(Communicator c){
        this.communicator = c;

    }


    public void setOnClickListenerMeth(){

        relationEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ) {
                    Intent intent = new Intent(getActivity(), ContactProfileActivity.class);
                    intent.putExtra("cbItem", (Serializable) classRelList.get(position));
                    intent.putExtra("uniqID", "fromCDActivity");
                    startActivity(intent);
                }
                else {
                    for(int i = 0; i < classRelList.size(); i++){
                        classRelList.get(i).setChecked(false);
                    }
                    communicator.respondForProfile(classRelList.get(position));
                }
            }
        });

    }

    @Override
    public void onDestroyView() {

        if (getActivity().getClass().getSimpleName().toString().equals("ContactDetailsActivity")) {
            ContactDetailsActivity cd = (ContactDetailsActivity) getActivity();
            cd.currName = nameEdit.getText().toString();
            cd.currNum = Integer.parseInt(phoneEdit.getText().toString());
            cd.currRelation = classRelList;
        }


        if (getActivity().getClass().getSimpleName().toString().equals("MainActivity")) {
            MainActivity cd = (MainActivity) getActivity();
            cd.currNameDetails = nameEdit.getText().toString();
            cd.currPhoneDetails = Integer.parseInt(phoneEdit.getText().toString());
            cd.currentCBList = classRelList;
        }
        if(classRelList != null) {
            for (int i = 0; i < classRelList.size(); i++) {
                classRelList.get(i).setChecked(false);
            }
        }
        super.onDestroyView();
    }


    public interface DetailsCommunicator{
        public void sendNewContact(ContactBean contactItem);

    }




}
