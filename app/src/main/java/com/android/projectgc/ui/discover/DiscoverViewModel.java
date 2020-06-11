package com.android.projectgc.ui.discover;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.projectgc.discover;
import com.android.projectgc.profile;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class DiscoverViewModel extends ViewModel {

    private discover disco;
    private MutableLiveData<discover> arrdisco;

    public DiscoverViewModel() {
        disco=new discover();
        arrdisco=new MutableLiveData<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("dustbin").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if(queryDocumentSnapshots!=null){
                        for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                            DocumentSnapshot snapshot=queryDocumentSnapshots.getDocuments().get(i);
                            discover disco=snapshot.toObject(discover.class);
                            arrdisco.setValue(disco);
                            //System.out.println(queryDocumentSnapshots.getDocuments().get(i).getData());
                        }

                    }
            }
        });

    }

    public LiveData<discover> getDiscoverData(){ return arrdisco;}


}
