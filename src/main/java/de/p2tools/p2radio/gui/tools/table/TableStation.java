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

import de.p2tools.p2Lib.tools.date.PLocalDate;
import de.p2tools.p2radio.controller.config.ProgColorList;
import de.p2tools.p2radio.controller.config.ProgConfig;
import de.p2tools.p2radio.controller.config.ProgData;
import de.p2tools.p2radio.controller.data.ProgIcons;
import de.p2tools.p2radio.controller.data.playable.PlayableXml;
import de.p2tools.p2radio.controller.data.station.Station;
import de.p2tools.p2radio.controller.data.station.StationFactory;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class TableStation extends PTable<Station> {

    private final Callback<TableColumn<Station, Integer>, TableCell<Station, Integer>> cellFactoryBitrate
            = (final TableColumn<Station, Integer> param) -> {

        final TableCell<Station, Integer> cell = new TableCell<>() {

            @Override
            public void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                if (item == 0) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(null);
                    setText(item + "");
                }

            }
        };
        return cell;
    };
    private final ProgData progData;
    private final BooleanProperty geoMelden;
    private final BooleanProperty small;
    private final Callback<TableColumn<Station, String>, TableCell<Station, String>> cellFactoryStart
            = (final TableColumn<Station, String> param) -> {

        final TableCell<Station, String> cell = new TableCell<>() {

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                final HBox hbox = new HBox();
                hbox.setSpacing(4);
                hbox.setAlignment(Pos.CENTER);
                hbox.setPadding(new Insets(0, 2, 0, 2));

                Station station = getTableView().getItems().get(getIndex());
                final boolean playing = station.getStart() != null;
                final boolean error = station.getStart() != null && station.getStart().getStartStatus().isStateError();
                final boolean fav = station.isFavourite();

                if (playing) {
                    //stoppen
                    final Button btnPlay = new Button("");
                    btnPlay.getStyleClass().add("btnSmallRadio");
                    btnPlay.setTooltip(new Tooltip("Sender stoppen"));
                    btnPlay.setGraphic(ProgIcons.Icons.IMAGE_TABLE_STATION_STOP_PLAY.getImageView());
                    btnPlay.setOnAction((ActionEvent event) -> {
                        progData.startFactory.stopStation(station);
                        getTableView().getSelectionModel().clearSelection();
                        getTableView().getSelectionModel().select(getIndex());
                    });

                    if (small.get()) {
                        btnPlay.setMaxHeight(18);
                        btnPlay.setMinHeight(18);
                    }
                    hbox.getChildren().add(btnPlay);

//                } else if (set) {
//                    //läuft nix, mehre Sets
//                    final ComboBox<SetData> cboSet;
//                    cboSet = new ComboBox();
//                    cboSet.setMinWidth(60);
//                    cboSet.getStyleClass().add("cboTableMoreSets");
//                    cboSet.setTooltip(new Tooltip("Set zum Abspielen des Senders auswählen"));
////                    cboSet.getStyleClass().add("combo-box-icon");
//                    cboSet.getItems().addAll(progData.setDataList);
//                    cboSet.getSelectionModel().selectedItemProperty().addListener((v, ol, ne) -> {
//                        progData.startFactory.playStation(station, ne);
//                        getTableView().getSelectionModel().clearSelection();
//                        getTableView().getSelectionModel().select(getIndex());
//                    });
//
//                    if (small.get()) {
//                        cboSet.setMinHeight(18);
//                        cboSet.setMaxHeight(18);
//                    }
//                    hbox.getChildren().add(cboSet);

                } else {
                    //starten
                    final Button btnPlay = new Button("");
                    btnPlay.getStyleClass().add("btnSmallRadio");
                    btnPlay.setTooltip(new Tooltip("Sender abspielen"));
                    btnPlay.setGraphic(ProgIcons.Icons.IMAGE_TABLE_STATION_PLAY.getImageView());
                    btnPlay.setOnAction((ActionEvent event) -> {
                        progData.startFactory.playStation(station);
                        getTableView().getSelectionModel().clearSelection();
                        getTableView().getSelectionModel().select(getIndex());
                    });

                    if (small.get()) {
                        btnPlay.setMaxHeight(18);
                        btnPlay.setMinHeight(18);
                    }
                    hbox.getChildren().add(btnPlay);
                }

                final Button btnFavorite;
                btnFavorite = new Button("");
                btnFavorite.getStyleClass().add("btnSmallRadio");
                btnFavorite.setTooltip(new Tooltip("Sender als Favoriten sichern"));
                btnFavorite.setGraphic(ProgIcons.Icons.IMAGE_TABLE_STATION_SAVE.getImageView());
                btnFavorite.setOnAction(event -> {
                    StationFactory.favouriteStation(station);
                });

                if (small.get()) {
                    btnFavorite.setMaxHeight(18);
                    btnFavorite.setMinHeight(18);
                }
                hbox.getChildren().add(btnFavorite);

                setGraphic(hbox);
            }
        };
        return cell;
    };

    public TableStation(Table.TABLE_ENUM table_enum, ProgData progData) {
        super(table_enum);
        this.table_enum = table_enum;
        this.progData = progData;
        initFileRunnerColumn();

        geoMelden = ProgConfig.SYSTEM_MARK_GEO;
        small = ProgConfig.SYSTEM_SMALL_ROW_TABLE;
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


        final TableColumn<Station, Integer> nrColumn = new TableColumn<>(PlayableXml.STATION_PROP_STATION_NO);
        nrColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        nrColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<Station, String> nameColumn = new TableColumn<>(PlayableXml.STATION_PROP_STATION_NAME);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("stationName"));
        nameColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<Station, String> genreColumn = new TableColumn<>(PlayableXml.STATION_PROP_GENRE);
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        genreColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<Station, String> codecColumn = new TableColumn<>(PlayableXml.STATION_PROP_CODEC);
        codecColumn.setCellValueFactory(new PropertyValueFactory<>("codec"));
        codecColumn.getStyleClass().add("alignCenter");

        final TableColumn<Station, Integer> bitrateColumn = new TableColumn<>(PlayableXml.STATION_PROP_BITRATE);
        bitrateColumn.setCellValueFactory(new PropertyValueFactory<>("bitrateInt"));
        bitrateColumn.setCellFactory(cellFactoryBitrate);
        bitrateColumn.getStyleClass().add("alignCenterRightPadding_10");

        final TableColumn<Station, String> startColumn = new TableColumn<>("");
        startColumn.setCellFactory(cellFactoryStart);
        startColumn.getStyleClass().add("alignCenter");

        final TableColumn<Station, String> stateColumn = new TableColumn<>(PlayableXml.STATION_PROP_STATE);
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        stateColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<Station, String> countryColumn = new TableColumn<>(PlayableXml.STATION_PROP_COUNTRY);
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        countryColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<Station, String> countryCodeColumn = new TableColumn<>(PlayableXml.STATION_PROP_COUNTRY_CODE);
        countryCodeColumn.setCellValueFactory(new PropertyValueFactory<>("countryCode"));
        countryCodeColumn.getStyleClass().add("alignCenter");

        final TableColumn<Station, String> languageColumn = new TableColumn<>(PlayableXml.STATION_PROP_LANGUAGE);
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
        languageColumn.getStyleClass().add("alignCenter");

        final TableColumn<Station, Integer> votesColumn = new TableColumn<>(PlayableXml.STATION_PROP_VOTES);
        votesColumn.setCellValueFactory(new PropertyValueFactory<>("votes"));
        votesColumn.getStyleClass().add("alignCenterRightPadding_10");

        final TableColumn<Station, Integer> clickCountColumn = new TableColumn<>(PlayableXml.STATION_PROP_CLICK_COUNT);
        clickCountColumn.setCellValueFactory(new PropertyValueFactory<>("clickCount"));
        clickCountColumn.getStyleClass().add("alignCenterRightPadding_10");

        final TableColumn<Station, Boolean> clickTrendColumn = new TableColumn<>(PlayableXml.STATION_PROP_CLICK_TREND);
        clickTrendColumn.setCellValueFactory(new PropertyValueFactory<>("clickTrend"));
        clickTrendColumn.getStyleClass().add("alignCenterRightPadding_10");

        final TableColumn<Station, PLocalDate> dateColumn = new TableColumn<>(PlayableXml.STATION_PROP_DATE);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("stationDate"));
        dateColumn.getStyleClass().add("alignCenter");

        final TableColumn<Station, String> websiteColumn = new TableColumn<>(PlayableXml.STATION_PROP_WEBSITE);
        websiteColumn.setCellValueFactory(new PropertyValueFactory<>("website"));
        websiteColumn.getStyleClass().add("alignCenterLeft");

        final TableColumn<Station, String> urlColumn = new TableColumn<>(PlayableXml.STATION_PROP_URL);
        urlColumn.setCellValueFactory(new PropertyValueFactory<>("stationUrl"));
        urlColumn.getStyleClass().add("alignCenterLeft");

        nrColumn.setPrefWidth(50);
        nameColumn.setPrefWidth(150);
        genreColumn.setPrefWidth(150);
        codecColumn.setPrefWidth(80);

        addRowFact();

        getColumns().addAll(nrColumn, nameColumn, genreColumn, codecColumn, bitrateColumn, startColumn,
                stateColumn, countryColumn, countryCodeColumn, languageColumn, votesColumn,
                clickCountColumn, clickTrendColumn, dateColumn, websiteColumn, urlColumn/*, urlrColumn*/);
    }

    private void addRowFact() {
        setRowFactory(tableview -> new TableRow<>() {
            @Override
            public void updateItem(Station station, boolean empty) {
                super.updateItem(station, empty);
                setStyle("");
                for (int i = 0; i < getChildren().size(); i++) {
                    getChildren().get(i).setStyle("");
                }

                if (station != null && !empty) {
                    final boolean playing = station.getStart() != null;
                    final boolean error = station.getStart() != null && station.getStart().getStartStatus().isStateError();
                    final boolean fav = station.isFavourite();

                    if (station.getStart() != null && station.getStart().getStartStatus().isStateError()) {
                        Tooltip tooltip = new Tooltip();
                        tooltip.setText(station.getStart().getStartStatus().getErrorMessage());
                        setTooltip(tooltip);
                    }

                    if (error) {
                        if (ProgColorList.STATION_ERROR_BG.isUse()) {
                            setStyle(ProgColorList.STATION_ERROR_BG.getCssBackgroundAndSel());
                        }
                        if (ProgColorList.STATION_ERROR.isUse()) {
                            for (int i = 0; i < getChildren().size(); i++) {
                                getChildren().get(i).setStyle(ProgColorList.STATION_ERROR.getCssFont());
                            }
                        }

                    } else if (playing) {
                        if (ProgColorList.STATION_RUN_BG.isUse()) {
                            setStyle(ProgColorList.STATION_RUN_BG.getCssBackgroundAndSel());
                        }
                        if (ProgColorList.STATION_RUN.isUse()) {
                            for (int i = 0; i < getChildren().size(); i++) {
                                getChildren().get(i).setStyle(ProgColorList.STATION_RUN.getCssFont());
                            }
                        }

                    } else if (station.isNewStation()) {
                        // neue Sender
                        if (ProgColorList.STATION_NEW_BG.isUse()) {
                            setStyle(ProgColorList.STATION_NEW_BG.getCssBackgroundAndSel());
                        }
                        if (ProgColorList.STATION_NEW.isUse()) {
                            for (int i = 0; i < getChildren().size(); i++) {
                                getChildren().get(i).setStyle(ProgColorList.STATION_NEW.getCssFont());
                            }
                        }

                    } else if (fav) {
                        if (ProgColorList.STATION_FAVOURITE_BG.isUse()) {
                            setStyle(ProgColorList.STATION_FAVOURITE_BG.getCssBackgroundAndSel());
                        }
                        if (ProgColorList.STATION_FAVOURITE.isUse()) {
                            for (int i = 0; i < getChildren().size(); i++) {
                                getChildren().get(i).setStyle(ProgColorList.STATION_FAVOURITE.getCssFont());
                            }
                        }
                    }
                }
            }
        });
    }
}
