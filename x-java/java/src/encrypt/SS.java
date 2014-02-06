package encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;

/**
 * Secure Sender <br>
 */
public class SS {
	final static String USAGE = "SS -e/d password input output";

	final private static int OPTION = 0;
	final private static int PASSWORD = 1;
	final private static int INPUT = 2;
	final private static int OUTPUT = 3;

	final private static int BUFFER_SIZE = 1024;

	enum Option {
		e("encript to base64") {
			@Override
			public void execute(String password, File input, File output)
					throws IOException, GeneralSecurityException {
				File tmp = File.createTempFile("tmp", ".tmp");
				encrypt(input, tmp, password);
				encodeBase64(tmp, output);
				tmp.delete();
			}
		},
		d("decript from base64") {
			@Override
			public void execute(String password, File input, File output)
					throws IOException, GeneralSecurityException {
				File tmp = File.createTempFile("tmp", ".tmp");
				decodeBase64(input, tmp);
				decrypt(tmp, output, password);
				tmp.delete();
			}
		},
		ec("encript only") {
			@Override
			public void execute(String password, File input, File output)
					throws IOException, GeneralSecurityException {
				encrypt(input, output, password);
			}
		},
		dc("decript only") {
			@Override
			public void execute(String password, File input, File output)
					throws IOException, GeneralSecurityException {
				decrypt(input, output, password);
			}
		},
		eb("encodeBase64") {
			@Override
			public void execute(String password, File input, File output)
					throws FileNotFoundException, IOException {
				encodeBase64(input, output);
			}
		},
		db("decodeBase64") {
			@Override
			public void execute(String password, File input, File output)
					throws FileNotFoundException, IOException {
				decodeBase64(input, output);
			}
		};

		private String name;

		Option(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public abstract void execute(String password, File input, File output)
				throws Exception;

		public static boolean isMember(String aName) {
			for (Option option : Option.values())
				if (option.name().equals(aName))
					return true;
			return false;
		}
	};

	public static void main(String[] args) {

		if (checkParam(args) == false) {
			System.out.println(USAGE);
			return;
		}

		try {
			new File(args[OUTPUT]).delete();
			Option.valueOf(args[OPTION]).execute(args[PASSWORD],
					new File(args[INPUT]), new File(args[OUTPUT]));
		} catch (Exception e) {
			System.out.println(Option.valueOf(args[OPTION]).getName()
					+ " failed!" + e.getMessage());
		}

		System.out.println("SS complete.");

	}

	private static boolean checkParam(String[] args) {
		if (args.length >= 4 && new File(args[INPUT]).exists()) {
			args[OPTION] = args[OPTION].replace("-", "");
			return Option.isMember(args[OPTION]);
		}
		return false;
	}

	private static void encrypt(File input, File output, String password)
			throws IOException, GeneralSecurityException {
		cipher(input, output, password, Cipher.ENCRYPT_MODE);

	}

	private static void decrypt(File input, File output, String password)
			throws IOException, GeneralSecurityException {
		cipher(input, output, password, Cipher.DECRYPT_MODE);
	}

	/* The default behavior of the Base64OutputStream is to ENCODE */
	private static void encodeBase64(File input, File output)
			throws FileNotFoundException, IOException {
		// true: encode, 76 characters/line, LF lineEnding/lineSeparator(Default is CR+LF - \r\n)
		writeStreamFile(new FileInputStream(input), new Base64OutputStream(
				new FileOutputStream(output), true , 76, "\n".getBytes()));
	}

	/* The default behavior of the Base64InputStream is to DECODE */
	private static void decodeBase64(File input, File output)
			throws FileNotFoundException, IOException {
		writeStreamFile(new Base64InputStream(new FileInputStream(input)),
				new FileOutputStream(output));
	}

	private static void writeStreamFile(InputStream in, OutputStream out)
			throws IOException {
		int count = 0;
		byte[] buffer = new byte[BUFFER_SIZE];
		while ((count = in.read(buffer)) >= 0) {
			out.write(buffer, 0, count);
		}
		in.close();
		out.close();
	}

	private static void cipher(File input, File output, String password,
			int ENCRYPT_MODE) throws IOException, GeneralSecurityException {

		SecretKeySpec sksSpec = new SecretKeySpec(password.getBytes(),
				"Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(ENCRYPT_MODE, sksSpec);

		InputStream in = new FileInputStream(input);
		OutputStream out = new FileOutputStream(output);
		out = new CipherOutputStream(out, cipher);
		int count = 0;
		byte[] buffer = new byte[BUFFER_SIZE];
		while ((count = in.read(buffer)) >= 0) {
			out.write(buffer, 0, count);
		}
		in.close();
		out.close();

	}

}
