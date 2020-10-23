package co.edu.uniquindio.architecture.model

import javafx.application.Application
import javafx.stage.Stage
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.random.Random
import kotlin.random.nextInt

class Main:Application() {

    companion object{
        @JvmStatic
        fun main(args:Array<String>){
            launch(Main::class.java)
        }
    }

    override fun start(primaryStage: Stage?) {
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
        var alu:ALUSimulator = ALUSimulator()
        println(alu.getDirections())
        alu.add("5h", "1h")
        println(alu.getDirections())
        println(alu.getFlags())
        System.exit(0)
    }
}