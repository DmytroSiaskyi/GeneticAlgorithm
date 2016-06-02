package geneticalgorithm.controller;

import geneticalgorithm.AlertBox;
import geneticalgorithm.model.GASolver;
import geneticalgorithm.model.TableResultObject;
import geneticalgorithm.model.Task;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class ExperimentController {

    @FXML
    private Button startExp;
    @FXML
    private ChoiceBox<String> operator1;
    @FXML
    private ChoiceBox<String> operator2;
    @FXML
    private ChoiceBox<String> operator3;
    @FXML
    private TextField points1;
    @FXML
    private TextField points2;
    @FXML
    private TextField points3;
    @FXML
    private TextField iterations;

    private Task task1;
    private Task task2;
    private Task task3;
    private GASolver gaSolver;

    /**
     * Initialize controller and stage
     */
    @FXML
    public void initialize(){
        operator1.setItems(FXCollections.observableArrayList("Панміксія", "Імбридинг", "Аутбридинг"));
        operator2.setItems(FXCollections.observableArrayList("Панміксія", "Імбридинг", "Аутбридинг"));
        operator3.setItems(FXCollections.observableArrayList("Панміксія", "Імбридинг", "Аутбридинг"));
        startExp.setOnAction(e -> experiment());
    }

    private void experiment(){
        gaSolver = GASolver.getInstance();
        List<Integer> answers1 = new ArrayList<>();
        List<Integer> answers2 = new ArrayList<>();
        List<Integer> answers3 = new ArrayList<>();
        List<Integer> taskSize = new ArrayList<>();
        Task mainTask;
        int taskNumber = 100;
        Random random = new Random();
        for(int i = 0; i < taskNumber; i++) {
            taskSize.add(random.nextInt(20) + 10);
            mainTask = new Task(taskSize.get(i));
            task1 = mainTask.copyTask();
            task2 = mainTask.copyTask();
            task3 = mainTask.copyTask();
            int iter = Integer.parseInt(iterations.getText());
            task1.setIterations(iter);
            task2.setIterations(iter);
            task3.setIterations(iter);
            task1.setParentsChoiceMethod(operator1.getValue());
            task1.setCrossingPoints(Integer.parseInt(points1.getText()));
            task1.generateCrossingPoints(task1.getCrossingPoints(), task1.getThings().size());
            task2.setParentsChoiceMethod(operator2.getValue());
            task2.setCrossingPoints(Integer.parseInt(points2.getText()));
            task2.generateCrossingPoints(task2.getCrossingPoints(), task2.getThings().size());
            task3.setParentsChoiceMethod(operator3.getValue());
            task3.setCrossingPoints(Integer.parseInt(points3.getText()));
            task3.generateCrossingPoints(task3.getCrossingPoints(), task3.getThings().size());
            gaSolver.setTask(task1);
            gaSolver.setParentsChoice(task1.getParentsChoiceMethod());
            answers1.add(gaSolver.solveForExperiment());
            gaSolver.setTask(task2);
            gaSolver.setParentsChoice(task2.getParentsChoiceMethod());
            answers2.add(gaSolver.solveForExperiment());
            gaSolver.setTask(task3);
            gaSolver.setParentsChoice(task3.getParentsChoiceMethod());
            answers3.add(gaSolver.solveForExperiment());
        }

        int best, best1, best2, best3;
        best = 0;
        best1 = answers1.get(answers1.size()-1);
        best2 = answers2.get(answers2.size()-1);
        best3 = answers3.get(answers3.size()-1);
        if(best1 > best){
            best = best1;
        }
        if(best2 > best){
            best = best2;
        }
        if(best3 > best){
            best = best3;
        }
        float difference1, difference2, difference3;
        difference1 = ((float)(best - best1)/(float)best) * 100;
        difference2 = ((float)(best - best2)/(float)best) * 100;
        difference3 = ((float)(best - best3)/(float)best) * 100;

        try {
            AnchorPane scene = FXMLLoader.load(getClass().getResource("/geneticalgorithm/view/experimentResultView.fxml"));
            Stage resultStage = new Stage();
            resultStage.setTitle("Результат експерименту");
            resultStage.setScene(new Scene(scene, 600, 390));
            resultStage.setResizable(false);
            resultStage.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image("/geneticalgorithm/resources/ico/app.png");
            resultStage.getIcons().add(icon);

            Label z1 = (Label) resultStage.getScene().lookup("#z1");
            z1.setText(z1.getText() + " " + best1);
            Label z2 = (Label) resultStage.getScene().lookup("#z2");
            z2.setText(z2.getText() + " " + best2);
            Label z3 = (Label) resultStage.getScene().lookup("#z3");
            z3.setText(z3.getText() + " " + best3);

            Label deviation1 = (Label) resultStage.getScene().lookup("#deviation1");
            deviation1.setText(deviation1.getText() + " " + difference1 + "%");
            Label deviation2 = (Label) resultStage.getScene().lookup("#deviation2");
            deviation2.setText(deviation2.getText() + " " + difference2 + "%");
            Label deviation3 = (Label) resultStage.getScene().lookup("#deviation3");
            deviation3.setText(deviation3.getText() + " " + difference3 + "%");
            //TableView with all tasks results
            ObservableList<TableResultObject> resultList = FXCollections.observableArrayList();
            for(int i = 0 ; i < answers1.size(); i++){
                resultList.add(new TableResultObject(taskSize.get(i), answers1.get(i), answers2.get(i), answers3.get(i)));
            }
            TableView<TableResultObject> experimentTable = (TableView<TableResultObject>) resultStage.getScene().lookup("#resultExperiment");
            experimentTable.setItems(resultList);
            TableColumn<TableResultObject, Integer> column = new TableColumn<TableResultObject, Integer>("Розмірність");
            column.setMinWidth(140);
            experimentTable.getColumns().add(column);
            column.setCellValueFactory(cellData -> cellData.getValue().taskSizeProperty().asObject());
            column = new TableColumn<TableResultObject, Integer>("z1");
            column.setMinWidth(140);
            experimentTable.getColumns().add(column);
            column.setCellValueFactory(cellData -> cellData.getValue().z1Property().asObject());
            column = new TableColumn<TableResultObject, Integer>("z2");
            column.setMinWidth(140);
            experimentTable.getColumns().add(column);
            column.setCellValueFactory(cellData -> cellData.getValue().z2Property().asObject());
            column = new TableColumn<TableResultObject, Integer>("z3");
            column.setMinWidth(140);
            experimentTable.getColumns().add(column);
            column.setCellValueFactory(cellData -> cellData.getValue().z3Property().asObject());

            resultStage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Validate experiment data
     */
    private String validate(){
        String validationResult = "success";
        int p1, p2, p3, iter;

        if(operator1.getValue() == null){
            validationResult = "Визначте оператор вибору батьків для першого алгоритму.";
            return validationResult;
        }
        if(points1.getText().isEmpty()){
            validationResult = "Оберть кількість точок кросинговеру для першого алгоритму.";
            return validationResult;
        }else{
            try{
                p1 = Integer.parseInt(points1.getText());
                if(p1 < 1 || p1 > 5){
                    validationResult = "Не вірна кількість точок кросинговеру для першого алгоритму.";
                    return validationResult;
                }
            }catch (NumberFormatException e){
                validationResult = "Не вірний формат для кількості точок кросинговеру.";
                return validationResult;
            }
        }
        if(operator2.getValue() == null){
            validationResult = "Визначте оператор вибору батьків для другого алгоритму.";
            return validationResult;
        }
        if(points2.getText() == null){
            validationResult = "Оберть кількість точок кросинговеру для другого алгоритму.";
            return validationResult;
        }else{
            try{
                p2 = Integer.parseInt(points2.getText());
                if(p2 < 1 || p2 > 5){
                    validationResult = "Не вірна кількість точок кросинговеру для другого алгоритму.";
                    return validationResult;
                }
            }catch (NumberFormatException e){
                validationResult = "Не вірний формат для кількості точок кросинговеру.";
                return validationResult;
            }
        }
        if(operator3.getValue() == null){
            validationResult = "Визначте оператор вибору батьків для третього алгоритму.";
            return validationResult;
        }
        if(points3.getText() == null){
            validationResult = "Оберть кількість точок кросинговеру для третього алгоритму.";
            return validationResult;
        }else{
            try{
                p3 = Integer.parseInt(points3.getText());
                if(p3 < 1 || p3 > 5){
                    validationResult = "Не вірна кількість точок кросинговеру для третього алгоритму.";
                    return validationResult;
                }
            }catch (NumberFormatException e){
                validationResult = "Не вірний формат для кількості точок кросинговеру.";
                return validationResult;
            }
        }
        try{
            iter = Integer.parseInt(iterations.getText());
            if(iter < 1){
                validationResult = "Не вірна кількість ітерацій.";
                return validationResult;
            }
        }catch (NumberFormatException e){
            validationResult = "Не вірний формат для кількості ітерацій.";
            return validationResult;
        }

        return validationResult;
    }
}
