package com.example.stopwatch

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Timer
import kotlin.concurrent.timer

class UpgradeMainActivity : AppCompatActivity(), View.OnClickListener {

    // 뷰 늦은 초기화
    private lateinit var btn_start : Button
    private lateinit var btn_refresh : Button
    private lateinit var tv_minute : TextView
    private lateinit var tv_second : TextView
    private lateinit var tv_millisecond : TextView

    private var isRunning = false
    private var timer : Timer? = null
    private var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_upgrade)

        btn_start = findViewById(R.id.btn_start)
        btn_refresh = findViewById(R.id.btn_refresh)
        tv_minute = findViewById(R.id.tv_minute)
        tv_second = findViewById(R.id.tv_second)
        tv_millisecond = findViewById(R.id.tv_millisecond)

        btn_start.setOnClickListener(this)
        btn_refresh.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_start -> {
                if(isRunning){
                    pause()
                }else{
                    start()
                }
            }
            R.id.btn_refresh -> {
                refresh()
            }
        }


    }

    private fun start() {
        btn_start.text = getString(R.string.btn_pause_eng)
        //btn_start.setBackgroundColor(getColor(R.color.btn_pause))
        isRunning = true

        //timer 함수는 항상 백그라운드에서 실행
        timer = timer(period = 10){
            // 1000ms = 1s
            // 0.01 time +1
            time ++

            val milli_second = time%100
            val second = (time%6000)/100
            val minute = time/6000

            //백그라운드 스레드에서는 ui 자원에 접근할 수 없어서 해당 함수 사용
            runOnUiThread {
                if(isRunning){
                    tv_millisecond.text = if(milli_second < 10 ) ":0${milli_second}" else ":${milli_second}"
                    tv_second.text = if(second < 10) ":0${second}" else ":${second}"
                    tv_minute.text = "${minute}"
                }

            }
        }
    }

    private fun pause() {
        btn_start.text = getString(R.string.btn_start_eng)
        //btn_start.setBackgroundColor(getColor(R.color.btn_start))

        isRunning = false
        timer?.cancel()
    }

    private fun refresh() {
        timer?.cancel()

        btn_start.text = getString(R.string.btn_start_eng)
       // btn_start.setBackgroundColor(getColor(R.color.btn_start))

        isRunning = false
        time = 0
        tv_millisecond.text = ":00"
        tv_second.text = ":00"
        tv_minute.text = "00"
    }


}