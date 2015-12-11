/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.data;

import java.awt.Color;
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

}
