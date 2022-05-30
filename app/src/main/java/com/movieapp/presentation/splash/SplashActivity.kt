package com.movieapp.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.movieapp.R
import com.movieapp.presentation.main.MainActivity

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Run()
        }
        val main = Intent(this, MainActivity::class.java)


        val timer = object : CountDownTimer(2000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                overridePendingTransition(0, 0)
                main.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(main)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }
        }
        timer.start()
    }

    @Composable
    @Preview(showBackground = true)
    fun Run() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.blue)), contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.width(100.dp)) {

                Image(modifier = Modifier.padding(bottom = 20.dp), painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
                LinearProgressIndicator(color = colorResource(id = R.color.white),modifier = Modifier.background(colorResource(id = R.color.blue)))
            }
        }
    }
}