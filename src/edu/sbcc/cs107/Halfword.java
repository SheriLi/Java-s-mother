package edu.sbcc.cs107;

/**
 * @author Xiao Li
 * CS 107: Disassembler Project
 *
 * This class is used to model a half-word of an object file. Each half-word must have an address as well as a data
 * value that can be disassembled into mnemonics and optional operands.
 * 
 * Note that the half-word is 16 bits but we are using a Java int which is typically 32 bits. Be sure to take that into
 * account when working with it.
 *
 */
public class Halfword {
	private int address;
	private int data;
	
	/**
	 * Constructor for a halfword.
	 * 
	 * @param address
	 * @param data
	 */
	public Halfword(int address, int data) {
		this.address = address;
		this.data = data;
	}
	
	/** 
	 * toString method.
	 * 
	 * The format for the halfword is a hex value 8 characters wide (address), a single space, and a hex
	 * value four characters wide (data).
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	String	hexAddress=Integer.toHexString(address).toUpperCase();
	StringBuilder a = new StringBuilder(hexAddress);
	for(int i=0;i<8-hexAddress.length();i++) {
		a.insert(0, "0");
	}
	String hexData=Integer.toHexString(data).toUpperCase();
	StringBuilder d= new StringBuilder(hexData);
	for(int i=0;i<4-hexData.length();i++) {
		d.insert(0, "0");
	}
		return a.toString()+" "+d.toString();
	}

	/**
	 * Get the address of the half-word.
	 * 
	 * @return
	 */
	public int getAddress() {
		return this.address;
	}
	
	/**
	 * Get the data of the half-word.
	 * 
	 * @return
	 */
	public int getData() {
		return this.data;
	}

}
