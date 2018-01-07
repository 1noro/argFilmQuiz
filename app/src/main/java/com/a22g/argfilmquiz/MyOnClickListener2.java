package com.a22g.argfilmquiz;

import android.view.View;
import org.json.JSONObject;

public class MyOnClickListener2 implements View.OnClickListener {
    final JSONObject params;

    public MyOnClickListener2(JSONObject _params) {
        this.params = _params;
    }

    @Override
    public void onClick(View v){}
}
