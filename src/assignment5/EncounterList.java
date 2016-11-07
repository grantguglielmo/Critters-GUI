/* CRITTERS GUI EncounterList.java
 * EE422C Project 5 submission by
 * Grant Guglielmo
 * gg25488
 * 16470
 * Mohit Joshi
 * msj696
 * 16475
 * Slip days used: 0
 * Fall 2016
 */
package assignment5;

import java.util.ArrayList;
/**
 * 
 *array of arrays, where each subarray is a list of critters
 *that landed on the same spot
 */
public class EncounterList {
	public ArrayList<ArrayList<Critter>> encounters;
	public static ArrayList<ArrayList<Critter>> cleanList;
	
	static{//clean list to easily erase old array
		cleanList = new ArrayList<ArrayList<Critter>>();
		for(int i = 0; i < Params.world_height*Params.world_width; i++){
			cleanList.add(null);
		}
	}
	/**
	 * default constructor sets all valuse of array to null
	 */
	public EncounterList(){//fill array with nulls
		encounters = new ArrayList<ArrayList<Critter>>();
		for(int i = 0; i < Params.world_height*Params.world_width; i++){
			encounters.add(null);
		}
	}
	/**
	 * adds critters that landed on same spot to array
	 * @param x xcoord of critters
	 * @param y ycoord of critters
	 * @param a critter a to be added
	 * @param b critter b to be added
	 */
	public void add(int x, int y, Critter a, Critter b){
		int index = x + y*Params.world_width;
		if(encounters.get(index) == null){//if no critters have collided here yet create a new array and add them both
			ArrayList<Critter> newCollision = new ArrayList<Critter>();
			newCollision.add(a);
			newCollision.add(b);
			encounters.set(index, newCollision);
		}
		else{//collision already has happened so add the bug that hasn't been added yet
			ArrayList<Critter> addCollision = encounters.get(index);
			addCollision.add(b);
			encounters.set(index, addCollision);
		}
	}
}