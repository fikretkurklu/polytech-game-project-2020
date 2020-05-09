package game.roomGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AutomaticRoomGenerator {

	File file;
	String[][] m_elementTable;
	int m_row;
	int m_col;

	public AutomaticRoomGenerator() {
		this.getFile();
		m_elementTable = new String[0][0];
	}

	/*
	 * This method is creating a table of empty spaces which creates an empty this
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
	 * This method is used to add borders to the this of the given size This method
	 * should be invoked last after you added every element in the this The added
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
	 * This method checks the this and add the required changes
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
	 * This method sets the door of the this at the given position The door cannot
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

	public void changeBlock(int i, int j, String blockType) {
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

	public void getFile() {
		File f1 = new File("resources/Room/Sample/room1.sample");
		file = f1;
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

	public void leftElevator1(int row, int col) {
		AddPlatformHard(9 + row, 3 + col, 2, 4);
		AddPlatformHard(7 + row, 8 + col, 2, 2);
		AddPlatformHard(5 + row, 2 + col, 2, 3);
		AddPlatformHard(4 + row, 2 + col, 2, 2);
		AddPlatformHard(3 + row, 1 + col, 2, 2);
		AddPlatformHard(2 + row, 0 + col, 2, 2);
		AddPlatformHard(-1 + row, 3 + col, 2, 4);
		int rand = (int) (Math.random() * 2) + 1;
		switch (rand) {
		case (1):
			changeBlock(8 + row, 5 + col, "ES_WO");
			break;
		case (2):
			break;
		}
	}

	public void leftElevator2(int row, int col) {
		AddPlatformHard(9 + row, 3 + col, 2, 4);
		AddPlatformHard(8 + row, 7 + col, 2, 3);
		AddPlatformHard(6 + row, 8 + col, 2, 2);
		AddPlatformHard(5 + row, 4 + col, 2, 2);
		AddPlatformHard(3 + row, 0 + col, 2, 4);
		AddPlatformHard(2 + row, 0 + col, 2, 2);
		AddPlatformHard(-1 + row, 3 + col, 2, 4);
		int rand = (int) (Math.random() * 2) + 1;
		switch (rand) {
		case (1):
			changeBlock(8 + row, 4 + col, "ES_WO");
			break;
		case (2):
			break;
		}
	}

	public void leftElevator3(int row, int col) {
		AddPlatformHard(9 + row, 3 + col, 2, 4);
		AddPlatformHard(8 + row, 6 + col, 2, 2);
		AddPlatformHard(7 + row, 7 + col, 2, 2);
		AddPlatformHard(5 + row, 3 + col, 2, 2);
		AddPlatformHard(3 + row, 1 + col, 3, 2);
		AddPlatformHard(2 + row, 0 + col, 2, 2);
		AddPlatformHard(-1 + row, 3 + col, 2, 4);
	}

	public void rightElevator1(int row, int col) {
		AddPlatformHard(9 + row, 6 + col, 2, 4);
		AddPlatformHard(6 + row, 0 + col, 2, 4);
		AddPlatformHard(7 + row, 7 + col, 2, 3);
		AddPlatformHard(5 + row, 0 + col, 2, 2);
		AddPlatformHard(2 + row, 3 + col, 2, 2);
		AddPlatformHard(1 + row, 4 + col, 2, 2);
		AddPlatformHard(0 + row, 5 + col, 2, 2);
		AddPlatformHard(-1 + row, 6 + col, 2, 4);
	}

	public void rightElevator2(int row, int col) {
		AddPlatformHard(9 + row, 6 + col, 2, 4);
		AddPlatformHard(7 + row, 8 + col, 2, 2);
		AddPlatformHard(6 + row, 0 + col, 2, 5);
		AddPlatformHard(3 + row, 6 + col, 2, 4);
		AddPlatformHard(2 + row, 0 + col, 2, 4);
		AddPlatformHard(0 + row, 1 + col, 2, 2);
		AddPlatformHard(-1 + row, 6 + col, 2, 4);
	}

	public void rightElevator3(int row, int col) {
		AddPlatformHard(9 + row, 6 + col, 2, 4);
		AddPlatformHard(7 + row, 7 + col, 2, 3);
		AddPlatformHard(5 + row, 0 + col, 2, 6);
		AddPlatformHard(1 + row, 4 + col, 2, 6);
		AddPlatformHard(-1 + row, 6 + col, 2, 4);
		changeBlock(4 + row, 3 + col, "ES_WO");
		AddPlatformHard(4 + row, 0 + col, 2, 2);
	}

	public void midPlatform1(int row, int col) {
		AddPlatformHard(7 + row, 0 + col, 3, 10);
		int rand = (int) (Math.random() * 2) + 1;
		switch (rand) {
		case (1):
			changeBlock(6 + row, 3 + col, "ES_WO");
			break;
		case (2):
			changeBlock(6 + row, 7 + col, "ES_WO");
			break;
		default:
			changeBlock(6 + row, 3 + col, "ES_WO");
		}
	}

	public void midPlatform2(int row, int col) {
		AddPlatformHard(7 + row, 0 + col, 3, 2);
		AddPlatformHard(6 + row, 3 + col, 3, 2);
		AddPlatformHard(7 + row, 6 + col, 3, 4);
		int rand = (int) (Math.random() * 2) + 1;
		switch (rand) {
		case (1):
			changeBlock(4 + row, 2 + col, "ES_FO");
			changeBlock(6 + row, 7 + col, "ES_WO");
			break;
		case (2):
			changeBlock(4 + row, 6 + col, "ES_FO");
			changeBlock(6 + row, 7 + col, "ES_WO");
			break;
		default:
			changeBlock(4 + row, 2 + col, "ES_FO");
		}
	}

	public void midPlatform3(int row, int col) {
		AddPlatformHard(8 + row, 0 + col, 2, 3);
		AddPlatformHard(8 + row, 7 + col, 2, 3);
		AddPlatformHard(6 + row, 3 + col, 3, 4);
		AddPlatformHard(3 + row, 0 + col, 2, 2);
		AddPlatformHard(3 + row, 8 + col, 2, 2);
		changeBlock(3 + row, 4 + col, "ES_FO");
		changeBlock(5 + row, 3 + col, "ES_WO");
	}

	public void midPlatform4(int row, int col) {
		AddPlatformHard(8 + row, 0 + col, 2, 2);
		AddPlatformHard(8 + row, 8 + col, 2, 2);
		AddPlatformHard(7 + row, 7 + col, 2, 2);
		AddPlatformHard(7 + row, 1 + col, 2, 2);
		AddPlatformHard(6 + row, 2 + col, 2, 2);
		AddPlatformHard(6 + row, 6 + col, 2, 2);
		AddPlatformHard(5 + row, 3 + col, 2, 4);
		int rand = (int) (Math.random() * 2) + 1;
		switch (rand) {
		case (1):
			changeBlock(2 + row, 2 + col, "ES_FO");
			changeBlock(4 + row, 5 + col, "ES_WO");
			break;
		case (2):
			changeBlock(2 + row, 7 + col, "ES_FO");
			changeBlock(4 + row, 5 + col, "ES_WO");
			break;
		default:
			changeBlock(4 + row, 2 + col, "ES_FO");
		}
	}

	public void midPlatform5(int row, int col) {
		AddPlatformHard(8 + row, 2 + col, 2, 6);
		AddPlatformHard(7 + row, 1 + col, 2, 2);
		AddPlatformHard(7 + row, 7 + col, 2, 2);
		AddPlatformHard(6 + row, 0 + col, 2, 2);
		AddPlatformHard(5 + row, 8 + col, 3, 2);
		AddPlatformHard(3 + row, 3 + col, 2, 4);
		int rand = (int) (Math.random() * 2) + 1;
		switch (rand) {
		case (1):
			changeBlock(3 + row, 4 + col, "ES_FO");
			changeBlock(7 + row, 5 + col, "ES_WO");
			break;
		case (2):
			changeBlock(2 + row, 5 + col, "ES_FO");
			changeBlock(7 + row, 5 + col, "ES_WO");
			break;
		default:
			changeBlock(2 + row, 3 + col, "ES_FO");
		}
	}

	public void midPlatform6(int row, int col) {
		AddPlatformHard(8 + row, 0 + col, 2, 10);
		AddPlatformHard(6 + row, 6 + col, 2, 2);
		AddPlatformHard(4 + row, 1 + col, 2, 4);
		int rand = (int) (Math.random() * 2) + 1;
		switch (rand) {
		case (1):
			changeBlock(7 + row, 2 + col, "ES_WO");
			break;
		case (2):
			changeBlock(3 + row, 3 + col, "ES_WO");
			break;
		default:
			changeBlock(7 + row, 2 + col, "ES_WO");
		}
	}

	public void initial1(int row, int col) {
		AddPlatformHard(7 + row, 0 + col, 3, 10);
		AddInitialpoint(6 + row, 5 + col);
	}

	public void initial2(int row, int col) {
		AddPlatformHard(7 + row, 0 + col, 3, 2);
		AddPlatformHard(6 + row, 3 + col, 3, 2);
		AddPlatformHard(7 + row, 6 + col, 3, 4);
		AddInitialpoint(5 + row, 3 + col);
	}

	public void initial3(int row, int col) {
		AddPlatformHard(8 + row, 0 + col, 2, 3);
		AddPlatformHard(8 + row, 7 + col, 2, 3);
		AddPlatformHard(6 + row, 3 + col, 3, 4);
		AddPlatformHard(3 + row, 0 + col, 2, 2);
		AddPlatformHard(3 + row, 8 + col, 2, 2);
		AddInitialpoint(5 + row, 5 + col);
	}

	public void initial4(int row, int col) {
		AddPlatformHard(8 + row, 0 + col, 2, 2);
		AddPlatformHard(8 + row, 8 + col, 2, 2);
		AddPlatformHard(7 + row, 7 + col, 2, 2);
		AddPlatformHard(7 + row, 1 + col, 2, 2);
		AddPlatformHard(6 + row, 2 + col, 2, 2);
		AddPlatformHard(6 + row, 6 + col, 2, 2);
		AddPlatformHard(5 + row, 3 + col, 2, 4);
		AddInitialpoint(4 + row, 5 + col);
	}

	public void initial5(int row, int col) {
		AddPlatformHard(8 + row, 2 + col, 2, 6);
		AddPlatformHard(7 + row, 1 + col, 2, 2);
		AddPlatformHard(7 + row, 7 + col, 2, 2);
		AddPlatformHard(6 + row, 0 + col, 2, 2);
		AddPlatformHard(5 + row, 8 + col, 3, 2);
		AddPlatformHard(3 + row, 3 + col, 2, 4);
		AddInitialpoint(7 + row, 4 + col);
	}

	public void initial6(int row, int col) {
		AddPlatformHard(8 + row, 0 + col, 2, 10);
		AddPlatformHard(6 + row, 6 + col, 2, 2);
		AddPlatformHard(4 + row, 1 + col, 2, 4);
		AddInitialpoint(3 + row, 1 + col);
	}

	public void door1(int row, int col) {
		AddPlatformHard(7 + row, 0 + col, 3, 10);
		changeBlock(6 + row, 3 + col, "ES_WO");
		changeBlock(6 + row, 7 + col, "ES_WO");
		AddDoor(6 + row, 5 + col);
	}

	public void door2(int row, int col) {
		AddPlatformHard(7 + row, 0 + col, 3, 2);
		AddPlatformHard(6 + row, 3 + col, 3, 2);
		AddPlatformHard(7 + row, 6 + col, 3, 4);
		changeBlock(4 + row, 6 + col, "ES_FO");
		changeBlock(6 + row, 7 + col, "ES_WO");
		AddDoor(5 + row, 3 + col);
	}

	public void door3(int row, int col) {
		AddPlatformHard(8 + row, 0 + col, 2, 3);
		AddPlatformHard(8 + row, 7 + col, 2, 3);
		AddPlatformHard(6 + row, 3 + col, 3, 4);
		AddPlatformHard(3 + row, 0 + col, 2, 2);
		AddPlatformHard(3 + row, 8 + col, 2, 2);
		changeBlock(4 + row, 4 + col, "ES_FO");
		changeBlock(4 + row, 5 + col, "ES_WO");
		AddDoor(5 + row, 5 + col);
	}

	public void door4(int row, int col) {
		AddPlatformHard(8 + row, 0 + col, 2, 2);
		AddPlatformHard(8 + row, 8 + col, 2, 2);
		AddPlatformHard(7 + row, 7 + col, 2, 2);
		AddPlatformHard(7 + row, 1 + col, 2, 2);
		AddPlatformHard(6 + row, 2 + col, 2, 2);
		AddPlatformHard(6 + row, 6 + col, 2, 2);
		AddPlatformHard(5 + row, 3 + col, 2, 4);
		changeBlock(2 + row, 7 + col, "ES_FO");
		changeBlock(4 + row, 5 + col, "ES_WO");
		AddDoor(4 + row, 4 + col);
	}

	public void door5(int row, int col) {
		AddPlatformHard(8 + row, 2 + col, 2, 6);
		AddPlatformHard(7 + row, 1 + col, 2, 2);
		AddPlatformHard(7 + row, 7 + col, 2, 2);
		AddPlatformHard(5 + row, 0 + col, 3, 2);
		AddPlatformHard(6 + row, 8 + col, 2, 2);
		AddPlatformHard(3 + row, 3 + col, 2, 4);
		changeBlock(2 + row, 4 + col, "ES_FO");
		changeBlock(7 + row, 5 + col, "ES_WO");
		AddDoor(7 + row, 4 + col);
	}

	public void door6(int row, int col) {
		AddPlatformHard(8 + row, 0 + col, 2, 10);
		AddPlatformHard(6 + row, 6 + col, 2, 2);
		AddPlatformHard(4 + row, 1 + col, 2, 4);
		changeBlock(7 + row, 2 + col, "ES_WO");
		changeBlock(3 + row, 3 + col, "ES_WO");
		AddDoor(3 + row, 1 + col);
	}

	/*
	 * 
	 * This method automatically generates a this with a random size, a random start
	 * and a random door
	 * 
	 */

	public void AutomaticGeneration() throws IOException {
		int row = (int) (Math.random() * (6 - 3 + 1)) + 3;
		int col = (int) (Math.random() * (10 - 3 + 1)) + 3;
		m_row = row * 10 + 6;
		m_col = col * 10 + 6;
		m_elementTable = new String[m_row][m_col];

		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				m_elementTable[i][j] = "";
			}
		}

		int Elevator = 1;
		int leftElevatorCol = 0;
		int rightElevatorCol = -1;
		int midElevatorCol = -1;
		if (col > 3 && col <= 8) {
			Elevator = 2;
		} else if (col > 8) {
			Elevator = 3;
		}

		if (Elevator == 2) {
			if (col == 5 || col == 6) {
				leftElevatorCol = (int) (Math.random() * 2);
				rightElevatorCol = (int) (Math.random() * 2) + (col - 3);
			} else if (col > 6) {
				leftElevatorCol = (int) (Math.random() * 3);
				rightElevatorCol = (int) (Math.random() * 3) + (col - 4);
			} else {
				rightElevatorCol = col - 1;
			}
		} else if (Elevator == 3) {
			leftElevatorCol = (int) (Math.random() * 2);
			rightElevatorCol = (int) (Math.random() * 2) + (col - 3);
			int middle = col / 2;
			midElevatorCol = (int) (Math.random() * 3) + (middle - 1);
		}

		int initialPositionRow = (int) (Math.random() * (row));
		int initialPositionCol = (int) (Math.random() * (col));
		while (initialPositionCol == leftElevatorCol || initialPositionCol == rightElevatorCol
				|| initialPositionCol == midElevatorCol) {
			initialPositionCol = (int) (Math.random() * (col));
		}

		int initialDoorRow = (int) (Math.random() * (row));
		int initialDoorCol = (int) (Math.random() * (col));
		while (initialDoorCol == leftElevatorCol || initialDoorCol == rightElevatorCol
				|| initialDoorCol == midElevatorCol
				|| (initialDoorCol == initialPositionCol && initialDoorRow == initialPositionRow)
				|| (initialDoorCol == initialPositionCol - 1 && initialDoorRow == initialPositionRow)
				|| (initialDoorCol == initialPositionCol + 1 && initialDoorRow == initialPositionRow)
				|| (initialDoorCol == initialPositionCol && initialDoorRow == initialPositionRow - 1)
				|| (initialDoorCol == initialPositionCol && initialDoorRow == initialPositionRow + 1)) {
			initialDoorRow = (int) (Math.random() * (row));
			initialDoorCol = (int) (Math.random() * (col));
		}

		this.emptyMapGenerator();
		for (int i = row - 1; i >= 0; i -= 1) {
			for (int j = col - 1; j >= 0; j -= 1) {
				if (j == leftElevatorCol) {
					int randomElevator = (int) (Math.random() * 2) + 1;
					switch (randomElevator) {
					case (1):
						this.leftElevator2(i * 10 + 3, j * 10 + 3);
						break;
					case (2):
						this.leftElevator3(i * 10 + 3, j * 10 + 3);
						break;
					}
				} else if (j == midElevatorCol) {
					int randomElevator = (int) (Math.random() * 2) + 1;
					switch (randomElevator) {
					case (1):
						this.leftElevator2(i * 10 + 3, j * 10 + 3);
						break;
					case (2):
						this.leftElevator3(i * 10 + 3, j * 10 + 3);
						break;
					}
				} else if (j == rightElevatorCol) {
					int randomElevator = (int) (Math.random() * 2) + 1;
					switch (randomElevator) {
					case (1):
						this.rightElevator1(i * 10 + 3, j * 10 + 3);
						break;
					case (2):
						this.rightElevator3(i * 10 + 3, j * 10 + 3);
						break;
					}
				} else if (j == initialDoorCol && i == initialDoorRow) {
					int randomDoor = (int) (Math.random() * 6) + 1;
					switch (randomDoor) {
					case (1):
						this.door1(i * 10 + 3, j * 10 + 3);
						break;
					case (2):
						this.door2(i * 10 + 3, j * 10 + 3);
						break;
					case (3):
						this.door3(i * 10 + 3, j * 10 + 3);
						break;
					case (4):
						this.door4(i * 10 + 3, j * 10 + 3);
						break;
					case (5):
						this.door5(i * 10 + 3, j * 10 + 3);
						break;
					case (6):
						this.door6(i * 10 + 3, j * 10 + 3);
						break;
					}
				} else if (j == initialPositionCol && i == initialPositionRow) {
					int randomInitial = (int) (Math.random() * 6) + 1;
					switch (randomInitial) {
					case (1):
						this.initial1(i * 10 + 3, j * 10 + 3);
						break;
					case (2):
						this.initial2(i * 10 + 3, j * 10 + 3);
						break;
					case (3):
						this.initial3(i * 10 + 3, j * 10 + 3);
						break;
					case (4):
						this.initial4(i * 10 + 3, j * 10 + 3);
						break;
					case (5):
						this.initial5(i * 10 + 3, j * 10 + 3);
						break;
					case (6):
						this.initial6(i * 10 + 3, j * 10 + 3);
						break;
					}
				} else {
					int randomMid = (int) (Math.random() * 6) + 1;
					switch (randomMid) {
					case (1):
						this.midPlatform1(i * 10 + 3, j * 10 + 3);
						break;
					case (2):
						this.midPlatform2(i * 10 + 3, j * 10 + 3);
						break;
					case (3):
						this.midPlatform3(i * 10 + 3, j * 10 + 3);
						break;
					case (4):
						this.midPlatform4(i * 10 + 3, j * 10 + 3);
						break;
					case (5):
						this.midPlatform5(i * 10 + 3, j * 10 + 3);
						break;
					case (6):
						this.midPlatform6(i * 10 + 3, j * 10 + 3);
						break;
					}
				}
			}
		}
		this.AddCompleteBorder(3);
		this.verification();
		this.updateTextDocument();
	}

	/*
	 * Generate a boss room
	 * 
	 */

	public void bossRoomGenerator() throws IOException {
		m_row = 23;
		m_col = 28;
		m_elementTable = new String[m_row][m_col];

		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				m_elementTable[i][j] = "";
			}
		}
		AddPlatformHard(3 + 15, 18 + 3, 2, 5);
		AddPlatformHard(3 + 14, 18 + 3, 2, 5);
		AddPlatformHard(3 + 13, 19 + 3, 2, 4);
		AddPlatformHard(3 + 12, 20 + 3, 2, 3);
		AddPlatformHard(3 + 11, 21 + 3, 2, 2);

		AddPlatformHard(3 + 15, -1 + 3, 2, 5);
		AddPlatformHard(3 + 14, -1 + 3, 2, 5);
		AddPlatformHard(3 + 13, -1 + 3, 2, 4);
		AddPlatformHard(3 +	12, -1 + 3, 2, 3);
		AddPlatformHard(3 + 11, -1 + 3, 2, 2);

		changeBlock(9 + 3, 9 + 3, "OW_N");
		changeBlock(9 + 3, 12 + 3, "OW_N");
		changeBlock(8 + 3, 16 + 3, "OW_N");
		changeBlock(8 + 3, 5 + 3, "OW_N");
		changeBlock(8 + 3, 1 + 3 , "OW_N");
		changeBlock(8 + 3, 20 + 3, "OW_N");
		changeBlock(7 + 3, 19 + 3, "OW_N");
		changeBlock(7 + 3, 2 + 3, "OW_N");

		changeBlock(14 + 3, 6 + 3, "ES_WO");
		changeBlock(14 + 3, 9 + 3, "ES_WO");

		changeBlock(5 + 3, 8 + 3, "ES_B");

		this.AddCompleteBorder(3);
		this.verification();
		AddInitialpoint(6 + 5, 9 + 3);
		this.updateTextDocument();
	}
}
