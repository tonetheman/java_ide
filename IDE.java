
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class IDE {

	JFrame mf = null;
	DefaultListModel projectFiles = new DefaultListModel();
	JList projectList = null;

	public void loadProjectFile(File f) {
		try {
			projectFiles.clear();
			BufferedReader inf = new BufferedReader(new FileReader(f));
			while(true) {
				String line = inf.readLine();
				if (line==null) break;
				projectFiles.addElement(line);
			}
			inf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String loadFile(File f) {
		return null;
	}

	public void gui() {
		mf = new JFrame("ide");
		mf.setSize(500,500);
		mf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		JEditorPane editor = new JEditorPane();
		editor.setEditable(true);
		editor.setSize(400,300);
		JScrollPane scroller = new JScrollPane(editor);
		scroller.setMinimumSize(new Dimension(200,200));
		mf.getContentPane().setLayout(new BorderLayout());
		mf.getContentPane().add(scroller, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		mf.getContentPane().add(bottom, BorderLayout.SOUTH);
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		JButton open = new JButton("open");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int ret = fc.showOpenDialog(mf);
				if (ret==JFileChooser.APPROVE_OPTION) {
					File inf = fc.getSelectedFile();
					loadProjectFile(inf);
				}
			}
		});
		top.add(open);
		JButton save = new JButton("save");
		top.add(save);
		JButton compile = new JButton("compile");
		top.add(compile);
		mf.getContentPane().add(top, BorderLayout.NORTH);
		JPanel left = new JPanel();
		projectList = new JList(projectFiles);
		projectList.setSelectionMode(
			ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		left.add(projectList);
		mf.getContentPane().add(left,BorderLayout.WEST);
		mf.setVisible(true);
	}

	public void run() {
		gui();
	}

	public static void main(String[] args) {
		IDE mainline = new IDE();
		mainline.run();
	}

}
