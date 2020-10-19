package com.amarnathtravels.routeplanner.dao;

import com.amarnathtravels.routeplanner.dao.route.Connection;

import java.util.List;
import java.util.Map;

public interface IGraphDao {
		boolean saveGraph(Map<String, Map<String,List<Map<String, List<Connection>>>>> graph);
}
