
import java.io.*;
import com.sun.tools.javac.Main;
import java.util.*;

class StringArray {
	ArrayList al = null;
	private StringArray() {
		al = new ArrayList();
	}
	public StringArray end() { return this; }
	public StringArray add(String a) {
		al.add(a);
		return this;
	}
	public static StringArray begin() {
		return new StringArray();
	}
}

public class TestCompile {
	public static void main(String[] args) {

		StringArray sa = StringArray.begin()
			.add("-verbose")
			.add("Junk.java")
			.end();
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		String[] ca = new String[2];
		ca[0] = "-verbose";
		ca[1] = "Junk.java";
		com.sun.tools.javac.Main.compile(ca, pw);
		System.out.println(sw);	
	}
}
