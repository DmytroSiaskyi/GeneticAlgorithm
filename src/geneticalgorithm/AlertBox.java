package geneticalgorithm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class AlertBox {
    public static void display(String title, String message){
        Stage window = new Stage();

        Image icon = new Image("/geneticalgorithm/resources/ico/app.png");
        window.getIcons().add(icon);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMaxWidth(700);
        Label label = new Label();
        label.setText(message);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}


