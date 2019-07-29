package com.example.muzi.utils

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Queue(private val activity: FragmentActivity?)   {

    private val gson = Gson()
    val items = arrayListOf<String>()
    companion object{
        const val MAX =5
    }
    fun create(list: List<String>){
        items.clear()
        if (!list.isNullOrEmpty()) {
            items.addAll(list)
        }
    }

    fun enQueue(element: String){
        items.add(0,element)
        if(items.size > MAX){
            deQueue()
        }
        write()
    }
     private fun deQueue() {
        if (items.isNotEmpty()) {
            items.removeAt(items.size - 1)
        }
    }

    fun write(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("Duy",gson.toJson(items))
            apply()
        }
    }

    fun read(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val listType = object : TypeToken<List<String>>() { }.type
        create(gson.fromJson(sharedPref.getString("Duy",""),listType))
    }
}