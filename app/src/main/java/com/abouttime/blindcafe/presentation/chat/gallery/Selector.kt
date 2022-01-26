package com.abouttime.blindcafe.presentation.chat.gallery

import java.util.*

class Selector<T>(
    val limit: Int
) {
    private val items: LinkedList<T> = LinkedList<T>()
    private val selectedIndex = mutableMapOf<Int, Int>()
    val size get() = items.size

    fun clickItem(item: T, position: Int): Boolean {
        if (selectedIndex.contains(position)) {
            unselectItem(item, position)
        } else {
            return selectItem(item, position)
        }
        return true
    }

    fun getNumberByIndex(position: Int): Int? = selectedIndex[position]

    fun isContain(position: Int): Boolean = selectedIndex.contains(position)

    fun getItems() = items

    private fun selectItem(item: T, position: Int): Boolean {
        if (isFull()) return false

        items.add(item)
        selectedIndex[position] = size
        return true
    }
    private fun unselectItem(item: T, position: Int) {
        updateAllByUnselect(position)
        items.remove(item)
        selectedIndex.remove(position)
    }
    private fun updateAllByUnselect(position: Int) {
        selectedIndex.keys.forEach { key ->
            if (selectedIndex[key]!! > selectedIndex[position]!!) {
                selectedIndex[key] = selectedIndex[key]!! - 1
            }
        }
    }

    private fun isFull(): Boolean = size >= limit


}