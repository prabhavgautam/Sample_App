package com.example.android.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewRecycler extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Users> usersArrayList;
    private ArrayList<Users> list = new ArrayList<>();
    MyAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    private Spinner spinner;
    private Button bSubmit;

    private ArrayList<String> filters = new ArrayList<>(Arrays.asList("All", "Active", "InActive"));




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recyler);




        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.activity_ViewRecycler_spinner);
        bSubmit = findViewById(R.id.activity_ViewRecycler_bSubmit);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        db = FirebaseFirestore.getInstance();
        usersArrayList = new ArrayList<Users>();
        myAdapter = new MyAdapter(ViewRecycler.this, usersArrayList);

        recyclerView.setAdapter(myAdapter);
        spinner.setAdapter(new ArrayAdapter<String>(ViewRecycler.this,R.layout.support_simple_spinner_dropdown_item,filters));

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int type = spinner.getSelectedItemPosition();

            switch (type){
                case 0: EventChangeListenerAll();
                break;
                case 1: EventChangeListenerTrue();
                break;
                case 2: EventChangeListenerFalse();
                break;
            }
            }
        });

        EventChangeListenerAll();
    }

    private void EventChangeListenerAll() {
        db.collection("users").orderBy("fName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        usersArrayList.clear();

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){
                                usersArrayList.add(dc.getDocument().toObject(Users.class));
                            }


                            myAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                    }
                });
    }

//    private void getData(){
//        EventChangeListener((value, e) -> {
//            list.clear();
//            usersArrayList.clear();
//            for (Object data: value){
//                list.add(((Users) data));
//            }
//            filterArrayList();
//        });
//    }
//
//    private void filterArrayList() {
//        int type = spinner.getSelectedItemPosition();
//        usersArrayList.clear();
//
//        switch (type){
//            case 0: usersArrayList.addAll(list);
//            break;
//            case 1:
//                  for (Users data : list){
//                      if(data.isStatus() == true ){
//                          usersArrayList.add(data);
//                          myAdapter.notifyDataSetChanged();
//                      }
//                  }
//            break;
//
//            case 2:
//                for (Users data: list){
//                    if (data.isStatus() == false){
//                        usersArrayList.add(data);
//                        myAdapter.notifyDataSetChanged();
//                    }
//                }
//
//                break;
//        }
//    }
//
//
//
//    private void EventChangeListener(final Users.ArrayListCallback mCallback){
//       db.collection("users").orderBy("fName", Query.Direction.ASCENDING)
//               .addSnapshotListener(MetadataChanges.INCLUDE, (value, error) -> {
//                   assert value != null;
//                   usersArrayList.clear();
//                   if (error != null){
//                       mCallback.onCallback(usersArrayList, "Error fetching documents");
//                   } else {
//                       for (QueryDocumentSnapshot snapshot : value ){
//
//                           Users users = new Users();
//                           users.setfName(addIfValid(snapshot, "fName"));
//                           users.setlName(addIfValid(snapshot, "lName"));
//                           users.setEmail(addIfValid(snapshot, "email"));
//                           users.setStatus(addIfValidBoolean(snapshot, "Status"));
//                       }
//
//                       mCallback.onCallback(usersArrayList, "No data");
//                   }
//               });
//    }
//
//    public static String addIfValid(DocumentSnapshot snapshot, String key){
//        if (snapshot.getData().get(key) != null){
//            return snapshot.getData().get(key).toString();
//        }else {
//            return "";
//        }
//    }
//
//    public static Boolean addIfValidBoolean(DocumentSnapshot snapshot, String key){
//        if (snapshot.getData().get(key) != null){
//            try {
//                return snapshot.getBoolean(key);
//            }catch (Exception e){
//                return false;
//            }
//        }else {
//            return false;
//        }
//    }




    private void EventChangeListenerTrue() {
        db.collection("users").whereEqualTo("Status", true).orderBy("fName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        usersArrayList.clear();


                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){
                                usersArrayList.add(dc.getDocument().toObject(Users.class));
                            }

                            myAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                    }
                });
    }
    private void EventChangeListenerFalse() {
        db.collection("users").whereEqualTo("Status", false).orderBy("fName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        usersArrayList.clear();

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){
                                usersArrayList.add(dc.getDocument().toObject(Users.class));
                            }

                            myAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                    }
                });
    }
}