package com.w36495.basiclist.listener;

import android.view.View;
import android.widget.CompoundButton;

import com.w36495.basiclist.database.Item;

public interface OnItemClickListener {

    void onItemClick(Item item);

    void onItemCheckedClick(Item item, boolean isChecked);

}
