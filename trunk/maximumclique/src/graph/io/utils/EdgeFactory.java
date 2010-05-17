package graph.io.utils;

import graph.util.Edge;

import org.apache.commons.collections15.Factory;

public class EdgeFactory implements Factory<Edge> {
	private int i;

	public EdgeFactory() {
		i = 1;
	}

	@Override
	public Edge create() {
		// System.out.println("Create Edge i=" + i);
		return new Edge(i++);
	}

}
