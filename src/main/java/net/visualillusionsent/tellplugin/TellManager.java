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
