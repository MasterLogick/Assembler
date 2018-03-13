import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Project {

    private static File fileDir = null;

    public static void createNew() {

        JFrame dialog = new JFrame("Creating project");
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        JPanel name = new JPanel();                            //project name chooser
        name.setLayout(new BoxLayout(name, BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel("Project name");
        JTextField projectName = new JTextField("untitled", 30);
        name.add(nameLabel);
        name.add(projectName);
        jp.add(name);
        JPanel path = new JPanel();                           //file selecting field
        path.setLayout(new BoxLayout(path, BoxLayout.X_AXIS));
        JTextField selectedPath = new JTextField("C:\\untitled", 55); //selected path
        JButton browse = new JButton("Browse...");       //browsing button
        path.add(selectedPath);
        path.add(browse);
        jp.add(path);                                         //adding path selector
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton create = new JButton("Create");
        JButton cancel = new JButton("Cancel");
        buttons.add(create);
        buttons.add(cancel);
        jp.add(buttons);
        dialog.add(jp);
        //-------------LISTNER INITIALISATION----------------------
        browse.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jFileChooser.setCurrentDirectory(new File("C:\\"));
            int result = jFileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                fileDir = jFileChooser.getSelectedFile();
                selectedPath.setText(fileDir.getAbsolutePath());
                projectName.setText(fileDir.getName());
            }
        });
        cancel.addActionListener(e -> {
            dialog.dispose();
        });
        projectName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!projectName.getText().isEmpty()) {
                    String text = selectedPath.getText();
                    selectedPath.setText(text.substring(0, text.lastIndexOf("\\") + 1) + projectName.getText());
                }
            }
        });
        create.addActionListener(e -> {
            dialog.dispose();
            create(projectName.getText(), selectedPath.getText());
        });
        jp.setBackground(Colors.SECONDARY_BACKGROUND_COLOR);
        jp.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        name.setBackground(Colors.SECONDARY_BACKGROUND_COLOR);
        name.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        path.setBackground(Colors.SECONDARY_BACKGROUND_COLOR);
        path.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        buttons.setBackground(Colors.SECONDARY_BACKGROUND_COLOR);
        buttons.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        nameLabel.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        projectName.setBackground(Colors.SECONDARY_BACKGROUND_COLOR);
        projectName.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        selectedPath.setBackground(Colors.SECONDARY_BACKGROUND_COLOR);
        selectedPath.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        create.setBackground(Colors.SECONDARY_BACKGROUND_COLOR);
        create.setForeground(Colors.MAIN_FOREGROUND_COLOR);
        cancel.setBackground(Colors.SECONDARY_BACKGROUND_COLOR);
        cancel.setForeground(Colors.MAIN_FOREGROUND_COLOR);

        //selectedPath.set
        dialog.pack();
        dialog.setVisible(true);
    }

    private static void create(String name, String path) {
        File dir = new File(path);
        //dir.delete();
        dir.mkdir();
        new File(path + "\\src").mkdir();
        new File(path + "\\out").mkdir();
        File main = new File(path + "\\src\\main.asm");
        try {
            main.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //-------------------MAIN ASM INPUT
        File settings = new File(path + "settings.xml");
        try {
            settings.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    "<project name=\"" + name + "\">\n" +
                    "    <directory main=\"" + path + "\">\n" +
                    "        <src path=\"\\src\"/>\n" +
                    "        <out path=\"\\out\"/>\n" +
                    "        <import path=\"\\src\\import\"/>\n" +
                    "    </directory>\n" +
                    "    <files>\n" +
                    "        <main path=\"\\main.asm\"/>\n" +
                    "    </files>\n" +
                    "    <settings>\n" +
                    "        <compiler path=\"$DEFAULT_COMPILER$\"/>\n" +
                    "    </settings>\n" +
                    "</project>"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        //EditorPane.closeALL();
        EditorPane.open(new File(path + "\\src\\main.asm"));
    }
}
