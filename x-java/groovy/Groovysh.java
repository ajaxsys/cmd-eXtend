package groovy;

import groovy.lang.GroovyShell;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Groovysh {
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.out.println("Input a groovy file Name");
			return;
		}

		GroovyShell shell = new GroovyShell(Groovysh.class.getClassLoader());
		shell.run(new FileReader(new File(args[0])), args[0], tail(args));
	}

	private static String[] tail(String[] array) {
		return Arrays.copyOfRange(array, 1, array.length);
	}

}
