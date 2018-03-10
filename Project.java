import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

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
        JButton cancel = new JButton("Cansel");
        buttons.add(create);
        buttons.add(cancel);
        jp.add(buttons);
        dialog.add(jp);
        //-------------LISTNER INITIALISATION----------------------
        browse.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = jFileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                fileDir = jFileChooser.getSelectedFile();
                selectedPath.setText(fileDir.getAbsolutePath());
                projectName.setText(fileDir.getName());
            }
        });
        cancel.addActionListener(e -> {
            dialog.dispose();
            return;
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
                    selectedPath.setText(text.substring(0,text.lastIndexOf("\\")+1)+projectName.getText());
                }
            }
        });
        dialog.pack();
        dialog.setVisible(true);
    }
}
