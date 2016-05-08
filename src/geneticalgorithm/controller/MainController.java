package geneticalgorithm.controller;

import geneticalgorithm.model.GeneticAlgorithmSolver;
import geneticalgorithm.model.Parent;
import geneticalgorithm.model.Thing;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class MainController {

    @FXML
    private TableView<Thing> thingsTable;
    @FXML
    private TableView<Parent> parentsTable;
    @FXML
    private TableColumn<Thing, String> thingName;
    @FXML
    private TableColumn<Thing, Integer> thingUtility;
    @FXML
    private TableColumn<Thing, Integer> thingWeight;
    @FXML
    private TextField maxWeight;
    @FXML
    private Button startButton;
    @FXML
    private Button generateDataButton;

    private GeneticAlgorithmSolver gas;
    private List<TableColumn<Parent, Integer>> columns;

    @FXML
    public void initialize(){
        generateDataButton.setOnAction(e -> generateData());
        startButton.setOnAction(e-> System.out.println("test"));
    }
    private void generateData(){
        gas = new GeneticAlgorithmSolver();
        thingsTable.setItems(gas.getThings());
        thingName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        thingUtility.setCellValueFactory(cellData -> cellData.getValue().utilityProperty().asObject());
        thingWeight.setCellValueFactory(cellData -> cellData.getValue().weightProperty().asObject());
        initializeParentsTable(gas.getParents());
        maxWeight.setText(gas.getBackpackMaxWeight().toString());
    }
    private void initializeParentsTable(ObservableList<Parent> parents){
        int size = gas.getThings().size();
        parentsTable.setItems(gas.getParents());
        if(columns == null) {
            TableColumn column;
            columns = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                column = new TableColumn<Parent, Integer>(gas.getThings().get(i).getName());
                columns.add(column);
                parentsTable.getColumns().add(column);
            }
            column = new TableColumn<Parent, Integer>("Вага");
            columns.add(column);
            parentsTable.getColumns().add(column);
            column = new TableColumn<Parent, Integer>("Корисність");
            columns.add(column);
            parentsTable.getColumns().add(column);
            for(int i = 0; i < size; i++){
                final int row = i;
                columns.get(i).setCellValueFactory(cellData -> new SimpleObjectProperty<Integer>(cellData.getValue().getChromosome().get(row)));
            }
            columns.get(size).setCellValueFactory(cellData -> cellData.getValue().weightProperty().asObject());
            columns.get(size+1).setCellValueFactory(cellData -> cellData.getValue().utilityProperty().asObject());
        }
    }
}
