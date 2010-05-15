package graph.io.utils;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.util.Edge;
import graph.util.Node;

// TODO [bbarczynski] 

public class UndirectedGraphFactory implements Factory<UndirectedSparseGraph<Node, Edge>> {

	@Override
	public UndirectedSparseGraph<Node, Edge> create() {
		return new UndirectedSparseGraph<Node, Edge>();
	}

}
