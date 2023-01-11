// import sekcija
import java_cup.runtime.*;
%%

// sekcija opcija i deklaracija
%class MPLexer

%cup

%line
%column

%eofval{
return new Symbol( sym.EOF);
%eofval}

%{
   public int getLine()
   {
      return yyline;
   }
%}

//stanja
%xstate KOMENTAR
//makroi
slovo = [a-zA-Z]
octCifra = [0-7]
decCifra = [0-9]
hexCifra = [0-9A-F]

%%

// pravila
%% { yybegin( KOMENTAR ); }
<KOMENTAR>~"%%" { yybegin( YYINITIAL ); }

[\t\n\r ] { ; }
\( { return new Symbol( sym.LEFTPAR ); }
\) { return new Symbol( sym.RIGHTPAR); }
\{ {return new Symbol(sym.LEFTBRA); }
\} {return new Symbol(sym.RIGHTBRA); }

//operatori
\+ { return new Symbol( sym.PLUS); }
- { return new Symbol( sym.MINUS); }
= { return new Symbol( sym.ASSIGN); }

//separatori
; { return new Symbol( sym.SEMICOLON); }
: { return new Symbol( sym.COLON); }
, { return new Symbol( sym.COMMA); }

//kljucne reci
"main" { return new Symbol(sym.MAIN); }
"int" { return new Symbol(sym.INT); }
"char" { return new Symbol(sym.CHAR); }
"float" { return new Symbol(sym.FLOAT); }
"case" { return new Symbol(sym.CASE); }
"when" { return new Symbol(sym.WHEN); }

//identifikatori
({slovo}|_)({slovo}|{decCifra}|_)* { return new Symbol(sym.ID, yytext()); }

//konstante
// int konstane
(0oct){octCifra}+ { return new Symbol(sym.INTCONST, new Integer(Integer.parseInt( yytext().substring(4), 8 )) ); }
(0hex){hexCifra}+ { return new Symbol(sym.INTCONST, new Integer(Integer.parseInt( yytext().substring(4), 16 )) ); }

// float konstante
{decCifra}+\.{decCifra}*(E(\+|\-){decCifra}+)? { return new Symbol(sym.FLOATCONST, new Float(Float.parseFloat(yytext()))); }

//int dec konstante
(0dec){decCifra}+ { return new Symbol(sym.INTCONST, new Integer(Integer.parseInt(yytext().substring(4))) ); }
{decCifra}+ { return new Symbol(sym.INTCONST, new Integer(Integer.parseInt(yytext())) ); }

// char konstante
'[^]' { return new Symbol(sym.CHARCONST, new Character(yytext().charAt(1))); }

//obrada gresaka
. { if (yytext() != null && yytext().length() > 0) System.out.println( "ERROR: " + yytext() ); }
