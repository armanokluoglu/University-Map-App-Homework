package app;

import console.IztechMap;

public class IzmapApp {

	public static void main(String[] args) {
		IztechMap izmap = new IztechMap("src/iztech.izmap");
		izmap.takeQuery();
	}
}