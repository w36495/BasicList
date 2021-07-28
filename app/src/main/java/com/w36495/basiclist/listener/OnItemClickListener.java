package com.w36495.basiclist.listener;

import android.view.View;
import android.widget.CompoundButton;

public interface OnItemClickListener {

    void onItemClick(View view, int position);

    void onItemCheckedClick(CompoundButton compoundButton, int position, boolean isChecked);

}
