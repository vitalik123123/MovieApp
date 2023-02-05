package com.example.fintech2023chupin.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.fintech2023chupin.R
import com.example.fintech2023chupin.databinding.ActivityMainBinding
import com.example.fintech2023chupin.ui.movieslist.ui.MoviesListFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.root, MoviesListFragment.newInstance()).commit()
        }
    }
}