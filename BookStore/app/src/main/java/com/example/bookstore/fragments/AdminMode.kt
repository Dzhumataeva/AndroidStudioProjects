package com.example.bookstore.fragments

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bookstore.databinding.FragmentAdminModeBinding

class AdminMode : Fragment() {

     lateinit var binding: FragmentAdminModeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminModeBinding.inflate(inflater)


        binding.enter.setOnClickListener {
            if(!binding.adminPasssword.text.isEmpty()){
                if(binding.adminPasssword.text.toString() == "admin2003"){
                    val intent = Intent(activity, com.example.bookstore.activities.Admin_mode::class.java)
                    startActivity(intent)
                }
                else{
                    binding.adminPasssword.error = "Incorrect password!"
                }
            }
            else{
                binding.adminPasssword.error = "Empty password!"
            }
        }

        binding.button.setOnClickListener {
            if(binding.button.text.toString().equals("Show")){
                binding.adminPasssword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.button.text = "Hide"
            }
            else{
                binding.adminPasssword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.button.text = "Show"
            }
        }

        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() = AdminMode()
    }
}