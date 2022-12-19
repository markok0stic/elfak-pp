// import sekcija
import java_cup.runtime.*;

%%
// sekcija deklaracija
%class MPLexer

%cup

%line
%column

%eofval{
	return new Symbol( sym.EOF );
%eofval}

//stanja
%xstate KOMENTAR
//macros
slovo = [a-zA-Z]
cifra = [0-9]
oct = 0oct
hex = 0hex
dec = 0dec
%%

// rules section
\*\*			{ yybegin( KOMENTAR ); }
<KOMENTAR>\*\)	{ yybegin( YYINITIAL ); }
<KOMENTAR>~"**" { yybegin( YYINITIAL ); }

[\t\r\n ]		{ ; }
\( { return new Symbol( sym.LEFTPAR );  }
\) { return new Symbol( sym.RIGHTPAR );  }
\{ { return new Symbol( sym.LEFTCURLYPAR );  }
\} { return new Symbol( sym.RIGHTCURLYPAR );  }

//operatori
\+				{ return new Symbol( sym.PLUS ); }
\-				{ return new Symbol( sym.MINUS );  }
\=				{ return new Symbol( sym.ASSIGN );  }

//separatori
\;				{ return new Symbol( sym.SEMICOLON );	}
\,				{ return new Symbol( sym.COMMA );	}
\:				{ return new Symbol( sym.COLON ); }

//kljucne reci
main			{ return new Symbol( sym.MAIN );	}
int		    { return new Symbol( sym.INT );	}
char			{ return new Symbol( sym.CHAR );	}
float			{ return new Symbol( sym.FLOAT );	}
case			{ return new Symbol( sym.CASE );	}
when			{ return new Symbol( sym.WHEN );	}

//konstante
({hex}|{oct}|{dec}|""*){cifra}+ { return new Symbol( sym.CONST ); }
{cifra}+\.{cifra}+(E(\+|\-){cifra}+) { return new Symbol( sym.CONST ); }
\’.\’ { return new Symbol( sym.CONST ); }

//identifikatori
({slovo}|_)({slovo}|{cifra}|_)* { return new Symbol( sym.ID ); }

//obrada greske
.		{ System.out.println( "ERROR: " + yytext() ); }

