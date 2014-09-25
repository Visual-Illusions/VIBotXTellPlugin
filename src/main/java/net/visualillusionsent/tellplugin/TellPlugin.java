package net.visualillusionsent.tellplugin;

import net.visualillusionsent.tellplugin.commands.ReadTellCommand;
import net.visualillusionsent.tellplugin.commands.TellCommand;
import net.visualillusionsent.tellplugin.commands.ViewTellCommand;
import net.visualillusionsent.tellplugin.xml.TellSaveHandler;
import net.visualillusionsent.vibotx.VIBotX;
import net.visualillusionsent.vibotx.api.command.CommandCreationException;
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
        return true;
    }

    @Override
    public void disable() {

    }

    public TellPlugin instance() {
        return instance;
    }
}
