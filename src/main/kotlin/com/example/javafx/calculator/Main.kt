package com.example.javafx.calculator

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class Main : Application() {

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("/calculator.fxml"))
        primaryStage.title = "Calculator"
        primaryStage.scene = Scene(root, 300.0, 275.0)
        primaryStage.show()
    }

    companion object {

        @JvmStatic fun main(args: Array<String>) {
            Application.launch(Main::class.java, *args)
        }
    }
}
