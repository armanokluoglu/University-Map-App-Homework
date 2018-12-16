package types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nodes.CampusNode;

public class Campus {
	private Map<Integer, CampusNode> campus;

	
	public Campus() {
		campus = new HashMap<>();
	}
	
	public Campus(List<CampusNode> nodes, List<Pair> pairs) {
		campus = new HashMap<>();
		for(CampusNode node1: nodes) {
			List<CampusNode> neighbors = new ArrayList<>();
			for(CampusNode node2: nodes) {
				Pair pair = new Pair(node1.getId(), node2.getId());
				if(pairs.contains(pair)) {
					neighbors.add(node2);	
				}
			}
			node1.setNeighbors(neighbors);
			addNode(node1);
		}
	}
	
	public Map<Integer, CampusNode> getCampus() {
		return campus;
	}

	public void setCampus(Map<Integer, CampusNode> campus) {
		this.campus = campus;
	}

	public List<CampusNode> getNodes() {
		List<CampusNode> nodes = new ArrayList<>();
		for(CampusNode node: campus.values()) {
			nodes.add(node);
		}
		return nodes;
	}
	
	public List<Pair> getPairs() {
		List<Pair> pairs = new ArrayList<>();
		List<CampusNode> nodes = new ArrayList<>();
		for(CampusNode value: campus.values()) {
			nodes.add(value);
		}
		for(CampusNode node: nodes) {
			for(int i = 1; i < nodes.size(); i++) {
				if(node.getNeighbors().contains(nodes.get(i))) {
					Pair pair = new Pair(node.getId(), nodes.get(i).getId());
					pairs.add(pair);
				}
			}
		}
		return pairs;
	}

	public int getMaxId() {
		Set<Integer> keys = campus.keySet();
		int maxKey = 0;
		for(Integer key: keys) {
			if(key > maxKey) {
				maxKey = key;
			}
		}
		return maxKey;
	}
	
	public CampusNode getNodeFromId(int id) {
		return campus.get(id);
	}

	public void addNode(CampusNode node) {
		campus.put(node.getId(), node);
	}
	
	public CampusNode removeNode(CampusNode node) {
		return campus.remove(node.getId());
	}
	
	public CampusNode removeNode(int id) {
		return campus.remove(id);
	}
	
	public int getSize() {
		return campus.size();
	}
}