package com.udacity.shoestore.utils

import com.udacity.shoestore.models.Shoe

class Utils {
    companion object {
        fun areShoeFieldsFilled(shoe: Shoe): Boolean {
            return shoe.name.isNotEmpty() && shoe.size.isNotEmpty() && shoe.company.isNotEmpty() && shoe.description.isNotEmpty()
        }
    }

}