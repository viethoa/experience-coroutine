package com.example.coroutine

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

/**
 * Conclusion
 *
 * For [flow]
 * - Flow will be completed automatically after run all line of codes of the [flow]
 *
 * For [callbackFlow]
 * - Flow won't be completed automatically, we need to complete it manually by calling channel.close()
 */
class TestFlowCompletion {

    suspend fun execute() {
        myCallbackFlow()
            .collect {
                println(it)
            }

        println("code block after flow")
    }

    private fun myFlow() = flow {
        delay(100)
        emit("flow emitted & completed")
    }

    private fun myCallbackFlow() = callbackFlow {
        delay(100)
        send("flow emitted")
        // channel.close() need to close Flow manually
        awaitClose()
    }
}