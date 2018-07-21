package orderFoldersForRadio;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class RemoveFiles {

	public static void clear() {
		clearRecursive(new File(App.DIR_SOURCE));
	}

	private static boolean clearRecursive(File dir) {
		if (!dir.exists() || !dir.isDirectory()) {
			return false;
		}
		List<File> files = Arrays.asList(dir.listFiles());
		files.stream().filter(f -> filesToClear(f)).forEach(f -> f.delete());
		files.stream().filter(f -> f.isDirectory()).forEach(f -> clearRecursive(f));
		return true;
	}

	private static boolean filesToClear(File file) {
		if (!file.isFile()) {
			return false;
		}
		String extension = getFileExtension(file);
		return !extension.equals("mp3");

	}

	private static String getFileExtension(File file) {
		String name = file.getName();
		try {
			return name.substring(name.lastIndexOf(".") + 1).toLowerCase();
		} catch (Exception e) {
			return "";
		}
	}
}
