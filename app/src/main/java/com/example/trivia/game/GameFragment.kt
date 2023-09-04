package com.example.trivia.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.trivia.R
import com.example.trivia.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]


        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer {
            question -> binding.questionText.text = question.text
         })


        viewModel.answers.observe(viewLifecycleOwner, Observer {
            answers ->   binding.firstAnswer.text = answers[0]
            binding.secondAnswer.text = answers[1]
            binding.thirdAnswer.text = answers[2]
            binding.fourthAnswer.text = answers[3]
        })


        binding.submitButton.setOnClickListener {
            view:View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId

            if(checkedId != -1){
                var answerIndex = 0
                when(checkedId){
                    R.id.second_answer -> answerIndex = 1
                    R.id.third_answer -> answerIndex = 2
                    R.id.fourth_answer -> answerIndex = 3
                }

                if(viewModel.answers.value?.get(answerIndex) == viewModel.currentQuestion.value?.answers?.get(0)){
                    viewModel.moveToNextQuestion()

                    if(viewModel.gameOver()){
                        view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(viewModel.numQuestions, viewModel.questionIndex))

                    }

                } else {
                    view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
                }
            }
        }







        return binding.root
    }


}
