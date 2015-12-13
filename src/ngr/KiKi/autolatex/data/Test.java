/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author KiKi
 */
public class Test
{

	private List<Question> questions;
	private Map<String, Color> groups;

	private String title;
	private String description;
	private boolean shuffle;
	private int columns;
	private boolean none;
	private String noneText;
	private boolean blocks;
	private Format format;
	private int code;
	private String codeText;
	private Language language;
	private String questionText;
	private String nameText;
	private boolean evenPages;
	private boolean blankPage;
	private String color;

	public Test ()
	{
		questions = new ArrayList<> ();
		groups = new HashMap<> ();
		format = Format.A4;
		language = Language.FR;
		description = "";
		noneText = "";
		codeText = "";
		questionText = "";
		nameText = "";
		color = "";
	}

	public List<Question> getQuestions ()
	{
		return questions;
	}

	public void setQuestions (List<Question> questions)
	{
		this.questions = questions;
	}

	public Map<String, Color> getGroups ()
	{
		return groups;
	}

	public void setGroups (Map<String, Color> groups)
	{
		this.groups = groups;
	}

	public String getTitle ()
	{
		return title;
	}

	public void setTitle (String title)
	{
		this.title = title;
	}

	public String getDescription ()
	{
		return description;
	}

	public void setDescription (String description)
	{
		this.description = description;
	}

	public boolean isShuffle ()
	{
		return shuffle;
	}

	public void setShuffle (boolean shuffle)
	{
		this.shuffle = shuffle;
	}

	public int getColumns ()
	{
		return columns;
	}

	public void setColumns (int columns)
	{
		this.columns = columns;
	}

	public boolean isNone ()
	{
		return none;
	}

	public void setNone (boolean none)
	{
		this.none = none;
	}

	public String getNoneText ()
	{
		return noneText;
	}

	public void setNoneText (String noneText)
	{
		this.noneText = noneText;
	}

	public boolean isBlocks ()
	{
		return blocks;
	}

	public void setBlocks (boolean blocks)
	{
		this.blocks = blocks;
	}

	public Format getFormat ()
	{
		return format;
	}

	public void setFormat (Format format)
	{
		this.format = format;
	}

	public int getCode ()
	{
		return code;
	}

	public void setCode (int code)
	{
		this.code = code;
	}

	public String getCodeText ()
	{
		return codeText;
	}

	public void setCodeText (String codeText)
	{
		this.codeText = codeText;
	}

	public Language getLanguage ()
	{
		return language;
	}

	public void setLanguage (Language language)
	{
		this.language = language;
	}

	public String getQuestionText ()
	{
		return questionText;
	}

	public void setQuestionText (String questionText)
	{
		this.questionText = questionText;
	}

	public String getNameText ()
	{
		return nameText;
	}

	public void setNameText (String nameText)
	{
		this.nameText = nameText;
	}

	public boolean isEvenPages ()
	{
		return evenPages;
	}

	public void setEvenPages (boolean evenPages)
	{
		this.evenPages = evenPages;
	}

	public boolean isBlankPage ()
	{
		return blankPage;
	}

	public void setBlankPage (boolean blankPage)
	{
		this.blankPage = blankPage;
	}

	public String getColor ()
	{
		return color;
	}

	public void setColor (String color)
	{
		this.color = color;
	}

	public static enum Format
	{

		A3, A4, A5, A6, B3, B4, B5, B6, letter, legal, ANSIA, ANSIB, ANSIC, ANSID, ANSIE
	}

	public static enum Language
	{

		FR, ES, DE, IT, NL, NO, JA
	}
}
