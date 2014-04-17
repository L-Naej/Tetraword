package game.inputs;

import java.awt.*;
import java.awt.event.*;

public class TestClavier extends java.applet.Applet implements KeyListener {
	
	String S="";
	
	public void init() {
		 addKeyListener(this);
	}
	
	public void start() {
		 requestFocus();
	}
	
	//Saisie des touches tapées au clavier
	public void keyPressed(KeyEvent evt) {
		char Caract = evt.getKeyChar();
		if (S.length()<10) 
			if (Caract!=0) {
				S = S + Caract;      
				repaint();
			} 
	}

	public void keyTyped(KeyEvent evt) { }

	public void keyReleased(KeyEvent evt) { }
	
	public void paint(Graphics g) {
		g.drawString("Ecrire un mot de 10 lettres...",10,30);
		g.drawString(S, 10, 60);
	}
	
}