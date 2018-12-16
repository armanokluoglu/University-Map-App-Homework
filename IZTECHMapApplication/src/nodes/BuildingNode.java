package nodes;
import types.Category;
import types.CategoryType;

public class BuildingNode extends CampusNode{
	
	public BuildingNode(String name, int id, CategoryType typeOfCategory) {
		super(name, id);
		setTypeOfCategory(typeOfCategory);
		setCategory(Category.BUILDING);
	}
	
	@Override
	public void setTypeOfCategory(CategoryType typeOfCategory) {
		if(!isCategoryTypeCompatible(typeOfCategory)) {
			throw new IllegalArgumentException("Given type of category is not compatible with a building node.");
		}
		this.typeOfCategory = typeOfCategory;
	}

	private boolean isCategoryTypeCompatible(CategoryType typeOfCategory) {
		boolean isValid;
		switch(typeOfCategory) {
			case DEPARTMENT:
				isValid = true;
			
			case CAFETERIA:
				isValid = true;
				break;
				
			case ADMINISTRATIVE:
				isValid = true;
				break;
				
			case FACILITY:
				isValid = true;
				break;
				
			default:
				isValid = false;
		}
		return isValid;
	}
}