package com.w36495.basiclist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ItemTouchHelper itemTouchHelper;
    private OnItemClickListener listener;

    private EditText et_item_add;
    private Button btn_item_add;

    private ArrayList<Item> mListItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();
    }

    private void setInit() {

        et_item_add = findViewById(R.id.et_item_add);
        btn_item_add = findViewById(R.id.btn_item_add);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        itemAdapter = new ItemAdapter(getApplicationContext(), listener);


        recyclerView.setAdapter(itemAdapter);

        // '확인'버튼 클릭했을 때 => 투두리스트 추가
        btn_item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
                et_item_add.setText("");
            }
        });

        // 왼쪽으로 스와이프 -> 리스트 삭제
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem(viewHolder.getLayoutPosition());
            }
        };

        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        // 우선순위 변경
        itemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Item item = mListItems.get(position);
                if (item.getState() == ItemState.BASIC) {
                    item.setState(ItemState.GREEN);
                }
                else if (item.getState() == ItemState.GREEN) {
                    item.setState(ItemState.BLUE);
                }
                else if (item.getState() == ItemState.BLUE) {
                    item.setState(ItemState.RED);
                }
                else if (item.getState() == ItemState.RED) {
                    item.setState(ItemState.BASIC);
                }

                mListItems.set(position, item);
                itemAdapter.stateUpdateItem(position, item);
            }
        });

    }

    // 투두리스트 추가
    private void addItem() {
        String list = et_item_add.getText().toString();
        mListItems.add(new Item(list, false, ItemState.BASIC));
        itemAdapter.itemAdd(new Item(list, false, ItemState.BASIC));
    }

    // 투두리스트 삭제
    private void removeItem(int position) {
        mListItems.remove(position);
        itemAdapter.removeItem(position);
    }

}