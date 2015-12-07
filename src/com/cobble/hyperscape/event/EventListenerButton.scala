package com.cobble.hyperscape.event

trait EventListenerButton {

    /**
     * Called whenever the button event happens in the pattern similar to holding down a key while typing.
     * eg: Event called, pauses, and then consecutive events.
     * @param button The character of the button being pressed.
     * @param charVal The LWJGL value of the button being pressed.
     */
    def onButtonTypingHold(button: Char, charVal: Int): Unit = {

    }

    /**
     * Called whenever the button event happens in the pattern similar to holding down a key while moving around in a game.
     * eg: Consecutive events are called until the button is released.
     * @param button The character of the button being pressed.
     * @param charVal The LWJGL value of the button being pressed.
     */
    def onButtonHold(button: Char, charVal: Int): Unit = {

    }

    /**
     * Called whenever a button is pressed.
     * eg: One event is called when the button is pressed and not called again.
     * @param button The character of the button being pressed.
     * @param charVal The LWJGL value of the button being pressed.
     */
    def onButtonDown(button: Char, charVal: Int): Unit = {

    }

    /**
     * Called whenever a button is released.
     * eg: One event is called when the button is released and not called again.
     * @param button The character of the button being pressed.
     * @param charVal The ASCII value of the button being pressed.
     */
    def onButtonUp(button: Char, charVal: Int): Unit = {

    }

    /**
     * Called every tick
     */
    def onTick(): Unit = {

    }
}
