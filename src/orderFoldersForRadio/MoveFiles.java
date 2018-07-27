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

	public MoveFiles(String sourcePath, String resultPath) throws IOException {
		setLangs(Arrays.asList(new File(sourcePath).listFiles()));
		setResultDir(resultPath);
	}

	public void move() {
		this.getLangs().stream().forEach(f -> buildLangFolder(f));
	}

	private boolean buildLangFolder(File file) {
		Arrays.asList(file.listFiles()).stream().filter(f -> f.isDirectory())
				.forEach(d -> saveToResult(getResultDir() + "/" + file.getName() + "/" + d.getName(),
						extractFilesByDirRecursive(d)));
		return true;
	}

	private void saveToResult(String newPath, List<File> files) {
		File folder = new File(newPath);
		folder.mkdirs();
		files.forEach(f -> moveFile(folder, f));
	}

	private void moveFile(File target, File file) {
		File newFile = new File(target.getPath() + "/" + file.getName());
		try {
			Files.move(file.toPath(), newFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<File> extractFilesByDirRecursive(File dir) {
		List<File> allFiles = Arrays.asList(dir.listFiles());
		List<File> files = allFiles.stream().filter(File::isFile).map(f -> renameWithFolderPrefix(f, dir))
				.collect(Collectors.toList());
		allFiles.stream().filter(File::isDirectory).forEach(d -> files.addAll(extractFilesByDirRecursive(d)));
		return files;
	}

	private File renameWithFolderPrefix(File file, File parent) {
		file.renameTo(new File(parent.getPath() + "/" + parent.getName() + "_" + file.getName()));
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;

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
