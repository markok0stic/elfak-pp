package AST;

import SymbolTable.Variable;

import java.io.BufferedWriter;
import java.io.IOException;

public class VariableExpression 
extends Expression {
	private Variable targetVar;
	
	public VariableExpression( Variable var )
	{
		targetVar = var;
	}
	
	public void translate( BufferedWriter out )
	throws IOException
	{
		this.result = targetVar.name;
	}
}
