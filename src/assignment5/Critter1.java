/* CRITTERS GUI Critter1.java
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
 * Critter1 runs in a random direction every doTimestep, where the directions is picked form an array 
 * of size 3 with 3 random directions stored inside. This array is initialized with random values in its default constructor.
 * Critter1 will decide to reproduce if it has sufficient energy(starting energy + 5*reproduce energy).
 * Critter1's babies inheirt some of their parents random direction 'genes'.
 * Critter1 will always stay and fight another critter.
 */

public class Critter1 extends Critter{
	
	private int[] dir;
	/**
	 * return "1"
	 */
	public String toString(){
		return "1";
	}
	/**
	 * no arg constructor, place random values 0-7 in int[3] dir array
	 */
	public Critter1(){
		dir = new int[3];
		dir[0] = Critter.getRandomInt(8);
		int x = Critter.getRandomInt(8);
		while(x == dir[0]){
			x = Critter.getRandomInt(8);
		}
		dir[1] = x;
		x = Critter.getRandomInt(8);
		while(x == dir[0] || x == dir[1]){
			x = Critter.getRandomInt(8);
		}
		dir[2] = x;
	}
	/**
	 * run in a random direction selected from dir[]
	 * reproduce if energy is >= 5*Params.min_reproduce_energy + Params.start_energy
	 */
	@Override
	public void doTimeStep() {
		run(dir[Critter.getRandomInt(3)]);
		if(this.getEnergy() >= 5*Params.min_reproduce_energy + Params.start_energy){//check if enough energy
			Critter1 child = new Critter1();
			for(int i = 0; i < 3; i++){//child inheirts some of parents genes
				int x = Critter.getRandomInt(3);
				child.dir[x] = this.dir[x];
			}
			this.reproduce(child, child.dir[0]);
		}
	}
	/**
	 * always stay and fight
	 */
	@Override
	public boolean fight(String oponent) {
		return true;//never back down
	}
	@Override
	public CritterShape viewShape() { return CritterShape.TRIANGLE; }
	@Override
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.PURPLE; }
	
}
