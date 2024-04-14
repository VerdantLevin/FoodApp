package com.example.foody.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.foody.R

/**
 * A simple [Fragment] subclass.
 * Use the [SplashScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashScreenFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if(onBoardingIsFinished()){
            findNavController().navigate(R.id.action_splashScreenFragment_to_recipesFragment)
        }else{
            findNavController().navigate(R.id.action_splashScreenFragment_to_viewPagerFragment)
        }

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    private fun onBoardingIsFinished():Boolean{
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding212", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("finisheda",false)

    }
}