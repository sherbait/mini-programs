from operator import attrgetter

"""
  This is a program using the OOP paradigm. It is a phonebook storing information about ASEAN students.
  The user is allowed to STORE into and EDIT the phonebook, as well as SEARCH by country. There are 11 
  countries in Southeast Asia but for simplicity's sake, the following ASEAN countries with their 
  corresponding country code are used:
  
    ASEAN COUNTRY                 COUNTRY CODE
    Federation of Malaysia        60
    Republic of Indonesia         62
    Republic of the Philippines   63
    Republic of Singapore         65
    Kingdom of Thailand           66
    
  No database or text file is used, so data is lost after exiting the program.
    
  @author Dinia Gepte
  @date   02 Sept 2017
"""

# dict THAT STORES EACH COUNTRY'S COUNTRY CODE
country_codes = {
  60: "Malaysia",
  62: "Indonesia",
  63: "Philippines",
  65: "Singapore",
  66: "Thailand"
}

"""
  STUDENT class stores all information of a student.
"""
class Student(object):
  
  def __init__(self, student_number, surname, first_name, occupation, gender, country_code, area_code, number):
    self.student_number = student_number
    self.surname = surname
    self.first_name = first_name
    self.occupation = occupation
    self.gender = gender
    self.country_code = country_code
    self.area_code = area_code
    self.number = number
  
  # RETURNS A string DESCRIPTION OF THE STUDENT WITHOUT student_number
  def short_desc(self):
    description = "%s %s is a %s. " % (self.first_name, self.surname, self.occupation)
    if self.gender == "F" or self.gender == "f":
      description = description + "Her "
    else:
      description = description + "His "
    description = description + "number is %d-%d-%d" % (self.country_code, self.area_code, self.number)
    return description
    
  # RETURNS A string DESCRIPTION OF THE STUDENT WITH student_number
  def long_desc(self):
    description = "%s, %s, with student number %s, is a %s. " % (self.surname, self.first_name, self.student_number, self.occupation)
    if self.gender == "F" or self.gender == "f":
      description = description + "Her "
    else:
      description = description + "His "
    description = description + "phone number is %d-%d-%d" % (self.country_code, self.area_code, self.number)
    return description
  
  def __repr__(self):
    return repr((self.student_number, self.surname, self.first_name, self.occupation, self.gender, self.country_code, self.area_code, self.number))

"""
  PHONEBOOK class stores STUDENT objects as entries in a list. It has methods for phonebook entries manipulation
  as well as getting useful information regarding the phonebook.
"""
class Phonebook(object):
  
  # ENTRIES IN THE PHONEBOOK ARE KEPT AS list
  entries = []
  
  def add(self, new_entry):
    self.entries.append(new_entry)
  
  # ACCEPTS A STUDENT NUMBER (sn) AND NEW STUDENT NUMBER THEN REPLACES THE OLD ONE
  def edit_student_number(self, sn, new_sn):
    for entry in self.entries:
      if entry.student_number == sn:
        entry.student_number = new_sn
  
  # ACCEPTS A STUDENT NUMBER (sn) AND NEW SURNAME THEN REPLACES THE OLD ONE
  def edit_surname(self, sn, new_surname):
    for entry in self.entries:
      if entry.student_number == sn:
        entry.surname = new_surname
  
  # ACCEPTS A STUDENT NUMBER (sn) AND NEW GENDER THEN REPLACES THE OLD ONE
  def edit_gender(self, sn, new_gender):
    for entry in self.entries:
      if entry.student_number == sn:
        entry.gender = new_gender
  
  # ACCEPTS A STUDENT NUMBER (sn) AND NEW OCCUPATION THEN REPLACES THE OLD ONE
  def edit_occupation(self, sn, new_occupation):
    for entry in self.entries:
      if entry.student_number == sn:
        entry.occupation = new_occupation
  
  # ACCEPTS A STUDENT NUMBER (sn) AND NEW COUNTRY CODE THEN REPLACES THE OLD ONE
  def edit_country_code(self, sn, new_cc):
    for entry in self.entries:
      if entry.student_number == sn:
        entry.country_code = new_cc
  
  # ACCEPTS A STUDENT NUMBER (sn) AND NEW AREA CODE THEN REPLACES THE OLD ONE
  def edit_area_code(self, sn, new_ac):
    for entry in self.entries:
      if entry.student_number == sn:
        entry.area_code = new_ac
        
  # ACCEPTS A STUDENT NUMBER (sn) AND NEW NUMBER THEN REPLACES THE OLD ONE
  def edit_number(self, sn, new_number):
    for entry in self.entries:
      if entry.student_number == sn:
        entry.number = new_number
  
  # RETURNS A student_number LIST WITH country_code IN THE country_code_list
  def search_by_country_code_list(self, country_code_list):
    filtered_student_number_list = []
    for cc in country_code_list:
      for entry in self.entries:
        if entry.country_code == cc:
          filtered_student_number_list.append(entry.student_number)
    return filtered_student_number_list
  
  # RETURNS A student_number LIST SORTED ALPHABETICALLY BY surname THEN first_name
  def sort_by_surname(self, student_number_list):
    # EXTRACT THE object REFERENCES FROM entries IN THE student_number_list
    unsorted_student_list = []
    for entry in self.entries:
      if entry.student_number in student_number_list:
        unsorted_student_list.append(entry)
    # SORT THE unsorted_student_list BY surname THEN BY first_name
    sorted_student_list = sorted(unsorted_student_list, key=attrgetter("surname", "first_name"))
    # RETURN THE student_numbers ONLY
    sorted_student_number_list = []
    for student in sorted_student_list:
      sorted_student_number_list.append(student.student_number)
    return sorted_student_number_list
  
  # RETURNS True IF THE GIVEN STUDENT NUMBER (sn) EXISTS IN THE PHONEBOOK; False OTHERWISE
  def check_entry(self, sn):
    for entry in self.entries:
      if entry.student_number == sn:
        return True
    return False
    
  # RETURNS THE NUMBER OF ENTRIES IN THE PHONEBOOK
  def size(self):
    return len(self.entries)
  
  # RETURNS A STRING DESCRIPTION OF THE Student GIVEN ITS STUDENT NUMBER (sn) IF IT EXISTS; EMPTY STRING OTHERWISE
  def get_short_desc(self, sn):
    for entry in self.entries:
      if entry.student_number == sn:
        return entry.short_desc()
    return ""
  
  # RETURNS A STRING DESCRIPTION OF THE Student GIVEN ITS STUDENT NUMBER (sn) IF IT EXISTS; EMPTY STRING OTHERWISE
  def get_long_desc(self, sn):
    for entry in self.entries:
      if entry.student_number == sn:
        return entry.long_desc()
    return ""
  
  # STRING REPRESENTATION OF THE PHONEBOOK WHERE EACH ENTRY IS ON A LINE SEPARATED BY COMMAS
  def __repr__(self):
    string_list_of_entries = ""
    for index, entry in enumerate(self.entries):
      if index == len(self.entries)-1:
        string_list_of_entries = string_list_of_entries + str(entry)
      else:
        string_list_of_entries = string_list_of_entries + str(entry) + "\n"
    return string_list_of_entries

"""
  Helper functions for the main program.
"""
# PRINTS THE MAIN MENU W/O ASKING FOR USER INPUT
def print_main_menu():
  main_menu_options = [
    "Store to ASEAN phonebook",
    "Edit entry in ASEAN phonebook",
    "Search ASEAN phonebook by country",
    "Exit"
  ]
  for index, option in enumerate(main_menu_options):
    print "[" + str(index+1) + "] " + option

# ASKS THE USER A SERIES OF QUESTIONS THEN RETURNS A Student OBJECT ASSUMING THAT ALL USER INPUT ARE VALID
def create_entry():
  student_number = raw_input("Enter student number: ").strip()
  surname = raw_input("Enter surname: ").strip()
  first_name = raw_input("Enter first name: ").strip()
  occupation = raw_input("Enter occupation: ").strip()
  gender = raw_input("Enter gender (M for male, F for female): ").upper().strip()
  country_code = int(raw_input("Enter country code: ").strip())
  area_code = int(raw_input("Enter area code: ").strip())
  number = int(raw_input("Enter number: ").strip())
  new_entry = Student(student_number, surname, first_name, occupation, gender, country_code, area_code, number)
  return new_entry

# ACCEPTS A STUDENT NUMBER (sn) AND PRINTS INFORMATION ABOUT IT ALONG WITH THE EDIT MENU OPTIONS.
# RETURNS True IF USER WANTS TO CONTINUE EDITING (i.e. selected [1-7]); False OTHERWISE.
# ALSO RETURNS THE student_number OF THE Student OBJECT CURRENTLY BEING EDITED. 
# IF CHANGES IN THE CURRENT student_number OCCURS, THE OLD IS REPLACED BY NEW AND CONTINUE EDIT THE Student OBJECT.
# THE VALUES RETURNED ARE REPRESENTED BY A 2-VALUE LIST
def print_edit_menu(sn):
  edit_menu_options = [
    "Student number",
    "Surname",
    "Gender",
    "Occupation",
    "Country code",
    "Area code",
    "Phone number",
    "None - Go back to main menu"
  ]
  print "Here is the existing information about %s:" % sn
  print asean_phonebook.get_short_desc(sn)
  print "Which of the following information do you wish to change?"
  for index, info in enumerate(edit_menu_options):
    if index == 3 or index == 6 or index == 7:
      print "[" + str(index+1) + "] " + info
    else:
      print "[" + str(index+1) + "] " + info,   # NEXT PRINT ON THE SAME LINE
  while True:
    edit_menu_option = raw_input("Enter choice: ").strip() # ASK THE USER WHICH INFO TO EDIT [1-7] OR NONE [8]
    
    # USER WANTS TO EDIT STUDENT NUMBER
    if edit_menu_option == "1":
      new_info = raw_input("Enter new %s: " % edit_menu_options[int(edit_menu_option)-1].lower().strip())
      asean_phonebook.edit_student_number(sn, new_info)
      return [True, new_info]   # SPECIAL CASE: RETURN NEW STUDENT ID TO CONTINUE EDITING THIS PHONEBOOK ENTRY
    
    # USER WANTS TO EDIT SURNAME  
    elif edit_menu_option == "2":
      new_info = raw_input("Enter new %s: " % edit_menu_options[int(edit_menu_option)-1].lower().strip())
      asean_phonebook.edit_surname(sn, new_info)
      return [True, sn]
    
    # USER WANTS TO EDIT GENDER
    elif edit_menu_option == "3":
      new_info = raw_input("Enter new %s: " % edit_menu_options[int(edit_menu_option)-1].lower().strip())
      asean_phonebook.edit_gender(sn, new_info)
      return [True, sn]
    
    # USER WANTS TO EDIT OCCUPATION
    elif edit_menu_option == "4":
      new_info = raw_input("Enter new %s: " % edit_menu_options[int(edit_menu_option)-1].lower().strip())
      asean_phonebook.edit_occupation(sn, new_info)
      return [True, sn]
    
    # USER WANTS TO EDIT COUNTRY CODE
    elif edit_menu_option == "5":
      new_info = int(raw_input("Enter new %s: " % edit_menu_options[int(edit_menu_option)-1].lower().strip()))
      asean_phonebook.edit_country_code(sn, new_info)
      return [True, sn]
    
    # USER WANTS TO EDIT AREA CODE  
    elif edit_menu_option == "6":
      new_info = int(raw_input("Enter new %s: " % edit_menu_options[int(edit_menu_option)-1].lower().strip()))
      asean_phonebook.edit_area_code(sn, new_info)
      return [True, sn]
    
    # USER WANTS TO EDIT PHONE NUMBER 
    elif edit_menu_option == "7":
      new_info = int(raw_input("Enter new %s: " % edit_menu_options[int(edit_menu_option)-1].lower().strip()))
      asean_phonebook.edit_number(sn, new_info)
      return [True, sn]
    
    # USER DONE EDITING
    elif edit_menu_option == "8":   
      return [False, sn]
    
    # NOT ONE OF THE OPTIONS SO ALERT THE USER
    else:
      print "ERROR: Please select an option from [1-8]"

# PRINTS THE SEARCH MENU AND ASKS FOR SEARCH PARAMETERS
def print_search_menu():
  search_menu_options = [
    "No More",
    "Philippines",
    "Thailand",
    "Singapore",
    "Indonesia",
    "Malaysia",
    "ALL"
  ]
  # MAPPING OF CHOICES AND THEIR RESPECTIVE COUNTRY CODE
  search_menu_options_country_code = {
    1: 63,  # PHILIPPINES
    2: 66,  # THAILAND
    3: 65,  # SINGAPORE
    4: 62,  # INDONESIA
    5: 60   # MALAYSIA
  }
  print "From which country:"
  # PRINT THE COUNTRY SELECTION
  print_last = ""
  for index, search_menu_option in enumerate(search_menu_options):
    if index == 0:
      print_last = "[%d] %s" % (index, search_menu_options[index])
    elif index == 4:
      print "[" + str(index) + "] " + search_menu_option
    else:
      print "[" + str(index) + "] " + search_menu_option,
    if index == (len(search_menu_options)-1):
      print print_last
  
  # GET USER INPUT
  user_input_country_code_list = []
  for i in range(len(country_codes)):
    user_input = ""
    while True:
      user_input = raw_input("Enter choice %d:" % (i+1)).strip()
      if user_input == "0" or user_input == "1" or user_input == "2" or user_input == "3" or user_input == "4" or user_input == "5" or user_input == "6":
        break
      else:
        print "ERROR: Please enter an option from [0-6]"
    # USER IS DONE SELECTING COUNTRIES TO SEARCH
    if user_input == "0":
      break
    # USER WANTS ALL COUNTRIES SEARCHED
    elif user_input == "6":
      user_input_country_code_list = [x for x in country_codes.keys()]
      break
    # USER IS NOT FINISHED SELECTING COUNTRIES
    else:
      user_input_country_code_list.append(search_menu_options_country_code[int(user_input)])
  
  # PRINT INFORMATION
  country_string = ""
  if len(user_input_country_code_list) == 0:
    print "You did not select any country. Returning to main menu..."
    return
  elif len(user_input_country_code_list) == 1:
    country_string = country_string + country_codes[user_input_country_code_list[0]] + ":"
  elif len(user_input_country_code_list) == 2:
    country_string = country_string + country_codes[user_input_country_code_list[0]] + " and " + country_codes[user_input_country_code_list[1]] + ":"
  else:
    for i in range(len(user_input_country_code_list)):
      if i < len(user_input_country_code_list)-1:
        country_string = country_string + country_codes[user_input_country_code_list[i]] + ", "
      else:
        country_string = country_string + "and " + country_codes[user_input_country_code_list[len(user_input_country_code_list)-1]] + ":"
      
  print "\nHere are the students from %s" % country_string
  
  # FIND THE STUDENTS WITH USER-SELECTED COUNTRY CODE IN THE PHONEBOOK
  student_list_with_selected_country_codes = asean_phonebook.search_by_country_code_list(user_input_country_code_list)
  # SORT THE STUDENT LIST BY SURNAME
  sorted_list = asean_phonebook.sort_by_surname(student_list_with_selected_country_codes)
  # PRINT STUDENT INFO PER LINE
  for s in sorted_list:
    print asean_phonebook.get_long_desc(s)
  print

"""
  Main program. The main program does not "mess" with Student objects directly (besides instantiating), 
  but rather uses Phonebook methods to add, modify, or search the phonebook. Phonebook variables are 
  also not accessed directly.
  
  Assumptions/Particulars:
    - no student will have the same student number
    - we utilize this fact to refer to a phonebook entry (i.e. Student object) and not by its object reference
    - every entry in the phonebook has complete details (i.e. no empty fields)
    - all student information entered by the user are valid with these data types:
        string:   student_number,
        string:   surname, 
        string:   first_name, 
        string:   occupation, 
        string:   gender, [M/F] otherwise defaults to pronoun "his"
           int:   country_code, 
           int:   area_code, 
           int:   number
"""
# CREATE THE PHONEBOOK THAT WILL STORE OUR STUDENT ENTRIES
asean_phonebook = Phonebook()

"""# INPUT SAMPLE DATA
new_entry1 = Student("2004-56", "Lee", "Sukarno", "doctor", "M", 63, 2, 4567890)
new_entry2 = Student("2000-123", "Saint", "John", "doctor", "M", 63, 2, 9998765)
new_entry3 = Student("1999-890", "Krap", "Sawadi", "sorcerer", "M", 66, 8, 12334567)
new_entry4 = Student("1991-000", "Dela Cruz", "Juliana", "princess", "F", 63, 6, 678123890)
asean_phonebook.add(new_entry1)
asean_phonebook.add(new_entry2)
asean_phonebook.add(new_entry3)
asean_phonebook.add(new_entry4)
print asean_phonebook"""

# SHOW THE USER THE MAIN MENU UNTIL USER SELECTS A CORRECT OPTION
while True:
  print_main_menu()
  option = raw_input("Select an option: ").strip()
  
  # OPTION 1: ADD TO PHONEBOOK
  if option == "1":
    user_input = "Y"
    while True:
      if user_input == "n" or user_input == "N":
        break
      elif user_input == "y" or user_input == "Y":
        new_entry = create_entry()
        # CHECK IF THERE'S A STUDENT WITH student_number IN THE PHONEBOOK
        if asean_phonebook.check_entry(new_entry.student_number) == False:
          asean_phonebook.add(new_entry)
        else:
          print "ERROR: Student number %s is already in the phonebook. Edit the entry instead." % new_entry.student_number
      else:
        print "ERROR: Please answer [Y/N]"
      user_input = raw_input("Do you want to enter another entry [Y/N]? " ).strip()
  
  # OPTION 2: EDIT PHONEBOOK
  elif option == "2":
    if asean_phonebook.size() == 0:  # THERE'S NOTHING TO EDIT!
      print "There are no phonebook entries. Returning to main menu..."
    else:
      user_input_sn = ""
      while True: # USER MUST ENTER A STUDENT NUMBER EXISTING IN THE PHONEBOOK TO EDIT
        user_input_sn = raw_input("Enter student number: ").strip()
        if asean_phonebook.check_entry(user_input_sn) == True:
          break
        else:
          print "ERROR: Student number %s is not in the phonebook" % user_input_sn
      flag = [True, user_input_sn] # flag HAS 2 VALUES TO ADDRESS CHANGES IN student_number; see print_edit_menu()
      while True: # CONTINUE DISPLAYING EDIT MENU UNTIL USER FINISHES EDITING A STUDENT
        flag = print_edit_menu(flag[1])   # WE FOUND A STUDENT TO EDIT SO DISPLAY THE EDIT MENU
        if flag[0] == False:
          break
  
  # OPTION 3: SEARCH PHONEBOOK
  elif option == "3":
    print_search_menu()
  
  # OPTION 4: EXIT
  elif option == "4":
    print "Phonebook closing..."
    break
  
  # NOT ONE OF THE OPTIONS SO ALERT THE USER
  else:
    print "ERROR: Please select an option from below"

