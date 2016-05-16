package geneticalgorithm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Так");
        yesButton.setMinWidth(75);
        Button noButton = new Button("Ні");
        noButton.setMinWidth(75);

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e->{
            answer = false;
            window.close();
        });
        VBox layout = new VBox(10);
        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(yesButton, noButton);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(label, buttons);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }
}
