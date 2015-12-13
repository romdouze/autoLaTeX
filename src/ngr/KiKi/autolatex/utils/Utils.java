/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author KiKi
 */
public class Utils
{

	public final static Map<String, Color> colors = new HashMap<String, Color> ()
	{
		{
			put ("Noir", Color.BLACK);
			put ("Bleu", Color.BLUE);
			put ("Cyan", Color.CYAN);
			put ("Gris foncé", Color.DARK_GRAY);
			put ("Gris", Color.GRAY);
			put ("Vert", Color.GREEN);
			put ("Gris clair", Color.LIGHT_GRAY);
			put ("Magenta", Color.MAGENTA);
			put ("Orange", Color.ORANGE);
			put ("Rose", Color.PINK);
			put ("Rouge", Color.RED);
			put ("Blanc", Color.WHITE);
			put ("Jaune", Color.YELLOW);
		}
	};

	public static String getColor (Color color)
	{
		return Utils.colors.keySet ().stream ().filter (c -> Utils.colors.get (c) == color).findFirst ().get ();
	}

	public final static String EXTENSION = "altx";
	public final static String PROPERTIES_FILENAME = "AMC_AutoLaTeX.properties";
	public final static String PROPERTIES_RECENT_PATH = "autolatex.recent-path";
}
