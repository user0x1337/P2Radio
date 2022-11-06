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

package de.p2tools.p2radio.gui;

import de.p2tools.p2radio.controller.config.ProgData;
import de.p2tools.p2radio.controller.data.SetDataList;
import de.p2tools.p2radio.controller.data.favourite.Favourite;
import de.p2tools.p2radio.controller.data.favourite.FavouriteFactory;
import de.p2tools.p2radio.gui.tools.table.TableFavourite;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import java.util.Optional;

public class FavouriteGuiTableContextMenu {

    private final ProgData progData;
    private final FavouriteGuiController favouriteGuiController;
    private final TableFavourite tableView;

    public FavouriteGuiTableContextMenu(ProgData progData, FavouriteGuiController favouriteGuiController, TableFavourite tableView) {
        this.progData = progData;
        this.favouriteGuiController = favouriteGuiController;
        this.tableView = tableView;
    }

    public ContextMenu getContextMenu(Favourite favourite) {
        final ContextMenu contextMenu = new ContextMenu();
        getMenu(contextMenu, favourite);
        return contextMenu;
    }

    private void getMenu(ContextMenu contextMenu, Favourite favourite) {
        MenuItem miStart = new MenuItem("Sender starten");
        miStart.setDisable(favourite == null);
        miStart.setOnAction(a -> favouriteGuiController.playStation());
        contextMenu.getItems().addAll(miStart);

        Menu mStartStation = startStationWithSet(favourite); // Sender mit Set starten
        if (mStartStation != null) {
            mStartStation.setDisable(favourite == null);
            contextMenu.getItems().add(mStartStation);
        }

        MenuItem miStop = new MenuItem("Sender stoppen");
        miStop.setOnAction(a -> favouriteGuiController.stopStation(false));
        MenuItem miStopAll = new MenuItem("alle Sender stoppen");
        miStopAll.setOnAction(a -> favouriteGuiController.stopStation(true /* alle */));
        MenuItem miCopyUrl = new MenuItem("Sender (URL) kopieren");
        miCopyUrl.setOnAction(a -> favouriteGuiController.copyUrl());

        MenuItem miChange = new MenuItem("Favorit ändern");
        miChange.setOnAction(a -> FavouriteFactory.changeFavourite(false));
        MenuItem miRemove = new MenuItem("Favoriten löschen");
        miRemove.setOnAction(a -> FavouriteFactory.deleteFavourite(false));

        miStop.setDisable(favourite == null);
        miStopAll.setDisable(favourite == null);
        miCopyUrl.setDisable(favourite == null);
        miChange.setDisable(favourite == null);
        miRemove.setDisable(favourite == null);

        contextMenu.getItems().addAll(miStop, miStopAll, miCopyUrl, miChange, miRemove);

        MenuItem resetTable = new MenuItem("Tabelle zurücksetzen");
        resetTable.setOnAction(a -> tableView.resetTable());

        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().addAll(resetTable);
    }

    private Menu startStationWithSet(Favourite station) {
        final SetDataList list = progData.setDataList.getSetDataListButton();
        if (!list.isEmpty()) {
            Menu submenuSet = new Menu("Sender mit Set abspielen");

            if (station == null) {
                submenuSet.setDisable(true);
                return submenuSet;
            }

            list.stream().forEach(setData -> {
                final MenuItem item = new MenuItem(setData.getVisibleName());
                item.setOnAction(event -> {
                    final Optional<Favourite> favourite = ProgData.getInstance().favouriteGuiController.getSel();
                    if (favourite.isPresent()) {
                        progData.startFactory.playFavourite(favourite.get(), setData);
                    }
                });
                submenuSet.getItems().add(item);
            });

            return submenuSet;
        }

        return null;
    }
}
