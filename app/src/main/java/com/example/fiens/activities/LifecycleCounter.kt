package com.example.fiens.activities

import java.io.Serializable

class LifecycleCounter : Serializable{
    var onCreate: Int = 0
        private set
    var onStart: Int = 0
        private set
    var onResume: Int = 0
        private set
    var onPause: Int = 0
        private set
    var onRestart: Int = 0
        private set
    var onStop: Int = 0
        private set
    var onDestroy: Int = 0
        private set

    fun onCreateMethod(){
        onCreate++
    }

    fun onStartMethod(){
        onStart++
    }

    fun onResumeMethod(){
        onResume++
    }

    fun onPauseMethod(){
        onPause++
    }

    fun onRestartMethod(){
        onRestart++
    }

    fun onStopMethod(){
        onStop++
    }

    fun onDestroyMethod(){
        onDestroy++
    }
}