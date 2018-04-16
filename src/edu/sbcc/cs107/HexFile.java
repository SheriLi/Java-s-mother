package edu.sbcc.cs107;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Xiao Li CS 107: Disassembler Project
 *
 *         This code implements working with a Hex file. The hex file format is
 *         documented at http://www.keil.com/support/docs/1584/
 */
public class HexFile {
	/**
	 * This is where you load the hex file. By making it an ArrayList you can
	 * easily traverse it in order.
	 */
	private ArrayList<String> hexFile = new ArrayList<>();
	private int type;
	private int length;
	private int address;
	private int i = 0;
	private int j = 9;


	/**
	 * Constructor that loads the .hex file.
	 * 
	 * @param hexFileName
	 * @throws IOException
	 */
	public HexFile(String hexFileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(hexFileName));
		String recordLine = br.readLine();
		while (recordLine != null) {
			hexFile.add(recordLine);
			recordLine = br.readLine();
		}
		br.close();
	}


	/**
	 * Pulls the length of the data bytes from an individual record.
	 * 
	 * This extracts the length of the data byte field from an individual hex
	 * record. This is referred to as LL->Record Length in the documentation.
	 * 
	 * @param Hex
	 *            file record (one line).
	 * @return record length.
	 */
	public int getDataBytesOfRecord(String record) {
		length = Integer.parseInt(record.substring(1, 3), 16);
		return length;
	}


	/**
	 * Get the starting address of the data bytes.
	 * 
	 * Extracts the starting address for the data. This tells you where the data
	 * bytes start and are referred to as AAAA->Address in the documentation.
	 * 
	 * @param Hex
	 *            file record (one line).
	 * @return Starting address of where the data bytes go.
	 */
	public int getAddressOfRecord(String record) {
		address = Integer.parseInt(record.substring(3, 7), 16);
		return address;
	}


	/**
	 * Gets the record type.
	 * 
	 * The record type tells you what the record can do and determines what
	 * happens to the data in the data field. This is referred to as DD->Data in
	 * the documentation.
	 * 
	 * @param Hex
	 *            file record (one line).
	 * @return Record type.
	 */
	public int getRecordType(String record) {
		type = Integer.parseInt(record.substring(7, 9));
		return type;
	}


	/**
	 * Returns the next halfword data byte.
	 * 
	 * This function will extract the next halfword from the Hex file. By
	 * repeatedly calling this function it will look like we are getting a
	 * series of halfwords. Behind the scenes we must parse the HEX file so that
	 * we are extracting the data from the data files as well as indicating the
	 * correct address. This requires us to handle the various record types.
	 * Some record types can effect the address only. These need to be processed
	 * and skipped. Only data from recordType 0 will result in something
	 * returned. When finished processing null is returned.
	 * 
	 * @return Next halfword.
	 */
	public Halfword getNextHalfword() {
		Halfword hf = null;

		while (hf == null) {

			String lines = hexFile.get(i);
			length = getDataBytesOfRecord(lines);
			type = getRecordType(lines);
			address = getAddressOfRecord(lines) + (j - 9) / 2;

			if (type == 00) {
				int data = Integer.parseInt(lines.substring(j + 2, j + 4) + lines.substring(j, j + 2), 16);
				hf = new Halfword(address, data);
				j = j + 4;

				if (j >= 9 + length * 2) {
					j = 9;
					i++;
				}
			}

			else {
				i++;
				j = 9;
			}
		}
		return hf;
	}
}
