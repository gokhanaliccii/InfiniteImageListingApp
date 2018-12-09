package com.gokhanaliccii.infiniteimagelisting.common.lifecycle

class LifeCycleBag {

    lateinit var stateAndListenersList: MutableMap<String, MutableList<() -> Unit>>

    fun lifeCycleEventTriggered(state: String) {
        stateAndListenersList[state]?.forEach { it.invoke() }
        stateAndListenersList.remove(state)
    }

    fun attachToLifeCycle(state: String, func: () -> Unit) {
        val mutableList = stateAndListenersList[state]
        if (mutableList != null) {
            mutableList.add(func)
        } else {
            val emptyList = mutableListOf<() -> Unit>()
            emptyList.add(func)
            stateAndListenersList.put(state, emptyList)
        }
    }
}