package edu.sbcc.cs107;

/**
 * @author Xiao Li CS 107: Disassembler Project
 * 
 *         This code implements the disassembler as well as pulling apart the
 *         Hex file. The hex file format is documented at
 *         http://www.keil.com/support/docs/1584/
 */
public class Disassembler {
	/**
	 * After implementing toBinaryString(),if the data is less than 16 bits,
	 * pattern it with "0" before it until it is 16 bits.
	 * 
	 * @param hw
	 * @return
	 */
	public String getRightBinary(Halfword hw) {
		String dataInBinary = Integer.toBinaryString(hw.getData());
		StringBuilder db = new StringBuilder(dataInBinary);
		for (int i = 0; i < 16 - dataInBinary.length(); i++) {
			db.insert(0, "0");
		}
		return db.toString();
	}


	/**
	 * Extracts the register operand from a halfword.
	 * 
	 * The register operand (e.g. r0) is used by many mnemonics and is embedded
	 * in the data halfword. It position is specified by the least significant
	 * bit and most significant bit. This value is extracted and concatenated
	 * with "r" to give us the desired register.
	 * 
	 * @param hw
	 *            Halfword that contains the machine code data.
	 * @param lsBitPosition
	 *            Encoded register value (LSB)
	 * @param msBitPosition
	 *            Encoded register value (MSB)
	 * @return Register field designation (e.g. r1)
	 */

	public String getRegister(Halfword hw, int lsBitPosition, int msBitPosition) {
		String dataB = getRightBinary(hw);
		StringBuilder s = new StringBuilder(dataB);
		s = s.reverse();
		String numberStringForR = new StringBuilder(s.substring(lsBitPosition,
				msBitPosition + 1)).reverse().toString();
		int valueForR = Integer.parseInt(numberStringForR, 2);
		return "r" + valueForR;
	}


	/**
	 * Extracts the immediate operand from a halfword.
	 * 
	 * Same as the getRegister function but returns the embedded immediate value
	 * (e.g. #4).
	 * 
	 * @param hw
	 *            Halfword that contains the machine code data.
	 * @param lsBitPosition
	 *            Encoded immediate value (LSB)
	 * @param msBitPosition
	 *            Encoded immediate value (MSB)
	 * @return Immediate field designation (e.g. #12)
	 */
	public String getImmediate(Halfword hw, int lsBitPosition, int msBitPosition) {
		String dataB = getRightBinary(hw);
		StringBuilder s = new StringBuilder(dataB);
		s = s.reverse();
		String numberStringForIm = new
				StringBuilder(s.substring(lsBitPosition,
						msBitPosition + 1)).reverse().toString();
		int immediateValue = Integer.parseInt(numberStringForIm, 2);
		return "#" + immediateValue;
	}


	/**
	 * Returns a formatted string consisting of the Mnemonic and Operands for
	 * the given halfword.
	 * 
	 * The halfword is decoded into its corresponding mnemonic and any optional
	 * operands. The return value is a formatted string with an 8 character wide
	 * field for the mnemonic (left justified) a single space and then any
	 * operands.
	 * 
	 * @param hw
	 *            Halfword that contains the machine code data.
	 * @return
	 */
	public String dissassembleToString(Halfword hw) {
		String dataB = getRightBinary(hw);
		String ds = null;
		switch (dataB.substring(0, 10)) {
		case "0100000101":
			ds = "ADCS     " + getRegister(hw, 0, 2) + ", " + getRegister(hw,
					3, 5);
			break;
		case "0100001011":
			ds = "CMN      " + getRegister(hw, 0, 2) + ", " + getRegister(hw,
					3, 5);
			break;
		case "0000000000":
			ds = "MOVS     " + getRegister(hw, 0, 2) + ", " + getRegister(hw, 3,
					5);
			break;
		case "1011101000":
			ds = "REV      " + getRegister(hw, 0, 2) + ", " + getRegister(hw, 3,
					5);
			break;
		}
		switch (dataB.substring(0, 7)) {
		case "0001110":
			ds = "ADDS     " + getRegister(hw, 0, 2) + ", " + getRegister(hw,
					3, 5) + ", " + getImmediate(hw, 6, 8);
			break;
		case "0101011":
			ds = "LDRSB    " + getRegister(hw, 0, 2) + ", " + "[" +
					getRegister(hw,
							3, 5) + ", " + getRegister(hw, 6, 8) + "]";
			break;
		}
		switch (dataB.substring(0, 5)) {
		case "00100":
			ds = "MOVS     " + getRegister(hw, 8, 10) + ", " + getImmediate(hw,
					0,
					7);
			break;
		case "11100":
			ds = "B        " + ".";
			break;
		}
		return ds;
	}
}
