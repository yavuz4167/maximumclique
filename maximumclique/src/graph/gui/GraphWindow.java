/**
 * 
 */
package graph.gui;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import graph.algorithm.MaximumClique;
import graph.io.UndirectedGraphReader;
import graph.util.Edge;
import graph.util.Node;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

import org.apache.commons.collections15.Transformer;

/**
 * @author Mateusz
 * 
 */
public class GraphWindow extends JFrame implements ActionListener {
	// Static variables
	public static final Color colors[] = { Color.GREEN, Color.ORANGE,
			Color.BLUE, Color.PINK, Color.MAGENTA, Color.CYAN, Color.DARK_GRAY,
			Color.GRAY, Color.RED, Color.YELLOW };
	public static final Color defaultColor = Color.WHITE;
	private static final String newline = "\n";

	// Algorithm variables
	private Graph<Node, Edge> graph;
	private List<Set<Node>> maximumCliques;
	MaximumClique<Node, Edge> mc;

	// Swing variables
	private JButton start, stop, load;
	private JTextArea console;
	private JPanel top, center;
	private Container content;
	private Calculations calculations;
	
	// Create new thread for calculations
	private class Calculations extends
			SwingWorker<MaximumClique<Node, Edge>, Void> {

		public MaximumClique<Node, Edge> doInBackground() {
			MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
			maximumCliques = new MaximumClique<Node, Edge>(graph).getCliques();
			return mc;
		}
		
		public void cancel() {
			mc.setRunning(false);
		}

		public void done() {
			try {
				mc = get();
				BasicVisualizationServer<Node, Edge> vv = visualize();
				console.setText("");
				for (Set<Node> clique : maximumCliques) {
					console.append("Nawiêksza klika to: "
							+ mc.printClique(clique) + newline);
				}
				console.append("Czas wykonania algorytmu: " + mc.getAlgorithmDuration());
				center.add(vv);
				center.revalidate();
				repaint();
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
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(100, 100));

		load = new JButton("Load");
		load.addActionListener(this);
		start = new JButton("Start");
		start.addActionListener(this);
		start.setEnabled(false);
		stop = new JButton("Stop");
		stop.addActionListener(this);
		stop.setEnabled(false);

		JToolBar toolBar = new JToolBar();
		toolBar.add(load);
		toolBar.add(start);
		toolBar.add(stop);

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
		Layout<Node, Edge> layout = new CircleLayout(graph);
		layout.setSize(new Dimension(800, 600));
		BasicVisualizationServer<Node, Edge> vv = new BasicVisualizationServer<Node, Edge>(
				layout);
		vv.setBackground(Color.white);
		vv.setPreferredSize(new Dimension(800, 600));

		// Maluje wszystkie znalezione najwiêksze kliki innym kolorem (do 10)
		Transformer<Edge, Paint> edgePaint = new Transformer<Edge, Paint>() {
			public Paint transform(Edge e) {
				Color c = defaultColor;
				int k = 0;
				for (Set<Node> clique : maximumCliques) {
					for (int i = 1; i < clique.size() + 1; i++) {
						Iterator<Node> it = clique.iterator();
						int j = i;
						Node n = new Node(1);
						while (j-- != 0)
							n = it.next();
						while (it.hasNext()) {
							if (graph.findEdge(n, it.next()) == e)
								c = colors[k];
						}
					}
					k++;
				}
				return c;
			}
		};

		float dash[] = { 10.0f };
		final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		Transformer<Edge, Stroke> edgeStrokeTransformer = new Transformer<Edge, Stroke>() {
			public Stroke transform(Edge e) {
				return edgeStroke;
			}
		};
		vv.getRenderContext().setEdgeFillPaintTransformer(edgePaint);
		vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);

		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO);
		return vv;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Load")) {
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				console.setText("");
				console.append("Wybrano: " + file.getAbsolutePath() + newline);
				UndirectedGraphReader reader = new UndirectedGraphReader();
				graph = reader.load(file.getPath());
				start.setEnabled(true);
			}
		}
		if (e.getActionCommand().equals("Stop")) {
			calculations.cancel(true);
			stop.setEnabled(false);
			start.setEnabled(true);
		}
		if (e.getActionCommand().equals("Start")) {
			calculations = new Calculations();
			calculations.execute();
			start.setEnabled(false);
			stop.setEnabled(true);
			console.setText("Trwa wykonywanie obliczeñ..." + newline);
			console.append("Aby przerwaæ obliczenia naciœnij \"Stop\""
					+ newline);
		}
	}
}