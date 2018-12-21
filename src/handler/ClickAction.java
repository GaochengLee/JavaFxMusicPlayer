package handler;


import GUI.Main;
import controller.Controller;
import controller.MainController;
import entity.Music;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import service.PlayState;

import java.awt.*;
import java.io.File;
import java.io.IOException;


/**
 * @author 李高丞
 * @version 1.0 Beta
 */
public class ClickAction implements EventHandler<MouseEvent> {
    /**
     * 右键菜单
     */
    private ContextContain contain = new ContextContain();
    /**
     * 获取主类
     */
    private Main mainGUI = Main.getMainGUI();
    /**
     * 获取控制类
     */
    private Handler handler = new Handler(mainGUI);
    /**
     * 播放状态
     */
    private PlayState playState = PlayState.getPlayState();
    /**
     * 我的音乐控制器
     */
    private Controller myMusicPageController = mainGUI.getMyMusicPageController();
    /**
     * 播放列表控制器
     */
    private Controller playListController = mainGUI.getPlayListController();
    /**
     * 搜索界面控制器
     */
    private Controller searchController = mainGUI.getSearchPageController();

    /**
     * 鼠标事件处理
     *
     * @param event 鼠标事件
     */
    @Override
    public void handle(MouseEvent event) {
        // 获取鼠标事件的来源
        Node source = (Node) event.getSource();
        // 获取鼠标位置的 x 坐标
        double x = event.getScreenX();
        // 获取鼠标位置的 y 坐标
        double y = event.getScreenY();
        // 右键主菜单
        ContextMenu contextMenu;
        // 事件来源的 fx:id
        String id = ((Node) event.getSource()).getId();

        // 判断来源来确认右键菜单的内容
        switch (id) {
            case "TableView_playList":
                contextMenu = contain.getListContext();
                break;
            case "TableView_songList":
                contextMenu = contain.getSongContext();
                break;
            case "TableView_searchList":
                contextMenu = contain.getSearchContext();
                break;
            default:
                contextMenu = new ContextMenu();
                break;
        }

        // 如果是鼠标右键单击事件
        if (event.getButton() == MouseButton.SECONDARY) {
            // 在 x，y位置显示右键菜单
            contextMenu.show(source, x, y);
            // 如果判断为空位置，则隐藏右键菜单
            if (checkEmpty(contextMenu)) contextMenu.hide();
        }
        // 如果是鼠标左键单击事件
        if (event.getButton() == MouseButton.PRIMARY) {
            // 隐藏右键菜单
            contextMenu.hide();
            // 如果左键单击了两次
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                // 判断左键单击区域
                switch (id) {
                    case "TableView_songList":
                        // 如果是我的音乐区域 
                        // 如果当前音乐列表为空或者选择的单位是空则无事件
                        if (myMusicPageController.getTableView_songList().getItems().size() == 0) return;
                        if (myMusicPageController.getTableView_songList().getSelectionModel().getSelectedItem() == null)
                            return;

                        // 否则，播放音乐
                        handler.play(myMusicPageController);
                        break;
                    case "TableView_playList":
                        // 如果是播放列表区域
                        // 如果当前音乐列表为空或者选择的单位是空则无事件
                        if (playListController.getTableView_songList().getItems().size() == 0) return;
                        if (playListController.getTableView_songList().getSelectionModel().getSelectedItem() == null)
                            return;

                        // 否则，播放音乐
                        handler.play(playListController);
                        break;
                    case "TableView_searchList":
                        // 如果是搜索界面区域
                        // 确认选择的音乐
                        Music music = searchController.getTableView_songList().getSelectionModel().getSelectedItem();
                        // 向服务器发送传输文件请求
                        MainController.getServerOut().println("# send " + music.getMusicTitle());
                        // 启动线程来接受文件
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 接受文件
                                    Handler.receiveFile(MainController.getSocket());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                        break;
                }
            }
        }

    }

    /**
     * 判断右键区域是否为空
     *
     * @param menu 右键菜单
     * @return true 说明选择区域不为空
     */
    @SuppressWarnings("unchecked")
    private boolean checkEmpty(ContextMenu menu) {
        if (menu == contain.getSongContext() || menu == contain.getListContext() && menu.getOwnerNode() instanceof TableView) {
            return ((TableView<Music>) menu.getOwnerNode()).getSelectionModel().getSelectedItems().isEmpty();
        }
        return false;
    }

    /**
     * 右键菜单的内容
     */
    private class ContextContain {
        /**
         * 我的音乐的右键菜单
         */
        private ContextMenu songContext = new ContextMenu();
        /**
         * 播放列表的右键菜单
         */
        private ContextMenu listContext = new ContextMenu();
        /**
         * 搜索列表的右键菜单
         */
        private ContextMenu searchContext = new ContextMenu();
        /**
         * 在播放列表里的播放选项
         */
        private MenuItem playInPlayList = new MenuItem("播放");
        /**
         * 在我的音乐里的播放选项
         */
        private MenuItem playInMyMusic = new MenuItem("播放");
        /**
         * 在我的音乐里的全部播放选项
         */
        private MenuItem playAll = new MenuItem("播放全部");
        /**
         * 在我的音乐里的下一个播放选项
         */
        private MenuItem playNext = new MenuItem("下一个播放");
        /**
         * 在播放列表里的从播放列表中删除选项
         */
        private MenuItem removeSong = new MenuItem("从播放列表中删除");
        /**
         * 在播放列表里的路径选项
         */
        private MenuItem pathInPlayList = new MenuItem("路径");
        /**
         * 在我的音乐里的路径选项
         */
        private MenuItem pathInSongList = new MenuItem("路径");
        /**
         * 在搜索界面的下载选项
         */
        private MenuItem download = new MenuItem("下载");

        /**
         * 右键菜单内容构造器
         */
        private ContextContain() {
            // 向我的音乐的右键菜单中添加选项
            songContext.getItems().addAll(playInMyMusic, new SeparatorMenuItem(), playNext, playAll,
                    new SeparatorMenuItem(), pathInSongList);
            // 向播放列表的右键菜单中添加选项
            listContext.getItems().addAll(playInPlayList, removeSong, new SeparatorMenuItem(), pathInPlayList);
            // 向搜索列表的右键菜单中添加选项
            searchContext.getItems().addAll(download);
            // 设置播放列表的播放选项事件
            playInPlayList.setOnAction(event -> handler.play(playListController));
            // 设置我的音乐的播放选项事件
            playInMyMusic.setOnAction(event -> handler.play(myMusicPageController));
            // 设置我的音乐的播放全部事件
            playAll.setOnAction(event -> handler.playAll());
            // 设置我的音乐的下一首播放事件
            playNext.setOnAction(event -> {
                // 获得选中的音乐
                Music nextMusic = myMusicPageController.getTableView_songList().getSelectionModel().getSelectedItem();
                // 添加到播放列表中
                playListController.getTableView_songList().getItems().add(playState.getCurrentIndex() + 1, nextMusic);
                // 添加到播放状态的当前播放音乐列表中
                playState.getCurrent_songList().add(playState.getCurrentIndex() + 1, nextMusic);
                // 刷新播放列表
                Handler.refreshPlayList();
            });
            // 设置从播放列表中删除选项事件
            removeSong.setOnAction(event -> {
                // 获得选中的音乐
                Music deleteMusic = playListController.getTableView_songList().getSelectionModel().getSelectedItem();
                // 从播放列表中移除
                playListController.getTableView_songList().getItems().remove(deleteMusic);
                // 从播放状态的当前播放音乐列表中移除
                playState.getCurrent_songList().remove(deleteMusic);
                // 刷新播放列表
                Handler.refreshPlayList();
            });
            // 设置在播放列表路径选项事件
            pathInPlayList.setOnAction(event -> {
                // 显示文件夹
                try {
                    Desktop.getDesktop().open(
                            new File(playListController.
                                    getTableView_songList().
                                    getSelectionModel().
                                    getSelectedItem().getPath()
                            ).getParentFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            // 设置在我的音乐路径选项事件
            pathInSongList.setOnAction(event -> {
                // 显示文件夹
                try {
                    Desktop.getDesktop().open(
                            new File(myMusicPageController.
                                    getTableView_songList().
                                    getSelectionModel().
                                    getSelectedItem().getPath()
                            ).getParentFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }

        /**
         * 获得我的音乐右键菜单
         *
         * @return 返回我的音乐右键菜单
         */
        private ContextMenu getSongContext() {
            return songContext;
        }

        /**
         * 获得播放列表右键菜单
         *
         * @return 返回播放列表右键菜单
         */
        private ContextMenu getListContext() {
            return listContext;
        }

        /**
         * 获得搜索界面右键菜单
         *
         * @return 返回搜索界面右键菜单
         */
        private ContextMenu getSearchContext() {
            return searchContext;
        }
    }
}
