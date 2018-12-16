package nodes;
import types.Category;
import types.CategoryType;

public class LandscapeNode extends CampusNode{
	
	public LandscapeNode(String name, int id, CategoryType typeOfCategory) {
		super(name, id);
		setTypeOfCategory(typeOfCategory);	
		setCategory(Category.LANDSCAPE);
	}
	
	@Override
	public void setTypeOfCategory(CategoryType typeOfCategory) {
		if(!isCategoryTypeCompatible(typeOfCategory)) {
			throw new IllegalArgumentException("Given type of category is not compatible with a landscape node.");
		}
		this.typeOfCategory = typeOfCategory;
	}

	private boolean isCategoryTypeCompatible(CategoryType typeOfCategory) {
		boolean isValid;
		switch(typeOfCategory) {
			case WATERFALL:
				isValid = true;
			
			case BEACH:
				isValid = true;
				break;
				
			case HISTORICALRUIN:
				isValid = true;
				break;
				
			default:
				isValid = false;
		}
		return isValid;
	}
}