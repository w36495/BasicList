package com.w36495.basiclist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;

    private ArrayList<Item> mListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();
    }

    private void setInit() {

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter();


        mListItems = new ArrayList<>();
        mListItems.add(new Item("테스트1", false));
        mListItems.add(new Item("테스트2", false));
        mListItems.add(new Item("테스트3", false));

        for (Item i : mListItems) {
            itemAdapter.itemAdd(i);
        }

        recyclerView.setAdapter(itemAdapter);



    }


}