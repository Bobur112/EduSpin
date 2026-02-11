package com.bobur.quizforphysics.other;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.View;

import com.bobur.quizforphysics.R;
import java.util.ArrayList;

public class VideoListActivity extends AppCompatActivity {

    // Videolar ro‘yxati uchun ichki klass
    class VideoItem {
        String title;
        String youtubeUrl;
        String thumbnailUrl;

        VideoItem(String title, String youtubeUrl, String thumbnailUrl) {
            this.title = title;
            this.youtubeUrl = youtubeUrl;
            this.thumbnailUrl = thumbnailUrl;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        LinearLayout container = findViewById(R.id.videoContainer);

        ArrayList<VideoItem> videos = new ArrayList<>();

        // 🔹 Shu yerga sizning videolaringizni qo‘shing
        videos.add(new VideoItem(
                "Orbital magnetic moment of an electron revolving in an orbit of an atom",
                "https://www.youtube.com/watch?v=Dpo4Ev8LAkI", // bu yerga haqiqiy YouTube ssilka
                "https://img.youtube.com/vi/Dpo4Ev8LAkI/0.jpg"
        ));

        videos.add(new VideoItem(
                "Diaamgnetic, Paramagnetic and Ferromagnetic Materials",
                "https://youtu.be/AwCTDtknYWU",
                "https://img.youtube.com/vi/AwCTDtknYWU/0.jpg"
        ));

        videos.add(new VideoItem(
                "Ferromagnetism & curie temperature | Magnetism & matter | Physics | Khan Academy",
                "https://www.youtube.com/watch?v=8a2IZZdIeXw",
                "https://img.youtube.com/vi/8a2IZZdIeXw/0.jpg"
        ));
        videos.add(new VideoItem(
                "Solid State Magnetism (Lecture 24): Stoner Model",
                "https://www.youtube.com/watch?v=kRy2_e4nlQU",
                "https://img.youtube.com/vi/kRy2_e4nlQU/0.jpg"
        ));
        videos.add(new VideoItem(
                "How Do Diluted Magnetic Semiconductors Work? - Chemistry For Everyone",
                "https://youtu.be/-rJMfceY6z4",
                "https://img.youtube.com/vi/-rJMfceY6z4/0.jpg"
        ));
        videos.add(new VideoItem(
                "Mod-01 Lec-13 Introduction to Spin Relaxation",
                "https://youtu.be/3WZKUoXSO8g",
                "https://img.youtube.com/vi/3WZKUoXSO8g/0.jpg"
        ));
        videos.add(new VideoItem(
                "Hall effect",
                "https://youtu.be/iPU_pzrg4UE?si=mGflQQp4w4RfXshC",
                "https://img.youtube.com/vi/iPU_pzrg4UE/0.jpg"
        ));
        videos.add(new VideoItem(
                "The Quantum Spin Hall Effect and its importance",
                "https://youtu.be/J5NLysqf184?si=YJOCYwyk96a_KaD8",
                "https://img.youtube.com/vi/J5NLysqf184/0.jpg"
        ));
        videos.add(new VideoItem(
                "How do LED lights work? Inside an LED small light",
                "https://youtu.be/Z6M3R6RjEas?si=n0tXoILVLL6B8tlw",
                "https://img.youtube.com/vi/Z6M3R6RjEas/0.jpg"
        ));
        videos.add(new VideoItem(
                "What is Rashba Effect ? | Spintronics Theory",
                "https://youtu.be/xjJtdmUvgHI",
                "https://img.youtube.com/vi/xjJtdmUvgHI/0.jpg"
        ));
        videos.add(new VideoItem(
                "Spin FET",
                "https://youtu.be/SZfEaULk3hs?si=mubdFGUdyDOm1f1G",
                "https://img.youtube.com/vi/SZfEaULk3hs/0.jpg"
        ));
        videos.add(new VideoItem(
                "Двумерные материалы помимо графена",
                "https://youtu.be/jkAXhJWixJ8",
                "https://img.youtube.com/vi/jkAXhJWixJ8/0.jpg"
        ));
        videos.add(new VideoItem(
                "Transition Metal Dichalcogenides, their properties, exfoliation, and characterization | Xintong Li",
                "https://youtu.be/ewTXTrdqQP0?si=V8bO3X1SX95rtV2D",
                "https://img.youtube.com/vi/ewTXTrdqQP0/0.jpg"
        ));
        videos.add(new VideoItem(
                "Spin Seebeck effect and spin transport in magnetic metals and insulators - Sergio Machado Rezende",
                "https://www.youtube.com/live/YHLE0BYgfOg?si=YISlgScmw9AiQNfs",
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

        // 🔹 Dinamik tarzda CardView’larni hosil qilish
        for (VideoItem video : videos) {
            View cardView = getLayoutInflater().inflate(R.layout.item_video_card, container, false);

            TextView title = cardView.findViewById(R.id.tvVideoTitle);
            ImageView thumbnail = cardView.findViewById(R.id.ivVideoThumb);

            title.setText(video.title);

            // YouTube preview rasmi
            new Thread(() -> {
                try {
                    java.net.URL url = new java.net.URL(video.thumbnailUrl);
                    final android.graphics.Bitmap bmp = android.graphics.BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    runOnUiThread(() -> thumbnail.setImageBitmap(bmp));
                } catch (Exception ignored) {}
            }).start();

            // Bosilganda video ochiladi
            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.youtubeUrl));
                startActivity(intent);
            });

            container.addView(cardView);
        }
    }
}
