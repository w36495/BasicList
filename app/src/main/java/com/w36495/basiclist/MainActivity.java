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
import com.w36495.basiclist.database.DeleteRunnable;
import com.w36495.basiclist.database.InsertRunnable;
import com.w36495.basiclist.database.Item;
import com.w36495.basiclist.database.ItemDatabase;
import com.w36495.basiclist.database.StateUpdateRunnable;
import com.w36495.basiclist.database.CompleteUpdateRunnable;

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

    private ArrayList<Item> mListItems = new ArrayList<>();
    private int listCount = 0;

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

        // DB 생성
        itemDB = ItemDatabase.getInstance(this);

        // DB에서 불러오기
        List<Item> list = new ArrayList<>();
        selectItem(list);

        itemAdapter = new ItemAdapter(this, mListItems, listener);
        recyclerView.setAdapter(itemAdapter);

        listCount = itemAdapter.getItemCount();


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



        itemAdapter.setOnItemClickListener(new OnItemClickListener() {
            // 우선순위 변경
            @Override
            public void onItemClick(View view, int position) {
                stateUpdateItem(position);
            }

            // 체크 상태 변경
            @Override
            public void onItemCheckedClick(CompoundButton compoundButton, int position, boolean isChecked) {
                Item item = mListItems.get(position);
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

    // db 불러오기
    private void selectItem(List<Item> list) {
        SelectRunnable selectRunnable = new SelectRunnable(this, list);
        Thread thread = new Thread(selectRunnable);
        thread.start();

        for (Item item : list) {
            Log.d("데이터베이스에서 불러옴", item.getId() + " : " + item.getContents());
            mListItems.add(item);
        }

    }

    // 투두리스트 추가
    private void addItem() {
        String list = et_item_add.getText().toString();

        Item item = new Item(listCount++, list, false, ItemState.BASIC);

        // db에 insert하기
        InsertRunnable insertRunnable = new InsertRunnable(this, item);
        Thread thread = new Thread(insertRunnable);
        thread.start();

        itemAdapter.itemAdd(item);
        Log.d(TAG, "item ID : " + item.getId());
    }

    // 투두리스트 삭제
    private void removeItem(int position) {
        Item item = mListItems.get(position);
        Log.d(TAG, "getId : " + item.getId());
        DeleteRunnable deleteRunnable = new DeleteRunnable(this, item.getId());
        Thread thread = new Thread(deleteRunnable);
        thread.start();

        itemAdapter.removeItem(position);
    }

    // 체크 상태 변경
    private void checkedUpdateItem(boolean isChecked, int position) {
        Item item = mListItems.get(position);

        CompleteUpdateRunnable completeUpdateRunnable = new CompleteUpdateRunnable(this, item.getId(), isChecked);
        Thread thread = new Thread(completeUpdateRunnable);
        thread.start();

        itemAdapter.checkedUpdateItem(isChecked, position);
    }

    // 우선순위 변경
    private void stateUpdateItem(int position) {
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

        StateUpdateRunnable stateUpdateRunnable = new StateUpdateRunnable(this, item.getId(), item.getState());
        Thread thread = new Thread(stateUpdateRunnable);
        thread.start();

        itemAdapter.stateUpdateItem(position, item);

    }


}