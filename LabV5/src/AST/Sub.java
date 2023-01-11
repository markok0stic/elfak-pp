package AST;

public class Sub extends BinaryExpression {

    public Sub( Expression left, Expression right )
    {
        super( left, right );
    }

    protected String opCode()
    {
        return "Sub";
    }
}
