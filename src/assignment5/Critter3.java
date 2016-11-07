/* CRITTERS GUI Critter3.java
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
 * This critter begins by randomly choosing a direction out of the possible eight. 
 * If the direction it chooses will not result in backtracking, then it will walk in that
 * direction. If the direction it chooses is the opposite of the last direction it walked,
 * it will instead reproduce, with the offspring's direction set as the last direction walked.
 * 
 * If the critter reproduced during the current time step, it will fight to protect it's 
 * offspring. Otherwise, it will choose not to fight.
 */
public class Critter3 extends Critter {
	
	private int prev;
	private boolean babies;
	
	@Override
	public String toString(){
		return "3";
	}
	
	public Critter3() {
		prev = -1;
		babies = false;
	}
	
	@Override
	public void doTimeStep() {
		int temp = Critter.getRandomInt(8);
		if(temp == prev)			//check if movement will result in backtrack
		{
			Critter3 child = new Critter3();
			this.reproduce(child, temp);
			babies = true;
			prev = -1;
		}
		else
		{	
			String lookNext = this.look(temp, false);
			if(lookNext == null)
				walk(temp);
			else
				walk(Critter.getRandomInt(8));
			babies = false;
			prev = (temp+4)%8;		//set prev to opposite of walking direction
		}
	}
	
	@Override
	public boolean fight(String oponent) {
		if(!babies)
			run(Critter.getRandomInt(8));
		return babies;				//fight if critter reproduced during this time step
	}

	@Override
	public CritterShape viewShape() { return CritterShape.DIAMOND; }
	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.RED; }
	@Override
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.GREEN; }
}