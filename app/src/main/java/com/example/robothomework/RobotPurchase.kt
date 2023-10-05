package com.example.robothomework

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

private const val TAG = "RobotPurchaseActivity"

private const val EXTRA_ROBOT_ENERGY = "com.example.robothomework.robot_energy"
private const val EXTRA_ROBOT_IMG = "com.example.robothomework.robot_image"
private const val EXTRA_ROBOT_REWARDS = "com.example.robothomework.robot_rewards"
const val EXTRA_ROBOT_REWARDS_GOT = "com.example.robothomework.reward_got"

class RobotPurchase : AppCompatActivity() {
    private lateinit var robotImage : ImageView
    private lateinit var rewardButton1 : Button
    private lateinit var rewardCost1 : TextView
    private lateinit var rewardButton2 : Button
    private lateinit var rewardCost2 : TextView
    private lateinit var rewardButton3 : Button
    private lateinit var rewardCost3 : TextView
    private lateinit var robotEnergyAvailable : TextView
    private var robotEnergy = 0
    private var robotImg = 1
    private var robotRewards = charArrayOf()
    private val rewards = listOf('A', 'B', 'C', 'D', 'E', 'F', 'G')
    private val rewardCost  : Map<Char, Int> = mapOf(
        'A' to 1,
        'B' to 2,
        'C' to 3,
        'D' to 3,
        'E' to 4,
        'F' to 4,
        'G' to 7
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_purchase)

        robotImage = findViewById(R.id.robotLarge)
        rewardButton1 = findViewById(R.id.buttonReward1)
        rewardCost1 = findViewById(R.id.costOfReward1Energy)
        rewardButton2 = findViewById(R.id.buttonReward2)
        rewardCost2 = findViewById(R.id.costOfReward2Energy)
        rewardButton3 = findViewById(R.id.buttonReward3)
        rewardCost3 = findViewById(R.id.costOfReward3Energy)
        robotEnergyAvailable = findViewById(R.id.robotEnergyToSpend)

        robotEnergy = intent.getIntExtra(EXTRA_ROBOT_ENERGY, 0)
        robotImg = intent.getIntExtra(EXTRA_ROBOT_IMG, 1)
        robotRewards = intent.getCharArrayExtra(EXTRA_ROBOT_REWARDS)!!

        robotEnergyAvailable.setText(robotEnergy.toString())
        robotImage.setImageResource(robotImg)

        // Generate random rewards
        val shuffledRewardsList = rewards.shuffled() // Shuffle the list randomly
        val selectedRewards = shuffledRewardsList.take(3).sorted()

        // Set rewards text to the buttons
        rewardButton1.setText("Reward \n" + selectedRewards[0].toString())
        rewardCost1.setText(rewardCost[selectedRewards[0]]!!.toString())
        rewardButton2.setText("Reward \n" + selectedRewards[1].toString())
        rewardCost2.setText(rewardCost[selectedRewards[1]]!!.toString())
        rewardButton3.setText("Reward \n" + selectedRewards[2].toString())
        rewardCost3.setText(rewardCost[selectedRewards[2]]!!.toString())

        rewardButton1.setOnClickListener { view: View ->
            if (makePurchase(selectedRewards[0])) {
                rewardButton1.isEnabled = false
                robotRewards += selectedRewards[0]
                val data = Intent().putExtra(EXTRA_ROBOT_REWARDS_GOT, robotRewards)
                setResult(Activity.RESULT_OK, data)
                Log.d(TAG, robotRewards.contentToString())
            }
        }

        rewardButton2.setOnClickListener { view: View ->
            if (makePurchase(selectedRewards[1])) {
                rewardButton2.isEnabled = false
                robotRewards += selectedRewards[1]
                val data = Intent().putExtra(EXTRA_ROBOT_REWARDS_GOT, robotRewards)
                setResult(Activity.RESULT_OK, data)
                Log.d(TAG, robotRewards.contentToString())
            }
        }

        rewardButton3.setOnClickListener { view: View ->
            if (makePurchase(selectedRewards[2])) {
                rewardButton3.isEnabled = false
                robotRewards += selectedRewards[2]
                val data = Intent().putExtra(EXTRA_ROBOT_REWARDS_GOT, robotRewards)
                setResult(Activity.RESULT_OK, data)
                Log.d(TAG, robotRewards.contentToString())
            }
        }
    }

    companion object {
        fun newIntent(packageContext : Context,
                      robot_energy : Int,
                      robot_image : Int,
                      robot_rewards : CharArray
                      ) : Intent {
            return Intent(packageContext, RobotPurchase::class.java).apply {
                putExtra(EXTRA_ROBOT_ENERGY, robot_energy)
                putExtra(EXTRA_ROBOT_IMG, robot_image)
                putExtra(EXTRA_ROBOT_REWARDS, robot_rewards)
            }
        }
    }

    private fun makePurchase(rewardOption : Char) : Boolean {
        if (robotEnergy >= rewardCost[rewardOption]!!) {
            val s1 = when {
                rewardOption == 'A' -> getString(R.string.reward_a)
                rewardOption == 'B' -> getString(R.string.reward_b)
                rewardOption == 'C' -> getString(R.string.reward_c)
                rewardOption == 'D' -> getString(R.string.reward_d)
                rewardOption == 'E' -> getString(R.string.reward_e)
                rewardOption == 'F' -> getString(R.string.reward_f)
                rewardOption == 'G' -> getString(R.string.reward_g)
                else -> getString(R.string.error_reward)
            }
            val s2 = getString(R.string.purchased)
            val s3 = s1 + " " + s2
            robotEnergy -= rewardCost[rewardOption]!!
            robotEnergyAvailable.setText(robotEnergy.toString())
            Toast.makeText(this, s3, Toast.LENGTH_SHORT).show()
            return true
        } else {
            Toast.makeText(this, R.string.insufficient_resources, Toast.LENGTH_SHORT).show()
            return false
        }
    }
}