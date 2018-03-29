package project;

import main.EditorFrame;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Project {

	public static void createNew() {
		final boolean[] isDirChosen = {false};
		JFrame dialog = new JFrame("Create new");
		JPanel main = new JPanel();
		JPanel filler = new JPanel();
		JTextField name = new JTextField(60);
		JTextField path = new JTextField(49);
		JButton cancel = new JButton("Cancel");
		JButton create = new JButton("Create");
		JButton browse = new JButton("Browse...");
		JFileChooser fileChooser = new JFileChooser();

		cancel.addActionListener(e -> dialog.dispose());
		browse.addActionListener(e -> {
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				path.setText(fileChooser.getSelectedFile().getAbsolutePath());
				isDirChosen[0] = true;
			}
		});
		name.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!isDirChosen[0]) {
					path.setText(path.getText().substring(0, path.getText().lastIndexOf("\\")) + "\\" + name.getText());
				}
			}
		});
		create.addActionListener(e -> {
			if ((!name.getText().isEmpty()) && (!path.getText().isEmpty())) {
				dialog.dispose();
				create(name.getText(), path.getText());
			}
		});
		name.setText("untitled");
		path.setText(FileSystemView.getFileSystemView().getRoots()[0].getPath()+ File.separator+"untitled");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		main.setLayout(new GridLayout(3, 1));
		filler.setLayout(new FlowLayout(FlowLayout.LEFT));
		filler.add(new JLabel("Project name: "));
		filler.add(name);
		main.add(filler);
		filler = new JPanel();
		filler.add(new JLabel("Project path:  "));
		filler.add(path);
		filler.add(browse);
		main.add(filler);
		filler = new JPanel();
		filler.setLayout(new FlowLayout(FlowLayout.RIGHT));
		filler.add(create);
		filler.add(cancel);
		main.add(filler);
		dialog.add(main);
		dialog.setSize(dialog.getPreferredSize().width + 17, dialog.getPreferredSize().height + 28);
		dialog.setResizable(false);
		dialog.setVisible(true);
	}

	private static void create(String name, String path) {
		System.out.println("test " + name + " " + path);
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		new File(path +File.separator+ "src").mkdir();
		new File(path +File.separator+ "src"+File.separator+"main.asm").mkdir();
		new File(path +File.separator+ "out").mkdir();
		File project = new File(path +File.separator+ "project.prj");
		try {
			project.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(path +File.separator+ "project.prj");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
				"<project>\n" +
				"    <name>" + name + "</name>\n" +
				"    <work_dir>" + path + "</work_dir>\n" +
				"    <src>\n" +
				"        <dir>$WORK_DIR$"+File.separator+"src</dir>\n" +
				"    </src>\n" +
				"    <out>\n" +
				"        <dir>$WORK_DIR$"+File.separator+"out</dir>\n" +
				"    </out>\n" +
				"</project>");
		EditorFrame.getEditor().open(new File(path +File.separator+ "src"+File.separator+"main.asm"));
	}

	public static void openProject() {
		for (File f:FileSystemView.getFileSystemView().getRoots()) {
			System.out.println(f.toString());
		}
		JFileChooser dialog = new JFileChooser(FileSystemView.getFileSystemView().getRoots()[0]);
		dialog.setDialogTitle("Open project");
		dialog.setMultiSelectionEnabled(false);
		dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		dialog.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if(f.isDirectory())return true;
				if(f.getName().toLowerCase().endsWith(".prj")){return true;}
				return false;
			}

			@Override
			public String getDescription() {
				return "Project ASM project file *.prj";
			}
		});
		if(dialog.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			File project = dialog.getSelectedFile();
			File dir = dialog.getCurrentDirectory();
		}
	}
}
