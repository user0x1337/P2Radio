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

package de.p2tools.p2radio.gui.tools.table;

import de.p2tools.p2Lib.guiTools.PCheckBoxCell;
import de.p2tools.p2Lib.tools.GermanStringIntSorter;
import de.p2tools.p2Lib.tools.date.PDate;
import de.p2tools.p2Lib.tools.date.PLocalDate;
import de.p2tools.p2radio.controller.data.playable.PlayableXml;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TablePlayable<T> extends TableView<T> {

    Table.TABLE_ENUM table_enum;

    public TablePlayable(Table.TABLE_ENUM table_enum) {
        super();
        this.table_enum = table_enum;

        initFileRunnerColumn();
    }

    public Table.TABLE_ENUM getETable() {
        return table_enum;
    }

    public void resetTable() {
        initFileRunnerColumn();
        Table.resetTable(this);
    }

    private void initFileRunnerColumn() {
        getColumns().clear();

        setTableMenuButtonVisible(true);
        setEditable(false);
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        final GermanStringIntSorter sorter = GermanStringIntSorter.getInstance();

        final TableColumn<T, Integer> nrColumn = new TableColumn<>(PlayableXml.STATION_PROP_NO);
        nrColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        nrColumn.setCellFactory(new CellNo().cellFactoryNo);
        nrColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<T, Integer> senderNoColumn = new TableColumn<>(PlayableXml.STATION_PROP_STATION_NO);
        senderNoColumn.setCellValueFactory(new PropertyValueFactory<>("stationNo"));
        senderNoColumn.setCellFactory(new CellNo().cellFactoryNo);
        senderNoColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<T, String> senderColumn = new TableColumn<>(PlayableXml.STATION_PROP_STATION_NAME);
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("stationName"));
        senderColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<T, String> collectionColumn = new TableColumn<>(PlayableXml.STATION_PROP_COLLECTION);
        collectionColumn.setCellValueFactory(new PropertyValueFactory<>("collectionName"));
        collectionColumn.getStyleClass().add("alignCenterLeft");
        collectionColumn.setComparator(sorter);

        final TableColumn<T, Integer> startColumn = new TableColumn<>("");
        if (this.table_enum.equals(Table.TABLE_ENUM.STATION)) {
            startColumn.setCellFactory(new CellStartStation().cellFactoryStart);
        } else if (this.table_enum.equals(Table.TABLE_ENUM.FAVOURITE)) {
            startColumn.setCellFactory(new CellStartFavourite().cellFactoryButton);
        } else if (this.table_enum.equals(Table.TABLE_ENUM.LAST_PLAYED)) {
            startColumn.setCellFactory(new CellStartLastPlayed().cellFactoryButton);
        } else {
            //dann smallRadio
            startColumn.setCellFactory(new CellStartSmallRadio().cellFactoryButton);
        }
        startColumn.getStyleClass().add("alignCenter");

        final TableColumn<T, Integer> gradeColumn = new TableColumn<>(PlayableXml.STATION_PROP_GRADE);
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        gradeColumn.setCellFactory(new CellGrade().cellFactoryGrade);

        final TableColumn<T, Integer> clickCountColumn = new TableColumn<>(PlayableXml.STATION_PROP_CLICK_COUNT);
        clickCountColumn.setCellValueFactory(new PropertyValueFactory<>("clickCount"));
        clickCountColumn.getStyleClass().add("alignCenter");

        final TableColumn<T, Boolean> clickTrendColumn = new TableColumn<>(PlayableXml.STATION_PROP_CLICK_TREND);
        clickTrendColumn.setCellValueFactory(new PropertyValueFactory<>("clickTrend"));
        clickTrendColumn.getStyleClass().add("alignCenterRightPadding_10");

        final TableColumn<T, String> genreColumn = new TableColumn<>(PlayableXml.STATION_PROP_GENRE);
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        genreColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<T, PDate> codecColumn = new TableColumn<>(PlayableXml.STATION_PROP_CODEC);
        codecColumn.setCellValueFactory(new PropertyValueFactory<>("codec"));
        codecColumn.getStyleClass().add("alignCenter");

        final TableColumn<T, Integer> bitrateColumn = new TableColumn<>(PlayableXml.STATION_PROP_BITRATE);
        bitrateColumn.setCellValueFactory(new PropertyValueFactory<>("bitrate"));
//        bitrateColumn.setCellFactory(cellFactoryBitrate);
        bitrateColumn.getStyleClass().add("alignCenterRightPadding_10");

        final TableColumn<T, Integer> ownColumn = new TableColumn<>(PlayableXml.STATION_PROP_OWN);
        ownColumn.setCellValueFactory(new PropertyValueFactory<>("own"));
        ownColumn.setCellFactory(new PCheckBoxCell().cellFactoryBool);
        ownColumn.getStyleClass().add("alignCenter");

        final TableColumn<T, String> stateColumn = new TableColumn<>(PlayableXml.STATION_PROP_STATE);
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        stateColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<T, PDate> countryColumn = new TableColumn<>(PlayableXml.STATION_PROP_COUNTRY);
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        countryColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<T, PDate> countryCodeColumn = new TableColumn<>(PlayableXml.STATION_PROP_COUNTRY_CODE);
        countryCodeColumn.setCellValueFactory(new PropertyValueFactory<>("countryCode"));
        countryCodeColumn.getStyleClass().add("alignCenter");

        final TableColumn<T, String> languageColumn = new TableColumn<>(PlayableXml.STATION_PROP_LANGUAGE);
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
        languageColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<T, Integer> votesColumn = new TableColumn<>(PlayableXml.STATION_PROP_VOTES);
        votesColumn.setCellValueFactory(new PropertyValueFactory<>("votes"));
        votesColumn.getStyleClass().add("alignCenterRightPadding_10");

        final TableColumn<T, PLocalDate> datumColumn = new TableColumn<>(PlayableXml.STATION_PROP_DATE);
        datumColumn.setCellValueFactory(new PropertyValueFactory<>("stationDate"));
        datumColumn.getStyleClass().add("alignCenter");

        final TableColumn<T, String> websiteColumn = new TableColumn<>(PlayableXml.STATION_PROP_WEBSITE);
        websiteColumn.setCellValueFactory(new PropertyValueFactory<>("website"));
        websiteColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<T, String> urlColumn = new TableColumn<>(PlayableXml.STATION_PROP_URL);
        urlColumn.setCellValueFactory(new PropertyValueFactory<>("stationUrl"));
        urlColumn.getStyleClass().add("alignCenterLeft");

        nrColumn.setPrefWidth(50);
        senderNoColumn.setPrefWidth(70);
        senderColumn.setPrefWidth(80);
        genreColumn.setPrefWidth(180);

        setRowFactory(tv -> new TableRowPlayable<>());
        getColumns().addAll(
                nrColumn, senderNoColumn,
                senderColumn, collectionColumn, startColumn, gradeColumn,
                clickCountColumn, clickTrendColumn, genreColumn,
                codecColumn, bitrateColumn, ownColumn, stateColumn, countryColumn,
                countryCodeColumn, languageColumn, votesColumn,
                datumColumn, websiteColumn, urlColumn);
    }
}
