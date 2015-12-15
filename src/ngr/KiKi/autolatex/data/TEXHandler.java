/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.data;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import ngr.KiKi.autolatex.utils.Utils;

/**
 *
 * @author KiKi
 */
public class TEXHandler
{

	private TEXHandler ()
	{

	}

	public static void TEXWriter (String path, Test test)
	{
		FileWriter file = null;
		if (!path.toLowerCase ().endsWith ("." + Utils.TEX_EXTENSION))
			path += "." + Utils.TEX_EXTENSION;

		try
		{
			file = new FileWriter (path);
			PrintWriter print = new PrintWriter (file);

			print.printf ("\\documentclass[%spaper]{article}\n\n", test.getFormat ().toString ().toLowerCase ());
			print.println ("\\usepackage[utf8x]{inputenc}");
			print.println ("\\usepackage[T1]{fontenc}\n");
			print.println ("\\usepackage[francais,bloc,completemulti]{automultiplechoice}");
			print.println ("\\begin{document}\n");

			print.println ("%%% préparation des groupes\n");
			test.getGroups ().keySet ().stream ().forEach ((g) ->
			{
				test.getQuestions ().stream ().filter ((q) -> q.getGroup ().equals (g)).forEach ((q) ->
				{
					print.printf ("\\element{%s}{\n", g);
					print.printf ("  \\begin{question}{%s}\n", q.getShortName ());
					print.println ("    " + q.getText ());
					print.println ("    \\begin{reponses}");
					q.getAnswers ().stream ().forEach ((a) ->
					{
						print.printf ("      \\%s{%s}\n", a.isCorrect () ? "bonne" : "mauvaise", a.getText ());
					});
					print.println ("    \\end{reponses}");
					print.println ("  \\end{question}");
					print.println ("}\n");
				});
			});
		}
		catch (IOException ex)
		{
			Logger.getLogger (TEXHandler.class.getName ()).log (Level.SEVERE, null, ex);
		}
		finally
		{
			try
			{
				file.close ();
			}
			catch (IOException ex)
			{
				Logger.getLogger (TEXHandler.class.getName ()).log (Level.SEVERE, null, ex);
			}
		}
	}
}
