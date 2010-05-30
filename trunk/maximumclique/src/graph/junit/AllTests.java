package graph.junit;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for graph.junit");
		//$JUnit-BEGIN$
		suite.addTestSuite(MaximumCliqueTest.class);
		//$JUnit-END$
		return suite;
	}

}
