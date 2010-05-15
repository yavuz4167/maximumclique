package graph.algorithm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import edu.uci.ics.jung.graph.Graph;

public class MaximumClique<V, E> {
	private Graph<V, E> graph;
	private Map<Integer, V> nodeMap;

	public MaximumClique(Graph<V, E> graph) {
		this.graph = graph;
		Collection<V> vertices = graph.getVertices();
		// index nodes for fast searching
		nodeMap = new HashMap<Integer, V>(graph.getVertexCount());
		Integer i = 1;
		for (V v : vertices) {
			nodeMap.put(i++, v);
		}

	}

	public Collection<V> getClique() {
		TIAS(new HashSet<V>(), 1);
		return null;
	}

	private void TIAS(Collection<V> maximalClique, Integer nodeIndex) {

		if (nodeIndex > graph.getVertexCount()) {
			System.out.println("!!! Znaleziono masymalna kilke : " + maximalClique.toString());
			return;
		}
		V node = nodeMap.get(nodeIndex);
		System.out.println("# node=" + node.toString() + " maximalClique=" + maximalClique.toString());
		Collection<V> neighbors = graph.getNeighbors(node);
		if (neighbors.containsAll(maximalClique)) {
			System.out.println("maximalClique zawiera sie w neighbors");
			maximalClique.add(node);
			TIAS(maximalClique, nodeIndex + 1);
		} else {
			System.out.println("Brak zawierania");
			TIAS(maximalClique, nodeIndex + 1);
			// TODO [bbarczynski] if M is the lexicographically smallest maximal
			// clique of {u1,...,uj−1} that contains M'

		}

	}
}
