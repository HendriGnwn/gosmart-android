package com.atc.gosmartlesmagistra.activity;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.atc.gosmartlesmagistra.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by cranium-01 on 18/09/17.
 */

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntro2Fragment.newInstance("Selamat Datang di Go Smart Les Magistra", "Tempat les favorit Siswa yang Cerdas dan mau jadi Juara", R.drawable.splash_logo, getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntro2Fragment.newInstance("Guru Terbaik", "Kami memiliki Pengajar yang baik dan berpengalaman", R.drawable.teacher, getResources().getColor(R.color.colorAccent)));
        addSlide(AppIntro2Fragment.newInstance("Guru", "Bisa memilih Guru yang sesuai keinginan", R.drawable.link, getResources().getColor(R.color.colorYellow)));
        addSlide(AppIntro2Fragment.newInstance("Daftar sebagai Siswa", "Daftarkan diri Anda sebagai siswa, dan daftar les segera", R.drawable.student, getResources().getColor(R.color.colorAccent)));
        addSlide(AppIntro2Fragment.newInstance("Daftar sebagai Guru", "Jika Anda lulusan Sarjana, silakan bergabung bersama kami untuk menjadi Guru", R.drawable.link, getResources().getColor(R.color.colorPrimary)));

        showSkipButton(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent;
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent;
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);

    }
}
