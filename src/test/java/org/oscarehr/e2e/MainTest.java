package org.oscarehr.e2e;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class MainTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test(expected=UnsupportedOperationException.class)
	public void mainInstantiationTest() {
		new Main();
	}

	@Test
	public void mainTest() {
		PrintStream stdOut = System.out;
		System.setOut(new PrintStream(new NullOutputStream()));
		Main.main(null);
		System.setOut(stdOut);
	}

	// Creates an OutputStream that does nothing
	public class NullOutputStream extends OutputStream {
		@Override
		public void write(int b) throws IOException {
		}
	}
}
