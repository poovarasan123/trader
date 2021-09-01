package com.propositive.tradewaale.bookmark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.propositive.tradewaale.R;

public class BookmarkNewsExtendActivity extends AppCompatActivity {

    LinearLayout bookmark, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_news_extend);

        bookmark = findViewById(R.id.remove_bookmark);

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"remove under construction!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}