import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Scanner;

enum Spy {
	BLACK, WHITE
}

@SuppressWarnings( { "nls" })
public class CheckClassFieldModifier {
	public final String test1 = "";
	final String test2 = "";
	public String test3 = "";
	Spy test4 = Spy.BLACK;

	// Synthetic Field in `switch case`
	@SuppressWarnings("incomplete-switch")
	public boolean test(Spy test) {
		boolean result = false;

		switch (test) {
		case BLACK:
			break;
		}
		return result;
	}

	public static void main(String[] args) {
		String textFromFile;
		try {
			textFromFile = getTextFromFile("D:/tmp/list2.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		String[] classPaths = textFromFile.split("\n");

		for (int i = 0; i < classPaths.length; i++) {
			System.out.print("●" + (i + 1));
			String clazz = classPaths[i];
			if (clazz.trim().length() == 0) {
				System.out.println("無効なInput");
				continue;
			}
			// 注意："final", "public"とはfinal public または、public finalの組み合わせ
			// checkContainsModifier(classPaths[i], "final", "public");
			checkContainsModifier(classPaths[i], "final");
		}
	}

	public static void checkContainsModifier(String... args) {
		try {
			Class<?> c = Class.forName(args[0], false, ClassLoader
					.getSystemClassLoader());
			int searchMods = 0x0;
			for (int i = 1; i < args.length; i++) {
				searchMods |= modifierFromString(args[i]);
			}

			Field[] flds = c.getDeclaredFields();
			System.out.println("●Check fields in Class '" + c.getName()
					+ "' containing modifiers:  "
					+ Modifier.toString(searchMods));

			boolean found = false;
			for (Field f : flds) {
				int foundMods = f.getModifiers();
				// Require all of the requested modifiers to be present
				if ((foundMods & searchMods) == searchMods) {
					System.out.println(f.getName() + " OK. "
							+ Modifier.toString(foundMods) + " field - type: "
							+ f.getType());
					found = true;
				} else {
					System.out.println(f.getName()
							+ " ★NG★. "
							+ Modifier.toString(foundMods)
							+ " field - type: "
							+ (f.isSynthetic() ? "Synthetic Field(switch enum)"
									: f.getType()));
				}
			}

			if (!found) {
				System.out.println("No matching fields%n");
			}

			// production code should handle this exception more gracefully
		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		}
	}

	private static int modifierFromString(String s) {
		int m = 0x0;
		if ("public".equals(s))
			m |= Modifier.PUBLIC;
		else if ("protected".equals(s))
			m |= Modifier.PROTECTED;
		else if ("private".equals(s))
			m |= Modifier.PRIVATE;
		else if ("static".equals(s))
			m |= Modifier.STATIC;
		else if ("final".equals(s))
			m |= Modifier.FINAL;
		else if ("transient".equals(s))
			m |= Modifier.TRANSIENT;
		else if ("volatile".equals(s))
			m |= Modifier.VOLATILE;
		return m;
	}

	private static String getTextFromFile(String path)
			throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader(path));
		StringBuilder stringBuilder = new StringBuilder();
		while (in.hasNextLine())
			stringBuilder.append(in.nextLine() + "\n");
		return stringBuilder.toString();
	}
}
