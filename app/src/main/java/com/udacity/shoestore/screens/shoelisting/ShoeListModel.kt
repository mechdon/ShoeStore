package com.udacity.shoestore.screens.shoelisting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.utils.Utils
import timber.log.Timber

class ShoeListModel : ViewModel() {

    private val _shoes = MutableLiveData<MutableList<Shoe>>()
    private val shoeList = ArrayList<Shoe>()
    val shoes: LiveData<MutableList<Shoe>> get() = _shoes

    private val _eventErrorToast = MutableLiveData<Boolean>()
    val eventErrorToast: LiveData<Boolean> get() = _eventErrorToast

    init {
        _shoes.value = ArrayList()
    }

    private val _eventSaved = MutableLiveData<Boolean>()
    val eventSaved: LiveData<Boolean> get() = _eventSaved

    fun onToastComplete() {
        _eventErrorToast.value = false
    }

    fun eventSavedComplete() {
        _eventSaved.value = false
    }

    fun onSaveClicked(shoe: Shoe?) {
        if (shoe !=null && Utils.areShoeFieldsFilled(shoe)){
            shoeList.add(shoe)
            _shoes.value = shoeList
            _eventSaved.value = true
        } else {
            _eventErrorToast.value = true
        }

    }

}