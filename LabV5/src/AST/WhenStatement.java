package AST;

import SymbolTable.Constant;

import java.io.BufferedWriter;
import java.io.IOException;

public class WhenStatement extends Statement{

    private ConstantExpression constant;
    private Statement statement;
    private String endLabel;
    private String exeLabel;

    public WhenStatement(ConstantExpression c, Statement s)
    {
        constant = c;
        statement = s;
    }

    public void setEndLabel(String endLabel)
    {
        this.endLabel = endLabel;
    }

    public void setExeLabel(String exeLabel)
    {
        this.exeLabel = exeLabel;
    }

    public void translate(BufferedWriter out) throws IOException {
        out.write(exeLabel + ":");
        out.newLine();
        statement.translate(out);
        out.write("\tJump\t" + endLabel);
        out.newLine();
    }
    public ConstantExpression getConstant()
    {
        return constant;
    }
}
