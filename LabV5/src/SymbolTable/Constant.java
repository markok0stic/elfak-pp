package SymbolTable;

public class Constant {
	public Type type;
	public Object value;
	public boolean isChar;

	public Constant( Type constType, Object constValue)
	{
		type = constType;
		value = constValue;
		this.isChar = false;
	}

	public Constant( Type constType, Object constValue, boolean isChar)
	{
		type = constType;
		value = constValue;
		this.isChar = isChar;
	}
}
