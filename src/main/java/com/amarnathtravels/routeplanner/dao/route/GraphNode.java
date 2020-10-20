package com.amarnathtravels.routeplanner.dao.route;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GraphNode {
		String code;
		Connection connection;
}
