package exercise;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        //BorderPane pane = new BorderPane();
        Scene scene = new Scene(gridPane);



        Label label = new Label("Title");
        label.setFont(Font.font(14));
        label.setTextFill(Paint.valueOf("white"));




        gridPane.setPrefSize(300, 400);

        TextArea area = new TextArea();
        area.setWrapText(true);
        area.setEditable(false);
        area.setPrefSize(300, 200);
        gridPane.add(area, 0, 0);

        TextField field = new TextField();
        field.setPromptText("Please enter some word: ");
        gridPane.add(field, 0, 1);


        field.setOnAction(event -> {
            area.appendText(field.getText() + "\n");
            field.setText("");
        });


        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
