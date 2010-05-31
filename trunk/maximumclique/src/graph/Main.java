package graph;

import graph.gui.GraphWindow;
import graph.gui.RadomTestsWindow;

public class Main {
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
