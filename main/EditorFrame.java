package main;

import editor.Editor;
import integration.VBox;
import project.Project;
import settings.Colors;
import swing.TabsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class EditorFrame extends JFrame {
	public static EditorFrame getInstance() {
		return instance;
	}

	public static Editor getEditor() {
		return editor;
	}

	private static EditorFrame instance;
	private static Editor editor;
	private static TabsPanel tabsPanel;

	public static void main(String[] args) {
		System.out.println(System.getProperty("vbox.home"));
		new VBox().init();
		/*
		Parser.init();
		instance = new EditorFrame();
		editor = new Editor();
		tabsPanel = new TabsPanel();
		EditorFrame.init();*/
	}

	private static void init() {
		JPanel main = new JPanel();
		JScrollPane jsp = new JScrollPane();
		//JTabbedPane jtp=new JTabbedPane();

		jsp.setForeground(Colors.MAIN_FOREGROUND_COLOR);
		jsp.setBackground(Colors.MAIN_BACKGROUND_COLOR);
        /*jtp.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        jtp.setBackground(Colors.MAIN_BACKGROUND_COLOR);*/
		instance.setBackground(Colors.MAIN_BACKGROUND_COLOR);
		instance.setForeground(Colors.MAIN_FOREGROUND_COLOR);
		jsp.setBackground(Colors.MAIN_BACKGROUND_COLOR);

		getInstance().addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {
				//todo
				getInstance().dispose();
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}
		});

		getInstance().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		jsp.getViewport().add(editor);
		main.add(tabsPanel);
		main.add(editor);
		instance.add(main);
		instance.setMinimumSize(new Dimension(400, 400));
		instance.setExtendedState(MAXIMIZED_BOTH);
		instance.setJMenuBar(menuInit());
		instance.repaint();
		instance.setVisible(true);
	}

	private static JMenuBar menuInit() {
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem open = new JMenuItem("Open...");
		JMenuItem create = new JMenuItem("Create Project");
		bar.setForeground(Colors.MAIN_FOREGROUND_COLOR);
		bar.setBackground(Colors.MAIN_BACKGROUND_COLOR);
		menu.setForeground(Colors.MAIN_FOREGROUND_COLOR);
		menu.setBackground(Colors.MAIN_BACKGROUND_COLOR);
		open.setForeground(Colors.MAIN_FOREGROUND_COLOR);
		open.setBackground(Colors.MAIN_BACKGROUND_COLOR);
		create.setForeground(Colors.MAIN_FOREGROUND_COLOR);
		create.setBackground(Colors.MAIN_BACKGROUND_COLOR);
		menu.getPopupMenu().setBackground(Colors.MAIN_BACKGROUND_COLOR);
		menu.getPopupMenu().setForeground(Colors.MAIN_FOREGROUND_COLOR);

		create.addActionListener(e -> {
			Project.createNew();
			System.out.println("123");
		});
		open.addActionListener(e -> {
			Project.openProject();
			System.out.println("asd");
		});
        /*menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instance.repaint(1000);
            }
        });*/

		bar.setBorderPainted(false);

		menu.add(open);
		menu.add(create);
		bar.add(menu);

		return bar;
	}

	public static TabsPanel getTabsPanel() {
		return tabsPanel;
	}
}
