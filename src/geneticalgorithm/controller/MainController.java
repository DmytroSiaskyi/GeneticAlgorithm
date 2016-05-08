package geneticalgorithm.controller;

import geneticalgorithm.model.GeneticAlgorithmSolver;
import geneticalgorithm.model.Thing;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class MainController {

    @FXML
    private TableView<Thing> thingsTable;
    @FXML
    private TableColumn<Thing, String> thingName;
    @FXML
    private TableColumn<Thing, Integer> thingUtility;
    @FXML
    private TableColumn<Thing, Integer> thingWeight;

    @FXML
    private Button startButton;

    @FXML
    public void initialize(){
        GeneticAlgorithmSolver gas = new GeneticAlgorithmSolver();
        thingsTable.setItems(gas.getThings());
        thingName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        thingUtility.setCellValueFactory(cellData -> cellData.getValue().utilityProperty().asObject());
        thingWeight.setCellValueFactory(cellData -> cellData.getValue().weightProperty().asObject());
        startButton.setOnAction(e-> {
            System.out.println("test");
        });
    }
}
