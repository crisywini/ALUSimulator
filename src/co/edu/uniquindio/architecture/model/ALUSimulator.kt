package co.edu.uniquindio.architecture.model

import java.util.*
import kotlin.experimental.and
import kotlin.random.Random

class ALUSimulator {

    private lateinit var directions:Hashtable<String, Byte>
    private lateinit var carry:Pair<String, Byte>
    private lateinit var lastUsed:Pair<String, Byte>
    private lateinit var flags:Hashtable<String, Boolean>

    init{
        directions = Hashtable()
        flags = Hashtable()
        carry = Pair("00",0)
        lastUsed = Pair("01",0)
        //initDirections()
        initDirectionsTest()
        initFlags()
    }
    private fun initFlags(){
        flags.put("CF", false)
        flags.put("ZF", false)
        flags.put("SF", false)
    }
    private fun initDirectionsTest(){
        directions.put("5h", 0)
        directions.put("0h", 12)
        directions.put("6h", 61)
        directions.put("1h", 114)
        directions.put("2h", 84)
        directions.put("4h", Byte.MAX_VALUE)
    }
    private fun initDirections(){
        for(i in 0..6){
            directions.put((i.toString()+"h"), Random.nextInt(0,Byte.MAX_VALUE.toInt()).toByte())
        }
    }
    fun getDirections():Hashtable<String, Byte>{
        return directions
    }
    fun getFlags():Hashtable<String,Boolean>{
        return flags
    }
    fun getCarry():Pair<String, Byte>{
        return carry
    }
    fun getLastUsed():Pair<String, Byte>{
        return lastUsed
    }

    /**
     * This method allows to add to values in one direction of the memory stack
     * @param direction1 direction one
     * @param direction2 direction two
     */
    fun add(direction1:String, direction2:String){
        var v1:Byte? = directions[direction1]
        var v2:Byte? = directions[direction2]
        var result = v1.toString().toInt()+ v2.toString().toInt()
        if(result>Byte.MAX_VALUE){
            var high:String
            var low:String = "".plus(Integer.toBinaryString(result)).reversed()
            high = low.substring(7, low.length).reversed()
            low = low.substring(0, 7).reversed()
            //println(high+" <-high low-> "+low)
            flags["CF"] = true
            if(low.toLong(2).toByte()==0.toByte()){
                flags["ZF"] = true
            }
            directions.replace(direction1, low.toLong(2).toByte())
            carry = Pair(direction1, high.toLong(2).toByte())
        }else if(result==0){
            flags["ZF"] = true
            directions.replace(direction1, result.toByte())
        }else{
            directions.replace(direction1, result.toByte())
        }
        lastUsed = Pair(direction1, result.toByte())
    }

    /**
     * This method allows to increment a value in one
     * @param direction where the value is going to increment
     */
    fun increment( direction:String ){
        var value = directions[direction]
        var result = value.toString().toInt()+1
        if(result>Byte.MAX_VALUE.toInt()){

            var high:String
            var low:String = "".plus(Integer.toBinaryString(result)).reversed()
            high = low.substring(7, low.length).reversed()
            low = low.substring(0, 7).reversed()
            //println(high+" <-high low-> "+low)
            flags["CF"] = true
            if(low.toLong(2).toByte()==0.toByte()){
                flags["ZF"] = true
            }
            directions.replace(direction, low.toLong(2).toByte())
            carry = Pair(direction, high.toLong(2).toByte())
        }else{
            directions.replace(direction, result.toByte())
        }
        lastUsed = Pair(direction, result.toByte())
    }

}