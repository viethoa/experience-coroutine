package com.example.coroutine

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

class SideKickDesignPattern {

    fun execute(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.launch {
            val robin = robin()
            val batman = batman()
            for (villain in listOf("Joker", "Bane", "Penguin", "Riddler", "Killer")) {
                val result = select<Pair<String, String>> {
                    robin.onSend(villain) {
                        "Robin" to villain
                    }
                    batman.onSend(villain) {
                        "Batman" to villain
                    }
                }
                println(result)
            }
        }
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun CoroutineScope.batman() = actor<String> {
        for (c in channel) {
            //println("Batman is dealing with $c")
            delay(200)
        }
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun CoroutineScope.robin() = actor<String> {
        for (c in channel) {
            //println("Robin is dealing with $c")
            delay(200)
        }
    }
}