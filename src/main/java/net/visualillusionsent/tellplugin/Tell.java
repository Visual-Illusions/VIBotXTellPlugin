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

    public Date getDate() {
        return date;
    }
}
