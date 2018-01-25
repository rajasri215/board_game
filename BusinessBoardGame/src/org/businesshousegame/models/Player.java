package org.businesshousegame.models;

public class Player implements Comparable<Object> {

	private static int playerIdGenerator = 1;

	private int currentCellId = 1;// 1 is the starting cell
	private String displayName;
	private int playerId;
	private double value; // Amount held by a player

	// On player creation, id is auto generated and initial amount is set to
	// configured value
	public Player() {
		playerId = playerIdGenerator++;
		displayName = "Player" + playerId;
		value = Configuration.getInstance().getInitialAmount();
	}

	public void collectValue(double additionalAmount) {
		this.value += additionalAmount;
	}

	@Override
	public int compareTo(Object arg0) {
		return Double.compare(((Player) arg0).value, this.value);
	}

	public int getCurrentCellPosition() {
		return currentCellId;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public int getPlayerId() {
		return this.playerId;
	}

	public double getValue() {
		return value;
	}

	public void payFine(double fineAmount) {
		this.value -= fineAmount;
	}

	public void updateCellPosition(int diceValue) {
		currentCellId = currentCellId + diceValue;
		if (currentCellId > Configuration.getInstance().getNoOfCells()) {
			// To re iterate from the starting point
			currentCellId = currentCellId - Configuration.getInstance().getNoOfCells();
		}
	}

}
