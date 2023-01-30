package com.tabasumu.sample

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.tabasumu.jambo.Jambo
import com.tabasumu.sample.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

//        Jambo.w("This is a WARN log")
//        Jambo.i("This is an INFO log")
//        Jambo.d("This is a DEBUG log")
//        Jambo.e("This is an ERROR log")
//        Jambo.v("This is a VERBOSE log")
//        Jambo.wtf("This is an ASSERT log")

        Timber.w("This is a WARN log")
        Timber.i("This is an INFO log")
        Timber.d("This is a DEBUG log")
        Timber.e("This is an ERROR log")
        Timber.v("This is a VERBOSE log")
        Timber.wtf("This is an ASSERT log")

        binding.btnMainCrash.setOnClickListener {
            throw RuntimeException("Triggered Crash!")
        }

    }
}