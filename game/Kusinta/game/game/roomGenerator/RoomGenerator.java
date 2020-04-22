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
	public static String[] outeWallDictionnary = { "OW_N", "OW_S", "OW_E", "OW_W", "OW_NE", "OW_NW", "OW_SE", "OW_SW" };
	public static String[] emptySpaceDictionnary = { "ES_T", "ES_I", "ES_D", "ES" };

	public RoomGenerator(int row, int col) {
		m_row = row;
		m_col = col;
		m_elementTable = new String[row][col];
	}

	public RoomGenerator(String[][] elementTable, int row, int col) {
		m_elementTable = elementTable;
		m_row = row;
		m_col = col;
	}

	/*
	 * This method is creating a table of empty spaces which creates an empty room
	 * 
	 */

	public void emptyMapGenerator() throws IOException {
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				m_elementTable[i][j] = "ES";
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
					if (m_elementTable[i + 1][j] == "OW_N" || m_elementTable[i + 1][j] == "OW_NE"
							|| m_elementTable[i + 1][j] == "OW_NW") {
						m_elementTable[i + 1][j] = "IW";
						m_elementTable[i][j] = "IW";
					} else if (m_elementTable[i + 1][j] == "OW_E" || m_elementTable[i + 1][j] == "OW_W"
							|| m_elementTable[i + 1][j] == "OW_S" || m_elementTable[i + 1][j] == "IW"
							|| m_elementTable[i + 1][j] == "OW_SE" || m_elementTable[i + 1][j] == "OW_SW") {
						m_elementTable[i][j] = "IW";
					} else {
						m_elementTable[i][j] = "OW_S";
					}
				} else if (i == m_row - borderSize && j > borderSize - 1 && j < m_col - borderSize) {
					if (m_elementTable[i - 1][j] == "OW_S" || m_elementTable[i - 1][j] == "OW_SW"
							|| m_elementTable[i - 1][j] == "OW_SE") {
						m_elementTable[i - 1][j] = "IW";
						m_elementTable[i][j] = "IW";
					} else if (m_elementTable[i - 1][j] == "OW_E" || m_elementTable[i - 1][j] == "OW_W"
							|| m_elementTable[i - 1][j] == "OW_N" || m_elementTable[i - 1][j] == "IW"
							|| m_elementTable[i - 1][j] == "OW_NE" || m_elementTable[i - 1][j] == "OW_NW") {
						m_elementTable[i][j] = "IW";
					} else {
						m_elementTable[i][j] = "OW_N";
					}
				} else if (j == borderSize - 1 && i > borderSize - 1 && i < m_row - borderSize) {
					if (m_elementTable[i][j + 1] == "OW_W" || m_elementTable[i][j + 1] == "OW_NW"
							|| m_elementTable[i][j + 1] == "OW_SW") {
						m_elementTable[i][j + 1] = "IW";
						m_elementTable[i][j] = "IW";
					} else if (m_elementTable[i][j + 1] == "OW_E" || m_elementTable[i][j + 1] == "OW_S"
							|| m_elementTable[i][j + 1] == "OW_N" || m_elementTable[i][j + 1] == "IW"
							|| m_elementTable[i][j + 1] == "OW_NE" || m_elementTable[i][j + 1] == "OW_SE") {
						m_elementTable[i][j] = "IW";
					} else {
						m_elementTable[i][j] = "OW_E";
					}
				} else if (j == m_col - borderSize && i > borderSize - 1 && i < m_row - borderSize) {
					if (m_elementTable[i][j - 1] == "OW_E" || m_elementTable[i][j - 1] == "OW_SE"
							|| m_elementTable[i][j - 1] == "OW_NE") {
						m_elementTable[i][j - 1] = "IW";
						m_elementTable[i][j] = "IW";
					} else if (m_elementTable[i][j - 1] == "OW_W" || m_elementTable[i][j - 1] == "OW_S"
							|| m_elementTable[i][j - 1] == "OW_N" || m_elementTable[i][j - 1] == "IW"
							|| m_elementTable[i][j - 1] == "OW_NW" || m_elementTable[i][j - 1] == "OW_SW") {
						m_elementTable[i][j] = "IW";
					} else {
						m_elementTable[i][j] = "OW_W";
					}
				} else {
					if (i == m_row - borderSize - 1 && j > borderSize - 1 && j < m_col - borderSize) {
						if (m_elementTable[i][j] == "ES" || m_elementTable[i][j] == "") {
							m_elementTable[i][j] = "ES_T";
						}
					}
					if (m_elementTable[i][j] == "") {
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
						if (m_elementTable[k][k2] != "" && m_elementTable[k][k2] != "ES"
								&& m_elementTable[k][k2] != "ES_I" && m_elementTable[k][k2] == "ES_T"
								&& m_elementTable[k][k2] == "ES_D") {
							System.out.println(
									"The platform is over an existing element\n you can't place another platform on it in soft\n Try medium or Hard mode");
							verification = -1;
							break;
						} else if (m_elementTable[k - 1][k2] == "IW" || m_elementTable[k][k2 - 1] == "IW"
								|| m_elementTable[k - 1][k2] == "OW_N" || m_elementTable[k][k2 - 1] == "OW_N"
								|| m_elementTable[k - 1][k2] == "OW_S" || m_elementTable[k][k2 - 1] == "OW_S"
								|| m_elementTable[k - 1][k2] == "OW_E" || m_elementTable[k][k2 - 1] == "OW_E"
								|| m_elementTable[k - 1][k2] == "OW_W" || m_elementTable[k][k2 - 1] == "OW_W"
								|| m_elementTable[k - 1][k2] == "OW_NE" || m_elementTable[k][k2 - 1] == "OW_NE"
								|| m_elementTable[k - 1][k2] == "OW_NW" || m_elementTable[k][k2 - 1] == "OW_NW"
								|| m_elementTable[k - 1][k2] == "OW_SE" || m_elementTable[k][k2 - 1] == "OW_SE"
								|| m_elementTable[k - 1][k2] == "OW_SW" || m_elementTable[k][k2 - 1] == "OW_SW") {
							System.out.println(
									"There is a block next to your platform.\n You cannot add it in Soft mode.\n Try medium mode or change location");
							verification = -1;
							break;
						}
					} else if (k == i && k2 == j + length - 1) {
						if (m_elementTable[k][k2] != "" && m_elementTable[k][k2] != "ES"
								&& m_elementTable[k][k2] != "ES_I" && m_elementTable[k][k2] == "ES_T"
								&& m_elementTable[k][k2] == "ES_D") {
							System.out.println(
									"The platform is over an existing element\n you can't place another platform on it in soft\n Try medium or Hard mode");
							verification = -1;
							break;
						} else if (m_elementTable[k - 1][k2] == "IW" || m_elementTable[k][k2 + 1] == "IW"
								|| m_elementTable[k - 1][k2] == "OW_N" || m_elementTable[k][k2 + 1] == "OW_N"
								|| m_elementTable[k - 1][k2] == "OW_S" || m_elementTable[k][k2 + 1] == "OW_S"
								|| m_elementTable[k - 1][k2] == "OW_E" || m_elementTable[k][k2 + 1] == "OW_E"
								|| m_elementTable[k - 1][k2] == "OW_W" || m_elementTable[k][k2 + 1] == "OW_W"
								|| m_elementTable[k - 1][k2] == "OW_NE" || m_elementTable[k][k2 + 1] == "OW_NE"
								|| m_elementTable[k - 1][k2] == "OW_NW" || m_elementTable[k][k2 + 1] == "OW_NW"
								|| m_elementTable[k - 1][k2] == "OW_SE" || m_elementTable[k][k2 + 1] == "OW_SE"
								|| m_elementTable[k - 1][k2] == "OW_SW" || m_elementTable[k][k2 + 1] == "OW_SW") {
							System.out.println(
									"There is a block next to your platform.\n You cannot add it in Soft mode.\n Try medium mode or change location");
							verification = -1;
							break;
						}
					} else if (k == i + width - 1 && k2 == j) {
						if (m_elementTable[k][k2] != "" && m_elementTable[k][k2] != "ES"
								&& m_elementTable[k][k2] != "ES_I" && m_elementTable[k][k2] == "ES_T"
								&& m_elementTable[k][k2] == "ES_D") {
							System.out.println(
									"The platform is over an existing element\n you can't place another platform on it in soft\n Try medium or Hard mode");
							verification = -1;
							break;
						} else if (m_elementTable[k + 1][k2] == "IW" || m_elementTable[k][k2 - 1] == "IW"
								|| m_elementTable[k + 1][k2] == "OW_N" || m_elementTable[k][k2 - 1] == "OW_N"
								|| m_elementTable[k + 1][k2] == "OW_S" || m_elementTable[k][k2 - 1] == "OW_S"
								|| m_elementTable[k + 1][k2] == "OW_E" || m_elementTable[k][k2 - 1] == "OW_E"
								|| m_elementTable[k + 1][k2] == "OW_W" || m_elementTable[k][k2 - 1] == "OW_W"
								|| m_elementTable[k + 1][k2] == "OW_NE" || m_elementTable[k][k2 - 1] == "OW_NE"
								|| m_elementTable[k + 1][k2] == "OW_NW" || m_elementTable[k][k2 - 1] == "OW_NW"
								|| m_elementTable[k + 1][k2] == "OW_SE" || m_elementTable[k][k2 - 1] == "OW_SE"
								|| m_elementTable[k + 1][k2] == "OW_SW" || m_elementTable[k][k2 - 1] == "OW_SW") {
							System.out.println(
									"There is a block next to your platform.\n You cannot add it in Soft mode.\n Try medium mode or change location");
							verification = -1;
							break;
						}
					} else if (k == i + width - 1 && k2 == j + length - 1) {
						if (m_elementTable[k][k2] != "" && m_elementTable[k][k2] != "ES"
								&& m_elementTable[k][k2] != "ES_I" && m_elementTable[k][k2] == "ES_T"
								&& m_elementTable[k][k2] == "ES_D") {
							System.out.println(
									"The platform is over an existing element\n you can't place another platform on it in soft\n Try medium or Hard mode");
							verification = -1;
							break;
						} else if (m_elementTable[k + 1][k2] == "IW" || m_elementTable[k][k2 + 1] == "IW"
								|| m_elementTable[k + 1][k2] == "OW_N" || m_elementTable[k][k2 + 1] == "OW_N"
								|| m_elementTable[k + 1][k2] == "OW_S" || m_elementTable[k][k2 + 1] == "OW_S"
								|| m_elementTable[k + 1][k2] == "OW_E" || m_elementTable[k][k2 + 1] == "OW_E"
								|| m_elementTable[k + 1][k2] == "OW_W" || m_elementTable[k][k2 + 1] == "OW_W"
								|| m_elementTable[k + 1][k2] == "OW_NE" || m_elementTable[k][k2 + 1] == "OW_NE"
								|| m_elementTable[k + 1][k2] == "OW_NW" || m_elementTable[k][k2 + 1] == "OW_NW"
								|| m_elementTable[k + 1][k2] == "OW_SE" || m_elementTable[k][k2 + 1] == "OW_SE"
								|| m_elementTable[k + 1][k2] == "OW_SW" || m_elementTable[k][k2 + 1] == "OW_SW") {
							System.out.println(
									"There is a block next to your platform.\n You cannot add it in Soft mode.\n Try medium mode or change location");
							verification = -1;
							break;
						}
					} else if (k == i) {
						if (m_elementTable[k][k2] != "" && m_elementTable[k][k2] != "ES"
								&& m_elementTable[k][k2] != "ES_I" && m_elementTable[k][k2] == "ES_T"
								&& m_elementTable[k][k2] == "ES_D") {
							System.out.println(
									"The platform is over an existing element\n you can't place another platform on it in soft\n Try medium or Hard mode");
							verification = -1;
							break;
						} else if (m_elementTable[k - 1][k2] == "IW" || m_elementTable[k - 1][k2] == "OW_N"
								|| m_elementTable[k - 1][k2] == "OW_S" || m_elementTable[k - 1][k2] == "OW_E"
								|| m_elementTable[k - 1][k2] == "OW_W" || m_elementTable[k - 1][k2] == "OW_NE"
								|| m_elementTable[k - 1][k2] == "OW_NW" || m_elementTable[k - 1][k2] == "OW_SE"
								|| m_elementTable[k - 1][k2] == "OW_SW") {
							System.out.println(
									"There is a block next to your platform.\n You cannot add it in Soft mode.\n Try medium mode or change location");
							verification = -1;
							break;
						}
					} else if (k == i + width - 1) {
						if (m_elementTable[k][k2] != "" && m_elementTable[k][k2] != "ES"
								&& m_elementTable[k][k2] != "ES_I" && m_elementTable[k][k2] == "ES_T"
								&& m_elementTable[k][k2] == "ES_D") {
							System.out.println(
									"The platform is over an existing element\n you can't place another platform on it in soft\n Try medium or Hard mode");
							verification = -1;
							break;
						} else if (m_elementTable[k + 1][k2] == "IW" || m_elementTable[k + 1][k2] == "OW_N"
								|| m_elementTable[k + 1][k2] == "OW_S" || m_elementTable[k + 1][k2] == "OW_E"
								|| m_elementTable[k + 1][k2] == "OW_W" || m_elementTable[k + 1][k2] == "OW_NE"
								|| m_elementTable[k + 1][k2] == "OW_NW" || m_elementTable[k + 1][k2] == "OW_SE"
								|| m_elementTable[k + 1][k2] == "OW_SW") {
							System.out.println(
									"There is a block next to your platform.\n You cannot add it in Soft mode.\n Try medium mode or change location");
							verification = -1;
							break;
						}
					} else if (k2 == j) {
						if (m_elementTable[k][k2] != "" && m_elementTable[k][k2] != "ES"
								&& m_elementTable[k][k2] != "ES_I" && m_elementTable[k][k2] == "ES_T"
								&& m_elementTable[k][k2] == "ES_D") {
							System.out.println(
									"The platform is over an existing element\n you can't place another platform on it in soft\n Try medium or Hard mode");
							verification = -1;
							break;
						} else if (m_elementTable[k][k2 - 1] == "IW" || m_elementTable[k][k2 - 1] == "OW_N"
								|| m_elementTable[k][k2 - 1] == "OW_S" || m_elementTable[k][k2 - 1] == "OW_E"
								|| m_elementTable[k][k2 - 1] == "OW_W" || m_elementTable[k][k2 - 1] == "OW_NE"
								|| m_elementTable[k][k2 - 1] == "OW_NW" || m_elementTable[k][k2 - 1] == "OW_SE"
								|| m_elementTable[k][k2 - 1] == "OW_SW") {
							System.out.println(
									"There is a block next to your platform.\n You cannot add it in Soft mode.\n Try medium mode or change location");
							verification = -1;
							break;
						}
					} else if (k2 == j + length - 1) {
						if (m_elementTable[k][k2] != "" && m_elementTable[k][k2] != "ES"
								&& m_elementTable[k][k2] != "ES_I" && m_elementTable[k][k2] == "ES_T"
								&& m_elementTable[k][k2] == "ES_D") {
							System.out.println(
									"The platform is over an existing element\n you can't place another platform on it in soft\n Try medium or Hard mode");
							verification = -1;
							break;
						} else if (m_elementTable[k][k2 + 1] == "IW" || m_elementTable[k][k2 + 1] == "OW_N"
								|| m_elementTable[k][k2 + 1] == "OW_S" || m_elementTable[k][k2 + 1] == "OW_E"
								|| m_elementTable[k][k2 + 1] == "OW_W" || m_elementTable[k][k2 + 1] == "OW_NE"
								|| m_elementTable[k][k2 + 1] == "OW_NW" || m_elementTable[k][k2 + 1] == "OW_SE"
								|| m_elementTable[k][k2 + 1] == "OW_SW") {
							System.out.println(
									"There is a block next to your platform.\n You cannot add it in Soft mode.\n Try medium mode or change location");
							verification = -1;
							break;
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
		if (m_elementTable[i][j] == "ES" || m_elementTable[i][j] == "ES_T") {
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
		if (m_elementTable[i][j] == "ES" || m_elementTable[i][j] == "ES_T") {
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

	public void QuickReview() {
		System.out.println("Quick review of the room :");
		System.out.print(
				"Table :\n-' . ' is an Empty space or a Decor\n-' I ' is the initial point\n-' D ' is the door\n-' X ' is a wall\n-' : ' is a unidentified cell\n");
		for (int i = 0; i < m_col + 1; i++) {
			if (i == 0) {
				System.out.print(" " + i + "  ");
			} else if (i >= 10) {
				System.out.print(i + " ");
			} else {
				System.out.print(" " + i + " ");
			}
		}
		System.out.print("\n");
		for (int i = 0; i < m_row; i++) {
			if (i + 1 < 10) {
				System.out.print(" " + (i + 1) + "  ");
			} else if (i + 1 >= 10) {
				System.out.print(" " + (i + 1) + " ");
			}
			for (int j = 0; j < m_col; j++) {
				if (m_elementTable[i][j] == "ES" || m_elementTable[i][j] == "ES_T") {
					System.out.print(" . ");
				} else if (m_elementTable[i][j] == "ES_I") {
					System.out.print(" I ");
				} else if (m_elementTable[i][j] == "ES_D") {
					System.out.print(" D ");
				} else if (m_elementTable[i][j] == "IW" || m_elementTable[i][j] == "OW_N"
						|| m_elementTable[i][j] == "OW_S" || m_elementTable[i][j] == "OW_E"
						|| m_elementTable[i][j] == "OW_W" || m_elementTable[i][j] == "OW_NE"
						|| m_elementTable[i][j] == "OW_NW" || m_elementTable[i][j] == "OW_SE"
						|| m_elementTable[i][j] == "OW_SW") {
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
	 * This method return an integer given by the user;
	 * 
	 */

	public static int enterNb() {
		Scanner scan = new Scanner(System.in);
		String fileName = scan.nextLine();
		return Integer.parseInt(fileName);
	}

	public static void main(String[] args) throws IOException {
		File f = getFile();
		int row = enterRow();
		int col = enterCol();
		RoomGenerator roomGen = new RoomGenerator(row, col);

		roomGen.emptyMapGenerator();
		roomGen.AddPlatformHard();
		roomGen.AddDoor();
		roomGen.AddInitialpoint();
		roomGen.QuickReview();
		roomGen.updateTextDocument(f);
	}

}
