/**
 * Program to estimate the function, f(x) = (1.7541226)^(-x^4) - (x^3)*sin(x^4) - 1.6360913
 * using Taylor series. The value of x will be provided by the user.
 *
 * @author Dinia Gepte, 107092681
 * Homework 1, AMS 326, FAll 2012
 */

#include <stdio.h>
#include <math.h>

// CONSTANTS
#define DELTA 0.005
#define DELTA_INV 200.0
#define A 0.0

// PROTOTYPES
double function(double x);
double first_derivative(double x);
double second_derivative(double x);
double third_derivative(double x);

int main(void)
{
	// DECLARATIONS
	double x_value, function_value;
	double first_term, second_term, third_term, fourth_term;

	// ASKS USER FOR A NUMBER
	printf("Enter a number x to estimate the function f(x): ");
	scanf("%lf", &x_value);

	// ACTUAL CALCULATION USING TAYLOR SERIES WHERE a=0
	first_term = function(A);
	second_term = first_derivative(A)*(x_value);	
	third_term = second_derivative(A)*pow(x_value-A, 2)*0.5;
	fourth_term = third_derivative(A)*pow(x_value-A, 3)/6;
	function_value = first_term + second_term + third_term + fourth_term;

	// OUTPUT
	printf("\nThe value of the function at f(%f) = %f where:", x_value, function_value);
	printf("\n  the 1st term = %f", first_term);
	printf("\n  the 2nd term = %f", second_term);
	printf("\n  the 3rd term = %f", third_term);
	printf("\n  the 4th term = %f", fourth_term);

	return 0;
}

double function(double x)
{
	return pow(1.7541226, -(pow(x, 4))) - (pow(x, 3) * sin(pow(x, 4))) - 1.6360913;
}

/*** Computes the derivatives using the Central Difference Method. ***/

double first_derivative(double x)
{
	return (function(x+DELTA) - function(x-DELTA))*(0.5*DELTA_INV);
}

double second_derivative(double x)
{
	return (function(x+DELTA) + function(x-DELTA) - 2*function(x)) * pow(DELTA_INV, 2);
}

double third_derivative(double x)
{
	return ((function(x+DELTA) - function(x-DELTA) - 2*first_derivative(x)*DELTA)*6) * (0.5) * pow(DELTA_INV, 3);
}