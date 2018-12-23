/*
 * Copyright (c) 18-12-22 下午10:49.
 * @author 李高丞
 */

package mainGUI;

import controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatRoomGUI extends Application {
    /**
     * 文本输入区
     */
    @FXML
    private TextArea TextArea_sendMessage;
    /**
     * 文本显示区
     */
    @FXML
    private TextArea TextArea_receiveMessage;
    /**
     * 发送按钮
     */
    @FXML
    private Button Button_sendButton;
    /**
     * 客户端连接套接字
     */
    private static Socket connection;
    /**
     * 网络输出流
     */
    private static PrintStream serverOut;
    /**
     * 网络输入流
     */
    private static BufferedReader serverIn;
    /**
     * 文字输出区
     */
    private static TextArea textAreaOut;
    /**
     * 文字输入区
     */
    private static TextArea textAreaIn;

    /**
     * 单击发送按钮事件
     */
    @FXML
    private void Action_send() {
        // 获得发送信息
        String temp = TextArea_sendMessage.getText();
        // 获得发送者名字
        String name = connection.getInetAddress().getHostName() + ":" + connection.getPort();

        // 如果发送信息不为空，则发送
        if (!temp.equals("")) {
            serverOut.println("# message " + name + " " + temp);
            textAreaOut.setText("");
        }
    }

    /**
     * main 方法
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 启动方法
     * @param primaryStage 主舞台
     * @throws Exception IO 异常
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 从 fxml 中读取 gui
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatRoom.fxml"));
        // 获取主面板
        BorderPane pane = loader.load();

        // 获取输出区
        textAreaIn = (TextArea) pane.getCenter();

        // 获得底部面板
        HBox hBox = (HBox) pane.getBottom();
        // 获得输出区
        textAreaOut = (TextArea) hBox.getChildren().get(0);

        // 设置主场景
        Scene scene = new Scene(pane);

        // 设置主舞台
        primaryStage.setScene(scene);
        primaryStage.show();

        // 界面启动完成后，再运行的方法
        Platform.runLater(() -> {
            try {
                // 启动线程
                Thread thread = new Thread(new RemoteReader());
                // 声明网络套接字
                connection = MainController.getSocket();
                // 获取输入流和输出流
                serverIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                serverOut = new PrintStream(connection.getOutputStream());

                thread.start();
            } catch (IOException e) {
                System.out.println("Can not connect the server!");
                textAreaIn.appendText("\n[Error: cannot connect the server!]");
            }
        });
    }

    /**
     * 一个 runnable 类可以持续读取从服务器发送的数据
     */
    private class RemoteReader implements Runnable {
        @Override
        public void run() {
            try {
                // 持续运行
                while (true) {
                    String Msg;
                    // 当输入不为空
                    if ((Msg = serverIn.readLine()) != null) {
                        textAreaIn.appendText(Msg + "\n");
                    }
                    // 休息一下
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
