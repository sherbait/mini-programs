#
# This program is a reproduction of the game MOO ("Bulls and Cows")
# originally written by Ken Thompson in the early 1970's. In this game, the
# computer randomly chooses a hidden sequence of four distinct digits. The
# user then makes a series of guesses, each of which itself a sequence of four
# digits. For each guess, the computer reports on the number of correct digits
# in correct positions ("bulls") and the number of correct digits in incorrect
# positions ("cows"). The objective is to discover the hidden sequence in as
# few guesses as possible.
#
# @author Dinia Gepte, 107092681
# Assignment:
#   Homework #11 in CSE220
#
	.data
moo:	.asciiz "MOO\n"
new:	.asciiz	"1) new game\n"
quit:	.asciiz	"2) quit\n"
error1:	.asciiz	"\nno option for entered input\n"
prompt:	.asciiz	"? "

	.text
main:
	la	$a0, moo	# Game header
	li	$v0, 4		# Print string syscall
	syscall
mainLoop:	
	li	$v0, 4		# Print string syscall
	la	$a0, new	# New game option
	syscall
	la	$a0, quit	# Quit option
	syscall
	la	$a0, prompt	# User input designator
	syscall
	li	$v0, 12		# Read char syscall
	syscall
	beq	$v0, '2', exit	# User wants to quit the game
	bne	$v0, '1', error	# User enters a number besides 1 and 2
	jal	MOO		# User wants to play the game
	b	mainLoop
error:
	la	$a0, error1
	li	$v0, 4		# Print string syscall
	syscall
	b	mainLoop
exit:	
	li	$v0	10
	syscall

#
# MOO: Subroutine that uses other subroutines to run the core of the game.
#
	.data
newg:	.asciiz	"\nnew game\n"
bulls:	.asciiz	" bulls; "
cows:	.asciiz	" cows\n"
error2: .asciiz "\nbad guess\n"
guess:	.asciiz	" guesses\n\n"
	
	.text
MOO:
	addiu	$sp, $sp, -12	# Allocate space
	sw	$ra, 0($sp)	# Save $ra
	jal	generateNumber
	sw	$v0, 4($sp)	# Save program-generated number
	li	$t0, 0
	sw	$t0, 8($sp)	# Save increment counter for "good" guess
	li	$a0, 5		# Length of user-entered number
	li	$v0, 9		# Heap allocation syscall
	syscall
	move	$s0, $v0
	la	$a0, newg	# New game label
	li	$v0, 4		# Print string syscall
	syscall
mooLoop:
	la	$a0, prompt	# User input designator
	li	$v0, 4		# Print string syscall
	syscall
	move	$a0, $s0	# Address to write string
	li	$a1, 5		# Maximum characters to read
	li	$v0, 8		# Read string syscall
	syscall
	jal	checkInput
	beq	$v0, 1, badIn	# User enters a bad input* (see checkInput)
	lw	$t0, 8($sp)
	addi	$t0, $t0, 1	# Increment counter for "good" guess
	sw	$t0, 8($sp)
	lw	$a1, 4($sp)	# Load random number
	jal	compare
	move	$t0, $v0	# Number of bulls
	move	$t1, $v1	# Number of cows
	li	$a0, '\n'
	li	$v0, 11		# Print char syscall
	syscall
	move	$a0, $t0
	li	$v0, 1		# Print int syscall
	syscall
	la	$a0, bulls
	li	$v0, 4		# Print string syscall
	syscall
	move	$a0, $t1
	li	$v0, 1		# Print int syscall
	syscall
	la	$a0, cows
	li	$v0, 4		# Print string syscall
	syscall
	beq	$t0, 4, mooEnd
	b 	mooLoop
badIn:
	la	$a0, error2
	li	$v0, 4		# Print string syscall
	syscall
	b 	mooLoop
mooEnd:
	lw	$a0, 8($sp)	# Load counter for "good" guess
	addi	$a0, $a0, -1	# Subtract "guess" for correct answer
	li	$v0, 1		# Print int syscall
	syscall
	la	$a0, guess
	li	$v0, 4		# Print string syscall
	syscall
	lw	$ra, 0($sp)	# Restore $ra from stack
	addiu	$sp, $sp, 12	# Deallocate stack memory
	jr	$ra

#
# compare: Subroutine that compares two 4-digit numbers in string format
#	   (with null terminator). It returns results describing how
#	   similar $a0 is to $a1.
#   Parameters:
#	$a0 - number1
#	$a1 - number2
#   Returns:
#	$v0 - number of correct digits in correct positions
#	$v1 - number of correct digits in incorrect positions
#
compare:
	move	$s0, $a0	# Create copies of number parameters
	move	$s1, $a1
	li	$v0, 0		# Reset return registers
	li	$v1, 0
	li	$t0, 0		# Counter
cLoop:
	lb	$t1, 0($s0)
	lb	$t2, 0($s1)
	addi	$s0, $s0, 1	# Increment pointers
	addi	$s1, $s1, 1
	addi	$t0, $t0, 1	# Increment counter
	beq 	$t1, $t2, exact	# Correct digit in correct position
	beq	$t0, 4, cont
	b 	cLoop
exact:
	addi	$v0, $v0, 1	# Increment $v0
	bne 	$t0, 4, cLoop
cont:				# Compare digits forward
	move	$s0, $a0	# Return $s0 values to original
	move	$s1, $a1
	li	$t0, 0		# Reset counter
cLoop1:	lb	$t1, 0($s0)
cLoop2:	lb	$t2, 1($s1)
	addi	$s1, $s1, 1	# Increment pointer
	beq	$t1, $t2, equal
	bnez	$t2, cLoop2
	addi	$s0, $s0, 1	# Move ptr in $s0 to next char to compare
	addi	$s1, $s1, -3	# Move back ptr in $s1 to 2nd char
	addi	$t0, $t0, 1	# Increment counter
	beq	$t0, 1, cLoop1
	addi	$s1, $s1, 1	# Move ptr 1 char forward
	beq	$t0, 2, cLoop1
reverse:			# Compare digits backwards
	la	$s0, 3($a0)
	la	$s1, 2($a1)
cLoop3:	lb	$t1, 0($s0)
cLoop4:	lb	$t2, 0($s1)
	addi	$s1, $s1, -1	# Decrement pointer in second number
	beq	$t1, $t2, equal
cLoop5:	move	$t2, $s1
	addi	$t2, $t2, 1
	bne 	$t2, $a1, cLoop4
	addi	$s0, $s0, -1
	addi	$s1, $s1, 2
	addi	$t0, $t0, 1
	beq	$t0, 4, cLoop3
	addi	$s1, $s1, -1
	beq	$t0, 5, cLoop3
	b 	cEnd
equal:
	addi	$v1, $v1, 1	# Increment $v1
	blt  	$t0, 3, cLoop2
	bge	$t0, 3, cLoop5	
cEnd:	jr	$ra
	

#
# checkInput: Subroutine for MOO game that checks if the user-entered
#	      number fulfills the following criteria:
#		(a) a four-digit number
#		(b) contains distinct digits
#   Parameter:
#	$a0 - memory address of number to check
#   Returns:
#	$v0 - 0(good) or 1(bad)
#
checkInput:
	addiu	$sp, $sp, -4	# Allocate space
	sw	$a0, 0($sp)	# Store memory address of string number
	li	$v0, 0		# $v0 = 0 by default
	li	$t9, 0		# Counter
CIloop:	
	lb	$t0, 0($a0)	#			| Checks if
	beq	$t9, 4, CInext	#			| entered
	blt 	$t0, 48, bad	# 48 = ASCII '0'	| values
	bgt	$t0, 57, bad	# 57 = ASCII '9'	| are numbers
	addi	$a0, $a0, 1	# Increment pointer	| from 0-9.
	addi	$t9, $t9, 1
	bnez	$t0, CIloop
CInext:	lw	$a0, 0($sp)	# Set $a0 to original value
	li	$t9, 0		# Reset counter
loop1:	lb	$t0, 0($a0)
loop2:	lb	$t1, 1($a0)
	beq	$t0, $t1, bad	# Digits are equal
	addi	$a0, $a0, 1	# Increment pointer to next char
	bnez	$t1, loop2
	lw	$a0, 0($sp)	# Reload $a0 back
	addi	$a0, $a0, 1
	addi	$t9, $t9, 1	# Increment counter
	beq	$t9, 1, loop1
	addi	$a0, $a0, 1
	beq	$t9, 2, loop1
	b 	CIend
bad:
	li	$v0, 1
CIend:
	lw	$a0, 0($sp)	# Restore string memory address
	addi	$sp, $sp, 4	# Clear allocated memory in stack
	jr	$ra

#
# generateNumber: Subroutine that generates a number seqence of
#		  four distinct digits represented by a string
#		  with a null terminator.
#   Returns:
#	$v0 - memory address of the string
#
generateNumber:
	li	$a0, 5		# $a0 = number sequence length (in bytes) including NULL
	li	$v0, 9		# Heap memory allocation syscall
	syscall
	move	$s0, $v0	# Save memory address of number sequence
	li	$t0, 1		# $t0 = digit currently randomized
GNloop:
	li	$a1, 9		# Only generate a number of range [0,9]
	li	$v0, 42		# Random int range syscall
	syscall
	addi	$a0, $a0, 48	# char '0' = decimal (48) in ASCII
	beq	$t0, 1, first
	beq	$t0, 2, second
	beq	$t0, 3, third
	beq	$t0, 4, fourth
first:
	sb	$a0, 0($s0)	# 0($s0) = first digit
	addi	$t0, $t0, 1	# Increment digit to be randomized
	b	GNloop
second:
	lb	$t1, 0($s0)
	beq	$t1, $a0, GNloop
	sb	$a0, 1($s0)	# 1($s0) = second digit
	addi	$t0, $t0, 1	# Increment digit to be randomized
	b	GNloop
third:
	lb	$t1, 0($s0)
	beq	$t1, $a0, GNloop
	lb	$t1, 1($s0)
	beq	$t1, $a0, GNloop
	sb	$a0, 2($s0)	# 2($s0) = third digit
	addi	$t0, $t0, 1	# Increment digit to be randomized
	b	GNloop
fourth:
	lb	$t1, 0($s0)
	beq	$t1, $a0, GNloop
	lb	$t1, 1($s0)
	beq	$t1, $a0, GNloop
	lb	$t1, 2($s0)
	beq	$t1, $a0, GNloop
	sb	$a0, 3($s0)	# 3($s0) = fourth digit
GNend:
	li	$t1, '\0'
	sb	$t1, 4($s0)	# 4($s0) = null terminator
	move	$v0, $s0
	jr	$ra