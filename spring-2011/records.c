/**
 * This program manages a simple database that stores information
 * about people in fixed-format records kept in a disk file.
 *
 * @author Dinia Gepte
 * SBU ID: 107092681
 * Assignment:
 *   Homework #7 in CSE 220
 */
 
#include <stdio.h>
#include <string.h>

// VARIABLE CONSTANTS
#define NAME_LENGTH 20
#define EMAIL_LENGTH 15
#define FILE_NAME_LENGTH 20

struct info {
	char name[NAME_LENGTH];
	char email[EMAIL_LENGTH];
	int flag;
};

// PROTOTYPES
void defaultRecords(FILE *file);
void showInfo(struct info *record);
struct info *get(FILE *file, int index);
int put(FILE *file, int index, struct info *record);
struct info *search(FILE *file, char *name);
int createDB(char *name);
FILE *openDB(char *name);
int closeDB(FILE *f);

// GLOBAL VARIABLES
int flag;	// TELLS IF fread() OR fwrite() WAS USED
			// 0 - fread(), 1 - fwrite()
int main()
{
	// ONLY ONE DATABASE (I.E. FILE) WILL BE USED THROUGHOUT THE PROGRAM
	static FILE *file;
	char fileName[FILE_NAME_LENGTH];
	printf("\nEnter a database file name: ");
	scanf("%s", fileName);
	createDB(fileName);
	file = openDB(fileName);
	
	// EVERY DATABASE HAS THESE DEFAULT RECORDS
	defaultRecords(file);
	
	short choice = 0;
	struct info tempRecord;
	struct info *tr = &tempRecord;
	while (choice != 4)
	{
		// PROMPTS THE USER FOR AN OPERATION
		choice = 0;
		printf("\n1) GET a record\n");
		printf("2) PUT a record\n");
		printf("3) SEARCH database\n");
		printf("4) QUIT\n");
		printf("Choice: ");
		scanf("%d", &choice);
		while (getchar() != '\n');
		
		if (choice == 1)
		{
			int index;
			printf("Enter a record index: ");
			if (scanf("%d", &index) == 0)
			{
				while (getchar() != '\n');
				printf("Wrong input!\nOperation cancelled.\n");
			}
			else
			{
				if ((tr = get(file, index)) != NULL)
					showInfo(tr);
				else
					printf("No record at given index.\n");
			}
		}
		else if (choice == 2)
		{
			char name[NAME_LENGTH];
			char email[EMAIL_LENGTH];
			int index;
			// GET USER VALUES
			printf("Enter name: ");
			gets(name);
			printf("Enter e-mail: ");
			gets(email);
			printf("Enter the index to put this record: ");
			if (scanf("%d", &index) == 0)
			{
				while (getchar() != '\n');
				printf("Wrong input!\nOperation cancelled.\n");
			}
			else
			{
				// SET RECORD VALUES
				strcpy(tr->name, name);
				strcpy(tr->email, email);
				tr->flag = 1;	// DEFAULT VALUE 1
				// PUT THE RECORD INTO THE FILE
				if (put(file, index, tr) != EOF)
					printf("Record entered successfully!\n");
				else
					printf("Attemp to put record was unsuccessful.\n");
			}
		}
		else if (choice == 3)
		{
			char name[NAME_LENGTH];
			printf("Enter name: ");
			gets(name);
			if ((tr = search(file, name)) != NULL)
				showInfo(tr);
			else
				printf("Record not in the database.\n");
		}
		else if (choice == 4) 	{}
		else
			printf("Wrong input!\n");
	}
	closeDB(file);
	return 0;
}

/**
 * These records are default to any database created.
 * They represent various test cases.
 */
void defaultRecords(FILE *file)
{
	static struct info recordToPut;
	
	strcpy(recordToPut.name, "Joe");
	strcpy(recordToPut.email, "joe@mail.com");
	recordToPut.flag = 1;
	put(file, 0, &recordToPut);
	
	strcpy(recordToPut.name, "Renee");
	strcpy(recordToPut.email, "r87@mail.com");
	recordToPut.flag = 25;
	put(file, 1, &recordToPut);
	
	strcpy(recordToPut.name, "David");
	strcpy(recordToPut.email, "1337@mail.com");
	recordToPut.flag = 0;
	put(file, 5, &recordToPut);
	
	strcpy(recordToPut.name, "Charleen Web");
	strcpy(recordToPut.email, "C.W@mail.com");
	recordToPut.flag = 395;
	put(file, 10, &recordToPut);
}

/**
 * This function prints on the standard output a representation
 * of a given pointer to a record.
 */
void showInfo(struct info *record)
{
	printf("\nname: %s\n", record->name);
	printf("e-mail: %s\n", record->email);
	printf("flag: %d\n", record->flag);
}

/**
 * This function locates the record at a given index and returns
 * a pointer to it. It returs NULL if reading the record has failed.
 */
struct info *get(FILE *file, int index)
{
	// CHECK WHICH FUNCTION WAS LAST USED
	if (flag == 1)
	{
		rewind(file);
		flag = 0;
	}
	
	static struct info record;
	fseek(file, index*sizeof(struct info), SEEK_SET);
	if (fread(&record, sizeof(struct info), 1, file) != 1)
		return NULL;
	else
		return &record;
}

/**
 * This function writes the record given by the parameter to the file.
 * It returns 0 if writing the record was successful, otherwise EOF.
 */
int put(FILE *file, int index, struct info *record)
{
	// WHICH FUNCTION WAS LAST USED?
	if (flag == 0)
	{
		rewind(file);
		flag = 1;
	}

	fseek(file, index*sizeof(struct info), SEEK_SET);
	if (fwrite(record, sizeof(struct info), 1, file) != 1)
		return EOF;
	else
		return 0;
}

/**
 * This function searches the file for the record that has the same
 * name as the parameter given that it is currently used by the database.
 * It returns the pointer to that record, otherwise NULL if the record
 * was not found in the file.
 */
struct info *search(FILE *file, char *name)
{
	int i;
	static struct info recordFound;
	static struct info *rf = &recordFound;
	for (i = 0; (rf = get(file, i)) != NULL; i++)
		if ((rf->flag != 0) && (strcmp(rf->name, name) == 0))
			return rf;
	return NULL;
}

/**
 * Creates a database file given the file name.
 */
int createDB(char *name)
{
	FILE *file;
	if ((file = fopen(name, "w+b")) != NULL)
		return fclose(file);
	else
		return EOF;
}

/**
 * Opens the database file corresponding to the given file name.
 */
FILE *openDB(char *name)
{
	return fopen(name, "r+b");
}

/**
 * Closes the database file corresponding to the given file name.
 */
int closeDB(FILE *f)
{
	return fclose(f);
}
