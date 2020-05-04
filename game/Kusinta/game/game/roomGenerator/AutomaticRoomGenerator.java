package game.roomGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AutomaticRoomGenerator {

	File file;
	String[][] m_elementTable;
	int m_row;
	int m_col;

	public AutomaticRoomGenerator(int nbRoom, int row, int col) {
		this.getFile(nbRoom);
		m_row = row;
		m_col = col;
		m_elementTable = new String[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				m_elementTable[i][j] = "";
			}
		}
	}

	public AutomaticRoomGenerator(int nbRoom, String[][] elementTable, int row, int col) {
		this.getFile(nbRoom);
		m_elementTable = elementTable;
		m_row = row;
		m_col = col;
	}

	/*
	 * This method is creating a table of empty spaces which creates an empty room
	 * It only put empty spaces were there is no wall
	 * 
	 */

	public void emptyMapGenerator() throws IOException {
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				if (m_elementTable[i][j].contentEquals("")) {
					m_elementTable[i][j] = "ES";
				}
			}
		}
	}

	/*
	 * 
	 * This method is used to add borders to the room of the given size This method
	 * should be invoked last after you added every element in the room The added
	 * border will automatically merge with close platforms If you left some spaces
	 * with no elements, this function will add empty spaces
	 * 
	 */

	public void AddCompleteBorder(int borderSize) throws IOException {
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				if (i < borderSize - 1 || j < borderSize - 1 || i > m_row - borderSize || j > m_col - borderSize
						|| (i == borderSize - 1 && j == borderSize - 1)
						|| (i == borderSize - 1 && j == m_col - borderSize)
						|| (i == m_row - borderSize && j == borderSize - 1)
						|| (i == m_row - borderSize && j == m_col - borderSize)) {
					m_elementTable[i][j] = "IW";
				} else if (i == borderSize - 1 && j > borderSize - 1 && j < m_col - borderSize) {
					if (m_elementTable[i + 1][j].contentEquals("OW_N")
							|| m_elementTable[i + 1][j].contentEquals("OW_NE")
							|| m_elementTable[i + 1][j].contentEquals("OW_NW")) {
						m_elementTable[i + 1][j] = "IW";
						m_elementTable[i][j] = "IW";
					} else if (m_elementTable[i + 1][j].contentEquals("OW_E")
							|| m_elementTable[i + 1][j].contentEquals("OW_W")
							|| m_elementTable[i + 1][j].contentEquals("OW_S")
							|| m_elementTable[i + 1][j].contentEquals("IW")
							|| m_elementTable[i + 1][j].contentEquals("OW_SE")
							|| m_elementTable[i + 1][j].contentEquals("OW_SW")) {
						m_elementTable[i][j] = "IW";
					} else {
						m_elementTable[i][j] = "OW_S";
					}
				} else if (i == m_row - borderSize && j > borderSize - 1 && j < m_col - borderSize) {
					if (m_elementTable[i - 1][j].contentEquals("OW_S")
							|| m_elementTable[i - 1][j].contentEquals("OW_SW")
							|| m_elementTable[i - 1][j].contentEquals("OW_SE")) {
						m_elementTable[i - 1][j] = "IW";
						m_elementTable[i][j] = "IW";
					} else if (m_elementTable[i - 1][j].contentEquals("OW_E")
							|| m_elementTable[i - 1][j].contentEquals("OW_W")
							|| m_elementTable[i - 1][j].contentEquals("OW_N")
							|| m_elementTable[i - 1][j].contentEquals("IW")
							|| m_elementTable[i - 1][j].contentEquals("OW_NE")
							|| m_elementTable[i - 1][j].contentEquals("OW_NW")) {
						m_elementTable[i][j] = "IW";
					} else {
						m_elementTable[i][j] = "OW_N";
					}
				} else if (j == borderSize - 1 && i > borderSize - 1 && i < m_row - borderSize) {
					if (m_elementTable[i][j + 1].contentEquals("OW_W")
							|| m_elementTable[i][j + 1].contentEquals("OW_NW")
							|| m_elementTable[i][j + 1].contentEquals("OW_SW")) {
						m_elementTable[i][j + 1] = "IW";
						m_elementTable[i][j] = "IW";
					} else if (m_elementTable[i][j + 1].contentEquals("OW_E")
							|| m_elementTable[i][j + 1].contentEquals("OW_S")
							|| m_elementTable[i][j + 1].contentEquals("OW_N")
							|| m_elementTable[i][j + 1].contentEquals("IW")
							|| m_elementTable[i][j + 1].contentEquals("OW_NE")
							|| m_elementTable[i][j + 1].contentEquals("OW_SE")) {
						m_elementTable[i][j] = "IW";
					} else {
						m_elementTable[i][j] = "OW_E";
					}
				} else if (j == m_col - borderSize && i > borderSize - 1 && i < m_row - borderSize) {
					if (m_elementTable[i][j - 1].contentEquals("OW_E")
							|| m_elementTable[i][j - 1].contentEquals("OW_SE")
							|| m_elementTable[i][j - 1].contentEquals("OW_NE")) {
						m_elementTable[i][j - 1] = "IW";
						m_elementTable[i][j] = "IW";
					} else if (m_elementTable[i][j - 1].contentEquals("OW_W")
							|| m_elementTable[i][j - 1].contentEquals("OW_S")
							|| m_elementTable[i][j - 1].contentEquals("OW_N")
							|| m_elementTable[i][j - 1].contentEquals("IW")
							|| m_elementTable[i][j - 1].contentEquals("OW_NW")
							|| m_elementTable[i][j - 1].contentEquals("OW_SW")) {
						m_elementTable[i][j] = "IW";
					} else {
						m_elementTable[i][j] = "OW_W";
					}
				} else {
					if (i == m_row - borderSize - 1 && j > borderSize - 1 && j < m_col - borderSize) {
						if (m_elementTable[i][j].contentEquals("ES") || m_elementTable[i][j].contentEquals("")) {
							m_elementTable[i][j] = "ES_T";
						}
					}
					if (m_elementTable[i][j].contentEquals("")) {
						m_elementTable[i][j] = "ES";
					}
				}
			}
		}

	}

	/*
	 * 
	 * This method add a platform at the i, j position, with the size and the width
	 * given. This width and the length must be at least bigger or equal to 2 (to
	 * have sprites corresponding for the platform) The platform will be added at
	 * this position without checking what is at this position Hard addition of a
	 * platform won't merge it with nearby platforms
	 * 
	 */

	public void AddPlatformHard(int i, int j, int width, int length) {
		if (length < 2 || width < 2) {
			System.out.println("The length or width is too small");
		} else {
			for (int k = i; k < i + width; k++) {
				for (int k2 = j; k2 < j + length; k2++) {
					if (k == i && k2 == j) {
						if (k > 0) {
							m_elementTable[k - 1][k2] = "ES_T";
						}
						m_elementTable[k][k2] = "OW_NW";
					} else if (k == i && k2 == j + length - 1) {
						if (k > 0) {
							m_elementTable[k - 1][k2] = "ES_T";
						}
						m_elementTable[k][k2] = "OW_NE";
					} else if (k == i + width - 1 && k2 == j) {
						m_elementTable[k][k2] = "OW_SW";
					} else if (k == i + width - 1 && k2 == j + length - 1) {
						m_elementTable[k][k2] = "OW_SE";
					} else if (k == i) {
						if (k > 0) {
							m_elementTable[k - 1][k2] = "ES_T";
						}
						m_elementTable[k][k2] = "OW_N";
					} else if (k == i + width - 1) {
						m_elementTable[k][k2] = "OW_S";
					} else if (k2 == j) {
						m_elementTable[k][k2] = "OW_W";
					} else if (k2 == j + length - 1) {
						m_elementTable[k][k2] = "OW_E";
					} else {
						m_elementTable[k][k2] = "IW";
					}
				}
			}
		}

	}


	/*
	 * 
	 * This method checks the room and add the required changes
	 * 
	 */

	public void verification() {
		for (int k = 1; k < m_row - 1; k++) {
			for (int k2 = 1; k2 < m_col - 1; k2++) {
				if (m_elementTable[k][k2].contentEquals("") || m_elementTable[k][k2] == null) {
					m_elementTable[k][k2] = "ES";
				} else if (m_elementTable[k][k2].contentEquals("ES") || m_elementTable[k][k2].contentEquals("ES_I")
						|| m_elementTable[k][k2].contentEquals("ES_D") || m_elementTable[k][k2].contentEquals("ES_T")) {
					m_elementTable[k][k2] = m_elementTable[k][k2];
				} else {
					if ((m_elementTable[k - 1][k2].contentEquals("IW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_N")
							|| m_elementTable[k - 1][k2].contentEquals("OW_S")
							|| m_elementTable[k - 1][k2].contentEquals("OW_E")
							|| m_elementTable[k - 1][k2].contentEquals("OW_W")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SW"))
							&& (m_elementTable[k][k2 - 1].contentEquals("IW")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_SW"))
							&& (m_elementTable[k + 1][k2].contentEquals("IW")
									|| m_elementTable[k + 1][k2].contentEquals("OW_N")
									|| m_elementTable[k + 1][k2].contentEquals("OW_S")
									|| m_elementTable[k + 1][k2].contentEquals("OW_E")
									|| m_elementTable[k + 1][k2].contentEquals("OW_W")
									|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
									|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
									|| m_elementTable[k + 1][k2].contentEquals("OW_SE")
									|| m_elementTable[k + 1][k2].contentEquals("OW_SW"))
							&& (m_elementTable[k][k2 + 1].contentEquals("IW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SW"))) {
						m_elementTable[k][k2] = "IW";
					} else if ((m_elementTable[k - 1][k2].contentEquals("IW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_N")
							|| m_elementTable[k - 1][k2].contentEquals("OW_S")
							|| m_elementTable[k - 1][k2].contentEquals("OW_E")
							|| m_elementTable[k - 1][k2].contentEquals("OW_W")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SW"))
							&& (m_elementTable[k][k2 - 1].contentEquals("IW")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_SW"))
							&& (m_elementTable[k + 1][k2].contentEquals("IW")
									|| m_elementTable[k + 1][k2].contentEquals("OW_N")
									|| m_elementTable[k + 1][k2].contentEquals("OW_S")
									|| m_elementTable[k + 1][k2].contentEquals("OW_E")
									|| m_elementTable[k + 1][k2].contentEquals("OW_W")
									|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
									|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
									|| m_elementTable[k + 1][k2].contentEquals("OW_SE")
									|| m_elementTable[k + 1][k2].contentEquals("OW_SW"))) {
						m_elementTable[k][k2] = "OW_E";
					} else if ((m_elementTable[k - 1][k2].contentEquals("IW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_N")
							|| m_elementTable[k - 1][k2].contentEquals("OW_S")
							|| m_elementTable[k - 1][k2].contentEquals("OW_E")
							|| m_elementTable[k - 1][k2].contentEquals("OW_W")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SW"))
							&& (m_elementTable[k][k2 - 1].contentEquals("IW")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_SW"))
							&& (m_elementTable[k][k2 + 1].contentEquals("IW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SW"))) {
						m_elementTable[k][k2] = "OW_S";
					} else if ((m_elementTable[k - 1][k2].contentEquals("IW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_N")
							|| m_elementTable[k - 1][k2].contentEquals("OW_S")
							|| m_elementTable[k - 1][k2].contentEquals("OW_E")
							|| m_elementTable[k - 1][k2].contentEquals("OW_W")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SW"))
							&& (m_elementTable[k][k2 + 1].contentEquals("IW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SW"))
							&& (m_elementTable[k + 1][k2].contentEquals("IW")
									|| m_elementTable[k + 1][k2].contentEquals("OW_N")
									|| m_elementTable[k + 1][k2].contentEquals("OW_S")
									|| m_elementTable[k + 1][k2].contentEquals("OW_E")
									|| m_elementTable[k + 1][k2].contentEquals("OW_W")
									|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
									|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
									|| m_elementTable[k + 1][k2].contentEquals("OW_SE")
									|| m_elementTable[k + 1][k2].contentEquals("OW_SW"))) {
						m_elementTable[k][k2] = "OW_W";
					} else if ((m_elementTable[k + 1][k2].contentEquals("IW")
							|| m_elementTable[k + 1][k2].contentEquals("OW_N")
							|| m_elementTable[k + 1][k2].contentEquals("OW_S")
							|| m_elementTable[k + 1][k2].contentEquals("OW_E")
							|| m_elementTable[k + 1][k2].contentEquals("OW_W")
							|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
							|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
							|| m_elementTable[k + 1][k2].contentEquals("OW_SE")
							|| m_elementTable[k + 1][k2].contentEquals("OW_SW"))
							&& (m_elementTable[k][k2 - 1].contentEquals("IW")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_SW"))
							&& (m_elementTable[k][k2 + 1].contentEquals("IW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SW"))) {
						m_elementTable[k][k2] = "OW_N";
					} else if ((m_elementTable[k - 1][k2].contentEquals("IW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_N")
							|| m_elementTable[k - 1][k2].contentEquals("OW_S")
							|| m_elementTable[k - 1][k2].contentEquals("OW_E")
							|| m_elementTable[k - 1][k2].contentEquals("OW_W")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SW"))
							&& (m_elementTable[k][k2 - 1].contentEquals("IW")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_SW"))) {
						m_elementTable[k][k2] = "OW_SE";
					} else if ((m_elementTable[k - 1][k2].contentEquals("IW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_N")
							|| m_elementTable[k - 1][k2].contentEquals("OW_S")
							|| m_elementTable[k - 1][k2].contentEquals("OW_E")
							|| m_elementTable[k - 1][k2].contentEquals("OW_W")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SW"))
							&& (m_elementTable[k][k2 + 1].contentEquals("IW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SW"))) {
						m_elementTable[k][k2] = "OW_SW";
					} else if ((m_elementTable[k + 1][k2].contentEquals("IW")
							|| m_elementTable[k + 1][k2].contentEquals("OW_N")
							|| m_elementTable[k + 1][k2].contentEquals("OW_S")
							|| m_elementTable[k + 1][k2].contentEquals("OW_E")
							|| m_elementTable[k + 1][k2].contentEquals("OW_W")
							|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
							|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
							|| m_elementTable[k + 1][k2].contentEquals("OW_SE")
							|| m_elementTable[k + 1][k2].contentEquals("OW_SW"))
							&& (m_elementTable[k][k2 - 1].contentEquals("IW")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
									|| m_elementTable[k][k2 - 1].contentEquals("OW_SW"))) {
						m_elementTable[k][k2] = "OW_NE";
					} else if ((m_elementTable[k + 1][k2].contentEquals("IW")
							|| m_elementTable[k + 1][k2].contentEquals("OW_N")
							|| m_elementTable[k + 1][k2].contentEquals("OW_S")
							|| m_elementTable[k + 1][k2].contentEquals("OW_E")
							|| m_elementTable[k + 1][k2].contentEquals("OW_W")
							|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
							|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
							|| m_elementTable[k + 1][k2].contentEquals("OW_SE")
							|| m_elementTable[k + 1][k2].contentEquals("OW_SW"))
							&& (m_elementTable[k][k2 + 1].contentEquals("IW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SW"))) {
						m_elementTable[k][k2] = "OW_NW";
					} else if ((m_elementTable[k - 1][k2].contentEquals("IW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_N")
							|| m_elementTable[k - 1][k2].contentEquals("OW_S")
							|| m_elementTable[k - 1][k2].contentEquals("OW_E")
							|| m_elementTable[k - 1][k2].contentEquals("OW_W")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SW"))
							&& (m_elementTable[k + 1][k2].contentEquals("IW")
									|| m_elementTable[k + 1][k2].contentEquals("OW_N")
									|| m_elementTable[k + 1][k2].contentEquals("OW_S")
									|| m_elementTable[k + 1][k2].contentEquals("OW_E")
									|| m_elementTable[k + 1][k2].contentEquals("OW_W")
									|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
									|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
									|| m_elementTable[k + 1][k2].contentEquals("OW_SE")
									|| m_elementTable[k + 1][k2].contentEquals("OW_SW"))) {
						if (m_elementTable[k][k2].contentEquals("IW") || m_elementTable[k][k2].contentEquals("OW_N")
								|| m_elementTable[k][k2].contentEquals("OW_S")
								|| m_elementTable[k][k2].contentEquals("OW_E")
								|| m_elementTable[k][k2].contentEquals("OW_W")
								|| m_elementTable[k][k2].contentEquals("OW_NE")
								|| m_elementTable[k][k2].contentEquals("OW_NW")
								|| m_elementTable[k][k2].contentEquals("OW_SE")
								|| m_elementTable[k][k2].contentEquals("OW_SW")) {
							m_elementTable[k][k2] = "ES";
						}
					} else if ((m_elementTable[k][k2 - 1].contentEquals("IW")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_SW"))
							&& (m_elementTable[k][k2 + 1].contentEquals("IW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
									|| m_elementTable[k][k2 + 1].contentEquals("OW_SW"))) {
						if (m_elementTable[k][k2].contentEquals("IW") || m_elementTable[k][k2].contentEquals("OW_N")
								|| m_elementTable[k][k2].contentEquals("OW_S")
								|| m_elementTable[k][k2].contentEquals("OW_E")
								|| m_elementTable[k][k2].contentEquals("OW_W")
								|| m_elementTable[k][k2].contentEquals("OW_NE")
								|| m_elementTable[k][k2].contentEquals("OW_NW")
								|| m_elementTable[k][k2].contentEquals("OW_SE")
								|| m_elementTable[k][k2].contentEquals("OW_SW")) {
							m_elementTable[k][k2] = "ES";
						}
					} else if (m_elementTable[k][k2 - 1].contentEquals("IW")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
							|| m_elementTable[k][k2 - 1].contentEquals("OW_SW")

							|| m_elementTable[k][k2 + 1].contentEquals("IW")
							|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
							|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
							|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
							|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
							|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
							|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
							|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
							|| m_elementTable[k][k2 + 1].contentEquals("OW_SW")

							|| m_elementTable[k - 1][k2].contentEquals("IW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_N")
							|| m_elementTable[k - 1][k2].contentEquals("OW_S")
							|| m_elementTable[k - 1][k2].contentEquals("OW_E")
							|| m_elementTable[k - 1][k2].contentEquals("OW_W")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
							|| m_elementTable[k - 1][k2].contentEquals("OW_SW")

							|| m_elementTable[k + 1][k2].contentEquals("IW")
							|| m_elementTable[k + 1][k2].contentEquals("OW_N")
							|| m_elementTable[k + 1][k2].contentEquals("OW_S")
							|| m_elementTable[k + 1][k2].contentEquals("OW_E")
							|| m_elementTable[k + 1][k2].contentEquals("OW_W")
							|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
							|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
							|| m_elementTable[k + 1][k2 - 1].contentEquals("OW_SE")
							|| m_elementTable[k + 1][k2 - 1].contentEquals("OW_SW")) {
						if (m_elementTable[k][k2].contentEquals("IW") || m_elementTable[k][k2].contentEquals("OW_N")
								|| m_elementTable[k][k2].contentEquals("OW_S")
								|| m_elementTable[k][k2].contentEquals("OW_E")
								|| m_elementTable[k][k2].contentEquals("OW_W")
								|| m_elementTable[k][k2].contentEquals("OW_NE")
								|| m_elementTable[k][k2].contentEquals("OW_NW")
								|| m_elementTable[k][k2].contentEquals("OW_SE")
								|| m_elementTable[k][k2].contentEquals("OW_SW")) {
							m_elementTable[k][k2] = "ES";
						}
					}
				}
			}
		}
	}

	/*
	 * 
	 * This method is used to add the initial point of the player This method will
	 * will not work if the cell is already full/used by a wall The initial point
	 * can replace a decor.
	 * 
	 */

	public void AddInitialpoint(int i, int j) {
		if (m_elementTable[i][j].contentEquals("ES") || m_elementTable[i][j].contentEquals("ES_T")) {
			m_elementTable[i][j] = "ES_I";
		} else {
			System.out.println("La case selectionnée ne convient pas");
		}
	}

	/*
	 * 
	 * This method sets the door of the room at the given position The door cannot
	 * be over the initial point and cannot be over a wall
	 * 
	 */

	public void AddDoor(int i, int j) {
		if (m_elementTable[i][j].contentEquals("ES") || m_elementTable[i][j].contentEquals("ES_T")) {
			m_elementTable[i][j] = "ES_D";
		} else {
			System.out.println("La case selectionnée ne convient pas");
		}
	}

	/*
	 * 
	 * This method changes one block
	 * 
	 */

	public void changeBlock(int i, int j) {
		String blockType = "";
		m_elementTable[i][j] = blockType;
	}


	/*
	 * 
	 * This method updates the text document based of the elementTable
	 * 
	 */

	public void updateTextDocument() throws IOException {
		BufferedWriter writer = CreateNewWriter(file);
		writer.write(m_row + ":" + m_col + "\n");
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				writer.write(m_elementTable[i][j] + "/");
			}
			writer.write("\n");
		}
		writer.close();
	}
	
	
	/*
	 * 
	 * This method allow to create of File of the given name. If the name is already
	 * used, a new name is asked
	 * 
	 */

	public void getFile(int nbRoom) {
		File f1 = new File("resources/Room/Sample/room" + nbRoom + ".sample");
		file =  f1;
	}


	/*
	 * 
	 * This method creates a new BufferedWriter that is used to write in the text
	 * document
	 * 
	 */

	public static BufferedWriter CreateNewWriter(File f1) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(f1));
			return writer;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * 
	 * This method is used to create a new BufferedReader that is used to read the
	 * text document
	 * 
	 */

	public static BufferedReader CreateNewReader(File f1) {
		try {
			if (!(f1.isFile())) {
				System.out.println("Fichier n'existe pas");
				return null;
			} else {
				BufferedReader reader = new BufferedReader(new FileReader(f1));
				return reader;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public void type1Generator(int row, int col) throws IOException {
		AddPlatformHard(1+row, 0+col, 2, 4);
		AddPlatformHard(3+row, 5+col, 2, 2);
		AddPlatformHard(2+row, 6+col, 2, 2);
		AddPlatformHard(7+row, 2+col, 2, 3);
		AddPlatformHard(5+row, 2+col, 2, 2);
		AddPlatformHard(8+row, 0+col, 2, 2);
		AddPlatformHard(8+row, 6+col, 2, 2);
		AddPlatformHard(5+row, 8+col, 2, 2);
		verification();
 	}
	
	public void type2Generator(int row, int col) throws IOException {
		AddPlatformHard(8+row, 4+col, 2, 2);
		AddPlatformHard(6+row, 0+col, 3, 3);
		AddPlatformHard(6+row, 7+col, 3, 3);
		AddPlatformHard(4+row, 4+col, 2, 2);
		AddPlatformHard(2+row, 1+col, 2, 2);
		AddPlatformHard(1+row, 0+col, 2, 2);
		AddPlatformHard(2+row, 7+col, 2, 2);
		AddPlatformHard(1+row, 8+col, 2, 2);
		AddPlatformHard(0+row, 4+col, 2, 2);
		verification();
	}

	public static void main(String[] args) throws IOException {
		AutomaticRoomGenerator Room = new AutomaticRoomGenerator(1, 30, 30);
		Room.emptyMapGenerator();
		Room.type1Generator(0+10, 0+10);
		Room.type2Generator(0+10, 10+10);
		Room.type2Generator(10+10, 0+10);
		Room.type1Generator(10+10,10+10);
		Room.AddInitialpoint(3+10, 4+10+10);
		Room.AddCompleteBorder(3);
		Room.updateTextDocument();
	}

}

	

