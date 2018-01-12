package com.a22g.argfilmquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class CreditsText extends AppCompatActivity {

    TextView credits_p1;
    TextView credits_p2;
    TextView credits_p3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits_text);
        setTitle(R.string.creditos);

        credits_p1 = findViewById(R.id.credits_p1);
        if (credits_p1 != null) {
            credits_p1.setMovementMethod(LinkMovementMethod.getInstance());
        }

        credits_p2 = findViewById(R.id.credits_p2);
        if (credits_p2 != null) {
            credits_p2.setMovementMethod(LinkMovementMethod.getInstance());
        }

        credits_p3 = findViewById(R.id.credits_p2);
        if (credits_p3 != null) {
            credits_p3.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
