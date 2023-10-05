package com.example.robothomework

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var redRobotImg: ImageView
    private lateinit var whiteRobotImg: ImageView
    private lateinit var yellowRobotImg: ImageView
    private lateinit var messageBox: TextView
    private lateinit var rewardButton : Button

    private lateinit var robotImages: MutableList<ImageView>

    private val robotViewModel: RobotViewModel by viewModels()

    private val robots = listOf(
        Robot(
            R.string.red_robot_message,
            false,
            R.drawable.king_of_detroit_robot_red_large,
            R.drawable.king_of_detroit_robot_red_small,
            0,
            charArrayOf()
        ),
        Robot(
            R.string.white_robot_message,
            false,
            R.drawable.king_of_detroit_robot_white_large,
            R.drawable.king_of_detroit_robot_white_small,
            0,
            charArrayOf()
        ),
        Robot(
            R.string.yellow_robot_message,
            false,
            R.drawable.king_of_detroit_robot_yellow_large,
            R.drawable.king_of_detroit_robot_yellow_small,
            0,
            charArrayOf()
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate() Entered")

        redRobotImg = findViewById(R.id.redRobot)
        whiteRobotImg = findViewById(R.id.whiteRobot)
        yellowRobotImg = findViewById(R.id.yellowRobot)
        messageBox = findViewById(R.id.messageBox)
        rewardButton = findViewById(R.id.rewardButton)

        robotImages = mutableListOf(redRobotImg, whiteRobotImg, yellowRobotImg)

        redRobotImg.setOnClickListener {
            robotViewModel.advanceTurn()
            updateMessageBox()
            setRobotTurn()
            setRobotImage()
            setRobotRewardsToast()
        }
        whiteRobotImg.setOnClickListener {
            robotViewModel.advanceTurn()
            updateMessageBox()
            setRobotTurn()
            setRobotImage()
            setRobotRewardsToast()
        }
        yellowRobotImg.setOnClickListener {
            robotViewModel.advanceTurn()
            updateMessageBox()
            setRobotTurn()
            setRobotImage()
            setRobotRewardsToast()
        }

        rewardButton.setOnClickListener {view : View ->
            //val intent = Intent(this, RobotPurchase::class.java)
            //intent.putExtra(EXTRA_ROBOT_ENERGY, robots[robotViewModel.currentTurn - 1].myEnergy)
            val intent = RobotPurchase.newIntent(
                this,
                robots[robotViewModel.currentTurn - 1].myEnergy,
                robots[robotViewModel.currentTurn - 1].robotImgLarge,
                robots[robotViewModel.currentTurn - 1].myReward)
            //startActivity(intent)
            purchaseLauncher.launch(intent)
        }

        // Check whether the current turn count equals to the original value and update it
        if (robotViewModel.currentTurn != 0) {
            updateMessageBox()
            setRobotTurn()
            setRobotImage()
        }
        Log.d(TAG, "Got a RobotViewModel : $robotViewModel")
    }

    private val purchaseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            robots[robotViewModel.currentTurn - 1].myReward = result.data?.getCharArrayExtra(
                EXTRA_ROBOT_REWARDS_GOT)!!
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() entered")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() entered")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() entered")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() entered")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() entered")
    }

    private fun updateMessageBox() {
        messageBox.setText(robots[robotViewModel.currentTurn - 1].robotMessageResource)
    }

    private fun setRobotTurn() {
        for (robot in robots) {
            robot.myTurn = false
        }
        robots[robotViewModel.currentTurn - 1].myTurn = true
        robots[robotViewModel.currentTurn - 1].myEnergy += 1
    }

    private fun setRobotImage() {
        for (indy in robots.indices) {
            if (robots[indy].myTurn) {
                robotImages[indy].setImageResource(robots[indy].robotImgLarge)
            } else {
                robotImages[indy].setImageResource(robots[indy].robotImgSmall)
            }
        }
    }

    private fun setRobotRewardsToast() {
        val rewardList = getString(R.string.reward_list) +
                " " +
                robots[robotViewModel.currentTurn - 1].myReward.contentToString()
        if (robots[robotViewModel.currentTurn - 1].myReward.isNotEmpty()) {
            Toast.makeText(this, rewardList, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.empty_reward_list, Toast.LENGTH_SHORT).show()
        }
    }
}
