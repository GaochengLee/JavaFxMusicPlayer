package mainGUI;

import controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatRoomGUI extends Application {

    @FXML
    private TextArea TextArea_sendMessage;

    @FXML
    private TextArea TextArea_receiveMessage;

    @FXML
    private Button Button_sendButton;

    @FXML
    private MenuBar MenuBar_chatRoom;

    private static Socket connection = MainController.getSocket();
    private static PrintStream serverOut;
    private static BufferedReader serverIn;
    private static TextArea textAreaOut;
    private static TextArea textAreaIn;


    @FXML
    private void Action_send() {
        String temp = TextArea_sendMessage.getText();
        String name = connection.getInetAddress().getHostName() + ":" + connection.getPort();

        if (!temp.equals("")) {
            serverOut.println("# message " + name + " " + temp);
            textAreaOut.setText("");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatRoom.fxml"));
        BorderPane pane = loader.load();

        textAreaIn = (TextArea) pane.getCenter();

        HBox hBox = (HBox) pane.getBottom();
        textAreaOut = (TextArea) hBox.getChildren().get(0);

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.show();

        Platform.runLater(() -> {
            try {
                Thread thread = new Thread(new RemoteReader());
                serverIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                serverOut = new PrintStream(connection.getOutputStream());

                thread.start();
            } catch (IOException e) {
                System.out.println("Can not connect the server!");
                textAreaIn.appendText("\n[Error: cannot connect the server!]");
            }
        });
    }

    private class RemoteReader implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    String Msg;
                    if ((Msg = serverIn.readLine()) != null) {
                        textAreaIn.appendText(Msg + "\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
