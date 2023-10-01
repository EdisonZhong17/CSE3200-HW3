package com.example.robothomework

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val EXTRA_ROBOT_ENERGY = "com.example.robothomework.robot_energy"
const val EXTRA_ROBOTPURCHASE_MADE = "com.example.robothomework.item_purchased"

class RobotPurchase : AppCompatActivity() {
    private lateinit var rewardButtonA : Button
    private lateinit var rewardButtonB : Button
    private lateinit var rewardButtonC : Button
    private lateinit var robotEnergyAvailable : TextView
    private var robotEnergy = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_purchase)

        rewardButtonA = findViewById(R.id.buttonRewardA)
        rewardButtonB = findViewById(R.id.buttonRewardB)
        rewardButtonC = findViewById(R.id.buttonRewardC)
        robotEnergyAvailable = findViewById(R.id.robotEnergyToSpend)

        // robotEnergy = 2
        robotEnergy = intent.getIntExtra(EXTRA_ROBOT_ENERGY, 0)
        robotEnergyAvailable.setText(robotEnergy.toString())

        rewardButtonA.setOnClickListener { view: View ->
            makePurchase(1)
        }

        rewardButtonB.setOnClickListener { view: View ->
            makePurchase(2)
        }

        rewardButtonC.setOnClickListener { view: View ->
            makePurchase(3)
        }
    }

    private fun setWhichItemPurchased(robotPurchaseMade : Int) {
        val data = Intent().apply {
            putExtra(EXTRA_ROBOTPURCHASE_MADE, robotPurchaseMade)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext : Context, robot_energy : Int) : Intent {
            return Intent(packageContext, RobotPurchase::class.java).apply {
                putExtra(EXTRA_ROBOT_ENERGY, robot_energy)
            }
        }
    }

    private fun makePurchase(costOfPurchase : Int) {
        //val rewards = listOf(R.string.reward_a, R.string.reward_b, R.string.reward_c)
        if (robotEnergy >= costOfPurchase) {
            val s1 = when {
                costOfPurchase == 1 -> getString(R.string.reward_a)
                costOfPurchase == 2 -> getString(R.string.reward_b)
                costOfPurchase == 3 -> getString(R.string.reward_c)
                else -> getString(R.string.error_reward)
            }
            val s2 = getString(R.string.purchased)
            val s3 = s1 + " " + s2
            robotEnergy -= costOfPurchase
            robotEnergyAvailable.setText(robotEnergy.toString())
            Toast.makeText(this, s3, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.insufficient_resources, Toast.LENGTH_SHORT).show()
        }
    }
}