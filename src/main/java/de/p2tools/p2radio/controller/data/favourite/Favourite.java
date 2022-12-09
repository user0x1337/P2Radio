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

package de.p2tools.p2radio.controller.data.favourite;

import de.p2tools.p2Lib.configFile.config.Config;
import de.p2tools.p2Lib.tools.date.PLocalDate;
import de.p2tools.p2radio.controller.config.ProgConst;
import de.p2tools.p2radio.controller.data.playable.Playable;
import de.p2tools.p2radio.controller.data.playable.PlayableProperty;
import de.p2tools.p2radio.controller.data.start.Start;
import de.p2tools.p2radio.controller.data.station.Station;

public final class Favourite extends PlayableProperty implements Playable {

    private Start start = null;

    public Favourite() {
    }

    public Favourite(Station station, String collectionName) {
        setStation(station);
        setCollectionName(collectionName);
        setStationUrl(station.getStationUrl());
    }

    //==============================================
    // Get/Set
    //==============================================
    public Start getStart() {
        return start;
    }

    public void setStart(Start start) {
        this.start = start;
    }

    public Favourite getCopy() {
        final Favourite ret = new Favourite();
        ret.start = start;

        Config[] configs = getConfigsArr();
        Config[] configsCopy = ret.getConfigsArr();
        for (int i = 0; i < configs.length; ++i) {
            configsCopy[i].setActValue(configs[i].getActValueString());
        }
        return ret;
    }

    public void copyToMe(Favourite favourite) {
        start = favourite.start;

        Config[] configs = favourite.getConfigsArr();
        Config[] configsCopy = getConfigsArr();
        for (int i = 0; i < configs.length; ++i) {
            configsCopy[i].setActValue(configs[i].getActValueString());
        }
    }

    public boolean isStation() {
        return false;
    }

    public void setStation(Playable station) {
        if (station == null) {
            // bei gespeicherten Sendern kann es den Sender nicht mehr geben
            setStationNo(ProgConst.NUMBER_DONT_USED);
            return;
        }

        setStationNo(station.getNo());
        setStationName(station.getStationName());
        setGenre(station.getGenre());
        setCodec(station.getCodec());
        setBitrate(station.getBitrate());
        setCountry(station.getCountry());
        setCountryCode(station.getCountryCode());
        setLanguage(station.getLanguage());
        setWebsite(station.getWebsite());
        setStationUrl(station.getStationUrl());
        setStationDate(station.getStationDate());
    }

    public void setStation(Station station) {
        if (station == null) {
            // bei gespeicherten Sendern kann es den Sender nicht mehr geben
            setStationNo(ProgConst.NUMBER_DONT_USED);
            return;
        }

        setStationNo(station.getNo());
        setStationName(station.getStationName());
        setGenre(station.getGenre());
        setCodec(station.getCodec());
        setBitrate(station.getBitrate());
        setCountry(station.getCountry());
        setCountryCode(station.getCountryCode());
        setLanguage(station.getLanguage());
        setWebsite(station.getWebsite());
        setStationUrl(station.getStationUrl());
        setStationDate(station.getStationDate());
    }

    public boolean isFavourite() {
        return true;
    }

    public void setFavourite(Favourite favourite) {
        if (favourite == null) {
            // bei gespeicherten Sendern kann es den Sender nicht mehr geben
            setStationNo(ProgConst.NUMBER_DONT_USED);
            return;
        }

        setStationNo(favourite.getNo());
        setStationName(favourite.getStationName());
        setGenre(favourite.getGenre());
        setCodec(favourite.getCodec());
        setBitrate(favourite.getBitrate());
        setCountry(favourite.getCountry());
        setCountryCode(favourite.getCountryCode());
        setLanguage(favourite.getLanguage());
        setWebsite(favourite.getWebsite());
        setStationUrl(favourite.getStationUrl());
        setStationDate(new PLocalDate().getDateTime(PLocalDate.FORMAT_dd_MM_yyyy));
    }

    public boolean isLastPlayed() {
        return false;
    }

}
