#
# rollDice: Program with roll subroutine.
#
# @author Dinia Gepte, 107092681
# Assignment:
# 	Homework #9 in CSE220
#
main:	la	$a1, 5		# Maximum number of dice for testing roll subroutine
	la	$v0, 42		# Random int system call
	syscall
	addi	$a0, $a0, 1	# A number N >= 1
	jal	roll		# Call roll
	la	$a1, 10		# Maximum number of dice for testing roll subroutine
	la	$v0, 42		# Random int system call
	syscall
	addi	$a0, $a0, 1	# A number N >= 1
	jal	roll		# Call roll
	la	$v0, 10		# Exit system call
	syscall

#
# roll: Subroutine simulating the rolling of N six-sided dice.
#	Takes the following argument:
#		$a0	A number N >= 1
#	Returns:
#		$v0	Total dice value
#
roll:	la	$t0, ($a0)	# $t0: counter representing N($a0) dice
	la	$t1, 0		# $t1: sum of rolls
	la	$a1, 6		# $a1: upper bound of random int system call
	la	$a0, msg1
	la	$v0, 4		# Print string system call
	syscall
	la	$a0, ($t0)
	la	$v0, 1		# Print int system call
	syscall
loop:	la	$a0, msg2
	la	$v0, 4		# Print string system call
	syscall
	la	$v0, 42		# Random int range system call
	syscall
	addi	$a0, $a0, 1	# A number representing die face (1-6)
	la	$v0, 1		# Print integer system call
	syscall
	add	$t1, $t1, $a0	# Add roll to total
	addi	$t0, $t0, -1	# Decrement the counter
	bgtz	$t0, loop	# Loop while N > 0
done:	la	$a0, msg3
	la	$v0, 4		# Print string system call
	syscall
	la	$a0, ($t1)
	la	$v0, 1		# Print integer system call
	syscall
	la	$v0, ($t1)
	jr	$ra
	.data
msg1:	.asciiz "\nNumber of dice: "
msg2:	.asciiz "\nRoll: "
msg3:	.asciiz	"\nTotal: "