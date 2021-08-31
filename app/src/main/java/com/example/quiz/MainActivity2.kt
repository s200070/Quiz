package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AlertDialog
import com.example.quiz.databinding.ActivityMain2Binding
import com.bumptech.glide.Glide
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.CompletableFuture

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private var data = arrayListOf<ArrayList<String>>()
    private val studentNames = arrayListOf<String>("s20007","s20010","s20014","s20024")
    private var allData = arrayListOf<ArrayList<String>>()
    private var crrectCount = 0
    private var nowCount = 1
    private var tempData = arrayListOf<String>()
    private var crrectNum = 0
    private var allTime = 0L
    private var times = allTimer(100 * 1000L, 100L)
    private var quizTimer = qCount(10L * 1000L, 100L)


    inner class allTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        var fastTime = millisInFuture
        override fun onTick(unko: Long) {
            val m = (fastTime - unko) / 1000L / 60L
            val s = (fastTime - unko) / 1000L % 60L
            binding.timer.text = "%1d:%2$02d".format(m, s)
            allTime = fastTime - unko
        }

        override fun onFinish() {

        }
    }

    inner class qCount(ryota: Long, kuni: Long) : CountDownTimer(ryota, kuni) {
        override fun onTick(millisUntilFinished: Long) {
            binding.quiztimer.progress = (millisUntilFinished / 100).toInt()
        }

        override fun onFinish() {
            clickAnswer(0)
        }

    }

    private fun printQuiz(n: Int) {
        var soloData = data[n]
        tempData = arrayListOf<String>()
        for (i in (2..5)) {
            tempData.add(soloData[i])
        }
        tempData.shuffle()
        crrectNum = tempData.indexOf(soloData[2])

        println(tempData)

        binding.quizView.text = data[n][0]

        Glide.with(this)
            .load(resources.getIdentifier(soloData[1].split(".")[0], "drawable", packageName))
            .into(binding.imageView)

        binding.t1.text = tempData[0]
        binding.t2.text = tempData[1]
        binding.t3.text = tempData[2]
        binding.t4.text = tempData[3]
        quizTimer.start()


    }

    private fun clickAnswer(n: Int) {
        quizTimer.cancel()
        if (++nowCount >= 11) {
            times.cancel()
            if (crrectNum + 1 == n) {
                dialog(true)
                crrectCount++
            } else {
                dialog(false)
            }

            val intent = Intent(this, MainActivity3::class.java)
            intent.putExtra("allTime", allTime)
            intent.putExtra("score", crrectCount)
            startActivity(intent)

        } else {
            if (crrectNum + 1 == n) {
                dialog(true)
                crrectCount++
            } else {
                dialog(false)
            }
            printQuiz(nowCount)
        }
    }


    private fun dialog(answer: Boolean) {
        if (answer) {
            AlertDialog.Builder(this)
                .setTitle("正解")
                .setMessage("正解は${tempData[crrectNum]}です")
                .setPositiveButton("OK", null)
                .show()
        } else {
            AlertDialog.Builder(this)
                .setTitle("不正解")
                .setMessage("正解は${tempData[crrectNum]}です")
                .setPositiveButton("OK", null)
                .show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        var a: InputStream? = null
        var i: BufferedReader? = null

        for (name in studentNames) {
            try {
                try {
                    a = assets.open("${name}.csv")
                    i = BufferedReader(InputStreamReader(a))

                    val voidString = i.readLine()
                    do {
                        var o = arrayListOf<String>()
                        val e = i.readLine()
                        println(e)
                        for (i in e.split(",")) {
                            o.add(i)
                        }
                        allData.add(o)
                    } while (e != null)
                } finally {
                    a?.close()
                    i?.close()
                }
            } catch (e: Exception) {
                println(e)
            }
        }

        allData.shuffle()
        data = allData


        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        printQuiz(1)
        times.start()

        binding.b1.setOnClickListener { clickAnswer(1) }
        binding.b2.setOnClickListener { clickAnswer(2) }
        binding.b3.setOnClickListener { clickAnswer(3) }
        binding.b4.setOnClickListener { clickAnswer(4) }


    }
}


