import java.io.IOException;
import java.util.*;
public class LLSyntaxAnalisator {
    HashMap<String,Integer> lRows = new HashMap<>();
    HashMap<String,Integer> lCols = new HashMap<>();
    String[][] syntaxTable = new String[17][12];
    String[] shifts = new String[10];

    public LLSyntaxAnalisator() {
        lRows.put("CS", 0);
        lRows.put("WSL", 1);
        lRows.put("WSL'", 2);
        lRows.put("WS", 3);
        lRows.put("S'", 4);
        lRows.put("case", 5);
        lRows.put("(", 6);
        lRows.put(")", 7);
        lRows.put("ID", 8);
        lRows.put("{", 9);
        lRows.put("}", 10);
        lRows.put("when", 11);
        lRows.put("CONST", 12);
        lRows.put(":", 13);
        lRows.put("=", 14);
        lRows.put(";", 15);
        lRows.put("#", 16);


        lCols.put("case", 0);
        lCols.put("(", 1);
        lCols.put(")", 2);
        lCols.put("ID", 3);
        lCols.put("{", 4);
        lCols.put("}", 5);
        lCols.put("when", 6);
        lCols.put("CONST", 7);
        lCols.put(":", 8);
        lCols.put("=", 9);
        lCols.put(";", 10);
        lCols.put("#", 11);


        shifts[0] = "case ( ID ) { WSL }";
        shifts[1] = "WS WSL'";
        shifts[2] = "WS WSL'";
        shifts[3]= "";
        shifts[4] = "when CONST : S";
        shifts[5] = "CS";
        shifts[6] = "ID = ID ;";
        shifts[7] = "ID = CONST ;";

        for(int i=0; i<17; i++)
        {
            for(int j=0; j<12; j++)
            {
                syntaxTable[i][j] = "";
            }
        }
        for(int i=5; i<17;i++)
        {
            syntaxTable[i][i-5] = "pop";
        }

        syntaxTable[16][11] = "acc";

        syntaxTable[0][0] = "0";
        syntaxTable[1][6] = "1";
        syntaxTable[2][6] = "2";
        syntaxTable[2][5] = "3";
        syntaxTable[3][6] = "4";
        syntaxTable[4][0] = "5";
        syntaxTable[4][3] = "6";

        for(int i=0; i<17; i++)
        {
            for(int j=0; j<12; j++)
            {
                if(syntaxTable[i][j] == "")
                    syntaxTable[i][j] = "err";
            }
        }

        System.out.println(syntaxTable);
    }

    public String MatchShiftsFromTable(String top, Yytoken next)
    {
        String[] topS = top.split(" ");

        if(next.m_index == sym.ID) //ID
        {
            if(topS[0].equals("ID"))
            {
                return "pop";
            }
            return syntaxTable[lRows.get(top)][lCols.get("ID")];
        }
        if(next.m_index == sym.CONST) //CONST
        {
            if(topS[0].equals("CONST"))
                return "pop";
            else
                return syntaxTable[lRows.get(top)][lCols.get("CONST")];
        }
        if(topS[0].equals("#") && next.m_text.equals("#")) // for #
        {
            return "acc";
        }
        if(topS[0].equals(next.m_text))
        {
            return "pop";
        }
        if(lCols.containsKey(next.m_text))
            return syntaxTable[lRows.get(top)][lCols.get(next.m_text)];

        return "err";
    }

    public boolean AnaliseLL(MPLexer lexer)
    {
        Stack<String> stack = new Stack<>();
        stack.push("#");
        stack.push("CS");
        boolean recognized = false;
        boolean errors = false;
        String temp;
        String[] array;

        try {
            Yytoken next = lexer.next_token();
            do
            {
                temp = MatchShiftsFromTable(stack.peek(), next);
                switch(temp)
                {
                    case "pop":
                        stack.pop();
                        next = lexer.next_token();
                        break;
                    case "acc":
                        recognized = true;
                        break;
                    case "err":
                        errors = true;
                        break;
                    default:
                        stack.pop();
                        array = shifts[Integer.parseInt(temp)].split(" ");
                        String[] b = new String[array.length];
                        int j = array.length;
                        for (int i = 0; i < array.length; i++) {
                            b[j - 1] = array[i];
                            j = j - 1;
                        }
                        for(String str : b)
                        {
                            if(!str.equals(""))
                                stack.push(str);
                        }
                        break;
                }
            }while(!(recognized || errors));

            return recognized;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recognized;

    }
}
