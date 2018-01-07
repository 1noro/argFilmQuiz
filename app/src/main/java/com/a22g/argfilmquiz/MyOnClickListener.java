package com.a22g.argfilmquiz;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by cosmo on 30/12/17.
 */

public class MyOnClickListener<E> implements View.OnClickListener {
    ArrayList<E> params = new ArrayList<>();

    public MyOnClickListener(ArrayList<E> _params) {
        this.params = _params;
    }

    @Override
    public void onClick(View v){};
}
