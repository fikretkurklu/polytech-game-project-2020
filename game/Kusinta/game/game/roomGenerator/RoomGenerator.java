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
	 * This method is used to create a room with borders with a borderSize equal to the size give
	 * This function will put empty spaces if the room was empty in the middle, if not, it only adds the border
	 * 
	 */
	
	public void AddCompleteBorder() throws IOException {
		System.out.println("Enter the boderSize :");
		int borderSize = enterNb();
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				if (i<borderSize-1 || j<borderSize-1 || i>m_row-borderSize || j>m_col-borderSize || (i==borderSize-1 && j==borderSize-1) || (i==borderSize-1 && j==m_col-borderSize) || (i==m_row-borderSize && j==borderSize-1) || (i==m_row-borderSize && j == m_col-borderSize)) {
					m_elementTable[i][j] = "IW";
				} else if (i == borderSize-1 && j > borderSize-1 && j< m_col-borderSize) {
					m_elementTable[i][j] = "OW_S";
				} else if (i == m_row-borderSize && j > borderSize-1 && j< m_col-borderSize) {
					m_elementTable[i][j] = "OW_N";
				} else if (j == borderSize-1 && i >borderSize-1 && i < m_row-borderSize) {
					m_elementTable[i][j] = "OW_E";
				} else if (j == m_col - borderSize && i >borderSize-1 && i < m_row-borderSize) {
					m_elementTable[i][j] = "OW_W";
				} else {
					if (m_elementTable[i][j]=="") {
						m_elementTable[i][j] = "ES";
					}
				}
			}
		}
		
	}
	
	/*
	 * 
	 * This method add a platform at the i, j position, with the size and the width given.
	 * This width and the length must be at least bigger or equal to 2 (to have sprites corresponding for the platform)
	 * The platform will be added at this position without checking what is at this position 
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
			for (int k = i; k < i+width; k++) {
				for (int k2 = j; k2 < j+length; k2++) {
					if (k == i && k2 == j) {
						m_elementTable[k][k2]="OW_NW";
					} else if (k==i && k2 == j+length-1) {
						m_elementTable[k][k2]="OW_NE";
					} else if (k ==i+width-1 && k2==j) {
						m_elementTable[k][k2]="OW_SW";
					} else if (k==i+width-1 && k2==j+length-1) {
						m_elementTable[k][k2]="OW_SE";
					} else if (k==i) {
						m_elementTable[k][k2] = "OW_N";
					} else if (k==i+width-1) {
						m_elementTable[k][k2] = "OW_S";
					} else if (k2==j) {
						m_elementTable[k][k2] = "OW_W";
					} else if (k2==j+length-1) {
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
	 * This method updates the text document based of the elementTable
	 * 
	 */
	
	public void updateTextDocument(File f) throws IOException {
		BufferedWriter writer = CreateNewWriter(f);
		writer.write(m_row+":"+m_col+"\n");
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				writer.write(m_elementTable[i][j]+"/");
			}
			writer.write("\n");
		}
		writer.close();
	}
	
	/*
	 * 
	 * This method allow to create of File of the given name.
	 * If the name is already used, a new name is asked
	 * 
	 */
	
	public static File getFile() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter new FileName");
		String fileName = scan.nextLine();
		File f1 = new File("resources/Room/Sample/"+fileName+".sample");
		while (f1.isFile()) {
			System.out.println("Already used");
			System.out.println("Enter new FileName");
			fileName = scan.nextLine();
			f1 = new File("resources/Room/Sample/"+fileName+".sample");
		}
		return f1;
	}
	
	/*
	 * 
	 * This method creates a new BufferedWriter that is used to write in the text document
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
	 * This method is used to create a new BufferedReader that is used to read the text document
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
		roomGen.AddCompleteBorder();
		roomGen.AddPlatformHard();
		roomGen.updateTextDocument(f);
	}

}
