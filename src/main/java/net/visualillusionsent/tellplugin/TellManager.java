package net.visualillusionsent.tellplugin;

import com.google.common.collect.Lists;
import net.visualillusionsent.tellplugin.xml.TellSaveHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 9/24/2014.
 */
public class TellManager {

    private static List<Tell> tells;

    static {
        tells = TellSaveHandler.get().loadAll();
    }

    public static List<Tell> getTells(String receiver) {
        ArrayList<Tell> toRet = new ArrayList<Tell>();
        for (Tell t : tells) {
            if (t.getReceiver().equals(receiver)) {
                toRet.add(t);
            }
        }
        return toRet;
    }

    public static void removeTells(Tell... data) {
        TellSaveHandler.get().delete(data);
        tells.removeAll(Lists.newArrayList(data));
    }

    public static void addTell(Tell tell) {
        TellSaveHandler.get().save(tell);
        tells.add(tell);
    }

    public static void init() {}
}
