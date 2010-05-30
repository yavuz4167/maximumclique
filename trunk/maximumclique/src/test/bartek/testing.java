package test.bartek;

import graph.gui.GraphWindow;
import graph.gui.RadomTestsWindow;

public class testing {

	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		if (args.length == 0)
			new GraphWindow();
		else if (args[0].equalsIgnoreCase("randomtest"))
			new RadomTestsWindow();
	}
}
