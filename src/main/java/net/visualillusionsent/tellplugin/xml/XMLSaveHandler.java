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
/*
 * This file is part of WeatherBot.
 *
 * Copyright © 2014-2015 Visual Illusions Entertainment
 *
 * WeatherBot is free software: you can redistribute it and/or modify
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
/*
 * This file is part of WeatherBot.
 *
 * Copyright © 2014-2014 Visual Illusions Entertainment
 *
 * WeatherBot is free software: you can redistribute it and/or modify
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
/*
 * This file is part of WeatherBot.
 *
 * Copyright © 2014 Visual Illusions Entertainment
 *
 * WeatherBot is free software: you can redistribute it and/or modify
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
package net.visualillusionsent.tellplugin.xml;

import net.visualillusionsent.vibotx.VIBotX;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Aaron (somners) on 9/23/2014.
 */
public abstract class XMLSaveHandler<T> {

    /** Directory of data files */
    private final File dataDir = new File("data/");
    /** This particular save file */
    private File file;
    private Document document;

    /** Used to serialize the XML data into a bytestream */
    private XMLOutputter xmlSerializer = new XMLOutputter(Format.getPrettyFormat().setExpandEmptyElements(true).setOmitDeclaration(true).setOmitEncoding(true).setLineSeparator("\n"));

    private SAXBuilder fileBuilder = new SAXBuilder();

    public XMLSaveHandler(String fileName) {
        try {
            file = new File(dataDir, fileName + ".xml");
            if (!file.exists()) {
                dataDir.mkdirs();
                file.createNewFile();

                /* Create our Document */
                document = new Document();
                /* initialize the root element */
                document.setRootElement(new Element(fileName));
                /* write doc to file */
                RandomAccessFile f = new RandomAccessFile(getFile().getPath(), "rw");
                f.getChannel().lock();
                f.setLength(0);
                f.write(getXmlSerializer().outputString(getDocument()).getBytes(Charset.forName("UTF-8")));
                f.close();
            } else {
                FileInputStream in = new FileInputStream(file);
                document = fileBuilder.build(in);
                in.close();
            }
        }
        catch (JDOMException e) {
            VIBotX.log.error("Error initializing XML Document.", e);
        }
        catch (IOException e) {
            VIBotX.log.error("Error initializing XML Document.", e);
        }
    }

    /**
     * Gets the stored XML Document.
     * @return
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Gets the stored XML Serializer.
     * @return
     */
    public XMLOutputter getXmlSerializer() {
        return xmlSerializer;
    }

    /**
     * Gets the XML File
     * @return
     */
    public File getFile() {
        return file;
    }

    /**
     * Saves the given class data with the implementing handler
     * @param data data to save
     */
    public abstract void save(T... data);

    /**
     * Deletes the given class data with the implementing handler
     * @param data data to delete
     */
    public abstract void delete(T... data);

    /**
     * Loads all data from file.
     * @return A List of
     */
    public abstract List<T> loadAll();


}
