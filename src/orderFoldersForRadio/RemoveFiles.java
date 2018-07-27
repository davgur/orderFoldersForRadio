package orderFoldersForRadio;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveFiles {

	private List<File> firstLevel;

	public RemoveFiles(String path) {
		setFirstLevel(Arrays.asList(new File(path).listFiles()));
	}

	public void clear() {
		this.firstLevel.forEach(f -> clearRecursive(f));
	}

	private boolean clearRecursive(File dir) {
		if (!dir.exists() || !dir.isDirectory()) {
			return false;
		}
		List<File> files = Arrays.asList(dir.listFiles());
		files.stream().filter(f -> filesToClear(f)).forEach(f -> f.delete());
		files.stream().filter(f -> f.isDirectory()).forEach(f -> clearRecursive(f));
		return true;
	}

	private boolean filesToClear(File file) {
		if (!file.isFile()) {
			return false;
		}
		String extension = getFileExtension(file);
		return !(extension.equals("mp3") || extension.equals("wav"));

	}

	private String getFileExtension(File file) {
		String name = file.getName();
		try {
			return name.substring(name.lastIndexOf(".") + 1).toLowerCase();
		} catch (Exception e) {
			return "";
		}
	}

	public List<File> getFirstLevel() {
		return this.firstLevel;
	}

	public void setFirstLevel(List<File> firstLevel) {
		this.firstLevel = firstLevel.stream().filter(f -> f.isDirectory()).collect(Collectors.toList());
	}
}
