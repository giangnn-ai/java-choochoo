package bk.zodi.android.games.choochoo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.test.IsolatedContext;

import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Vector2;

public class World {
	public interface WorldListener {
		public void autoRotate();

		public void crash();

		public void choochoo();

		public void wheelshreek();

		public void win();
	}

	public static final float WORLD_WIDTH = 20;
	public static final float WORLD_HEIGHT = 12;

	public static final int WORLD_STATE_RAILING = 0;
	public static final int WORLD_STATE_RUNNING = 1;
	public static final int WORLD_STATE_LEVEL_COMPLETE = 2;
	public static final int WORLD_STATE_CRASH = 3;

	public int state = WORLD_STATE_RAILING;

	public float STEP_TIME = 0.1f;

	public float timer = 0;
	public int crashCount = 0;
	private boolean choo = true;

	private boolean isDoubleSpeed = false;
	private int level;

	public final WorldListener listener;
	public List<Rail> rails;
	public List<Train> trains;
	public List<Station> stations;

	public World(WorldListener listener, int level) {
		this.listener = listener;
		this.level = level;
		init(level);
	}

	private void init(int level) {
		state = WORLD_STATE_RAILING;
		generateRails(level);
		generateStations(level);
		generateTrains(level);
		setRailForCarriage();
	}

	public void update(float deltaTime) {
		if (state == WORLD_STATE_RUNNING) {
			timer += deltaTime;
			if (timer > STEP_TIME) {
				timer -= STEP_TIME;
				listener.wheelshreek();
				moveTrain();
				listener.wheelshreek();

				if (isDoubleSpeed) {
					listener.wheelshreek();
					moveTrain();
					listener.wheelshreek();
					listener.wheelshreek();
					moveTrain();
					listener.wheelshreek();
				}
				checkCoolisionsTrains();
			}
		} else if (state == WORLD_STATE_CRASH) {
			if (crashCount < 1)
				listener.crash();
			if (crashCount < 50) {
				timer += deltaTime;
				if (timer > STEP_TIME) {
					timer -= STEP_TIME;
					crashTrain();
					++crashCount;
				}
			}
		} else if (state == WORLD_STATE_LEVEL_COMPLETE) {
			if (level < 3) {
				listener.win();
				this.level++;
				init(level);
			}
		} else {
			timer = 0;
			crashCount = 0;
		}
	}

	public void generateRails(int level) {
		this.rails = new ArrayList<Rail>();
		int[][] map = GameConstants.getInstance().getMap(level);
		Random rand = new Random();
		for (int i = 0; i < WORLD_HEIGHT; ++i) {
			for (int j = 0; j < WORLD_WIDTH; ++j) {
				int cell = map[i][j];
				if (cell == GameConstants.NONE_RAIL)
					continue;
				else {
					Rail rail;
					float x = j + 0.5f;
					float y = i + 0.5f;
					if (cell == GameConstants.RAIL_STRAIGHT) {
						rail = new StraightRail(x, y, rand.nextInt(2));
					} else if (cell == GameConstants.RAIL_TURN) {
						rail = new TurnRail(x, y, rand.nextInt(4));
					} else if (cell == GameConstants.RAIL_RIGTH_PRIORITY) {
						rail = new RightPriorityRail(x, y, rand.nextInt(4));
					} else if (cell == GameConstants.RAIL_LEFT_PRIORITY) {
						rail = new LeftPriorityRail(x, y, rand.nextInt(4));
					} else if (cell == GameConstants.RAIL_CROSS) {
						rail = new CrossRail(x, y, 0);
					} else if (cell == GameConstants.RAIL_DOUBLE_TURN) {
						rail = new DoubleTurnRail(x, y, rand.nextInt(2));
					} else if (cell == GameConstants.RAIL_JUMP_BRIDGE) {
						rail = new JumpBridgeRail(x, y, rand.nextInt(4));
					} else if (cell == GameConstants.RAIL_STRAIGHT_HORIZONTAL) {
						rail = new StraightRail(x, y, 0);
					} else {
						rail = new StraightRail(x, y, 1);
					}
					rails.add(rail);
				}
			}
		}
		int[] autoRotate = GameConstants.getInstance().getAutoRotate(level);
		if (autoRotate != null) {
			int len = autoRotate.length;
			for (int i = 0; i < len; i += 2) {
				float x = (float) autoRotate[i] + 0.5f;
				float y = (float) autoRotate[i + 1] + 0.5f;
				try {
					getRail(x, y).isAutoRotate = true;
				} catch (NullPointerException ne) {
					System.out.println("Wrong map boy!");
				}
			}
		}
	}

	public void generateStations(int level) {
		this.stations = new ArrayList<Station>();
		stations = GameConstants.getInstance().getStations(level);
	}

	public void generateTrains(int level) {
		this.trains = new ArrayList<Train>();
		trains = GameConstants.getInstance().getTrains(level);
	}

	public void setRailForCarriage() {
		int len = trains.size();
		for (int i = 0; i < len; ++i) {
			Train train = trains.get(i);
			int numCarriage = train.carriages.size();
			for (int j = 0; j < numCarriage; ++j) {
				Carriage carriage = train.carriages.get(j);
				Rail rail = getRail(carriage.position.x, carriage.position.y);
				carriage.rail = rail;
			}
		}
	}

	public Rail getRail(float x, float y) {
		Vector2 position = new Vector2(x, y);
		for (Rail rail : rails) {
			if (OverlapTester.pointInRectangle(rail.bounds, position)) {
				return rail;
			}
		}
		return null;
	}

	public Rail getNextRail(Carriage carriage) {
		int direction = carriage.direction;
		float x = carriage.rail.position.x;
		float y = carriage.rail.position.y;
		if (direction == GameConstants.DIRECTION_UP)
			y += carriage.rail.nextRail;
		else if (direction == GameConstants.DIRECTION_RIGHT)
			x += carriage.rail.nextRail;
		else if (direction == GameConstants.DIRECTION_DOWN)
			y -= carriage.rail.nextRail;
		else
			x -= carriage.rail.nextRail;

		for (Rail rail : rails) {
			if (rail.position.x == x && rail.position.y == y) {
				return rail;
			}
		}
		return null;
	}

	public boolean isInStation(Carriage carriage) {
		int direction = carriage.direction;
		float x = carriage.position.x;
		float y = carriage.position.y;
		if (direction == GameConstants.DIRECTION_UP)
			y += 0.5f;
		else if (direction == GameConstants.DIRECTION_RIGHT)
			x += 0.5f;
		else if (direction == GameConstants.DIRECTION_DOWN)
			y -= 0.5f;
		else
			x -= 0.5f;

		for (Station station : stations) {
			if (carriage.type == station.type) {
				System.out
						.println(carriage.direction + "-" + station.direction);
				if ((carriage.direction == GameConstants.DIRECTION_RIGHT && station.direction == Station.STATION_RIGHT)
						|| (carriage.direction == GameConstants.DIRECTION_UP && station.direction == Station.STATION_UP)
						|| (carriage.direction == GameConstants.DIRECTION_LEFT && station.direction == Station.STATION_LEFT)
						|| (carriage.direction == GameConstants.DIRECTION_DOWN && station.direction == Station.STATION_DOWN)) {
					if (OverlapTester.pointInRectangle(station.bounds, x, y)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void touchWorld(Vector2 touchPoint) {
		touchPoint.x = (touchPoint.x / (float) 800) * WORLD_WIDTH;
		touchPoint.y = (touchPoint.y / (float) 480) * WORLD_HEIGHT;
	}

	public void checkCoolisionsRails(Vector2 touchPoint) {
		for (Rail rail : rails) {
			if (OverlapTester.pointInRectangle(rail.bounds, touchPoint)) {
				rail.turn();
			}
		}
	}

	public void checkCoolisionsTrains() {
		int len = trains.size();
		for (int i = 0; i < len; ++i) {
			Carriage head = trains.get(i).carriages.get(0);
			for (int j = 0; j < len; ++j) {
				if (j != i) {
					Train train = trains.get(j);
					int numCarriage = train.carriages.size();
					for (int k = 0; k < numCarriage; ++k) {
						Carriage carriage = train.carriages.get(k);
						if (OverlapTester.overlapRectangles(head.bounds,
								carriage.bounds)) {
							trains.get(i).isCrash = true;
							trains.get(j).isCrash = true;
							state = WORLD_STATE_CRASH;
							return;
						}
					}
				}
			}
		}
	}

	public void moveTrain() {
		int len = trains.size();
		for (int i = 0; i < len; ++i) {
			Train train = trains.get(i);
			train.inRailPosition += 1;
			int numCarriage = train.carriages.size();
			for (int j = 0; j < numCarriage; ++j) {
				Carriage carriage = train.carriages.get(j);
				if (!carriage.isDisappear) {
					try {
						carriage.move(train.inRailPosition);
					} catch (NullPointerException ne) {
						train.isCrash = true;
						state = WORLD_STATE_CRASH;
					}
					if (train.inRailPosition > 19) {
						carriage.direction = carriage.updateDirection();
						if (j == numCarriage - 1) {
							if (carriage.rail.isAutoRotate) {
								carriage.rail.turn();
								listener.autoRotate();
							}
							if (choo == true) {
								choo = false;
								listener.choochoo();
							} else {
								choo = true;
							}
						}
						carriage.rail = getNextRail(carriage);
						if (carriage.rail == null) {
							if (isInStation(carriage)) {
								carriage.isDisappear = true;
								if (checkLevelComplete()) {
									state = WORLD_STATE_LEVEL_COMPLETE;
								}
							} else {
								train.isCrash = true;
								state = WORLD_STATE_CRASH;
							}
						}
					}
				}
			}
			if (train.inRailPosition > 19) {
				train.inRailPosition = 1;
			}
		}
	}

	public void crashTrain() {
		int len = trains.size();
		for (int i = 0; i < len; ++i) {
			Train train = trains.get(i);
			if (train.isCrash) {
				int numCarriage = train.carriages.size();
				for (int j = 0; j < numCarriage; ++j) {
					Carriage carriage = train.carriages.get(j);
					if (j % 2 == 0)
						carriage.angle += 5;
					else
						carriage.angle -= 5;
					if (carriage.direction == GameConstants.DIRECTION_UP)
						carriage.position.y += (j + 1) * 0.01f;
					else if (carriage.direction == GameConstants.DIRECTION_LEFT)
						carriage.position.x -= (j + 1) * 0.01f;
					else if (carriage.direction == GameConstants.DIRECTION_DOWN)
						carriage.position.y -= (j + 1) * 0.01f;
					else
						carriage.position.x += (j + 1) * 0.01f;
				}
			}
		}
	}

	public boolean checkLevelComplete() {
		int len = trains.size();
		for (int i = 0; i < len; ++i) {
			Train train = trains.get(i);
			int numCarriage = train.carriages.size();
			for (int j = 0; j < numCarriage; ++j) {
				Carriage carriage = train.carriages.get(j);
				if (!carriage.isDisappear)
					return false;
			}
		}
		return true;
	}

	public void restart() {
		state = WORLD_STATE_RAILING;
		generateTrains(level);
		setRailForCarriage();
	}

	public void changeSpeed() {
		isDoubleSpeed = !isDoubleSpeed;
	}
}
