package com.example.gruppe43.idretts_app.application.controll;

import android.support.annotation.NonNull;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;
import com.example.gruppe43.idretts_app.application.view.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by gebi9 on 26-Apr-17.
 */

public class DataBaseHelper {
    private DatabaseReference fbTrainerPostsDbRef;
    private DatabaseReference fbPlayerPostsDbRef;
    private DatabaseReference fbUsersDbRef;
    private DatabaseReference fbAbsenceDbRef;
    private DatabaseReference fbCapsDbRef;
    private FragmentActivityInterface mCallback;

    private Authentication authClass;

    private FirebaseUser fbUser;
    private FirebaseAuth fbAuth;

    public DataBaseHelper() {
        fbTrainerPostsDbRef = FirebaseDatabase.getInstance().getReference().child("TrainerPosts");
        fbPlayerPostsDbRef = FirebaseDatabase.getInstance().getReference().child("PlayerPosts");
        fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbAbsenceDbRef = FirebaseDatabase.getInstance().getReference().child("Abcences");
        fbCapsDbRef = FirebaseDatabase.getInstance().getReference().child("Camps");

        fbAuth = FirebaseAuth.getInstance();
        authClass = new Authentication();
    }



    public void setIsAdmin() {
        fbUsersDbRef.child(fbAuth.getCurrentUser().getUid()).child("isAdmin").setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    authClass.alert(authClass.getString(R.string.alert),authClass.getString(R.string.setAdminFailured));
                }
            }
        });
    }

    public void setSignedInUserType() {////////////////////////////////////////////takes time!
        fbUsersDbRef.child(fbAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("isAdmin").getValue()+"";
                if(value.equals("true")){

                }else{

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
