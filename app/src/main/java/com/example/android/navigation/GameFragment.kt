/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.android.navigation.databinding.FragmentGameBinding
import kotlin.properties.Delegates

class GameFragment : Fragment() {

    data class Question(
            val img: Int,
            val answers: List<String>)



    private val questions: MutableList<Question> = mutableListOf(
            Question(R.drawable.canada,
                    answers = listOf("Canada", "USA", "China","Iran")),
            Question(R.drawable.china,
                    answers = listOf("China", "Canada", "Greenland", "Russia")),
            Question(R.drawable.france,
                    answers = listOf("France", "Netherlands", "UK", "Israel")),
            Question(R.drawable.jordan,
                    answers = listOf("Jordan", "Yemen", "Oman", "UAE")),
            Question(R.drawable.kuwait,
                    answers = listOf("Kuwait", "Qatar", "Iran", "Egypt")),
            Question(R.drawable.malaysia,
                    answers = listOf("Malaysia", "Mali", "Indonesia", "Pakistan")),
            Question(R.drawable.qatar,
                    answers = listOf("Qatar", "Liberia", "Yemen", "Kuwait")),
            Question(R.drawable.singapore,
                    answers = listOf("Singapore", "Malaysia", "India", "Australia")),
            Question(R.drawable.turkey,
                    answers = listOf("Turkey", "Greece", "Jordan", "Afghanistan")),
            Question(R.drawable.uae,
                    answers = listOf("UAE", "Egypt", "Kuwait", "Qatar"))
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions =  10
   // private val numQuestions = Math.min((questions.size + 1) / 2, 10)
    private lateinit var imgflag:ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)



        imgflag=binding.flagImage
        // Shuffles the questions and sets the question index to the first question.
        randomizeQuestions()

        // Bind this fragment class to the layout
        binding.game = this


        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_gameWonFragment)
                    }
                } else {
                   Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_gameOverFragment2)
                }
            }
        }
        return binding.root
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {


        currentQuestion = questions[questionIndex]
        val imgID=currentQuestion.img
        when (imgID)
        {
            R.drawable.canada-> imgflag.setImageResource(R.drawable.canada)
            R.drawable.china-> imgflag.setImageResource(R.drawable.china)
            R.drawable.france-> imgflag.setImageResource(R.drawable.france)
            R.drawable.jordan-> imgflag.setImageResource(R.drawable.jordan)
            R.drawable.kuwait-> imgflag.setImageResource(R.drawable.kuwait)
            R.drawable.malaysia-> imgflag.setImageResource(R.drawable.malaysia)
            R.drawable.qatar-> imgflag.setImageResource(R.drawable.qatar)
            R.drawable.singapore-> imgflag.setImageResource(R.drawable.singapore)
            R.drawable.turkey-> imgflag.setImageResource(R.drawable.turkey)
            R.drawable.uae-> imgflag.setImageResource(R.drawable.uae)
        }

        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
}
