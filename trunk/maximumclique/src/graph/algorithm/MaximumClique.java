package graph.algorithm;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.uci.ics.jung.graph.Graph;

/**
 * Algorytm wyszukiwania najwiekszej kliki w grafie.
 * 
 * @author bbarczynski
 * 
 * @param <V>
 *            wiezchoÅki
 * @param <E>
 *            krawÄdzie
 */
public class MaximumClique<V, E> {
	/**
	 * Analizowany graf
	 */
	private Graph<V, E> graph;

	/**
	 * Lista wierzchołków. Jest niezbędna aby pobierać poszczególne wierzchołki
	 * po indexie. Graph#getVertices() zwraca kolekcję, po której można się
	 * jedynie iterować, a nie jest możliwe pobranie elementu z wybranego
	 * indexu.
	 */
	private ArrayList<V> nodes;

	/**
	 * Lista znalezionych maksymalnych (nie koniecznie najwiekszych) klik
	 */
	private List<Set<V>> maximalCliques;

	/**
	 * Czas trwania algorytmu. Sumowa są czynności przygotowawcze (wykonane w
	 * kostruktorze) oraz czas wykonania samego algorytmu
	 */
	private BigInteger algorithmDuration;

	/**
	 * Zmienna na przerwanie obliczeñ
	 */
	private static boolean isRunning = true;

	/**
	 * 
	 * @param graph
	 *            graf, w ktorym bedzie wyznaczana najwieksza klika
	 */
	public MaximumClique(Graph<V, E> graph) {
		BigInteger start = BigInteger.valueOf(System.nanoTime());
		algorithmDuration = BigInteger.valueOf(0);
		this.graph = graph;
		maximalCliques = new LinkedList<Set<V>>();
		// index nodes for fast searching
		nodes = new ArrayList<V>(graph.getVertices());
		algorithmDuration = algorithmDuration.add(BigInteger.valueOf(System.nanoTime()).subtract(start));
	}

	public List<Set<V>> getCliques() {
		BigInteger start = BigInteger.valueOf(System.nanoTime());
		maximalCliques.clear();
		TIAS(new HashSet<V>(), 1);
		findMaximumCliques();
		algorithmDuration = algorithmDuration.add(BigInteger.valueOf(System.nanoTime()).subtract(start));
		return maximalCliques;
	}

	public BigInteger getAlgorithmDuration() {
		return algorithmDuration;
	}

	public static void setRunning(boolean b) {
		isRunning = b;
	}

	/**
	 * Algorytm wyszykiwania maksymalnych klik w grafie. Uruchamiać: TIAS(new
	 * HashSet<V>(), 1);
	 * 
	 * @param maximalClique
	 *            aktulana maksymalana klika
	 * @param nodeIndex
	 *            numer analizowane wierzchołka
	 */
	private void TIAS(HashSet<V> maximalClique, Integer nodeIndex) {
		if (nodeIndex > graph.getVertexCount() && isRunning) {
			maximalCliques.add(maximalClique);
			return;
		}
		V node = nodes.get(nodeIndex - 1);
		Collection<V> neighbors = graph.getNeighbors(node);
		if (neighbors.containsAll(maximalClique)) {
			TIAS(cloneSetAndAddNode(maximalClique, node), nodeIndex + 1);
		} else {
			TIAS(cloneSet(maximalClique), nodeIndex + 1);

			HashSet<V> intersection = getIntersection(neighbors, maximalClique);
			Collection<V> lexiClique = getLexicographicallySmallestMaximalClique(intersection, nodeIndex - 1);
			if (maximalClique.equals(lexiClique)) {
				TIAS(cloneSetAndAddNode(intersection, node), nodeIndex + 1);
			}
		}
	}

	/**
	 * Metoda kopiuje zbior wejsciowy i dodaje do niego nowy element
	 * 
	 * @param set
	 * @param node
	 * @return
	 */
	private HashSet<V> cloneSetAndAddNode(Collection<V> set, V node) {
		HashSet<V> clone = new HashSet<V>(set);
		clone.add(node);
		return clone;
	}

	/**
	 * Metoda kopiuje zbior wejsciowy
	 * 
	 * @param set
	 * @return
	 */
	private HashSet<V> cloneSet(Collection<V> set) {
		return new HashSet<V>(set);
	}

	/**
	 * Metoda znajduje czesc wspolna 2 zbiorow.
	 * 
	 * @param set1
	 * @param set2
	 * @return
	 */
	private HashSet<V> getIntersection(Collection<V> set1, Collection<V> set2) {
		HashSet<V> cloneSet = cloneSet(set1);
		cloneSet.retainAll(set2);
		return cloneSet;
	}

	/**
	 * Metoda zwraca najmniejszą leksygograficznie (czyli sortowanie rosnace
	 * wierzcholkow) klike maksymalna, ktora zawiera klike wejsciowa. Metoda
	 * probuje dodawac do clique kolejne wiercholka i spr czy clique ciagle
	 * bedzie klika
	 * 
	 * @param clique
	 *            klika poczatkowa
	 * @param maxIndex
	 *            numer ostatniego wierzcholka, ktory mozna dolaczyc
	 * @return
	 */
	private HashSet<V> getLexicographicallySmallestMaximalClique(Collection<V> clique, Integer maxIndex) {

		HashSet<V> lexiClique = cloneSet(clique);
		for (int i = 1; i <= maxIndex; ++i) {
			V node = nodes.get(i - 1);
			Collection<V> neighbors = graph.getNeighbors(node);
			// mozna dolaczyc nowy wierzcholek gdy sasiedzi Vi zawieraja
			// wszystkie wierzcholki z kliki. Czyli po dolaczenieu Vi ciagle
			// bedzie istniala klika
			if (neighbors.containsAll(lexiClique))
				lexiClique.add(node);// powiekszamy klike
		}
		return lexiClique;
	}

	private String getPrefix(V node) {
		return node.toString() + ": ";
	}

	public void printAllCliques() {
		for (Collection<V> element : maximalCliques) {
			System.out.println(printClique(element));
		}
	}

	public String printClique(Collection<V> clique) {
		return "Rozmiar: " + clique.size() + " | Wierzchołki: " + clique.toString();
	}

	private void findMaximumCliques() {
		sortMaximalCliques();
		// printAllCliques();
		removeNoMaximumCliques();
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
