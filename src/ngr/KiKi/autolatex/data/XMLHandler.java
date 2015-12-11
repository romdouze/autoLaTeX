/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.data;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import ngr.KiKi.autolatex.utils.Utils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author KiKi
 */
public class XMLHandler
{

	private XMLHandler ()
	{

	}

	public static Test XMLReader (String path)
	{
		Test test = new Test ();

		Document dom;
		// Make an  instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ();
		try
		{
			// use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder ();
			// parse using the builder to get the DOM mapping of the    
			// XML file
			dom = db.parse (path);

			Element doc = dom.getDocumentElement ();

			test.setGroups (readGroups (doc.getElementsByTagName ("groups").item (0)));

			test.setQuestions (readQuestions (doc.getElementsByTagName ("questions").item (0)));

		}
		catch (ParserConfigurationException | SAXException pce)
		{
			System.out.println (pce.getMessage ());
		}
		catch (IOException ioe)
		{
			System.err.println (ioe.getMessage ());
		}

		return test;
	}

	private static Map<String, Color> readGroups (Node node)
	{
		Map<String, Color> groups = new HashMap<> ();

		for (Node n : findAllSubNode (node, "group"))
			groups.put (findSubNode (n, "name").getTextContent (), Utils.colors.get (findSubNode (n, "color").getTextContent ()));

		return groups;
	}

	private static List<Question> readQuestions (Node node)
	{
		List<Question> questions = new ArrayList<> ();

		for (Node n : findAllSubNode (node, "question"))
		{
			Question q = new Question (findSubNode (n, "text").getTextContent (), Question.TYPE.getType (findSubNode (n, "type").getTextContent ()));
			q.setGroup (findSubNode (n, "group").getTextContent ());
			q.setNbLines (Integer.valueOf (findSubNode (n, "nb-lines").getTextContent ()));
			q.setAnswers (readAnswers (findSubNode (n, "answers")));

			questions.add (q);
		}

		return questions;
	}

	private static List<Answer> readAnswers (Node node)
	{
		List<Answer> answers = new ArrayList<> ();

		for (Node n : findAllSubNode (node, "answer"))
		{
			Answer a = new Answer (findSubNode (n, "text").getTextContent (), Boolean.valueOf (findSubNode (n, "correct").getTextContent ()));

			answers.add (a);
		}

		return answers;
	}

	private static Node findSubNode (Node node, String name)
	{
		if (node.getNodeType () != Node.ELEMENT_NODE)
			System.err.println ("Error: Search node not of element type");

		if (!node.hasChildNodes ())
			return null;
		NodeList list = node.getChildNodes ();
		for (int i = 0; i < list.getLength (); i++)
		{
			Node subNode = list.item (i);
			if (subNode.getNodeType () == Node.ELEMENT_NODE)
				if (subNode.getNodeName ().equals (name))
					return subNode;
		}

		return null;
	}

	private static List<Node> findAllSubNode (Node node, String name)
	{
		if (node.getNodeType () != Node.ELEMENT_NODE)
			System.err.println ("Error: Search node not of element type");

		List<Node> list = new ArrayList<> ();
		if (!node.hasChildNodes ())
			return list;

		NodeList children = node.getChildNodes ();
		for (int i = 0; i < children.getLength (); i++)
		{
			Node subNode = children.item (i);
			if (subNode.getNodeType () == Node.ELEMENT_NODE)
				if (subNode.getNodeName ().equals (name))
					list.add (subNode);
		}

		return list;
	}

	public static void XMLWriter (String path, Test test)
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

			rootEle.appendChild (writeGroups (dom, test.getGroups ()));
			rootEle.appendChild (writeQuestions (dom, test.getQuestions ()));

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
			e.appendChild (dom.createTextNode (Utils.getColor (groups.get (s))));
			group.appendChild (e);

			root.appendChild (group);
		});

		return root;
	}
}
