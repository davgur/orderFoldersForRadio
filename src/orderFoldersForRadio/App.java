/**
 * 
 */
package orderFoldersForRadio;

import java.io.Console;

/**
 * @author david
 *
 */
public class App {

	public static String DIR_SOURCE = "/home/david/Downloads/radio/";
	public static String DIR = "/home/david/Downloads/result/";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DIR_SOURCE = waitForEnter("Wright source dir and Press ENTER", DIR_SOURCE);
		DIR = waitForEnter("Wright result dir and Press ENTER", DIR);
		RemoveFiles.clear();
		MoveFiles.move();

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
