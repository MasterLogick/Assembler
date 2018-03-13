import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Vector;

public class EditorPane extends JFrame {
    static Editor editor = new Editor();

    public EditorPane() {
        super("Assembler IDE");
        setBackground(Colors.MAIN_BACKGROUND_COLOR);
        setForeground(Colors.MAIN_FOREGROUND_COLOR);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Editor.saveAll();
                EditorPane.super.dispose();
                editor.stop();
                System.exit(0);
                //TODO show dialog:save changes
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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setVisible(false);
    }

    public void init() {
        TabsPane.setBack(Colors.MAIN_BACKGROUND_COLOR);
        JMenuBar bar = new JMenuBar();
        bar.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        bar.setBackground(Colors.MAIN_BACKGROUND_COLOR);
        JMenu menu = new JMenu("File");
        menu.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        menu.setBackground(Colors.MAIN_BACKGROUND_COLOR);
        JMenuItem item = new JMenuItem("Open...");
        item.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        item.setBackground(Colors.MAIN_BACKGROUND_COLOR);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
        JMenuItem item1 = new JMenuItem("Create Project");
        item1.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        item1.setBackground(Colors.MAIN_BACKGROUND_COLOR);
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Project.createNew();
            }
        });
        bar.setBorderPainted(false);
        menu.add(item);
        menu.add(item1);
        menu.getPopupMenu().setBackground(Colors.MAIN_BACKGROUND_COLOR);
        menu.getPopupMenu().setForeground(Colors.MAIN_FOREGROUND_COLOR);
        bar.add(menu);
        setJMenuBar(bar);
        JPanel main = new JPanel();
        /*Tab t = new Tab("tEsTafff");
        t.setForeground(Colors.TEXT_COLOR);
        t.setBackground(Colors.MAIN_BACKGROUND_COLOR);*/
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        //main.add(t);
        JScrollPane jsp = new JScrollPane();
        jsp.setBackground(Colors.MAIN_BACKGROUND_COLOR);
        jsp.getViewport().add(editor);
        main.add(new TabsPane());
        main.add(jsp);
        jsp.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        jsp.setBackground(Colors.MAIN_BACKGROUND_COLOR);
        setBackground(Colors.MAIN_BACKGROUND_COLOR);
        setForeground(Colors.MAIN_FOREGROUND_COLOR);
        add(main);
        setMinimumSize(new Dimension(400, 400));
        setExtendedState(MAXIMIZED_BOTH);
        open(new File("C:\\1.txt"));
        this.repaint();
        setVisible(true);

    }

    public static void open(File file) {
        Tab t = new Tab(file.getName());
        t.setForeground(Colors.TEXT_COLOR);
        t.setBackground(Colors.MAIN_BACKGROUND_COLOR);
        TabsPane.add(t);
        editor.openFile(file);
    }
}
