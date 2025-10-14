package com.example.coroutine

/**
 * Iterator design pattern
 */
class DemoIterator {
    fun execute() {
        val platoon = Squad(
            Trooper(1),
            Squad(
                Trooper(2),
            ),
            Trooper(3),
            Squad(
                Trooper(4),
                Trooper(5),
            ),
            Trooper(6)
        )

        // Result: 1, 0, 3, 0, 6
        for (trooper in platoon) {
            trooper.print()
        }
    }
}

open class Trooper(private val value: Int) {
    fun print() {
        println("Trooper $value")
    }
}

class Squad(private val units: List<Trooper>) : Trooper(0) {
    constructor(vararg units: Trooper) : this(units.toList())

    operator fun iterator() = object : Iterator<Trooper> {
        private var i = 0
        override fun hasNext(): Boolean = i < units.size
        override fun next(): Trooper = units[i++]
    }
}