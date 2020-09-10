package com.sd.app.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager



    fun Activity.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0);
    }
