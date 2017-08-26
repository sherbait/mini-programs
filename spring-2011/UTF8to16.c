/**
 * Converts UTF-8 to UTF-16 encoding.
 * 
 * @author Dinia Gepte
 * Assignment:
 *   Homework #4 in CSE 220
 */

#include <stdio.h>

void convertToSurrogatePair(int num);

int main()
{
	//BIG ENDIAN byte order mark
	putchar(0xFE);
	putchar(0xFF);

	int c, temp;
	while ((c = getchar()) != EOF)
	{
		//CASE 1: U+0000 to U+007F
		if ((c & 0x80) == 0x00)
		{
			putchar(0x00);
			putchar(c);
		}
		//CASE 2: U+0080 to U+07FF
		else if ((c & 0xE0) == 0xC0)
		{
			//code point building
			temp = (c & 0x1F) << 6;
			c = getchar();
			temp = temp | (c & 0x3F);

			putchar((temp >> 8) & 0x00FF);
			putchar(temp & 0x00FF);
		}
		//CASE 3: U+0800 to U+FFFF
		else if ((c & 0xF0) == 0xE0)
		{
			temp = (c & 0x0F) << 12;
			c = getchar();
			temp = temp | ((c & 0x3F) << 6);
			c = getchar();
			temp = temp | (c & 0x3F);

			putchar((temp >> 8) & 0x00FF);
			putchar(temp & 0x00FF);
		}
		//CASE 4: U+010000 to U+10FFFF
		else if ((c & 0xF8) == 0xF0)
		{
			temp = (c & 0x07) << 18;
			c = getchar();
			temp = temp | ((c & 0x3F) << 12);
			c = getchar();
			temp = temp | ((c & 0x3F) << 6);
			c = getchar();
			temp = temp | (c & 0x3F);
			
			convertToSurrogatePair(temp);
		}
	}
	fflush(stdout);
}

/**
 *	Method used to convert supplementary code point to a
 *	4-byte sequence in UTF-16.
 */
void convertToSurrogatePair(int num)
{
	int surrogate, highS, lowS;

	num = num - 0x10000;
	highS = (num >> 10) + 0xD800;
	lowS = (num & 0x3FF) + 0xDC00;
	
	putchar((highS >> 8) & 0x00FF);
	putchar(highS & 0x00FF);
	putchar((lowS >> 8) & 0x00FF);
	putchar(lowS & 0x00FF);
}