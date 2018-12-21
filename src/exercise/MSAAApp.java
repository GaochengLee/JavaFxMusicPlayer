package exercise;
/*
 * Copyright (c) 2013, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MSAAApp extends Application {

    @Override
    public void start(Stage stage) {
        if (!Platform.isSupported(ConditionalFeature.SCENE3D)) {
            throw new RuntimeException("*** ERROR: common conditional SCENE3D is not supported");
        }

        stage.setTitle("JavaFX MSAA demo");

        Group root = new Group();
        Scene scene = new Scene(root, 1000, 800);
        scene.setFill(Color.color(0.2, 0.2, 0.2, 1.0));

        HBox hbox = new HBox();
        hbox.setLayoutX(75);
        hbox.setLayoutY(200);

        PhongMaterial phongMaterial = new PhongMaterial(Color.color(1.0, 0.7, 0.8));
        Cylinder cylinder1 = new Cylinder(100, 200);
        cylinder1.setMaterial(phongMaterial);
        SubScene noMsaa = createSubScene("MSAA = false", cylinder1,
                Color.TRANSPARENT,
                new PerspectiveCamera(), false);
        hbox.getChildren().add(noMsaa);

        Cylinder cylinder2 = new Cylinder(100, 200);
        cylinder2.setMaterial(phongMaterial);
        SubScene msaa = createSubScene("MSAA = true", cylinder2,
                Color.TRANSPARENT,
                new PerspectiveCamera(), true);
        hbox.getChildren().add(msaa);

        Slider slider = new Slider(0, 360, 0);
        slider.setBlockIncrement(1);
        slider.setTranslateX(425);
        slider.setTranslateY(625);
        cylinder1.rotateProperty().bind(slider.valueProperty());
        cylinder2.rotateProperty().bind(slider.valueProperty());
        root.getChildren().addAll(hbox, slider);

        stage.setScene(scene);
        stage.show();
    }

    private static Parent setTitle(String str) {
        final VBox vbox = new VBox();
        final Text text = new Text(str);
        text.setFont(Font.font("Times New Roman", 24));
        text.setFill(Color.WHEAT);
        vbox.getChildren().add(text);
        return vbox;
    }

    private static SubScene createSubScene(String title, Node node,
                                           Paint fillPaint, Camera camera, boolean msaa) {
        Group root = new Group();

        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(50);
        light.setTranslateY(-300);
        light.setTranslateZ(-400);
        PointLight light2 = new PointLight(Color.color(0.6, 0.3, 0.4));
        light2.setTranslateX(400);
        light2.setTranslateY(0);
        light2.setTranslateZ(-400);

        AmbientLight ambientLight = new AmbientLight(Color.color(0.2, 0.2, 0.2));
        node.setRotationAxis(new Point3D(2, 1, 0).normalize());
        node.setTranslateX(180);
        node.setTranslateY(180);
        root.getChildren().addAll(setTitle(title), ambientLight,
                light, light2, node);

        SubScene subScene = new SubScene(root, 500, 400, true,
                msaa ? SceneAntialiasing.BALANCED : SceneAntialiasing.DISABLED);
        subScene.setFill(fillPaint);
        subScene.setCamera(camera);

        return subScene;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

