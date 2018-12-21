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
     *
     */
    private ContextContain contain = new ContextContain();
    /**
     *
     */
    private Main mainGUI = Main.getMainGUI();
    /**
     *
     */
    private Handler handler = new Handler(mainGUI);
    /**
     *
     */
    private PlayState playState = PlayState.getPlayState();
    /**
     *
     */
    private Controller myMusicPageController = mainGUI.getMyMusicPageController();
    /**
     *
     */
    private Controller playListController = mainGUI.getPlayListController();
    /**
     *
     */
    private Controller searchController = mainGUI.getSearchPageController();

    /**
     *
     * @param event
     */
    @Override
    public void handle(MouseEvent event) {
        //
        Node source = (Node) event.getSource();
        //
        double x = event.getScreenX();
        //
        double y = event.getScreenY();
        //
        ContextMenu contextMenu;
        //
        String id = ((Node) event.getSource()).getId();

        if (id.equals("TableView_playList")) contextMenu = contain.getListContext();
        else if(id.equals("TableView_songList")) contextMenu = contain.getSongContext();
        else contextMenu = contain.getSearchContext();

        if (event.getButton() == MouseButton.SECONDARY) {
            contextMenu.show(source, x, y);
            if (checkEmpty(contextMenu)) contextMenu.hide();

        }
        if (event.getButton() == MouseButton.PRIMARY) {
            contextMenu.hide();

            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                if (id.equals("TableView_songList")) {
                    if (myMusicPageController.getTableView_songList().getItems().size() == 0) return;
                    if (myMusicPageController.getTableView_songList().getSelectionModel().getSelectedItem() == null) return;

                    handler.play(myMusicPageController);
                } else if (id.equals("TableView_playList")){
                    if (playListController.getTableView_songList().getItems().size() == 0) return;
                    if (playListController.getTableView_songList().getSelectionModel().getSelectedItem() == null) return;

                    handler.play(playListController);
                } else {
                    Music music = searchController.getTableView_songList().getSelectionModel().getSelectedItem();

                    MainController.getServerOut().println("# send " + music.getMusicTitle());

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Handler.receiveFile(MainController.getSocket());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
            }
        }

    }

    /**
     *
     * @param menu
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean checkEmpty(ContextMenu menu) {
        if (menu == contain.getSongContext() || menu == contain.getListContext() && menu.getOwnerNode() instanceof TableView) {
            return ((TableView<Music>) menu.getOwnerNode()).getSelectionModel().getSelectedItems().isEmpty();
        }
        return false;
    }

    /**
     *
     */
    private class ContextContain {
        /**
         *
         */
        private ContextMenu songContext = new ContextMenu();
        /**
         *
         */
        private ContextMenu listContext = new ContextMenu();
        /**
         *
         */
        private ContextMenu searchContext = new ContextMenu();
        /**
         *
         */
        private MenuItem playInPlayList = new MenuItem("播放");
        /**
         *
         */
        private MenuItem playInMyMusic = new MenuItem("播放");
        /**
         *
         */
        private MenuItem playAll = new MenuItem("播放全部");
        /***
         *
         */
        private MenuItem playNext = new MenuItem("下一个播放");
        /**
         *
         */
        private MenuItem removeSong = new MenuItem("从播放列表中删除");
        /**
         *
         */
        private MenuItem pathInPlayList = new MenuItem("路径");
        /**
         *
         */
        private MenuItem pathInSongList = new MenuItem("路径");
        /**
         *
         */
        private MenuItem download = new MenuItem("下载");

        /**
         *
         */
        private ContextContain() {
            //
            songContext.getItems().addAll(playInMyMusic, new SeparatorMenuItem(), playNext, playAll,
                    new SeparatorMenuItem(), pathInSongList);
            //
            listContext.getItems().addAll(playInPlayList, removeSong, new SeparatorMenuItem(), pathInPlayList);
            //
            searchContext.getItems().addAll(download);
            //
            playInPlayList.setOnAction(event -> handler.play(playListController));
            //
            playInMyMusic.setOnAction(event -> handler.play(myMusicPageController));
            //
            playAll.setOnAction(event -> handler.playAll());
            //
            playNext.setOnAction(event -> {
                //
                Music nextMusic = myMusicPageController.getTableView_songList().getSelectionModel().getSelectedItem();
                //
                playListController.getTableView_songList().getItems().add(playState.getCurrentIndex() + 1, nextMusic);
                //
                playState.getCurrent_songList().add(playState.getCurrentIndex() + 1, nextMusic);
                //
                Handler.refreshPlayList();
            });
            //
            removeSong.setOnAction(event -> {
                //
                Music deleteMusic = playListController.getTableView_songList().getSelectionModel().getSelectedItem();
                //
                playListController.getTableView_songList().getItems().remove(deleteMusic);
                //
                playState.getCurrent_songList().remove(deleteMusic);
                //
                Handler.refreshPlayList();
            });
            //
            pathInPlayList.setOnAction(event -> {
                //
                try {
                    Desktop.getDesktop().open(new File(playListController.getTableView_songList().getSelectionModel().getSelectedItem().getPath()));
                }catch (IOException e) {
                    e.printStackTrace();
                }
            });
            //
            pathInSongList.setOnAction(event -> {
                //
                try {
                    Desktop.getDesktop().open(new File(myMusicPageController.getTableView_songList().getSelectionModel().getSelectedItem().getPath()));
                }catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }

        /**
         *
         * @return
         */
        private ContextMenu getSongContext() {
            return songContext;
        }

        /**
         *
         * @return
         */
        private ContextMenu getListContext() {
            return listContext;
        }

        /**
         *
         * @return
         */
        private ContextMenu getSearchContext() {
            return searchContext;
        }
    }
}
