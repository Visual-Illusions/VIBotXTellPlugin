package net.visualillusionsent.tellplugin.commands;

/**
 * Created by Aaron on 9/24/2014.
 */

import net.visualillusionsent.tellplugin.Tell;
import net.visualillusionsent.tellplugin.TellManager;
import net.visualillusionsent.tellplugin.listeners.UserChatListener;
import net.visualillusionsent.vibotx.api.command.BaseCommand;
import net.visualillusionsent.vibotx.api.command.BotCommand;
import net.visualillusionsent.vibotx.api.command.CommandCreationException;
import net.visualillusionsent.vibotx.api.command.CommandEvent;
import net.visualillusionsent.vibotx.api.plugin.Plugin;

import java.util.Calendar;
import java.util.Date;

/**
 * Shut The Fuck Up Command<br>
 * Quiets the {@link net.visualillusionsent.vibotx.VIBotX} in a {@link org.pircbotx.Channel}<br>
 * <b>Usage:</b>!tell <username> <message><br>
 * <b>Minimum Params:</b> 0<br>
 * <b>Maximum Params:</b> 2<br>
 * <b>Requires:</b> <br>
 *
 * @author Aaron (somners)
 */
@BotCommand(
        main = "tell",
        prefix = '!',
        usage = "!tell <username> <message>",
        desc = "Stores a message for a user to view later.",
        minParam = 2,
        op = false,
        privateAllowed = false
)
public final class TellCommand extends BaseCommand {

    /**
     * Constructs a new {@code ShutTheFuckUpCommand}
     */
    public TellCommand(Plugin plugin) throws CommandCreationException {
        super(plugin);
    }

    @Override
    public final synchronized boolean execute(CommandEvent event) {
        String username = event.getArgument(0);
        String message = event.getArgumentsAsString().replaceFirst(username, "");
        Date date = Calendar.getInstance().getTime();
        Tell tell = new Tell(username, event.getUser().getNick(), message, date);
        TellManager.addTell(tell);
        event.getChannel().send().message(event.getUser(), "I will send, " + username + ", that message.");
        /* notify the user that they have tells next time the message */
        UserChatListener.get().removeUser(username);
        return true;
    }
}
