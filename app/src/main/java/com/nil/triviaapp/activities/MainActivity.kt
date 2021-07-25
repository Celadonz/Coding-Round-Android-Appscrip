package com.nil.triviaapp.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.nil.triviaapp.R
import com.nil.triviaapp.fragments.NameFragment
import com.nil.triviaapp.viewmodels.MainActivityViewModel
import es.dmoral.toasty.Toasty


class MainActivity : AppCompatActivity() {

    private var shouldExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //start the appropriate fragment
        try {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(R.id.layout_inflate, NameFragment.newInstance(), "GetName")
            ft.commit()
        } catch (err: Exception) {
            Toasty.error(applicationContext, "Error: $err", Toast.LENGTH_SHORT, false).show()
        }
    }

    //prevent user from closing the application my mistake by pressing back button once
    override fun onBackPressed() {
        val fm: FragmentManager = supportFragmentManager
        val fragmentCount: Int = fm.backStackEntryCount

        if (fragmentCount == 0) {
            if (shouldExit) {
                finish()
            } else {
                shouldExit = true
                Toasty.info(applicationContext, "Press again to exit.", Toast.LENGTH_SHORT, false).show()
                Handler(Looper.getMainLooper()).postDelayed(Runnable { shouldExit = false }, 2000)
            }
        } else {
            super.onBackPressed()
        }
    }
}