import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.Scanner;

/**
 * Read Me : reference all project where target class may in. Get Remember to
 * configure a bigger the console buffer size!
 *
 * @author HouTokki
 *
 */
public class GetProjectPath {
	static int c = 0;
	enum MODE {TREE, LIST} // TREE:展開された階層Copy  -VS-  LIST：修飾名Copy
	static String TREE_MODE_FLG = " - ";
	public static void main(String[] args) throws FileNotFoundException {
		// String path = "mybeans.YenToDoller";
		// Step:
		// 1.修飾名Copy
		// 2.paste it to "D:/allclass.txt"
		// 3.run this program
		String textFromFile = getTextFromFile("D:/tmp/tree.txt");
		String[] paths = textFromFile.split("\n");
		MODE mode;
		if (paths.length > 0 && textFromFile.contains(TREE_MODE_FLG)) {
			mode = MODE.TREE;
		}else {
			mode = MODE.LIST;
		}

		int count=0;
		for (String path : paths) {
			count++;
			path = path.replaceAll("\t", "");

			// Remove first method in tree mode
			if (mode==MODE.TREE) {
				if (path.split(TREE_MODE_FLG).length > 1) {
					path = path.split(TREE_MODE_FLG)[1];
				} else {
					System.out.println(" ●Passed! " + count + " - " + path);
					continue;
				}
			}

			// System.out.println(++c);
			if (path.indexOf(".new ") > 0){
				// [0] a.b.Class.method.new Class --> a.b.Class.method
				path = path.substring(0, path.indexOf(".new "));
				// [1] a.b.Class.Method --> a.b.Class
				path = path.substring(0, path.lastIndexOf('.'));
			}
			// [2] a.b.Class (2 個の一致) --> a.b.Class
			if (path.indexOf(" (") > 0) {
				path = path.substring(0, path.indexOf(" ("));
			}
			if (path.indexOf('(') > 0) {
				// [3] a.b.Class.Method(..) --> a.b.Class.Method
				path = path.substring(0, path.indexOf('('));
				// [4] a.b.Class.Method --> a.b.Class
				path = path.substring(0, path.lastIndexOf('.'));
			}
			if (path.endsWith(" ")) {
				// [5] "a.b.Class.treeAndroid " --> "a.b.Class"
				path = path.substring(0, path.lastIndexOf('.'));
			}

			try {
				String location = getFileLocationByClassName(path);
				System.out.println(guessPrj(location) + " " + count + " - " + path + " - "
						+ location);
			} catch (ClassNotFoundException e) {
				System.out.println("★" + guessPrj(path) + " " + count + " - " + path + " - Not Found " + e.getMessage());
				e.printStackTrace();
			} catch (RuntimeException e) {
				System.out.println("★" + guessPrj(path) + " " + count + " - " + path + " - Not Found " + e.getMessage());
				e.printStackTrace();
			}
		}

	}
	private static String guessPrj(String path) {
		return path.replaceAll("/target.*", "").replaceAll(".*/", "");
	}

	/**
	 * Get class file location
	 *
	 * @param path
	 *            : example: com.test.Class1
	 * @return : example: /D:/workspace/project/target/com/test/Class1.class
	 * @throws ClassNotFoundException
	 */
	private static String getFileLocationByClassName(String path)
			throws ClassNotFoundException,RuntimeException {
		if (path == null || path.length() == 0)
			return "";
		// false:停止类初始化
		// 理由：静态初始化代码可以抛出异常，异常被包装到java.lang.ExceptionInInitializerError的实例中，但无法被程序捕获到。
		// 异常抛出后, 这个类将不可用. 并导致整个程序执行结束。
		Class<?> forName = Class.forName(path, false , ClassLoader.getSystemClassLoader());
		URL resource = forName.getResource(forName.getSimpleName() + ".class");

		if (!"file".equalsIgnoreCase(resource.getProtocol()))
			//throw new IllegalStateException("Class is not stored in a file.");
			return "";

		return resource.getPath();
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
