
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author KiKi
 */
public class Question
{

	private String text;
	private String shortName;
	private TYPE type;
	private int nbLines;
	private String group;
	private List<Answer> answers;
	private String picture;

	public Question (String txt, TYPE t)
	{
		text = txt;
		type = t;
		group = "Défaut";
		shortName = "";
		picture = "";
		answers = new ArrayList<> ();
	}

	public Answer addAnswer (String a, boolean b)
	{
		Answer answer = new Answer (a, b);
		answers.add (answer);
		return answer;
	}

	public String getText ()
	{
		return text;
	}

	public void setText (String text)
	{
		this.text = text;
	}

	public TYPE getType ()
	{
		return type;
	}

	public void setType (TYPE type)
	{
		this.type = type;
	}

	public List<Answer> getAnswers ()
	{
		return answers;
	}

	public void setAnswers (List<Answer> questions)
	{
		this.answers = questions;
	}

	public int getNbLines ()
	{
		return nbLines;
	}

	public void setNbLines (int nbLines)
	{
		this.nbLines = nbLines;
	}

	public String getGroup ()
	{
		return group;
	}

	public void setGroup (String group)
	{
		this.group = group;
	}

	public String getShortName ()
	{
		return shortName;
	}

	public String getShortNameOrGenerate ()
	{
		shortName = shortName.isEmpty () ? text.substring (0, 10) + "__" + (int) (Math.random () * 1000) : shortName;

		return shortName;
	}

	public void setShortName (String shortName)
	{
		this.shortName = shortName;
	}

	public String getPicture ()
	{
		return picture;
	}

	public void setPicture (String picture)
	{
		this.picture = picture;
	}

	@Override
	public String toString ()
	{
		return text;
	}

	public static enum TYPE
	{

		SIMPLE ("Simple"), MULTIPLE ("Multiple"), OPEN ("Ouverte"), INFO ("Informative");

		private final String stringValue;

		private TYPE (final String s)
		{
			stringValue = s;
		}

		public static TYPE getType (String s)
		{
			return Arrays.stream (TYPE.values ()).filter (t -> t.toString ().equals (s)).findFirst ().get ();
		}

		@Override
		public String toString ()
		{
			return stringValue;
		}
	};
}
