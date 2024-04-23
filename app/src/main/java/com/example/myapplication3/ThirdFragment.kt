package com.example.myapplication3

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.unit.dp
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.findNavController
import com.example.myapplication3.databinding.FragmentThirdBinding
import java.util.Timer
import kotlin.concurrent.timerTask

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
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
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var timeS: Int = 10
        var score: Int = 0
        var bStr = "Start"
        val width = Resources.getSystem().displayMetrics.widthPixels
        val height = Resources.getSystem().displayMetrics.heightPixels
        val f3bB = binding.f3button.background
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
                    binding.timeS.text = timeS.toString()
                }
            }
            override fun onFinish() {
                subAct.updateIsOver (true)
                bStr = "restart"
                binding.f3button.x = (width / 2).toFloat()
                binding.f3button.y = (height / 2).toFloat()
                binding.floatingActionButton.show()
//                binding.test.text = "${subAct.isStarted()} ${subAct.isOver()}"
                binding.f3button.text = bStr
            }
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }

        binding.timeS.text = timeS.toString()
        binding.score.text = "$score score"
        binding.highScore.text = "high score ${subAct.highScore1()}"
//        binding.test.text = "${binding.f3button.x} ${binding.f3button.y}"
        binding.f3button.text = bStr

        binding.f3button.setOnClickListener {
            if(!subAct.isStarted()){
                binding.floatingActionButton.hide()

                bStr="click"
                score = 1
                timeS = 10
//                binding.f3button.x = (0..width).random().toFloat()
//                binding.f3button.y = (0..height).random().toFloat()

                binding.f3button.x = (width - 300).toFloat()
                binding.f3button.y = (height - 200).toFloat()
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
                    binding.f3button.x = (0..(width - 300)).random().toFloat()
                    binding.f3button.y = (0..(height - 200)).random().toFloat()
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
            if (subAct.highScore1() < score) {
                subAct.updateHighScore1(score)
            }
            binding.score.text = "$score score"
            binding.highScore.text = "high score ${subAct.highScore1()}"
//            binding.test.text = "${width} ${height}"
            binding.f3button.text = bStr
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

