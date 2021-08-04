package com.w36495.basiclist.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.w36495.basiclist.MainActivity;
import com.w36495.basiclist.database.ItemState;
import com.w36495.basiclist.listener.OnItemClickListener;
import com.w36495.basiclist.R;
import com.w36495.basiclist.database.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    private static String TAG = "로그";

    private OnItemClickListener onItemClickListener = null;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

    private List<Item> mListItems = null;
    private Context context;

    public ItemAdapter(Context context, OnItemClickListener onItemClickListener) {
        Log.d(TAG, "ItemAdapter - ItemAdapter() 생성자 호출");
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        Log.d(TAG, "ItemAdapter - onItemClickListener() 제대로 연결");
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todolist_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.tv_list_item.setText(mListItems.get(position).getContents());

        // 체크 표시
        if (mListItems.get(position).getComplete() == true) {
            holder.tv_list_item.setTextColor(Color.GRAY);
            holder.cb_list_item.setOnCheckedChangeListener(null);
            holder.cb_list_item.setChecked(true);
            holder.cb_list_item.setOnCheckedChangeListener(mOnCheckedChangeListener);
            holder.tv_list_item.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        }
        // 체크가 되어 있지 않다면
        else if (mListItems.get(position).getComplete() == false) {
            holder.tv_list_item.setTextColor(Color.BLACK);
            holder.cb_list_item.setOnCheckedChangeListener(null);
            holder.cb_list_item.setChecked(false);
            holder.cb_list_item.setOnCheckedChangeListener(mOnCheckedChangeListener);
            holder.tv_list_item.setPaintFlags(0);
        }

        // 우선순위 색 지정
        if (mListItems.get(position).getState() == ItemState.BASIC) {
            holder.tv_list_state.setBackgroundColor(ContextCompat.getColor(context, R.color.grey));
        }
        else if (mListItems.get(position).getState() == ItemState.GREEN) {
            holder.tv_list_state.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }
        else if (mListItems.get(position).getState() == ItemState.BLUE) {
            holder.tv_list_state.setBackgroundColor(ContextCompat.getColor(context, R.color.blue));
        }
        else if (mListItems.get(position).getState() == ItemState.RED) {
            holder.tv_list_state.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        protected TextView tv_list_item;
        protected CheckBox cb_list_item;
        protected TextView tv_list_state;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tv_list_item = itemView.findViewById(R.id.tv_list_item);
            cb_list_item = itemView.findViewById(R.id.cb_list_item);
            tv_list_state = itemView.findViewById(R.id.tv_list_state);

            // 체크박스를 클릭했을 때(완료/미완료)
            cb_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    Item item = mListItems.get(position);

                    if (onItemClickListener != null) {
                        onItemClickListener.onItemCheckedClick(item);
                    }
                }
            });

            // 텍스트를 클릭했을 때(우선순위 변경) -> OnItemClickListener의 onItemClick 호출
            tv_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    Item item = mListItems.get(position);
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(item);
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() { return mListItems.size(); }

    /**
     * DB에서 가져온 리스트 -> ArrayList
     */
    public void setList(ArrayList<Item> mListItems) {
        this.mListItems = mListItems;
        notifyDataSetChanged();
    }

    public Item getList(int position) { return mListItems.get(position); }

}
