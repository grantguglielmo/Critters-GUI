package assignment5;

public class Params {
	//public final static int world_width = 160;
	///public final static int world_height = 80;
	public  static int world_width = 10;
	public  static int world_height = 10;
	public  static int walk_energy_cost = 2;
	public  static int run_energy_cost = 5;
	public  static int rest_energy_cost = 1;
	public  static int min_reproduce_energy = 20;
	public  static int refresh_algae_count = (int)Math.max(1, world_width*world_height/1000);

	public static  int photosynthesis_energy_amount = 1;
	public static  int start_energy = 100;
	public static int look_energy_cost = 10;
}