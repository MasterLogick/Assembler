package main;

import editor.Editor;
import editor.Parser;
import project.Project;
import settings.Colors;
import swing.TabsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

public class EditorFrame extends JFrame {
    public static EditorFrame getInstance() {
        return instance;
    }

    public static Editor getEditor() {
        return editor;
    }

    private static EditorFrame instance;
    private static Editor editor;

    public static void main(String[] args) {
        Parser.init();
        instance = new EditorFrame();
        editor = new Editor();
        EditorFrame.init();
    }

    private static void init() {
        JPanel main = new JPanel();
        JScrollPane jsp = new JScrollPane();

        jsp.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        jsp.setBackground(Colors.MAIN_BACKGROUND_COLOR);
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
        main.add(new TabsPanel());
        main.add(jsp);
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

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Project.createNew();
            }
        });
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Project.openProject();
            }
        });

        bar.setBorderPainted(false);

        menu.add(open);
        menu.add(create);
        bar.add(menu);

        return bar;
    }
}
