package com.propositive.traderwaale.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.propositive.traderwaale.MainActivity;
import com.propositive.traderwaale.R;
import com.propositive.traderwaale.bookmark.BookmarkActivity;

public class NewsExtendActivity extends AppCompatActivity {

    LinearLayout comment, bookmark;

    BottomSheetDialog comSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_extend);

        comment = findViewById(R.id.comment_layout);
        bookmark = findViewById(R.id.bookmark);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCommentBox();
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewsExtendActivity.this, "bookmark under construction!...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openCommentBox() {
        comSheet = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View commentsheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.comment_sheet, findViewById(R.id.comment_sheet));

        comSheet.setContentView(commentsheet);
        comSheet.show();
    }
}