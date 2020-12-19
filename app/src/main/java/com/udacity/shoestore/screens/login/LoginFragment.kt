package com.udacity.shoestore.screens.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import androidx.databinding.DataBindingUtil
import com.udacity.shoestore.databinding.LoginFragmentBinding
import com.udacity.shoestore.screens.shoelisting.ShoeListModel
import timber.log.Timber
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val binding: LoginFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)

        var email = ""
        var password = ""
        var loginBool : Boolean?

        val loginSP = activity?.getSharedPreferences(getString(R.string.login_pref), Context.MODE_PRIVATE)
        loginBool = loginSP?.getBoolean(getString(R.string.login_bool), false)

        if (loginBool == true){
            // Navigate to Welcome view
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
        }


        binding.emailET.validate(getString(R.string.valid_email)) { s -> s.isValidEmail() }

        binding.emailET.afterTextChanged {
            email = it
        }

        binding.passwordET.afterTextChanged {
            password = it
            binding.passwordET.error = if (it.length >=6 ) null else getString(R.string.plse_enter_password)
        }

        binding.loginBtn.setOnClickListener {

            // Email and password validation
            if (email.isValidEmail() && password.isValidPassword()){

                // Check if user has registered before
                val emailSP = activity?.getSharedPreferences(getString(R.string.email_pref), Context.MODE_PRIVATE)
                val savedEmail = emailSP?.getString(getString(R.string.saved_email), "")

                val passWdSP = activity?.getSharedPreferences(getString(R.string.password_pref), Context.MODE_PRIVATE)
                val savedPw = passWdSP?.getString(getString(R.string.saved_password), "")

                if (email.equals(savedEmail) && password.equals(savedPw)) {

                    val loginSP = activity?.getSharedPreferences(getString(R.string.login_pref), Context.MODE_PRIVATE)
                    var loginEdit = loginSP?.edit()
                    loginEdit?.putBoolean(getString(R.string.login_bool), true)
                    loginEdit?.commit()

                    // Navigate to Welcome view
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())

                } else {
                    Toast.makeText(context, getString(R.string.not_registered),
                            Toast.LENGTH_SHORT).show()
                }

            }  else {
                Toast.makeText(context, getString(R.string.plse_enter_email),
                        Toast.LENGTH_SHORT).show()
            }

        }

        binding.createBtn.setOnClickListener {

            // Email and password validation
            if (email.isValidEmail() && password.isValidPassword()){

                // Save email and password
                val emailSP = activity?.getSharedPreferences(getString(R.string.email_pref), Context.MODE_PRIVATE)
                var emailEdit = emailSP?.edit()
                emailEdit?.putString(getString(R.string.saved_email), email)
                emailEdit?.commit()

                val passwordSP = activity?.getSharedPreferences(getString(R.string.password_pref), Context.MODE_PRIVATE)
                var passwordEdit = passwordSP?.edit()
                passwordEdit?.putString(getString(R.string.saved_password), password)
                passwordEdit?.commit()

                val loginSP = activity?.getSharedPreferences(getString(R.string.login_pref), Context.MODE_PRIVATE)
                var loginEdit = loginSP?.edit()
                loginEdit?.putBoolean(getString(R.string.login_bool), true)
                loginEdit?.commit()


            // Navigate to Welcome view
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())

            }  else {
                Toast.makeText(context, getString(R.string.plse_enter_email),
                        Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }


    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }

    fun EditText.validate(message: String, validator: (String) -> Boolean) {
        this.afterTextChanged {
            this.error = if (validator(it)) null else message
        }
        this.error = if (validator(this.text.toString())) null else message
    }


    fun String.isValidEmail(): Boolean
            = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun String.isValidPassword(): Boolean
        = this.isNotEmpty() && this.length > 6


}

