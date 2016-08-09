import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.*;

@SuppressWarnings("unchecked")
public class InfixEvaluator {
	
	//static Deque operatorStack = new ArrayDeque();
	//static Deque operandStack = new ArrayDeque();

	static char[] operators = {'^','*','(',')','/','+','-'};

	public static void main(String[] args){
		String testString;
		if(args.length > 0){
			testString = args[0];
		} else {
			testString = "25 + (3 * 1.5)";
		}
		double t = 0;

		// Validation could have been placed in the parse method
		// However, this implementation results in cleaner code,
		// and is easier to follow.  Validate parses through the string
		// and makes sure it is valid.  The parsing is done exactly the
		// same as in parse.
		if(validate(testString)){ 
			t = parse(testString);

			if(t%1 == 0){
				System.out.println("Result: " + (int)t);
			} else {
				System.out.println("Result: " + t);
			}
		} else {
			System.out.println("***** Invalid expression ******");
		}
	}

	public static double parse(String s){
		Deque operatorStack = new ArrayDeque();
		Deque operandStack = new ArrayDeque();
		try
	    {
	      StringReader sr = new StringReader(s);
	      StreamTokenizer st = new StreamTokenizer(sr);
	      st.ordinaryChar('-');
	      st.ordinaryChar('/');

	      int currentToken = st.nextToken();

	      boolean precedenceFlag = false;
	      boolean operateFlag = false;
	      while (currentToken != StreamTokenizer.TT_EOF || operatorStack.peekFirst() != null)
	      {
	      	
	      	if(currentToken == StreamTokenizer.TT_EOF){	
	      		double temp1,temp2;
		      	if(operandStack.peekFirst() != null){
			      	temp1 = (double)operandStack.removeFirst();
			      	//System.out.println("final operand 1: " + temp1);
			      	if(operandStack.peekFirst() != null){
			      		temp2 = (double)operandStack.removeFirst();
			      		//System.out.println("final operand 2: " + temp2);
			      		if(operatorStack.peekFirst() != null){
			      			//System.out.println("final operator: " + (char)operatorStack.peekFirst());
			      			operandStack.addFirst(calculate((char)operatorStack.removeFirst(),temp2,temp1));
			      		}
			      	}
			    }
			    printStacks(operandStack,operatorStack);
	      	} else {
			    if(currentToken == StreamTokenizer.TT_NUMBER){
			    	//System.out.println("Num: " + String.valueOf(st.nval));
			    	//operandStack.addFirst(st.nval);
			    	double t = st.nval;
			    	double temp1 = t;
			    	double temp2;
			    	if(operandStack.peekFirst() != null){
			    		//temp2 = (double) operandStack.removeFirst();
			    		operateFlag = true;
			    	} else {
			    		operateFlag = false;
			    	}
			    	operandStack.addFirst(t);
			    	if(t%1==0){
			    		System.out.println("Processing token " + (int)t);
			    	} else {
			    		System.out.println("Processing token " + t);
			    	}

			    	printStacks(operandStack,operatorStack);
			    	//System.out.println("Num: " + (operandStack.peekFirst()+1));
			    }
			    else if(currentToken == StreamTokenizer.TT_WORD){
			    	System.out.println("Str: " + String.valueOf(st.sval));
			    	double t = variable(String.valueOf(st.sval));

			    	//double t = st.nval;
			    	double temp1 = t;
			    	double temp2;
			    	if(operandStack.peekFirst() != null){
			    		//temp2 = (double) operandStack.removeFirst();
			    		operateFlag = true;
			    	} else {
			    		operateFlag = false;
			    	}
			    	operandStack.addFirst(t);
			    	if(t%1==0){
			    		System.out.println("Processing token " + (int)t);
			    	} else {
			    		System.out.println("Processing token " + t);
			    	}

			    	printStacks(operandStack,operatorStack);
			    }
			    else {
			    	//System.out.println("Chr: " + String.valueOf((char) st.ttype));
			    	precedenceFlag = true;
			    	char t = (char)st.ttype;
			    	if(t!=')'){
			        	double temp1;
			        	double temp2;
			        	if(operateFlag){
			        		if(precedence(t) < precedence((char)operatorStack.peekFirst())){
			        			precedenceFlag = false;
			        		}
			        	}
			        	System.out.println("Processing token " + t);
			        	if(precedenceFlag && operateFlag){
				        		temp1 = (double)operandStack.removeFirst();

				        		if(operandStack.peekFirst() != null){
				        			temp2 = (double)operandStack.removeFirst();
			        				double result = calculate((char)operatorStack.removeFirst(),temp2,temp1);
			        				operandStack.addFirst(result);
				        		} else {
				        			operandStack.addFirst(temp1);
				        		}
				        }
				        operatorStack.addFirst(t);
			    		printStacks(operandStack,operatorStack);
			        } else {
			        	double temp1;
			        	double temp2;
			        	while((char)operatorStack.peekFirst() != '('){
			        		temp1 = (double)operandStack.removeFirst();

			        		if(operandStack.peekFirst() != null){
			        			temp2 = (double)operandStack.removeFirst();
		        				double result = calculate((char)operatorStack.removeFirst(),temp2,temp1);
		        				operandStack.addFirst(result);
			        		} else {
			        			operandStack.addFirst(temp1);
			        		}
			        	}
			        	if((char) operatorStack.peekFirst() == '('){
			        		operatorStack.removeFirst();
			        	}
			        	System.out.println("Operated the parentheses");
			    		printStacks(operandStack,operatorStack);
			        }
			    }
		        currentToken = st.nextToken();
		    }
	      }
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	      return 0;
	    }
	    return (double)operandStack.removeFirst();
	}

	public static boolean inOperators(char c){
		boolean state = false;
		for(char i: operators){
			if(c==i)
				state = true;
		}
		return state;
	}

	public static boolean validate(String s){
		Deque operatorStack = new ArrayDeque();
		Deque operandStack = new ArrayDeque();
		Deque parenthesesStack = new ArrayDeque();
		try
	    {
	      StringReader sr = new StringReader(s);
	      StreamTokenizer st = new StreamTokenizer(sr);
	      st.ordinaryChar('-');
	      st.ordinaryChar('/');

	      int currentToken = st.nextToken();

	      boolean precedenceFlag = false;
	      boolean operateFlag = false;

	      boolean lastOperator = false;
	      boolean lastOperand = false;
	      while (currentToken != StreamTokenizer.TT_EOF || operatorStack.peekFirst() != null)
	      {
		    if(currentToken == StreamTokenizer.TT_NUMBER){
		    	if(lastOperand){
		    		throw new AdjacentOperands();
		    	}
		    	lastOperand = true;
		    	lastOperator = false;
		    }
		    else if(currentToken == StreamTokenizer.TT_WORD){
		    	if(lastOperand){
		    		throw new AdjacentOperands();
		    	}
		    	lastOperand = true;
		    	lastOperator = false;
		    	//System.out.println("Str: " + String.valueOf(st.sval));
		    }
		    else {
		    	char t = (char) st.ttype;
		    	if(!inOperators(t)){
		    		throw new InvalidOperator();
		    	}
		    	if(lastOperator && t!='(' && t!=')'){
			    	
		    		throw new AdjacentOperators();
		    	}
		    	if(t!='(' && t!= ')'){
		    		lastOperand = false;
			    	lastOperator = true;
			    }
		    }
	        currentToken = st.nextToken();
		    
	      }
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	      return false;
	    }
	    catch(InvalidOperator e){
	    	System.out.println("Invalid Operator");
	    	return false;
	    }
	    catch(AdjacentOperators e){
	    	System.out.println("Adjacent Operators");
	    	return false;
	    }
	    catch(AdjacentOperands e){
	    	System.out.println("Adjacent Operands");
	    	return false;
	    }
	    return true;
	}

	public static double variable(String var){
		System.out.println("I've come across a variable named " + var);
		System.out.print("Enter its value: ");
		Scanner input = new Scanner(System.in);
		return input.nextDouble();
	}

	public static int precedence(char op) throws java.lang.IllegalArgumentException {
		switch(op){
			case '(':
				return 5;
			case ')':
				return 0;
			case '^':
				return 1;
			case '*':
				return 2;
			case '/':
				return 2;
			case '+':
				return 3;
			case '-':
				return 3;
			default:
				break;
		}
		throw new java.lang.IllegalArgumentException("Illegal Operator in precedence");
	}

	public static double calculate(char operator, double operand1, double operand2) throws java.lang.IllegalArgumentException {
		switch(operator){
			case '^':
				return Math.pow(operand1,operand2);
			case '*':
				return operand1*operand2;
			case '/':
				return operand1/operand2;
			case '+':
				return operand1+operand2;
			case '-':
				return operand1-operand2;
			default:
				break;
		}
		throw new java.lang.IllegalArgumentException("Illegal Operator in calculate");
	}

	public static void printStacks(Deque operandStack, Deque operatorStack){
		Deque tempStack = new ArrayDeque();
		System.out.print("Operand Stack: \n\t");
		while(operandStack.peekFirst() != null){
			double t = (double)operandStack.removeFirst();
			tempStack.addFirst(t);
		}
		while(tempStack.peekFirst() != null){
			double t = (double)tempStack.removeFirst();
			if(t%1 == 0){
				System.out.print((int) t + "  ");
			} else {
				System.out.print(t + "  ");
			}
			operandStack.addFirst(t);
		}
		System.out.println("  <-- top");

		tempStack = new ArrayDeque();

		System.out.print("Operator Stack: \n\t");
		while(operatorStack.peekFirst() != null){
			char t = (char)operatorStack.removeFirst();
			tempStack.addFirst(t);
		}
		while(tempStack.peekFirst() != null){
			char t = (char)tempStack.removeFirst();
			System.out.print(t + "  ");
			operatorStack.addFirst(t);
		}
		System.out.println("  <-- top\n\n");
	}

	static class InvalidOperator extends Exception{
		public InvalidOperator(){}
	}

	static class AdjacentOperators extends Exception{
		public AdjacentOperators(){}
	}

	static class AdjacentOperands extends Exception{
		public AdjacentOperands(){}
	}
}