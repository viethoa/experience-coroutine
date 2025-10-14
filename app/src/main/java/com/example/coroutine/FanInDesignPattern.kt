package com.example.coroutine

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class FanInDesignPattern {

    fun execute(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.launch {
            val workChannel = generateWork()
            val resultChannel = Channel<String>()
            List(10) {
                doWorkAsync(workChannel, resultChannel)
            }

            resultChannel.consumeEach {
                println(it) // Handle result synchronously here
            }
        }
    }

    private fun CoroutineScope.doWorkAsync(
        channel: ReceiveChannel<String>,
        resultChannel: Channel<String>
    ) = async(Dispatchers.Default) {
        for (p in channel) {
            resultChannel.send(p)
        }
    }

    fun CoroutineScope.generateWork() = produce {
        for (i in 1..100) {
            send("page$i")
        }
        close()
    }
}