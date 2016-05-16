package geneticalgorithm.controller;

import geneticalgorithm.AlertBox;
import geneticalgorithm.ConfirmBox;
import geneticalgorithm.model.GeneticAlgorithmSolver;
import geneticalgorithm.model.Parent;
import geneticalgorithm.model.Thing;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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
    private TextField crossingPoints;
    @FXML
    private TextField iterNumber;
    @FXML
    private CheckBox staticCrossingPoints;
    @FXML
    private Button startButton;
    @FXML
    private Button generateDataButton;
    @FXML
    private ChoiceBox<String> mutationPoints;
    @FXML
    private ChoiceBox<String> parentsChoice;
    @FXML
    private MenuItem openItem;
    @FXML
    private MenuItem saveItem;
    @FXML
    private MenuItem exitItem;

    private GeneticAlgorithmSolver gas;
    private List<TableColumn<Parent, Integer>> columns;

    @FXML
    public void initialize(){
        mutationPoints.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        parentsChoice.setItems(FXCollections.observableArrayList("Панміксія", "Імбридинг", "Аутбридинг", "Селекція", "Метод рулетки"));
        generateDataButton.setOnAction(e -> generateTaskData());
        openItem.setOnAction(e -> openTask());
        saveItem.setOnAction(e -> saveTask());
        exitItem.setOnAction(e -> exitProgram());
        startButton.setOnAction(e-> System.out.println("test"));
    }
    private void openTask(){

    }
    private void saveTask(){
        if(gas != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Зберегти умову завдання");
            File file = fileChooser.showSaveDialog((Stage) startButton.getScene().getWindow());
            if (file != null) {
                try {
                    File newFile = new File(file.getPath() + ".txt");
                    if (newFile.createNewFile()) {
                        System.out.println("File is created!");
                    } else {
                        System.out.println("File already exists.");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error, can't create file!");
            }
        }else{
            AlertBox.display("Помилка", "Не можливо зберегти початкову умову задачі!\n" +
                    " Для коректного збереження необхідно заповнити всі поля, згенерувати\n або зчитати початкову популяцію і набір предметів.");
        }
    }
    private void exitProgram(){
        if(ConfirmBox.display("Завершення роботи", "Всі не збережені дані зникнуть по завершенню. Ви впевнені, що хочете завершити роботу з програмою?")){
            Platform.exit();
        }
    }
    private void generateTaskData(){
        gas = new GeneticAlgorithmSolver();
        parentsChoice.setValue(parentsChoice.getItems().get(0));
        initializeThingsTable(gas.getThings());
        initializeParentsTable(gas.getParents());
        maxWeight.setText(gas.getBackpackMaxWeight().toString());
        crossingPoints.setText(gas.getCrossingPoints() + "");
        mutationPoints.setValue(mutationPoints.getItems().get(gas.getMutationPoints().size()-1));
        iterNumber.setText(gas.getIterations() + "");
        staticCrossingPoints.setSelected(true);
    }

    private void initializeThingsTable(ObservableList<Thing> things){
        thingsTable.setItems(things);
        thingName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        thingUtility.setCellValueFactory(cellData -> cellData.getValue().utilityProperty().asObject());
        thingWeight.setCellValueFactory(cellData -> cellData.getValue().weightProperty().asObject());
    }

    private void initializeParentsTable(ObservableList<Parent> parents){
        parentsTable.setItems(gas.getParents());

        if(columns == null) {
            int size = gas.getThings().size();
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
