/* CRITTERS GUI Critter4.java
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

/**
 * This critter alternates its movement style every time step. If the critter moved diagonally
 * during the last time step, it will move along the grid during the current time step. Alternately,
 * the critter will move diagonally if it moved along the grid last time step.
 * 
 * The critter will opt to stay and fight any other critter it encounters, with the exception of
 * Critter 1. Since Critter 1 always opts to fight, Critter 4 will decide to run instead.
 */
public class Critter4 extends Critter {
	
	private boolean diagonal;
	
	@Override
	public String toString(){
		return "4";
	}
	
	public Critter4() {
		diagonal = true;
	}
	
	@Override
	public void doTimeStep() {
		int temp = Critter.getRandomInt(8);
		if(temp%2 == 1) {
			if(diagonal) {				//check if critter moved diagonally last time step
				walk((temp+1)&8);
				
			}
			else {
				walk(temp);
			}
		}
		else {
			if(diagonal) {
				walk(temp);
			}
			else {
				walk((temp+1)&8);
			}
		}
		diagonal = !diagonal;			//toggle flag to alternate every time step
	}
	
	@Override
	public boolean fight(String oponent) {
		if(oponent.equals("1")) {
			run(Critter.getRandomInt(8));	//run away if fighting critter 1
			return false;
		}
		else
			return true;					//otherwise, stay and fight
	}

	@Override
	public CritterShape viewShape() { return CritterShape.TRIANGLE; }
	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.CYAN; }
}
