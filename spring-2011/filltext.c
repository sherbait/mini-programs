/**
 * This program reads ASCII text from a file, breaks 
 * it into a sequence of words, and prints out the text,
 * in such a way that as many words are placed on
 * the same line as possible without exceeding a
 * maximum line length.
 *
 * @author Dinia Gepte
 * SBU ID: 107092681
 * Assignment:
 *   Homework #5 in CSE 220
 */
 
 #include <stdio.h>
 
 // VARIABLE CONSTANTS
 #define MAXWORD 30
 #define MAXLINE 60
 
 // PROTOTYPES
 void getword(char *buf, char *c, int *lineLength);
 void putword(char *buf, int wordLength, int *lineLength);
 int iswhitespace(char c);
 
 int main()
 {
	char buf[MAXWORD];
	char c;
	int lineLength = 0;
	
	if ((c = getchar()) != EOF)
		getword(buf, &c, &lineLength);
	fflush(stdout);
 }
 
 void getword(char *buf, char *c, int *lineLength)
 {
	int wordLength = 0;
	
	// THIS WILL READ A CHARACTER UNTIL IT REACHES A
	// WHITESPACE OR THE ALLOWED WORD LENGTH HAS BEEN
	// REACHED.
	while ((iswhitespace(*c) == 1) &&
			(wordLength < MAXWORD))
	{
		buf[wordLength] = *c;
		wordLength++;
		if ((*c = getchar()) == EOF)
		{
			putword(buf, wordLength, &*lineLength);
			return;
		}
	}
	
	// THIS WILL TURN MANY WHITESPACE CHARACTERS INTO
	// A SINGLE WHITESPACE CHARACTER.
	if (iswhitespace(*c) == 0)
		while ((*c = getchar()) != EOF)
			if ((iswhitespace(*c)) == 1)
				break;
	
	putword(buf, wordLength, &*lineLength);
	getword(buf, &*c, &*lineLength);
 }
 
 void putword(char *buf, int wordLength, int *lineLength)
 {
	int index;
	*lineLength = *lineLength + wordLength;
	if (*lineLength > MAXLINE)
	{
		putchar('\n');
		*lineLength = 0;
		*lineLength = *lineLength + wordLength;
	}
	
	// PRINTS OUT THE WORD
	for (index = 0; index < wordLength; index++)
		putchar(buf[index]);
	
	putchar(' ');
	(*lineLength)++;
 }
 
 /**
  * This method returns 0 if a character is a whitespace.
  * Otherwise, 1.
  */
 int iswhitespace(char c)
 {
	if ((c == ' ') || (c == '\t') ||
		(c == '\r')|| (c == '\n'))
		return 0;
	else
		return 1;
 }