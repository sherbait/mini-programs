import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Driver class that contains a main method that prompts the user for a
 * postfix expression, evaluates the expression, and prints the result.
 * This class uses the <CODE>Stack</CODE> and <CODE>Token</CODE> classes
 * to evaluate the given expression.<br><br>These conditions must be met
 * in order for the program to work correctly:<br>
 * 1. All operators and numbers are separated by spaces in the input.<br>
 * 2. Valid operators include addition (+), subtraction (-), multiplication(*),
 *    division(/), and exponent(^).<br>
 * 3. All expressions must be legal postfix expressions.<br>
 * 4. No negative numbers.<br>
 * 5. No division by zero.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #3 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class Evaluator
{
	/**
	 * This main method instantiates and initializes the primary variables
	 * to be used by the program. It also controls the program based on user
	 * input and calls a method within this class to find the value of the
	 * given postfix expression.
	 */
	public static void main(String[] args)
	{
		// Scanner class is used to get user input
		Scanner input = new Scanner(System.in);
		String choice = "Y";
		// Acts as flag to exit the loop and terminate the program normally
		boolean quit = false;
		while (!quit)
		{
			if (choice.equalsIgnoreCase("Y"))
			{
				System.out.print("\nPlease enter a postfix expression: ");
				StringTokenizer expression = 
				  new StringTokenizer(input.nextLine().trim());
				
				System.out.println("The value of your expression is " +
				  findValue(expression) + ".");
				
				System.out.print("Would you like to enter another " +
				  "expression (y or n)? ");
				choice = input.nextLine().trim();
			}
			else if (choice.equalsIgnoreCase("N"))
				quit = true;
			else
			{
				System.out.println("Input is incorrect. Try again.");
				System.out.print("Would you like to enter another " +
				  "expression (y or n)? ");
				choice = input.nextLine().trim();
			}
		}
		System.out.println("\nProgram terminated successfully.");
	}

	/**
	 * Returns the <CODE>int</CODE> value of the postfix expression as a
	 * parameter to the main method.
	 * @param expression
	 *   - the postfix expression to be evaluated
	 * @return
	 *   an integer result of the postfix expression parameter.
	 */
	private static int findValue(StringTokenizer expression)
	{
		// Creation of a stack that will be used to store the operands.
		Stack numberStack = new Stack();
		// Array list of the tokens from the postfix expression.
		// It has size of MAX_SIZE*2 because of the total number
		// of operands and operators two Stacks can hold.
		ArrayList inputList = new ArrayList(numberStack.MAX_SIZE * 2);
		while (expression.hasMoreTokens())
		{
			// Creation of a new Token object for every token extracted
			// from the StringTokenizer expression.
			Token tokenObject = new Token(expression.nextToken());
			inputList.add(tokenObject);
		}
		// The result to be returned to the main method.
		Integer result = 0;
		try
		{
			// Evaluating the elements in the inputList from index 0.
			for (int i = 0; i < inputList.size(); i++)
			{
				// Temporary Token variable to cast the element from the
				// inputList back to type Token.
				Token element = (Token)inputList.get(i);
				// Only integers are pushed in the created stack.
				if (!element.isOperator())
					numberStack.push(element);
				// Executes when the Token is an operator.
				else
				{
					int operand2 = numberStack.pop().getValue();
					int operand1 = numberStack.pop().getValue();
					if (element.getOperator() == '+')
						result = operand1 + operand2;
					else if (element.getOperator() == '-')
						result = operand1 - operand2;
					else if (element.getOperator() == '*')
						result = operand1 * operand2;
					else if (element.getOperator() == '/')
						result = operand1 / operand2;
					else
						result = (int)Math.pow(operand1, operand2);
					// Temporary Token variable to store the Integer result
					// in the form of String to be able to push it into the
					// numberStack.
					Token tokenResult = new Token(result.toString());
					numberStack.push(tokenResult);
				}
			}
			return numberStack.pop().getValue();
		}
		catch (FullStackException fse)		{System.out.println(fse.getMessage());}
		catch (EmptyStackException ese)		{System.out.println(ese.getMessage());}
		catch (EmptyVariableException eve)	{System.out.println(eve.getMessage());}
		return result;
	}
}
