package com.tugas.storyappbydavid

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.SharedPreferences
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import com.tugas.storyappbydavid.context.SuperApplication
import com.tugas.storyappbydavid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val sharedPrefFile = "kotlinsharedpreference"
    val contextAplikasi = SuperApplication.appContext

    private lateinit var binding: ActivityMainBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edLoginPassword.inputType = 129
        binding.progressBar.visibility = View.GONE

        playAnimation()
        val sharedPreferences: SharedPreferences? = contextAplikasi?.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
        val sharedNameValue = sharedPreferences?.getString("token","defaultname")

        if (sharedNameValue!="defaultname") {
            finish()
            overridePendingTransition(0, 0)
            val intent = Intent(this@MainActivity, ListStoryActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }


        binding.edLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.count() < 8) {
                    binding.loginButton.isEnabled = false
                }
                else {
                    binding.loginButton.isEnabled = true
                }
            }
            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })

        binding.loginButton.setOnClickListener {
            viewModel.loginAccount(binding.edLoginEmail.text.toString(), binding.edLoginPassword.text.toString())
            val editor: SharedPreferences.Editor? =  sharedPreferences?.edit()
            viewModel.isLoading.observe(this, {
                showLoading(it)
            })

            viewModel.tokenMasuk.observe(this, {
                    tes ->
                if (tes != "null") {
                    editor?.putString("token", tes)
                    editor?.apply()
                    editor?.commit()
                    finish()
                    overridePendingTransition(0, 0)
                    val intent = Intent(this@MainActivity, ListStoryActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                }
            })
        }

        binding.registerClickable.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView2, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(500)
        val subtitle = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(500)
        val emailText = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(500)
        val passwordText = ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(500)
        val loginButton = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val signupText = ObjectAnimator.ofFloat(binding.textView3, View.ALPHA, 1f).setDuration(500)
        val signupButton  = ObjectAnimator.ofFloat(binding.registerClickable, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, subtitle, emailText, passwordText, loginButton, signupText, signupButton)
            start()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}