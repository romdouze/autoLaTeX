/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

/**
 *
 * @author KiKi
 */
public class FormulaPanel extends JPanel
{

	private final Image image;
	private final String formula;

	public FormulaPanel (String f)
	{
		formula = f;
		TeXFormula texFormula = new TeXFormula (formula);
		TeXIcon icon = texFormula.createTeXIcon (TeXConstants.STYLE_DISPLAY, 40);
		BufferedImage bfImage = new BufferedImage (icon.getIconWidth (), icon.getIconHeight (), BufferedImage.TYPE_4BYTE_ABGR);
		image = bfImage.getScaledInstance (-1, -1, Image.SCALE_FAST);

		setSize (200, 200);
		setBackground (Color.yellow);
		setLayout (new FlowLayout (FlowLayout.CENTER));
	}

	@Override
	public void paintComponent (Graphics g)
	{
		g.drawImage (image, 0, 0, this);
	}
}
