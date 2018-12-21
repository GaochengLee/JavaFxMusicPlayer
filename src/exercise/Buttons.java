package exercise;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Buttons extends Application {

    private static final Color color = Color.web("#464646");
    Button button3 = new Button("Decline");
    DropShadow shadow = new DropShadow();
    Label label = new Label();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Button Sample");
        primaryStage.setWidth(300);
        primaryStage.setHeight(190);
        scene.getStylesheets().add("buttons.css");

        label.setFont(Font.font("Times New Roman", 22));
        label.setTextFill(color);

        VBox vBox = new VBox();
        vBox.setLayoutX(20);
        vBox.setLayoutY(20);
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();

        Button button1 = new Button("Accept");
        button1.setId("button1");
        button1.getStylesheets().add("button");
        button1.setOnAction(event -> label.setText("Accepted"));

        Button button2 = new Button("Accept");
        button2.setOnAction(event -> label.setText("Declined"));

        button3.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> button3.setEffect(shadow));

        button3.addEventHandler(MouseEvent.MOUSE_EXITED, event -> button3.setEffect(null));

        hBox1.getChildren().add(button1);
        hBox1.getChildren().add(button2);
        hBox1.getChildren().add(button3);
        hBox1.getChildren().add(label);
        hBox1.setSpacing(10);
        hBox1.setAlignment(Pos.BOTTOM_CENTER);

        ((Group)scene.getRoot()).getChildren().add(hBox1);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
