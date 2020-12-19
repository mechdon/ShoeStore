package com.udacity.shoestore.screens.shoelisting

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ShoeItemBinding
import com.udacity.shoestore.databinding.ShoelistFragmentBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.screens.instruction.InstructionsFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class ShoeListFragment : Fragment() {

    private lateinit var viewModel: ShoeListModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: ShoelistFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.shoelist_fragment, container, false)
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(requireActivity()).get(ShoeListModel::class.java)

        binding.lifecycleOwner = this
        binding.shoeViewModel = viewModel

        viewModel.shoes.observe(viewLifecycleOwner, Observer { shoes->

            binding.shoeListItem.removeAllViews()
            shoes.forEach{ shoe ->
                val shoeBinding: ShoeItemBinding =
                        DataBindingUtil.inflate(inflater, R.layout.shoe_item, container, false)
                shoeBinding.shoe = shoe
                shoeBinding.executePendingBindings()
                binding.shoeListItem.addView(shoeBinding.root)
            }

        })


        // Remove back arrow to prevent going back to the onboarding screens
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(null)

        binding.fab.setOnClickListener {
            // Navigate to Detail Screen
            findNavController().navigate(ShoeListFragmentDirections.actionShoeListFragmentToDetailFragment())
        }

        return  binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing - Prevent going back to the onboarding screens via back button
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        menu.findItem(R.id.logoutAction).isVisible = true
        super.onCreateOptionsMenu(menu, inflater)
    }

}