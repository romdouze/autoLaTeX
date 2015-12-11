/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.autolatex;

import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.WindowConstants;
import ngr.KiKi.autolatex.data.Test;
import ngr.KiKi.autolatex.views.JFrameMain;

/**
 *
 * @author KiKi
 */
public class AMCAutoLateX
{

	/**
	 * @param args the command line arguments
	 */
	public static void main (String args[])
	{
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try
		{
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels ())
				if ("Nimbus".equals (info.getName ()))
				{
					javax.swing.UIManager.setLookAndFeel (info.getClassName ());
					break;
				}
		}
		catch (ClassNotFoundException ex)
		{
			java.util.logging.Logger.getLogger (JFrameMain.class.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
		}
		catch (InstantiationException ex)
		{
			java.util.logging.Logger.getLogger (JFrameMain.class.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
		}
		catch (IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger (JFrameMain.class.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
		}
		catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger (JFrameMain.class.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>
		//</editor-fold>

		/* Create and display the form */
		JFrameMain frame = new JFrameMain ();
		start (frame);
	}

	private static void start (JFrameMain frame)
	{
		java.awt.EventQueue.invokeLater (() ->
		{
			frame.setDefaultCloseOperation (WindowConstants.DO_NOTHING_ON_CLOSE);
			frame.addWindowListener (new WindowAdapter ()
			{
				@Override
				public void windowClosing (WindowEvent e)
				{
					frame.exit ();
				}
			});
			frame.setVisible (true);
		});
	}

	public static void reset (JFrameMain frame)
	{
		Point location = frame.getLocationOnScreen ();
		frame.dispose ();
		frame = new JFrameMain ();
		frame.setLocation (location);
		start (frame);
	}
	
	public static void reload(JFrameMain frame)
	{
		Point location = frame.getLocationOnScreen ();
		Test test = frame.getTest();
		frame.dispose ();
		frame = new JFrameMain (test);
		frame.setLocation (location);
		start (frame);
	}
}
