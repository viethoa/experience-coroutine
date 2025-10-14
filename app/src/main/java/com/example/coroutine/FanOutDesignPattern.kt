package com.example.coroutine

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class FanOutDesignPattern {

    fun execute(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.launch {
            val workChannel = generateWork()
            List(10) { id ->
                doWork(id, workChannel)
            }
        }
    }

    private fun CoroutineScope.generateWork() = produce {
        for (i in 1..10_000) {
            send("page$i")
        }
        close()
    }

    private fun CoroutineScope.doWork(id: Int, channel: ReceiveChannel<String>) =
        launch(Dispatchers.Default) {
            for (p in channel) {
                println("Worker $id processed $p")
            }
        }
}