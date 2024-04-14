package com.example.foody.ui.fragments.profile

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foody.R
import com.example.foody.databinding.FragmentConfirmSheetBinding
import com.example.foody.databinding.FragmentProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class ConfirmSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentConfirmSheetBinding
    private lateinit var confirmViewModel : ConfirmViewModel
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val activity = requireActivity()
        Log.d("PK","OKK")

        confirmViewModel = ViewModelProvider(activity).get(ConfirmViewModel::class.java)
        binding.filledButton.setOnClickListener{
            dismiss()
        }
        binding.outlinedButton.setOnClickListener{
            dismiss()
            signOut()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentConfirmSheetBinding.inflate(inflater,container,false)
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity as Activity , gso)
        // Inflate the layout for this fragment
        return binding.root
    }
    private fun signOut(){
        auth!!.signOut()
        googleSignInClient.revokeAccess().addOnCompleteListener(activity as Activity) {
            // Google Sign In failed, update UI appropriately
            Log.w(ContentValues.TAG, "Revoked Access")
        }
        findNavController().navigate(R.id.profileFragment2)
    }

}