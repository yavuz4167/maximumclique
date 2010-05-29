/**
 * 
 */
package graph.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import graph.algorithm.MaximumClique;
import graph.io.UndirectedGraphReader;
import graph.util.Edge;
import graph.util.Node;

/**
 * @author Mateusz
 * 
 */
public class GraphWindow extends JFrame implements ActionListener {
	// Static variables
	private static final Color defaultCliequeEgdeColor = Color.ORANGE;
	private static final Color defaultCliqueVertexColor = Color.GREEN;
	private static final Color defaultVertexColor = Color.RED;
	private static final String newline = "\n";

	// Algorithm variables
	private Graph<Node, Edge> graph;
	private List<Set<Node>> maximumCliques;
	private MaximumClique<Node, Edge> mc;

	// Visualization variables
	private BasicVisualizationServer<Node, Edge> vv;
	private ArrayList<Transformer<Edge, Paint>> cliqueEgdeColor;
	private ArrayList<Transformer<Node, Paint>> cliqueVertexColor;

	// Swing variables
	private JButton start, stop, load, prev, next;
	private JTextArea console;
	private JPanel top, center;
	private Container content;
	private Calculations calculations;

	// Other
	private int pos = 0, end;

	// Create new thread for calculations
	private class Calculations extends SwingWorker<MaximumClique<Node, Edge>, Void> {

		@Override
		public MaximumClique<Node, Edge> doInBackground() {
			try {
				MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
				maximumCliques = mc.getCliques();
				return mc;
			} catch (IndexOutOfBoundsException e) {
				return null;
			}
		}

		@Override
		public void done() {
			try {
				mc = get();
				if (mc == null)
					return;
				vv = visualize();
				int cliquesSize = maximumCliques.size();
				if (cliquesSize == 1)
					console.setText("Znaleziono 1 największą klikę:");
				else
					console.setText("Znaleziono " + cliquesSize + " największych klik:" + newline);
				for (Set<Node> clique : maximumCliques) {
					console.append("\t" + mc.printClique(clique) + newline);
				}
				console.append("Czas wykonania algorytmu: "
						+ mc.getAlgorithmDuration().divide(BigInteger.valueOf(1000000)) + "ms");
				center.add(vv);
				center.revalidate();
				repaint();
				end = cliquesSize - 1;
				if (cliquesSize != 1)
					next.setEnabled(true);
				else
					next.setEnabled(false);
				stop.setEnabled(false);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			} catch (ExecutionException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * @throws HeadlessException
	 */
	public GraphWindow() throws HeadlessException {
		console = new JTextArea(5, 30);
		console.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(console);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(100, 100));

		load = new JButton("Load");
		load.addActionListener(this);
		start = new JButton("Start");
		start.addActionListener(this);
		start.setEnabled(false);
		stop = new JButton("Stop");
		stop.addActionListener(this);
		stop.setEnabled(false);
		prev = new JButton("Prev");
		prev.addActionListener(this);
		prev.setEnabled(false);
		next = new JButton("Next");
		next.addActionListener(this);
		next.setEnabled(false);

		JToolBar toolBar = new JToolBar();
		toolBar.add(load);
		toolBar.add(start);
		toolBar.add(stop);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.addSeparator();
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(prev);
		toolBar.add(next);
		toolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add(toolBar, BorderLayout.PAGE_START);
		top.add(scrollPane);

		center = new JPanel();
		center.setLayout(new BorderLayout());
		center.setPreferredSize(new Dimension(850, 100));

		content = getContentPane();
		content.setBackground(Color.white);
		content.setLayout(new BorderLayout());
		content.add(top, BorderLayout.PAGE_START);
		content.add(center, BorderLayout.CENTER);
		setPreferredSize(new Dimension(800, 800));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private BasicVisualizationServer<Node, Edge> visualize() {
		cliqueEgdeColor = new ArrayList<Transformer<Edge, Paint>>();
		cliqueVertexColor = new ArrayList<Transformer<Node, Paint>>();
		Layout<Node, Edge> layout = new CircleLayout(graph);
		layout.setSize(new Dimension(800, 600));
		BasicVisualizationServer<Node, Edge> vv = new BasicVisualizationServer<Node, Edge>(layout);
		vv.setBackground(Color.white);
		vv.setPreferredSize(new Dimension(800, 600));

		for (final Set<Node> clique : maximumCliques) {
			Transformer<Edge, Paint> edgePaint = new Transformer<Edge, Paint>() {
				public Paint transform(Edge e) {
					Color c = null;
					for (int i = 1; i < clique.size() + 1; i++) {
						Iterator<Node> it = clique.iterator();
						int j = i;
						Node n = new Node(1);
						while (j-- != 0)
							n = it.next();
						while (it.hasNext()) {
							if (graph.findEdge(n, it.next()) == e)
								c = defaultCliequeEgdeColor;
						}
					}
					return c;
				}
			};
			cliqueEgdeColor.add(edgePaint);

			Transformer<Node, Paint> vertexPaint = new Transformer<Node, Paint>() {

				@Override
				public Paint transform(Node n) {
					for (Node node : clique) {
						if (n.equals(node))
							return defaultCliqueVertexColor;
					}
					return defaultVertexColor;
				}

			};
			cliqueVertexColor.add(vertexPaint);
		}

		float dash[] = { 10.0f };
		final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		Transformer<Edge, Stroke> edgeStrokeTransformer = new Transformer<Edge, Stroke>() {
			public Stroke transform(Edge e) {
				return edgeStroke;
			}
		};
		vv.getRenderContext().setEdgeFillPaintTransformer(cliqueEgdeColor.get(0));
		vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
		vv.getRenderContext().setVertexLabelTransformer(new Transformer<Node, String>() {
			@Override
			public String transform(Node node) {
				if (graph.containsVertex(node))
					return node.toString() + " (" + graph.degree(node) + ")";
				return "UNKNOWN";
			}
		});
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO);
		vv.getRenderContext().setVertexFillPaintTransformer(cliqueVertexColor.get(0));
		return vv;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Load")) {
			MaximumClique.setRunning(true);
			final JFileChooser fc = new JFileChooser(new File("./graphs"));
			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				console.setText("");
				console.append("Wybrano: " + file.getAbsolutePath() + newline);
				UndirectedGraphReader reader = new UndirectedGraphReader();
				graph = reader.load(file.getPath());
				if (graph == null)
					console.append("Wybrano zły format pliku!");
				else {
					start.setEnabled(true);
					prev.setEnabled(false);
					next.setEnabled(false);
					pos = 0;
					this.setTitle("File: " + file.getName());
				}
			}
		}
		if (e.getActionCommand().equals("Stop")) {
			MaximumClique.setRunning(false);
			// calculations.cancel(true);
			stop.setEnabled(false);
			start.setEnabled(true);
			console.append("Obliczenia zatrzymane." + newline);
		}
		if (e.getActionCommand().equals("Start")) {
			calculations = new Calculations();
			calculations.execute();
			start.setEnabled(false);
			stop.setEnabled(true);
			console.setText("Trwa wykonywanie obliczeń..." + newline);
			console.append("Aby przerwać obliczenia naciśnij \"Stop\"" + newline);
		}
		if (e.getActionCommand().equals("Next")) {
			if (pos < end) {
				pos++;
				vv.getRenderContext().setEdgeFillPaintTransformer(cliqueEgdeColor.get(pos));
				vv.getRenderContext().setVertexFillPaintTransformer(cliqueVertexColor.get(pos));
				vv.revalidate();
				vv.repaint();
				prev.setEnabled(true);
				if (pos == end)
					next.setEnabled(false);
			}
		}
		if (e.getActionCommand().equals("Prev")) {
			if (pos > 0) {
				pos--;
				vv.getRenderContext().setEdgeFillPaintTransformer(cliqueEgdeColor.get(pos));
				vv.getRenderContext().setVertexFillPaintTransformer(cliqueVertexColor.get(pos));
				vv.revalidate();
				vv.repaint();
				next.setEnabled(true);
				if (pos == 0)
					prev.setEnabled(false);
			}
		}
	}
}