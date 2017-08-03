package com.joan.TiendaPanes.modelo;

import java.util.ArrayList;

public class Stock {
	private ArrayList<Panes> panes;

	public Stock(ArrayList<Panes> panes) {
		super();
		this.panes = panes;
	}

	public ArrayList<Panes> getPanes() {
		return panes;
	}

	public void setPanes(ArrayList<Panes> panes) {
		this.panes = panes;
	}
	
	
}
