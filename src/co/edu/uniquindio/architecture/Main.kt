package co.edu.uniquindio.architecture

import co.edu.uniquindio.architecture.controller.RootViewController
import co.edu.uniquindio.architecture.model.ALUSimulator
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import kotlin.experimental.and
import kotlin.random.Random

class Main:Application() {

    companion object{
        @JvmStatic
        fun main(args:Array<String>){
            launch(Main::class.java)
        }
    }
    override fun start(primaryStage: Stage?) {
        val loader = FXMLLoader(Main::class.java.getResource("/RootView.fxml"))
        val parent:Parent = loader.load()
        val controller:RootViewController= loader.getController()
        val scene = Scene(parent)
        primaryStage?.scene = scene
        primaryStage?.title = "Simulador ALU"
        primaryStage?.show()
    }
    fun test(){
        var bit:Byte = 2  // 0010
        var bit2:Byte = 3 // 0011
        var bitMax:Byte = 24
        println(bitMax)
        var result = bit and bit2 //0010

        println(Byte.MAX_VALUE)
        var aux = "".plus(bitMax)
        println(Integer.toBinaryString(Byte.MAX_VALUE.toInt()))

        println(Random.nextInt(0,3))
        println(Integer.toBinaryString(bit.plus(bit2)))
        var alu: ALUSimulator = ALUSimulator()
        println(alu.getDirections())
        var byte4:Byte = -1

        alu.add("5h", "0h")
        alu.increment("4h")
        alu.increment("0h")
        println(alu.getDirections())
        println(alu.getFlags())
        println(alu.getCarry())
    }
}