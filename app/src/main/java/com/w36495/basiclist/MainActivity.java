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
import android.widget.CompoundButton;
import android.widget.EditText;

import com.w36495.basiclist.adapter.ItemAdapter;
import com.w36495.basiclist.database.Item;
import com.w36495.basiclist.database.ItemDatabase;
import com.w36495.basiclist.database.ItemState;
import com.w36495.basiclist.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ItemTouchHelper itemTouchHelper;
    private OnItemClickListener listener;

    private ItemDatabase itemDB = null;

    private EditText et_item_add;
    private Button btn_item_add;

    private int listCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();
    }

    private void setInit() {

        // DB 생성
        itemDB = ItemDatabase.getInstance(this);

        et_item_add = findViewById(R.id.et_item_add);
        btn_item_add = findViewById(R.id.btn_item_add);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        itemAdapter = new ItemAdapter(this, listener);
        recyclerView.setAdapter(itemAdapter);

        // DB에서 불러오기
        itemAdapter.setList((ArrayList)itemDB.itemDAO().getAll());

        listCount = itemDB.itemDAO().lastGetId()+1;


        // '확인'버튼 클릭했을 때 => 투두리스트 추가
        btn_item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
                et_item_add.setText("");    // 입력창 비우기
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
                int position = viewHolder.getAbsoluteAdapterPosition();
                removeItem(position);
            }
        };

        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        itemAdapter.setOnItemClickListener(new OnItemClickListener() {
            // textView 클릭 -> 우선순위 변경
            @Override
            public void onItemClick(View view, int position) {
                stateUpdateItem(position);
            }

            // checkBox 클릭 -> 체크 상태 변경
            @Override
            public void onItemCheckedClick(CompoundButton compoundButton, int position, boolean isChecked) {
                // 체크되어 있으면 => complete = 1로 변경
                if (isChecked) {
                    checkedUpdateItem(true, position);
                }
                else if (!isChecked) {
                    checkedUpdateItem(false, position);
                }
            }
        });

    }

    // 투두리스트 추가
    private void addItem() {
        String list = et_item_add.getText().toString();

        Item item = new Item(listCount++, list, false, ItemState.BASIC);

        // db에 insert
        itemDB.itemDAO().insertItem(item);
        itemAdapter.itemAdd(item);

        Log.d(TAG, "item ID : " + item.getId());
    }

    // 투두리스트 삭제
    private void removeItem(int position) {
        Item item = itemAdapter.findOne(position);
        Log.d(TAG, "removeItemId : " + item.getId());

        // db에서 해당 list delete 실행
        itemDB.itemDAO().deleteItem(item.getId());

        itemAdapter.removeItem(position);
    }

    // 체크 상태 변경
    private void checkedUpdateItem(boolean isChecked, int position) {
        Item item = itemAdapter.findOne(position);

        // db update 반영
        itemDB.itemDAO().checkedUpdateItem(item.getId(), isChecked);

        itemAdapter.checkedUpdateItem(isChecked, position);
    }

    // 우선순위 변경
    private void stateUpdateItem(int position) {
        Item item = itemAdapter.findOne(position);

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

        // db update 반영
        itemDB.itemDAO().stateUpdateItem(item.getId(), item.getState());

        itemAdapter.stateUpdateItem(position);

        Log.d("우선순위 변경", "itemID : " + item.getId());
    }


}