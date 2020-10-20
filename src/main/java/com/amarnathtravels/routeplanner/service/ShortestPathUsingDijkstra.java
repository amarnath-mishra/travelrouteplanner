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
		private Map<String, Node> parent;
		private Node destNode = null;

		public ShortestPathUsingDijkstra(int V) {
				this.V = V;
				dist = new HashMap<>();
				parent = new HashMap<>();
				visited = new HashSet<>();
				pq = new PriorityQueue<Node>(V, new DistanceComparator());
		}

		public void dijkstra(Map<String, List<GraphNode>> adj, String src, String dest, TravelMode travelMode, Node noParent) {
				this.adj = adj;
				parent.entrySet().forEach(node-> node.setValue(noParent));
				dist.entrySet().forEach(node -> node.setValue(Long.MAX_VALUE));

				pq.add(new Node(src, 0L, null));
				parent.put(src,noParent);
				dist.put(src, 0L);
				while (visited.size() != V) {
						Node minNode = pq.remove();
						if(minNode.getCode().equals(dest)){
								destNode = minNode;
						}
						String u = minNode.getCode();
						visited.add(u);

						goOverNeighbours(minNode, travelMode, parent);
				}
		}

		private void goOverNeighbours(Node node, TravelMode travelMode, Map<String, Node> parent) {
				String u = node.getCode();
				LocalDateTime lastFlightArrivalTime = node.getLastFlightArrivalTime();
				long edgeDistance = -1;
				long newDistance = -1;
				List<GraphNode> filteredList = filterConnectionList(adj.get(u), lastFlightArrivalTime);
				for (GraphNode graphNode : filteredList) {

						if (!visited.contains(graphNode.getCode())) {
								String nextStopCode = null;
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
								if (newDistance < dist.get(nextStopCode)){
										dist.put(nextStopCode, newDistance);
										parent.put(nextStopCode, node);
								}
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

		public List<Node> getShortestPath(Map<String, List<GraphNode>> graph, String src, String dest, TravelMode travelMode,
				LocalDateTime date) {
			dijkstra(graph, src, dest, travelMode, new Node("NO_PARENT",0L,null));
			List<Node> shortestPath = new LinkedList<>();
			loadPath(destNode, shortestPath);
			return shortestPath;
		}

		private  void loadPath(Node currentVertex, List<Node> path)
		{
				if (currentVertex.getCode().equals("NO_PARENT"))
				{
						return;
				}
				if(visited.contains(currentVertex.getCode()))
				{
						loadPath(parent.get(currentVertex), path);
						System.out.print(currentVertex.getCode() + " ");
						path.add(currentVertex);
				}

		}

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

