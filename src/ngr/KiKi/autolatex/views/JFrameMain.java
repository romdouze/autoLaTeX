/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import ngr.KiKi.autolatex.AMCAutoLateX;
import ngr.KiKi.autolatex.data.Question;
import ngr.KiKi.autolatex.data.TEXHandler;
import ngr.KiKi.autolatex.data.Test;
import ngr.KiKi.autolatex.data.XMLHandler;
import ngr.KiKi.autolatex.utils.Utils;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

/**
 *
 * @author KiKi
 */
public class JFrameMain extends javax.swing.JFrame
{

	private Test test;
	private QuestionRenderer selectedRenderer;

	private boolean init;

	/**
	 * Creates new form AMCAutoLateX
	 */
	public JFrameMain ()
	{
		initComponents ();

		init = true;

		setTemplate ();
		init ();

		init = false;
	}

	public JFrameMain (Test t)
	{
		initComponents ();

		init = true;

		test = t;

		init ();

		init = false;
	}

	private void setTemplate ()
	{
		test = new Test ();
		List<Question> questions = new ArrayList<> ();
		Map<String, Color> groups = new HashMap<> ();

		groups.put ("Défaut", Color.BLACK);

		for (int i = 0; i < 5; i++)
		{
			Question q = new Question ("Quelle était la couleur du cheval blanc d'Henry " + i + " ? C'est la question ultime", Question.TYPE.SIMPLE);
			q.addAnswer ("Faut voir...", false);
			q.addAnswer ("Beige", false);
			questions.add (q);
		}

		test.setQuestions (questions);
		test.setGroups (groups);

		test.setTitle ("Intitulé du devoir");
		test.setColumns (2);
		test.setNoneText ("Aucune des réponses ci-dessus");
		test.setCodeText ("Coder votre numéro étudiant");
		test.setQuestionText ("Question");
		test.setNameText ("Nom et prénom");
		test.setEvenPages (true);
		test.setColor ("Rouge");
	}

	private void init ()
	{

		setTitle ("AMC - AutoLaTeX : " + test.getTitle ());
		jTextFieldTitle.setText (test.getTitle ());
		jTextAreaDescription.setText (test.getDescription ());
		jCheckBoxShuffle.setSelected (test.isShuffle ());
		jTextFieldColumns.setText ("" + test.getColumns ());
		jCheckBoxNone.setSelected (test.isNone ());
		jTextFieldNoneText.setText (test.getNoneText ());
		jCheckBoxBlocks.setSelected (test.isBlocks ());
		jComboBoxFormat.setSelectedItem (test.getFormat ().toString ());
		jTextFieldCode.setText ("" + test.getCode ());
		jTextFieldCodeText.setText (test.getCodeText ());
		jComboBoxLanguage.setSelectedItem (test.getLanguage ().toString ());
		jTextFieldQuestionText.setText (test.getQuestionText ());
		jTextFieldNameText.setText (test.getNameText ());
		jCheckBoxEvenPages.setSelected (test.isEvenPages ());
		jCheckBoxBlankPage.setSelected (test.isBlankPage ());
		jComboBoxColor.setSelectedItem (test.getColor ());

		DocumentListener docListener = new DocumentListener ()
		{

			@Override
			public void insertUpdate (DocumentEvent de)
			{
				updateTestValues ();
			}

			@Override
			public void removeUpdate (DocumentEvent de)
			{
				updateTestValues ();
			}

			@Override
			public void changedUpdate (DocumentEvent de)
			{
				updateTestValues ();
			}
		};

		ChangeListener changeListener = (ChangeEvent ce) ->
		{
			updateTestValues ();
		};

		ActionListener actionListener = (ActionEvent ae) ->
		{
			updateTestValues ();
		};

		jTextFieldTitle.getDocument ().addDocumentListener (docListener);
		jTextAreaDescription.getDocument ().addDocumentListener (docListener);
		jCheckBoxShuffle.addChangeListener (changeListener);
		jTextFieldColumns.getDocument ().addDocumentListener (docListener);
		jCheckBoxNone.addChangeListener (changeListener);
		jTextFieldNoneText.getDocument ().addDocumentListener (docListener);
		jCheckBoxBlocks.addChangeListener (changeListener);
		jComboBoxFormat.addActionListener (actionListener);
		jTextFieldCode.getDocument ().addDocumentListener (docListener);
		jTextFieldCodeText.getDocument ().addDocumentListener (docListener);
		jComboBoxLanguage.addActionListener (actionListener);
		jTextFieldQuestionText.getDocument ().addDocumentListener (docListener);
		jTextFieldNameText.getDocument ().addDocumentListener (docListener);
		jCheckBoxEvenPages.addChangeListener (changeListener);
		jCheckBoxBlankPage.addChangeListener (changeListener);
		jComboBoxColor.addActionListener (actionListener);

		initQuestions ();
//		formulas ();
	}

	private void updateTestValues ()
	{
		if (init)
			return;

		setTitle ("AMC - AutoLaTeX : " + jTextFieldTitle.getText ());
		test.setTitle (jTextFieldTitle.getText ());
		test.setDescription (jTextAreaDescription.getText ());
		test.setShuffle (jCheckBoxShuffle.isSelected ());
		test.setNone (jCheckBoxNone.isSelected ());
		test.setNoneText (jTextFieldNoneText.getText ());
		test.setBlocks (jCheckBoxBlocks.isSelected ());
		test.setFormat (Test.Format.valueOf ((String) jComboBoxFormat.getSelectedItem ()));
		test.setCodeText (jTextFieldCodeText.getText ());
		test.setLanguage (Test.Language.valueOf ((String) jComboBoxLanguage.getSelectedItem ()));
		test.setQuestionText (jTextFieldQuestionText.getText ());
		test.setNameText (jTextFieldNameText.getText ());
		test.setEvenPages (jCheckBoxEvenPages.isSelected ());
		test.setBlankPage (jCheckBoxBlankPage.isSelected ());
		test.setColor ((String) jComboBoxColor.getSelectedItem ());

		try
		{
			test.setColumns (Integer.valueOf (jTextFieldColumns.getText ()));
			test.setCode (Integer.valueOf (jTextFieldCode.getText ()));
		}
		catch (NumberFormatException ex)
		{

		}
	}

	private void initQuestions ()
	{
		List<Question> questions = test.getQuestions ();

		jPanelQuestionsList.removeAll ();
		jPanelQuestionsList.setLayout (new MigLayout (new LC ().fillX ().hideMode (3)));
		for (int i = 0; i < questions.size (); i++)
		{
			JPanel panel = new JPanel (new BorderLayout ());
			Question q = questions.get (i);
			QuestionRenderer qr = new QuestionRenderer (this, q, i + 1);
			JButton deleteQuestion = new JButton ("X");
			deleteQuestion.addActionListener ((ActionEvent ae) ->
			{
				questions.remove (q);
				initQuestions ();
				if (selectedRenderer != null && selectedRenderer.getQuestion () == q)
					switchQuestionPanel (null);
			});

			panel.add (qr, BorderLayout.CENTER);
			panel.add (deleteQuestion, BorderLayout.EAST);

			JPanel upDown = new JPanel (new BorderLayout ());
			JButton up = new JButton ("\u25B2");
			up.setEnabled (i != 0);
			JButton down = new JButton ("\u25BC");
			down.setEnabled (i != questions.size () - 1);
			up.addActionListener ((ActionEvent ae) ->
			{
				int index = questions.indexOf (q);
				questions.remove (q);
				questions.add (index - 1, q);
				initQuestions ();
			});
			down.addActionListener ((ActionEvent ae) ->
			{
				int index = questions.indexOf (q);
				questions.remove (q);
				questions.add (index + 1, q);
				initQuestions ();
			});

			upDown.add (up, BorderLayout.NORTH);
			upDown.add (down, BorderLayout.SOUTH);
			panel.add (upDown, BorderLayout.WEST);

			jPanelQuestionsList.add (panel, new CC ().wrap ().growX ());
		}
		jPanelQuestionsList.add (jButtonNewQuestion);

		jPanelQuestion.setLayout (new BorderLayout ());

		repaint ();
	}

	public void updateQR ()
	{
		selectedRenderer.updateColor ();
		selectedRenderer.updateText ();
	}

	public void switchQuestionPanel (QuestionRenderer qr)
	{
		if (qr == null)
			jPanelQuestion.removeAll ();
		else
		{
			Question question = qr.getQuestion ();
			selectedRenderer = qr;
			jPanelQuestion.removeAll ();
			jPanelQuestion.add (new QuestionPanel (this, question));
		}

		repaint ();
	}

	public void addGroup (String g, Color c)
	{
		test.getGroups ().put (g, c);
	}

	public Map<String, Color> getGroups ()
	{
		return test.getGroups ();
	}

	private void formulas ()
	{
		String math = "\\frac {V_m} {K_M+S}";
		String bigFormula = "\\[\\forall f\\in C^\\infty\\left(\\left[-\\frac{T}{2};\\frac{T}{2}\\right]\\right),\n"
				+ "\\forall t\\in \\left[-\\frac{T}{2};\\frac{T}{2}\\right],\n"
				+ "f(\\tau) = \\sum_{k = -\\infty}^{+\\infty} e^{2i\\pi\\frac{k}{T}t} \\times\n"
				+ "\\underbrace{\\frac{1}{T}\n"
				+ "\\int_{-\\frac{T}{2}}^{\\frac{T}{2}} f(t) e^{-2i\\pi\\frac{k}{T}t} dt\n"
				+ "}_{a_k = \\tilde{f}\\left(\\nu = \\frac{k}{T}\\right)}\n"
				+ "\\]\n";

		String chemistry = "\\mathrm{ H_{3}O^{+} + OH^{-} \\rightleftharpoons 2H_{2}O";

		TeXFormula chemistryTexFormula = new TeXFormula (chemistry);
		TeXIcon icon = chemistryTexFormula.new TeXIconBuilder ().setStyle (TeXConstants.STYLE_DISPLAY).setSize (20).build ();

		icon.setInsets (new Insets (5, 5, 5, 5));
		BufferedImage image = new BufferedImage (icon.getIconWidth (), icon.getIconHeight (), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics ();
		g2.setColor (Color.white);
		g2.fillRect (0, 0, icon.getIconWidth (), icon.getIconHeight ());
		JLabel jl = new JLabel ();
		jl.setForeground (new Color (0, 0, 0));
		icon.paintIcon (jl, g2, 0, 0);
		File file = new File ("Example2.png");
		try
		{
			ImageIO.write (image, "png", file.getAbsoluteFile ());
		}
		catch (IOException ex)
		{
		}

//		FormulaPanel fp = new FormulaPanel (math);
//		JPanel p = new JPanel ();
//		p.setBorder (new LineBorder (Color.red));
//		p.setLayout (new BorderLayout ());
//		p.add (fp, BorderLayout.NORTH);
//		add (p);
//
//		pack ();
//		repaint ();
	}

	public void exit ()
	{
		if (JOptionPane.showOptionDialog (this, "Êtes-vous sûr de vouloir quitter ?", "Quitter", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]
		{
			"Oui", "Non"
		}, null) == JOptionPane.YES_OPTION)
		{
			try
			{
				FileOutputStream fos = new FileOutputStream (new File (Utils.PROPERTIES_FILENAME));
				AMCAutoLateX.properties.store (fos, "");
			}
			catch (IOException ex)
			{
				Logger.getLogger (JFrameMain.class.getName ()).log (Level.SEVERE, null, ex);
			}
			dispose ();
		}
	}

	public Test getTest ()
	{
		return test;
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jTabbedPaneMain = new javax.swing.JTabbedPane();
        jPanelDetails = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jComboBoxFormat = new javax.swing.JComboBox();
        jTextFieldCode = new javax.swing.JTextField();
        jTextFieldCodeText = new javax.swing.JTextField();
        jComboBoxLanguage = new javax.swing.JComboBox();
        jTextFieldQuestionText = new javax.swing.JTextField();
        jTextFieldNameText = new javax.swing.JTextField();
        jCheckBoxEvenPages = new javax.swing.JCheckBox();
        jCheckBoxBlankPage = new javax.swing.JCheckBox();
        jComboBoxColor = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldTitle = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDescription = new javax.swing.JTextArea();
        jCheckBoxShuffle = new javax.swing.JCheckBox();
        jTextFieldColumns = new javax.swing.JTextField();
        jCheckBoxNone = new javax.swing.JCheckBox();
        jTextFieldNoneText = new javax.swing.JTextField();
        jCheckBoxBlocks = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanelQuestions = new javax.swing.JPanel();
        jScrollPaneQuestionsList = new javax.swing.JScrollPane();
        jPanelQuestionsList = new javax.swing.JPanel();
        jButtonNewQuestion = new javax.swing.JButton();
        jPanelQuestion = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AMC - AutoLaTeX");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Formatage"));

        jLabel1.setText("Format");

        jLabel6.setText("Code");

        jLabel2.setText("Langue");

        jLabel11.setText("\"Question\"");

        jLabel12.setText("\"Nom et prénom\"");

        jLabel13.setText("Texte");

        jLabel14.setText("Nombre de pages pair");

        jLabel15.setText("<html>Page blanche avant<br/> feuille de réponses</html>");

        jLabel16.setText("Couleur cases");

        jComboBoxFormat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A3", "A4", "A5", "A6", "B3", "B4", "B5", "B6", "letter", "legal", "ANSIA", "ANSIB", "ANSIC", "ANSID", "ANSIE" }));

        jTextFieldCode.setText("8");

        jTextFieldCodeText.setText("Coder votre numéro étudiant");

        jComboBoxLanguage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FR", "ES", "DE", "IT", "NL", "NO", "JA" }));

        jTextFieldQuestionText.setText("Question");

        jTextFieldNameText.setText("Nom et prénom");

        jCheckBoxEvenPages.setSelected(true);

        jCheckBoxBlankPage.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jCheckBoxBlankPageActionPerformed(evt);
            }
        });

        jComboBoxColor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rouge", "Magenta", "Rose", "Gris clair", "Cyan" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCodeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldQuestionText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxEvenPages)
                    .addComponent(jCheckBoxBlankPage)
                    .addComponent(jComboBoxColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldCodeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldQuestionText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jCheckBoxEvenPages))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxBlankPage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(jComboBoxColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Informations générales"));

        jLabel7.setText("Colonnes");

        jLabel3.setText("Titre");

        jLabel4.setText("Présentation");

        jLabel5.setText("Mélange Questions");

        jLabel8.setText("Option \"Aucune des réponses\"");

        jLabel9.setText("Texte");

        jLabel10.setText("Questions par \"blocs\"");

        jLabel17.setText("Barême questions simples");

        jLabel18.setText("Barême questions multiples");

        jTextFieldTitle.setText("Intitulé du devoir");

        jTextAreaDescription.setColumns(20);
        jTextAreaDescription.setRows(3);
        jScrollPane1.setViewportView(jTextAreaDescription);

        jTextFieldColumns.setText("2");

        jCheckBoxNone.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jCheckBoxNoneActionPerformed(evt);
            }
        });

        jTextFieldNoneText.setText("Aucune des réponses ci-dessus");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldTitle)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                    .addComponent(jCheckBoxShuffle)
                    .addComponent(jTextFieldColumns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxNone)
                    .addComponent(jTextFieldNoneText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxBlocks))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBoxShuffle)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldColumns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jCheckBoxNone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldNoneText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jCheckBoxBlocks))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addGap(193, 193, 193))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Avancé"));

        jLabel19.setText("LaTeX");

        jLabel20.setText("LaTeX-Preambule");

        jLabel21.setText("LaTeX-BeginDocument");

        jLabel22.setText("Disable");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelDetailsLayout = new javax.swing.GroupLayout(jPanelDetails);
        jPanelDetails.setLayout(jPanelDetailsLayout);
        jPanelDetailsLayout.setHorizontalGroup(
            jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetailsLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanelDetailsLayout.setVerticalGroup(
            jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 477, Short.MAX_VALUE)
            .addGroup(jPanelDetailsLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneMain.addTab("Détails du contrôle", jPanelDetails);

        jScrollPaneQuestionsList.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jButtonNewQuestion.setText("+");
        jButtonNewQuestion.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonNewQuestionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelQuestionsListLayout = new javax.swing.GroupLayout(jPanelQuestionsList);
        jPanelQuestionsList.setLayout(jPanelQuestionsListLayout);
        jPanelQuestionsListLayout.setHorizontalGroup(
            jPanelQuestionsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQuestionsListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonNewQuestion, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelQuestionsListLayout.setVerticalGroup(
            jPanelQuestionsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQuestionsListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonNewQuestion)
                .addContainerGap(462, Short.MAX_VALUE))
        );

        jScrollPaneQuestionsList.setViewportView(jPanelQuestionsList);

        javax.swing.GroupLayout jPanelQuestionLayout = new javax.swing.GroupLayout(jPanelQuestion);
        jPanelQuestion.setLayout(jPanelQuestionLayout);
        jPanelQuestionLayout.setHorizontalGroup(
            jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );
        jPanelQuestionLayout.setVerticalGroup(
            jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelQuestionsLayout = new javax.swing.GroupLayout(jPanelQuestions);
        jPanelQuestions.setLayout(jPanelQuestionsLayout);
        jPanelQuestionsLayout.setHorizontalGroup(
            jPanelQuestionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQuestionsLayout.createSequentialGroup()
                .addComponent(jScrollPaneQuestionsList, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelQuestion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelQuestionsLayout.setVerticalGroup(
            jPanelQuestionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneQuestionsList, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
            .addComponent(jPanelQuestion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPaneMain.addTab("Questions", jPanelQuestions);

        jMenu1.setText("File");

        jMenuItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemNew.setText("Nouveau");
        jMenuItemNew.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItemNewActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemNew);

        jMenuItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemOpen.setText("Ouvrir");
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItemOpenActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemOpen);

        jMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSave.setText("Enregistrer");
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemSave);

        jMenuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemExit.setText("Quitter");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem1.setText("Générer LaTeX");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneMain)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneMain)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBoxBlankPageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxBlankPageActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxBlankPageActionPerformed
		// TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxBlankPageActionPerformed

    private void jCheckBoxNoneActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxNoneActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxNoneActionPerformed
		// TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxNoneActionPerformed

    private void jButtonNewQuestionActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonNewQuestionActionPerformed
    {//GEN-HEADEREND:event_jButtonNewQuestionActionPerformed
		Question q = new Question ("Nouvelle question", Question.TYPE.SIMPLE);
		test.getQuestions ().add (q);
		initQuestions ();
    }//GEN-LAST:event_jButtonNewQuestionActionPerformed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemExitActionPerformed
    {//GEN-HEADEREND:event_jMenuItemExitActionPerformed

		exit ();
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void jMenuItemNewActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemNewActionPerformed
    {//GEN-HEADEREND:event_jMenuItemNewActionPerformed
		AMCAutoLateX.reset (this);
    }//GEN-LAST:event_jMenuItemNewActionPerformed

    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemSaveActionPerformed
    {//GEN-HEADEREND:event_jMenuItemSaveActionPerformed
		JFileChooser chooser = new JFileChooser ();
		chooser.setFileFilter (new FileNameExtensionFilter ("Fichier AutoLaTeX", Utils.EXTENSION, Utils.EXTENSION.toUpperCase ()));
		chooser.setCurrentDirectory (new File (AMCAutoLateX.properties.getProperty (Utils.PROPERTIES_RECENT_PATH) == null ? "" : AMCAutoLateX.properties.getProperty (Utils.PROPERTIES_RECENT_PATH)));
		chooser.setAcceptAllFileFilterUsed (false);

		if (chooser.showSaveDialog (this) != JFileChooser.APPROVE_OPTION)
			return;

		File file = chooser.getSelectedFile ();
		AMCAutoLateX.properties.setProperty (Utils.PROPERTIES_RECENT_PATH, file.getParent ());
		XMLHandler.XMLWriter (file.getAbsolutePath (), test);
    }//GEN-LAST:event_jMenuItemSaveActionPerformed

    private void jMenuItemOpenActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemOpenActionPerformed
    {//GEN-HEADEREND:event_jMenuItemOpenActionPerformed
		JFileChooser chooser = new JFileChooser ();
		chooser.setFileFilter (new FileNameExtensionFilter ("Fichier AutoLaTeX", Utils.EXTENSION, Utils.EXTENSION.toUpperCase ()));
		chooser.setCurrentDirectory (new File (AMCAutoLateX.properties.getProperty (Utils.PROPERTIES_RECENT_PATH) == null ? "" : AMCAutoLateX.properties.getProperty (Utils.PROPERTIES_RECENT_PATH)));
		chooser.setAcceptAllFileFilterUsed (false);

		if (chooser.showOpenDialog (this) != JFileChooser.OPEN_DIALOG)
			return;

		File file = chooser.getSelectedFile ();
		AMCAutoLateX.properties.setProperty (Utils.PROPERTIES_RECENT_PATH, file.getParent ());

		test = XMLHandler.XMLReader (file.getAbsolutePath ());

		AMCAutoLateX.reload (this);
    }//GEN-LAST:event_jMenuItemOpenActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
		TEXHandler.TEXWriter ("output.tex", test);
    }//GEN-LAST:event_jMenuItem1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonNewQuestion;
    private javax.swing.JCheckBox jCheckBoxBlankPage;
    private javax.swing.JCheckBox jCheckBoxBlocks;
    private javax.swing.JCheckBox jCheckBoxEvenPages;
    private javax.swing.JCheckBox jCheckBoxNone;
    private javax.swing.JCheckBox jCheckBoxShuffle;
    private javax.swing.JComboBox jComboBoxColor;
    private javax.swing.JComboBox jComboBoxFormat;
    private javax.swing.JComboBox jComboBoxLanguage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelDetails;
    private javax.swing.JPanel jPanelQuestion;
    private javax.swing.JPanel jPanelQuestions;
    private javax.swing.JPanel jPanelQuestionsList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneQuestionsList;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextField jTextFieldCode;
    private javax.swing.JTextField jTextFieldCodeText;
    private javax.swing.JTextField jTextFieldColumns;
    private javax.swing.JTextField jTextFieldNameText;
    private javax.swing.JTextField jTextFieldNoneText;
    private javax.swing.JTextField jTextFieldQuestionText;
    private javax.swing.JTextField jTextFieldTitle;
    // End of variables declaration//GEN-END:variables
}
