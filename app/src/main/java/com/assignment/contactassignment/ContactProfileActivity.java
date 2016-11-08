package com.assignment.contactassignment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactProfileActivity extends Activity {

    ContactBean cbItem;
    static String testString;
    static ContactBean sendContactToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)) {
            // System.out.println("899 - Landscape in contact profile activity");
            Intent intent = new Intent();

            if(sendContactToMain !=null) {
               intent.putExtra("fromProfileActivity", true);
                intent.putExtra("cbItem",(Serializable)sendContactToMain);
            }

            setResult(RESULT_OK, intent);
            finish();
            return;
            //getActivity().finish();
        }

        setContentView(R.layout.activity_profile_details);

        Intent intent = getIntent();
       String uniqID = intent.getStringExtra("uniqID");
        if (uniqID.equals("fromMain")){
             cbItem = (ContactBean) intent.getSerializableExtra("cbItem");
        }
        else if (uniqID.equals("fromCDActivity")){
            cbItem = (ContactBean) intent.getSerializableExtra("cbItem");
        }
        else if (uniqID.equals("fromCPActivity")){
            cbItem = (ContactBean) intent.getSerializableExtra("cbItem");
        }


       // ArrayList<ContactBean> cbList = (ArrayList<ContactBean>) intent.getSerializableExtra("cbList");

        ContactProfile cd = (ContactProfile) getFragmentManager().findFragmentById(R.id.fragment2);

        if (cd != null) {
            cd.changeData(cbItem);
        }
    }
}
