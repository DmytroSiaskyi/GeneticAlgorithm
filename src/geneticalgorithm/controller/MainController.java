package geneticalgorithm.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class MainController {

    @FXML
    private Button startButton;

    @FXML
    public void initialize(){
        startButton.setOnAction(e-> {
            System.out.println("test");
        });
    }
}
