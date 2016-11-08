package com.assignment.contactassignment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends Activity implements Communicator{

    ContactList contactListFragment;
    ContactProfile contactProfileFragment;
    ContactDetails contactDetailsFragment;
    FragmentManager manager;
    static ContactBean currentProfile;
    static ArrayList<ContactBean> currentCBList;
    static String currNameDetails;
    static int currPhoneDetails;


    private static final int REQ_CODE = 123;
    private static final int RES_CODE = 321;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                       if(savedInstanceState != null){
                    String result = savedInstanceState.getString("savedInstance");
                    if(result != null && result.equals("saveProfile")) {
                        System.out.println("9999 restored instance state profile");
                        ContactBean cbLocal = (ContactBean) savedInstanceState.getSerializable("showProfile");
                        respondForProfile(cbLocal);

                    }
//                    if(result != null && result.equals("saveDetails")) {
//                        System.out.println("9999 restored instance state details");
//                        ArrayList<ContactBean>  cbListLocal = (ArrayList<ContactBean>) savedInstanceState.getSerializable("showDetails");
//
//                        if(currentCBList != null) {
//                            System.out.println("9999 cbLocal is not null" + currNameDetails);
//                            addWithText(cbListLocal, currNameDetails, currPhoneDetails);
//                        }
//                        else {
//                            System.out.println("9999 cbLocal is null");
//                        }

            }

//        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
//        Gson gson = new Gson();
//        String json = appSharedPrefs.getString("contactList", "");
//        Type type = new TypeToken<ArrayList<ContactBean>>(){}.getType();
//        ArrayList<ContactBean> students= gson.fromJson(json, type);
//
//        if(students != null) {
//            System.out.println("yeay!!" + students.get(students.size() - 1).getName());
//        }
//        System.out.println("899 - Landscape SAFE");
        manager = getFragmentManager();
        contactListFragment = (ContactList) manager.findFragmentById(R.id.fragment);
        contactListFragment.setCommunicator(this);

    }



    @Override
    public void respondForProfile(ContactBean cbItem) {

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            currentProfile = cbItem;
            FragmentTransaction transaction = manager.beginTransaction();
            contactProfileFragment = new ContactProfile();
            contactProfileFragment.setLocalData(cbItem);
            transaction.replace(R.id.group, contactProfileFragment, "fragment_profile");
            transaction.commit();
        }
        else{
            //portrait
            Intent intent = new Intent(this, ContactProfileActivity.class);
            intent.putExtra("cbItem", (Serializable) cbItem);
            intent.putExtra("uniqID", "fromMain");
            startActivityForResult(intent, RES_CODE);
        }
    }




    @Override
    public void add(ArrayList<ContactBean> cbList) {
       // sharedPreferences(cbList);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            FragmentTransaction transaction = manager.beginTransaction();
            contactDetailsFragment = new ContactDetails();
            contactDetailsFragment.setRelationList(cbList);
            contactDetailsFragment.setLandCommunicator(this);
            transaction.replace(R.id.group, contactDetailsFragment, "fragment_details");
            transaction.commit();
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Intent intent = new Intent(this, ContactDetailsActivity.class);
            intent.putExtra("cbList", (Serializable) cbList);
            startActivityForResult(intent, REQ_CODE);
        }

  }



    @Override
    public void addWithText(ArrayList<ContactBean> cbList, String name, int phone) {
        // sharedPreferences(cbList);

       // currentCBList = cbList;

        System.out.println("SUCCESS in main " + name);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            FragmentTransaction transaction = manager.beginTransaction();
            contactDetailsFragment = new ContactDetails();
            contactDetailsFragment.setRelationList(cbList);
            contactDetailsFragment.setTextView(name, phone);
            contactDetailsFragment.setLandCommunicator(this);
            transaction.replace(R.id.group, contactDetailsFragment, "fragment_details");
            transaction.commit();
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Intent intent = new Intent(this, ContactDetailsActivity.class);
            System.out.println("9999 cbLocal is not null -----2" + currNameDetails);
            intent.putExtra("cbList", (Serializable) cbList);
            intent.putExtra("receivedName", currNameDetails);
            intent.putExtra("receivedNumber", currPhoneDetails);
            intent.putExtra("returningView", true);
            startActivityForResult(intent, REQ_CODE);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if( requestCode == RES_CODE){
            if(intent != null){
                boolean success = intent.getBooleanExtra("fromProfileActivity", false);
                if(success){
                    ContactBean cbProfile = (ContactBean) intent.getSerializableExtra("cbItem");
                    if(cbProfile != null){
                        respondForProfile(cbProfile);
                    }
                }
            }
        }

        if (requestCode == REQ_CODE) {
            if (intent != null) {
                if(intent.getBooleanExtra("fromDetailsActivity", false)){

                    ArrayList<ContactBean> cbListLocal = (ArrayList<ContactBean>) intent.getSerializableExtra("relationFromDetails");

                    String nameLocal = intent.getStringExtra("nameFromDetails");
                    int phoneLocal = intent.getIntExtra("phoneFromDetails", 0);

                    currentCBList = cbListLocal;
                    if(cbListLocal!= null){
                        System.out.println("SUCCESS in Main" + nameLocal );
                        addWithText(cbListLocal, nameLocal, phoneLocal);
                    }
                    else {
                        System.out.println("989 SUCCESS in Main with null object");
                    }
                }else{
                // came back from SecondActivity
                ContactBean cbItem = (ContactBean) intent.getSerializableExtra("cbIntentItem");
                if(cbItem != null){
                contactListFragment.addNewItem(cbItem);
            }
        }
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ContactProfile myFragment = (ContactProfile) getFragmentManager().findFragmentByTag("fragment_profile");
            if (myFragment != null && myFragment.isVisible()) {
                String saved = "saveProfile";
                outState.putString("savedInstance", saved);
                outState.putSerializable("showProfile", (Serializable) currentProfile);
                System.out.println("1111 onSaveInstanceState profile");
            }
            ContactDetails cdFragment = (ContactDetails) getFragmentManager().findFragmentByTag("fragment_details");
            if (cdFragment != null && cdFragment.isVisible()) {
                String saved = "saveDetails";
                outState.putString("savedInstance", saved);
                outState.putSerializable("showDetails", (Serializable) currentCBList);
//            outState.putString("receivedName", currNameDetails);
//            outState.putInt("receivedNumber", currPhoneDetails);
                // outState.getSerializable("showName",)
                System.out.println("1111 onSaveInstanceState details");
            }
        }

    }
    @Override
    public void respondWithContactLand(ContactBean contactItem) {
        System.out.println("899 - respondWithContactLand " + contactItem.getRelationList().size());
        contactListFragment.addNewItem(contactItem);

    }

    public void sharedPreferences(ArrayList<ContactBean> cbList) {


        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = appSharedPrefs.edit();

        editor.putString("contactList", new Gson().toJson(cbList));
        editor.commit();
    }
}
