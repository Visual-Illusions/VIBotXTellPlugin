package net.visualillusionsent.tellplugin.commands;


import net.visualillusionsent.tellplugin.Tell;
import net.visualillusionsent.tellplugin.TellManager;
import net.visualillusionsent.vibotx.VIBotX;
import net.visualillusionsent.vibotx.api.command.BaseCommand;
import net.visualillusionsent.vibotx.api.command.BotCommand;
import net.visualillusionsent.vibotx.api.command.CommandCreationException;
import net.visualillusionsent.vibotx.api.command.CommandEvent;
import net.visualillusionsent.vibotx.api.plugin.Plugin;

import java.util.List;

/**
 * Read Tell Command<br>
 * Quiets the {@link net.visualillusionsent.vibotx.VIBotX} in a {@link org.pircbotx.Channel}<br>
 * <b>Usage:</b>!readtell <index><br>
 * <b>Minimum Params:</b> 0<br>
 * <b>Maximum Params:</b> 2<br>
 * <b>Requires:</b> <br>
 *
 * @author Aaron (somners)
 */
@BotCommand(
        main = "readtell",
        prefix = '!',
        usage = "!readtell <index>",
        desc = "Views stored Tell Messages",
        maxParam = 1,
        op = false,
        privateAllowed = false
)
public final class ReadTellCommand extends BaseCommand {

    /**
     * Constructs a new {@code ReadTellCommand}
     */
    public ReadTellCommand(Plugin plugin) throws CommandCreationException {
        super(plugin);
    }

    @Override
    public final synchronized boolean execute(CommandEvent event) {
        int index = 0;
        if (event.getArguments().length > 0 && event.getArgument(0) != null) {
            try {
                /*
                If they Input a number, we expect the first tell is index 1 to them, so subtract one.
                If the input isn't an integer, 'silently' catch the error and continue with index 0.
                 */
                int temp = Integer.valueOf(event.getArgument(0));
                index = temp - 1;
            } catch(Exception e) {
                VIBotX.log.error("'Silently' Catching parameter error in !readtell command.", e);
            }
        }
        List<Tell> tells = TellManager.getTells(event.getUser().getNick());
        /* no tells check */
        if (tells.size() == 0) {
            event.getUser().send().notice(String.format("%s: You have no pending tells.", event.getUser().getNick()));
            return true;
        }
        /* index out of bounds check */
        if (tells.size() <= index) {
            event.getUser().send().notice(
                    String.format("%s: You have not tells at index %s. You have %s tells.", event.getUser().getNick(), index + 1, tells.size()));
            return true;
        }
        /* select our tell, and relay it to the user */
        Tell tell = tells.get(index);
        event.getUser().send().notice(
                String.format("%s: %s sent on %s: %s", event.getUser().getNick(), tell.getSender(), tell.getDateString(), tell.getMessage()));
        /* Remove the tell from the queue */
        TellManager.removeTells(tell);
        return true;
    }
}
