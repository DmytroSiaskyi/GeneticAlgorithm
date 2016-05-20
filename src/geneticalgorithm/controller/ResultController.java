package geneticalgorithm.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class ResultController {

    @FXML
    private Button closeSolutionButton;
    /**
     * Initialize controller and stage
     */
    @FXML
    public void initialize(){
        closeSolutionButton.setOnAction(event -> {
            Stage stage = (Stage) closeSolutionButton.getScene().getWindow();
            stage.close();
        });
    }
}
