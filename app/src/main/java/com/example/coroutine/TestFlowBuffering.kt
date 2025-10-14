package com.example.coroutine

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TestFlowBuffering {
    private val shareFlow = MutableSharedFlow<String>()

    fun execute(lifecycleScope: LifecycleCoroutineScope) {
        testFlow(lifecycleScope)
    }

    /**
     * With buffering enabled, the flow emits values without waiting for the consumer to catch up.
     */
    fun testFlow(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.launch {
            myFlow()
                .buffer()
                .collect {
                    var total = 0F
                    for (i in 0..10000000000) {
                        total += i
                    }
                    println(it)
                }
        }
    }

    /**
     * For ShareFlow, it will wait till consumer done operation to emit next item
     */
    fun testShareFlow(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.launch {
            shareFlow
                .collect {
                    var total = 0F
                    for (i in 0..10000000000) {
                        total += i
                    }
                    println(it)
                }
        }

        lifecycleScope.launch {
            shareFlow.emit("1")
            println("Emit 1")
        }
        lifecycleScope.launch {
            shareFlow.emit("2")
            println("Emit 2")
        }
    }

    private fun myFlow(): Flow<String> = flow {
        emit("1")
        println("Emit 1")
        emit("2")
        println("Emit 2")
    }
}