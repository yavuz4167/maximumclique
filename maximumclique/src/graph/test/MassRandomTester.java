package graph.test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Klasa służy do pomiaru czasu wykonania algorytmu wyszukiania najwiekszej
 * kliki w grafie dla grafów o zdefiniowanych: wielkości i gęstości.
 * 
 * Testowane są grafy o różnych wielkościach i gęstościach.
 * 
 * @author bbarczynski
 * 
 */
public class MassRandomTester {
	Collection<Integer> numVerticesList;
	Collection<Double> probabilityList;
	/**
	 * Ilość powtórzeń wykonania algorytmu dla losowego grafu
	 */
	private int numIteration;

	/**
	 * Czasy wykonania algorytmu dla grafów o poszczególnych numVertices i
	 * probability
	 */
	ArrayList<ArrayList<Double>> times;

	/**
	 * 
	 * @param numVerticesList
	 *            wielkości grafu (ilość wierzchołków)
	 * @param probabilityList
	 *            gęstości grafu (wartości prawdopodobieńst)
	 * @param numIteration
	 *            liczba powtórzeń algorytmu dla losowego grafu o tych samych
	 *            parametrach
	 */
	public MassRandomTester(Collection<Integer> numVerticesList, Collection<Double> probabilityList, int numIteration) {
		super();
		this.numVerticesList = numVerticesList;
		this.probabilityList = probabilityList;
		this.numIteration = numIteration;

		times = new ArrayList<ArrayList<Double>>();
	}

	/**
	 * Oblicznie średnich czasów wykonania algorytmu dla zdefinowanych wielkości
	 * i gestości
	 * 
	 * @return
	 */
	public ArrayList<ArrayList<Double>> run() {
		times.clear();
		for (Integer numVertices : numVerticesList) {
			ArrayList<Double> timesForAllProbability = new ArrayList<Double>(probabilityList.size());
			for (Double probability : probabilityList) {
				RandomTester randomTester = new RandomTester(numVertices.intValue(), probability.doubleValue(),
						numIteration);
				double duration = randomTester.run();
				timesForAllProbability.add(duration);
			}
			times.add(timesForAllProbability);
		}
		return getAverageTimes();
	}

	public ArrayList<ArrayList<Double>> getAverageTimes() {
		return times;
	}
}
