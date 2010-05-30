package graph.test;

import java.io.Serializable;
import java.math.BigInteger;
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
public class MassRandomTester implements Serializable {

	/**
	 * Ilość powtórzeń wykonania algorytmu dla losowego grafu
	 */
	private int numIteration;

	/**
	 * Wielkości poszczególnych grafów, czyli ilości wierzchołków
	 */
	private Collection<Integer> numVertexList;

	/**
	 * Wartości prawdopobieństw
	 */
	private Collection<Double> probabilityList;

	/**
	 * Czasy wykonania algorytmu dla grafów o poszczególnych numVertex i
	 * probability. Zew lista - wierzochoolki Wew lista - prawdopowodobieństwa
	 */
	private ArrayList<ArrayList<BigInteger>> times;

	/**
	 * 
	 * @param numVertexList
	 *            wielkości grafu (ilość wierzchołków)
	 * @param probabilityList
	 *            gęstości grafu (wartości prawdopodobieńst)
	 * @param numIteration
	 *            liczba powtórzeń algorytmu dla losowego grafu o tych samych
	 *            parametrach
	 */
	public MassRandomTester(Collection<Integer> numVertexList, Collection<Double> probabilityList, int numIteration) {
		super();
		this.numVertexList = numVertexList;
		this.probabilityList = probabilityList;
		this.numIteration = numIteration;

		times = new ArrayList<ArrayList<BigInteger>>();
	}

	/**
	 * For serialization
	 */
	public MassRandomTester() {

	}

	/**
	 * Oblicznie średnich czasów wykonania algorytmu dla zdefinowanych wielkości
	 * i gestości
	 * 
	 * @return
	 */
	public ArrayList<ArrayList<BigInteger>> run() {
		times.clear();
		for (Integer numVertex : numVertexList) {
			ArrayList<BigInteger> timesForAllProbability = new ArrayList<BigInteger>(probabilityList.size());
			for (Double probability : probabilityList) {
				RandomTester randomTester = new RandomTester(numVertex.intValue(), probability.doubleValue(),
						numIteration);
				BigInteger duration = randomTester.run();
				timesForAllProbability.add(duration);
			}
			times.add(timesForAllProbability);
		}
		return getTimes();
	}

	public ArrayList<ArrayList<BigInteger>> getTimes() {
		return times;
	}

	/**
	 * Obliczenie średniego czasu algorytmu w zależności od wielkości grafu
	 * (gęstość zostaje uśredniona)
	 * 
	 * @return
	 */
	public ArrayList<BigInteger> getAverageVertexTimes() {
		ArrayList<BigInteger> averageForVetexes = new ArrayList<BigInteger>(times.size());
		for (ArrayList<BigInteger> timesForAllProbability : times) {
			BigInteger sum = BigInteger.ZERO;
			for (BigInteger time : timesForAllProbability) {
				sum = sum.add(time);
			}
			boolean add = averageForVetexes.add(sum.divide(BigInteger.valueOf(timesForAllProbability.size())));
		}
		return averageForVetexes;
	}

	public int getNumIteration() {
		return numIteration;
	}

	public void setNumIteration(int numIteration) {
		this.numIteration = numIteration;
	}

	public Collection<Integer> getNumVertexList() {
		return numVertexList;
	}

	public void setNumVerticesList(Collection<Integer> numVerticesList) {
		this.numVertexList = numVertexList;
	}

	public Collection<Double> getProbabilityList() {
		return probabilityList;
	}

	public void setProbabilityList(Collection<Double> probabilityList) {
		this.probabilityList = probabilityList;
	}
}
