package net.visualillusionsent.tellplugin.commands;

import net.visualillusionsent.tellplugin.Tell;
import net.visualillusionsent.tellplugin.TellManager;
import net.visualillusionsent.vibotx.api.command.BaseCommand;
import net.visualillusionsent.vibotx.api.command.BotCommand;
import net.visualillusionsent.vibotx.api.command.CommandCreationException;
import net.visualillusionsent.vibotx.api.command.CommandEvent;
import net.visualillusionsent.vibotx.api.plugin.Plugin;
import org.pircbotx.Colors;

import java.util.List;

/**
 * ViewTells Command<br>
 * Quiets the {@link net.visualillusionsent.vibotx.VIBotX} in a {@link org.pircbotx.Channel}<br>
 * <b>Usage:</b>!viewtells<br>
 * <b>Minimum Params:</b> 0<br>
 * <b>Maximum Params:</b> 2<br>
 * <b>Requires:</b> <br>
 *
 * @author Aaron (somners)
 */
@BotCommand(
        main = "viewtells",
        prefix = '!',
        usage = "!viewtells",
        desc = "Views stored Tell Messages",
        maxParam = 0,
        op = false,
        privateAllowed = false
)
public final class ViewTellCommand extends BaseCommand {

    /**
     * Constructs a new {@code ViewTellCommand}
     */
    public ViewTellCommand(Plugin plugin) throws CommandCreationException {
        super(plugin);
    }

    @Override
    public final synchronized boolean execute(CommandEvent event) {
        List<Tell> tells = TellManager.getTells(event.getUser().getNick());
        /* no tells check */
        if (tells.size() == 0) {
            event.getUser().send().notice(String.format("%s: You have no pending tells.", event.getUser().getNick()));
            return true;
        }
        /* Only send a max of 5 recent tells */
        for (int i = 0; tells.size() > i && i < 5; i++) {
            Tell t = tells.get(i);
            /* Get the Proper ending index, don't throw IOOBE's */
            int endIndex = t.getMessage().length() < 30 ? t.getMessage().length() : 30;
            /* Build our String */
            StringBuilder sb = new StringBuilder();
            /* Append the tell number */
            sb.append(Colors.DARK_GREEN).append("Tell #").append(String.valueOf(i + 1)).append(Colors.NORMAL).append(":");
            /* Append the time since the tell was sent */
            sb.append(Colors.BROWN).append("Sent ").append(t.getTimeSince()).append(" Ago").append(Colors.NORMAL).append(":");
            /* Append the sender */
            sb.append(Colors.DARK_GREEN).append(t.getSender()).append(Colors.NORMAL).append(": ");
            /* Append the message */
            sb.append(t.getMessage().substring(0, endIndex));
            /* If this message is getting trimmed, lets give an indicator */
            if (t.getMessage().length() > 30) sb.append("...");
            /* And finally, lets notify the player */
            event.getUser().send().notice(sb.toString());
        }
        return true;
    }
}
