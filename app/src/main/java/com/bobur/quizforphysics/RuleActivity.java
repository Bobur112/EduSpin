package com.bobur.quizforphysics;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.bobur.quizforphysics.other.PdfActivity;

import java.util.ArrayList;

public class RuleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);

        ListView listView = findViewById(R.id.listViewMavzular);

        // Mavzular ro‘yxati (har bir satrga bold format beramiz)
        ArrayList<SpannableString> mavzular = new ArrayList<>();

        String[] nomlar = {
                "1-mavzu: \nKirish. Qattiq jismlarning magnit xususiyatliga qarab bo'lishi. Atomlar xususiy va ularning magnit momentlari",
                "2-mavzu: \nQattiq jismlarning magnit xususiyatli. Paramagnetizm va Ferromagnitizim. Stoner modeli. Kyuri temperaturasi",
                "3-mavzu: \nYarimo’tkazgichning magnit xususiyatlari. Magnit yarimo’tkazgichlar. Zener p–d almashuvchi o‘zaro ta’siri",
                "4-mavzu: \nSpinni yarimo‘tkazgichlarga kiritish. Spinning relaksatsiyasi va yarimo‘tkazgichlarni o‘tkazuvchilarni mos kelmasligi muammosi. Elliot–Yafet, Dyakonov–Perel va Aronov–Bir–Pikus mexanizmlari",
                "5-mavzu: \nAnomal va Spin-Hall effektlar. Magnit yarimo‘tkazgichlarda anomal va Spin-Hall effektlar mexanizmlari",
                "6-mavzu: \nMagnit yarimo‘tkazgichlar optikasi. Xanle effekti. Magnit yarimo‘tkazgichli yorug‘lik chiqaradigan diodlar",
                "7-mavzu: \nBipolyar spintronik qurilmalar va spin maydoni tranzistori. Magnit diod. Magnit bipolyar tranzistor. Rashba spin-orbita effekti. Datta–Das tranzistori.",
                "8-mavzu: \nIkki o‘lchovli magnit yarimo‘tkazgichlar. Grafen, silitsen va germenen. O‘tish metall xalkogenidlari.",
                "9-mavzu: \nYarimo‘tkazgichli termospintronika. Spin Zeebek effekti. Spin memristor.",
                "10-mavzu: \nYarimo‘tkazgich magnit kvant nuqtalari. Magnit yarimo‘tkazgichlar asosida kvantli kompyuterda ishlatish uchun kvantli bit (kubit) yaratish. Magnit maydonga ta’sirli asboblar turlari va ularning ishlash prinsiplari."
        };

        for (String s : nomlar) {
            int index = s.indexOf(":") + 1; // "1-mavzu:" qismi qayergacha ekanligini topish
            SpannableString ss = new SpannableString(s);
            ss.setSpan(new StyleSpan(Typeface.BOLD), 0, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // faqat shu qismini bold qilamiz
            mavzular.add(ss);
        }

        // Adapter orqali ListView’ga joylaymiz
        ArrayAdapter<SpannableString> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mavzular);
        listView.setAdapter(adapter);

        // Har bir mavzuga bosilganda PDF ochish
        listView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            Intent intent = new Intent(RuleActivity.this, PdfActivity.class);
            intent.putExtra("pdfName", "mavzu" + (position + 1) + ".pdf");
            startActivity(intent);
        });
    }
}
