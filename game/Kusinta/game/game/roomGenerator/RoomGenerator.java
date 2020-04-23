package game.roomGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RoomGenerator {

	String[][] m_elementTable;
	int m_row;
	int m_col;

	public RoomGenerator(int row, int col) {
		m_row = row;
		m_col = col;
		m_elementTable = new String[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				m_elementTable[i][j] = "";
			}
		}
	}

	public RoomGenerator(String[][] elementTable, int row, int col) {
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

	public void AddCompleteBorder() throws IOException {
		System.out.println("Enter the boderSize :");
		int borderSize = enterNb();
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

	public void AddPlatformHard() {
		System.out.println("Enter i position :");
		int i = enterNb();
		System.out.println("Enter j position :");
		int j = enterNb();
		System.out.println("Enter width :");
		int width = enterNb();
		System.out.println("Enter length :");
		int length = enterNb();
		if (length < 2 || width < 2) {
			System.out.println("The length or width is too small");
		} else {
			for (int k = i; k < i + width; k++) {
				for (int k2 = j; k2 < j + length; k2++) {
					if (k == i && k2 == j) {
						m_elementTable[k - 1][k2] = "ES_T";
						m_elementTable[k][k2] = "OW_NW";
					} else if (k == i && k2 == j + length - 1) {
						m_elementTable[k - 1][k2] = "ES_T";
						m_elementTable[k][k2] = "OW_NE";
					} else if (k == i + width - 1 && k2 == j) {
						m_elementTable[k][k2] = "OW_SW";
					} else if (k == i + width - 1 && k2 == j + length - 1) {
						m_elementTable[k][k2] = "OW_SE";
					} else if (k == i) {
						m_elementTable[k - 1][k2] = "ES_T";
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
	 * This method is used to add a platform and insert in the environnement where
	 * you add it The changes to make it correponds to the nearby platforms are
	 * This method cannot be applied for border platforms
	 * 
	 */

	public void AddPlatformOptimise() {
		int i = 0;
		int j = 0;
		int width = 0;
		int length = 0;
		while ((i<=0 || i+width>=m_row-1) && (j<=0 || j+length>=m_col-1)) {
			System.out.println("Enter i position :");
			i = enterNb();
			System.out.println("Enter j position :");
			j = enterNb();
			System.out.println("Enter width :");
			width = enterNb();
			System.out.println("Enter length :");
			length = enterNb();
		}
		if (length < 2 || width < 2) {
			System.out.println("The length or width is too small");
		} else {
			for (int k = i; k < i + width; k++) {
				for (int k2 = j; k2 < j + length; k2++) {
					if (k == i && k2 == j) {
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
										|| m_elementTable[k][k2 - 1].contentEquals("OW_SW"))) {
							m_elementTable[k][k2] = "IW";
						} else if ((m_elementTable[k - 1][k2].contentEquals("IW")
								|| m_elementTable[k - 1][k2].contentEquals("OW_N")
								|| m_elementTable[k - 1][k2].contentEquals("OW_S")
								|| m_elementTable[k - 1][k2].contentEquals("OW_E")
								|| m_elementTable[k - 1][k2].contentEquals("OW_W")
								|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
								|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
								|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
								|| m_elementTable[k - 1][k2].contentEquals("OW_SW"))) {
							m_elementTable[k][k2] = "OW_W";
						} else if ((m_elementTable[k][k2 - 1].contentEquals("IW")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_SW"))) {
							m_elementTable[k][k2] = "OW_N";
						} else {
							m_elementTable[k - 1][k2] = "ES_T";
							m_elementTable[k][k2] = "OW_NW";
						}
					} else if (k == i && k2 == j + length - 1) {
						if ((m_elementTable[k - 1][k2].contentEquals("IW")
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
							m_elementTable[k][k2] = "IW";
						} else if ((m_elementTable[k - 1][k2].contentEquals("IW")
								|| m_elementTable[k - 1][k2].contentEquals("OW_N")
								|| m_elementTable[k - 1][k2].contentEquals("OW_S")
								|| m_elementTable[k - 1][k2].contentEquals("OW_E")
								|| m_elementTable[k - 1][k2].contentEquals("OW_W")
								|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
								|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
								|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
								|| m_elementTable[k - 1][k2].contentEquals("OW_SW"))) {
							m_elementTable[k][k2] = "OW_E";
						} else if ((m_elementTable[k][k2 + 1].contentEquals("IW")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_SW"))) {
							m_elementTable[k][k2] = "OW_N";
						} else {
							m_elementTable[k - 1][k2] = "ES_T";
							m_elementTable[k][k2] = "OW_NE";
						}
					} else if (k == i + width - 1 && k2 == j) {

						if ((m_elementTable[k + 1][k2].contentEquals("IW")
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
							m_elementTable[k][k2] = "IW";
						} else if ((m_elementTable[k + 1][k2].contentEquals("IW")
								|| m_elementTable[k + 1][k2].contentEquals("OW_N")
								|| m_elementTable[k + 1][k2].contentEquals("OW_S")
								|| m_elementTable[k + 1][k2].contentEquals("OW_E")
								|| m_elementTable[k + 1][k2].contentEquals("OW_W")
								|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
								|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
								|| m_elementTable[k + 1][k2].contentEquals("OW_SE")
								|| m_elementTable[k + 1][k2].contentEquals("OW_SW"))) {
							m_elementTable[k][k2] = "OW_W";
						} else if ((m_elementTable[k][k2 - 1].contentEquals("IW")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_SW"))) {
							m_elementTable[k][k2] = "OW_S";
						} else {
							m_elementTable[k][k2] = "OW_SW";
						}
					} else if (k == i + width - 1 && k2 == j + length - 1) {
						if ((m_elementTable[k + 1][k2].contentEquals("IW")
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
						} else if ((m_elementTable[k + 1][k2].contentEquals("IW")
								|| m_elementTable[k + 1][k2].contentEquals("OW_N")
								|| m_elementTable[k + 1][k2].contentEquals("OW_S")
								|| m_elementTable[k + 1][k2].contentEquals("OW_E")
								|| m_elementTable[k + 1][k2].contentEquals("OW_W")
								|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
								|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
								|| m_elementTable[k + 1][k2].contentEquals("OW_SE")
								|| m_elementTable[k + 1][k2].contentEquals("OW_SW"))) {
							m_elementTable[k][k2] = "OW_E";
						} else if ((m_elementTable[k][k2 + 1].contentEquals("IW")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_SW"))) {
							m_elementTable[k][k2] = "OW_S";
						} else {
							m_elementTable[k][k2] = "OW_SE";
						}
					} else if (k == i) {
						if ((m_elementTable[k - 1][k2].contentEquals("IW")
								|| m_elementTable[k - 1][k2].contentEquals("OW_N")
								|| m_elementTable[k - 1][k2].contentEquals("OW_S")
								|| m_elementTable[k - 1][k2].contentEquals("OW_E")
								|| m_elementTable[k - 1][k2].contentEquals("OW_W")
								|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
								|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
								|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
								|| m_elementTable[k - 1][k2].contentEquals("OW_SW"))) {
							m_elementTable[k][k2] = "IW";
						} else {
							m_elementTable[k - 1][k2] = "ES_T";
							m_elementTable[k][k2] = "OW_N";
						}
					} else if (k == i + width - 1) {
						if ((m_elementTable[k + 1][k2].contentEquals("IW")
								|| m_elementTable[k + 1][k2].contentEquals("OW_N")
								|| m_elementTable[k + 1][k2].contentEquals("OW_S")
								|| m_elementTable[k + 1][k2].contentEquals("OW_E")
								|| m_elementTable[k + 1][k2].contentEquals("OW_W")
								|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
								|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
								|| m_elementTable[k + 1][k2].contentEquals("OW_SE")
								|| m_elementTable[k + 1][k2].contentEquals("OW_SW"))) {
							m_elementTable[k][k2] = "IW";
						} else {
							m_elementTable[k][k2] = "OW_S";
						}
					} else if (k2 == j) {
						if ((m_elementTable[k][k2 - 1].contentEquals("IW")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_SW"))) {
							m_elementTable[k][k2] = "IW";
						} else {
							m_elementTable[k][k2] = "OW_W";
						}
					} else if (k2 == j + length - 1) {
						if ((m_elementTable[k][k2 + 1].contentEquals("IW")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_SW"))) {
							m_elementTable[k][k2] = "IW";
						} else {
							m_elementTable[k][k2] = "OW_E";
						}
					} else {
						m_elementTable[k][k2] = "IW";
					}
				}
			}
		}

	}

	/*
	 * 
	 * This method adds the platform at the follow coordinate ajusting it to the
	 * other platforms around it length >=2 and width >=2 Soft addition of a
	 * platform will only add it if there is no platform on the same blocks or no
	 * platforms around it
	 * 
	 */

	public void AddPlatformSoft() {
		int verification = 0;
		System.out.println("Enter i position :");
		int i = enterNb();
		System.out.println("Enter j position :");
		int j = enterNb();
		System.out.println("Enter width :");
		int width = enterNb();
		System.out.println("Enter length :");
		int length = enterNb();
		if (length < 2 || width < 2) {
			System.out.println("The length or width is too small");
		} else {
			for (int k = i; k < i + width; k++) {
				for (int k2 = j; k2 < j + length; k2++) {
					if (k == i && k2 == j) {
						if (!m_elementTable[k][k2].contentEquals("") && !m_elementTable[k][k2].contentEquals("ES")
								&& !m_elementTable[k][k2].contentEquals("ES_I")
								&& !m_elementTable[k][k2].contentEquals("ES_T")
								&& m_elementTable[k][k2].contentEquals("ES_D")) {
							// System.out.println(
							// "The platform is over an existing element\n you can't place another platform
							// on it in soft\n Try medium or Hard mode");

							verification = -1;
							return;
						} else if (m_elementTable[k - 1][k2].contentEquals("IW")
								|| m_elementTable[k][k2 - 1].contentEquals("IW")
								|| m_elementTable[k - 1][k2].contentEquals("OW_N")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
								|| m_elementTable[k - 1][k2].contentEquals("OW_S")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
								|| m_elementTable[k - 1][k2].contentEquals("OW_E")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
								|| m_elementTable[k - 1][k2].contentEquals("OW_W")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
								|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
								|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
								|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
								|| m_elementTable[k - 1][k2].contentEquals("OW_SW")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_SW")) {
							// System.out.println(
							// "There is a block next to your platform.\n You cannot add it in Soft mode.\n
							// Try medium mode or change location");
							verification = -1;
							return;
						}
					} else if (k == i && k2 == j + length - 1) {
						if (!m_elementTable[k][k2].contentEquals("") && !m_elementTable[k][k2].contentEquals("ES")
								&& !m_elementTable[k][k2].contentEquals("ES_I")
								&& !m_elementTable[k][k2].contentEquals("ES_T")
								&& !m_elementTable[k][k2].contentEquals("ES_D")) {
							// System.out.println(
							// "The platform is over an existing element\n you can't place another platform
							// on it in soft\n Try medium or Hard mode");
							verification = -1;
							return;
						} else if (m_elementTable[k - 1][k2].contentEquals("IW")
								|| m_elementTable[k][k2 + 1].contentEquals("IW")
								|| m_elementTable[k - 1][k2].contentEquals("OW_N")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
								|| m_elementTable[k - 1][k2].contentEquals("OW_S")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
								|| m_elementTable[k - 1][k2].contentEquals("OW_E")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
								|| m_elementTable[k - 1][k2].contentEquals("OW_W")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
								|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
								|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
								|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
								|| m_elementTable[k - 1][k2].contentEquals("OW_SW")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_SW")) {
							// System.out.println(
							// "There is a block next to your platform.\n You cannot add it in Soft mode.\n
							// Try medium mode or change location");
							verification = -1;
							return;
						}
					} else if (k == i + width - 1 && k2 == j) {
						if (!m_elementTable[k][k2].contentEquals("") && !m_elementTable[k][k2].contentEquals("ES")
								&& !m_elementTable[k][k2].contentEquals("ES_I")
								&& !m_elementTable[k][k2].contentEquals("ES_T")
								&& m_elementTable[k][k2].contentEquals("ES_D")) {
							// System.out.println(
							// "The platform is over an existing element\n you can't place another platform
							// on it in soft\n Try medium or Hard mode");
							verification = -1;
							return;
						} else if (m_elementTable[k + 1][k2].contentEquals("IW")
								|| m_elementTable[k][k2 - 1].contentEquals("IW")
								|| m_elementTable[k + 1][k2].contentEquals("OW_N")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
								|| m_elementTable[k + 1][k2].contentEquals("OW_S")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
								|| m_elementTable[k + 1][k2].contentEquals("OW_E")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
								|| m_elementTable[k + 1][k2].contentEquals("OW_W")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
								|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
								|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
								|| m_elementTable[k + 1][k2].contentEquals("OW_SE")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
								|| m_elementTable[k + 1][k2].contentEquals("OW_SW")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_SW")) {
							// System.out.println(
							// "There is a block next to your platform.\n You cannot add it in Soft mode.\n
							// Try medium mode or change location");
							verification = -1;
							return;
						}
					} else if (k == i + width - 1 && k2 == j + length - 1) {
						if (!m_elementTable[k][k2].contentEquals("") && m_elementTable[k][k2].contentEquals("ES")
								&& m_elementTable[k][k2].contentEquals("ES_I")
								&& m_elementTable[k][k2].contentEquals("ES_T")
								&& m_elementTable[k][k2].contentEquals("ES_D")) {
							// System.out.println(
							// "The platform is over an existing element\n you can't place another platform
							// on it in soft\n Try medium or Hard mode");
							verification = -1;
							return;
						} else if (m_elementTable[k + 1][k2].contentEquals("IW")
								|| m_elementTable[k][k2 + 1].contentEquals("IW")
								|| m_elementTable[k + 1][k2].contentEquals("OW_N")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
								|| m_elementTable[k + 1][k2].contentEquals("OW_S")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
								|| m_elementTable[k + 1][k2].contentEquals("OW_E")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
								|| m_elementTable[k + 1][k2].contentEquals("OW_W")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
								|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
								|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
								|| m_elementTable[k + 1][k2].contentEquals("OW_SE")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
								|| m_elementTable[k + 1][k2].contentEquals("OW_SW")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_SW")) {
							// System.out.println(
							// "There is a block next to your platform.\n You cannot add it in Soft mode.\n
							// Try medium mode or change location");
							verification = -1;
							return;
						}
					} else if (k == i) {
						if (!m_elementTable[k][k2].contentEquals("") && !m_elementTable[k][k2].contentEquals("ES")
								&& !m_elementTable[k][k2].contentEquals("ES_I")
								&& !m_elementTable[k][k2].contentEquals("ES_T")
								&& !m_elementTable[k][k2].contentEquals("ES_D")) {
							// System.out.println(
							// "The platform is over an existing element\n you can't place another platform
							// on it in soft\n Try medium or Hard mode");
							verification = -1;
							return;
						} else if (m_elementTable[k - 1][k2].contentEquals("IW")
								|| m_elementTable[k - 1][k2].contentEquals("OW_N")
								|| m_elementTable[k - 1][k2].contentEquals("OW_S")
								|| m_elementTable[k - 1][k2].contentEquals("OW_E")
								|| m_elementTable[k - 1][k2].contentEquals("OW_W")
								|| m_elementTable[k - 1][k2].contentEquals("OW_NE")
								|| m_elementTable[k - 1][k2].contentEquals("OW_NW")
								|| m_elementTable[k - 1][k2].contentEquals("OW_SE")
								|| m_elementTable[k - 1][k2].contentEquals("OW_SW")) {
							// System.out.println(
							// "There is a block next to your platform.\n You cannot add it in Soft mode.\n
							// Try medium mode or change location");
							verification = -1;
							return;
						}
					} else if (k == i + width - 1) {
						if (!m_elementTable[k][k2].contentEquals("") && !m_elementTable[k][k2].contentEquals("ES")
								&& !m_elementTable[k][k2].contentEquals("ES_I")
								&& !m_elementTable[k][k2].contentEquals("ES_T")
								&& !m_elementTable[k][k2].contentEquals("ES_D")) {
							// System.out.println(
							// "The platform is over an existing element\n you can't place another platform
							// on it in soft\n Try medium or Hard mode");
							verification = -1;
							return;
						} else if (m_elementTable[k + 1][k2].contentEquals("IW")
								|| m_elementTable[k + 1][k2].contentEquals("OW_N")
								|| m_elementTable[k + 1][k2].contentEquals("OW_S")
								|| m_elementTable[k + 1][k2].contentEquals("OW_E")
								|| m_elementTable[k + 1][k2].contentEquals("OW_W")
								|| m_elementTable[k + 1][k2].contentEquals("OW_NE")
								|| m_elementTable[k + 1][k2].contentEquals("OW_NW")
								|| m_elementTable[k + 1][k2].contentEquals("OW_SE")
								|| m_elementTable[k + 1][k2].contentEquals("OW_SW")) {
							// System.out.println(
							// "There is a block next to your platform.\n You cannot add it in Soft mode.\n
							// Try medium mode or change location");
							verification = -1;
							return;
						}
					} else if (k2 == j) {
						if (!m_elementTable[k][k2].contentEquals("") && !m_elementTable[k][k2].contentEquals("ES")
								&& !m_elementTable[k][k2].contentEquals("ES_I")
								&& !m_elementTable[k][k2].contentEquals("ES_T")
								&& !m_elementTable[k][k2].contentEquals("ES_D")) {
							// System.out.println(
							// "The platform is over an existing element\n you can't place another platform
							// on it in soft\n Try medium or Hard mode");
							verification = -1;
							return;
						} else if (m_elementTable[k][k2 - 1].contentEquals("IW")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_N")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_S")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_E")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_W")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_NE")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_NW")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_SE")
								|| m_elementTable[k][k2 - 1].contentEquals("OW_SW")) {
							// System.out.println(
							// "There is a block next to your platform.\n You cannot add it in Soft mode.\n
							// Try medium mode or change location");
							verification = -1;
							return;
						}
					} else if (k2 == j + length - 1) {
						if (!m_elementTable[k][k2].contentEquals("") && !m_elementTable[k][k2].contentEquals("ES")
								&& !m_elementTable[k][k2].contentEquals("ES_I")
								&& m_elementTable[k][k2].contentEquals("ES_T")
								&& m_elementTable[k][k2].contentEquals("ES_D")) {
							// System.out.println(
							// "The platform is over an existing element\n you can't place another platform
							// on it in soft\n Try medium or Hard mode");
							verification = -1;
							return;
						} else if (m_elementTable[k][k2 + 1].contentEquals("IW")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_N")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_S")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_E")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_W")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_NE")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_NW")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_SE")
								|| m_elementTable[k][k2 + 1].contentEquals("OW_SW")) {
							// System.out.println(
							// "There is a block next to your platform.\n You cannot add it in Soft mode.\n
							// Try medium mode or change location");
							verification = -1;
							return;
						}
					}
				}
				if (verification == 0) {
					for (int k3 = i; k3 < i + width; k3++) {
						for (int k4 = j; k4 < j + length; k4++) {
							if (k3 == i && k4 == j) {
								m_elementTable[k3 - 1][k4] = "ES_T";
								m_elementTable[k3][k4] = "OW_NW";
							} else if (k3 == i && k4 == j + length - 1) {
								m_elementTable[k3 - 1][k4] = "ES_T";
								m_elementTable[k3][k4] = "OW_NE";
							} else if (k3 == i + width - 1 && k4 == j) {
								m_elementTable[k3][k4] = "OW_SW";
							} else if (k3 == i + width - 1 && k4 == j + length - 1) {
								m_elementTable[k3][k4] = "OW_SE";
							} else if (k3 == i) {
								m_elementTable[k3 - 1][k4] = "ES_T";
								m_elementTable[k3][k4] = "OW_N";
							} else if (k3 == i + width - 1) {
								m_elementTable[k3][k4] = "OW_S";
							} else if (k4 == j) {
								m_elementTable[k3][k4] = "OW_W";
							} else if (k4 == j + length - 1) {
								m_elementTable[k3][k4] = "OW_E";
							} else {
								m_elementTable[k3][k4] = "IW";
							}
						}

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
		for (int k = 1; k < m_row-1; k++) {
			for (int k2 = 1; k2 < m_col-1; k2++) {
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
								|| m_elementTable[k + 1][k2].contentEquals("OW_SW"))){
					m_elementTable[k][k2] = "OW_E";
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

	public void AddInitialpoint() {
		System.out.println("Enter the i position of the initial point :");
		int i = enterNb();
		System.out.println("Enter the j position of the initial point :");
		int j = enterNb();
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

	public void AddDoor() {
		System.out.println("Enter the i position of the door:");
		int i = enterNb();
		System.out.println("Enter the j position of the door :");
		int j = enterNb();
		if (m_elementTable[i][j].contentEquals("ES") || m_elementTable[i][j].contentEquals("ES_T")) {
			m_elementTable[i][j] = "ES_D";
		} else {
			System.out.println("La case selectionnée ne convient pas");
		}
	}

	/*
	 * 
	 * This method is used to get a quick view of the blocks and platform you've
	 * already put
	 * 
	 */

	public void QuickPreview() {
		System.out.println("Quick review of the room :");
		System.out.print(
				"Table :\n-' . ' is an Empty space or a Decor\n-' I ' is the initial point\n-' D ' is the door\n-' X ' is a wall\n-' : ' is a unidentified cell\n-' / ' is NW\n-' \\ ' is NW\n-' L ' is SW\n-' J ' is SE\n");
		System.out.print(" /  ");
		for (int i = 0; i < m_col; i++) {
			if (i >= 10) {
				System.out.print(i + " ");
			} else {
				System.out.print(" " + i + " ");
			}
		}
		System.out.print("\n");
		for (int i = 0; i < m_row; i++) {
			if (i < 10) {
				System.out.print(" " + i + "  ");
			} else if (i >= 10) {
				System.out.print(" " + i + " ");
			}
			for (int j = 0; j < m_col; j++) {
				if (m_elementTable[i][j].contentEquals("ES") || m_elementTable[i][j].contentEquals("ES_T")) {
					System.out.print(" . ");
				} else if (m_elementTable[i][j].contentEquals("ES_I")) {
					System.out.print(" I ");
				} else if (m_elementTable[i][j].contentEquals("ES_D")) {
					System.out.print(" D ");
				} else if (m_elementTable[i][j].contentEquals("OW_N")) {
					System.out.print(" ^ ");
				} else if (m_elementTable[i][j].contentEquals("OW_S")) {
					System.out.print(" v ");
				} else if (m_elementTable[i][j].contentEquals("OW_E")) {
					System.out.print(" > ");
				} else if (m_elementTable[i][j].contentEquals("OW_W")) {
					System.out.print(" < ");
				} else if (m_elementTable[i][j].contentEquals("OW_NE")) {
					System.out.print(" \\ ");
				} else if (m_elementTable[i][j].contentEquals("OW_NW")) {
					System.out.print(" / ");
				} else if (m_elementTable[i][j].contentEquals("OW_SE")) {
					System.out.print(" J ");
				} else if (m_elementTable[i][j].contentEquals("OW_SW")) {
					System.out.print(" L ");
				} else if (m_elementTable[i][j].contentEquals("IW")) {
					System.out.print(" X ");
				} else {
					System.out.print(" : ");
				}
			}
			System.out.print("\n");
		}
	}

	/*
	 * 
	 * This method destroys a platform at the given position
	 * 
	 */

	public void zoneDestruction() {
		System.out.println("Enter i of the position you want to destroy :");
		int i = enterNb();
		System.out.println("Enter j of the position you want to destroy :");
		int j = enterNb();
		System.out.println("Enter width of the area to destroy :");
		int width = enterNb();
		System.out.println("Enter length of the area to destroy");
		int length = enterNb();
		for (int k = i; k < i + width; k++) {
			for (int k2 = j; k2 < j + length; k2++) {
				m_elementTable[k][k2] = "ES";
			}
		}

	}

	/*
	 * 
	 * This method changes one block
	 * 
	 */

	public void changeBlock() {
		int i = -1;
		int j = -1;
		while ((i < 0 || i > m_row) && (j < 0 || j > m_col)) {
			System.out.println("Enter i row (need to fit in the map):");
			i = enterNb();
			System.out.println("Enter j col (need to fit in the map):");
			j = enterNb();
		}
		String blockType = "";
		while (!blockType.contentEquals("ES") && !blockType.contentEquals("ES_I") && !blockType.contentEquals("ES_D")
				&& !blockType.contentEquals("ES_T") && !blockType.contentEquals("IW")
				&& !blockType.contentEquals("OW_N") && !blockType.contentEquals("OW_S")
				&& !blockType.contentEquals("OW_E") && !blockType.contentEquals("OW_W")
				&& !blockType.contentEquals("OW_NE") && !blockType.contentEquals("OW_NW")
				&& !blockType.contentEquals("OW_SE") && !blockType.contentEquals("OW_SW")) {
			System.out.println("Enter new block type :");
			System.out.println("Possible blockTypes are :");
			System.out.println("IW, ES, ES_I, ES_D, ES_T, OW_N, OW_S, OW_E, OW_W, OW_NE, OW_NW, OW_SE, OW_SW");
			blockType = enterTxt();
		}
		m_elementTable[i][j] = blockType;
	}

	/*
	 * 
	 * This method is used to create a menu to select the different method the
	 * player would use
	 * 
	 */

	public static void Menu() throws IOException {
		File f = null;
		int row = 0;
		int col = 0;
		int state = 0;
		RoomGenerator roomGen = null;
		while (state == 0) {
			System.out.println("Menu Display :");
			System.out.println("-'new' to generate a new map with the desired size");
			System.out.println("-'edit' to edit an existing map");
			System.out.println("-'e' to create a map full of empty spaces");
			System.out.println("-'i' to set the position of the initial point");
			System.out.println("-'d' to set the position of the door");
			System.out.println("-'p--s' to create a new platform in Soft mode (no platforms around)");
			System.out.println(
					"-'p--h' to create a new platform in Hard mode (without checking what is behind/around it)");
			System.out.println("-'p--o' to add a new platform in Optimise mode (making automatic ajustement)");
			System.out.println("-'chb' to change a specific bloc");
			System.out.println("-'b' to finish a room by adding it's border");
			System.out.println("-'des' to change an area of the map to empty spaces");
			System.out.println("-'s' to save the current room as a text file");
			System.out.println("-'qr' to get a Quick Preview of the room");
			System.out.println("-'q' to quit the room generator");
			System.out.print("\n\n");
			System.out.println("Enter an option :");
			String str = enterTxt();
			if (str.contentEquals("new")) {
				f = getFile();
				row = 0;
				col = 0;
				while (row <= 0 || col <= 0) {
					row = enterRow();
					col = enterCol();
					if (row <= 0) {
						System.out.println("A room need at least 1 row");
					} else if (col <= 0) {
						System.out.println("A room need at least 1 col");
					}
				}
				roomGen = new RoomGenerator(row, col);
				System.out.println(
						"The new room has been created with the size : " + roomGen.m_row + ":" + roomGen.m_col);
				enterTxt();
			} else if (str.contentEquals("edit")) {
				f = openFile();
				row = getRow(f);
				col = getCol(f);
				roomGen = new RoomGenerator(row, col);
				roomGen.m_elementTable = roomGen.extractor(f);
				if (roomGen.m_elementTable != null) {
					System.out.println("The File is correctly openned");
				} else {
					System.out.println("The file was open incorrectly");
					f = null;
					row = 0;
					col = 0;
					roomGen = null;
				}
				enterTxt();
			} else if (str.contentEquals("des")) {
				if (f == null || roomGen == null) {
					System.out.println("No room are currently edited");
				}
				roomGen.zoneDestruction();
				System.out.println("The area has been cleaned");
				enterTxt();
			} else if (str.contentEquals("chb")) {
				if (f == null || roomGen == null) {
					System.out.println("No room are currently edited");
				}
				roomGen.changeBlock();
				System.out.println("The given block has been changed");
				enterTxt();
			} else if (str.contentEquals("e")) {
				if (f == null || roomGen == null) {
					System.out.println("No room are currently edited");
				}
				roomGen.emptyMapGenerator();
				System.out.println("The room has been filled up with empty spaces");
				enterTxt();
			} else if (str.contentEquals("i")) {
				if (f == null || roomGen == null) {
					System.out.println("No room are currently edited");
				}
				roomGen.AddInitialpoint();
				System.out.println("AddInitialpoint Done");
				enterTxt();
			} else if (str.contentEquals("d")) {
				if (f == null || roomGen == null) {
					System.out.println("No room are currently edited");
				}
				roomGen.AddDoor();
				System.out.println("AddDoor done");
				enterTxt();
			} else if (str.contentEquals("p--s")) {
				if (f == null || roomGen == null) {
					System.out.println("No room are currently edited");
				}
				roomGen.AddPlatformSoft();
				System.out.print("AddPlatform mode Soft done");
				enterTxt();
			} else if (str.contentEquals("p--h")) {
				if (f == null || roomGen == null) {
					System.out.println("No room are currently edited");
				}
				roomGen.AddPlatformHard();
				System.out.print("AddPlatform mode Hard done");
				enterTxt();
			} else if (str.contentEquals("p--o")) {
				if (f == null || roomGen == null) {
					System.out.println("No room are currently edited");
				}
				roomGen.AddPlatformOptimise();
				System.out.print("AddPlatform mode Optimise done");
				enterTxt();
			} else if (str.contentEquals("b")) {
				if (f == null || roomGen == null) {
					System.out.println("No room are currently edited");
				}
				roomGen.AddCompleteBorder();
				System.out.println("AddBorder done");
				enterTxt();
			} else if (str.contentEquals("s")) {
				if (f == null || roomGen == null) {
					System.out.println("No room are currently edited");
				}
				roomGen.updateTextDocument(f);
				System.out.println("The room has been saved in the Sample folder");
				enterTxt();
			} else if (str.contentEquals("qr")) {
				if (f == null || roomGen == null) {
					System.out.println("No room are currently edited");
				}
				roomGen.QuickPreview();
				enterTxt();
			} else if (str.contains("q")) {
				System.out.println("Are you sure you want to quit? y/n");
				String rep = enterTxt();
				if (rep.contentEquals("y")) {
					state = 1;
					System.out.println("End of use");
					System.out.println("Goodbye");
				} else if (rep.contentEquals("n")) {
					System.out.println("Lets continue then !");
				} else {
					System.out.println("I didn't get your answer, so lets continue !");
				}
			} else {
				System.out.println("The following option doesn't exist");
				enterTxt();
			}
		}
	}

	/*
	 * 
	 * This method updates the text document based of the elementTable
	 * 
	 */

	public void updateTextDocument(File f) throws IOException {
		BufferedWriter writer = CreateNewWriter(f);
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
	 * This method open an existing file with the given name
	 * 
	 */

	public static File openFile() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter new FileName");
		String fileName = scan.nextLine();
		File f1 = new File("resources/Room/Sample/" + fileName + ".sample");
		while (!f1.isFile()) {
			System.out.println("File not found");
			System.out.println("Enter new FileName");
			fileName = scan.nextLine();
			f1 = new File("resources/Room/Sample/" + fileName + ".sample");
		}
		return f1;
	}

	/*
	 * 
	 * This method extract the element from each lines of the document to get the
	 * room set up
	 * 
	 */

	public String[][] extractor(File f) throws IOException {
		BufferedReader reader = CreateNewReader(f);
		reader.readLine();
		for (int i = 0; i < m_row; i++) {
			String line = reader.readLine();
			if (line == null || line == "") {
				System.out.println("File is smaller than expected");
				reader.close();
				return null;
			}
			String[] lineTable = line.split("/");
			if (lineTable.length != m_col) {
				System.out.println("Error in the extraction : dimensions do not correpond");
				reader.close();
				return null;
			}
			for (int j = 0; j < m_col; j++) {
				m_elementTable[i][j] = lineTable[j];
			}
		}
		reader.close();
		return m_elementTable;
	}

	/*
	 * 
	 * This method allow to create of File of the given name. If the name is already
	 * used, a new name is asked
	 * 
	 */

	public static File getFile() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter new FileName");
		String fileName = scan.nextLine();
		File f1 = new File("resources/Room/Sample/" + fileName + ".sample");
		while (f1.isFile()) {
			System.out.println("Already used");
			System.out.println("Enter new FileName");
			fileName = scan.nextLine();
			f1 = new File("resources/Room/Sample/" + fileName + ".sample");
		}
		return f1;
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

	/*
	 * 
	 * This method return the number of row given by the user
	 * 
	 */

	public static int enterRow() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter row number");
		String fileName = scan.nextLine();
		return Integer.parseInt(fileName);
	}

	/*
	 * 
	 * This method return the number of col given by the user
	 * 
	 */

	public static int enterCol() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter col number");
		String fileName = scan.nextLine();
		return Integer.parseInt(fileName);
	}

	/*
	 * 
	 * This method reads the corresponding file to get the number of rows
	 * 
	 */

	public static int getRow(File f) throws IOException {
		BufferedReader reader = CreateNewReader(f);
		String line = reader.readLine();
		String[] lineTable = line.split(":");
		reader.close();
		return Integer.parseInt(lineTable[0]);
	}

	/*
	 * 
	 * This method reads the corresponding file to get the number of cols
	 * 
	 */

	public static int getCol(File f) throws IOException {
		BufferedReader reader = CreateNewReader(f);
		String line = reader.readLine();
		String[] lineTable = line.split(":");
		reader.close();
		return Integer.parseInt(lineTable[1]);
	}

	/*
	 * 
	 * This method return an integer given by the user;
	 * 
	 */

	public static int enterNb() {
		Scanner scan = new Scanner(System.in);
		String nbString = scan.nextLine();
		return Integer.parseInt(nbString);
	}

	/*
	 * 
	 * This method return a String given by the user;
	 * 
	 */

	public static String enterTxt() {
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		return name;
	}

	public static void main(String[] args) throws IOException {
		Menu();
	}

}
