package com.example.foody.ui.fragments.profile

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.foody.R
import com.example.foody.databinding.FragmentConfirmSheetBinding
import com.example.foody.databinding.FragmentLanguageSettingBinding
import com.example.foody.databinding.FragmentProfileBinding
import com.example.foody.databinding.FragmentSignInBinding
import java.util.Locale

class LanguageSettingFragment : Fragment() {
    private var _binding: FragmentLanguageSettingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val radioButtonCheck = AppCompatDelegate.getApplicationLocales()
        _binding = FragmentLanguageSettingBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.radioButton1.setOnClickListener{
            setLocal("en")
        }
        binding.radioButton2.setOnClickListener{
            setLocal("cn")
        }
        binding.radioButton3.setOnClickListener{
            setLocal("vn")
        }

        return binding.root
    }

    fun setLocal(langCode:String){
        val languageTag = langCode
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.create(Locale.forLanguageTag(languageTag))
        )
    }
}