package com.example.appexpensemanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.appexpensemanager.utils.PreferenceHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : AppCompatActivity() {
    private var mToolbar: Toolbar? = null
    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPrefHelper()

        setTheme(R.style.Theme_AppExpenseManager)
        setContentView(R.layout.activity_main)

        checkNightMode()

        mToolbar = main_toolbar
        setSupportActionBar(mToolbar)

        bottom_navigation.setupWithNavController(navHostFragment.findNavController())
        bottom_navigation.setOnNavigationItemReselectedListener { /* recreate */ }
    }

    private fun checkNightMode(){
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            isChecked = true
        night_mode_image_view.setOnClickListener {
            if (!isChecked) {
                PreferenceHelper.getInstance().setBoolean("theme", false)
                this.recreate()
            } else {
                PreferenceHelper.getInstance().setBoolean("theme", true)
                this.recreate()
            }
        }
    }

    private fun initPrefHelper() {
        PreferenceHelper.initialize(this)
        if (!PreferenceHelper.getInstance().getBoolean("theme")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}