Austin Frownfelter - 4032386
CS 0445 MoWe 6:00-7:15 ;; Wednesday Recitation

Features:
	InfixEvaluator may read an expression as either a single, string argument from the commandline, or as a string in the main() method (named: testString).

Potential Issues/Incompletions:
	The errors caught are most likely not all that can be.  Currently caught errors are:
		- Invalid Operator
		- Adjacent Operators
		- Adjacent Operands

	Uncaught exceptions that will/may result in infinite loops or incorrect results:
		- Unbalanced/Improperly nested brackets


Bonus:
	InfixEvaluator implements a function to handle variables.  It should handle multi-character (e.g. 'ab' is treated as a single variable), and multiple variables in a single expression.