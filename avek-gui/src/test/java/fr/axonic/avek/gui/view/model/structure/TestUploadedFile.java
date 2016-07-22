package fr.axonic.avek.gui.view.model.structure;

import com.sun.javafx.application.PlatformImpl;
import fr.axonic.avek.gui.model.structure.UploadedFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nathaël N on 20/07/16.
 */
public class TestUploadedFile {
	@Before
	public void before() throws IOException {
		PlatformImpl.startup(() ->{});

		File f = new File("./temp/test.txt"); // 31 bytes
		f.createNewFile();

		PrintWriter writer = new PrintWriter(f, "UTF-8");
		writer.println("The first line");
		writer.println("The second line");
		writer.close();

		f = new File("./temp/toto/titi.txt"); // 25 bytes
		f.getParentFile().mkdirs();
		f.createNewFile();

		writer = new PrintWriter(f, "UTF-8");
		writer.println("TATA TATa TAta Tata tata");
		writer.close();

		f = new File("./temp/toto/tonton.txt"); // 49 bytes
		f.createNewFile();

		writer = new PrintWriter(f, "UTF-8");
		writer.println("tonton Tonton TOnton TONton");
		writer.println("TONTon TONTOn TONTON");
		writer.close();
	}

	@Test
	public void testUploadedFile() throws IOException {
		UploadedFile uf = new UploadedFile(new File("./temp/test.txt"));

		assertEquals(new File("./temp/test.txt"), uf.getOriginal());
		assertEquals(31, uf.getSize());

		if(!waitingForUpload(uf)) {
			uf.removeListener();
			assertTrue("Waiting for more than 30s", false);
			return;
		}

		uf.removeListener();

		List<File> fileList = new ArrayList<>();
		Collections.addAll(fileList, UploadedFile.uploadedFolder.listFiles());

		assertEquals(1, fileList.size());
		File f = fileList.get(0);
		assertEquals("test.txt", f.getName());
		assertEquals(31, f.length());
		assertTrue(f.isFile());
	}

	@Test
	public void testUploadedFolder() throws IOException {
		UploadedFile uf = new UploadedFile(new File("./temp/toto/"));

		assertEquals(new File("./temp/toto"), uf.getOriginal());
		assertEquals(25 + 49, uf.getSize());


		if(!waitingForUpload(uf)) {
			uf.removeListener();
			assertTrue("Waiting for more than 30s", false);
			return;
		}

		uf.removeListener();

		List<File> fileList = new ArrayList<>();
		Collections.addAll(fileList, UploadedFile.uploadedFolder.listFiles());

		assertEquals(1, fileList.size());
		File f = fileList.get(0);
		assertEquals("toto", f.getName());
		assertTrue(f.isDirectory());

		assertEquals(2, f.listFiles().length);
		File titi = f.listFiles()[1];
		File tonton = f.listFiles()[0];
		assertEquals("titi.txt", titi.getName());
		assertEquals("tonton.txt", tonton.getName());
		assertTrue(titi.isFile());
		assertTrue(tonton.isFile());

		try {
			List<String> l = new ArrayList<>();
			l.add("TATA TATa TAta Tata tata");
			assertEquals(l, Files.readAllLines(titi.toPath()));

			l.clear();
			l.add("tonton Tonton TOnton TONton");
			l.add("TONTon TONTOn TONTON");
			assertEquals(l, Files.readAllLines(tonton.toPath()));

		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
	}

	@Test
	public void testAlreadyUploaded() throws FileNotFoundException {
		UploadedFile uf1 = new UploadedFile(new File("./temp/test.txt"));
		UploadedFile uf2 = new UploadedFile(new File("./temp/test.txt"));
		try {
			if(!waitingForUpload(uf1)) {
				uf1.removeListener();
				assertTrue("Waiting for more than 30s", false);
				return;
			}
		} catch (FileAlreadyExistsException e) {
			e.printStackTrace();
		}

		uf1.removeListener();

		try {
			if(!waitingForUpload(uf2)) {
				uf2.removeListener();
				assertTrue("Waiting for more than 30s", false);
				return;
			}
			assertTrue("Not thrown: FileAlreadyExistsException", false);
		} catch (FileAlreadyExistsException e) {
			assertTrue(true);
		}

		uf2.removeListener();

		List<File> fileList = new ArrayList<>();
		Collections.addAll(fileList, UploadedFile.uploadedFolder.listFiles());

		assertEquals(1, fileList.size());
		File f = fileList.get(0);
		assertEquals("test.txt", f.getName());
		assertEquals(31, f.length());
		assertTrue(f.isFile());
	}

	private volatile boolean pass;
	private boolean waitingForUpload(UploadedFile uf1) throws FileAlreadyExistsException, FileNotFoundException {
		pass = false;

		uf1.setUpdateListener(() -> {
			if(uf1.getPct() == 1)
				pass = true;
		});
		uf1.doUpload();

		long timeStamp = Calendar.getInstance().getTimeInMillis()+30_000;
		while(!pass) {
			if(timeStamp < Calendar.getInstance().getTimeInMillis())
				return false;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}


	@After public void after() {
		try {
			File f = new File("./temp/test.txt");
			delete(f);
			f = new File("./temp/toto");
			delete(f);
			for(File ff : UploadedFile.uploadedFolder.listFiles())
				delete(ff);
		} catch (FileNotFoundException ignored) {}
	}

	private static void delete(File f) throws FileNotFoundException {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				delete(c);
		}
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}
}
