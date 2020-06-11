package com.android.projectgc.ui.profile;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.projectgc.profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class ProfileViewModel extends ViewModel {

    private profile prof;
    private MutableLiveData<profile> arrprof;

    public ProfileViewModel(){
        prof=new profile();
        arrprof=new MutableLiveData<>();
        String u= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().substring(3);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        System.out.println(u);

        final DocumentReference docRef = db.collection("users").document(u);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("TAG", "Current data: " + snapshot.getData());
                    profile prof=snapshot.toObject(profile.class);
                    arrprof.setValue(prof);


                } else {
                    Log.d("TAG", "Current data: null");
                }
            }
        });


    }
    public LiveData<profile> getProfData(){ return arrprof;}
}
