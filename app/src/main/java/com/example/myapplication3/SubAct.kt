package com.example.myapplication3

import android.content.Context
import android.content.SharedPreferences


class SubAct(context: Context?) {
    private var sharedPref : SharedPreferences = context!!.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    private var highScore: Int = 0
    private var highScore1: Int = 0
    private var highScore2: Int = 0
    private var isStarted: Boolean = false
    private var isOver: Boolean = false

//    private var timeS: Int = 10

    init{
        highScore = sharedPref.getInt(HIGH_SCORE, 0)
        isStarted = sharedPref.getBoolean(ISS, false)
        isOver = sharedPref.getBoolean(ISO, false)
        highScore1 = sharedPref.getInt(HIGH_SCORE1, 0)
        highScore2 = sharedPref.getInt(HIGH_SCORE2, 0)
//        timeS = sharedPref.getInt(TIMES, 10)
    }

    fun highScore(): Int = highScore
    fun highScore1(): Int = highScore1
    fun highScore2(): Int = highScore2
    fun isStarted(): Boolean = isStarted
    fun isOver(): Boolean = isOver
//    fun timeS(): Int = timeS

    fun updateHighScore(score: Int){
        with(sharedPref.edit()){
            putInt(HIGH_SCORE, score)
            apply()
        }
        highScore = sharedPref.getInt(HIGH_SCORE, 0)
    }
    fun updateHighScore1(score: Int){
        with(sharedPref.edit()){
            putInt(HIGH_SCORE1, score)
            apply()
        }
        highScore1 = sharedPref.getInt(HIGH_SCORE1, 0)
    }
    fun updateHighScore2(score: Int){
        with(sharedPref.edit()){
            putInt(HIGH_SCORE2, score)
            apply()
        }
        highScore2 = sharedPref.getInt(HIGH_SCORE2, 0)
    }
    fun updateIsStarted(value: Boolean ){
        with(sharedPref.edit()){
            putBoolean(ISS, value)
            apply()
        }
        isStarted = sharedPref.getBoolean(ISS, false)
    }
    fun updateIsOver(value: Boolean ){
        with(sharedPref.edit()){
            putBoolean(ISO, value)
            apply()
        }
        isOver = sharedPref.getBoolean(ISO, false)
    }


//    fun updateTimeS(t: Int){
//        timeS = t
//        with(sharedPref.edit()){
//            putInt(TIMES, t)
//            apply()
//        }
//    }

    companion object{
        const val HIGH_SCORE = "fragment2_highScore"
        const val HIGH_SCORE1 = "fragment3_highScore"
        const val HIGH_SCORE2 = "fragment4_highScore"
        const val ISS = "isStarted"
        const val ISO = "isOver"
        const val TIMES = "timeS"
    }
}