package com.example.trivia.game

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

   //The current question
    private val _currentQuestion = MutableLiveData<Question>()
    val currentQuestion:LiveData<Question>
        get()=_currentQuestion


    private val _answers = MutableLiveData<List<String>>()
    val answers:LiveData<List<String>>
        get()=_answers


    var questionIndex = 0



    private lateinit var questionsList:MutableList<Question>

    init
    {
        loadQuestions()
    }

    private fun loadQuestions(){
        questionsList  = mutableListOf(
        Question(
            text = "What is Android Jetpack?",
            answers = listOf("all of these", "tools", "documentation", "libraries")
        ),
        Question(
            text = "Base class for Layout?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")
        ),
        Question(
            text = "Layout for complex Screens?",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")
        ),
        Question(
            text = "Pushing structured data into a Layout?",
            answers = listOf("Data Binding", "Data Pushing", "Set Text", "OnClick")
        ),
        Question(
            text = "Inflate layout in fragments?",
            answers = listOf("onCreateView", "onViewCreated", "onCreateLayout", "onInflateLayout")
        ),
        Question(
            text = "Build system for Android?",
            answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")
        ),
        Question(
            text = "Android vector format?",
            answers = listOf(
                "VectorDrawable",
                "AndroidVectorDrawable",
                "DrawableVector",
                "AndroidVector"
            )
        ),
        Question(
            text = "Android Navigation Component?",
            answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")
        ),
        Question(
            text = "Registers app with launcher?",
            answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")
        ),
        Question(
            text = "Mark a layout for Data Binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")
        )
        )
        // Shuffle the questions in the list
        questionsList.shuffle()

        _currentQuestion.value = questionsList[0]
        _answers.value = questionsList[0].answers.shuffled()

    }

    var numQuestions = ((questionsList.size + 1) / 2).coerceAtMost(3)

    fun moveToNextQuestion(){

        if(questionIndex < numQuestions){
            questionIndex++
            _currentQuestion.value = questionsList[questionIndex]
            _answers.value = questionsList[questionIndex].answers.shuffled()
        }
    }

    fun gameOver():Boolean {
        return  (questionIndex >= numQuestions)
    }




}