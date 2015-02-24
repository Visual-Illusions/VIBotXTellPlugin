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

import net.visualillusionsent.tellplugin.commands.ReadTellCommand;
import net.visualillusionsent.tellplugin.commands.TellCommand;
import net.visualillusionsent.tellplugin.commands.ViewTellCommand;
import net.visualillusionsent.tellplugin.listeners.UserChatListener;
import net.visualillusionsent.tellplugin.xml.TellSaveHandler;
import net.visualillusionsent.vibotx.VIBotX;
import net.visualillusionsent.vibotx.api.command.CommandCreationException;
import net.visualillusionsent.vibotx.api.events.EventHandler;
import net.visualillusionsent.vibotx.api.events.EventMethodSignatureException;
import net.visualillusionsent.vibotx.api.plugin.JavaPlugin;

/**
 * Created by Aaron on 9/23/2014.
 */
public class TellPlugin extends JavaPlugin {
    public static TellPlugin instance;

    @Override
    public boolean enable() {
        instance = this;
        TellSaveHandler<Tell> handler = TellSaveHandler.get();
        TellManager.init();
        try {
            new TellCommand(this);
            new ViewTellCommand(this);
            new ReadTellCommand(this);
        } catch (CommandCreationException e) {
            VIBotX.log.error("Error Registering Commands in Tell Plugin.", e);
        }
        try {
            EventHandler.getInstance().registerListener(UserChatListener.get(), this);
        } catch (EventMethodSignatureException e) {
            VIBotX.log.error("Error Registering Event Listeners in Tell Plugin.", e);
        }
        return true;
    }

    @Override
    public void disable() {

    }

    public TellPlugin instance() {
        return instance;
    }
}
