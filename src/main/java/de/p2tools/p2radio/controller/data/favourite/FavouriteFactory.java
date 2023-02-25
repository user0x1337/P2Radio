/*
 * P2tools Copyright (C) 2022 W. Xaver W.Xaver[at]googlemail.com
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


package de.p2tools.p2radio.controller.data.favourite;

import de.p2tools.p2lib.P2LibConst;
import de.p2tools.p2lib.alert.PAlert;
import de.p2tools.p2lib.tools.date.PLDateFactory;
import de.p2tools.p2radio.controller.config.ProgData;
import de.p2tools.p2radio.controller.data.station.StationData;
import de.p2tools.p2radio.controller.data.station.StationListFactory;
import de.p2tools.p2radio.gui.dialog.FavouriteAddOwnDialogController;
import de.p2tools.p2radio.gui.dialog.FavouriteEditDialogController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class FavouriteFactory {
    private FavouriteFactory() {
    }

    public static void favouriteStationList() {
        final ArrayList<StationData> list = ProgData.getInstance().stationGuiPack.getStationGuiController().getSelList();
        favouriteStation(list);
    }

    public static void favouriteStation(StationData station) {
        ArrayList<StationData> list = new ArrayList<>();
        list.add(station);
        favouriteStation(list);
    }

    private static void favouriteStation(ArrayList<StationData> list) {
        if (list.isEmpty()) {
            return;
        }

        ProgData progData = ProgData.getInstance();
        ArrayList<StationData> addList = new ArrayList<>();

        for (final StationData station : list) {
            // erst mal schauen obs den schon gibt
            StationData stationData = progData.favouriteList.getUrlStation(station.getStationUrl());
            if (stationData == null) {
                addList.add(station);
            } else {
                // dann ist der Sender schon in der Liste
                if (list.size() <= 1) {
                    PAlert.BUTTON answer = PAlert.showAlert_yes_no("Anlegen?", "Nochmal anlegen?",
                            "Sender existiert bereits:" + P2LibConst.LINE_SEPARATORx2 +
                                    station.getCountry() + P2LibConst.LINE_SEPARATORx2 +
                                    "Nochmal anlegen?");
                    switch (answer) {
                        case NO:
                            // alles Abbrechen
                            return;
                        case YES:
                            addList.add(station);
                            break;
                    }

                } else {
                    PAlert.BUTTON answer = PAlert.showAlert_yes_no_cancel("Anlegen?", "Nochmal anlegen?",
                            "Sender existiert bereits:" + P2LibConst.LINE_SEPARATORx2 +
                                    station.getCountry() + P2LibConst.LINE_SEPARATORx2 +
                                    "Nochmal anlegen (Ja / Nein)?" + P2LibConst.LINE_SEPARATOR +
                                    "Oder alles Abbrechen?");
                    switch (answer) {
                        case CANCEL:
                            // alles Abbrechen
                            return;
                        case NO:
                            continue;
                        case YES:
                            addList.add(station);
                            break;
                    }
                }
            }
        }
        if (!addList.isEmpty()) {
            ArrayList<StationData> newFavourites = new ArrayList<>();
            addList.stream().forEach(stationData -> {
                StationData newStationData = new StationData(stationData, "");
                newFavourites.add(newStationData);
            });

            FavouriteEditDialogController favouriteEditDialogController =
                    new FavouriteEditDialogController(progData, newFavourites);

            if (favouriteEditDialogController.isOk()) {
                newFavourites.stream().forEach(stationData -> {
                    stationData.setFavourite(true);
                    progData.favouriteList.addAll(stationData);
                });

                //Favoriten markieren und Filter anstoßen
                addList.stream().forEach(stationData -> {
                    stationData.setFavourite(true);
                });
                progData.stationListBlackFiltered.triggerFilter();
            }
        }
    }

    public static void addOwnStationAsFavourite() {
        StationData stationData = new StationData();
        stationData.setOwn(true);
        stationData.setStationDate(PLDateFactory.toString(LocalDate.now()));

        FavouriteAddOwnDialogController favouriteEditDialogController =
                new FavouriteAddOwnDialogController(ProgData.getInstance(), stationData);

        if (favouriteEditDialogController.isOk()) {
            ProgData.getInstance().favouriteList.add(stationData);
            ProgData.getInstance().collectionList.updateNames();//könnte ja geändert sein
        }
    }

    public static void changeFavourite(StationData stationData) {
        ArrayList<StationData> list = new ArrayList<>();
        list.add(stationData);
        changeFavourite(list);
    }

    public static void changeFavourite(boolean allSel) {
        ArrayList<StationData> list = new ArrayList<>();
        if (allSel) {
            list.addAll(ProgData.getInstance().favouriteGuiPack.getFavouriteGuiController().getSelList());
        } else {
            final Optional<StationData> favourite = ProgData.getInstance().favouriteGuiPack.getFavouriteGuiController().getSel();
            if (favourite.isPresent()) {
                list.add(favourite.get());
            }
        }
        changeFavourite(list);
    }

    private static void changeFavourite(ArrayList<StationData> list) {
        ArrayList<StationData> listCopy = new ArrayList<>();
        if (list.isEmpty()) {
            return;
        }
        list.stream().forEach(f -> {
            StationData favouriteCopy = f.getCopy();
            listCopy.add(favouriteCopy);
        });

        FavouriteEditDialogController favouriteEditDialogController =
                new FavouriteEditDialogController(ProgData.getInstance(), listCopy);

        if (favouriteEditDialogController.isOk()) {
            for (int i = 0; i < listCopy.size(); ++i) {
                final StationData f, fCopy;
                f = list.get(i);
                fCopy = listCopy.get(i);
                f.copyToMe(fCopy);
            }
            ProgData.getInstance().collectionList.updateNames();//könnte ja geändert sein
        }
    }

    public static void deleteFavourite(StationData stationData) {
        if (PAlert.showAlert_yes_no(ProgData.getInstance().primaryStage, "Favoriten löschen?", "Favoriten löschen?",
                "Soll der Favorite gelöscht werden?").equals(PAlert.BUTTON.YES)) {
            ProgData.getInstance().favouriteList.remove(stationData);
            StationListFactory.findAndMarkFavouriteStations(ProgData.getInstance());
        }
    }

    public static void deleteFavourite(boolean all) {
        if (all) {
            final ArrayList<StationData> list = ProgData.getInstance().favouriteGuiPack.getFavouriteGuiController().getSelList();
            if (list.isEmpty()) {
                return;
            }

            final String text;
            if (list.size() == 1) {
                text = "Soll der Favorit gelöscht werden?";
            } else {
                text = "Sollen die Favoriten gelöscht werden?";
            }
            if (PAlert.showAlert_yes_no(ProgData.getInstance().primaryStage, "Favoriten löschen?", "Favoriten löschen?", text)
                    .equals(PAlert.BUTTON.YES)) {
                ProgData.getInstance().favouriteList.removeAll(list);
                StationListFactory.findAndMarkFavouriteStations(ProgData.getInstance());
            }

        } else {
            final Optional<StationData> favourite = ProgData.getInstance().favouriteGuiPack.getFavouriteGuiController().getSel();
            if (favourite.isPresent()) {
                FavouriteFactory.deleteFavourite(favourite.get());
            }
        }
    }

    public static synchronized int countStartedAndRunningFavourites() {
        //es wird nach gestarteten und laufenden Favoriten gesucht
        int ret = 0;
        for (final StationData stationData : ProgData.getInstance().favouriteList) {
            if (stationData.getStart() != null &&
                    (stationData.getStart().getStartStatus().isStarted() || stationData.getStart().getStartStatus().isStateStartedRun())) {
                ++ret;
            }
        }
        return ret;
    }
}
