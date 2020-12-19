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
import com.udacity.shoestore.screens.shoelisting.ShoeListFragmentDirections
import com.udacity.shoestore.screens.shoelisting.ShoeListModel
import timber.log.Timber

class DetailFragment : Fragment() {

    private lateinit var viewModel: ShoeListModel
    var shoe: Shoe? = null

    var shoeName = ""
    var shoeSize = ""
    var shoeCompany = ""
    var shoeDesc = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: DetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(ShoeListModel::class.java)

        binding.lifecycleOwner = this
        binding.shoeListModel = viewModel

        // Get data when user enter details in the text fields

        binding.shoeNameET.afterTextChanged {
            shoeName = it
        }

        binding.shoeSizeET.afterTextChanged {
            shoeSize = it
        }

        binding.shoeComET.afterTextChanged {
            shoeCompany = it
        }

        binding.shoeDescET.afterTextChanged {
            shoeDesc = it
        }


        binding.cancelBtn.setOnClickListener {

            // Go back to Shoelist view without saving
            activity?.onBackPressed()
        }



        binding.saveBtn.setOnClickListener {

            // Check all fields are filled
            if (shoeName.isNotEmpty() && shoeSize.isNotEmpty() && shoeCompany.isNotEmpty() && shoeDesc.isNotEmpty()){

                shoe = Shoe(shoeName, shoeSize, shoeCompany, shoeDesc)
                viewModel.addShoeItem(shoe)

                // Go back to Shoelist view
                activity?.onBackPressed()

            } else {
                // Show toast to fill all fields
                Toast.makeText(context, getString(R.string.plse_fill_in_all_fields),
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


}