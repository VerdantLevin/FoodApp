package com.example.foody

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foody.databinding.FragmentProfileBinding
import com.example.foody.databinding.FragmentRecipesBinding
import com.example.foody.ui.fragments.recipes.RecipesFragmentArgs
import com.example.foody.viewmodels.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.checkbox.MaterialCheckBox.CheckedState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class ProfileFragment : Fragment() {
    private var dataRequested = false
    private val args by navArgs<RecipesFragmentArgs>()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    private lateinit var googleSignInClient:GoogleSignInClient
    private lateinit var auth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if(currentUser!= null){
            binding.textView3.text=currentUser.email

            binding.materialSwitch.visibility= View.VISIBLE
            binding.materialSwitch2.visibility =View.VISIBLE
        }else{
            binding.LoginButton.visibility= View.VISIBLE
            binding.signUpButton.visibility= View.VISIBLE
            binding.textView4.visibility= View.VISIBLE
            binding.imageView2.visibility= View.VISIBLE
            binding.ggButton.visibility= View.VISIBLE

            binding.view7.visibility= View.GONE
            binding.materialSwitch.visibility= View.GONE
            binding.materialSwitch2.visibility =View.GONE
            binding.textView11.visibility= View.GONE
            binding.textView12.visibility =View.GONE
            binding.textView5.visibility =View.GONE
            binding.textView3.visibility =View.GONE
            binding.textView6.visibility =View.GONE
            binding.textView8.visibility =View.GONE
            binding.textView7.visibility =View.GONE
            binding.textView9.visibility =View.GONE
            binding.textView10.visibility =View.GONE
            binding.imageView10.visibility =View.GONE
            binding.imageView11.visibility =View.GONE
            binding.imageView12.visibility =View.GONE
            binding.imageView13.visibility =View.GONE
            binding.imageView14.visibility =View.GONE
            binding.imageView16.visibility =View.GONE
            binding.imageView15.visibility =View.GONE
            binding.imageView9.visibility = View.GONE
            binding.imageView18.visibility =View.GONE
            binding.imageView9.visibility = View.GONE
            binding.imageView8.visibility = View.GONE
            binding.imageView4.visibility = View.GONE
            binding.imageView5.visibility = View.GONE
            binding.imageView6.visibility = View.GONE
        }
        binding.imageView15.setOnClickListener{
            signOut()

        }
        binding.textView5.setOnClickListener{
            binding.materialSwitch.isChecked=!binding.materialSwitch.isChecked
        }
        binding.imageView5.setOnClickListener{
            binding.materialSwitch.isChecked=!binding.materialSwitch.isChecked
        }
        binding.textView7.setOnClickListener{
            binding.materialSwitch2.isChecked=!binding.materialSwitch2.isChecked
        }
        binding.imageView8.setOnClickListener{
            binding.materialSwitch2.isChecked=!binding.materialSwitch2.isChecked
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity as Activity , gso)

        binding.ggButton.setOnClickListener {
            signInGoogle()
        }
        // Inflate the layout for this fragment
        binding.LoginButton.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment2_to_signInFragment)
        }
        binding.signUpButton.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment2_to_signUpFragment)
        }
        return binding.root
    }
    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
       launcher.launch(signInIntent)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }
    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(activity, task.exception.toString() , Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                findNavController().navigate(R.id.profileFragment2)
            }else{
                Toast.makeText(activity, it.exception.toString() , Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun signOut(){
        auth!!.signOut()
        googleSignInClient.revokeAccess().addOnCompleteListener(activity as Activity) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Revoked Access")
        }
        findNavController().navigate(R.id.profileFragment2)
    }
    private fun onBoardingIsFinished():Boolean{
        val sharedPreferences = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("",false)
    }
}