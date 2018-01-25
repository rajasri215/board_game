package org.businesshousegame.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the Board properties
 * 
 * @author Rajasri
 */
public class Board {
	private List<Cell> cells;

	/**
	 * When board is created, cells for the board will be loaded based on
	 * Configuration
	 */
	public Board() {
		loadCells();
	}

	/**
	 * Returns the cells corresponding to the board
	 * 
	 * @param cellId
	 * @return
	 */
	public Cell getCells(int cellId) {
		return cells.get(cellId);
	}

	/**
	 * Creates cells for the board using the cell definitions loaded in the
	 * Configuration class
	 */
	private void loadCells() {
		List<CellType> cellDefinitions = Configuration.getInstance().getCellDefinitions();
		if (!cellDefinitions.isEmpty()) {
			for (CellType cellType : cellDefinitions) {
				Cell cell = new Cell(cellType);
				if (cells == null) {
					cells = new ArrayList<>();
				}
				this.cells.add(cell);
			}
		}
	}
}
