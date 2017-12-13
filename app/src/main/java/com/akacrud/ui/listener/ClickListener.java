package com.akacrud.ui.listener;

import android.view.View;

/**
 * Created by luisvespa on 12/13/17.
 */

public interface ClickListener {
    public void onClick(View view, int position);

    public void onLongClick(View view, int position);
}
