/*
 * This file is part of TellPlugin.
 *
 * Copyright © 2014-2015 Visual Illusions Entertainment
 *
 * TellPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see http://www.gnu.org/licenses/gpl.html.
 */
package net.visualillusionsent.tellplugin;

import net.visualillusionsent.utils.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Aaron (somners) on 9/23/2014.
 */
public class Tell {

    private String receiver, sender, message;
    private Date date;

    public Tell(String receiver, String sender, String message, String date) {
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        this.date = DateUtils.getDateFromString(date);
    }

    public Tell(String receiver, String sender, String message, Date date) {
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        this.date = date;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getDateString() {
        DateFormat datetime_form = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        return datetime_form.format(date);
    }

    public String getTimeSince() {
        int[] timeSince = DateUtils.getTimeUntilArray(Math.abs(DateUtils.getUnixTimestamp() - (date.getTime()/ 1000L)));
        StringBuilder sb = new StringBuilder();
        if (timeSince[0] > 0) {
            sb.append(String.format("%sDays, ", timeSince[0]));
        }
        if (timeSince[1] > 0) {
            sb.append(String.format("%sH, ", timeSince[1]));
        }
        if (timeSince[2] > 0) {
            sb.append(String.format("%sM, ", timeSince[2]));
        }
        if (timeSince[3] > 0) {
            if (sb.length() != 0) {
                sb.append("and ");
            }
            sb.append(String.format("%ss", timeSince[3]));
        }
        return sb.toString();
    }

    public Date getDate() {
        return date;
    }
}
