package utilities;

import java.util.ArrayList;
import java.util.List;
import exceptions.NodesNotNeighborsException;
import nodes.CampusNode;
import types.Campus;
import types.CategoryType;
import types.Pair;
import types.Path;

public class DistanceCalculator {
	private Campus campus;
	
	public DistanceCalculator(Campus campus) {
		this.campus = campus;
	}
	
	public int getDistanceBetweenNeighbors(CampusNode node1, CampusNode node2) {
		int distance = 0;
		if(!areNodesNeighbors(node1, node2)) {
			try {
				throw new NodesNotNeighborsException();
			} catch (NodesNotNeighborsException e) {
				e.printStackTrace();
			} 
		}
		distance = chooseAppropriateFormula(node1, node2);
		return distance;
	}

	private int chooseAppropriateFormula(CampusNode node1, CampusNode node2) {
		int distance = 6; //default distance
		if((node1.getTypeOfCategory() == CategoryType.DEPARTMENT && node2.getTypeOfCategory() == CategoryType.CAFETERIA) ||
			(node1.getTypeOfCategory() == CategoryType.CAFETERIA && node2.getTypeOfCategory() == CategoryType.DEPARTMENT)){
			distance =  Formula.DepToCafFormula.getDistance();
			
		}else if((node1.getTypeOfCategory() == CategoryType.ADMINISTRATIVE && node2.getTypeOfCategory() == CategoryType.DEPARTMENT) ||
				(node1.getTypeOfCategory() == CategoryType.DEPARTMENT && node2.getTypeOfCategory() == CategoryType.ADMINISTRATIVE)){
			distance =  Formula.AdToDepFormula.getDistance();
			
		}else if((node1.getTypeOfCategory() == CategoryType.CAFETERIA && node2.getTypeOfCategory() == CategoryType.FACILITY) ||
					(node1.getTypeOfCategory() == CategoryType.FACILITY && node2.getTypeOfCategory() == CategoryType.CAFETERIA)){
			distance =  Formula.CafToFacFormula.getDistance();
			
		}else if((node1.getTypeOfCategory() == CategoryType.CAFETERIA && node2.getTypeOfCategory() == CategoryType.WATERFALL) ||
					(node1.getTypeOfCategory() == CategoryType.WATERFALL && node2.getTypeOfCategory() == CategoryType.CAFETERIA)){
			distance =  Formula.CafToWatFormula.getDistance();
			
		}else if((node1.getTypeOfCategory() == CategoryType.FACILITY && node2.getTypeOfCategory() == CategoryType.WATERFALL) ||
					(node1.getTypeOfCategory() == CategoryType.WATERFALL && node2.getTypeOfCategory() == CategoryType.FACILITY)){
			distance =  Formula.FacToWatFormula.getDistance();
			
		}else if((node1.getTypeOfCategory() == CategoryType.HISTORICALRUIN && node2.getTypeOfCategory() == CategoryType.CAFETERIA) ||
					(node1.getTypeOfCategory() == CategoryType.CAFETERIA && node2.getTypeOfCategory() == CategoryType.HISTORICALRUIN)){
			distance =  Formula.HisToCafFormula.getDistance();
			
		}else if((node1.getTypeOfCategory() == CategoryType.DEPARTMENT && node2.getTypeOfCategory() == CategoryType.BEACH) ||
					(node1.getTypeOfCategory() == CategoryType.BEACH && node2.getTypeOfCategory() == CategoryType.CAFETERIA)){
			distance =  Formula.DepToBeachFormula.getDistance();
		}
		return distance;
	}

	private boolean areNodesNeighbors(CampusNode node1, CampusNode node2) {
		List<CampusNode> neighborsOfNode1 = node1.getNeighbors();
		for(CampusNode node: neighborsOfNode1) {
			if(node.getId() == node2.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public List<CampusNode> reachableNodesFrom(CampusNode source, int distance) {
		List<CampusNode> nodes = campus.getNodes();
		List<CampusNode> reachableNodes = new ArrayList<>();
		for(CampusNode node: nodes) {
			int pathDistance = calculateShortestPath(source, node);
			if(distance >= pathDistance) {
				reachableNodes.add(node);
			}
		}
		reachableNodes.remove(source);
		return reachableNodes;
	}
	
	public List<Path> shortestPath(CampusNode source, CampusNode destination) {
		Walker walker = new Walker();
		walker.setStartingPoint(source);
		walker.setTargetPoint(destination);
		List<Pair> pairs = campus.getPairs();
		for(Pair pair: pairs) {
			CampusNode startingNode = campus.getNodeFromId(pair.getId1());
			CampusNode targetNode = campus.getNodeFromId(pair.getId2());
			walker.addPath(startingNode, targetNode, getDistanceBetweenNeighbors(startingNode, targetNode));
		}
		walker.experiencePaths();
		List<Path> paths = walker.shortestPath;
		return paths;
	}
	
	public int calculateShortestPath(CampusNode source, CampusNode destination) {
		int distance = 0;
		List<Path> paths = shortestPath(source, destination);
		for(Path path: paths) {
			distance += path.getDistance();
		}
		return distance;
	}	
	
	public class Walker {
	    private CampusNode startingPoint;
	    private CampusNode targetPoint;
	    private List<Path> allPaths;
	    private List<Path> shortestPath;
	    private List<Path> currentPath;
	    
	    public void setTargetPoint(CampusNode value) {
            targetPoint = value;
        }

        public void setStartingPoint(CampusNode value) {
            startingPoint = value;
        }

        public Walker() {
            this.allPaths = new ArrayList<Path>();
            this.shortestPath = new ArrayList<Path>();
            this.currentPath = new ArrayList<Path>();
        }
        
        public void addPath(CampusNode startingPoint, CampusNode endingPoint, int distance) {
            Path path = new Path(startingPoint, endingPoint, distance);
            this.allPaths.add(path);
        }

        public boolean move(Path path) {
            if(checkIfPathIsAlreadyExperienced(path)) {
                return false;
            }
            currentPath.add(path);
            return true;
        }
        
        public List<Path> getAvailablePaths(CampusNode point) {
            List<Path> availablePaths = new ArrayList<Path>();
            for(Path path: this.allPaths) {
                if(path.getStartingPoint() == point) {
                    availablePaths.add(path);   
                }
            }
            return availablePaths;
        }

        public void experiencePaths() {
            experience(this.startingPoint);
        }

        public void setShortestPath(List<Path> pathList) {
            this.shortestPath.subList(0, this.shortestPath.size()).clear();
            for(Path path: pathList) {
                this.shortestPath.add(path);
            }
        }

        public void experience(CampusNode point) {
            List<Path> availablePaths = getAvailablePaths(point);
            for(Path path: availablePaths) {
                if(move(path)) {
                    if(path.getEndingPoint() == this.targetPoint) {
                        if(totalDistance(this.shortestPath) == 0 || totalDistance(this.shortestPath) > totalDistance(this.currentPath)) {
                        	setShortestPath(this.currentPath);
                        }
                    } else {
                        experience(path.getEndingPoint());
                    }
                }
                this.currentPath.remove(path);
            }
        }

        public int totalDistance(List<Path> paths) {
            int totalDistance  = 0;
            for(Path path: paths) {
                totalDistance += path.getDistance();
            }
            return totalDistance;
        }

        private boolean checkIfPathIsAlreadyExperienced(Path path) {
            for(Path pathItem: this.currentPath) {
                if(pathItem.equals(path)) {
                    return true;
                }
            }
            return false;
        }
	}
}