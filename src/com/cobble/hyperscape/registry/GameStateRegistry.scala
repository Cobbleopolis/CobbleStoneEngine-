package com.cobble.hyperscape.registry

import com.cobble.hyperscape.core.gamestate.GameState

object GameStateRegistry {
    private var states: Map[String, GameState] = Map()

    /**
     * Registers a new Game State
     * @param state The Game State to register
     * @param stateName The internal name of the Game State
     */
    def registerGameState(state: GameState, stateName: String): Unit = {
        states += (stateName -> state)
    }

    /**
     * Gets a Game State from the registry
     * @param stateName Internal name of the Game State
     * @return The Game State with the passed internal name
     */
    def getGameState(stateName: String): GameState = {
        states(stateName)
    }

}
