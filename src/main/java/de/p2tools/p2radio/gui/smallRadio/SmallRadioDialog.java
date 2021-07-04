/*
 * P2tools Copyright (C) 2018 W. Xaver W.Xaver[at]googlemail.com
 * https://www.p2tools.de/
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */

package de.p2tools.p2radio.gui.smallRadio;

import de.p2tools.p2Lib.P2LibConst;
import de.p2tools.p2Lib.P2LibInit;
import de.p2tools.p2Lib.configFile.IoReadWriteStyle;
import de.p2tools.p2Lib.dialogs.dialog.PDialogFactory;
import de.p2tools.p2Lib.guiTools.PGuiSize;
import de.p2tools.p2Lib.icon.GetIcon;
import de.p2tools.p2Lib.tools.PException;
import de.p2tools.p2Lib.tools.log.PLog;
import de.p2tools.p2radio.controller.config.ProgData;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.nio.file.Path;


public class SmallRadioDialog {
    private Scene scene = null;
    private Stage stage = null;
    private VBox vBoxComplete = new VBox(0);
    private VBox vBoxCenter = new VBox(10);
    private HBox hBoxBottom = new HBox(0);

    private final StringProperty sizeConfiguration;
    private final String title;
    private final Stage ownerForCenteringDialog;

    private double stageWidth = 0;
    private double stageHeight = 0;

    SmallRadioDialog(Stage ownerForCenteringDialog, StringProperty sizeConfiguration, String title) {
        this.ownerForCenteringDialog = ownerForCenteringDialog;
        this.sizeConfiguration = sizeConfiguration;
        this.title = title;
    }


    void init() {
        try {
            createNewScene();
            if (scene == null) {
                PException.throwPException(912012458, "no scene");
            }

            updateCss();
            stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(true);
            stage.setScene(scene);
            stage.setTitle(title);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest(e -> {
                e.consume();
                close();
            });
            scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    close();
                }
            });

            GetIcon.addWindowP2Icon(stage);

            hBoxBottom.getStyleClass().add("extra-pane");
            hBoxBottom.setPadding(new Insets(15, 15, 15, 15));
            hBoxBottom.setSpacing(20);
            hBoxBottom.setAlignment(Pos.CENTER_LEFT);

            VBox.setVgrow(vBoxCenter, Priority.ALWAYS);
            vBoxComplete.getChildren().addAll(vBoxCenter, hBoxBottom);

            make();
            if (sizeConfiguration.get().isEmpty()) {
                scene.getWindow().sizeToScene();
            }

            showDialog();
        } catch (final Exception exc) {
            PLog.errorLog(858484821, exc);
        }
    }

    private void updateCss() {
        P2LibInit.addP2LibCssToScene(scene);

        if (P2LibConst.styleFile != null && !P2LibConst.styleFile.isEmpty() && scene != null) {
            final Path path = Path.of(P2LibConst.styleFile);
            IoReadWriteStyle.readStyle(path, scene);
        }
    }

    private void createNewScene() {
        int w = PGuiSize.getWidth(sizeConfiguration);
        int h = PGuiSize.getHeight(sizeConfiguration);
        if (w > 0 && h > 0) {
            this.scene = new Scene(vBoxComplete, w, h);
        } else {
            this.scene = new Scene(vBoxComplete, 750, 300);
        }
    }

    public void hide() {
        // close/hide are the same
        close();
    }

    public void close() {
        //bei wiederkehrenden Dialogen: die pos/size merken
        stageWidth = stage.getWidth();
        stageHeight = stage.getHeight();

        if (sizeConfiguration != null) {
            PGuiSize.getSizeScene(sizeConfiguration, stage);
        }
        ProgData.getInstance().favouriteFilterController.resetFilter();
        stage.close();
    }

    public void showDialog() {
        if (stageHeight > 0 && stageWidth > 0) {
            //bei wiederkehrenden Dialogen die pos/size setzen
            stage.setHeight(stageHeight);
            stage.setWidth(stageWidth);
        }

        if (!PGuiSize.setPos(sizeConfiguration, stage)) {
            if (ownerForCenteringDialog == null) {
                PDialogFactory.setInCenterOfScreen(stage);
            } else {
                PDialogFactory.setInFrontOfPrimaryStage(ownerForCenteringDialog, stage);
            }
        }

        stage.showAndWait();
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isShowing() {
        return stage.isShowing();
    }

    public VBox getVBoxCenter() {
        return vBoxCenter;
    }

    public HBox getHBoxBottom() {
        return hBoxBottom;
    }

    public void showStage() {
        stage.show();
    }

    public void closeStage() {
        stage.close();
    }

    protected void make() {
    }
}
