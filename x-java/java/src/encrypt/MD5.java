package encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length < 1) {
				System.out.println("Usage: MD5 filePath");
				return;
			}

			String input = args[0];

			// Text mode
			File file = new File(input);
			if (!file.exists() || file.isDirectory()) {
				System.out.println("[INFO] Text mode");
				System.out.println(MD5Text(input));
				return;
			}

			System.out.println("[INFO] File mode");
			System.out.println(MD5File(file));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static private String MD5Text(String inputString) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(inputString.getBytes("UTF-8"));
			// md5.update(inputString.getBytes("ms932"));
			byte byteData[] = md5.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();
		} catch (Exception ex) {
			return "";
		}
	}

	private static String MD5File(File file) throws NoSuchAlgorithmException,
			IOException {
		InputStream in = new FileInputStream(file);
		MessageDigest digest = MessageDigest.getInstance("MD5");
		try {
			byte[] buff = new byte[4096];
			int len = 0;
			while ((len = in.read(buff, 0, buff.length)) >= 0) {
				digest.update(buff, 0, len);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		byte[] hash = digest.digest();
		// NG: return new String(hash);
		// Change to Hex
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hash.length; i++) {
			int b = (0xFF & hash[i]);
			if (b < 16)
				sb.append("0");
			sb.append(Integer.toHexString(b));
		}
		return sb.toString();
	}

}
