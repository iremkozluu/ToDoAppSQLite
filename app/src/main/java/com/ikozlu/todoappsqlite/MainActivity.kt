package com.ikozlu.todoappsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ikozlu.todoappsqlite.common.viewBinding
import com.ikozlu.todoappsqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding (ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}