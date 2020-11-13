package co.edu.uniquindio.architecture.controller

import co.edu.uniquindio.architecture.exceptions.DirectionNullException
import co.edu.uniquindio.architecture.model.ALUSimulator
import co.edu.uniquindio.architecture.view.DirectionObservable
import co.edu.uniquindio.architecture.view.FlagObservable
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*


class RootViewController:Initializable {

    @FXML lateinit var directionsTableView:TableView<DirectionObservable>
    @FXML lateinit var directionTableColumn:TableColumn<DirectionObservable, String>
    @FXML lateinit var valueTableColumn:TableColumn<DirectionObservable, String>
    @FXML lateinit var flagsTableView:TableView<FlagObservable>
    @FXML lateinit var flagTableColumn:TableColumn<FlagObservable,String>
    @FXML lateinit var stateTableColumn:TableColumn<FlagObservable,String>
    @FXML lateinit var comboBox:ComboBox<String>
    @FXML lateinit var direction1Field:TextField
    @FXML lateinit var direction2Field:TextField
    @FXML lateinit var othersTableView:TableView<DirectionObservable>
    @FXML lateinit var typeTableColumn:TableColumn<DirectionObservable, String>
    @FXML lateinit var directionCarryTableColumn:TableColumn<DirectionObservable, String>
    @FXML lateinit var valueCarryTableColumn:TableColumn<DirectionObservable, String>

    private val aluSimulator:ALUSimulator = ALUSimulator()

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        directionTableColumn.cellValueFactory = PropertyValueFactory<DirectionObservable,String>("direction")
        valueTableColumn.cellValueFactory = PropertyValueFactory<DirectionObservable,String>("value")

        flagTableColumn.cellValueFactory = PropertyValueFactory<FlagObservable,String>("name")
        stateTableColumn.cellValueFactory = PropertyValueFactory<FlagObservable,String>("state")

        typeTableColumn.cellValueFactory = PropertyValueFactory<DirectionObservable, String>("type")
        directionCarryTableColumn.cellValueFactory = PropertyValueFactory<DirectionObservable, String>("direction")
        valueCarryTableColumn.cellValueFactory = PropertyValueFactory<DirectionObservable, String>("value")


        fillDirections()
        fillFlags()
        fillCombo()
        fillCarryTableView()
    }
    fun fillCarryTableView(){
        othersTableView.items.clear()
        val carry = aluSimulator.getCarry()
        val lastUsed = aluSimulator.getLastUsed()
        othersTableView.items.add(DirectionObservable(carry.first, Integer.toBinaryString(carry.second.toInt()),"Carry"))
        othersTableView.items.add(DirectionObservable(lastUsed.first, Integer.toBinaryString(lastUsed.second.toInt()),"Usado"))
        othersTableView.refresh()
    }
    fun fillCombo(){
        comboBox.items.add("ADD")
        comboBox.items.add("INC")
        comboBox.items.add("DEC")
        comboBox.items.add("AND")
        comboBox.items.add("OR")
        comboBox.items.add("NOT")
    }
    fun fillDirections(){
        directionsTableView.items.clear()
        val directions:Hashtable<String, Byte> = aluSimulator.getDirections()
        val keys = directions.keys()
        while(keys.hasMoreElements()){
            var key = keys.nextElement()
            directionsTableView.items.add(DirectionObservable(key,Integer.toBinaryString(directions[key]!!.toInt()), ""))
        }
        directionsTableView.refresh()
    }
    fun fillFlags(){
        flagsTableView.items.clear()
        val flags = aluSimulator.getFlags()
        val keys = flags.keys()
        while(keys.hasMoreElements()){
            var key = keys.nextElement()
            if(flags[key]==false){
                flagsTableView.items.add(FlagObservable(key,"0"))
            }else{
                flagsTableView.items.add(FlagObservable(key,"1"))
            }
        }
        flagsTableView.refresh()
    }

    @FXML
    fun handleOperateButton(e:ActionEvent){
        val dir1 = direction1Field.text
        val dir2 = direction2Field.text
        val selection = comboBox.selectionModel.selectedItem
        try {
            when (selection) {
                null -> (showAlert("Debes seleccionar una operación!", "ADVERTENCIA", Alert.AlertType.WARNING))
                "AND" -> (if (!dir1.isNullOrEmpty() && !dir2.isNullOrEmpty()) {
                    aluSimulator.and(dir1, dir2)
                } else {
                    showAlert("Debes ingresar las dos direcciones para la operación AND", "ADVERTENCIA", Alert.AlertType.WARNING)
                })
                "OR" -> (if (!dir1.isNullOrEmpty() && !dir2.isNullOrEmpty()) {
                    aluSimulator.or(dir1, dir2)
                } else {
                    showAlert("Debes ingresar las dos direcciones para la operación OR", "ADVERTENCIA", Alert.AlertType.WARNING)
                })
                "ADD" -> (if (!dir1.isNullOrEmpty() && !dir2.isNullOrEmpty()) {
                    aluSimulator.add(dir1, dir2)
                } else {
                    showAlert("Debes ingresar las dos direcciones para la operación ADD", "ADVERTENCIA", Alert.AlertType.WARNING)
                })
                "INC" -> (if (!dir1.isNullOrEmpty()) {
                    aluSimulator.increment(dir1)
                } else if (!dir2.isNullOrEmpty()) {
                    aluSimulator.increment(dir2)
                } else {
                    showAlert("Debes ingresar alguna dirección para incrementar", "ADVERTENCIA", Alert.AlertType.WARNING)
                })
                "DEC" -> (if (!dir1.isNullOrEmpty()) {
                    aluSimulator.decrement(dir1)
                } else if (!dir2.isNullOrEmpty()) {
                    aluSimulator.decrement(dir2)
                } else {
                    showAlert("Debes ingresar alguna dirección para decrementar", "ADVERTENCIA", Alert.AlertType.WARNING)
                })
                "NOT" -> (if (!dir1.isNullOrEmpty()) {
                    aluSimulator.not(dir1)
                } else if (!dir2.isNullOrEmpty()) {
                    aluSimulator.not(dir2)
                } else {
                    showAlert("Debes ingresar alguna dirección para negar", "ADVERTENCIA", Alert.AlertType.WARNING)
                })
            }
            fillDirections()
            fillFlags()
            fillCarryTableView()
        }catch (e:DirectionNullException){
            showAlert(e.message.toString(),"ERROR",Alert.AlertType.ERROR)
        }

    }
    @FXML
    fun showFirstStack(e:ActionEvent){
        showAlert(aluSimulator.getFirstStack(),"PRIMER STACK",Alert.AlertType.INFORMATION)
    }
    fun showAlert(message:String, title:String, type:Alert.AlertType){
        val alert = Alert(type)
        alert.contentText = message
        alert.title = title
        alert.headerText = ""
        alert.showAndWait()
    }

}