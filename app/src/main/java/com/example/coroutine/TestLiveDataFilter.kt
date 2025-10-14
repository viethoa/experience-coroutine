package com.example.coroutine

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

/**
 * Filter is working right for this simple test
 *  - Only collect value != 2
 */
private inline fun <T> LiveData<T>.filter(crossinline predicate: (T) -> Boolean): LiveData<T> {
    return MediatorLiveData<T>().apply {
        addSource(this@filter) {
            if (predicate(it)) {
                this.value = it
            }
        }
    }
}

class TestLiveDataFilter {

    val liveData = MutableLiveData(1)

    fun execute(lifecycleOwner: LifecycleOwner) {
        liveData
            .filter { it != 2 }
            .observe(lifecycleOwner) {
                println("Test LiveData Filter > value: $it")
            }
    }
}