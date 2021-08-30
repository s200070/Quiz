package com.example.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quiz.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    private  lateinit var binding: ActivityMain3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val time = intent.getLongExtra("allTime", 0L)
        val score = intent.getIntExtra("score", 0)

        val m = time / 1000L / 60L
        val s = time / 1000L % 60L

        binding.time.text = "%1d:%2$02d".format(m, s)
        binding.score.text = "${score} / 10"

    }
}