package editor;

import main.EditorFrame;
import settings.Colors;
import settings.Fonts;
import swing.TabsPanel;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Editor extends JTextPane {
	public Editor() {
		setBackground(Colors.MAIN_BACKGROUND_COLOR);
		setForeground(Colors.MAIN_FOREGROUND_COLOR);
		setFont(Fonts.MAIN_FONT);
		setCaretColor(Colors.CURSOR_COLOR);
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				Parser.parse();
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
	}

	public void open(File f) {
		//System.out.println("test");
		TabsPanel.open(f);
		//System.out.println("test");
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			String s = "";
			while ((s = bf.readLine()) != null) {
				StyledDocument sd = getStyledDocument();
				sd.insertString(sd.getLength(), s + '\n', new SimpleAttributeSet());
			}
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		Parser.parse();
		this.repaint();
		EditorFrame.getTabsPanel().repaint();
	}
}
