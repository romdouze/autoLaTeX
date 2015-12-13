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

		if (!path.toLowerCase ().endsWith ("." + Utils.EXTENSION))
			return test;

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

			readSettings (doc.getElementsByTagName ("settings").item (0), test);

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

	private static void readSettings (Node node, Test test)
	{
		test.setTitle (findSubNode (node, "title").getTextContent ());
		test.setDescription (findSubNode (node, "description").getTextContent ());
		test.setShuffle (Boolean.valueOf (findSubNode (node, "shuffle").getTextContent ()));
		test.setColumns (Integer.valueOf (findSubNode (node, "columns").getTextContent ()));
		test.setNone (Boolean.valueOf (findSubNode (node, "none").getTextContent ()));
		test.setNoneText (findSubNode (node, "none-text").getTextContent ());
		test.setBlocks (Boolean.valueOf (findSubNode (node, "blocks").getTextContent ()));
		test.setFormat (Test.Format.valueOf (findSubNode (node, "format").getTextContent ()));
		test.setCode (Integer.valueOf (findSubNode (node, "code").getTextContent ()));
		test.setCodeText (findSubNode (node, "code-text").getTextContent ());
		test.setLanguage (Test.Language.valueOf (findSubNode (node, "language").getTextContent ()));
		test.setQuestionText (findSubNode (node, "question-text").getTextContent ());
		test.setNameText (findSubNode (node, "name-text").getTextContent ());
		test.setEvenPages (Boolean.valueOf (findSubNode (node, "even-pages").getTextContent ()));
		test.setBlankPage (Boolean.valueOf (findSubNode (node, "blank-page").getTextContent ()));
		test.setColor (findSubNode (node, "color").getTextContent ());
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
		if (!path.toLowerCase ().endsWith ("." + Utils.EXTENSION))
			path += "." + Utils.EXTENSION;

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

			rootEle.appendChild (writeSettings (dom, test));
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

	private static Element writeSettings (Document dom, Test test)
	{
		Element root = dom.createElement ("settings");

		Element e = dom.createElement ("title");
		e.appendChild (dom.createTextNode (test.getTitle ()));
		root.appendChild (e);

		e = dom.createElement ("description");
		e.appendChild (dom.createTextNode (test.getDescription ()));
		root.appendChild (e);

		e = dom.createElement ("shuffle");
		e.appendChild (dom.createTextNode ("" + test.isShuffle ()));
		root.appendChild (e);

		e = dom.createElement ("columns");
		e.appendChild (dom.createTextNode ("" + test.getColumns ()));
		root.appendChild (e);

		e = dom.createElement ("none");
		e.appendChild (dom.createTextNode ("" + test.isNone ()));
		root.appendChild (e);

		e = dom.createElement ("none-text");
		e.appendChild (dom.createTextNode (test.getNoneText ()));
		root.appendChild (e);

		e = dom.createElement ("blocks");
		e.appendChild (dom.createTextNode ("" + test.isBlocks ()));
		root.appendChild (e);

		e = dom.createElement ("format");
		e.appendChild (dom.createTextNode (test.getFormat ().toString ()));
		root.appendChild (e);

		e = dom.createElement ("code");
		e.appendChild (dom.createTextNode ("" + test.getCode ()));
		root.appendChild (e);

		e = dom.createElement ("code-text");
		e.appendChild (dom.createTextNode (test.getCodeText ()));
		root.appendChild (e);

		e = dom.createElement ("language");
		e.appendChild (dom.createTextNode (test.getLanguage ().toString ()));
		root.appendChild (e);

		e = dom.createElement ("question-text");
		e.appendChild (dom.createTextNode (test.getQuestionText ()));
		root.appendChild (e);

		e = dom.createElement ("name-text");
		e.appendChild (dom.createTextNode (test.getNameText ()));
		root.appendChild (e);

		e = dom.createElement ("even-pages");
		e.appendChild (dom.createTextNode ("" + test.isEvenPages ()));
		root.appendChild (e);

		e = dom.createElement ("blank-page");
		e.appendChild (dom.createTextNode ("" + test.isBlankPage ()));
		root.appendChild (e);

		e = dom.createElement ("color");
		e.appendChild (dom.createTextNode (test.getColor ()));
		root.appendChild (e);

		return root;
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
