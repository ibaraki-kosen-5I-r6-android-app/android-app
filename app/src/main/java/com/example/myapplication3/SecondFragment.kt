package com.example.myapplication3

import android.content.res.Resources
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication3.databinding.FragmentSecondBinding
import java.util.Timer
import kotlin.concurrent.timerTask

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var subAct: SubAct
    private var isAppStarted = false
    private var timer = Timer()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var timeS: Int = 10
        var score: Int = 0
        var bStr = "Start"
        val iy =  (Resources.getSystem().displayMetrics.heightPixels/2.0 - 200).toFloat()
        subAct = SubAct(context)
        if(!isAppStarted){
            subAct.updateIsStarted(false)
            subAct.updateIsOver(false)
            isAppStarted = true
        }
        val cnt = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if(!subAct.isOver()) {
                    timeS--
                }
                binding.timeS.text = timeS.toString()
            }
            override fun onFinish() {
                subAct.updateIsOver (true)
                bStr = "restart"
                binding.floatingActionButton.show()

                binding.f2button.y = iy+150
//                binding.test.text = "${subAct.isStarted()} ${subAct.isOver()}"
                binding.f2button.text = bStr
            }
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.timeS.text = timeS.toString()
        binding.score.text = "$score score"
        binding.highScore.text = "high score ${subAct.highScore()}"
//        binding.test.text = "${subAct.isStarted()} ${subAct.isOver()}"
        binding.f2button.text = bStr
//        binding.f2button.y = iy


        binding.f2button.setOnClickListener {
            if(!subAct.isStarted()){
                binding.floatingActionButton.hide()
                binding.f2button.y = iy

                bStr="click"
                score = 1
                timeS = 10
//                x = (0..width).random()
//                y = (0..height).random()
                subAct.updateIsStarted(true)

                val pt = System.currentTimeMillis()
                timer.schedule(timerTask {
                    val nt = System.currentTimeMillis()
                    if (nt - pt <= 11000) {
                        cnt.start()
                    }
                }, 0)
            }
            else {
                if (!subAct.isOver()) {
                    score += 2
                    if (subAct.highScore() < score) {
                        subAct.updateHighScore(score)
                    }
//                    x = (0..width).random()
//                    y = (0..height).random()
                } else {
                    score = 0
                    timeS = 10
                    subAct.updateIsStarted(false)
                    subAct.updateIsOver(false)
                    bStr="Start"
                }
            }
            if (subAct.highScore() < score) {
                subAct.updateHighScore(score)
            }
            binding.score.text = "$score score"
            binding.highScore.text = "high score ${subAct.highScore()}"
//            binding.test.text = "${subAct.isStarted()} ${subAct.isOver()}"
            binding.f2button.text = bStr
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
