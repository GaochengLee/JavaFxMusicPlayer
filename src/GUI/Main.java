/*
 * Copyright (c) 2018. 18-12-10 下午8:14.
 * @author 李高丞
 */

package GUI;

import controller.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

/**
 * Main 类
 * 采用了单例的设计模式
 * 客户端的主要界面，通过 fxml 来设计布局，通过 javafx 将在 fxml 写好的布局加载出来
 * 定义了一系列的 get 方法和 set 方法来获得与修改属性
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class Main extends Application {

    /**
     * 单例设计模式 Main 类
     */
    private static Main mainGUI;
    /**
     * 主场景
     */
    public static Stage mainStage;
    /**
     * 主面板
     */
    private BorderPane mainPane;
    /**
     * 左面板
     */
    private AnchorPane leftMusicList;
    /**
     * 我的音乐面板
     */
    private StackPane myMusicPage;
    /**
     * 播放列表面板
     */
    private AnchorPane playList;
    /**
     * 搜索面板
     */
    private AnchorPane searchPage;

    /**
     * main 控制器
     */
    private MainController mainController;
    /**
     * 我的音乐面板控制器
     */
    private MyMusicPageController myMusicPageController;
    /**
     * 播放列表控制器
     */
    private PlayListController playListController;
    /**
     * 左面板控制器
     */
    private LeftMusicController leftMusicController;
    /**
     * 搜索面板控制器
     */
    private SearchPageController searchPageController;
    /**
     * 实现面板拖移时需要的 x 偏移量
     */
    private double xOffset = 0;
    /**
     * 实现面板拖移时需要的 y 偏移量
     */
    private double yOffset = 0;

    /**
     * 初始化方法
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();
        if (mainGUI == null) {
            mainGUI = this;
        }
    }

    /**
     * javafx 的启动方法
     *
     * @param primaryStage 主舞台
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 换个名字
        mainStage = primaryStage;

        // 从 fxml 文件中加载 main 类
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        mainPane = loader.load();

        // 获得 main 类的控制器
        mainController = loader.getController();
        // 初始化控制器
        mainController.__init__();

        // 从 fxml 文件中加载 左面板 类
        loader = new FXMLLoader(getClass().getResource("leftMusicList.fxml"));
        leftMusicList = loader.load();

        // 获得 左面板控制器
        leftMusicController = loader.getController();
        // 初始化控制器
        leftMusicController.__init__();

        // 从 fxml 文件中加载 我的音乐界面 类
        loader = new FXMLLoader(getClass().getResource("myMusicPage.fxml"));
        myMusicPage = loader.load();

        // 获得 我的音乐界面控制器
        myMusicPageController = loader.getController();


        // 从 fxml 文件中加载 播放列表界面 类
        loader = new FXMLLoader(getClass().getResource("playList.fxml"));
        playList = loader.load();

        // 获得 播放列表界面 控制器
        playListController = loader.getController();
        // 初始化控制器
        playListController.__init__();

        // 从 fxml 文件中加载 搜索界面 类
        loader = new FXMLLoader(getClass().getResource("searchPage.fxml"));
        searchPage = loader.load();

        // 获得 搜索界面 控制器
        searchPageController = loader.getController();
        // 初始化控制器
        searchPageController.__init__();

        // 在主面板的中间设置为 我的音乐 界面
        mainPane.setCenter(myMusicPage);
        // 在主面板的左侧设置为 左面板 界面
        mainPane.setLeft(leftMusicList);

        // 初始化 main 控制器的数据
        mainController.__initData__();
        // 初始化 我的音乐界面控制器的数据
        myMusicPageController.__initData__();

        // 添加鼠标拖动主窗体的事件处理方法
        mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        // 添加鼠标拖动主窗体的事件处理方法
        mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        // 设置播放器界面大小
        Scene scene = new Scene(mainPane, 1280, 720);

        // 更换鼠标图标
        File file = new File("timg.gif");
        Image image = new Image(file.toURI().toString());
        ImageCursor cursor = new ImageCursor(image, 20, 20);
        scene.setCursor(cursor);

        // 加载样式层叠表
        // todo：样式层叠表没有写好
        scene.getStylesheets().add("css/main/main.css");
        scene.getStylesheets().add("css/leftMusic/leftMusic.css");
        scene.getStylesheets().add("css/myMusicPage/myMusicPage.css");

        // 删除主舞台的系统自带窗体
        primaryStage.initStyle(StageStyle.UNDECORATED);
        // 设置主舞台的主场景
        primaryStage.setScene(scene);
        // 展示主舞台
        primaryStage.show();
    }

    /**
     * main 方法，启动音乐播放器
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        launch(args);
    }

    /*
     * get 方法和 set 方法
     * 用于修改和获得主要界面的控制器和场景面板
     */

    /**
     * 获得主舞台
     *
     * @return 返回主舞台
     */
    public static Stage getMainStage() {
        return mainStage;
    }

    /**
     * 获得主场景的主面板
     *
     * @return 返回主场景的主面板
     */
    public BorderPane getMainPane() {
        return mainPane;
    }

    /**
     * 获得左侧控制面板
     *
     * @return 返回左侧控制面板
     */
    public AnchorPane getLeftMusicList() {
        return leftMusicList;
    }

    /**
     * 获得我的音乐面板
     *
     * @return 返回我的音乐面板
     */
    public StackPane getMyMusicPage() {
        return myMusicPage;
    }

    /**
     * 获得播放列表面板
     *
     * @return 返回播放列表面板
     */
    public AnchorPane getPlayList() {
        return playList;
    }

    /**
     * 获得搜索界面面板
     *
     * @return 返回搜索界面面板
     */
    public AnchorPane getSearchPage() {
        return searchPage;
    }

    /**
     * 获得主类
     *
     * @return 返回主类
     */
    public static Main getMainGUI() {
        return mainGUI;
    }

    /**
     * 获得主面板控制器
     *
     * @return 返回主面板控制器
     */
    public MainController getMainController() {
        return mainController;
    }

    /**
     * 获得我的音乐面板控制器
     *
     * @return 返回我的音乐面板控制器
     */
    public MyMusicPageController getMyMusicPageController() {
        return myMusicPageController;
    }

    /**
     * 获得播放列表面板控制器
     *
     * @return 返回播放列表面板控制器
     */
    public PlayListController getPlayListController() {
        return playListController;
    }

    /**
     * 获得左侧面板控制器
     *
     * @return 返回左侧面板控制器
     */
    public LeftMusicController getLeftMusicController() {
        return leftMusicController;
    }

    /**
     * 获得搜索界面面板控制器
     *
     * @return 返回搜索界面面板控制器
     */
    public SearchPageController getSearchPageController() {
        return searchPageController;
    }
}
