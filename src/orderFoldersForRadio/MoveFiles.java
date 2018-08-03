package orderFoldersForRadio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MoveFiles {

	private List<File> langs;
	private File resultDir;
	final private String NAME_SEPARATOR = "_";

	public MoveFiles(String sourcePath, String resultPath) throws IOException {
		setLangs(Arrays.asList(new File(sourcePath).listFiles()));
		setResultDir(resultPath);
	}

	public void move() {
		this.getLangs().forEach(f -> moveByLang(f));
	}

	private void moveByLang(File folder) {
		String target = targetFolderByLang(folder.getName());
		moveFilesByDirRecursive(folder, target, null);
	}

	private boolean isExistSameFileInDir(File file, String resultDir2) {
		File dir = new File(resultDir2);
		return Arrays.asList(dir.listFiles()).stream().anyMatch(f -> f.getUsableSpace() == file.getUsableSpace());
	}

	private boolean moveFilesByDirRecursive(File file, String targetPath, String prefix) {

		if (file.getUsableSpace() < 100) {
			return false;
		}

		if (file.isFile()) {
			return moveFile(file, targetPath, prefix);
		}

		String newPrefix = prefix == null ? ""
				: prefix.equals("") ? file.getName() : prefix + NAME_SEPARATOR + file.getName();
		Arrays.asList(file.listFiles()).stream().forEach(d -> moveFilesByDirRecursive(d, targetPath, newPrefix));
		return true;
	}

	private boolean moveFile(File file, String targetPath, String prefix) {
		if (notNeedToMove(file, targetPath)) {
			return true;
		}

		String fileName = prefix == null || prefix.equals("") ? file.getName()
				: prefix + NAME_SEPARATOR + file.getName();
		File newFile = new File(targetPath + "/" + fileName);
		if (newFile.exists()) {
			return false;
		}
		try {
			Files.copy(file.toPath(), newFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	private String targetFolderByLang(String lang) {
		File folder = new File(getResultDir() + "/" + lang);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return folder.getPath();
	}

	private boolean notNeedToMove(File file, String resultDir) {
		String extension = getFileExtension(file);
		if(!(extension.equals("mp3") && !extension.equals("wav"))) {
			return true;
		}
		
		/*if(isExistSameFileInDir(file, resultDir)) {
			return true;
		}*/
		return false;
		
	}

	private String getFileExtension(File file) {
		String name = file.getName();
		try {
			return name.substring(name.lastIndexOf(".") + 1).toLowerCase();
		} catch (Exception e) {
			return "";
		}
	}

	/*
	 * public File convertWmaToMp3(File file) throws IOException { if
	 * (!file.exists()) { return null; }
	 * 
	 * long fileSize = file.length(); int frameSize = 160; long numFrames = fileSize
	 * / frameSize; AudioFormat audioFormat = new
	 * AudioFormat(AudioFormat.Encoding.ULAW, 8000, 8, 1, frameSize, 50, true);
	 * AudioInputStream audioInputStream = new AudioInputStream(new
	 * FileInputStream(file), audioFormat, numFrames);
	 * 
	 * 
	 * ByteArrayInputStream bais = new
	 * ByteArrayInputStream(FileUtils.readFileToByteArray(file)); sourceAIS =
	 * AudioSystem.getAudioInputStream(bais); AudioFormat sourceFormat =
	 * sourceAIS.getFormat(); AudioFormat convertFormat = new
	 * AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sourceFormat.getSampleRate(),
	 * 16, sourceFormat.getChannels(), sourceFormat.getChannels()*2,
	 * sourceFormat.getSampleRate(), false);
	 * 
	 * 
	 * AudioSystem.write(audioInputStream, Type., new File("C:\\file.wav"));
	 * 
	 * return file; }
	 */

	/**
	 * getters and setters
	 */

	public List<File> getLangs() {
		return this.langs;
	}

	public void setLangs(List<File> langs) {
		this.langs = langs.stream().filter(f -> f.isDirectory()).collect(Collectors.toList());
	}

	public File getResultDir() {
		return this.resultDir;
	}

	public void setResultDir(String pathname) throws IOException {
		this.resultDir = new File(pathname);
		this.resultDir.mkdirs();
	}

}
