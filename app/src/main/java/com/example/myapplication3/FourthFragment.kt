package com.example.myapplication3

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.myapplication3.databinding.FragmentFourthBinding
import java.util.Timer
import kotlin.concurrent.timerTask

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FourthFragment : Fragment() {

    private var _binding: FragmentFourthBinding? = null
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
        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var timeS: Int = 10
        var score: Int = 0
        var bStr = "Start"
        var bStr1 = "0"
        var bStr2 = "0"
        subAct = SubAct(context)
        if(!isAppStarted){
            subAct.updateIsStarted(false)
            subAct.updateIsOver(false)
            isAppStarted = true
        }
        val cnt = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeS--
                binding.timeS.text = timeS.toString()
            }
            override fun onFinish() {
                bStr = "restart"
                binding.floatingActionButton.show()
                binding.f4button.visibility = View.VISIBLE
                binding.f4button1.visibility = View.GONE
                binding.f4button2.visibility = View.GONE
            }
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_FourthFragment_to_FirstFragment)
        }

        binding.timeS.text = timeS.toString()
        binding.score.text = "$score score"
        binding.highScore.text = "high score ${subAct.highScore2()}"
        binding.f4button.text = bStr

        binding.f4button.setOnClickListener {
            binding.floatingActionButton.hide()
            binding.f4button.visibility = View.GONE
            binding.f4button1.visibility = View.VISIBLE
            binding.f4button2.visibility = View.VISIBLE
            bStr1 = (1..100).random().toString()
            do{
                bStr2 = (1..100).random().toString()
            }while(bStr1==bStr2)

            binding.f4button1.text = bStr1
            binding.f4button2.text = bStr2

            bStr="click"
            score = 1
            timeS = 10
            val pt = System.currentTimeMillis()
            timer.schedule(timerTask {
                val nt = System.currentTimeMillis()
                if (nt - pt <= 11000) {
                    cnt.start()
                }
            }, 0)
            binding.score.text = "$score score"
            binding.highScore.text = "high score ${subAct.highScore2()}"
        }

        binding.f4button1.setOnClickListener {
            if(bStr1.toInt()>bStr2.toInt()){
                score+=2

            }
            else{
                score-=3
            }
            if(subAct.highScore2()<score){
                subAct.updateHighScore2(score)
            }
            binding.score.text = "$score score"
            binding.highScore.text = "high score ${subAct.highScore2()}"

            bStr1 = (1..100).random().toString()
            do{
                bStr2 = (1..100).random().toString()
            }while(bStr1==bStr2)
            binding.f4button1.text = bStr1
            binding.f4button2.text = bStr2
        }
        binding.f4button2.setOnClickListener {
            if(bStr2.toInt()>bStr1.toInt()){
                score+=2

            }
            else{
                score-=3
            }
            if(subAct.highScore2()<score){
                subAct.updateHighScore2(score)
            }
            binding.score.text = "$score score"
            binding.highScore.text = "high score ${subAct.highScore2()}"

            bStr1 = (1..100).random().toString()
            do{
                bStr2 = (1..100).random().toString()
            }while(bStr1==bStr2)
            binding.f4button1.text = bStr1
            binding.f4button2.text = bStr2
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
