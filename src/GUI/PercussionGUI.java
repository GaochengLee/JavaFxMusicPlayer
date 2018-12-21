/*
 * Copyright (c) 2018. 18-12-12 上午11:15.
 * @author 李高丞
 */

package GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.sound.midi.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Percussion 类‘
 * 使用的 javax.sound.midi 这个包
 * 敲击乐类，主界面单击敲击乐按钮后就运行
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class PercussionGUI extends Application {
    /**
     * checkBox列表
     */
    private static ArrayList<CheckBox> checkBoxList;
    /**
     * 敲击乐的音乐序
     */
    private Sequencer sequencer;
    /**
     * 敲击乐的音乐序列
     */
    private Sequence sequence;
    /**
     * 敲击乐的音乐轨迹
     */
    private Track track;
    /**
     * 敲击乐的乐器名称
     */
    private String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
            "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
            "Low-mid Tom", "High Agogo", "Open Hi Conga"};
    /**
     * 敲击乐的乐器代码
     */
    private int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    /**
     * main 方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 启动方法
     *
     * @param primaryStage 主舞台
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 从 fxml 文件中加载 gui
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Percussion.fxml"));
        // 获得主面板
        BorderPane borderPane = loader.load();
        // 获得左面板
        VBox vBox = (VBox) borderPane.getLeft();
        // 声明一个网格面板
        GridPane gridPane = new GridPane();
        // 设置格网格面板的每个单元格之间的间距
        gridPane.setVgap(9);
        gridPane.setHgap(6);

        // 把网格面板设置为中央
        borderPane.setCenter(gridPane);
        // 新建一个 checkBox 表
        checkBoxList = new ArrayList<>();
        // 向表内天剑元素
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                CheckBox box = new CheckBox();
                // 设置 CheckBox 当前状态为未选中状态
                box.setSelected(false);
                checkBoxList.add(box);
                gridPane.add(box, i, j);
            }
        }
        // 将乐器名字添加到左侧面板
        for (int i = 0; i < instrumentNames.length; i++) {
            Label label = new Label(instrumentNames[i]);
            vBox.getChildren().add(label);
        }
        // 设置左侧面板每个元素的间隔
        vBox.setSpacing(10);
        // 设置主场景大小
        Scene scene = new Scene(borderPane, 600, 400);
        // 向主舞台里添加主场景
        primaryStage.setScene(scene);
        primaryStage.show();
        setUpMidi();
    }

    /**
     * 启动 midi 播放音乐
     */
    private void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            if (sequencer == null) System.out.println(1);
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建造声轨然后启动
     */
    private void buildTrackAndStart() {
        ArrayList<Integer> trackList;

        setUpMidi();
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; i++) {
            trackList = new ArrayList<>();

            for (int j = 0; j < 16; j++) {
                CheckBox jc = checkBoxList.get(j + (16 * i));
                if (jc.isSelected()) {
                    int key = instruments[i];
                    trackList.add(key);
                } else {
                    trackList.add(null);
                }
            }
            makeTracks(trackList);
        }
        track.add(makeEvent(192, 9, 1, 0, 15));

        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 制作声轨
     *
     * @param list 声轨列表
     */
    private void makeTracks(ArrayList list) {
        Iterator it = list.iterator();

        for (int i = 0; i < 16; i++) {
            Integer num = (Integer) it.next();
            if (num != null) {
                int numKey = num.intValue();
                track.add(makeEvent(144, 9, numKey, 100, i));
                track.add(makeEvent(128, 9, numKey, 100, i + 1));
            }
        }
    }

    /**
     * 播放声音
     *
     * @param comd
     * @param chan
     * @param one
     * @param two
     * @param tick
     * @return 返回事件
     */
    private MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;

        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    /**
     * 单击启动按钮后的事件
     */
    @FXML
    private void Action_Start() {
        buildTrackAndStart();
    }

    /**
     * 单击停止按钮后的事件
     */
    @FXML
    private void Action_Stop() {
        sequencer.stop();
    }

    /**
     * 单击了加速按钮后的事件
     */
    @FXML
    private void Action_TempoUp() {
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor((float) (tempoFactor * 1.03));
    }

    /**
     * 单击的减速按钮后的事件
     */
    @FXML
    private void Action_TempoDown() {
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor((float) (tempoFactor * 0.97));
    }

}
