/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import ngr.KiKi.autolatex.data.Question;

/**
 *
 * @author KiKi
 */
public class QuestionRenderer extends JTextArea
{

	private final int id;
	private final Question question;
	private final JFrameMain parent;

	public QuestionRenderer (JFrameMain p, Question q, int i)
	{
		super ();

		parent = p;
		question = q;
		id = i;

		setText (id + " - " + question.toString ());
		setLineWrap (true);
		setWrapStyleWord (true);
		setFont (Font.decode ("Tahoma-13"));
		setMargin (new Insets (5, 5, 5, 5));
		setRows (2);
		setEditable (false);
		setCursor (new Cursor (Cursor.HAND_CURSOR));

		addMouseListener (new MouseAdapter ()
		{

			@Override
			public void mouseClicked (MouseEvent me)
			{
				parent.switchQuestionPanel ((QuestionRenderer) me.getSource ());
			}

			@Override
			public void mouseEntered (MouseEvent me)
			{
				setBackground (Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited (MouseEvent me)
			{
				setBackground (Color.WHITE);
			}
		});
	}

	public void updateText ()
	{
		setText (id + " - " + question.toString ());
	}

	public void updateColor ()
	{
		setForeground (parent.getGroups ().get (question.getGroup ()));
	}

	public Question getQuestion ()
	{
		return question;
	}

	@Override
	public Dimension getPreferredSize ()
	{
		return new Dimension (300, 30);
	}
}
