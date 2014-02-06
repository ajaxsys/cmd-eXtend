package groovy;

import groovy.ui.Console;

public class GroovyConsole {
	public static void main(String[] args) {
		new Console(GroovyConsole.class.getClassLoader()).run();
	}
}