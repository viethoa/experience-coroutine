package com.example.coroutine

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.selectUnbiased
import kotlin.String

/**
 * When using selectUnbiased, it will unbiased select one of the coroutine to run.
 * Not equal to Racing (by theory) as run all of coroutines then wait
 * for first one which complete first
 */
class RacingDesignPattern {

    fun execute(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.launch {
            val batman = batman()
            val robin = robin()
            val result = selectUnbiased<String> {
                batman.onSend("Jocker") {
                    "Batman is done"
                }
                robin.onSend("Jocker") {
                    "Robin is done"
                }
            }
            println(result)
        }
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun CoroutineScope.batman() = actor<String> {
        for (c in channel) {
            println("Batman is dealing with $c")
            delay(1000)
        }
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun CoroutineScope.robin() = actor<String> {
        for (c in channel) {
            println("Robin is dealing with $c")
            delay(200)
        }
    }
}