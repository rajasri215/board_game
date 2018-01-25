package org.businesshousegame.models;

/**
 * To define each cell of the board game
 * 
 * @author Rajasri
 */
public class Cell {

	private static int cellIdGenerator = 1; // To automatically generate cell id
	private int cellId; // To uniquely identify each cell
	// Hotel has a cell owner. This is added here so that if any other type can
	// have owner, we can easily incorporate it
	// If the use case changes to "Cell can have multiple owners", this can be
	// changed to a list
	private int cellOwner;
								private CellType cellType;// Cell type could be Hotel, Jail, Empty and
										// Treasure.
	private double creditValue = 0.0; // Credit value for the landed player or

	// hotel rent value
	private double debitValue = 0.0; // Debit value for the landed player or
										// hotel purchase value

	public Cell(CellType cellType) {
		cellId = cellIdGenerator++;
		this.cellType = cellType;
		setCreditAndDebitValues();
	}

	public int getCellId() {
		return cellId;
	}

	public CellType getCellType() {
		return this.cellType;
	}

	public double getCreditValue() {
		return this.creditValue;
	}

	public double getDebitValue() {
		return this.debitValue;
	}

	public int getOwner() {
		return this.cellOwner;
	}

	/**
	 * Based on the Cell type, credit and debit values are set
	 */
	private void setCreditAndDebitValues() {
		if (this.cellType == CellType.HOTEL) {
			this.creditValue = Configuration.getInstance().getHotelRentValue();
			this.debitValue = Configuration.getInstance().getHotelPurchaseValue();
		} else if (this.cellType == CellType.JAIL) {
			this.debitValue = Configuration.getInstance().getJailValue();
		} else if (this.cellType == CellType.TREASURE) {
			this.creditValue = Configuration.getConfiguration().getTreasureValue();
		}
	}

	public void updateOwner(int playerId) {
		this.cellOwner = playerId;
	}
}
