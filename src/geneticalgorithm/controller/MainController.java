package geneticalgorithm.controller;

import geneticalgorithm.AlertBox;
import geneticalgorithm.ConfirmBox;
import geneticalgorithm.model.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class MainController {

    //Table data controlls
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

    //Properties controlls
    @FXML
    private TextField maxWeight;
    @FXML
    private TextField crossingPoints;
    @FXML
    private TextField iterNumber;
    @FXML
    private CheckBox staticCrossingPoints;
    @FXML
    private CheckBox mutationInversion;
    @FXML
    private Button startButton;
    @FXML
    private Button generateDataButton;
    @FXML
    private ChoiceBox<String> mutationPoints;
    @FXML
    private ChoiceBox<String> parentsChoice;

    //MenuBar controlls
    @FXML
    private MenuItem openItem;
    @FXML
    private MenuItem saveItem;
    @FXML
    private MenuItem exitItem;
    @FXML
    private MenuItem aboutAlgorithmItem;
    @FXML
    private MenuItem aboutProjectItem;

    private List<TableColumn<Parent, Integer>> columns;

    private GASolver gaSolver;
    private TaskArchitector taskArchitector;


    /**
     * Initialize controller and stage
     */
    @FXML
    public void initialize(){
        taskArchitector = new TaskArchitector();
        taskArchitector.setTaskBuilder(new GenTaskBuilder());
        mutationPoints.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        parentsChoice.setItems(FXCollections.observableArrayList("Панміксія", "Імбридинг", "Аутбридинг", "Селекція", "Метод рулетки"));
        generateDataButton.setOnAction(e -> generateTaskData());
        openItem.setOnAction(e -> openTask());
        saveItem.setOnAction(e -> saveTask());
        exitItem.setOnAction(e -> exitProgram());
        aboutAlgorithmItem.setOnAction(e -> aboutAlgorithm());
        aboutProjectItem.setOnAction(e -> aboutProject());
        startButton.setOnAction(e-> solve());
    }

    /**
     * Display view with authors data
     */
    private void aboutProject(){
        try {
            AnchorPane scene = FXMLLoader.load(getClass().getResource("/geneticalgorithm/view/aboutAuthorView.fxml"));
            Stage resultStage = new Stage();
            resultStage.setTitle("Курсовий проект");
            resultStage.setScene(new Scene(scene, 700, 500));
            resultStage.setResizable(false);
            resultStage.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image("/geneticalgorithm/resources/ico/app.png");
            resultStage.getIcons().add(icon);
            resultStage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Displaying view with data about algorithm
     */
    private void aboutAlgorithm(){
        try {
            AnchorPane scene = FXMLLoader.load(getClass().getResource("/geneticalgorithm/view/aboutAlgorithmView.fxml"));
            Stage resultStage = new Stage();
            resultStage.setTitle("Генетичний алгоритм");
            resultStage.setScene(new Scene(scene, 700, 500));
            resultStage.setResizable(false);
            resultStage.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image("/geneticalgorithm/resources/ico/app.png");
            resultStage.getIcons().add(icon);
            resultStage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Solving backpack task with generated or readen task data
     */
    private void solve(){
        String test = updateTask();
        if(test.equals("success")){
            //solving task
            try {
                AnchorPane scene = FXMLLoader.load(getClass().getResource("/geneticalgorithm/view/resultView.fxml"));
                Stage resultStage = new Stage();
                resultStage.setTitle("Результат");
                resultStage.setScene(new Scene(scene, 700, 500));
                resultStage.setResizable(false);
                resultStage.initModality(Modality.APPLICATION_MODAL);
                Image icon = new Image("/geneticalgorithm/resources/ico/app.png");
                resultStage.getIcons().add(icon);
                resultStage.showAndWait();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            AlertBox.display("Помилка", test);
        }
    }

    /**
     * Update task data from stage
     */
    private String updateTask(){
        String result = "success";
        try {
            int newMaxWeight = Integer.parseInt(maxWeight.getText());
            String operator = parentsChoice.getValue();
            int crossPoints = Integer.parseInt(crossingPoints.getText());
            boolean staticCrossPoints = staticCrossingPoints.isSelected();
            int mutationPointsNumber = Integer.parseInt(mutationPoints.getValue());
            boolean inversions = mutationInversion.isSelected();
            int iterations = Integer.parseInt(iterNumber.getText());
            if(newMaxWeight < 1){
                result = "Обмеження максимальної ваги рюкзака має бути не менше 1.";
            }else{
                if(operator == null){
                    result = "Виберіть оператор вибору батьків для виконання даної операції. ";
                }else{
                    if(crossPoints < 1){
                        result = "Кількість точок кросинговеру повинна бути більше 1.";
                    }else{
                        if(mutationPointsNumber < 1){
                            result = "Кількість точок мутації повинна бути більше 1.";
                        }
                    }
                }
            }
            if(!result.equals("success")){
                return result;
            }else{
                if(iterations < 1){
                    result = "Кількість ітерацій повинна бути більше або рівна 1.";
                }else{
                    Task task = gaSolver.getTask();
                    task.setBackpackMaxWeight(newMaxWeight);
                    task.setCrossingPoints(crossPoints);
                    task.generateCrossingPoints(crossPoints, task.getParents().size());
                    task.setStaticCrossingPoints(staticCrossPoints);
                    task.setMutationInversion(inversions);
                    task.generateMutationPoints(mutationPointsNumber, task.getParents().size());
                    task.setIterations(iterations);
                    gaSolver.setTask(task);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            result = "Заповніть всі поля налаштування роботи алгоритму.";
        }
        return result;
    }

    /**
     * Open task from .txt file
     *
     */
    private void openTask(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Відкрити умову завдання");
        File file = fileChooser.showOpenDialog((Stage) startButton.getScene().getWindow());
        String[] splitedString;
        try {
            FileReader fr = new FileReader(file.getAbsolutePath());
            BufferedReader br = new BufferedReader(fr);

            int maxBackPackWeight;

            String buffer;
            buffer = br.readLine();
            splitedString = buffer.split(": ");
            maxBackPackWeight = Integer.parseInt(splitedString[1]);
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(file);
    }

    /**
     * Save task data to .txt file
     */
    private void saveTask(){
        Task task = gaSolver.getTask();
        if(task != null) {
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

                    buffer = "Максимальна вага рюкзака: " + task.getBackpackMaxWeight();
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Оператор вибору батьків: " + task.getParentsChoiceMethod();
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Точки кросинговеру: ";
                    List<Integer> crossingPoints = task.getCrossingPointsList();
                    for(int i = 0; i < crossingPoints.size(); i++){
                        buffer += crossingPoints.get(i) + " ";
                    }
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Постійність точок кросинговеру: " + task.getStaticCrossingPoints();
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Точки мутації: ";
                    List<Integer> mutationPoints = task.getMutationPoints();
                    for(int i = 0; i < mutationPoints.size(); i++){
                        buffer += mutationPoints.get(i) + " ";
                    }
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Інверсія при мутації: " + task.getMutationInversion();
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Кількість ітерацій: " + task.getIterations();
                    bw.write(buffer);
                    bw.newLine();

                    buffer = "Набір предметів(назва, корисність, вага): ";
                    bw.write(buffer);
                    bw.newLine();

                    List<Thing> things = task.getThings();
                    for(int i = 0; i < things.size(); i++){
                        buffer = things.get(i).getName() + " " + things.get(i).getUtility() + " " + things.get(i).getWeight();
                        bw.write(buffer);
                        bw.newLine();
                    }
                    bw.write(buffer);
                    bw.newLine();

                    List<Parent> parents = task.getParents();
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
        taskArchitector.constructTask(10);
        Task task = taskArchitector.getTask();
        gaSolver = GASolver.getInstance();
        gaSolver.setTask(task);
        parentsChoice.setValue(parentsChoice.getItems().get(0));
        initializeThingsTable(task.getThings());
        initializeParentsTable(task.getParents());
        maxWeight.setText(task.getBackpackMaxWeight().toString());
        crossingPoints.setText(task.getCrossingPointsList().size() + "");
        mutationPoints.setValue(mutationPoints.getItems().get(task.getMutationPoints().size()-1));
        iterNumber.setText(task.getIterations() + "");
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
        parentsTable.setItems(parents);
        Task task = GASolver.getInstance().getTask();
        if(columns == null) {
            int size = task.getThings().size();
            TableColumn column;
            columns = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                column = new TableColumn<Parent, Integer>(task.getThings().get(i).getName());
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
