package utilities;

public enum Formula {

	//Default value is taken 6.
	DepToCafFormula((6*2) -3),
	AdToDepFormula(6 / 2),
	CafToWatFormula(6 / 3),
	FacToWatFormula((6*5)/2),
	HisToCafFormula(6*6),
	DepToBeachFormula(((6*6)/2)+4),
	CafToFacFormula((int)(Math.abs(Math.sqrt(6))));

	private int distance;
	
	private Formula(int distance) {
		this.distance = distance;
	}
	
	public int getDistance() {return distance;}
}
