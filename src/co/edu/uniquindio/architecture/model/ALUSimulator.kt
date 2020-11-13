package co.edu.uniquindio.architecture.model

import co.edu.uniquindio.architecture.exceptions.DirectionNullException
import java.util.*
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.random.Random

class ALUSimulator {

    private var directions:Hashtable<String, Byte> = Hashtable()
    private var carry:Pair<String, Byte>
    private var lastUsed:Pair<String, Byte>
    private var flags:Hashtable<String, Boolean> = Hashtable()
    private var firstStack:Hashtable<String, Byte> = Hashtable()

    init{
        carry = Pair("00",0)
        lastUsed = Pair("01",0)
        //initDirections()
        initDirectionsTest()
        initFlags()
        firstStack = directions?.clone() as Hashtable<String, Byte>
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
            directions[(i.toString()+"h")] = Random.nextInt(0,Byte.MAX_VALUE.toInt()).toByte()
        }
    }

    fun getFirstStack():String{

        var result = ""
        val keys = firstStack.keys()
        while(keys.hasMoreElements()){
            val key = keys.nextElement()
            result += "Dirección: $key valor: ${Integer.toBinaryString(firstStack[key].toString().toInt())}\n"
        }
        return result
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
    @Throws(DirectionNullException::class)
    fun add(direction1:String, direction2:String){
        if(directions[direction1] != null && directions[direction2] != null) {
            val v1: Byte? = directions[direction1]
            val v2: Byte? = directions[direction2]
            val result = v1.toString().toInt() + v2.toString().toInt()
            if (result > Byte.MAX_VALUE) {
                var high: String
                var low: String = "".plus(Integer.toBinaryString(result)).reversed()
                high = low.substring(7, low.length).reversed()
                low = low.substring(0, 7).reversed()
                //println(high+" <-high low-> "+low)
                flags["CF"] = true
                if (low.toLong(2).toByte() == 0.toByte()) {
                    flags["ZF"] = true
                }
                directions.replace(direction1, low.toLong(2).toByte())
                carry = Pair(direction1, high.toLong(2).toByte())
            } else if (result == 0) {
                flags["ZF"] = true
                directions.replace(direction1, result.toByte())
            } else {
                directions.replace(direction1, result.toByte())
            }
            lastUsed = Pair(direction1, result.toByte())
        }else{
            throw DirectionNullException("Alguna de las direcciones no es valida")
        }
    }

    /**
     * This method allows to increment a value in one
     * @param direction where the value is going to increment
     */
    @Throws(DirectionNullException::class)
    fun increment( direction:String ){
        if(directions[direction] !=null) {
            val value = directions[direction]
            val result = value.toString().toInt() + 1
            if (result > Byte.MAX_VALUE.toInt()) {

                var high: String
                var low: String = "".plus(Integer.toBinaryString(result)).reversed()
                high = low.substring(7, low.length).reversed()
                low = low.substring(0, 7).reversed()
                //println(high+" <-high low-> "+low)
                flags["CF"] = true
                if (low.toLong(2).toByte() == 0.toByte()) {
                    flags["ZF"] = true
                }
                directions.replace(direction, low.toLong(2).toByte())
                carry = Pair(direction, high.toLong(2).toByte())
            } else {
                directions.replace(direction, result.toByte())
            }
            lastUsed = Pair(direction, directions[direction]!!)
        }else{
            throw DirectionNullException("La dirección: $direction no es válida")
        }
    }
    /**
     * This method allows to decrement a value in one
     * @param direction where the value is going to decrement
     */
    @Throws(DirectionNullException::class)
    fun decrement( direction: String ){
        if(directions[direction] != null) {
            val value = directions[direction]
            val result = value.toString().toInt() - 1
            when {
                result.toString().toByte() == 0.toByte() -> {
                    flags["ZF"] = true
                    directions[direction] = result.toByte()
                }
                result.toString().toByte() == (-1).toByte() -> {
                    flags["SF"] = true
                    directions[direction] = Byte.MAX_VALUE
                }
                else -> {
                    directions[direction] = result.toByte()
                }
            }
            lastUsed = Pair(direction, directions[direction]!!)
        }else{
            throw DirectionNullException("La dirección: $direction no es válida")
        }
    }
    /**
     * This method allows to do the and operations and its saves in the first direction
     * @param direction1 where the value is going to be saved
     * @param direction2 needed to the and operation
     */
    @Throws(DirectionNullException::class)
    fun and(direction1: String, direction2: String){
        if(directions[direction1] != null && directions[direction2] != null) {
            val value1 = directions[direction1]
            val value2 = directions[direction2]!!
            val result = value1!! and value2
            directions[direction1] = result
            lastUsed = Pair(direction1, directions[direction1]!!)
        }else{
            throw DirectionNullException("Alguna de las direcciones no es válida")
        }
    }
    /**
     * This method allows to do the or operations and its saves in the first direction
     * @param direction1 where the value is going to be saved
     * @param direction2 needed to the or operation
     */
    @Throws(DirectionNullException::class)
    fun or(direction1: String, direction2: String){
        if(directions[direction1] != null && directions[direction2] != null) {
            val value1 = directions[direction1]
            val value2 = directions[direction2]!!
            val result = value1!! or value2
            directions[direction1] = result
            lastUsed = Pair(direction1, directions[direction1]!!)
        }else{
            throw DirectionNullException("Alguna de las direcciones no es válida")
        }
    }

    /**
     * This method allows to do the not operations and its saves in the direction
     * @param direction where the value is going to be saved
     */
    @Throws(DirectionNullException::class)
    fun not(direction:String){

        if(directions[direction] != null) {
            val direction1 = directions[direction]
            val str = direction1.toString()
            var result = ""
            for (i in str) {
                if (i == '0') {
                    result += "1"
                } else {
                    result += "0"
                }
            }
            directions[direction] = result.toByte()
            lastUsed = Pair(direction, directions[direction]!!)
        }else{
            throw DirectionNullException("La dirección: $direction no es válida")
        }
    }
}