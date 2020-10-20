package com.amarnathtravels.routeplanner.service;

import com.amarnathtravels.routeplanner.dao.route.*;
import com.amarnathtravels.routeplanner.model.Node;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ShortestPathUsingDijkstra {
		private Map<String, Long> dist;
		private Set<String> visited;
		private Map<String, Map<String, LocalDateTime>> waitingTimeMapInMinutes;
		private PriorityQueue<Node> pq;
		private int V; // Number of vertices
		Map<String, List<GraphNode>> adj;

		public ShortestPathUsingDijkstra(int V) {
				this.V = V;
				dist = new HashMap<>();
				visited = new HashSet<>();
				pq = new PriorityQueue<Node>(V, new DistanceComparator());
		}

		public void dijkstra(Map<String, List<GraphNode>> adj, String src, TravelMode travelMode) {
				this.adj = adj;

				dist.entrySet().forEach(node -> node.setValue(Long.MAX_VALUE));

				pq.add(new Node(src, 0L, null));

				dist.put(src, 0L);
				while (visited.size() != V) {
						Node minNode = pq.remove();
						String u = minNode.getCode();
						visited.add(u);

						e_Neighbours(u, minNode.getLastFlightArrivalTime(), travelMode);
				}
		}

		private void e_Neighbours(String u, LocalDateTime lastFlightArrivalTime, TravelMode travelMode) {
				long edgeDistance = -1;
				long newDistance = -1;
				// All the neighbors of v
				List<GraphNode> filteredList = filterConnectionList(adj.get(u), lastFlightArrivalTime);
				for (GraphNode graphNode : adj.get(u)) {

						if (!visited.contains(graphNode.getCode())) {
								String nextStopCode = null;
								Connection connection = null;
								LocalDateTime lastStopArrivalTime = null;
								if (travelMode == TravelMode.FLIGHT) {
										FlightConnection flightConnection = (FlightConnection) graphNode
												.getConnection();
										Duration flightDuration = Duration.between(flightConnection.getArrivalTime(),
												flightConnection.getDeptTime());
										edgeDistance = flightDuration.toMinutes();
										nextStopCode = flightConnection.getDest().getCode();
								} else {
										BusConnection busConnection = (BusConnection) graphNode.getConnection();
										Duration flightDuration = Duration
												.between(busConnection.getDeptTime(), busConnection.getArrivalTime());
										edgeDistance = flightDuration.toMinutes();
										nextStopCode = busConnection.getDest().getCode();
								}

								if (lastFlightArrivalTime != null) {
										Duration waitingTime = Duration.between(graphNode.getConnection().getDeptTime(),
												lastFlightArrivalTime);
										edgeDistance += waitingTime.toMinutes();
								}
								newDistance = dist.get(u) + edgeDistance;
								// If new distance is cheaper in cost
								if (newDistance < dist.get(nextStopCode))
										dist.put(nextStopCode, newDistance);

								// Add the current node to the queue
								pq.add(new Node(nextStopCode, dist.get(nextStopCode),
										graphNode.getConnection().getArrivalTime()));

						}
				}

		}

		private List<GraphNode> filterConnectionList(List<GraphNode> graphNodes, LocalDateTime lastArrivalTime) {
				Map<String, List<GraphNode>> groupedNodes = graphNodes.stream().filter(graphNode -> {
						if (lastArrivalTime == null) {
								return true;
						}
						LocalDateTime nextDepartureTime = graphNode.getConnection().getArrivalTime();
						return nextDepartureTime.getYear() == lastArrivalTime.getYear() && nextDepartureTime
								.getMonth() == lastArrivalTime.getMonth() && nextDepartureTime
								.getDayOfMonth() == lastArrivalTime.getDayOfMonth();
				}).filter(graphNode -> {
						if (lastArrivalTime == null)
								return true;
						return graphNode.getConnection().getArrivalTime().isAfter(lastArrivalTime);
				}).collect(Collectors.groupingBy(GraphNode::getCode));

				List<GraphNode> list = new ArrayList<>();
				groupedNodes.entrySet().stream().map(Map.Entry::getValue).forEach(nodeList -> {
						if (nodeList != null) {
								List<GraphNode> collect = nodeList.stream().sorted((n1, n2) -> {
										Long duration1 = Duration.between(n1.getConnection().getDeptTime(),
												n1.getConnection().getArrivalTime()).toMinutes();
										Long duration2 = Duration.between(n2.getConnection().getDeptTime(),
												n2.getConnection().getArrivalTime()).toMinutes();
										if (lastArrivalTime != null) {
												duration1 += Duration
														.between(n1.getConnection().getArrivalTime(), lastArrivalTime)
														.toMinutes();
												duration2 += Duration
														.between(n2.getConnection().getArrivalTime(), lastArrivalTime)
														.toMinutes();
										}
										return (int) (duration1 - duration2);
								}).collect(Collectors.toList());
								list.add(collect.get(collect.size() - 1));
						}
				});
				return list;
		}

		//		// Driver code
		//		public static void main(String arg[])
		//		{
		//				int V = 5;
		//				int source = 0;
		//
		//				// Adjacency list representation of the
		//				// connected edges
		//				List<List<Node> > adj = new ArrayList<List<Node> >();
		//
		//				// Initialize list for every node
		//				for (int i = 0; i < V; i++) {
		//						List<Node> item = new ArrayList<Node>();
		//						adj.add(item);
		//				}
		//
		//				// Inputs for the DPQ graph
		//				adj.get(0).add(new Node(1, 9));
		//				adj.get(0).add(new Node(2, 6));
		//				adj.get(0).add(new Node(3, 5));
		//				adj.get(0).add(new Node(4, 3));
		//
		//				adj.get(2).add(new Node(1, 2));
		//				adj.get(2).add(new Node(3, 4));
		//
		//				// Calculate the single source shortest path
		//				ShortestPathUsingDijkstra dpq = new ShortestPathUsingDijkstra(V);
		//				dpq.dijkstra(adj, source);
		//
		//				// Print the shortest path to all the nodes
		//				// from the source node
		//				System.out.println("The shorted path from node :");
		//				for (int i = 0; i < dpq.dist.length; i++)
		//						System.out.println(source + " to " + i + " is "
		//								+ dpq.dist[i]);
		//		}
}

class DistanceComparator implements Comparator<Node> {
		@Override public int compare(Node node1, Node node2) {
				if (node1.getCostInMinutes() < node2.getCostInMinutes())
						return -1;
				if (node1.getCostInMinutes() > node2.getCostInMinutes())
						return 1;
				return 0;
		}
}

