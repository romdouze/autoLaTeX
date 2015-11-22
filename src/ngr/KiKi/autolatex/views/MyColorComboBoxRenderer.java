/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.views;

import java.awt.Color;
import java.awt.Component;
import java.util.Arrays;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author KiKi
 */
public class MyColorComboBoxRenderer extends JPanel implements ListCellRenderer
{

	private static final long serialVersionUID = -1L;
	private Color[] colors;
	private String[] strings;
	private Map<String, Color> values;

	JPanel textPanel;
	JLabel text;

	public MyColorComboBoxRenderer (JComboBox combo)
	{

		textPanel = new JPanel ();
		textPanel.add (this);
		text = new JLabel ();
		text.setOpaque (true);
		text.setFont (combo.getFont ());
		textPanel.add (text);
	}

	public void setValues (Map<String, Color> v)
	{
		values = v;
		strings = values.keySet ().toArray (new String[values.keySet ().size ()]);
		colors = new Color[strings.length];
		for (int i = 0; i < strings.length; i++)
			colors[i] = values.get (strings[i]);
	}

	public Map<String, Color> getValues ()
	{
		return values;
	}

	public void setColor (Color[] col)
	{
		colors = col;
	}

	public void setStrings (String[] str)
	{
		strings = str;
	}

	public Color[] getColors ()
	{
		return colors;
	}

	public String[] getStrings ()
	{
		return strings;
	}

	@Override
	public Component getListCellRendererComponent (JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus)
	{

		if (isSelected)
			setBackground (list.getSelectionBackground ());
		else
			setBackground (Color.WHITE);

		if (colors.length != strings.length)
		{
			System.out.println ("colors.length does not equal strings.length");
			return this;
		}
		else if (colors == null)
		{
			System.out.println ("use setColors first.");
			return this;
		}
		else if (strings == null)
		{
			System.out.println ("use setStrings first.");
			return this;
		}

		text.setBackground (getBackground ());

		text.setText (value.toString ());
		if (index > -1)
			text.setForeground (colors[index]);
		return text;
	}
}
