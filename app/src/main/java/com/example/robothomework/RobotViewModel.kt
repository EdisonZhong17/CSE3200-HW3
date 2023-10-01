package com.example.robothomework

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "RobotViewModel"
class RobotViewModel : ViewModel() {

    private var _currentTurn: Int = 0

    val currentTurn: Int
        get() = _currentTurn

    fun advanceTurn() {
        _currentTurn++
        if (_currentTurn > 3) {
            _currentTurn = 1
        }
    }

    init {
        Log.d(TAG, "instance of RobotViewModel created.")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "instance of RobotViewModel about to be destroyed.")
    }
}
