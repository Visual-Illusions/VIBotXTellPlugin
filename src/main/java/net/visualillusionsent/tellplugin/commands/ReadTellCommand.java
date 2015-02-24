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
package net.visualillusionsent.tellplugin.commands;


import net.visualillusionsent.tellplugin.Tell;
import net.visualillusionsent.tellplugin.TellManager;
import net.visualillusionsent.vibotx.VIBotX;
import net.visualillusionsent.vibotx.api.command.BaseCommand;
import net.visualillusionsent.vibotx.api.command.BotCommand;
import net.visualillusionsent.vibotx.api.command.CommandCreationException;
import net.visualillusionsent.vibotx.api.command.CommandEvent;
import net.visualillusionsent.vibotx.api.plugin.Plugin;
import org.pircbotx.Colors;

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
        StringBuilder sb = new StringBuilder();
        /* Append the tell number */
        sb.append(Colors.DARK_GREEN).append("Tell #").append(String.valueOf(index + 1)).append(Colors.NORMAL).append(":");
        /* Append the time since it was sent */
        sb.append(Colors.BROWN).append("Sent ").append(tell.getTimeSince()).append(" Ago").append(Colors.NORMAL).append(":");
        /* Append the sender */
        sb.append(Colors.DARK_GREEN).append(tell.getSender()).append(Colors.NORMAL).append(": ");
        /* Append the message */
        sb.append(tell.getMessage());
        /* Send the message */
        event.getUser().send().notice(sb.toString());
        /* Remove the tell from the queue */
        TellManager.removeTells(tell);
        return true;
    }
}
