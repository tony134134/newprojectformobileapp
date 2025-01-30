package com.example.mobileapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FloatingActionButton floating;
    RecyclerView recyclerView;
    ArrayList<Model> arrayList = new ArrayList<>();
    Helper helper;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    loadData();
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        floating = view.findViewById(R.id.floating);
        recyclerView = view.findViewById(R.id.recyclerView);
        helper = new Helper(getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadData();

        floating.setOnClickListener(view1 ->
                activityResultLauncher.launch(new Intent(getActivity(), MainActivity2.class))
        );

        return view;
    }

    private void loadData() {
        arrayList.clear();
        Cursor cursor = helper.showData();
        while (cursor.moveToNext()) {
            arrayList.add(new Model(cursor.getString(1), cursor.getString(2), cursor.getInt(0)));
        }
        Adepter adepter = new Adepter((FragmentActivity) getActivity(), arrayList, activityResultLauncher);
        recyclerView.setAdapter(adepter);
    }
}
