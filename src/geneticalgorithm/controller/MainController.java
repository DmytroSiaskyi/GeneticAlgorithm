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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

    /**
     * Initialize controller and stage
     */
    @FXML
    public void initialize(){
        mutationPoints.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        parentsChoice.setItems(FXCollections.observableArrayList("Панміксія", "Імбридинг", "Аутбридинг", "Селекція", "Метод рулетки"));
        generateDataButton.setOnAction(e -> generateTaskData());
        openItem.setOnAction(e -> openTask());
        saveItem.setOnAction(e -> saveTask());
        exitItem.setOnAction(e -> exitProgram());
        startButton.setOnAction(e-> solve());
    }

    /**
     * Solving backpack task with generated or readen task data
     */
    private void solve(){
        String test = updateTask();
        if(test.equals("success")){
            System.out.println("Start");
            //solving task
        }else{
            AlertBox.display("Помилка", test);
        }
    }

    /**
     * Update task data from stage
     */
    private String updateTask(){
        String result = "success";

        return result;
    }

    /**
     * Open task from .txt file
     *
     */
    private void openTask(){

    }

    /**
     * Save task data to .txt file
     */
    private void saveTask(){
        if(gas != null) {
            String test = updateTask();
            if(!test.equals("success")){
                AlertBox.display("Помилка", test);
                return;
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Зберегти умову завдання");
            File file = fileChooser.showSaveDialog((Stage) startButton.getScene().getWindow());
            String buffer = new String();
            if (file != null) {
                try {
                    File newFile = new File(file.getPath() + ".txt");
                    newFile.createNewFile();
                    FileWriter fw = new FileWriter(newFile.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);

                    buffer = "Максимальна вага рюкзака: " + gas.getBackpackMaxWeight();
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Оператор вибору батьків: " + gas.getParentsChoiceMethod();
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Точки кросинговеру: ";
                    List<Integer> crossingPoints = gas.getCrossingPointsList();
                    for(int i = 0; i < crossingPoints.size(); i++){
                        buffer += crossingPoints.get(i) + " ";
                    }
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Постійність точок кросинговеру: " + gas.getStaticCrossingPoints();
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Точки мутації: ";
                    List<Integer> mutationPoints = gas.getMutationPoints();
                    for(int i = 0; i < mutationPoints.size(); i++){
                        buffer += mutationPoints.get(i) + " ";
                    }
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Інверсія при мутації: " + gas.getMutationInversion();
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Кількість ітерацій: " + gas.getIterations();
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Набір предметів(назва, корисність, вага): ";
                    bw.write(buffer);
                    bw.newLine();

                    List<Thing> things = gas.getThings();
                    for(int i = 0; i < things.size(); i++){
                        buffer = things.get(i).getName() + " " + things.get(i).getUtility() + " " + things.get(i).getWeight();
                        bw.write(buffer);
                        bw.newLine();
                    }
                    bw.write(buffer);
                    bw.newLine();

                    List<Parent> parents = gas.getParents();
                    buffer = "Набір предків(хромосома, вага, корисність): ";
                    bw.write(buffer);
                    bw.newLine();
                    for(int i = 0; i < parents.size(); i++){
                        Parent parent = parents.get(i);
                        buffer = new String();
                        for(int j = 0; j < things.size(); j ++){
                            buffer += parent.getChromosome().get(j) + " ";
                        }
                        buffer += parent.getWeight() + " " + parent.getUtility();
                        bw.write(buffer);
                        bw.newLine();
                    }

                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error, can't create file!");
            }
        }else{
            AlertBox.display("Помилка", "Не можливо зберегти початкову умову задачі!\n" +
                    "Для коректного збереження необхідно заповнити всі поля, згенерувати\nабо зчитати початкову популяцію і набір предметів.");
        }
    }

    /**
     * Close program with user confirm
     */
    private void exitProgram(){
        if(ConfirmBox.display("Завершення роботи", "Всі не збережені дані зникнуть по завершенню. Ви впевнені, що хочете завершити роботу з програмою?")){
            Platform.exit();
        }
    }

    /**
     * Generate task with task details
     */
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
    /**
     * Initialize things table data
     *
     * @param things
     */
    private void initializeThingsTable(ObservableList<Thing> things){
        thingsTable.setItems(things);
        thingName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        thingUtility.setCellValueFactory(cellData -> cellData.getValue().utilityProperty().asObject());
        thingWeight.setCellValueFactory(cellData -> cellData.getValue().weightProperty().asObject());
    }

    /**
     * Initialize or update parents table data
     *
     * @param parents
     */
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
