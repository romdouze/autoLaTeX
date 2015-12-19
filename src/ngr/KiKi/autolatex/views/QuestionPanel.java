/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import ngr.KiKi.autolatex.data.Answer;
import ngr.KiKi.autolatex.data.Question;
import ngr.KiKi.autolatex.utils.Utils;

/**
 *
 * @author KiKi
 */
public class QuestionPanel extends javax.swing.JPanel
{

	private final Question question;
	private final JFrameMain parent;
	private boolean init;

	/**
	 * Creates new form QuestionPanel
	 */
	public QuestionPanel (JFrameMain p, Question q)
	{
		initComponents ();
		init = true;

		question = q;
		parent = p;

		init ();

		init = false;
	}

	private void init ()
	{
		DocumentListener dc = new DocumentListener ()
		{

			@Override
			public void insertUpdate (DocumentEvent de)
			{
				updateValues ();
			}

			@Override
			public void removeUpdate (DocumentEvent de)
			{
				updateValues ();
			}

			@Override
			public void changedUpdate (DocumentEvent de)
			{
				updateValues ();
			}

		};

		jTextAreaText.setText (question.toString ());
		jTextAreaText.getDocument ().addDocumentListener (dc);

		jTextFieldNbLines.setText ("" + question.getNbLines ());
		jTextFieldNbLines.getDocument ().addDocumentListener (dc);

		jComboBoxType.setSelectedItem (question.getType ().toString ());

		initAnswers ();

		initGroups ();

		updateDisplay ();
	}

	private void initGroups ()
	{
		jComboBoxGroup.removeAllItems ();
		Map<String, Color> groups = parent.getGroups ();
		groups.keySet ().stream ().forEach ((g) ->
		{
			jComboBoxGroup.addItem (g);
		});
		MyColorComboBoxRenderer renderer = new MyColorComboBoxRenderer (jComboBoxGroup);
		renderer.setValues (groups);
		jComboBoxGroup.setSelectedItem (question.getGroup ());
		jComboBoxGroup.setRenderer (renderer);
	}

	private void initAnswers ()
	{
		jPanelAnswers.removeAll ();
		jPanelAnswers.setLayout (new MigLayout (new LC ().fillX ().hideMode (3)));

		question.getAnswers ().stream ().forEach ((a) ->
		{
			jPanelAnswers.add (new AnswerPanel (this, a), new CC ().wrap ().growX ());
		});

		jPanelAnswers.add (jButtonAddAnswer);
	}

	public void removeAnswer (AnswerPanel ap)
	{
		question.getAnswers ().remove (ap.getAnswer ());
		initAnswers ();

		updateDisplay ();
	}

	private void updateDisplay ()
	{
		((TitledBorder) jScrollPaneAnswers.getBorder ()).setTitle (question.getAnswers ().size () + " réponses");

		parent.updateQR ();
		parent.repaint ();
	}

	private void updateValues ()
	{
		if (!init)
		{
			question.setGroup ((String) jComboBoxGroup.getSelectedItem ());
			question.setText (jTextAreaText.getText ());
			question.setType (Question.TYPE.getType ((String) jComboBoxType.getSelectedItem ()));
			try
			{
				question.setNbLines (Integer.valueOf (jTextFieldNbLines.getText ()));
			}
			catch (NumberFormatException ex)
			{

			}
			parent.updateQR ();
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaText = new javax.swing.JTextArea();
        jPanelConfig = new javax.swing.JPanel();
        jComboBoxType = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButtonAddPicture = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNbLines = new javax.swing.JTextField();
        jScrollPaneAnswers = new javax.swing.JScrollPane();
        jPanelAnswers = new javax.swing.JPanel();
        jButtonAddAnswer = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jComboBoxGroup = new javax.swing.JComboBox();
        jButtonAddCategory = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(389, 168));
        setPreferredSize(new java.awt.Dimension(389, 168));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Intitulé"));

        jTextAreaText.setColumns(20);
        jTextAreaText.setRows(3);
        jScrollPane1.setViewportView(jTextAreaText);

        jPanelConfig.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuration"));

        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Simple", "Multiple", "Ouverte", "Informative" }));
        jComboBoxType.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jComboBoxTypeActionPerformed(evt);
            }
        });

        jLabel1.setText("Type");

        jButtonAddPicture.setText("Ajouter photo");

        jLabel2.setText("Nombre de lignes");

        jTextFieldNbLines.setText("2");

        javax.swing.GroupLayout jPanelConfigLayout = new javax.swing.GroupLayout(jPanelConfig);
        jPanelConfig.setLayout(jPanelConfigLayout);
        jPanelConfigLayout.setHorizontalGroup(
            jPanelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelConfigLayout.createSequentialGroup()
                        .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonAddPicture))
                    .addGroup(jPanelConfigLayout.createSequentialGroup()
                        .addComponent(jTextFieldNbLines, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelConfigLayout.setVerticalGroup(
            jPanelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConfigLayout.createSequentialGroup()
                .addGroup(jPanelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(jButtonAddPicture))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldNbLines, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 60, Short.MAX_VALUE))
        );

        jScrollPaneAnswers.setBorder(javax.swing.BorderFactory.createTitledBorder("Réponses"));

        jButtonAddAnswer.setText("+");
        jButtonAddAnswer.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonAddAnswer.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonAddAnswerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAnswersLayout = new javax.swing.GroupLayout(jPanelAnswers);
        jPanelAnswers.setLayout(jPanelAnswersLayout);
        jPanelAnswersLayout.setHorizontalGroup(
            jPanelAnswersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAnswersLayout.createSequentialGroup()
                .addComponent(jButtonAddAnswer)
                .addGap(0, 405, Short.MAX_VALUE))
        );
        jPanelAnswersLayout.setVerticalGroup(
            jPanelAnswersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAnswersLayout.createSequentialGroup()
                .addComponent(jButtonAddAnswer)
                .addGap(0, 185, Short.MAX_VALUE))
        );

        jScrollPaneAnswers.setViewportView(jPanelAnswers);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Catégorie"));

        jComboBoxGroup.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jComboBoxGroupActionPerformed(evt);
            }
        });

        jButtonAddCategory.setText("+");
        jButtonAddCategory.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonAddCategory.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonAddCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxGroup, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAddCategory))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAddCategory))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelConfig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPaneAnswers, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneAnswers, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxGroupActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBoxGroupActionPerformed
    {//GEN-HEADEREND:event_jComboBoxGroupActionPerformed

		updateValues ();
    }//GEN-LAST:event_jComboBoxGroupActionPerformed

    private void jButtonAddAnswerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddAnswerActionPerformed
    {//GEN-HEADEREND:event_jButtonAddAnswerActionPerformed
		Answer newAnswer = question.addAnswer ("Nouvelle réponse", false);
		jPanelAnswers.remove (jButtonAddAnswer);
		jPanelAnswers.add (new AnswerPanel (this, newAnswer), new CC ().wrap ().growX ());
		jPanelAnswers.add (jButtonAddAnswer);

		updateDisplay ();
    }//GEN-LAST:event_jButtonAddAnswerActionPerformed

    private void jButtonAddCategoryActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddCategoryActionPerformed
    {//GEN-HEADEREND:event_jButtonAddCategoryActionPerformed

		JPanel panel = new JPanel ();
		JDialog dialog = new JDialog (parent, "Nouvelle catégorie", true);

		JTextField tf = new JTextField ("Nouvelle catégorie...");
		DefaultComboBoxModel cbm = new DefaultComboBoxModel ();
		Utils.colors.keySet ().stream ().forEach ((s) ->
		{
			cbm.addElement (s);
		});
		JComboBox cols = new JComboBox (cbm);
		cols.addActionListener ((ActionEvent ae) ->
		{
			tf.setForeground (Utils.colors.get ((String) cols.getSelectedItem ()));
		});
		cols.setSelectedItem ("Noir");
		panel.add (tf);
		panel.add (cols);

		JButton ok = new JButton ("OK");
		JButton cancel = new JButton ("Annuler");

		ok.addActionListener ((ActionEvent ae) ->
		{
			if (tf.getText ().equals ("Nouvelle catégorie...") || tf.getText ().isEmpty () || parent.getGroups ().containsKey (tf.getText ()))
				dialog.dispose ();

			String newGroup = tf.getText ();
			parent.addGroup (newGroup, Utils.colors.get ((String) cols.getSelectedItem ()));
			initGroups ();
			jComboBoxGroup.setSelectedItem (newGroup);
			updateValues ();
			dialog.dispose ();
		});
		cancel.addActionListener ((ActionEvent ae) ->
		{
			dialog.dispose ();
		});

		JButton[] buttons =
		{
			ok, cancel
		};
		JOptionPane optionPane = new JOptionPane (panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION, null, buttons, ok);

		dialog.getContentPane ().add (optionPane);
		dialog.setLocationRelativeTo (parent);
		dialog.pack ();
		dialog.setVisible (true);
    }//GEN-LAST:event_jButtonAddCategoryActionPerformed

    private void jComboBoxTypeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBoxTypeActionPerformed
    {//GEN-HEADEREND:event_jComboBoxTypeActionPerformed
		if (!((String) jComboBoxType.getSelectedItem ()).toUpperCase ().equals (question.getType ().toString ()))
			updateValues ();
    }//GEN-LAST:event_jComboBoxTypeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddAnswer;
    private javax.swing.JButton jButtonAddCategory;
    private javax.swing.JButton jButtonAddPicture;
    private javax.swing.JComboBox jComboBoxGroup;
    private javax.swing.JComboBox jComboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelAnswers;
    private javax.swing.JPanel jPanelConfig;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneAnswers;
    private javax.swing.JTextArea jTextAreaText;
    private javax.swing.JTextField jTextFieldNbLines;
    // End of variables declaration//GEN-END:variables
}
