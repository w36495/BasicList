package com.w36495.basiclist;

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

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder>
implements OnItemClickListener {

    private ArrayList<Item> mListItems = new ArrayList<>();
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
                    int position = getAdapterPosition();
                    if (isChecked) {    // 체크되었으면 완료 -> 취소선
                        tv_list_item.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        checkedUpdateItem(true, position);
                    }
                    else {
                        // 체크되어있지 않으면 -> 취소선 없애기
                        tv_list_item.setPaintFlags(0);
                        checkedUpdateItem(false, position);
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
    public void stateUpdateItem(int position, Item item) {
        mListItems.set(position, item);
        notifyItemChanged(position);
        notifyDataSetChanged();
    }

    /**
     * 완료/미완료 상태 변경 반영
     */
    public void checkedUpdateItem(boolean checked, int position) {
        Item item = mListItems.get(position);
        // 완료된 리스트
        if (checked == true) {
            item.setComplete(true);
        }
        else item.setComplete(false);
        mListItems.set(position, item);
        notifyItemChanged(position);
        notifyDataSetChanged();
    }



}
