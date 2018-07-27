/**
 * 
 */
package orderFoldersForRadio;

import java.io.Console;
import java.io.IOException;

/**
 * @author david
 *
 */
public class App {

	private static String DIR_SOURCE = "/home/david/Downloads/radio/";
	private static String DIR = "/home/david/Downloads/result";

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		DIR_SOURCE = waitForEnter("Wright source dir and Press ENTER", DIR_SOURCE);
		DIR = waitForEnter("Wright result dir and Press ENTER", DIR);
		new RemoveFiles(DIR_SOURCE).clear();
		new MoveFiles(DIR_SOURCE, DIR).move();

	}

	private static String waitForEnter(String message, String defPath) {
		Console c = System.console();

		if (c == null) {
			return defPath;
		}

		c.format(message);
		return c.readLine();

	}
}
