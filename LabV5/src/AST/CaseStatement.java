package AST;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CaseStatement extends Statement{

    private Expression condition;
    private ArrayList whenStatements;

    public CaseStatement(Expression e, ArrayList ws)
    {
        this.condition = e;
        this.whenStatements = ws;
    }
    public void translate(BufferedWriter out) throws IOException {
        condition.translate( out );
        condition.genLoad( "R1", out );
        String endLabel = ASTNode.genLab();

        for (int i = 0; i < whenStatements.size(); i++)
        {
            WhenStatement ws = (WhenStatement)whenStatements.get(i);
            ws.setEndLabel(endLabel);
            String exeLabel = ASTNode.genLab();
            ws.setExeLabel(exeLabel);
            ws.getConstant().translate(out);
            ws.getConstant().genLoad("R2", out);
            out.write("\tCompare_Equal\tR2, R1");
            out.newLine();
            out.write("\tJumpIfZero\tR2, " + exeLabel);
            out.newLine();
        }

        out.write("\tJump\t" + endLabel);
        out.newLine();

        for (int i = 0; i < whenStatements.size(); i++)
        {
            WhenStatement ws = (WhenStatement)whenStatements.get(i);
            ws.translate(out);
        }

        out.write(endLabel + ":");

        out.newLine();
    }
}
