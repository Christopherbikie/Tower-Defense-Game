package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;

import org.lwjgl.input.Mouse;

import UI.Button;
import UI.UI;
import UI.UI.Menu;

public class Game {
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	private UI gameUI;
	private Menu towerPickerMenu;

	public Game(int[][] map) {
		grid = new TileGrid(map);
		waveManager = new WaveManager(
				new Enemy(QuickLoad("UFO64"), grid.getTile(10, 8), grid, TILE_SIZE, TILE_SIZE, 70, 25), 2, 2);
		player = new Player(grid, waveManager);
		player.setup();
		setupUI();
	}

	private void setupUI() {
		gameUI = new UI();
//		towerPickerUI.addButton("CannonBlue", "cannonBlueFull", 0, 0);
//		towerPickerUI.addButton("CannonIce", "cannonIceFull", TILE_SIZE, 0);
		gameUI.createMenu("TowerPicker", 1280, 0, 192, 960, 2, 0);
		towerPickerMenu = gameUI.getMenu("TowerPicker");
		towerPickerMenu.quickAdd("CannonBlue", "cannonBlueFull");
		towerPickerMenu.quickAdd("CannonIce", "cannonIceFull");
	}

	private void updateUI() {
		gameUI.draw();

		if (Mouse.next()) {
			boolean mouseClicked = Mouse.isButtonDown(0);
			if (mouseClicked) {
				if (towerPickerMenu.isButtonClicked("CannonBlue"))
					player.pickTower(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				if (towerPickerMenu.isButtonClicked("CannonIce"))
					player.pickTower(new TowerCannonIce(TowerType.CannonIce, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
			}
		}
	}

	public void update() {
		DrawQuadTex(QuickLoad("Menu_Background"), 1280, 0, 192, 960);
		grid.draw();
		waveManager.update();
		player.update();
		updateUI();
	}
}
