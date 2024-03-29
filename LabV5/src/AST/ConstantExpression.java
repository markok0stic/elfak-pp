package AST;

import SymbolTable.Constant;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConstantExpression 
extends Expression{
	private Constant targetConst;
	
	public ConstantExpression( Constant c )
	{
		targetConst = c;
	}
	
	public void translate( BufferedWriter out )
	throws IOException
	{
		this.result = targetConst.value.toString();
	}
	
	protected void genLoad( String reg, BufferedWriter out )
	throws IOException
	{
		out.write( "\tLoad_Const\t" + 
				reg + ", " + result );
		out.newLine();
	}
}
