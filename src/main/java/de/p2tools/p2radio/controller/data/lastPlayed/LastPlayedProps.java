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

package de.p2tools.p2radio.controller.data.lastPlayed;

import de.p2tools.p2Lib.configFile.config.*;
import de.p2tools.p2Lib.tools.date.PLocalDate;
import de.p2tools.p2Lib.tools.date.PLocalDateProperty;
import de.p2tools.p2radio.controller.data.favourite.FavouriteConstants;
import de.p2tools.p2radio.controller.data.favourite.FavouriteFieldNames;
import de.p2tools.p2radio.tools.Data;
import javafx.beans.property.*;

import java.util.ArrayList;

public class LastPlayedProps extends LastPlayedXml {

    private final IntegerProperty no = new SimpleIntegerProperty(FavouriteConstants.FAVOURITE_NUMBER_NOT_STARTED);
    private final IntegerProperty stationNo = new SimpleIntegerProperty(FavouriteConstants.STATION_NUMBER_NOT_FOUND);

    private final StringProperty stationName = new SimpleStringProperty("");
    private final StringProperty genre = new SimpleStringProperty("");
    private final StringProperty codec = new SimpleStringProperty("");
    private final IntegerProperty bitrate = new SimpleIntegerProperty(0);
    private final IntegerProperty grade = new SimpleIntegerProperty(0);
    private final BooleanProperty own = new SimpleBooleanProperty(false);
    private final IntegerProperty clickCount = new SimpleIntegerProperty();
    private final StringProperty country = new SimpleStringProperty("");
    private final StringProperty language = new SimpleStringProperty("");
    private final StringProperty countryCode = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");

    private final StringProperty stationUrl = new SimpleStringProperty("");
    private final StringProperty website = new SimpleStringProperty("");

    private final PLocalDateProperty stationDate = new PLocalDateProperty();

    private boolean favouriteUrl = false;

    @Override
    public Config[] getConfigsArr() {
        ArrayList<Config> list = new ArrayList<>();
        list.add(new ConfigIntPropExtra("no", FavouriteFieldNames.FAVOURITE_NO, no));
        list.add(new ConfigIntPropExtra("stationNo", FavouriteFieldNames.FAVOURITE_STATION_NO, stationNo));
        list.add(new ConfigStringPropExtra("station", FavouriteFieldNames.FAVOURITE_STATION, stationName));
        list.add(new ConfigStringPropExtra("genre", FavouriteFieldNames.FAVOURITE_GENRE, genre));
        list.add(new ConfigStringPropExtra("codec", FavouriteFieldNames.FAVOURITE_GENRE, codec));
        list.add(new ConfigIntPropExtra("bitrate", FavouriteFieldNames.FAVOURITE_BITRATE, bitrate));
        list.add(new ConfigIntPropExtra("grade", FavouriteFieldNames.FAVOURITE_GRADE, grade));
        list.add(new ConfigBoolPropExtra("own", FavouriteFieldNames.FAVOURITE_OWN, own));
        list.add(new ConfigIntPropExtra("clickCount", FavouriteFieldNames.FAVOURITE_STATE, clickCount));

        list.add(new ConfigStringPropExtra("country", FavouriteFieldNames.FAVOURITE_COUNTRY, country));
        list.add(new ConfigStringPropExtra("countryCode", FavouriteFieldNames.FAVOURITE_COUNTRY, countryCode));
        list.add(new ConfigStringPropExtra("language", FavouriteFieldNames.FAVOURITE_COUNTRY, language));
        list.add(new ConfigStringPropExtra("description", FavouriteFieldNames.FAVOURITE_DESCRIPTION, description));

        list.add(new ConfigStringPropExtra("url", FavouriteFieldNames.FAVOURITE_URL, stationUrl));
        list.add(new ConfigStringPropExtra("website", FavouriteFieldNames.FAVOURITE_URL, website));
        list.add(new ConfigLocalDatePropExtra("stationDate", FavouriteFieldNames.FAVOURITE_DATE, stationDate));

        return list.toArray(new Config[]{});
    }

    @Override
    public String getTag() {
        return TAG;
    }


    public int getNo() {
        return no.get();
    }

    public void setNo(int no) {
        this.no.set(no);
    }

    public IntegerProperty noProperty() {
        return no;
    }

    public int getStationNo() {
        return stationNo.get();
    }

    public void setStationNo(int stationNo) {
        this.stationNo.set(stationNo);
    }

    public IntegerProperty stationNoProperty() {
        return stationNo;
    }

    public String getStationName() {
        return stationName.get();
    }

    public void setStationName(String stationName) {
        this.stationName.set(stationName);
    }

    public StringProperty stationNameProperty() {
        return stationName;
    }

    public String getGenre() {
        return genre.get();
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public String getCodec() {
        return codec.get();
    }

    public void setCodec(String codec) {
        this.codec.set(codec);
    }

    public StringProperty codecProperty() {
        return codec;
    }

    public int getBitrate() {
        return bitrate.get();
    }

    public void setBitrate(int bitrate) {
        this.bitrate.set(bitrate);
    }

    public IntegerProperty bitrateProperty() {
        return bitrate;
    }

    public int getGrade() {
        return grade.get();
    }

    public void setGrade(int grade) {
        this.grade.set(grade);
    }

    public IntegerProperty gradeProperty() {
        return grade;
    }

    public boolean isOwn() {
        return own.get();
    }

    public void setOwn(boolean own) {
        this.own.set(own);
    }

    public BooleanProperty ownProperty() {
        return own;
    }

    public int getClickCount() {
        return clickCount.get();
    }

    public void setClickCount(int clickCount) {
        this.clickCount.set(clickCount);
    }

    public IntegerProperty clickCountProperty() {
        return clickCount;
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public StringProperty countryProperty() {
        return country;
    }

    public String getCountryCode() {
        return countryCode.get();
    }

    public void setCountryCode(String countryCode) {
        this.countryCode.set(countryCode);
    }

    public StringProperty countryCodeProperty() {
        return countryCode;
    }

    public String getLanguage() {
        return language.get();
    }

    public void setLanguage(String language) {
        this.language.set(language);
    }

    public StringProperty languageProperty() {
        return language;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getStationUrl() {
        return stationUrl.get();
    }

    public void setStationUrl(String stationUrl) {
        this.stationUrl.set(stationUrl);
    }

    public StringProperty stationUrlProperty() {
        return stationUrl;
    }

    public String getWebsite() {
        return website.get();
    }

    public void setWebsite(String website) {
        this.website.set(website);
    }

    public StringProperty websiteProperty() {
        return website;
    }

    public PLocalDate getStationDate() {
        return stationDate.get();
    }

    public void setStationDate(PLocalDate stationDate) {
        this.stationDate.setValue(stationDate);
    }

    public void setStationDate(String date) {
        this.stationDate.setPLocalDate(date);
    }

    public PLocalDateProperty stationDateProperty() {
        return stationDate;
    }

    public boolean isFavouriteUrl() {
        return favouriteUrl;
    }

    public void setFavouriteUrl(boolean favouriteUrl) {
        this.favouriteUrl = favouriteUrl;
    }

    public int compareTo(LastPlayedProps arg0) {
        int ret;
        if ((ret = Data.sorter.compare(getStationName(), arg0.getStationName())) == 0) {
            return getStationUrl().compareTo(arg0.getStationUrl());
        }
        return ret;
    }
}
