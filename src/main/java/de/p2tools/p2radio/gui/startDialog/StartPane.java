/*
 * Copyright (C) 2017 W. Xaver W.Xaver[at]googlemail.com
 * https://www.p2tools.de
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

package de.p2tools.p2radio.gui.startDialog;

import de.p2tools.p2Lib.P2LibConst;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class StartPane {
    private final Stage stage;

    public StartPane(Stage stage) {
        this.stage = stage;
    }

    public void close() {
    }

    public TitledPane makeStart1() {

        HBox hBox = new HBox();
        hBox.setSpacing(25);
        hBox.setPadding(new Insets(20));

        ImageView iv = new ImageView();
        Image im = getHelpScreen1();
        iv.setSmooth(true);
        iv.setImage(im);

        hBox.getChildren().addAll(iv);
        Label text = new Label("1) Hier kann die Liste der" + P2LibConst.LINE_SEPARATOR +
                "Sender gefiltert werden." +

                P2LibConst.LINE_SEPARATORx2 +
                "2) Die Ansicht der Sender und der Favoriten" + P2LibConst.LINE_SEPARATOR +
                "wird hier umgeschaltet." +

                P2LibConst.LINE_SEPARATORx2 +
                "3) Hier befinden sich" + P2LibConst.LINE_SEPARATOR +
                "die Programmeinstellungen." +

                P2LibConst.LINE_SEPARATORx2 +
                "4) Mit dem Pluszeichen können" + P2LibConst.LINE_SEPARATOR +
                "Spalten in der Tabelle" + P2LibConst.LINE_SEPARATOR +
                "ein- und ausgeblendet werden." +

                P2LibConst.LINE_SEPARATORx2 +
                "5) Hier können Sender gestartet" + P2LibConst.LINE_SEPARATOR +
                "und verarbeitet werden." +

                P2LibConst.LINE_SEPARATORx2 +
                "6) Damit können Sender gestartet" + P2LibConst.LINE_SEPARATOR +
                "und gestoppt werden." +

                P2LibConst.LINE_SEPARATORx2 +
                "7) Damit können Sender zu" + P2LibConst.LINE_SEPARATOR +
                "den Favoriten hinzugefügt werden.");

        hBox.getChildren().add(text);

        TitledPane tpConfig = new TitledPane("Infos zur Programmoberfläche", hBox);
        return tpConfig;
    }

    public TitledPane makeStart2() {

        HBox hBox = new HBox();
        hBox.setSpacing(25);
        hBox.setPadding(new Insets(20));

        ImageView iv = new ImageView();
        Image im = getHelpScreen2();
        iv.setSmooth(true);
        iv.setImage(im);

        hBox.getChildren().addAll(iv);

        Label text = new Label("1) Damit werden nur die Favoriten" + P2LibConst.LINE_SEPARATOR +
                "der ausgewählten Sammlung angezeigt." +
                P2LibConst.LINE_SEPARATORx2 +

                "2) In dem Menü können die Favoriten" + P2LibConst.LINE_SEPARATOR +
                "bearbeitet werden." +
                P2LibConst.LINE_SEPARATORx2 +

                "3) Damit werden die Favoriten" + P2LibConst.LINE_SEPARATOR +
                "gestartet und gestoppt" +
                P2LibConst.LINE_SEPARATORx2 +

                "4) Hier können die Favoriten" + P2LibConst.LINE_SEPARATOR +
                "geändert oder gelöscht (\"X\") werden.");
        hBox.getChildren().add(text);

        TitledPane tpConfig = new TitledPane("Infos zum Sender-Filter", hBox);
        return tpConfig;
    }

    private javafx.scene.image.Image getHelpScreen1() {
        final String path = "/de/p2tools/p2radio/res/p2Radio-startpage-1.jpg";
        return new javafx.scene.image.Image(path, 600,
                600,
                true, true);
    }

    private javafx.scene.image.Image getHelpScreen2() {
        final String path = "/de/p2tools/p2radio/res/p2Radio-startpage-2.jpg";
        return new javafx.scene.image.Image(path, 600,
                600,
                true, true);
    }
}
