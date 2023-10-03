package com.ikozlu.todoappsqlite.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ikozlu.todoappsqlite.R
import com.ikozlu.todoappsqlite.common.viewBinding
import com.ikozlu.todoappsqlite.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)

    private lateinit var sharedPref : SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //amaç: login yapıldıysa, uygulama kapanıp yeniden açıldığında login işlemleri istenmemesini sağlamak
        sharedPref = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE) //sharedPref i ayağa kaldırdık önce

        val islogin = sharedPref.getBoolean("isLogin",false)

        if (islogin){
            findNavController().navigate(R.id.loginToDailyNotes)
        }

        with(binding){
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (checkFields(email, password)){
                    sharedPref.edit().putBoolean("isLogin", true).apply()
                    findNavController().navigate(R.id.loginToDailyNotes)

                }

            }
        }
    }

    private fun checkFields(email :String, password :String): Boolean{
        return when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                binding.tilEmail.error = "E-mail is not valid!"
                false
            }
            password.isEmpty() -> {
                binding.tilEmail.isErrorEnabled = false
                binding.tilPassword.error = "Password is empty!"
                false
            }
            password.length < 6 -> {
                binding.tilEmail.isErrorEnabled = false
                binding.tilPassword.error = "Password length should be more than six character!"
                false
            }
            else -> true



        }
    }

}
