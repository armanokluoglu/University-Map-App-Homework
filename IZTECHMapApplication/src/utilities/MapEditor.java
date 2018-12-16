package utilities;

import java.util.List;
import data.layer.IzmapReader;
import data.layer.IzmapWriter;
import nodes.CampusNode;
import types.Campus;
import types.Pair;

public class MapEditor {
	private Campus campus;
	
	public MapEditor(Campus campus) {
		this.campus = campus;
	}
	
	public void addNode(CampusNode node, String path) {
		IzmapWriter writer = new IzmapWriter();
		IzmapReader reader = new IzmapReader();
		List<String> lines = reader.readLines(path);
		String str = node.toString() + "\n";
		lines.add(node.getId() + 1, str);
		writer.write(path, str);
	}
	
	public void addPair(Pair pair, String path) {
		IzmapWriter writer = new IzmapWriter();
		IzmapReader reader = new IzmapReader();
		List<String> lines = reader.readLines(path);
		String str = pair.toString() + "\n";
		lines.add(pair.getMaxId() + campus.getSize() + 3, str);
		writer.write(path, str);
	}
	
	public void removeNode(CampusNode node, String path) {
		IzmapWriter writer = new IzmapWriter();
		IzmapReader reader = new IzmapReader();
		List<String> lines = reader.readLines(path);
		String str = "";
		for(int i = 0; i < lines.size(); i++) {
			String[] tokens = lines.get(i).split(",[]\n\\s");
			if(Integer.parseInt(tokens[0]) == node.getId()) {
				lines.remove(i);
				break;
			}
		}
		for(String line: lines) {
			str += line;
		}
		writer.write(path, str);
	}
	
	public void removePair(Pair pair, String path) {
		IzmapWriter writer = new IzmapWriter();
		IzmapReader reader = new IzmapReader();
		List<String> lines = reader.readLines(path);
		String str = "";
		for(int i = 0; i < lines.size(); i++) {
			String[] tokens = lines.get(i).split("\n\\s<>-");
			if(pair.equals(new Pair(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])))) {
				lines.remove(i);
				break;
			}
		}
		for(String line: lines) {
			str += line;
		}
		writer.write(path, str);
	}
}