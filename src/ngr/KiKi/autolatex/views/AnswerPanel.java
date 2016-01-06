/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex.views;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import ngr.KiKi.autolatex.data.Answer;

/**
 *
 * @author KiKi
 */
public class AnswerPanel extends javax.swing.JPanel
{

	private final Answer answer;
	private final QuestionPanel parent;
	private boolean init;

	/**
	 * Creates new form AnswerPanel
	 */
	public AnswerPanel (QuestionPanel p, Answer a)
	{
		initComponents ();

		init = true;

		parent = p;
		answer = a;

		init ();

		init = false;
	}

	private void init ()
	{
		jCheckBoxCorrect.setSelected (answer.isCorrect ());
		jCheckBoxCorrect.addActionListener ((ActionEvent ae) ->
		{
			jTextFieldScore.setText ("" + (jCheckBoxCorrect.isSelected () ? parent.getFrame ().getTest ().getDefaultScoreCorrect () : parent.getFrame ().getTest ().getDefaultScoreIncorrect ()));
			if (jCheckBoxCorrect.isSelected ())
				parent.checkMultiple ();
			update ();
		});

		DocumentListener dc = new DocumentListener ()
		{

			@Override
			public void insertUpdate (DocumentEvent de)
			{
				update ();
			}

			@Override
			public void removeUpdate (DocumentEvent de)
			{
				update ();
			}

			@Override
			public void changedUpdate (DocumentEvent de)
			{
				update ();
			}
		};

		jTextAreaText.setText (answer.toString ());
		jTextAreaText.getDocument ().addDocumentListener (dc);

		jTextFieldScore.setText ("" + answer.getScore ());
		jTextFieldScore.getDocument ().addDocumentListener (dc);
	}

	public Answer getAnswer ()
	{
		return answer;
	}

	private void update ()
	{
		if (!init)
		{
			answer.setCorrect (jCheckBoxCorrect.isSelected ());
			answer.setText (jTextAreaText.getText ());
			try
			{
				answer.setScore (Integer.valueOf (jTextFieldScore.getText ()));
			}
			catch (NumberFormatException ex)
			{

			}
		}
	}

	public void setScoreVisibility (boolean b)
	{
		jPanelScore.setVisible (b);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jCheckBoxCorrect = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaText = new javax.swing.JTextArea();
        jButtonDelete = new javax.swing.JButton();
        jPanelScore = new javax.swing.JPanel();
        jTextFieldScore = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(350, 40));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTextAreaText.setColumns(20);
        jTextAreaText.setRows(2);
        jScrollPane1.setViewportView(jTextAreaText);

        jButtonDelete.setText("-");
        jButtonDelete.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonDelete.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jTextFieldScore.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText("Score");

        javax.swing.GroupLayout jPanelScoreLayout = new javax.swing.GroupLayout(jPanelScore);
        jPanelScore.setLayout(jPanelScoreLayout);
        jPanelScoreLayout.setHorizontalGroup(
            jPanelScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScoreLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanelScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldScore, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );
        jPanelScoreLayout.setVerticalGroup(
            jPanelScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScoreLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBoxCorrect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonDelete)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonDelete)
                    .addComponent(jCheckBoxCorrect))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonDeleteActionPerformed
    {//GEN-HEADEREND:event_jButtonDeleteActionPerformed
		if (JOptionPane.showOptionDialog (parent.getFrame (), "Êtes-vous sûr de vouloir supprimer cette réponse ?", "Supprimer réponse", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]
		{
			"Oui", "Non"
		}, null) == JOptionPane.YES_OPTION)
			parent.removeAnswer (this);
    }//GEN-LAST:event_jButtonDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JCheckBox jCheckBoxCorrect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanelScore;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaText;
    private javax.swing.JTextField jTextFieldScore;
    // End of variables declaration//GEN-END:variables
}
