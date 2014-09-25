package net.visualillusionsent.tellplugin.xml;

import net.visualillusionsent.tellplugin.Tell;
import net.visualillusionsent.vibotx.VIBotX;
import org.jdom2.Element;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 9/23/2014.
 */
public class TellSaveHandler<T> extends XMLSaveHandler<T> {

    private static TellSaveHandler<Tell> instance;

    public TellSaveHandler () {
        super("tells");
    }

    public static TellSaveHandler get() {
        if (instance == null) {
            instance = new TellSaveHandler<Tell>();
        }
        return instance;
    }
    @Override
    public void save(T... data) {
        for (T d : data) {
            this.insertData((Tell) d);
        }
    }

    @Override
    public List<T> loadAll() {
        return (List<T>) this.load();
    }

    @Override
    public void delete(T... data) {
        for (T d : data) {
            this.deleteData((Tell) d);
        }
    }

    public void insertData(Tell tell) {
        Element set = new Element("entry");

        /* Add Element Data */
        Element receiverCol = new Element("receiver");
        receiverCol.setText(tell.getReceiver());
        set.addContent(receiverCol);

        Element senderCol = new Element("sender");
        senderCol.setText(tell.getSender());
        set.addContent(senderCol);

        Element messageCol = new Element("message");
        messageCol.setText(tell.getMessage());
        set.addContent(messageCol);

        Element dateCol = new Element("date");
        dateCol.setText(tell.getDateString());
        set.addContent(dateCol);


        getDocument().getRootElement().addContent(set);
        try {
            write();
        } catch (IOException e) {
            VIBotX.log.error("Error writng to TellPlugin Xml File", e);
        }
    }

    public void deleteData(Tell tell) {
        ArrayList<Element> toremove = new ArrayList<Element>();

        for (Element element : getDocument().getRootElement().getChildren()) {
            VIBotX.log.info(element.getChildText("receiver"));
            VIBotX.log.info(element.getChildText("sender"));
            VIBotX.log.info(element.getChildText("message"));
            VIBotX.log.info(element.getChildText("date"));
            if (!tell.getReceiver().equals(element.getChildText("receiver"))) continue;
            if (!tell.getSender().equals(element.getChildText("sender"))) continue;
            if (!tell.getMessage().equals(element.getChildText("message"))) continue;
            if (!tell.getDateString().equals(element.getChildText("date"))) continue;

            toremove.add(element);
        }
        for (Element e : toremove) {
            e.detach();
        }
        try {
            write();
        } catch (IOException e) {
            VIBotX.log.error("Error deleting from TellPlugin Xml File", e);
        }
    }

    public List<Tell> load() {
        ArrayList<Tell> toRet = new ArrayList<Tell>();
        for (Element element : getDocument().getRootElement().getChildren()) {
            String receiver, sender, message, date;

            receiver = element.getChild("receiver").getText();
            sender = element.getChild("sender").getText();
            message = element.getChild("message").getText();
            date = element.getChild("date").getText();

            Tell tell = new Tell(receiver, sender, message, date);
            toRet.add(tell);
        }
        return toRet;
    }

    private void write() throws IOException {
        RandomAccessFile f = new RandomAccessFile(getFile().getPath(), "rw");
        f.getChannel().lock();
        f.setLength(0);
        f.write(getXmlSerializer().outputString(getDocument()).getBytes(Charset.forName("UTF-8")));
        f.close();
    }
}
