package com.example.recyclerviewwithdatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerviewwithdatastore.MainActivity.Companion.KEY_USER
import com.example.recyclerviewwithdatastore.databinding.ActivityDisplayUserTextBinding

class DisplayUserActivity : AppCompatActivity() {
    private var binding : ActivityDisplayUserTextBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayUserTextBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val bundle = intent.extras?.getBundle(MainActivity.KEY_BUNDLE_USER)
        val user = bundle?.getSerializable(KEY_USER) as User

        binding?.uiTvDisplayTitle?.text = user.title
        binding?.uiTvDisplayBody?.text = user.body

    }
}