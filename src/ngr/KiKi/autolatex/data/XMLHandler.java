/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.data;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import ngr.KiKi.autolatex.utils.Utils;
import org.w3c.dom.*;

/**
 *
 * @author KiKi
 */
public class XMLHandler
{

	private XMLHandler ()
	{

	}

	public static void XMLWriter (String path, List<Question> questions, Map<String, Color> groups)
	{
		Document dom;
		Element e;

		// instance of a DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ();
		try
		{
			// use factory to get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder ();
			// create instance of DOM
			dom = db.newDocument ();

			// create the root element
			Element rootEle = dom.createElement ("amc-root");

			rootEle.appendChild (writeGroups (dom, groups));
			rootEle.appendChild (writeQuestions (dom, questions));

			dom.appendChild (rootEle);

			try
			{
				Transformer tr = TransformerFactory.newInstance ().newTransformer ();
				tr.setOutputProperty (OutputKeys.INDENT, "yes");
				tr.setOutputProperty (OutputKeys.METHOD, "xml");
				tr.setOutputProperty (OutputKeys.ENCODING, "UTF-8");
				tr.setOutputProperty ("{http://xml.apache.org/xslt}indent-amount", "4");

				// send DOM to file
				tr.transform (new DOMSource (dom),
						new StreamResult (new FileOutputStream (path)));

			}
			catch (TransformerException | IOException te)
			{
				System.out.println (te.getMessage ());
			}
		}
		catch (ParserConfigurationException pce)
		{
			System.out.println ("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
		}
	}

	private static Element writeQuestions (Document dom, List<Question> questions)
	{
		Element root = dom.createElement ("questions");

		questions.stream ().forEach ((q) ->
		{
			Element e;
			Element question = dom.createElement ("question");

			e = dom.createElement ("text");
			e.appendChild (dom.createTextNode (q.getText ()));
			question.appendChild (e);

			e = dom.createElement ("type");
			e.appendChild (dom.createTextNode (q.getType ().toString ()));
			question.appendChild (e);

			e = dom.createElement ("nb-lines");
			e.appendChild (dom.createTextNode ("" + q.getNbLines ()));
			question.appendChild (e);

			e = dom.createElement ("group");
			e.appendChild (dom.createTextNode (q.getGroup ()));
			question.appendChild (e);

			question.appendChild (writeAnswers (dom, q.getAnswers ()));

			root.appendChild (question);
		});

		return root;
	}

	private static Element writeAnswers (Document dom, List<Answer> answers)
	{
		Element root = dom.createElement ("answers");

		answers.stream ().forEach ((a) ->
		{
			Element e;
			Element answer = dom.createElement ("answer");

			e = dom.createElement ("text");
			e.appendChild (dom.createTextNode (a.getText ()));
			answer.appendChild (e);

			e = dom.createElement ("score");
			e.appendChild (dom.createTextNode ("" + a.getScore ()));
			answer.appendChild (e);

			e = dom.createElement ("correct");
			e.appendChild (dom.createTextNode ("" + a.isCorrect ()));
			answer.appendChild (e);

			root.appendChild (answer);
		});

		return root;
	}

	private static Element writeGroups (Document dom, Map<String, Color> groups)
	{
		Element root = dom.createElement ("groups");

		groups.keySet ().stream ().forEach ((s) ->
		{
			Element e;
			Element group = dom.createElement ("group");

			e = dom.createElement ("name");
			e.appendChild (dom.createTextNode (s));
			group.appendChild (e);

			e = dom.createElement ("color");
			e.appendChild (dom.createTextNode (Utils.colors.keySet ().stream ().filter (c -> Utils.colors.get (c) == groups.get (s)).findFirst ().get ()));
			group.appendChild (e);

			root.appendChild (group);
		});

		return root;
	}
}
