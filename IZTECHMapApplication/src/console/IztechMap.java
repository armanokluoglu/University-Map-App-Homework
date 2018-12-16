package console;

import java.util.List;
import exceptions.InvalidOperationNumberException;
import nodes.BuildingNode;
import nodes.CampusNode;
import nodes.LandscapeNode;
import types.Campus;
import types.Category;
import types.CategoryType;
import types.Pair;
import types.Path;
import utilities.DistanceCalculator;
import utilities.MapEditor;
import utilities.MapReader;
import java.util.ArrayList;
import java.util.Comparator;

public class IztechMap {

	private Campus campus;
	private String path;

	public IztechMap(String path) {
		this.path = path;
		MapReader mapReader = new MapReader();
		List<CampusNode> nodes = mapReader.readNodesFromFile(path);
		List<Pair> pairs = mapReader.readPairsFromFile(path);
		campus = new Campus(nodes, pairs);
	}

	private void addNode(CampusNode aNode, List<CampusNode> neighbors) {
		MapEditor editor = new MapEditor(campus);
		editor.addNode(aNode, path);
		List<Pair> pairs = new ArrayList<>();
		for(CampusNode neighbor: neighbors) {
			Pair pair = new Pair(aNode.getId(), neighbor.getId());
			editor.addPair(pair, path);
			pairs.add(pair);
		}
		aNode.setNeighbors(neighbors);
		for(CampusNode node: campus.getNodes()) {
			if(neighbors.contains(node)) {
				List<CampusNode> nodeNeighbors = node.getNeighbors();
				nodeNeighbors.add(aNode);
			}
		}
		campus.addNode(aNode);
	}

	private CampusNode removeNode(CampusNode aNode) {
		MapEditor editor = new MapEditor(campus);
		editor.removeNode(aNode, path);
		List<CampusNode> neighborsOfNode = aNode.getNeighbors();
		for(CampusNode neighbor: neighborsOfNode) {
			Pair pair = new Pair(aNode.getId(), neighbor.getId());
			editor.removePair(pair, path);
			List<CampusNode> neighborsOfNeighbor = neighbor.getNeighbors();
			neighborsOfNeighbor.remove(aNode);
			for(CampusNode otherNeighbor: neighborsOfNode) {
				if(!otherNeighbor.equals(neighbor)) {
					neighborsOfNeighbor.add(otherNeighbor);
				}
			}
		}
		campus.removeNode(aNode);
		return aNode;
	}

	private void printShortestPathBetween(CampusNode node1, CampusNode node2) {
		DistanceCalculator distCalc = new DistanceCalculator(campus);
		List<Path> paths = distCalc.shortestPath(node1, node2);
		String str = "";
		if(paths.size() == 0) {
			str = "No such path exists.";
		} else {
			Path firstPath = paths.get(0);
			str = firstPath.toString();
			for(int i = 1; i < paths.size(); i++) {
				str += " ---(" + paths.get(i).getDistance() + " Units)---> " + paths.get(i).getEndingPoint().getName();
			}
		}
		System.out.println(str + "\n");
	}

	private void printReachableNodesFrom(CampusNode aNode, int distance) {
		DistanceCalculator distCalc = new DistanceCalculator(campus);
		List<CampusNode> nodes = distCalc.reachableNodesFrom(aNode, distance);
		for(CampusNode node: nodes) {
			System.out.println(node.toString());
		}
		if(nodes.size() == 0) {
			System.out.println("No location found.");
		}
		System.out.println();
	}

	private void printSortedNeighborsOf(CampusNode aNode) {
		List<CampusNode> neighborsOfGivenNode = aNode.getNeighbors();
		neighborsOfGivenNode.sort(Comparator.naturalOrder());
		for(CampusNode nextNode: neighborsOfGivenNode) {
			System.out.println(nextNode.toString());
		}
	}

	private void printAvailableCommands() {
		System.out.println("1.Add a node to the map.");
		System.out.println("2.Remove node from the the map.");
		System.out.println("3.Print reachable nodes from a specified node.");
		System.out.println("4.Print neighbors of a specified node.");
		System.out.println("5.Print the shortest path between two given nodes.");
		System.out.println("0 to exit the program.");
	}

	private void printAvailableCategories(Category category) {
		if(category == Category.BUILDING) {
			System.out.println("1.Department \n 2.Cafeteria");
			System.out.println("3.Administrative \n 4.Facilities");
		} else {
			System.out.println("1.Waterfall \n 2.Beach");
			System.out.println("3.Historical ruin");
		}
	}

	// Delegating the scanner part to another class in which a separate scanner
	// object is created.
	private CampusNode readNode() {
		System.out.println("Please choose the category of node you'd like to add: ");
		System.out.println("1.Building node \n 2.Landscape node");
		MapKeyboardReader inputReader = new MapKeyboardReader();
		CategoryType typeOfCategory = null;
		String name;
		CampusNode readNode = null;
		System.out.println("Please enter the name of node: ");
		name = inputReader.readName();
		int categoryNo = inputReader.readTypeNumber();
		switch(categoryNo) {
		// Building node case
		case 1:
			printAvailableCategories(Category.BUILDING);
			System.out.println("Please enter the category of type number here: ");
			int typeNo = inputReader.readTypeNumber();
			switch(typeNo) {
			case 1:
				typeOfCategory = CategoryType.DEPARTMENT;
				break;

			case 2:
				typeOfCategory = CategoryType.CAFETERIA;
				break;

			case 3:
				typeOfCategory = CategoryType.ADMINISTRATIVE;
				break;
			case 4:

				typeOfCategory = CategoryType.FACILITY;
				break;

			default:
				try {
					throw new InvalidOperationNumberException();
				} catch (InvalidOperationNumberException e) {
					System.out.println("Invalid entry, try again.\n");
					readNode();
				}
			}
			readNode = new BuildingNode(name, campus.getMaxId() + 1, typeOfCategory);
			break;

		case 2:
			printAvailableCategories(Category.LANDSCAPE);
			System.out.println("Please enter the category of type number here: ");
			int typeNumber = inputReader.readTypeNumber();
			switch(typeNumber) {
			case 1:
				typeOfCategory = CategoryType.WATERFALL;
				break;

			case 2:
				typeOfCategory = CategoryType.BEACH;
				break;

			case 3:
				typeOfCategory = CategoryType.HISTORICALRUIN;
				break;

			default:
				try {
					throw new InvalidOperationNumberException();
				} catch (InvalidOperationNumberException e) {
					System.out.println("Invalid entry, try again.\n");
					readNode();
				}
			}
			readNode = new LandscapeNode(name, campus.getMaxId() + 1, typeOfCategory);
			break;

		default:
			try {
				throw new InvalidOperationNumberException();
			} catch (InvalidOperationNumberException e) {
				System.out.println("Invalid entry, try again.\n");
				readNode();
			}
		}
		return readNode;
	}

	public List<CampusNode> readNeighbors() {
		MapKeyboardReader inputReader = new MapKeyboardReader();
		String ids;
		System.out.println("Please enter the ids of neighboring nodes separated by comma: ");
		ids = inputReader.readName();
		String[] idArray = ids.split(",");
		List<CampusNode> nodes = campus.getNodes();
		List<CampusNode> neighbors = new ArrayList<>();
		for(CampusNode node: nodes) {
			for(int i = 0; i < idArray.length; i++) {
				if(node.getId() == Integer.parseInt(idArray[i])) {
					neighbors.add(node);
				}
			}
		}
		return neighbors;
	}

	public void takeQuery() {
		boolean end = false;
		MapKeyboardReader inputReader = new MapKeyboardReader();
		while(!end) {
			printAvailableCommands();
			int operationNo = inputReader.readOperationNumber();
			switch(operationNo) {
			// Quitting.
			case 0:
				System.out.println("Goodbye.");
				end = true;
				break;

			// Adding a node.
			case 1:
				CampusNode nodeToBeAdded = readNode();
				List<CampusNode> neighbors = readNeighbors();
				addNode(nodeToBeAdded, neighbors);
				System.out.println(nodeToBeAdded.toString() + " was added.");
				break;

			// Removing a node.
			case 2:
				System.out.println("Please enter the id of the node that will be removed.");
				int id = inputReader.readId();
				CampusNode removedNode = removeNode(campus.getNodeFromId(id));
				System.out.println(removedNode.toString() + " was removed.");
				break;

			// Printing reachable nodes from a specified node.
			case 3:
				System.out.println("Please enter the id of the node.");
				int id2 = inputReader.readId();
				System.out.println("Please enter the distance.");
				int distance = inputReader.readId();
				printReachableNodesFrom(campus.getNodeFromId(id2), distance);
				break;

			// Printing neighbors of a specified node.
			case 4:
				System.out.println("Please enter the id of the node.");
				int id4 = inputReader.readId();
				printSortedNeighborsOf(campus.getNodeFromId(id4));
				break;

			// Printing the shortest path between two nodes.
			case 5:
				System.out.println("Please enter the first node's id here: ");
				int firstId = inputReader.readId();
				System.out.println("Please enter the second node's id here: ");
				int secondId = inputReader.readId();
				CampusNode node1 = campus.getNodeFromId(firstId);
				CampusNode node2 = campus.getNodeFromId(secondId);
				printShortestPathBetween(node1, node2);
				break;
				
			default:
				try {
					throw new InvalidOperationNumberException();
				} catch (InvalidOperationNumberException e) {
					System.out.println("Invalid entry, try again.\n");
					continue;
				}
			}
		}
	}
}