package com.cobble.hyperscape.registry

import com.cobble.hyperscape.render.{Model, ModelRule, OBJLoader}


object ModelRegistry {
    private var models: Map[String, (Model, ModelRule)] = Map()

    /**
     * Loads a model into the game
     * @param modelName Name of the model to store it with
     * @param pathToObj Path to the OBJ folder
     */
    def loadModel(modelName: String, pathToObj: String, modelRule: ModelRule = null): Unit = {
        models += (modelName -> (OBJLoader.loadFromOBJFile(pathToObj), modelRule))
    }

    /**
     * Retrieves a model from the model registry
     * @param modelName Name of the model to return
     * @return The model with the corresponding name
     */
    def getModel(modelName: String): Model = {
        val (model, _) = models.getOrElse(modelName, (null, null))
	    model.copy
    }

	def getModelWithRule(modelName: String): (Model, ModelRule) = {
		val (model, modelRule) = models.getOrElse(modelName, (null, null))
		(model.copy, modelRule)
	}

	def getModelRule(modelName: String): ModelRule = {
		val (_, modelRule) = models.getOrElse(modelName, (null, null))
		modelRule
	}

    //    val cube = OBJLoader.loadFromOBJFile("res/model/cube.obj")
    //    val model = OBJLoader.loadFromOBJFile("res/model/model.obj")
}
