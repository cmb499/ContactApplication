package com.assignment.contactassignment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactDetailsActivity extends Activity implements ContactDetails.DetailsCommunicator {

    ContactDetails contactDetailsFragmentNew;
    ArrayList<ContactBean> cbList;
    FragmentManager manager;

    public static String currName;
    public  static int currNum;
    public static ArrayList<ContactBean> currRelation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)) {
            Intent intent = new Intent();
            intent.putExtra("fromDetailsActivity", true);
            intent.putExtra("relationFromDetails", (Serializable)currRelation);
            intent.putExtra("nameFromDetails", currName);
            intent.putExtra("phoneFromDetails",currNum);
            setResult(RESULT_OK, intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_contact_details);

        Intent intent = getIntent();

        cbList = (ArrayList<ContactBean>) intent.getSerializableExtra("cbList");
        manager = getFragmentManager();

        contactDetailsFragmentNew = (ContactDetails) manager.findFragmentById(R.id.fragment3);

        boolean returningView = intent.getBooleanExtra("returningView", false);
        if(returningView){
            String currNameLocal = intent.getStringExtra("receivedName");
            int currPhoneLocal = intent.getIntExtra("receivedNumber", 0);
            contactDetailsFragmentNew.setTextView(currNameLocal, currPhoneLocal);
        }
        contactDetailsFragmentNew.setRelationList(cbList);
        contactDetailsFragmentNew.setContactDetailsCommunicator(this);
        contactDetailsFragmentNew.displayRelationList();

        }


    @Override
    public void sendNewContact(ContactBean contactItem) {
        Intent sendIntent = new Intent();
        sendIntent.putExtra("cbIntentItem",contactItem);
        setResult(RESULT_OK, sendIntent);
        finish();
    }



}
