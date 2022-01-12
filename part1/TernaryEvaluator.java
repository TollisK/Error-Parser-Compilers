import java.io.InputStream;
import java.io.IOException;


class TernaryEvaluator {
    private final InputStream in;

    private int lookahead;

    public TernaryEvaluator(InputStream in) throws IOException {
        this.in = in;
        lookahead = in.read();
    }

    private void consume(int symbol) throws IOException, ParseError {
        if (lookahead == symbol)
            lookahead = in.read();
        else
            throw new ParseError();
    }

    private boolean isDigit(int c) {
        return '0' <= c && c <= '9';
    }

    private int evalDigit(int c) {
        return c - '0';
    }

    public int eval() throws IOException, ParseError {
        int value = goal();

        if (lookahead != -1 && lookahead != '\n')
            throw new ParseError();

        return value;
    }


    private int goal() throws IOException, ParseError {
        if (isDigit(lookahead)){
            int cond = evalDigit(lookahead);
            return expression(action(cond)); 
        }
        else if(lookahead=='('){
            int cond = 0; 
            return expression(action(cond));
        }
        throw new ParseError();
    }

    private int expression(int condition) throws IOException, ParseError {
        int cond;
        switch (lookahead){
            case '+':
                consume('+');
                cond = condition+action(condition);

                return expression(cond);
            case '-':
                consume('-');
                cond = condition-action(condition);
                return expression(cond);
            case -1:
            case '\n':
            case ')':
                return condition;
        }

        throw new ParseError();
    }

    private int action(int condition) throws IOException, ParseError {
        if (isDigit(lookahead)) {
            return power(number(condition,0)); 
        }
        else if(lookahead == '('){
            return power(number(condition,0));
        }
        throw new ParseError();
    }

    private int power(int condition) throws IOException, ParseError {
        switch(lookahead){
            case '*':
                consume('*');
                consume('*');
                int num = number(condition,0);
                int ret = power(num);
                int cond=1;
                for(int i=0;i<ret;i++){
                    cond = cond * condition;
                }
                return power(cond);
            case '+':
            case '-':
            case ')':
            case '\n':
                return condition;
        }
        throw new ParseError();
    }

    private int number(int condition,int num) throws IOException, ParseError {
        if (lookahead == '(') {
            consume('(');
            int cond = expression(action(condition));
            consume(')');
            return cond;
        }
        else if(isDigit(lookahead)){
            return number2(digit(condition,num));
        }
        throw new ParseError();
    }

    private int number2(int condition) throws IOException, ParseError {
        if (isDigit(lookahead)) {
            return number(condition,-1); 
        }
        else if(lookahead == '*'||lookahead == '+'||lookahead == '-'||lookahead == '\n'||lookahead == -1||lookahead == ')'){
            return condition;
        }
        throw new ParseError();
    }
    
    private int digit(int condition,int num) throws IOException, ParseError {
        if (isDigit(lookahead)) {
            int cond = evalDigit(lookahead);
            consume(lookahead);

            if(num == -1){
                if(condition == 0){
                    throw new ParseError();
                }
                return condition*10 + cond; 
            }

            return cond;
        }
        else if(lookahead == '*'){
            return condition;
        }
        throw new ParseError();
    }
}
