package com.bobur.quizforphysics.other;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bobur.quizforphysics.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VideoListActivity extends AppCompatActivity {

    static class VideoItem {
        String title;
        String youtubeUrl;
        String thumbnailUrl;

        VideoItem(String title, String youtubeUrl, String thumbnailUrl) {
            this.title        = title;
            this.youtubeUrl   = youtubeUrl;
            this.thumbnailUrl = thumbnailUrl;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        LinearLayout container = findViewById(R.id.videoContainer);

        ArrayList<VideoItem> videos = new ArrayList<>();

        videos.add(new VideoItem(
                "Orbital magnetic moment of an electron revolving in an orbit of an atom",
                "https://www.youtube.com/watch?v=Dpo4Ev8LAkI",
                "https://img.youtube.com/vi/Dpo4Ev8LAkI/0.jpg"
        ));
        videos.add(new VideoItem(
                "Diamagnetic, Paramagnetic and Ferromagnetic Materials",
                "https://youtu.be/AwCTDtknYWU",
                "https://img.youtube.com/vi/AwCTDtknYWU/0.jpg"
        ));
        videos.add(new VideoItem(
                "Ferromagnetism & Curie temperature | Khan Academy",
                "https://www.youtube.com/watch?v=8a2IZZdIeXw",
                "https://img.youtube.com/vi/8a2IZZdIeXw/0.jpg"
        ));
        videos.add(new VideoItem(
                "Solid State Magnetism (Lecture 24): Stoner Model",
                "https://www.youtube.com/watch?v=kRy2_e4nlQU",
                "https://img.youtube.com/vi/kRy2_e4nlQU/0.jpg"
        ));
        videos.add(new VideoItem(
                "How Do Diluted Magnetic Semiconductors Work?",
                "https://youtu.be/-rJMfceY6z4",
                "https://img.youtube.com/vi/-rJMfceY6z4/0.jpg"
        ));
        videos.add(new VideoItem(
                "Introduction to Spin Relaxation",
                "https://youtu.be/3WZKUoXSO8g",
                "https://img.youtube.com/vi/3WZKUoXSO8g/0.jpg"
        ));
        videos.add(new VideoItem(
                "Hall effect",
                "https://youtu.be/iPU_pzrg4UE",
                "https://img.youtube.com/vi/iPU_pzrg4UE/0.jpg"
        ));
        videos.add(new VideoItem(
                "The Quantum Spin Hall Effect and its importance",
                "https://youtu.be/J5NLysqf184",
                "https://img.youtube.com/vi/J5NLysqf184/0.jpg"
        ));
        videos.add(new VideoItem(
                "How do LED lights work? Inside an LED small light",
                "https://youtu.be/Z6M3R6RjEas",
                "https://img.youtube.com/vi/Z6M3R6RjEas/0.jpg"
        ));
        videos.add(new VideoItem(
                "What is Rashba Effect? | Spintronics Theory",
                "https://youtu.be/xjJtdmUvgHI",
                "https://img.youtube.com/vi/xjJtdmUvgHI/0.jpg"
        ));
        videos.add(new VideoItem(
                "Spin FET",
                "https://youtu.be/SZfEaULk3hs",
                "https://img.youtube.com/vi/SZfEaULk3hs/0.jpg"
        ));
        videos.add(new VideoItem(
                "2D materials beyond graphene",
                "https://youtu.be/jkAXhJWixJ8",
                "https://img.youtube.com/vi/jkAXhJWixJ8/0.jpg"
        ));
        videos.add(new VideoItem(
                "Transition Metal Dichalcogenides: properties, exfoliation and characterization",
                "https://youtu.be/ewTXTrdqQP0",
                "https://img.youtube.com/vi/ewTXTrdqQP0/0.jpg"
        ));
        videos.add(new VideoItem(
                "Spin Seebeck effect and spin transport - Sergio Machado Rezende",
                "https://www.youtube.com/watch?v=YHLE0BYgfOg",
                "https://img.youtube.com/vi/YHLE0BYgfOg/0.jpg"
        ));
        videos.add(new VideoItem(
                "Quantum Dot Qubits",
                "https://youtu.be/vQnA05VnGis",
                "https://img.youtube.com/vi/vQnA05VnGis/0.jpg"
        ));
        videos.add(new VideoItem(
                "Spin Qubits Explained: Quantum Computing with Silicon",
                "https://youtu.be/fao-hMe6Cws",
                "https://img.youtube.com/vi/fao-hMe6Cws/0.jpg"
        ));

        for (VideoItem video : videos) {
            View cardView = getLayoutInflater().inflate(R.layout.item_video_card, container, false);

            TextView  title     = cardView.findViewById(R.id.tvVideoTitle);
            ImageView thumbnail = cardView.findViewById(R.id.ivVideoThumb);

            title.setText(video.title);

            // Glide — thread-safe, xatolarni o'z ichida boshqaradi
            Glide.with(this)
                    .load(video.thumbnailUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_gallery)
                    .centerCrop()
                    .into(thumbnail);

            String url = video.youtubeUrl;
            cardView.setOnClickListener(v ->
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            );

            container.addView(cardView);
        }
    }
}
