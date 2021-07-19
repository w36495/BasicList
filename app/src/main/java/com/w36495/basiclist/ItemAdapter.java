package com.w36495.basiclist;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    private ArrayList<Item> mListItems = new ArrayList<>();
    private OnItemClickListener onItemClickListener;


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todolist_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.tv_list_item.setText(mListItems.get(position).getListItem());
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        protected TextView tv_list_item;
        protected CheckBox cb_list_item;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tv_list_item = itemView.findViewById(R.id.tv_list_item);
            cb_list_item = itemView.findViewById(R.id.cb_list_item);

            cb_list_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    // 체크되었으면 완료 -> 취소선
                        Log.d("체크여부 : ", "check");
                        tv_list_item.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    else {
                        // 체크되어있지 않으면 -> 취소선 없애기
                        Log.d("체크여부 : ", "non-check");
                        tv_list_item.setPaintFlags(0);
                    }
                }
            });
        }
    }

    public void itemAdd(Item item) {
        mListItems.add(item);
        notifyDataSetChanged();
    }
}
