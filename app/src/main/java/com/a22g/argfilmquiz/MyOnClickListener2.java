package com.a22g.argfilmquiz;

import android.view.View;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by cosmo on 30/12/17.
 */

public class MyOnClickListener2 implements View.OnClickListener {
    JSONObject params;

    public MyOnClickListener2(JSONObject _params) {
        this.params = _params;
    }

    @Override
    public void onClick(View v){};
}
