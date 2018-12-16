package utilities;

import java.util.ArrayList;
import java.util.List;
import nodes.BuildingNode;
import nodes.CampusNode;
import nodes.LandscapeNode;
import types.CategoryType;
import types.Pair;

public class MapParser {

	public List<CampusNode> parseNodes(List<String> lines) {
		List<CampusNode> nodes = new ArrayList<>();
		for(String line : lines) {
			if(line.equals("")) {
				break;
			}
			if(line.charAt(0) != '#') {
				String[] tokens = line.replaceAll(", ", ",").replaceAll(" \\[", "\\[").split("[\\[\\],]");
				if(tokens[1].equals("Building")) {
					BuildingNode node = new BuildingNode(tokens[3], Integer.parseInt(tokens[0]),
							CategoryType.valueOf(tokens[2].toUpperCase().replaceAll(" ", "")));
					nodes.add(node);
				} else if(tokens[1].equals("Landscape")) {
					LandscapeNode node = new LandscapeNode(tokens[3], Integer.parseInt(tokens[0]),
							CategoryType.valueOf(tokens[2].toUpperCase().replaceAll(" ", "")));
					nodes.add(node);
				}
			}
		}
		return nodes;
	}

	public List<Pair> parsePairs(List<String> lines) {
		int spaceCounter = 0;
		List<Pair> pairs = new ArrayList<>();
		for(String line : lines) {
			if(line.equals("")) {
				spaceCounter++;
				continue;
			}
			if(!(spaceCounter > 0)) {
				continue;
			} else {
				if(line.charAt(0) != '#') {
					if(line.equals("\n")) {
						break;
					}
					String[] tokens = line.replaceAll("\\s", "").split("[\\<\\-\\>]");
					Pair pair = new Pair(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[4]));
					pairs.add(pair);
				}
			}
		}
		return pairs;
	}
}