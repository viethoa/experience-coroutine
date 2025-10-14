package com.example.coroutine

/**
 * Use function instead of interface
 */
class CommandDesignPattern {
    private val moveGenerator = fun(trooper: Trooper): Command {
        return fun() {
            trooper.print()
        }
    }

    fun execute() {
        moveGenerator(Trooper(100)).invoke()
    }
}

typealias Command = () -> Unit

