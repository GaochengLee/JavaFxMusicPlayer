/*
 * Copyright (c) 2018. 18-12-10 下午3:06.
 * @author 李高丞
 */

package controller;

import mainGUI.Main;
import mainGUI.PercussionGUI;
import handler.Handler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

/**
 * 左侧面板的控制器 从FXML中获取 fx:id 设置一系列的 get 方法
 * 初始化了各个按钮的 Action，将事件处理放在了 handler 类里。
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class LeftMusicController {
    /**
     * 获取 Main 类
     */
    private static Main mainGUI = Main.getMainGUI();

    /**
     * 获取 handler 事件处理类
     */
    private static Handler handler = new Handler(mainGUI);

    /**
     * 左侧界面的主面板
     */
    @FXML
    private AnchorPane Anchor_leftMusic;

    /**
     * 左侧界面的垂直面板 - 1
     */
    @FXML
    private VBox VBox_leftMusic;

    /**
     * 左侧界面的垂直面板 - 2
     */
    @FXML
    private VBox VBox_main;

    /**
     * 左侧界面的垂直面板 - 3
     */
    @FXML
    private VBox VBox_top;

    /**
     * 左侧界面的引入音乐文件按钮
     */
    @FXML
    private Button Button_importMusic;

    /**
     * 左侧界面的引入音乐文件夹按钮
     */
    @FXML
    private Button Button_importFolder;

    /**
     * 左侧界面的敲击乐界面按钮
     */
    @FXML
    private Button Button_percussion;

    /**
     * 左侧界面的聊天室按钮
     */
    @FXML
    private Button Button_chatRoom;

    /**
     * 左侧界面的小游戏按钮
     */
    @FXML
    private Button Button_game;

    /**
     * 导出音乐信息为 Excel Button
     */
    @FXML
    private Button Button_exportExcel;

    /**
     * 导出音乐信息为 PDF Button
     */
    @FXML
    private Button Button_exportPDF;


    /**
     * 左侧界面的锚面板
     */
    @FXML
    private AnchorPane Anchor_bottom;

    /**
     * 左侧界面的水平面板 - 2
     */
    @FXML
    private HBox HBox_message;

    /**
     * 左侧界面的图片观看器
     */
    @FXML
    private ImageView ImageView_cover;

    /**
     * 左侧界面的垂直面板 - 4
     */
    @FXML
    private VBox VBox_message;

    /**
     * 左侧界面的歌名标签
     */
    @FXML
    private Label Label_songName;

    /**
     * 左侧界面的歌手标签
     */
    @FXML
    private Label Label_singer;

    /*
     * 各种 get 方法，用于获取相应的对象
     */

    /**
     * 获得左侧界面的 AnchorPane
     *
     * @return 返回左侧界面的 AnchorPane
     */
    public AnchorPane getAnchor_leftMusic() {
        return Anchor_leftMusic;
    }

    /**
     * 获得左侧界面的 VBox
     *
     * @return 返回左侧界面的 VBox
     */
    public VBox getVBox_leftMusic() {
        return VBox_leftMusic;
    }

    /**
     * 获得左侧界面的主 VBox
     *
     * @return 返回左侧界面的主 VBox
     */
    public VBox getVBox_main() {
        return VBox_main;
    }

    /**
     * 获得左侧界面的顶部 VBox
     *
     * @return 返回左侧界面的顶部 VBox
     */
    public VBox getVBox_top() {
        return VBox_top;
    }

    /**
     * 获得左侧界面的导入音乐文件 Button
     *
     * @return 返回左侧界面的导入音乐文件 Button
     */
    public Button getButton_importMusic() {
        return Button_importMusic;
    }

    /**
     * 获得左侧界面的导入音乐文件夹 Button
     *
     * @return 返回左侧界面的导入音乐文件夹 Button
     */
    public Button getButton_importFolder() {
        return Button_importFolder;
    }

    /**
     * 获得左侧界面的敲击乐 Button
     *
     * @return 返回左侧界面的敲击乐 Button
     */
    public Button getButton_percussion() {
        return Button_percussion;
    }

    /**
     * 获得左侧界面的聊天室 Button
     *
     * @return 返回左侧界面的聊天室 Button
     */
    public Button getButton_chatRoom() {
        return Button_chatRoom;
    }

    /**
     * 获得左侧界面的小游戏 Button
     *
     * @return 返回左侧界面的小游戏 Button
     */
    public Button getButton_game() {
        return Button_game;
    }

    /**
     * 获得左侧界面的底部 AnchorPane
     *
     * @return 返回左侧界面的底部 AnchorPane
     */
    public AnchorPane getAnchor_bottom() {
        return Anchor_bottom;
    }

    /**
     * 获得左侧界面的信息 HBox
     *
     * @return 返回左侧界面的信息HBox
     */
    public HBox getHBox_message() {
        return HBox_message;
    }

    /**
     * 获得左侧界面的歌曲封面 ImageView
     *
     * @return 返回左侧界面的歌曲封面 ImageView
     */
    public ImageView getImageView_cover() {
        return ImageView_cover;
    }

    /**
     * 获得左侧界面的信息 VBox
     *
     * @return 返回左侧界面的信息 VBox
     */
    public VBox getVBox_message() {
        return VBox_message;
    }

    /**
     * 获得左侧界面的歌名 Label
     *
     * @return 返回左侧界面的歌名 Label
     */
    public Label getLabel_songName() {
        return Label_songName;
    }

    /**
     * 获得左侧界面的歌手 Label
     *
     * @return 返回左侧界面的歌手 Label
     */
    public Label getLabel_singer() {
        return Label_singer;
    }

    /**
     * 获得左侧界面的导出音乐信息 Button
     *
     * @return 返回左侧界面的导出音乐信息 Button
     */
    public Button getButton_exportExcel() {
        return Button_exportExcel;
    }

    /**
     * 获得左侧界面的导出音乐信息 Button
     *
     * @return 返回左侧界面的导出音乐信息 Button
     */
    public Button getButton_exportPDF() {
        return Button_exportPDF;
    }

    /**
     * 单击了“添加音乐”按钮后的事件处理
     */
    @FXML
    private void Action_addMusic() {
        handler.addLocalMusic();
    }

    /**
     * 单击了“添加文件夹”按钮后的事件处理
     */
    @FXML
    private void Action_addMusicFolder() {
        handler.addLocalMusicFolder();
    }

    /**
     * 弹出一个敲击乐界面可以玩敲击乐
     */
    @FXML
    private void Action_percussion() {
        // 启动敲击乐界面
        Platform.runLater(() -> {
            try {
                new PercussionGUI().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 弹出一个聊天室界面登录后可以聊天
     */
    @FXML
    private void Action_chatRoom() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("通知");
        alert.setHeaderText("^-^");
        alert.setContentText("聊天室功能尚在开发");
        alert.showAndWait();
    }

    /**
     * 弹出一个小游戏界面
     */
    @FXML
    private void Action_game() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("通知");
        alert.setHeaderText("^-^");
        alert.setContentText("小游戏功能尚在开发");
        alert.showAndWait();
    }

    /**
     * 导出音乐信息为 Excel
     */
    @FXML
    private void Action_exportExcel() {
        try {
            handler.exportExcel();
        } catch (Exception e) {
            System.out.println("Error in export Excel.");
        }
    }

    /**
     * 导出音乐信息为 PDF
     */
    @FXML
    private void Action_exportPDF() {
        try {
            handler.exportPDF();
        } catch (Exception e) {
            System.out.println("Error in export PDF");
        }
    }

    /**
     * 初始化 LeftMusicController
     */
    public void __init__() {
        __initCSS__();
    }

    /**
     * 初始化 左侧列表的样式层叠表
     */
    private void __initCSS__() {
//        Button_importMusic.getStyleClass().set(0, "button--nina");
//        Button_importMusic.getStyleClass().remove(0);
//        Button_importFolder.getStyleClass().remove(0);
//        Button_percussion.getStyleClass().remove(0);
//        Button_chatRoom.getStyleClass().remove(0);
//        Button_game.getStyleClass().remove(0);

        // 初始化歌曲封面图片
        File file = new File("C:\\Users\\hasee\\Documents\\JavaCode\\LearnJavaFx\\src\\initial.jpg");
        Image image = new Image(file.toURI().toString());
        ImageView_cover.imageProperty().setValue(image);
    }

}
