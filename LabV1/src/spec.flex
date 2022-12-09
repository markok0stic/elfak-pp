// import sekcija

%%

// sekcija opcija i deklaracija
%class MPLexer
%function next_token
%line
%column
%debug
%type Yytoken

%eofval{
return new Yytoken( sym.EOF, null, yyline, yycolumn);
%eofval}


%{
//dodatni clanovi generisane klase
KWTable kwTable = new KWTable();
Yytoken getKW()
{
	return new Yytoken( kwTable.find( yytext() ),
	yytext(), yyline, yycolumn );
}
%}

//stanja
%xstate KOMENTAR
//makroi
slovo = [a-zA-Z]
cifra = [0-9]
oct = 0oct
hex = 0hex
dec = 0dec
%%

// pravila
\%\% { yybegin( KOMENTAR ); }
<KOMENTAR>~\%\% { yybegin( YYINITIAL ); }

[\t\n\r ] { ; }
\( { return new Yytoken( sym.LEFTPAR, yytext(), yyline, yycolumn ); }
\) { return new Yytoken( sym.RIGHTPAR, yytext(), yyline, yycolumn ); }
\{ { return new Yytoken( sym.LEFTCURLYPAR, yytext(), yyline, yycolumn ); }
\} { return new Yytoken( sym.RIGHTCURLYPAR, yytext(), yyline, yycolumn ); }

//operatori
\+ { return new Yytoken( sym.PLUS,yytext(), yyline, yycolumn ); }
\- { return new Yytoken( sym.MINUS,yytext(), yyline, yycolumn ); }
\= { return new Yytoken( sym.ASSIGN, yytext(), yyline, yycolumn ); }

//separatori
; { return new Yytoken( sym.SEMICOLON, yytext(), yyline, yycolumn ); }
: { return new Yytoken( sym.COLON, yytext(), yyline, yycolumn ); }
, { return new Yytoken( sym.COMMA, yytext(), yyline, yycolumn ); }
# { return new Yytoken( sym.HASHTAG, yytext(), yyline, yycolumn ); }


//kljucne reci
{slovo}+ { return getKW(); }

//identifikatori
({slovo}|_)({slovo}|{cifra}|_)* { return new Yytoken(sym.ID, yytext(),yyline, yycolumn ); }

//konstante
({hex}|{oct}|{dec}|""*){cifra}+ { return new Yytoken( sym.CONST, yytext(), yyline, yycolumn ); }
{cifra}+\.{cifra}+(E(\+|\-){cifra}+) { return new Yytoken( sym.CONST, yytext(), yyline, yycolumn ); }
\’.\’ { return new Yytoken( sym.CONST,yytext(), yyline, yycolumn ); }

//obrada gresaka
. { if (yytext() != null && yytext().length() > 0) System.out.println( "ERROR: " + yytext() ); }
