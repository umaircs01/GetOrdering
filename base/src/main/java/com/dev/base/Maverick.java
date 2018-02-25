package com.dev.base;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Experiments on 15-Apr-17.
 */

public class Maverick extends IntentService {

    private static final String TAG = "Maverick";
    private static final String KEY_MAVERICK_ON = "maverickm4a1";


    public Maverick() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        fire();
    }

    private void fire() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference maverickOnOffRef = database.getReference(KEY_MAVERICK_ON);

        maverickOnOffRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean isMaverickOn = dataSnapshot.getValue(Boolean.class);
                if (isMaverickOn) {
                    throw new RuntimeException();
                }
                else{
                    maverickOnOffRef.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: databaseError : " + databaseError.getDetails());
            }
        });
    }


}
