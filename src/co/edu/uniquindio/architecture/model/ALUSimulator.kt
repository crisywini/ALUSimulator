package co.edu.uniquindio.architecture.model

import java.util.*
import kotlin.experimental.and
import kotlin.random.Random

class ALUSimulator {

    private lateinit var directions:Hashtable<String, Byte>
    private lateinit var flags:Hashtable<String, Boolean>

    init{
        directions = Hashtable()
        flags = Hashtable()
        initDirections()
        initFlags()
    }
    private fun initFlags(){
        flags.put("CF", false)
        flags.put("ZF", false)
        flags.put("SF", false)
    }
    private fun initDirections(){

        for(i in 0..6){
            directions.put((i.toString()+"h"), Random.nextInt(0,16).toByte())
        }
    }
    fun getDirections():Hashtable<String, Byte>{
        return directions
    }
    fun getFlags():Hashtable<String,Boolean>{
        return flags
    }
    fun add(direction1:String, direction2:String){
        var v1:Byte? = directions.get(direction1)
        var v2:Byte? = directions.get(direction2)
        if(v1.toString().toByte().plus(v2.toString().toByte()).toByte()==16.toByte()){
            //Activar bandera
            flags["CF"] = true

            flags["ZF"] = true
            directions.replace(direction1, 0)
        }
        if(v1.toString().toByte().plus(v2.toString().toByte()).toByte()>16.toByte()){
            var aux:String = Integer.toBinaryString(v1.toString().toByte().plus(v2.toString().toByte()))
            //11010
            for(i in aux.length-1 downTo aux.length-4){

            }
        }
    }
}