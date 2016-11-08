/* CRITTERS GUI Main.java
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
package assignment5; // cannot be in default package

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.lang.reflect.Method;

/*
 * Usage: java assignment4.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
@SuppressWarnings("unused")
public class Main extends Application {

	private static GraphicsContext gc;
	private static double scale;
	static Scanner kb; // scanner connected to keyboard input, or input file
	private static String inputFile; // input file, used instead of keyboard
										// input if specified
	private static int stepNum = 1;
	private static int CritterNum = 1;
	private static int animSpeed = 1;
	private static String CritterMake = "Algae";
	private static String CritterStat = "Algae";
	private Timeline timeline;
	private static ArrayList<String> crits = new ArrayList<String>();
	static ByteArrayOutputStream testOutputString; // if test specified, holds
													// all console output
	private static String myPackage; // package of Critter file. Critter cannot
										// be in default pkg.
	protected static boolean DEBUG = false; // Use it or not, as you wish!
	static PrintStream old = System.out; // if you want to restore output to
											// console

	// Gets the package name. The usage assumes that Critter and its subclasses
	// are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            args can be empty. If not empty, provide two parameters -- the first is a file name, and the second is test (for test output, where all output to be directed to a String), or nothing.
	 */
	public static void main(String[] args) {
		if (DEBUG) {
			if (args.length != 0) {
				try {
					inputFile = args[0];
					kb = new Scanner(new File(inputFile));
				} catch (FileNotFoundException e) {
					System.out.println("USAGE: java Main OR java Main <input file> <test output>");
					e.printStackTrace();
				} catch (NullPointerException e) {
					System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
				}
				if (args.length >= 2) {
					if (args[1].equals("test")) { // if the word "test" is the
													// second argument to java
						// Create a stream to hold the output
						testOutputString = new ByteArrayOutputStream();
						PrintStream ps = new PrintStream(testOutputString);
						// Save the old System.out.
						old = System.out;
						// Tell Java to use the special stream; all console
						// output will be redirected here from now
						System.setOut(ps);
					}
				}
			} else { // if no arguments to main
				kb = new Scanner(System.in); // use keyboard and console
			}

			/* Do not alter the code above for your submission. */
			/* Write your code below. */
			boolean cont = true;
			while (cont) {// continue readin inputs until user inputs quit
				System.out.print("critters>");// display command prompt
				String commandLine = kb.nextLine();
				String[] commands = commandLine.split(" ");// split command into
															// array
				int cycles = 1;
				switch (commands[0]) {// switch to find command
				case "quit":
					if (commands.length > 1) {// error if any arguments
						System.out.println("error processing: " + commandLine);
						break;
					}
					cont = false;// exit program
					break;
				case "show":
					if (commands.length > 1) {// error if any arguments
						System.out.println("error processing: " + commandLine);
						break;
					}
					Critter.displayWorld();// display critters in a grid
					break;
				case "step":
					if (commands.length > 2) {// error if 3 or more arguments
						System.out.println("error processing: " + commandLine);
						break;
					}
					if (commands.length > 1) {// check if any arguments for
												// number of steps, default to 1
						try {
							cycles = Integer.parseInt(commands[1]);
						} catch (Exception e) {// error if not integer
							System.out.println("error processing: " + commandLine);
							break;
						}
					}
					for (int i = 0; i < cycles; i++) {// loop through specified
														// number of time steps
						Critter.worldTimeStep();
					}
					break;
				case "seed":
					if (commands.length != 2) {// error if not 1 argument
						System.out.println("error processing: " + commandLine);
						break;
					}
					try {
						long seed = Long.parseLong(commands[1]);// seed random
																// number
																// generator
						Critter.setSeed(seed);
						break;
					} catch (Exception e) {// error if not integer
						System.out.println("error processing: " + commandLine);
						break;
					}
				case "make":
					if (commands.length > 3 || commands.length < 2) {// error if
																		// not
																		// 1-2
																		// arguments
						System.out.println("error processing: " + commandLine);
						break;
					}
					String className = commands[1];
					if (commands.length > 2) {
						try {// check if second argument is integer
							cycles = Integer.parseInt(commands[2]);
						} catch (Exception e) {
							System.out.println("error processing: " + commandLine);
							break;
						}
					}
					for (int i = 0; i < cycles; i++) {// make specified number
														// of critters of
														// specififed type
						try {
							Critter.makeCritter(className);
						} catch (InvalidCritterException e) {
							System.out.println("error processing: " + commandLine);
							break;
						}
					}
					break;
				case "stats":
					if (commands.length != 2) {// error if not 1 argument
						System.out.println("error processing: " + commandLine);
						break;
					}
					String statName = commands[1];
					try {// call static method of specified class
						List<Critter> statList = Critter.getInstances(statName);
						Class<?>[] types = { List.class };
						Class<?> testClass = Class.forName(myPackage + "." + statName);
						Method stat = testClass.getMethod("runStats", types);
						stat.invoke(null, statList);
					} catch (Exception e) {
						System.out.println("error processing: " + commandLine);
					}
					break;
				default:// not a valid command inputed
					System.out.println("invalid command: " + commandLine);
				}
			}

			/* Write your code above */
			System.out.flush();

		} else {
			launch(args);
		}
	}

	/**
	 * function that draws passed critter to critter world screen
	 * 
	 * @param c
	 *            critter to draw
	 * @param x
	 *            x coord of critter
	 * @param y
	 *            y coord of critter
	 */
	public static void write(Critter c, int x, int y) {
		switch (c.viewShape()) {// draw shape for critter based on viewshape
		case CIRCLE:// draw a circle
			gc.setFill(c.viewFillColor());// draw solid shape
			gc.fillOval(x * scale, y * scale, scale, scale);
			gc.setStroke(c.viewOutlineColor());// then draw outline
			gc.strokeOval(x * scale, y * scale, scale, scale);
			break;
		case SQUARE:
			gc.setFill(c.viewFillColor());
			gc.fillRect(x * scale, y * scale, scale, scale);
			gc.setStroke(c.viewOutlineColor());
			gc.strokeRect(x * scale, y * scale, scale, scale);
			break;
		case TRIANGLE:
			gc.setFill(c.viewFillColor());
			gc.fillPolygon(new double[] { x * scale + scale / 2, (x + 1) * scale, x * scale },
					new double[] { y * scale, (y + 1) * scale, (y + 1) * scale }, 3);
			gc.setStroke(c.viewOutlineColor());
			gc.strokePolygon(new double[] { x * scale + scale / 2, (x + 1) * scale, x * scale },
					new double[] { y * scale, (y + 1) * scale, (y + 1) * scale }, 3);
			break;
		case DIAMOND:
			gc.setFill(c.viewFillColor());
			gc.fillPolygon(new double[] { x * scale + scale / 2, (x + 1) * scale, x * scale + scale / 2, x * scale },
					new double[] { y * scale, y * scale + scale / 2, (y + 1) * scale, y * scale + scale / 2 }, 4);
			gc.setStroke(c.viewOutlineColor());
			gc.strokePolygon(new double[] { x * scale + scale / 2, (x + 1) * scale, x * scale + scale / 2, x * scale },
					new double[] { y * scale, y * scale + scale / 2, (y + 1) * scale, y * scale + scale / 2 }, 4);
			break;
		case STAR:
			gc.setStroke(c.viewOutlineColor());// draw outline first cause its not a star but just 2  triangles
			gc.strokePolygon(new double[] { x * scale + scale / 2, (x + 1) * scale, x * scale },
					new double[] { y * scale, (y + 1) * scale - scale / 4, (y + 1) * scale - scale / 4 }, 3);
			gc.strokePolygon(new double[] { x * scale + scale / 2, (x + 1) * scale, x * scale },
					new double[] { (y + 1) * scale, y * scale + scale / 4, y * scale + scale / 4 }, 3);
			gc.setFill(c.viewFillColor());
			gc.fillPolygon(new double[] { x * scale + scale / 2, (x + 1) * scale, x * scale },
					new double[] { y * scale, (y + 1) * scale - scale / 4, (y + 1) * scale - scale / 4 }, 3);
			gc.fillPolygon(new double[] { x * scale + scale / 2, (x + 1) * scale, x * scale },
					new double[] { (y + 1) * scale, y * scale + scale / 4, y * scale + scale / 4 }, 3);
			break;
		default:
			gc.setStroke(c.viewOutlineColor());
			gc.strokeOval(x * scale, y * scale, scale, scale);
			gc.setFill(c.viewFillColor());
			gc.fillOval(x * scale, y * scale, scale, scale);
			break;
		}
	}

	/**
	 * finds all critter classes in the same package also in the same src/ as Main.java
	 * 
	 * @param box
	 *            choice box to add critter class names to
	 */
	private static void findCritters(ComboBox<String> box) {
		if (crits.size() == 0) {
			File folder = new File("src/" + myPackage);// locate all files in  package folder
			File[] listOfFiles = folder.listFiles();
			ArrayList<String> files = new ArrayList<String>();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {// check if file is a file and truncate file extension
					String x = listOfFiles[i].getName();
					files.add(x.split("\\.")[0]);
				}
			}

			for (int i = 0; i < files.size(); i++) {
				try {
					Class<?> testClass = Class.forName(myPackage + "." + files.get(i));// sets testClass to critter_class_name
					Critter makeCritter = (Critter) testClass.newInstance();// check  if instatiable
					if (Class.forName(myPackage + ".Critter").isAssignableFrom(testClass)) {
						crits.add(files.get(i));
					}
				} catch (Exception e) {// catch any errors and throw invalid critter exception

				}
			}
		}
		for (String s : crits) {
			box.getItems().add(s);
		}
	}

	@Override
	/**
	 * main method for displaying the GUI Displays all 3 screens: control, critter world, stats screen handles all button and choice box on action events
	 * 
	 */
	public void start(Stage primaryStage) throws Exception {
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();// find bounds of screen
		double x_val = bounds.getWidth() / 4;
		double y_val = bounds.getHeight();
		double gridheight = ((bounds.getHeight() * 3) / 4);
		scale = gridheight / Params.world_height;// determine scaleing factor for screen based on  screen size and params
		double gridwidth = Params.world_width * scale;
		if (gridwidth > bounds.getWidth() * 3 / 4) {
			double scalex = (((bounds.getWidth() * 3) / 4 - 12) / Params.world_width);
			if (scalex < scale) {
				scale = scalex;
				gridwidth = Params.world_width * scale;
				gridheight = Params.world_height * scale;
			}
		}
		primaryStage.setTitle("Critter World");
		Group world = new Group();
		Canvas grid = new Canvas(gridwidth, gridheight);// create canvas and  group for critter  world stage
		GraphicsContext gcCanvas = grid.getGraphicsContext2D();
		primaryStage.setX(x_val + 5);// set stage location
		primaryStage.setY(0);
		gc = gcCanvas;
		world.getChildren().add(grid);
		Critter.displayWorld();
		Scene s = new Scene(world, gridwidth + 5, gridheight + 5, Color.WHITE);// set  size  of  scene based  on scaling
		primaryStage.setScene(s);
		primaryStage.show();// display critter world

		Stage statscreen = new Stage();// create stats screen stage
		statscreen.setTitle("Stats");
		statscreen.setX(x_val + 5);
		statscreen.setY(gridheight + 47);// position stage on screen
		TextArea textArea = new TextArea();// creat text area that will be  console output
		VBox vbox = new VBox(textArea);
		double textH = bounds.getHeight() - gridheight;
		if (textH > 227) {
			textH = 227;
		}
		Scene scenetext = new Scene(vbox, (bounds.getWidth() * 3) / 4, textH);// determine  size  of stats screen
		OutputStream out = new OutputStream() {// set outputstream to display on stats screen
			@Override
			public void write(int b) throws IOException {
				textArea.appendText(String.valueOf((char) b));
			}
		};
		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
		statscreen.setScene(scenetext);// display stats screen
		statscreen.show();

		GridPane root = new GridPane();// create stage for controller of GUI
		Stage control = new Stage();
		control.setTitle("Controller");
		Scene control_scene = new Scene(root, x_val, y_val, Color.WHITE);// set size of controller
		control.setScene(control_scene);
		control.setX(0);// set position of controller
		control.setY(0);
		ColumnConstraints column = new ColumnConstraints(x_val / 3);// create rows and colmuns for placements of buttons
		root.getColumnConstraints().add(column);
		root.getColumnConstraints().add(column);
		RowConstraints row = new RowConstraints(y_val / 9);
		row.setValignment(VPos.TOP);
		RowConstraints minirow = new RowConstraints(20);
		minirow.setValignment(VPos.TOP);
		root.getRowConstraints().add(row);
		root.getRowConstraints().add(minirow);
		root.getRowConstraints().add(row);
		root.getRowConstraints().add(minirow);
		root.getRowConstraints().add(row);
		root.getRowConstraints().add(minirow);
		root.getRowConstraints().add(row);
		root.getRowConstraints().add(minirow);
		root.getRowConstraints().add(row);
		root.getRowConstraints().add(row);
		Label lbl = new Label();
		lbl.setText("Run stats for:");
		Label lbl2 = new Label();
		lbl2.setText("Speed:");
		Label lbl3 = new Label();
		lbl3.setText("# of steps:");
		Label lbl4 = new Label();
		lbl4.setText("Critter to make:");
		Label lbl5 = new Label();
		lbl5.setText("# to make:");
		Button btn0 = new Button();// create all the buttons in GUI
		btn0.setText("make");
		Button btn1 = new Button();
		btn1.setText("step");
		Button btn2 = new Button();
		btn2.setText("stats");
		Button btn7 = new Button();
		btn7.setText("clear");
		Button btn3 = new Button();
		btn3.setText("quit");
		Button btn4 = new Button();
		btn4.setText("run");
		Button btn5 = new Button();
		btn5.setText("stop");
		TextField seed = new TextField();// text feild for seed input
		seed.setPromptText("Set Random Number");
		Button btn6 = new Button();
		btn6.setText("seed");
		ComboBox<String> box0 = new ComboBox<String>();// create all the choice boxes
		findCritters(box0);
		box0.setEditable(true);
		ComboBox<String> box1 = new ComboBox<String>();
		box1.getItems().addAll("1", "10", "42", "100", "1000");// fill with correct data
		box1.setEditable(true);
		ComboBox<String> box = new ComboBox<String>();
		box.getItems().addAll("1", "10", "42", "100", "1000");
		box.setEditable(true);// allow users to add their own choices to the boxes
		ComboBox<String> box2 = new ComboBox<String>();
		findCritters(box2);
		box2.setEditable(true);
		ComboBox<String> box3 = new ComboBox<String>();
		box3.getItems().addAll("x1", "x2", "x5", "x10", "x20", "x50", "x100");
		root.add(btn0, 2, 2);// add buttons to the gridpane
		root.add(lbl4, 0, 1);
		root.add(lbl5, 1, 1);
		root.add(btn1, 1, 4);
		root.add(lbl3, 0, 3);
		root.add(box, 0, 4);
		root.add(box0, 0, 2);
		root.add(box1, 1, 2);
		root.add(box2, 0, 6);
		root.add(lbl, 0, 5);
		root.add(seed, 0, 9);
		root.add(btn6, 1, 9);
		root.add(btn7, 0, 10);
		root.add(btn3, 1, 10);
		root.add(btn4, 1, 8);
		root.add(btn5, 2, 8);
		root.add(lbl2, 0, 7);
		root.add(box3, 0, 8);
		box.setValue("1");// set starting values of the boxes
		box0.setValue("Algae");
		box1.setValue("1");
		box3.setValue("x1");
		box2.setValue("Algae");
		btn5.setDisable(true);
		timeline = new Timeline();// create timeline for the animation
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		@SuppressWarnings("rawtypes")
		EventHandler onFinished = new EventHandler<ActionEvent>() {// what happens each animation frame
			public void handle(ActionEvent t) {
				for (int i = 0; i < animSpeed; i++) {// execute worldtiemstep based on animation speed
					Critter.worldTimeStep();
				}
				gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());// clear screen
				Critter.displayWorld();// display critter world
				primaryStage.show();
				try {
					textArea.clear();// display stats after each update to critter world
					List<Critter> statList = Critter.getInstances(CritterStat);
					Class<?>[] types = { List.class };
					Class<?> testClass = Class.forName(myPackage + "." + CritterStat);
					Method stat = testClass.getMethod("runStats", types);
					stat.invoke(null, statList);
				} catch (Exception e) {

				}
			}
		};
		Duration duration = Duration.millis(300);// set time between animation frames
		@SuppressWarnings("unchecked")
		KeyFrame keyFrame = new KeyFrame(duration, onFinished);// intialize timeline
		timeline.getKeyFrames().add(keyFrame);
		btn0.setOnAction(new EventHandler<ActionEvent>() {// make button

			@Override
			public void handle(ActionEvent event) {
				try {
					for (int i = 0; i < CritterNum; i++) {// create specified number of critters
						Critter.makeCritter(CritterMake);
					}
				} catch (Exception e) {

				}
				gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
				Critter.displayWorld();
				primaryStage.show();
				btn2.fire();
			}
		});
		btn7.setOnAction(new EventHandler<ActionEvent>() {// clear button

			@Override
			public void handle(ActionEvent event) {
				Critter.clearWorld();// clear critter world and update all stages
				gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
				Critter.displayWorld();
				primaryStage.show();
				btn2.fire();
			}
		});
		btn6.setOnAction(new EventHandler<ActionEvent>() {// seed button

			@Override
			public void handle(ActionEvent event) {
				try {
					long seedNum = Integer.parseInt(seed.getText());// set random number seed
					Critter.setSeed(seedNum);
				} catch (Exception e) {

				}

			}
		});
		btn5.setOnAction(new EventHandler<ActionEvent>() {// stop button

			@Override
			public void handle(ActionEvent event) {
				btn0.setDisable(false);// enable all other buttons and boxes
				btn1.setDisable(false);// end animation
				btn2.setDisable(false);// disable this button
				btn3.setDisable(false);
				box.setDisable(false);
				box0.setDisable(false);
				seed.setDisable(false);
				btn6.setDisable(false);
				btn7.setDisable(false);
				box1.setDisable(false);
				box2.setDisable(false);
				box3.setDisable(false);
				btn4.setDisable(false);
				btn5.setDisable(true);
				timeline.stop();
			}
		});
		btn4.setOnAction(new EventHandler<ActionEvent>() {// run button

			@Override
			public void handle(ActionEvent event) {
				btn0.setDisable(true);// disable all boxes and butons besides stop button
				btn1.setDisable(true);// start animation
				btn2.setDisable(true);
				btn3.setDisable(true);
				box.setDisable(true);
				box0.setDisable(true);
				box1.setDisable(true);
				box2.setDisable(true);
				seed.setDisable(true);
				btn6.setDisable(true);
				btn7.setDisable(true);
				btn5.setDisable(false);
				box3.setDisable(true);
				btn4.setDisable(true);
				timeline.play();
			}
		});
		btn3.setOnAction(new EventHandler<ActionEvent>() {// quit button

			@Override
			public void handle(ActionEvent event) {// end program
				System.exit(1);
			}
		});
		btn2.setOnAction(new EventHandler<ActionEvent>() {// stats button

			@Override
			public void handle(ActionEvent event) {
				try {
					textArea.clear();// clear stats screen
					List<Critter> statList = Critter.getInstances(CritterStat);// runstats based on specified critter
					Class<?>[] types = { List.class };
					Class<?> testClass = Class.forName(myPackage + "." + CritterStat);
					Method stat = testClass.getMethod("runStats", types);
					stat.invoke(null, statList);
				} catch (Exception e) {

				}
			}
		});
		box0.setOnAction(new EventHandler<ActionEvent>() {// critter select for make

			@Override
			public void handle(ActionEvent event) {// store selected critter class name
				CritterMake = box0.getValue();

			}
		});
		box2.setOnAction(new EventHandler<ActionEvent>() {// critter select for stats

			@Override
			public void handle(ActionEvent event) {
				CritterStat = box2.getValue();// store selected critter class name
				btn2.fire();// update stats screen
			}
		});
		box3.setOnAction(new EventHandler<ActionEvent>() {// animation speed select

			@Override
			public void handle(ActionEvent event) {
				try {
					String x = box3.getValue();// store animation speed int
					x = x.substring(1);
					animSpeed = Integer.parseInt(x);
				} catch (Exception e) {
					animSpeed = 1;
				}

			}
		});
		box1.setOnAction(new EventHandler<ActionEvent>() {// number of critters to make select

			@Override
			public void handle(ActionEvent event) {
				try {
					CritterNum = Integer.parseInt(box1.getValue());// store selected number of critters to make int
				} catch (Exception e) {
					CritterNum = 1;// if value not an int set to 1
				}

			}
		});
		box.setOnAction(new EventHandler<ActionEvent>() {// number of steps to execute select

			@Override
			public void handle(ActionEvent event) {
				try {
					stepNum = Integer.parseInt(box.getValue());// store number of steps to  do int
				} catch (Exception e) {
					stepNum = 1;// if value inputted not int set to 1
				}

			}
		});
		btn1.setOnAction(new EventHandler<ActionEvent>() {// step button

			@Override
			public void handle(ActionEvent event) {
				for (int i = 0; i < stepNum; i++) {
					Critter.worldTimeStep();// run world timestep for specified number of steps
				}
				gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());// clear critter world screen
				Critter.displayWorld();// display critters on screen
				primaryStage.show();
				btn2.fire();// update stats
			}
		});
		control.show();
		btn2.fire();
	}
}