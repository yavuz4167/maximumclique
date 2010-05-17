package graph.algorithm;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.graph.Graph;

/**
 * 
 * @author bbarczynski
 * 
 * @param <V>
 * @param <E>
 */
public class MaximumClique<V, E> {
	private Graph<V, E> graph;
	private Map<Integer, V> nodeMap;

	private List<Collection<V>> maximalCliques;

	public MaximumClique(Graph<V, E> graph) {
		this.graph = graph;
		maximalCliques = new LinkedList<Collection<V>>();
		// index nodes for fast searching
		Collection<V> vertices = graph.getVertices();
		nodeMap = new HashMap<Integer, V>(graph.getVertexCount());
		Integer i = 1;
		for (V v : vertices) {
			nodeMap.put(i++, v);
		}

	}

	public List<Collection<V>> getCliques() {
		TIAS(new HashSet<V>(), 1);
		sortMaximalCliques();
		printAllMaximalCliques();
		removeNoMaximumCliques();
		return maximalCliques;
	}

	private void TIAS(Collection<V> maximalClique, Integer nodeIndex) {

		if (nodeIndex > graph.getVertexCount()) {
			System.out.println("!!! Znaleziono masymalna kilke : " + maximalClique.toString());
			// TODO [bbarczynski] sprawdzenie czy dodajemy druga taka sama klike
			// maksylana
			maximalCliques.add(maximalClique);
			return;
		}
		V node = nodeMap.get(nodeIndex);
		System.out.println(getPrefix(node) + "maximalClique=" + maximalClique.toString());
		Collection<V> neighbors = graph.getNeighbors(node);
		if (neighbors.containsAll(maximalClique)) {
			System.out.println(getPrefix(node) + "maximalClique zawiera sie w neighbors");
			maximalClique.add(node);
			TIAS(maximalClique, nodeIndex + 1);
		} else {
			System.out.println(getPrefix(node) + "maximalClique - brak zawierania");
			TIAS(maximalClique, nodeIndex + 1);
			// TODO [bbarczynski] if M is the lexicographically smallest maximal
			// clique of {u1,...,uj−1} that contains M'
			System.out.println(getPrefix(node) + "maximalClique=" + maximalClique);
			System.out.println(getPrefix(node) + "neighbors=" + neighbors);
			HashSet<V> intersection = new HashSet<V>(neighbors);
			intersection.retainAll(maximalClique);
			System.out.println(getPrefix(node) + "intersection=" + intersection);

			Collection<V> lexiClique = getLexicographicallySmallestMaximalClique(new HashSet<V>(intersection),
					nodeIndex - 1);
			System.out.println(getPrefix(node) + "lexiClique=" + lexiClique);
			if (maximalClique.equals(lexiClique)) {
				System.out.println(getPrefix(node) + "lexiClique jest rowne maximalClique ");
				intersection.add(node);
				TIAS(intersection, nodeIndex + 1);
			} else {
				System.out.println(getPrefix(node) + "lexiClique jest rozne od maximalClique ");
			}

		}

	}

	/**
	 * Metoda zwraca najmniejszą leksygograficznie (czyli sortowanie rosnace
	 * wierzcholkow) klike, ktora zawiera klike wejsciowa. Metoda probuje
	 * dodawac do clique kolejne wiercholki, spr czy clique ciagle bedzie klika
	 * 
	 * @param clique
	 *            klika poczatkowa
	 * @param maxIndex
	 *            ostatni wierzcholek, ktory mozna dolaczyc
	 * @return
	 */
	private Collection<V> getLexicographicallySmallestMaximalClique(Collection<V> clique, Integer maxIndex) {

		for (int i = 1; i <= maxIndex; ++i) {
			V node = nodeMap.get(i);
			Collection<V> neighbors = graph.getNeighbors(node);
			// mozna dolaczyc nowy wierzcholek gdy sasiedzi Vi zawieraja
			// wszystkie wierzcholki z kliki. Czyli po dolaczenieu Vi ciagle
			// bedzie klika
			if (neighbors.containsAll(clique))
				clique.add(node);// powiekszamy klike
		}
		return clique;
	}

	private String getPrefix(V node) {
		return node.toString() + ": ";
	}

	private void printAllMaximalCliques() {
		System.out.println("Kliki maksymalne:");
		for (Collection<V> element : maximalCliques) {
			System.out.println(element.toString());
		}
	}

	private void sortMaximalCliques() {
		Collections.sort(maximalCliques, new Comparator<Collection<V>>() {

			@Override
			public int compare(Collection<V> o1, Collection<V> o2) {
				return o2.size() - o1.size();
			}
		});
	}

	private void removeNoMaximumCliques() {
		int firstSize = maximalCliques.get(0).size();
		for (int i = maximalCliques.size() - 1; i >= 0; --i) {
			if (maximalCliques.get(i).size() < firstSize)
				maximalCliques.remove(i);
		}
	}
}
