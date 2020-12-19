package com.udacity.shoestore.screens.shoelisting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import timber.log.Timber

class ShoeListModel : ViewModel() {

    private val _shoes = MutableLiveData<MutableList<Shoe>>()
    private val shoeList = ArrayList<Shoe>()
    val shoes: LiveData<MutableList<Shoe>> get() = _shoes

    init {
        _shoes.value = ArrayList()
    }


    fun addShoeItem(shoe: Shoe?) {
        if (shoe != null){
            shoeList.add(shoe)
            _shoes.value = shoeList
        }
    }

}