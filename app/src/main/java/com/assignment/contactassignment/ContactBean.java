package com.assignment.contactassignment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Careena on 10/24/16.
 */
public class ContactBean implements Serializable {

    private String name;
    private int contact;
    private ArrayList<ContactBean> relationList;
    private boolean checked;
    private boolean deleteRequested;

    public ContactBean(String name, int contact, ArrayList<ContactBean> relationList) {
        this.name = name;
        this.contact = contact;
        this.relationList = relationList;
    }

    public ContactBean(String name, int contact) {
        this.name = name;
        this.contact = contact;

    }

    public ContactBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public ArrayList<ContactBean> getRelationList() {
        return relationList;
    }

    public void setRelationList(ArrayList<ContactBean> relationList) {
        this.relationList = relationList;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isDeleteRequested() {
        return deleteRequested;
    }

    public void setDeleteRequested(boolean deleteRequested) {
        this.deleteRequested = deleteRequested;
    }
}
