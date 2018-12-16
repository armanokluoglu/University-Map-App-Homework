package nodes;
import java.util.ArrayList;
import java.util.List;

import types.Campus;
import types.Category;
import types.CategoryType;
import utilities.DistanceCalculator;

public abstract class CampusNode implements Comparable<CampusNode>{

	private Category category;
	private String name;
	private int id;
	protected CategoryType typeOfCategory;
	private List<CampusNode> neighbors;
	
	public CampusNode(String name, int id) {
		setId(id);
		setName(name);
		setNeighbors(new ArrayList<>());
	}
	
	public Category getCategory() {return category;}
	
	public String getName() {return name;}

	public int getId() {return id;}

	public CategoryType getTypeOfCategory() {return typeOfCategory;}

	public List<CampusNode> getNeighbors() {
		return neighbors;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

	public void setName(String name) {
		 if(name == "" || name == null) {
			 throw new IllegalArgumentException("Given name can neither be empty nor null.");
		 }
		this.name = name;
	}

	private void setId(int id) {
		if(id <= 0) {
			 throw new IllegalArgumentException("Given id cannot be less than or equal to zero.");
		}
		this.id = id;
	}

	
	public abstract void setTypeOfCategory(CategoryType typeOfCategory);

	@Override
	public int compareTo(CampusNode node) {
		return getName().compareTo(node.getName());
	}
	
	public int distanceBetween(CampusNode anotherNode, Campus campus) {
		DistanceCalculator distanceCalculator = new DistanceCalculator(campus);
		return distanceCalculator.calculateShortestPath(this, anotherNode);
	}
	
	public void setNeighbors(List<CampusNode> neighbors) {
		if(neighbors == null) {
			throw new IllegalArgumentException("Given list of neighbors is null.");
		}
		this.neighbors = neighbors;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CampusNode other = (CampusNode) obj;
		if(other.equals(this)) {
			return true;
		}
		return false;
	}
	
	public boolean equals(CampusNode node) {
		if((getId() == node.getId()) && (getName() == node.getName()) && (getCategory() == node.getCategory()) && (getTypeOfCategory() == node.getTypeOfCategory())) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String str = getId() + " [" + getCategory() + ", " + getTypeOfCategory() + ", " + getName() + "]";
		return str;
	}
}