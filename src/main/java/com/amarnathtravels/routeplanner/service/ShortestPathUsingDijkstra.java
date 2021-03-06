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
				//parent.entrySet().forEach(node-> node.setValue(noParent));
				adj.entrySet().stream().map(Map.Entry::getKey).forEach(key->{
						dist.putIfAbsent(key,Long.MAX_VALUE);
						parent.put(key, noParent);
				});
				//dist.entrySet().forEach(node -> node.setValue(Long.MAX_VALUE));

				pq.add(new Node(src, null,null,0L, null,null));
				parent.put(src,noParent);
				dist.put(src, 0L);
				while (visited.size() != V && pq.size()>0) {
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
				LocalDateTime lastFlightArrivalTime = node.getLastArrivalTime();
				long edgeDistance = -1;
				long newDistance = -1;
				List<GraphNode> filteredList = filterConnectionList(adj.get(u), lastFlightArrivalTime);
				for (GraphNode graphNode : filteredList) {

						if (!visited.contains(graphNode.getCode())) {
								Node nextNode = new Node();
								String nextStopCode = null;
								if (travelMode == TravelMode.FLIGHT) {
										FlightConnection flightConnection = (FlightConnection) graphNode
												.getConnection();
										nextNode.setFlightCode(flightConnection.getFlight().getFlightNo());
										Duration flightDuration = Duration.between(flightConnection.getDeptTime(),
												flightConnection.getArrivalTime());
										edgeDistance = flightDuration.toMinutes();
										nextStopCode = flightConnection.getDest().getCode();
								} else {
										BusConnection busConnection = (BusConnection) graphNode.getConnection();
										nextNode.setBusCode(busConnection.getBus().getBusNo());
										Duration flightDuration = Duration
												.between(busConnection.getDeptTime(), busConnection.getArrivalTime());
										edgeDistance = flightDuration.toMinutes();
										nextStopCode = busConnection.getDest().getCode();
								}

								if (lastFlightArrivalTime != null) {
										Duration waitingTime = Duration.between(lastFlightArrivalTime,
												graphNode.getConnection().getDeptTime());
										edgeDistance += waitingTime.toMinutes();
								}
								newDistance = dist.get(u) + edgeDistance;
								if (newDistance < dist.get(nextStopCode)){
										dist.put(nextStopCode, newDistance);
										parent.put(nextStopCode, node);
								}
								nextNode.setCode(nextStopCode);
								nextNode.setCostInMinutes(dist.get(nextStopCode));
								nextNode.setLastDepartureTime(graphNode.getConnection().getDeptTime());
								nextNode.setLastArrivalTime(graphNode.getConnection().getArrivalTime());
								pq.add(nextNode);

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
								list.add(collect.get(0));
						}
				});
				return list;
		}

		public List<Node> getShortestPath(Map<String, List<GraphNode>> graph, String src, String dest, TravelMode travelMode,
				LocalDateTime date) {
			dijkstra(graph, src, dest, travelMode, new Node("NO_PARENT",null, null,0L, null,null));
			List<Node> shortestPath = new LinkedList<>();
			if(destNode==null){
					return null;
			}
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
						loadPath(parent.get(currentVertex.getCode()), path);
						System.out.print(currentVertex.getCode() + " -> ");
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

