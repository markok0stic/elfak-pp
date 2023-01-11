package SymbolTable;
// klasa za predstavljanje tabele simbola
public class SymbolTable {
	/*tabela simbola za "language scope"
	u ovom slucaju tu pripadaju samo tipovi*/
	private SymbolNode types;
	/* tabela simbola za oblast vazenja programa */
	private SymbolNode variables;
	private int scope;

	public SymbolTable( )
	{
		types = new Type( "char", Type.CHARACTER, null);
		types = new Type( "integer", Type.INTEGER, types );
		types = new Type("float", Type.FLOAT, types);
		types = new Type("unknown", Type.UNKNOWN, types);
		variables = null;
		scope = 0;
	}

	public boolean addVar( String name, Type type )
	{
		Variable existing = this.getVar( name );
		if ( existing != null && existing.scope == this.scope)
			return false;
		variables = new Variable( name, type, this.scope, variables );
		return true;
	}

	public Variable getVar( String name )
	{
		SymbolNode current = variables;
		while ( current != null && current.name.compareTo( name ) != 0)
			current = current.next;
		return ( Variable ) current;
	}

	public Type getType(String typeName)
	{
		SymbolNode current = types;
		while ( current != null && current.name.compareTo( typeName ) !=0)
			current = current.next;
		return ( Type ) current;
	}

	public SymbolNode getVariables()
	{
		return variables;
	}

	public void newScope()
	{
		this.scope++;
	}

	public void deleteScope()
	{
		SymbolNode current = variables;
		while (current != null && ((Variable)current).scope == this.scope)
		{
			variables = current.next;
			current = current.next;
		}
		this.scope--;
	}

	public int getScope()
	{
		return scope;
	}
}