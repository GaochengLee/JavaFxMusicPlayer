/*
 * Copyright (c) 2018. 18-12-12 上午11:15.
 * @author 李高丞
 */

package GUI;

import javafx.application.Application;
import javafx.application.Platform;
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
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class PercussionGUI extends Application {
    static ArrayList<CheckBox> checkBoxList;

    Sequencer sequencer;
    Sequence sequence;
    Track track;

    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
            "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
            "Low-mid Tom", "High Agogo", "Open Hi Conga"};

    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Percussion.fxml"));
        BorderPane borderPane = loader.load();
        VBox vBox = (VBox) borderPane.getLeft();

        GridPane gridPane = new GridPane();
        gridPane.setVgap(9);
        gridPane.setHgap(6);


        borderPane.setCenter(gridPane);

        checkBoxList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                CheckBox box = new CheckBox();
                box.setSelected(false);
                checkBoxList.add(box);
                gridPane.add(box, i, j);
            }
        }
        for (int i = 0; i < instrumentNames.length; i++) {
            Label label = new Label(instrumentNames[i]);
            vBox.getChildren().add(label);
        }

        vBox.setSpacing(10);

        Scene scene = new Scene(borderPane, 600, 400);
        File file = new File("timg.gif");
        Image image = new Image(file.toURI().toString());
        ImageCursor cursor = new ImageCursor(image, 20, 20);
        scene.setCursor(cursor);

        primaryStage.setScene(scene);
        primaryStage.show();
        setUpMidi();
    }


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

    @FXML
    private void Action_Start() {
        buildTrackAndStart();
    }

    @FXML
    private void Action_Stop() {
        sequencer.stop();
    }

    @FXML
    private void Action_TempoUp() {
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor((float) (tempoFactor * 1.03));
    }

    @FXML
    private void Action_TempoDown() {
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor((float) (tempoFactor * 0.97));
    }

}
