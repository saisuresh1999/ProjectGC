package com.android.projectgc.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.projectgc.R;
import com.android.projectgc.profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class ProfileFragment extends Fragment {


    private TextView mobileNumber;
    private TextView userName;
    private TextView emailID;
    private ProfileViewModel profileViewModel;
    private  profile prfiles;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root =inflater.inflate(R.layout.profile_fragment, container, false);
        mobileNumber=root.findViewById(R.id.mobileNumber);
        userName=root.findViewById(R.id.userName);
        emailID=root.findViewById(R.id.emailID);
        profileViewModel.getProfData().observe(this, new Observer<profile>() {
            @Override
            public void onChanged(profile profile) {
                prfiles = profile;
                mobileNumber.setText(profile.getNumber());
                userName.setText(profile.getUname());
                emailID.setText(profile.getEmail());


            }
        });





        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}
