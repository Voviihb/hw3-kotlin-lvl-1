package com.voviihb.dz3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lsf = LoginScreenFragment.newInstance()
        Log.d("!!!", "lsf " + lsf.hashCode().toString())
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, lsf)
            .commit()
    }
}