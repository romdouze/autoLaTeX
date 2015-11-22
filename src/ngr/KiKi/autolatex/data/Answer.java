/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.data;

/**
 *
 * @author KiKi
 */
public class Answer
{

	private String text;
	private int score;
	private boolean correct;

	public Answer (String t, boolean c)
	{
		text = t;
		correct = c;
	}

	public String getText ()
	{
		return text;
	}

	public void setText (String text)
	{
		this.text = text;
	}

	public boolean isCorrect ()
	{
		return correct;
	}

	public void setCorrect (boolean correct)
	{
		this.correct = correct;
	}

	public int getScore ()
	{
		return score;
	}

	public void setScore (int score)
	{
		this.score = score;
	}

	@Override
	public String toString ()
	{
		return text;
	}

}
