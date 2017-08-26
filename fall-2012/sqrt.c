/**
 * Program to perform the sqrt() operation using Newton's Method.
 *
 * @author Dinia Gepte, 107092681
 * Homework 1, AMS 326, FAll 2012
 */

#include <stdio.h>
#include <math.h>

void check(double n1, double n2, int *pass);

int main(void)
{
	double n1, n2, number;
	int pass = 1;	// USED FOR TOLERANCE

	// GET USER INPUT
	printf("Square root of: ");
	scanf("%lf", &number);

	// SET AN ARBITRARY VALUE OF n1
	n1 = number / 2;

	// THIS LOOP WILL DETERMINE THE ROOT BY USING NEWTON'S METHOD.
	// IT WILL BE DONE WHEN n1 AND n2 ARE ACCURATE UP TO 6 DECIMALS
	while (pass != 0)
	{
		n2 = 0.5 * (n1 + number/n1);
		check(n1, n2, &pass);
		n1 = n2;
	}

	// OUTPUT
	printf("= %f", n2);

	return 0;
}

/** Performs the checking whether n1 and n2 are correct up to 6 decimals. **/
void check(double n1, double n2, int *pass)
{
	if (fabs(n2-n1) < 0.000001)
		*pass = 0;
}