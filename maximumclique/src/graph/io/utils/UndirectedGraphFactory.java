package graph.io.utils;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.util.Edge;
import graph.util.Node;

public class UndirectedGraphFactory implements Factory<UndirectedGraph<Node, Edge>> {

	@Override
	public UndirectedSparseGraph<Node, Edge> create() {
		return new UndirectedSparseGraph<Node, Edge>();
	}

}
