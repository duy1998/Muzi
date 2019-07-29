package com.example.muzi.data

class Resource<T> private constructor ( val status :Int =1,
                                        val data : T? = null)
{
    companion object{
        const val LOADING = 1
        const val SUCCESSED = 2
        const val NETWORK_ERROR = 3
        fun <T> success(data :T) : Resource<T>{
            return Resource(SUCCESSED,data)
        }
        fun <T> load() : Resource<T>{
            return Resource(LOADING,null)
        }
        fun <T> error() : Resource<T>{
            return Resource(NETWORK_ERROR,null)
        }

    }
}