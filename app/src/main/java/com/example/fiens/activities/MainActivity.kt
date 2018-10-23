package com.example.fiens.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import java.io.Serializable

class MainActivity : CounterActivity() {

    //Name of the class --> used for logging
    override val CLASSLOG = "MainActivityLog"

    override fun getLayoutId() = R.layout.activity_main
}
