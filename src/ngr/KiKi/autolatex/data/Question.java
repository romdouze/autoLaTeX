/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KiKi
 */
public class Question
{

	private String text;
	private TYPE type;
	private int nbLines;
	private String group;
	private List<Answer> answers;

	public Question (String txt, TYPE t)
	{
		text = txt;
		type = t;
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

	@Override
	public String toString ()
	{
		return text;
	}

	public static enum TYPE
	{

		SIMPLE, MULTIPLE, OPEN
	};
}
