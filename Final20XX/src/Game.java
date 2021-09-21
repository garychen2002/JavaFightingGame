//Author: Gary Chen
//Teacher: Mr. Radulovic
//Don Mills CI - ICS Grade 12 Culminating
//May 21 2019 - June 15 2019
//This program is a fighting game.

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.util.Random;

/**
 * @author Gary Chen
 *
 */
public class Game extends Application 
{

	public final static double WINDOW_WIDTH = 800; //storing sizes in variables
	public final static double WINDOW_HEIGHT = 600;
	public final static double WINDOW_WIDTH_SCORES = 200; //storing sizes in variables
	public final static double WINDOW_HEIGHT_SCORES = 400;

	/**
	 * List of character classes: used for switching character
	 */
	private final Class[] characterList = {Duck.class, Crab.class};
	/**
	 * Parameters needed to create a Character (pos) - used for switching character
	 */
	private final Class[] parameters = new Class[] {Point2D.class};
	/**
	 * p1's character index (in characterList)
	 */
	private int p1CharIndex;
	/**
	 * p2's character index (in characterList)
	 */
	private int p2CharIndex;

	/**
	 * Group that contains all
	 */
	private Group root; //Main group 
	/**
	 * VBox that contains the highscore label. Used in a different scene.
	 */
	private VBox highscores;
	/**
	 * Group for the fight related stuff
	 */
	private Group fight;
	/**
	 * Contains imageViews for characters.
	 */
	private Group characters;
	/**
	 * Contains imageViews for attacks.
	 */
	private Group attacks; //Contains imageviews
	/**
	 * Contains imageViews for hiteffects.
	 */
	private Group hitEffectsImages;

	/**
	 * Labels related to the fight.
	 */
	private VBox fightLabels;
	/**
	 * Labels to display character health.
	 */
	private HBox healthLabels;

	/**
	 * GUI related to the fight
	 */
	private VBox fightGUI;
	/**
	 * Stage that high scores are displayed on (separate)
	 */
	private Stage highScoreStage;

	/**
	 * Player 1
	 */
	private Character p1;
	/**
	 * Player 2
	 */
	private Character p2;
	private ArrayList<Character> players;
	private ImageView p1Image;
	private ImageView p2Image;
	
	private ArrayList<Hitbox> hitboxes; //Contains actual hitbox classes
	private ArrayList<HitEffect> hitEffects;


	/**
	 * Each row contains an array with 2 values: the Date and the Score.
	 */
	private String[][] savedHighScores; //1 row contains 1 array with 2 size (date, score)
	/**
	 * Maximum number of scores that will be saved
	 */
	private final static int maxScores = 10; //Maximum scores to be saved. 

	/**
	 * Current score for the current game
	 */
	private int currentScore;
	/**
	 * Paused or not?
	 */
	private boolean paused;
	/**
	 * Has the game ended (character dead?)
	 */
	private boolean playing;
	/**
	 * Time to wait for update (1/60th of a second, 60 frames)
	 */
	private final double waitTime = 100000000 / 6.0; // wait 1/60 second to update. Reused
	/**
	 * Current time in nanoseconds
	 */
	private double currentSeconds; //current time
	/**
	 * Time that has passed since game started
	 */
	private long timePassed;
	/** 
	 * Time when game started
	 */
	private long timeStarted;

	/**
	 * Default pos for p1 to start (1/4th of screen)
	 */
	private final Point2D defaultPositionP1 = new Point2D(WINDOW_WIDTH/4,WINDOW_HEIGHT);
	/**
	 * Default pos for p2 to start (3/4th of screen)
	 */
	private final Point2D defaultPositionP2 = new Point2D(WINDOW_WIDTH/4*3,WINDOW_HEIGHT);

	/**
	 * Label displaying p1's health/max health
	 */
	private Label p1Health;
	/**
	 * Label displaying p2's health/max health
	 */
	private Label p2Health;
	/**
	 * Label displaying the high scores
	 */
	private Label highScoresLabel;
	/**
	 * Label displaying the winner of the game
	 */
	private Label winner;


	private KeyCode p1LeftKey;
	private KeyCode p1RightKey;
	private KeyCode p2LeftKey;
	private KeyCode p2RightKey;
	private KeyCode p1UpKey;
	private KeyCode p2UpKey;
	private KeyCode p1DownKey;
	private KeyCode p2DownKey;

	private KeyCode p1AttackKey;
	private KeyCode p2AttackKey;
	private KeyCode p1SwitchKey;
	private KeyCode p2SwitchKey;

	public static void main(String[] args) 
	{
		launch(args);
	}

	public Game()
	{
		//Initialization
		p1LeftKey = KeyCode.LEFT;  //Get these from a config file and be able to edit?
		p1RightKey = KeyCode.RIGHT;
		p2LeftKey = KeyCode.A;
		p2RightKey = KeyCode.D;
		p1UpKey = KeyCode.UP;
		p2UpKey = KeyCode.W;
		p1DownKey = KeyCode.DOWN;
		p2DownKey = KeyCode.S;
		p1AttackKey = KeyCode.P;
		p2AttackKey = KeyCode.N;
		p1SwitchKey = KeyCode.DIGIT1;
		p2SwitchKey = KeyCode.DIGIT2;
		p1CharIndex = 0; //DUCK
		p2CharIndex = 1; //CRAB

		players = new ArrayList<Character>();
		hitboxes = new ArrayList<Hitbox>();
		hitEffects = new ArrayList<HitEffect>();

		paused = false;
		playing = true; 
		timePassed = 0;
		timeStarted = System.nanoTime();

	}

	public void start (Stage primaryStage) throws Exception {

		//Initialization
		root = new Group();
		fight = new Group();
		characters = new Group();
		attacks = new Group();
		hitEffectsImages = new Group();
		highscores = new VBox();
		fightLabels = new VBox();
		fightGUI = new VBox();
		healthLabels = new HBox();
		healthLabels.setSpacing(300);

		//select characters by default
		p1 = new Duck(defaultPositionP1);
		p1.turn(); //face forward
		p2 = new Crab(defaultPositionP2);
		p1.setGame(this);
		p1Image = p1.getImageView();
		p2.setGame(this);
		p2Image = p2.getImageView();
		characters.getChildren().addAll(p1Image,p2Image);
		players.add(p1);
		players.add(p2);

		//GUI/labels
		p1Health = new Label("D");
		p1Health.setMinWidth(100);
		p1Health.setMaxWidth(100);

		p2Health = new Label("E");
		p2Health.setMinWidth(100);

		winner = new Label();
		winner.setVisible(false);
		healthLabels.getChildren().addAll(p1Health,p2Health);
		fightLabels.getChildren().addAll(healthLabels,winner);

		highScoresLabel = new Label();
		highscores.getChildren().addAll(highScoresLabel);

		Button pause = new Button("PAUSE");
		Button restart = new Button("RESTART");
		Button showScores = new Button("SCORES");
		Button clearScores = new Button("CLEAR");


		fightGUI.getChildren().addAll(fightLabels,pause,restart,showScores);


		fight.getChildren().addAll(characters,attacks,fightGUI, hitEffectsImages);

		root.getChildren().addAll(fight);


		//Highscore gang
		savedHighScores = new String[maxScores][];
		File HighScoreFile = new File("resources/highscores.txt"); //get highscores from file
		RandomAccessFile makeFile = new RandomAccessFile("resources/highscores.txt", "rw"); //make file
		//Automatically creates file if doesn't exist.
		makeFile.close();
		Scanner scan = new Scanner(HighScoreFile);
		int i = 0;
		while (scan.hasNextLine() && i < maxScores) //Go through all, don't overflow.
		{
			String row = scan.nextLine(); //get the highscores from file
			String[] split = row.split("\\|"); //| is a special regex character. Need to escape
			savedHighScores[i] = split;
			i++;
		}
		String[] placeholder = {"20XX-04-12","0"};
		while (i < maxScores) //continue if not all lines used
		{
			savedHighScores[i] = placeholder.clone();
			i++;
		}
		updateHighScoreLabel();
		scan.close();




		//Scene
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);//setting the scene
		//Handle key inputs.
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == p1RightKey)
				{//Right is pressed
					System.out.println("P1RIGHT PUSH");
					p1.setHoldingRight(true);
				}

				else if(event.getCode() == p1LeftKey)
				{//Left is pressed
					System.out.println("P1LEFT PUSH");
					p1.setHoldingLeft(true);

				}

				else if(event.getCode() == p1UpKey)
				{
					if (p1.isHoldingUp() == false)
					{
						p1.jump(); 
						p1.setHoldingUp(true);

					}
				}

				else if(event.getCode() == p1DownKey)
				{
					System.out.println("P1DOWN PUSH");
					p1.setHoldingDown(true);
				}


				else if(event.getCode() == p2LeftKey)
				{//Left is pressed
					System.out.println("P2LEFT PUSH");
					p2.setHoldingLeft(true);

				}

				else if(event.getCode() == p2RightKey)
				{
					System.out.println("P2RIGHT PUSH");
					p2.setHoldingRight(true);
				}

				else if(event.getCode() == p2DownKey)
				{
					System.out.println("P2DOWN PUSH");
					p2.setHoldingDown(true);
				}

				else if(event.getCode() == p2UpKey)
				{
					System.out.println("P2UP PUSH");
					if (p2.isHoldingUp() == false)
					{
						p2.jump(); 
						p2.setHoldingUp(true);

					}
				}

				else if(event.getCode() == p1AttackKey)
				{
					if (!p1.isHoldingAttack())
					{
						System.out.println("P1ATTACK");
						if (p1.isHoldingLeft() || p1.isHoldingRight())
							p1.sideAttack();
						else if (p1.isHoldingUp())
							p1.upAttack();
						else if (p1.isHoldingDown())
							p1.downAttack();
						else
							p1.neutralAttack();
						p1.setHoldingAttack(true);
					}

				}

				else if(event.getCode() == p2AttackKey)
				{
					if (!p2.isHoldingAttack())
					{
						System.out.println("P2ATTACK");
						if (p2.isHoldingLeft() || p2.isHoldingRight())
							p2.sideAttack();
						else if (p2.isHoldingUp())
							p2.upAttack();
						else if (p2.isHoldingDown())
							p2.downAttack();
						else
							p2.neutralAttack();
						p2.setHoldingAttack(true);
					}

				}

				else if (event.getCode() == p1SwitchKey) //1
				{
					p1CharIndex++; //cycle through character list...
					p1CharIndex = p1CharIndex % characterList.length;
					restart();
				}
				else if (event.getCode() == p2SwitchKey) //2
				{
					p2CharIndex++;
					p2CharIndex = p2CharIndex % characterList.length;
					restart();
				}
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == p1RightKey)
				{
					System.out.println("P1RIGHT RELEASE");
					p1.setHoldingRight(false);

				}
				else if(event.getCode() == p1LeftKey)
				{
					System.out.println("P1LEFT RELEASE");
					p1.setHoldingLeft(false);
				}


				else if(event.getCode() == p2LeftKey)
				{
					System.out.println("P2LEFT RELEASE");
					p2.setHoldingLeft(false);
				}

				else if(event.getCode() == p2RightKey)
				{
					System.out.println("P2RIGHT RELEASE");
					p2.setHoldingRight(false);
				}

				else if(event.getCode() == p1UpKey)
				{
					System.out.println("1UP key released.");
					p1.fall(); //stop going up
					p1.setHoldingUp(false);
				}

				else if(event.getCode() == p2UpKey)
				{
					System.out.println("2UP key released.");
					p2.fall(); //stop going up
					p2.setHoldingUp(false);
				}

				else if(event.getCode() == p1DownKey)
				{
					System.out.println("P1DOWN RELEASE");
					p1.setHoldingDown(false);
				}


				else if(event.getCode() == p2DownKey)
				{
					System.out.println("P2DOWN RELEASE");
					p2.setHoldingDown(false);
				}

				else if(event.getCode() == p1AttackKey)
				{
					System.out.println("P1ATK RELEASE");
					p1.setHoldingAttack(false);
				}


				else if(event.getCode() == p2AttackKey)
				{
					System.out.println("P2ATK RELEASE");
					p2.setHoldingAttack(false);
				}


			}
		});

		pause.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent arg0) {

				paused = !paused;

			}
		});

		restart.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent arg0) {
				restart();

			}
		});

		showScores.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent arg0) {
				highScoreStage.show();

			}
		});

		clearScores.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent arg0) {
				try {
					RandomAccessFile makeFile = new RandomAccessFile("resources/highscores.txt", "rw");
					makeFile.setLength(0);
					String[] placeholder = {"20XX-04-12","0"};
					int i = 0;
					while (i < maxScores) //continue if not all lines used
					{
						savedHighScores[i] = placeholder.clone();
						i++;
					}
					updateHighScoreLabel();
					System.out.println("F");
					makeFile.close();
				} catch (IOException e) {
					System.out.println("no highscores file");
					e.printStackTrace();
				} 

			}
		});

		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long temp)
			{
				if (!paused)
					upDate();	

			}
		};


		//https://stackoverflow.com/questions/12194967/how-to-close-all-stages-when-the-primary-stage-is-closed
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				Platform.exit();
				//Close all other stages (High scores)
			}
		});



		timer.start();
		primaryStage.setTitle("Gary Chen - ICS40 Culminating - Don MillZ FighterZ");
		primaryStage.setScene(scene);
		primaryStage.show();

		Scene highScoreScene = new Scene(highscores, WINDOW_WIDTH_SCORES, WINDOW_HEIGHT_SCORES);
		highscores.getChildren().addAll(clearScores);

		highScoreStage = new Stage();
		highScoreStage.setTitle("High Scores");
		highScoreStage.setScene(highScoreScene);


	}

	public void upDate()
	{
		for (int i = 0; i < players.size(); i++) //Go through all chars and update.
		{
			Character player = players.get(i);
			if (player.isHoldingLeft() == true)
			{
				player.moveLeft();
			}
			if (player.isHoldingRight() == true)
			{
				player.moveRight();
			}
			player.upDate(waitTime / 1000000000);
			if (player.getHealth() <= 0)
			{
				player.setDead(true);
			}
		}
		if (playing && (p1.getDead() || p2.getDead())) //Game ended
		{
			Character winningCharacter;
			if (p1.getDead() && p2.getDead())
			{
				winner.setText("draw");
				//draw
			}
			else
			{
				if (p1.getDead())
				{
					winner.setText("p2 wins");
					winningCharacter = p2;
					//p1 dead
				}
				else//p2 dead

				{
					winner.setText("p1 wins");
					winningCharacter = p1;
				}
				//someone dead: Calculate the score.
				double tempScore = (1000 * winningCharacter.getHealth() / winningCharacter.getDefaultHealth() - (timePassed-timeStarted)/1000000000*5);
				if (tempScore < 0)
					tempScore = 0; //Set to 0 if negative (too much time taken).
				//percentage of remaining health - time taken in seconds.
				currentScore = (int) tempScore;
				System.out.println("SCORE " + currentScore); //test
				//add to list and sort
				savedHighScores = setScore(currentScore);
				//write to file
				RandomAccessFile makeFile;
				try { //write high score to the file
					makeFile = new RandomAccessFile("resources/highscores.txt", "rw");
					for (int i = 0; i < savedHighScores.length; i++)
					{
						makeFile.writeBytes(savedHighScores[i][0] + "|" + savedHighScores[i][1] + "\n");
					}
					makeFile.close();
				} catch (IOException e) {
					System.out.println("Couldn't find highscores file");
					e.printStackTrace();
				} 

				//update label
				updateHighScoreLabel();

			}
			winner.setVisible(true);
			playing = false;

		}

		for (int i = 0; i < hitboxes.size(); i++)
		{
			Hitbox currentHitbox = hitboxes.get(i);
			currentHitbox.update();

			if (currentHitbox.isExpired() == true)
			{
				removeHitbox(currentHitbox);
			}
		}

		for (int i = 0; i < hitEffects.size(); i++)
		{
			HitEffect currentEffect = hitEffects.get(i);
			currentEffect.update();

			if (currentEffect.isExpired() == true)
			{
				removeHitEffect(currentEffect);
			}
		}

		p1Health.setText("P1 HP "+String.valueOf(p1.getHealth()) + "/" + String.valueOf(p1.getDefaultHealth()));
		p2Health.setText("P2 HP "+String.valueOf(p2.getHealth()) + "/" + String.valueOf(p2.getDefaultHealth()));

		timePassed += getSeconds();
		currentSeconds = System.nanoTime(); //prepare time to add

	}

	private double getSeconds()
	{
		return System.nanoTime() - currentSeconds;
	}

	public ArrayList<ImageView> getCharacters()
	{
		ArrayList<ImageView> temp = new ArrayList<ImageView>();
		for (int i = 0; i < characters.getChildren().size(); i++)
		{
			temp.add((ImageView) characters.getChildren().get(i));
		}
		return temp;
	}

	public ArrayList<ImageView> getHitboxesImages()
	{
		ArrayList<ImageView> temp = new ArrayList<ImageView>();
		for (int i = 0; i < attacks.getChildren().size(); i++)
		{
			temp.add((ImageView) attacks.getChildren().get(i));
		}
		return temp;
	}

	public ArrayList<Hitbox> getHitboxes()
	{
		ArrayList<Hitbox> temp = new ArrayList<Hitbox>();
		for (int i = 0; i < hitboxes.size(); i++)
		{
			temp.add(hitboxes.get(i));
		}
		return temp;
	}

	public void addHitbox(Hitbox hitbox)
	{
		hitboxes.add(hitbox);
		attacks.getChildren().add(hitbox.getImageView());
	}

	public void removeHitbox(Hitbox hitbox)
	{
		hitboxes.remove(hitbox);
		attacks.getChildren().remove(hitbox.getImageView());
	}

	public void addHitEffect(HitEffect effect)
	{
		hitEffects.add(effect);
		if (!hitEffectsImages.getChildren().contains(effect.getImageView()))
			hitEffectsImages.getChildren().add(effect.getImageView());
	}

	public void removeHitEffect(HitEffect effect)
	{
		hitEffects.remove(effect);
		hitEffectsImages.getChildren().remove(effect.getImageView());
	}
	
	/**
	 * 
	 * @param index
	 * The index of the hitbox in the list of hitbices
	 * @param player
	 * The player that called that they got hit
	 * @throws CloneNotSupportedException
	 * Probably shouldn't happen (implemented Cloneable)
	 */
	public void handlePlayerHit(int index, Character player) throws CloneNotSupportedException
	{//Called from the player that gets hit
		Hitbox hitbox = hitboxes.get(index);

		if (hitbox.isCanHit() == true)
		{
			HitEffect visual;
			Random random = new Random();
			double damageScale = hitbox.getOwnedPlayer().getScaleMultiplier(hitbox);
			hitbox.getOwnedPlayer().addStaleMove(hitbox); //add to staled moves
			hitboxes.get(index).setHitPlayer();
			double angle = hitbox.getAngle();
			if (hitbox.getDirection() < 0)
			{
				angle = 180 - hitbox.getAngle();
			}
			player.getHit(hitbox.getHitstun(), angle, hitbox.getKnockbackSpeed(), hitbox.getDamage()*damageScale);
			System.out.println(hitbox.damage*damageScale); //debug, remove

			if (random.nextBoolean())
			{
				visual = (HitEffect) hitbox.getEffect().clone(); 
				//Clone so that duplicate children aren't added to multihit moves every time (Beam)
				visual.setPos(player.getPos());
				addHitEffect(visual);
			}
		}
	}
	/**
	 * restarts the gamestate
	 */
	public void restart()
	{
		playing = true;
		winner.setVisible(false);
		hitboxes.clear();
		attacks.getChildren().clear();

		//Source: https://stackoverflow.com/questions/1268817/create-new-object-from-a-string-in-java
		//
		try {
			p1 = (Character) characterList[p1CharIndex].getConstructor(parameters).newInstance(new Object[] {defaultPositionP1});
			p2 = (Character) characterList[p2CharIndex].getConstructor(parameters).newInstance(new Object[] {defaultPositionP2});
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.out.println("Class creation failed");
			e.printStackTrace();
		}

		p1.setGame(this);
		p2.setGame(this);
		p1.getImageView().setScaleX(-1); //Face forward.
		p2.getImageView().setScaleX(1); //Face forward.
		players.clear();
		characters.getChildren().clear();
		players.add(p1);
		players.add(p2);
		characters.getChildren().addAll(p1.getImageView(),p2.getImageView());

		timeStarted = System.nanoTime();

	}

	/**
	 * Takes a new score, sorts it with current list and takes the new top 10/maxScores
	 * @param newScore
	 * New score to be sorted with current high score list and compared.
	 * @return A new sorted list of high scores + date after a game has ended.
	 */
	public String[][] setScore(int newScore)
	{
		String[][] sortedHighScores = new String[maxScores+1][];
		//Prepare a list with all past high scores + new score.
		for (int i = 0; i < savedHighScores.length; i++)
		{
			sortedHighScores[i] = savedHighScores[i];
		}


		ZoneId zonedId = ZoneId.of( "America/Montreal" );
		LocalDate today = LocalDate.now( zonedId );
		String newDate = today.toString();
		//		System.out.println( "today : " + today); //get date
		sortedHighScores[maxScores] = new String[] {newDate,String.valueOf(newScore)};
		sortedHighScores = sortList(sortedHighScores);
		String[][] newHighScores = new String[maxScores][];
		for (int i = 0; i < maxScores; i++) //newHighScores.length
		{
			newHighScores[i] = sortedHighScores[i];
		}
		return newHighScores;
	}

	/**
	 * Sorts a String 2D array with integers in the second array
	 * @param list
	 * A String[][] 2D array with integers in the second array
	 * @return
	 * Returns sorted array
	 */
	public String[][] sortList(String[][] list)
	{ //Inefficient but small.
		int offset = 1; //sort less each time a number is sorted
		while (offset != list.length) //all sorted
		{
			for (int j = 0; j < list.length-offset; j++)
			{
				int compare1 = Integer.parseInt(list[j][1]);
				int compare2 = Integer.parseInt(list[j+1][1]);//get 2 numbers
				if (compare1 < compare2) //if the 2 are out of order
				{
					String date1 = list[j][0];
					list[j][0] = list[j+1][0];
					list[j+1][0] = date1;
					list[j][1] = String.valueOf(compare2); //swap
					list[j+1][1] = String.valueOf(compare1);

				}

			}
			offset++;
		}
		return list;
	}

	/**
	 * Updated whenever highscores are loaded/changed
	 */
	private void updateHighScoreLabel()
	{
		highScoresLabel.setText("");
		for (int i = 0; i < savedHighScores.length; i++)
			if (savedHighScores[i] != null)
				highScoresLabel.setText(highScoresLabel.getText() + "#" + (i+1) + ": " + savedHighScores[i][0] + " " + savedHighScores[i][1] + "\n");
	}

	public static double getWindowWidth() {
		return WINDOW_WIDTH;
	}

	public static double getWindowHeight() {
		return WINDOW_HEIGHT;
	}

}