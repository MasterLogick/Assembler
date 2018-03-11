import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Vector;

public class EditorFrame extends JFrame {
    private static Vector<File> tabs = new Vector<File>();
    Editor editor = new Editor();

    public EditorFrame() {
        super("Assembler IDE");
        setBackground(Colors.MAIN_BACKGROUND_COLOR);
        setForeground(Colors.MAIN_FOREGROUND_COLOR);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                EditorFrame.super.dispose();
                editor.stop();
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
        main.add(new Tab("tEsTafff"));
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
        JScrollPane jsp = new JScrollPane();
        jsp.setBackground(Colors.MAIN_BACKGROUND_COLOR);
        jsp.getViewport().add(editor);
        //JPanel main = new JPanel();
        /*JToolBar tool = new JToolBar();
        tool.add(new JButton("aaa"));
        add(tool);*/

        main.add(jsp);
        add(main);
        setMinimumSize(new Dimension(400,400));
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);

    }

    public static void closeALL() {
        tabs.removeAllElements();
    }

    public static void open(File file) {
        tabs.add(file);
    }
}