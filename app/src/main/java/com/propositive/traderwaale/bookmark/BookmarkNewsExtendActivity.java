package com.propositive.traderwaale.bookmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.propositive.traderwaale.R;
import com.propositive.traderwaale.home.newsAdapter;
import com.propositive.traderwaale.home.newsModel;

import java.util.ArrayList;

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