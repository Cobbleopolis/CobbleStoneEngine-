package com.cobble.hyperscape.registry

import com.cobble.hyperscape.event.{EventListenerButton, EventListenerMouse}

object EventRegistry {

    var mouseEventListener: Array[EventListenerMouse] = Array[EventListenerMouse]()

    var buttonEventListener: Array[EventListenerButton] = Array[EventListenerButton]()

    /**
     * Registers a mouse event listener
     * @param eventListenerMouse The mouse event listener to be registered
     */
    def registerMouseEventListener(eventListenerMouse: EventListenerMouse): Unit = {
        mouseEventListener = mouseEventListener :+ eventListenerMouse
    }

    /**
     * @return A list of all the registered mouse event listeners
     */
    def getMouseListeners: Array[EventListenerMouse] = mouseEventListener

    /**
     * Registers a button event listener
     * @param eventListenerButton The button event listener to be registered
     */
    def registerButtonEventListener(eventListenerButton: EventListenerButton): Unit = {
        buttonEventListener = buttonEventListener :+ eventListenerButton
    }

    /**
     * @return A list of all the registered button event listeners
     */
    def getButtonEventListeners: Array[EventListenerButton] = buttonEventListener

}
