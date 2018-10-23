package com.example.fiens.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.io.Serializable

abstract class CounterActivity : AppCompatActivity(), AnkoLogger {

    //Name of the class --> used for logging
    abstract val CLASSLOG  : String

    //Get the id of the layout
    abstract fun getLayoutId(): Int

    //object that includes all the counters
    private var lifecycleCounter = LifecycleCounter()

    // "static" variables
    companion object {
        //boolean to test with shared preferences OR savedInstanceState
        private val runWithSharedPreferences = true

        //names for shared preferences
        private val PREFS_NAME = "activities_prefs"
        private val LIFECYCLE_NAME = "lifecycleCounter"

        //for deserializing the shared preferences!
        private val gson = Gson()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        logStartOrStopMethod(true, object{}.javaClass.enclosingMethod.name)

        //Creating the layout + calling super
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        if (runWithSharedPreferences) {
            getLifecycleCounterSharedPrefs()
        }
        else{
            getLifecycleCounterSavedInstance(savedInstanceState)
        }

        //Increase count for onCreate
        lifecycleCounter.onCreateMethod()

        //Write to the screen
        updateUI()

        logStartOrStopMethod(false, object{}.javaClass.enclosingMethod.name)
    }

    override fun onStop() {
        logStartOrStopMethod(true, object{}.javaClass.enclosingMethod.name)

        //Calling super
        super.onStop()

        //Increase count + update screen
        lifecycleCounter.onStopMethod()
        updateUI()

        if (runWithSharedPreferences) {
            setLifecycleCounterSharedPrefs()
        }

        logStartOrStopMethod(false, object{}.javaClass.enclosingMethod.name)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!runWithSharedPreferences){
            setLifecycleCounterSavedInstance(outState)
        }
    }

    override fun onStart() {
        logStartOrStopMethod(true, object{}.javaClass.enclosingMethod.name)

        //Calling super
        super.onStart()

        //Increase count + update screen
        lifecycleCounter.onStartMethod()
        updateUI()

        logStartOrStopMethod(false, object{}.javaClass.enclosingMethod.name)
    }

    override fun onResume() {
        logStartOrStopMethod(true, object{}.javaClass.enclosingMethod.name)

        //Calling super
        super.onResume()

        //Increase count + update screen
        lifecycleCounter.onResumeMethod()
        updateUI()

        logStartOrStopMethod(false, object{}.javaClass.enclosingMethod.name)
    }

    override fun onPause() {
        logStartOrStopMethod(true, object{}.javaClass.enclosingMethod.name)

        //Calling super
        super.onPause()

        //Increase count + update screen
        lifecycleCounter.onPauseMethod()
        updateUI()

        logStartOrStopMethod(false, object{}.javaClass.enclosingMethod.name)
    }

    override fun onRestart() {
        logStartOrStopMethod(true, object{}.javaClass.enclosingMethod.name)

        //Calling super
        super.onRestart()

        //Increase count + update screen
        lifecycleCounter.onRestartMethod()
        updateUI()

        logStartOrStopMethod(false, object{}.javaClass.enclosingMethod.name)
    }

    override fun onDestroy() {
        logStartOrStopMethod(true, object{}.javaClass.enclosingMethod.name)

        //Increase count
        lifecycleCounter.onDestroyMethod()

        if (runWithSharedPreferences) {
            setLifecycleCounterSharedPrefs()
        }

        Thread.sleep(5000)

        //Calling super
        super.onDestroy()

        logStartOrStopMethod(false, object{}.javaClass.enclosingMethod.name)
    }

    //Logging at the start or end of a method
    private fun logStartOrStopMethod(isStart: Boolean, funName: String) {
        val enterOrExit: String = if(isStart) "Entered" else "Exited"
        info("$CLASSLOG: $enterOrExit $funName")
    }


    private fun updateUI() {
        amountOnCreate.text = "${lifecycleCounter.onCreate}"
        amountOnStart.text = "${lifecycleCounter.onStart}"
        amountOnResume.text = "${lifecycleCounter.onResume}"
        amountOnPause.text = "${lifecycleCounter.onPause}"
        amountOnRestart.text = "${lifecycleCounter.onRestart}"
        amountOnStop.text = "${lifecycleCounter.onStop}"
        amountOnDestroy.text = "${lifecycleCounter.onDestroy}"
    }

    /** Getting lifecycleCounter from savedInstance when it's not null
    and store in attribute lifecycleCounter
     */
    private fun getLifecycleCounterSavedInstance(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            lifecycleCounter = it.getSerializable(LIFECYCLE_NAME) as LifecycleCounter
        }
    }

    /** Write to savedInstance
     */
    private fun setLifecycleCounterSavedInstance(outState: Bundle) {
        outState.putSerializable(LIFECYCLE_NAME, lifecycleCounter)
    }

    /** Getting lifecycleCounter from sharedPreferences
    and when the savedString it's not null
    --> store the deserialized String in attribute lifecycleCounter
     */
    private fun getLifecycleCounterSharedPrefs() {
        val savedString = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(LIFECYCLE_NAME, null)
        info("$CLASSLOG: Reading value for $LIFECYCLE_NAME as $savedString with SharedPreferences")
        savedString?.let {
            info("$CLASSLOG: Setting value for $LIFECYCLE_NAME as $savedString with SharedPreferences")
            lifecycleCounter = gson.fromJson(savedString, LifecycleCounter::class.java)
        }
    }

    /**Write to sharedPreferences as a JSON string (serialized object)
     */
    private fun setLifecycleCounterSharedPrefs() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        val lifecycleJSON = gson.toJson(lifecycleCounter as Serializable)
        info("$CLASSLOG: Saving value for $LIFECYCLE_NAME as $lifecycleJSON")
        sharedPreferences.putString(LIFECYCLE_NAME, lifecycleJSON).apply()
    }
}
