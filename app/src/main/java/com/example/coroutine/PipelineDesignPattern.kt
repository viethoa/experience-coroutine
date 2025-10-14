package com.example.coroutine

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch

/**
 * Similar as a pipeline
 * - First coroutine will run first
 * - Second coroutine will run after first coroutine complete
 * - Same for third coroutine
 * => They run synchronously
 */
@OptIn(ExperimentalCoroutinesApi::class)
class PipelineDesignPattern {

    fun execute(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.launch {
            val pagesProducer = producePages()                            // Run first
            val docProducer = produceDoc(pagesProducer)           // Run second
            val titleProducer = produceTitles(docProducer)  // Run third
            titleProducer.consumeEach {
                println(it)                                               // => Synchronously
            }
        }
    }

    private fun CoroutineScope.producePages() = produce {
        for (p in listOf("Cool stuff", "Even more stuff")) {
            println("Produce Page sent")
            send(p)
        }
        close()
    }

    private fun CoroutineScope.produceDoc(pages: ReceiveChannel<String>) = produce {
        for (p in pages) {
            println("Produce Doc sent")
            send(Document(p))
        }
        close()
    }

    private fun CoroutineScope.produceTitles(parsedPages: ReceiveChannel<Document>) = produce {
        for (page in parsedPages) {
            println("Produce Title sent")
            send(page.getElementsByTagName("h1"))
        }
        close()
    }
}

data class Document(val page: String) {
    fun getElementsByTagName(tag: String): String {
        return page
    }
}