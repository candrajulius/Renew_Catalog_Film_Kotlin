package com.candra.katalog_film.core.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.candra.katalog_film.core.ui.mainutama.MainActivity
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.core.utils.Helper.contentImage
import com.candra.katalog_film.databinding.SplashScreenActivityBinding

class SplashScreen: AppCompatActivity(){

    private lateinit var binding: SplashScreenActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.gambarContent.contentImage(Constant.IMAGE_LOGO,this@SplashScreen)
        toMainActivity()
    }

    private fun toMainActivity(){
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this@SplashScreen,MainActivity::class.java)).also {
                finish()
            }
        },3000)
    }
}