package com.mynoteapp.common

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    object Loading : Resource()
}
