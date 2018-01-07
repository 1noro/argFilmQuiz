package com.a22g.argfilmquiz;

import android.view.View;

import java.util.ArrayList;

public class MyOnClickListener<E> implements View.OnClickListener {
    private ArrayList<E> params = new ArrayList<>();

    public MyOnClickListener(ArrayList<E> _params) {
        this.params = _params;
    }

    @Override
    public void onClick(View v){}
}
