package net.visualillusionsent.tellplugin.listeners;

import net.visualillusionsent.tellplugin.Tell;
import net.visualillusionsent.tellplugin.TellManager;
import net.visualillusionsent.vibotx.api.events.EventListener;
import net.visualillusionsent.vibotx.api.events.EventMethod;
import org.pircbotx.Colors;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 9/25/2014.
 */
public class UserChatListener implements EventListener {

    private static UserChatListener instance;
    /* list of players we have notified that they have tells */
    private final List<String> names = new ArrayList<String>();

    public UserChatListener() {
        instance = this;
    }

    public static UserChatListener get() {
        if (instance == null)
            instance = new UserChatListener();
        return instance;
    }

    @EventMethod
    public void onChannelMessage(MessageEvent event) {
        String username = event.getUser().getNick();
        /* get a list of the users tells */
        List<Tell> tells = TellManager.getTells(username);
        /* check if the user has any tells */
        if (tells.size() > 0) {
            /* check if we've notified them of their tells */
            if (!hasUser(username)) {
                /* notify them of their tells */
                StringBuilder sb = new StringBuilder();
                sb.append("Hey, ").append(Colors.DARK_GREEN).append(username).append(Colors.NORMAL);
                sb.append(", you have ").append(Colors.DARK_GREEN).append(tells.size());
                sb.append(Colors.NORMAL).append(" tells. Use '.help' to learn how to read view them.");
                event.getUser().send().notice(sb.toString());
                /* add them to the list of users we've notified */
                addUser(username);
            }
        }
    }

    public void addUser(String username) {
        if (!names.contains(username)) {
            names.add(username);
        }
    }

    public void removeUser(String username) {
        if (names.contains(username)) {
            names.remove(username);
        }
    }

    public boolean hasUser(String username) {
        return names.contains(username);
    }

}
