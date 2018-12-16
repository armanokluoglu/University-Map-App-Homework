package types;

import nodes.CampusNode;

public class Path {
	private CampusNode startingPoint, endingPoint;
	private int distance;
	
    public CampusNode getEndingPoint() {
        return endingPoint;
    }

    public CampusNode getStartingPoint() {
        return startingPoint;
    }

    public int getDistance() {
        return distance;
    }

    public Path(CampusNode startingPoint, CampusNode endingPoint, int distance) {
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.distance = distance;
    }

    Path(Path Path) {
        this.startingPoint = Path.startingPoint;
        this.endingPoint = Path.endingPoint;
        this.distance = Path.distance;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Path other = (Path) obj;
		if(other.equals(this)) {
			return true;
		}
		return false;
	}

	public boolean equals(Path path) {
        if((getStartingPoint().equals(path.getStartingPoint())) && (getEndingPoint().equals(path.getEndingPoint())) && (getDistance() == path.getDistance())) {
            return true;
        } else if((getStartingPoint().equals(path.getEndingPoint())) && (getEndingPoint().equals(path.getStartingPoint())) && (getDistance() == path.getDistance())){
        	return true;
        } else {
        	return false;
        }
    }
    
    @Override
    public String toString() {
    	String str = getStartingPoint().getName() + " ---(" + getDistance() + " Units)---> " + getEndingPoint().getName();
    	return str;
    }
}