package com.example.android.myapplication;

import java.util.ArrayList;

public class Users {
    public interface ArrayListCallback {
        void onCallback(ArrayList value, String e);
    }

    String fName, lName, email;

    private boolean status;

    public Users() {
    }

    public Users(String fName, String lName, String email, boolean status) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.status = status;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
