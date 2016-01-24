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
			print.println ("\\usepackage[francais" + (test.isBlocks () ? ",bloc" : "") + (test.isNone () ? ",completemulti" : "") + "]{automultiplechoice}");
			if (test.getColumns () > 1)
				print.println ("\\usepackage{multicol}\n");

			print.println ("\\begin{document}\n");

			if (!test.getNoneText ().isEmpty ())
				print.println ("\\AMCtext{none}{" + test.getNoneText () + "}\n");

			print.println ("%%% préparation des groupes\n");
			test.getGroups ().keySet ().stream ().forEach ((g) ->
			{
				test.getQuestions ().stream ().filter ((q) -> q.getGroup ().equals (g)).forEach ((q) ->
				{
					print.printf ("\\element{%s}{\n", Utils.normalize (g));
					writeQuestion (print, q, test);
					print.println ("}\n");
				});
			});

			print.println ("%%% fabrication des copies\n");
			print.println ("\\exemplaire{10}{\n");
			print.println ("%%% debut de l'en-tête des copies :\n");
			print.println ("\\noindent{\\bf QCM \\hfill TEST}\n");
			print.println ("\\vspace*{.5cm}");
			print.println ("\\begin{minipage}{.4\\linewidth}");
			print.println ("  \\centering\\large\\bf " + test.getTitle ());
			print.println ("\\end{minipage}");
			print.println ("\\champnom{\\fbox{\\begin{minipage}{.5\\linewidth}");
			print.println (test.getNameText () + "\n");
			print.println ("\\vspace*{.5cm}\\dotfill");
			print.println ("\\vspace*{1mm}");
			print.println ("\\end{minipage}}}\n");

			print.println ("\\begin{center}\\em");
			print.println (test.getDescription ());
			print.println ("\\end{center}");
			print.println ("\\vspace{1ex}");

			print.println ("%%% fin de l'en-tête\n");

			test.getGroups ().keySet ().stream ().forEach ((g) ->
			{
				print.println ("\\begin{center}");
				print.println ("  \\hrule\\vspace{2mm}");
				print.println ("  \\bf\\Large " + g);
				print.println ("  \\vspace{2mm}\\hrule");
				print.println ("\\end{center}\n");
				print.println ("\\melangegroupe{" + Utils.normalize (g) + "}");
				print.println ("\\restituegroupe{" + Utils.normalize (g) + "}\n");
			});

			print.println ("\\clearpage\n");

			print.println ("}\n");

			print.println ("\\end{document}");
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

	private static void writeQuestion (PrintWriter print, Question q, Test test)
	{
		if (q.getType () == Question.TYPE.OPEN)
		{
			print.println ("  \\begin{question}{" + Utils.normalize (q.getShortNameOrGenerate ()) + "}");
			print.println ("    " + q.getText ());
			print.print ("    \\AMCOpen{lines=" + q.getNbLines () + "}{");
			q.getAnswers ().stream ().forEach ((a) ->
			{
				print.print ("\\" + (a.isCorrect () ? "correct" : "wrong") + "choice[" + a.getText () + "]{" + Utils.normalize (a.getText () + "}\\scoring{" + a.getScore () + "}"));
			});
			print.println ("}");
			print.println ("\\end{question}\n");
		}
		else
		{
			print.printf ("  \\begin{%s}{%s}\n", q.getType () == Question.TYPE.SIMPLE ? "question" : "questionmult", Utils.normalize (q.getShortNameOrGenerate ()));
			print.println ("    " + q.getText ());
			if (test.getColumns () > 1)
				print.println ("    \\begin{multicols}{" + test.getColumns () + "}");
			print.println ("    \\begin{reponses}");
			q.getAnswers ().stream ().forEach ((a) ->
			{
				print.printf ("      \\%s{%s}\n", a.isCorrect () ? "bonne" : "mauvaise", a.getText ());
			});
			print.println ("    \\end{reponses}");
			if (test.getColumns () > 1)
				print.println ("    \\end{multicols}");
			print.println ("  \\end{question" + (q.getType () == Question.TYPE.MULTIPLE ? "mult" : "") + "}");
		}
	}
}
