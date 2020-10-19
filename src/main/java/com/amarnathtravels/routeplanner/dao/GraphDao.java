package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.dao.db.ITravelInMemoryDB;
import com.amarnathtravels.routeplanner.dao.route.Connection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class GraphDao implements IGraphDao{
		@Autowired ITravelInMemoryDB iTravelInMemoryDB;
		@Override
		public boolean saveGraph(Map<String, Map<String, List<Map<String, List<Connection>>>>> graph) {
				return iTravelInMemoryDB.loadGraphConnections(graph);
		}
}
