package com.udacity.shoestore.screens.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.DetailFragmentBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.screens.shoelisting.ShoeListModel
import timber.log.Timber

class DetailFragment : Fragment() {

    private lateinit var viewModel: ShoeListModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: DetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(ShoeListModel::class.java)

        binding.lifecycleOwner = this
        binding.shoeListModel = viewModel
        binding.shoe = Shoe()

        binding.cancelBtn.setOnClickListener {

            // Go back to Shoelist view without saving
            activity?.onBackPressed()
        }

        viewModel.eventSaved.observe(viewLifecycleOwner, Observer { clicked ->
            if (clicked){
                // Go to Shoelist view
                activity?.onBackPressed()
                viewModel.eventSavedComplete()
            }
        })

        viewModel.eventErrorToast.observe(viewLifecycleOwner, Observer { error ->
            if (error){
                // Show toast to fill all fields
                Toast.makeText(context, getString(R.string.plse_fill_in_all_fields),
                        Toast.LENGTH_SHORT).show()

                viewModel.onToastComplete()
            }

        })

        return binding.root

    }





}