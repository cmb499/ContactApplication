package com.assignment.contactassignment;

import java.util.ArrayList;

/**
 * Created by Careena on 10/25/16.
 */
public interface Communicator {

   // public void respond(String name, int phone, int[] relList);
    public  void respondForProfile(ContactBean contactItem);
    public void add(ArrayList<ContactBean> cbList);
    public void addWithText(ArrayList<ContactBean> cbList, String name, int phone);
    public void respondWithContactLand(ContactBean contactItem);


}
