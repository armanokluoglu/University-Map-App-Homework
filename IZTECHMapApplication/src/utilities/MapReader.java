package utilities;

import java.util.List;
import data.layer.IzmapReader;
import nodes.CampusNode;
import types.Pair;

public class MapReader {

	public List<CampusNode> readNodesFromFile(String path) {
		IzmapReader reader = new IzmapReader();
		MapParser parser = new MapParser();
		return parser.parseNodes(reader.readLines(path));
	}
	
	public List<Pair> readPairsFromFile(String path) {
		IzmapReader reader = new IzmapReader();
		MapParser parser = new MapParser();
		return parser.parsePairs(reader.readLines(path));
	}
}