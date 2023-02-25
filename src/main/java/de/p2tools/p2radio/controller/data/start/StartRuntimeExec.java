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

package de.p2tools.p2radio.controller.data.start;

import de.p2tools.p2lib.tools.log.PLog;
import de.p2tools.p2radio.controller.starter.PlayerMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StartRuntimeExec {

    public static final String TRENNER_PROG_ARRAY = "<>";
    private static final int INPUT = 1;
    private static final int ERROR = 2;
    Thread clearIn;
    Thread clearOut;
    private Process process = null;

    private Start start = null;
    private final String strProgCall;
    private String[] arrProgCallArray = null;
    private String strProgCallArray = "";
    private PlayerMessage playerMessage = new PlayerMessage();

    public StartRuntimeExec(Start start) {
        this.start = start;
        this.strProgCall = start.getProgramCall();
        this.strProgCallArray = start.getProgramCallArray();

        arrProgCallArray = strProgCallArray.split(TRENNER_PROG_ARRAY);
        if (arrProgCallArray.length <= 1) {
            arrProgCallArray = null;
        }
    }

    public StartRuntimeExec(String p) {
        strProgCall = p;
    }

    //===================================
    // Public
    //===================================
    public Process exec() {
        try {
            if (arrProgCallArray != null) {
                PLog.sysLog("=====================");
                PLog.sysLog("Starte Array: ");
                PLog.sysLog(" -> " + strProgCallArray);
                PLog.sysLog("=====================");
                process = Runtime.getRuntime().exec(arrProgCallArray);
            } else {
                PLog.sysLog("=====================");
                PLog.sysLog("Starte nicht als Array:");
                PLog.sysLog(" -> " + strProgCall);
                PLog.sysLog("=====================");
                process = Runtime.getRuntime().exec(strProgCall);
            }

            clearIn = new Thread(new ClearInOut(INPUT, process));
            clearOut = new Thread(new ClearInOut(ERROR, process));

            clearIn.setName("exec-in");
            clearIn.start();

            clearOut.setName("exec-out");
            clearOut.start();
        } catch (final Exception ex) {
            PLog.errorLog(450028932, ex, "Fehler beim Starten");
        }
        return process;
    }

    //===================================
    // Private
    //===================================
    private class ClearInOut implements Runnable {

        private final int art;
        private BufferedReader buff;
        private InputStream in;
        private final Process process;

        public ClearInOut(int a, Process p) {
            art = a;
            process = p;
        }

        @Override
        public void run() {
            String title = "";
            try {
                switch (art) {
                    case INPUT:
                        in = process.getInputStream();
                        title = "INPUTSTREAM";
                        break;
                    case ERROR:
                        in = process.getErrorStream();
                        //TH
                        synchronized (this) {
                            title = "ERRORSTREAM";
                        }
                        break;
                }
                buff = new BufferedReader(new InputStreamReader(in));
                String inStr;
                while ((inStr = buff.readLine()) != null) {
                    playerMessage.playerMessage(title + ": " + inStr);
                }
            } catch (final IOException ignored) {
            } finally {
                try {
                    buff.close();
                } catch (final IOException ignored) {
                }
            }
        }
    }
}
