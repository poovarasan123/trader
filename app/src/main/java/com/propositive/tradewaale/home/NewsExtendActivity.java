package com.propositive.tradewaale.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.propositive.tradewaale.R;

public class NewsExtendActivity extends AppCompatActivity {

    LinearLayout comment, bookmark;

    BottomSheetDialog comSheet;

    TextView title, full_discription, date;
    ImageView news_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_extend);

//        comment = findViewById(R.id.comment_layout);
        //bookmark = findViewById(R.id.bookmark);

        news_image = findViewById(R.id.imageView);
        title = findViewById(R.id.titletxt);
        full_discription = findViewById(R.id.desctxt);
        date = findViewById(R.id.datetxt);

        Intent i = getIntent();

        String image = i.getStringExtra("image");
        String Title = i.getStringExtra("title");
        String message = i.getStringExtra("message");
        String Date = i.getStringExtra("date");

        Glide.with(news_image.getContext()).load("http://1192.168.90.211/trader/images/" + image).into(news_image);
        title.setText(Title);
        full_discription.setText(message);
        date.setText(Date);


//        comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //openCommentBox();
//            }
//        });

//        bookmark.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(NewsExtendActivity.this, "bookmark under construction!...", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void openCommentBox() {
        comSheet = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View commentsheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.comment_sheet, findViewById(R.id.comment_sheet));

        comSheet.setContentView(commentsheet);
        comSheet.show();
    }
}