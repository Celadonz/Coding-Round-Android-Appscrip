package com.nil.triviaapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import com.nil.triviaapp.R
import es.dmoral.toasty.BuildConfig
import es.dmoral.toasty.Toasty
import timber.log.Timber
import timber.log.Timber.DebugTree

class Controller: Application() {
    override fun onCreate() {
        super.onCreate()

        //disable night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //initialize timber
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        //customize toasty
        /*val typeface = ResourcesCompat.getFont(this@Controller, R.font.roboto_slab)!!
        Toasty.Config.getInstance()
            .setToastTypeface(typeface)
            .apply()*/
    }
}