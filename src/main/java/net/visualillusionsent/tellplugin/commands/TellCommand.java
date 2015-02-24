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
