package com.udacity.shoestore.screens.instruction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.InstructionsFragmentBinding
import com.udacity.shoestore.screens.welcome.WelcomeFragmentDirections


class InstructionsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        val binding: InstructionsFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.instructions_fragment, container, false)


        binding.shoeListBtn.setOnClickListener {
            // Navigate to Shoe List view
            findNavController().navigate(InstructionsFragmentDirections.actionInstructionsFragmentToShoeListFragment())
        }

        return binding.root
    }

}