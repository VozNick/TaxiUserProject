package com.example.vmm408.taxiuserproject;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public abstract class CustomValueEventListener implements ValueEventListener {
    public abstract void onDataChange(DataSnapshot dataSnapshot);

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
