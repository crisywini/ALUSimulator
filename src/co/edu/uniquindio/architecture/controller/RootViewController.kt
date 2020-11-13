package co.edu.uniquindio.architecture.controller

import co.edu.uniquindio.architecture.model.ALUSimulator
import co.edu.uniquindio.architecture.view.DirectionObservable
import co.edu.uniquindio.architecture.view.FlagObservable
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ComboBox
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
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

    private val aluSimulator:ALUSimulator = ALUSimulator()

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        directionTableColumn.cellValueFactory = PropertyValueFactory<DirectionObservable,String>("direction")
        valueTableColumn.cellValueFactory = PropertyValueFactory<DirectionObservable,String>("value")

        flagTableColumn.cellValueFactory = PropertyValueFactory<FlagObservable,String>("name")
        stateTableColumn.cellValueFactory = PropertyValueFactory<FlagObservable,String>("state")
        fillDirections()
        fillFlags()
        fillCombo()
    }
    fun fillCombo(){
        comboBox.items.add("Add")
        comboBox.items.add("Increment")
        comboBox.items.add("Decrement")
        comboBox.items.add("And")
        comboBox.items.add("Or")
        comboBox.items.add("Not")
    }
    fun fillDirections(){
        directionsTableView.items.clear()
        val directions = aluSimulator.getDirections()
        val keys = directions.keys()
        while(keys.hasMoreElements()){
            var key = keys.nextElement()
            directionsTableView.items.add(DirectionObservable(key,directions[key].toString()))
        }
        directionsTableView.refresh()
    }
    fun fillFlags(){
        flagsTableView.items.clear()
        val flags = aluSimulator.getFlags()
        val keys = flags.keys()
        while(keys.hasMoreElements()){
            var key = keys.nextElement()
            flagsTableView.items.add(FlagObservable(key,flags[key].toString()))
        }
        flagsTableView.refresh()
    }

    @FXML
    fun handleOperateButton(e:ActionEvent){
        if(!direction1Field.text.isNullOrEmpty()){
            if(!direction2Field.text.isNullOrEmpty()){
                val dir1 = direction1Field.text
                val dir2 = direction2Field.text
                aluSimulator.add(dir1,dir2)
                fillDirections()
                fillFlags()
            }
        }

    }

}