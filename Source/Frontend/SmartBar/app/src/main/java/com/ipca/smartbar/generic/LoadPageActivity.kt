package com.ipca.smartbar.generic

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ipca.smartbar.R
import com.ipca.smartbar.client.products.ClientProductsActivity


class LoadPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_page)

        val logoIPCA = findViewById<ImageView>(R.id.imageViewLogoIPCA)
        val animScaleIn = AnimationUtils.loadAnimation(this, R.anim.scale_in)
        logoIPCA.startAnimation(animScaleIn)

        animScaleIn.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}
            override fun onAnimationRepeat(p0: Animation?) {}
            override fun onAnimationEnd(p0: Animation?) {
                val intent = Intent(this@LoadPageActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        })
    }
}