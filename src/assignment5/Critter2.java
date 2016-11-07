/* CRITTERS GUI Critter2.java
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
 * Critter2 will randomly decide to either walk run or rest during a given doTimestep.
 * Critter2's preference on running or resting is decided by a random value picked at construction.
 * Its direction is randomly chosen during when it decides to walk or run.
 * During a fight Critter2 will begin reproducing until its energy drops
 * below the min_reproduce_energy parameter. Its children are sent off in random directions
 * and are given their parents runChance value. The Critter2 will then stay and fight
 * once it has exhausted its reproduction potential.
 */
public class Critter2 extends Critter{
	
	private int runChance;
	/**
	 * return 2
	 */
	public String toString(){
		return "2";
	}
	/**
	 * default constructor, place random value 17-42 in runChance
	 */
	public Critter2(){
		do{
		runChance = Critter.getRandomInt(41);//set runChance randomly between 17-41
		}while(runChance < 17);
	}
	/**
	 * randomly decide whether to walk run or rest based of runChance
	 */
	@Override
	public void doTimeStep() {
		int deciscion = Critter.getRandomInt(42);
		if(deciscion < 17){
			walk(Critter.getRandomInt(8));//critter will walk
		}
		else if(deciscion < runChance){//critter will run
			run(Critter.getRandomInt(8));
		}
		else{//critter will rest
			return;
		}
	}
	/**
	 * continually reproduce until out of energy then stay and fight.
	 * will not reproduce if algae, but just eat it instead.
	 */
	@Override
	public boolean fight(String oponent) {
		if(oponent.equals("@")){//fight immediately if algae
			return true;
		}
		String chk = this.look(Critter.getRandomInt(8), false);
		if(chk != null && chk.equals("2")){
			return true;
		}
		while(getEnergy() > Params.min_reproduce_energy){//critter just spams area with babies then kills itself
			Critter2 child = new Critter2();//continue reproducing untill out of energy
			child.runChance = this.runChance;
			this.reproduce(child, Critter.getRandomInt(8));
		}
		return true;//stay and fight
	}
	@Override
	public CritterShape viewShape() { return CritterShape.STAR; }
	@Override
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.ORANGE; }
	
}
