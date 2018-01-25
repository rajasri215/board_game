package org.businesshousegame.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.businesshousegame.utils.Utilities;

public class Game {

	Board board;
	Dice dice;
	List<Player> players;

	public Game() {
		loadPlayers();
		loadBoard();
		this.dice = new Dice();
	}

	/**
	 * Lists the players and their balance amount at the end of play
	 */
	public void displayResult() {
		Utilities.displayMessageNewLine("> Results");
		Collections.sort(this.players);
		for (Player player : this.players) {
			Utilities.displayMessageNewLine(
					"Player ID: " + player.getDisplayName() + " Final amount : " + player.getValue());
		}
	}

	private void loadBoard() {
		this.board = new Board();
	}

	/**
	 * Created players based on configuration and loads it to Game
	 */
	private void loadPlayers() {
		int noOfPlayers = Configuration.getInstance().getNoOfPlayers();
		for (int i = 0; i < noOfPlayers; i++) {
			Player player = new Player();
			if (players == null) {
				players = new ArrayList<>();
			}
			this.players.add(player);
		}
	}

	/**
	 * Based on the configuration, dice will be diced and based on the value,
	 * the player position keeps changing in the board
	 */
	public void play() {
		int currentTurn = 1;
		Utilities.displayMessageNewLine("> Game Starts");
		int playerCount = Configuration.getInstance().getNoOfPlayers();
		int passCount = Configuration.getInstance().getNoOfPass() * playerCount;

		for (int pass = 1; pass <= passCount; pass++) {

			int diceValue = dice.dice();
			StringBuilder spacing = new StringBuilder();
			for (int i = 0; i < currentTurn; i++) {
				spacing.append("   ");
			}
			Utilities.displayMessage(spacing.toString() + ">");
			Utilities.displayMessageNewLine("Current Turn: " + this.players.get(currentTurn - 1).getDisplayName()
					+ " - Dice value: " + diceValue);
			try {
				TimeUnit.SECONDS.sleep(Configuration.getInstance().getPassDuration());
			} catch (InterruptedException e) {
				Utilities.displayMessageNewLine("Cannot wait for the configured pass duration");
			}
			updatePlayerAmount(this.players.get(currentTurn - 1), diceValue, spacing);
			currentTurn = (currentTurn + 1 <= playerCount) ? (currentTurn + 1) : 1;
			Utilities.displayMessageNewLine("");
		}
		Utilities.displayMessageNewLine("> Game Over");
		displayResult();
	}

	/**
	 * Update the balance amount when user lands in Hotel cell
	 * 
	 * @param player
	 *            : Player landed in the current cell
	 * @param currentCell
	 *            : Current cell in the board
	 * @param spacing
	 *            : For formatted printing in console
	 */
	private void updateHotelCellPlayer(Player player, Cell currentCell, StringBuilder spacing) {
		if (currentCell.getOwner() == 0) {
			currentCell.updateOwner(player.getPlayerId());
			// Amount paid to purchase hotel
			player.payFine(currentCell.getDebitValue());
			Utilities.displayMessageNewLine(
					spacing.toString() + "-Purchased hotel for : " + currentCell.getDebitValue());
		} else if ((player.getPlayerId() != (currentCell.getOwner()))) {
			player.payFine(currentCell.getCreditValue()); // Rent paid by the
															// visitor player
			Utilities.displayMessageNewLine(spacing.toString() + "-Rent amount " + currentCell.getCreditValue()
					+ " paid to " + this.players.get(currentCell.getOwner() - 1).getDisplayName());
			// Rent amount to be added for the owner
			this.players.get(currentCell.getOwner() - 1).collectValue(currentCell.getCreditValue());
		} else {
			Utilities.displayMessageNewLine(spacing.toString() + "-This player is the owner of the hotel.");
		}
	}

	/**
	 * Update the balance amount when user lands in Jail cell
	 * 
	 * @param player
	 *            : Player landed in the current cell
	 * @param currentCell
	 *            : Current cell in the board
	 * @param spacing
	 *            : For formatted printing in console
	 */
	private void updateJailCellPlayer(Player player, Cell currentCell, StringBuilder spacing) {
		player.payFine(currentCell.getDebitValue());
		Utilities.displayMessageNewLine(spacing.toString() + "-Fine paid for Jail : " + currentCell.getDebitValue());
	}

	/**
	 * Based on cell type, update the amount for the player
	 * 
	 * @param player
	 *            : Player
	 * @param diceValue
	 *            : dice output
	 * @param spacing
	 *            : text formatting
	 */
	private void updatePlayerAmount(Player player, int diceValue, StringBuilder spacing) {

		// Update player position and display the navigation details
		int prevCellId = player.getCurrentCellPosition();
		player.updateCellPosition(diceValue);
		int playerLatestCell = player.getCurrentCellPosition();
		Utilities.displayMessageNewLine(
				spacing.toString() + "-Player moving from C" + prevCellId + " to C" + playerLatestCell);

		// Update the balance amount
		Cell currentCell = this.board.getCells(playerLatestCell - 1);
		if (currentCell.getCellType() == CellType.HOTEL) {
			updateHotelCellPlayer(player, currentCell, spacing);
		} else if (currentCell.getCellType() == CellType.JAIL) {
			updateJailCellPlayer(player, currentCell, spacing);
		} else if (currentCell.getCellType() == CellType.TREASURE) {
			updateTreasureCellPlayer(player, currentCell, spacing);
		} else {
			Utilities.displayMessageNewLine(spacing.toString() + "-Landed in empty cell");
		}
	}

	/**
	 * Update the balance amount when user lands in Treasure cell
	 * 
	 * @param player
	 *            : Player landed in the current cell
	 * @param currentCell
	 *            : Current cell in the board
	 * @param spacing
	 *            : For formatted printing in console
	 */
	private void updateTreasureCellPlayer(Player player, Cell currentCell, StringBuilder spacing) {
		player.collectValue(currentCell.getCreditValue());
		Utilities.displayMessageNewLine(spacing.toString() + "-Treasure collected : " + currentCell.getCreditValue());
	}

}
