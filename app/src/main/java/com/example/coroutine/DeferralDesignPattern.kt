package com.example.coroutine

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * As it named Deferral, it will wait for result
 * - Can handle completion
 * - Or exception
 */
class DeferralDesignPattern {

    fun execute(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.launch {
            val value = valueAsync()
            runCatching {
                println(value.await())
                println("Deferral is done")
            }
                .onFailure {
                    println("Deferral is failed")
                }
        }
    }

    suspend fun valueAsync(): Deferred<String> = coroutineScope {
        val deferred = CompletableDeferred<String>()
        launch {
            delay(1000)
            if (Random.nextBoolean()) {
                deferred.complete("OK")
            } else {
                deferred.completeExceptionally(
                    RuntimeException()
                )
            }
        }
        deferred
    }
}