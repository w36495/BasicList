package com.w36495.basiclist.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.w36495.basiclist.database.ItemState;
import com.w36495.basiclist.listener.OnItemClickListener;
import com.w36495.basiclist.R;
import com.w36495.basiclist.database.Item;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder>
implements OnItemClickListener {

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

    private ArrayList<Item> mListItems = null;
    private OnItemClickListener listener;
    private Context context;

    public ItemAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
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

            holder.cb_list_item.setOnCheckedChangeListener(null);

            holder.cb_list_item.setChecked(true);
            holder.cb_list_item.setOnCheckedChangeListener(mOnCheckedChangeListener);
            holder.tv_list_item.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else if (mListItems.get(position).getComplete() == false) {

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

    private void onBindChecked(@NonNull ItemHolder holder) {

    }

    @Override
    public int getItemCount() { return mListItems.size(); }

    public void removeItem(int position) {

        mListItems.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(View view, int position) {
        if (listener != null) {
            listener.onItemClick(view, position);
        }
    }

    @Override
    public void onItemCheckedClick(CompoundButton compoundButton, int position, boolean checked) {
        if (listener != null) {
            listener.onItemCheckedClick(compoundButton, position, checked);
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


            cb_list_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAbsoluteAdapterPosition();

                    if (listener != null) {
                        listener.onItemCheckedClick(buttonView, position, isChecked);
                    }

                }
            });

            tv_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(v, position);
                    }
                }
            });
        }
    }

    /**
     * 새로운 리스트 추가
     */
    public void itemAdd(Item item) {
        mListItems.add(item);
        notifyDataSetChanged();
    }

    /**
     * 우선순위 변경 반영
     */
    public void stateUpdateItem(int position) {
        Item item = mListItems.get(position);
        mListItems.set(position, item);
        notifyItemChanged(position, item);
        notifyDataSetChanged();
    }

    /**
     * 완료/미완료 상태 변경 반영
     */
    public void checkedUpdateItem(boolean isChecked, int position) {
        Item item = mListItems.get(position);
        // 완료된 리스트
        if (isChecked == true) {
            item.setComplete(true);
        }
        else item.setComplete(false);

        mListItems.set(position, item);
        notifyItemChanged(position, item);
        notifyDataSetChanged();
    }

    // db에서 가져온 list들 -> ArrayList
    public void setList(ArrayList<Item> mListItems) {
        this.mListItems = mListItems;
        notifyDataSetChanged();
    }

    public Item findOne(int position) {
        return mListItems.get(position);
    }

}
