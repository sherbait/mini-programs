#
# print: Test driver for printf subroutine.
#
# @author Dinia Gepte
# Assignment:
#   Homework #10 in CSE220
#
main:
	la	$a0, arg	# Load format string to $a0
	addiu	$sp, $sp, -24	# Allocate memory for the other arguments
	la	$t0, string	# Load string argument to register
	or	$s0, $zero, $sp	# Store stack pointer
loop:				# Store string argument to stack
	lb	$t9, 0($t0)	# Transfer character from string
	sb	$t9, 0($sp)
	addi	$t0, $t0, 1	# Increment pointers
	addi	$sp, $sp, 1
	bnez	$t9, loop
	or	$sp, $zero, $s0	# Restore stack pointer
	lb	$t9, char	# Store char argument into stack
	sb	$t9, 6($sp)
	lw	$t9, int	# Store int argument into stack 
	sw	$t9, 8($sp)
	ldc1	$f0, double	# Store double argument into stack
	sdc1	$f0, 16($sp)
	jal	printf		# Call printf
end:	
	addiu	$sp, $sp, 24	# Deallocate stack
	li	$v0, 10
	syscall
	
	.data
arg:	.asciiz	"string: %s\nchar: %c\nint: %d\ndouble: %f"
string:	.asciiz	"Hello"
char:	.byte	'A'
int:	.word	345
double:	.double	-20.9

#
# printf: Subroutine that is a simplified version of the C library function.
#	Assumes $a0 points to the format string,
#	and with all arguments passed on the stack
#	(aligned appropriately for their widths).
#	Accepts the following conversion specifications:
#	  %d, %s, %c, and %f (type double)
#
	.text
printf:
	or	$s0, $zero, $sp	# Store stack pointer
	or	$s1, $zero, $a0	# Store address of format string
	or	$t0, $zero, $a0
read:	
	lb	$t1, 0($t0)	# Read a character
	beq 	$t1, '%', fs	# '%' indicates a following format specifier
	move	$a0, $t1
	li	$v0, 11		# Print char system call
	syscall
	addi	$t0, $t0, 1	# Increment pointer
	bnez	$t1, read
	b 	endprintf
fs:
	addi	$t0, $t0, 1	# Increment pointer
	lb	$t1, 0($t0)	# Read the character after '%'
	addi	$t0, $t0, 1	# Increment pointer
	beq 	$t1, 'c', c	# Branch to char(c) handler
	beq	$t1, 's', s	# Branch to string(s) handler
	beq	$t1, 'd', d	# Branch to integer(d) handler
	beq	$t1, 'f', f	# Branch to double(f) handler
	b	endprintf
c:
	lb	$a0, 0($sp)	# Read character from stack
	addi	$sp, $sp, 1	# Increment stack pointer
	li	$v0, 11		# Print char system call
	syscall
	b	read
s:
	lb	$a0, 0($sp)	# Read character from stack
	addi	$sp, $sp, 1	# Increment stack pointer
	li	$v0, 11		# Print char system call
	syscall
	bnez	$a0, s
	b	read
d:
	sub	$t9, $sp, $s0	# Find the "location" of $sp from base stack
	ori	$t8, $zero, 4	# 4 (bytes) will be used for modulo division
	div	$t9, $t8	# Determine alignment of current $sp
	mfhi	$t7		# Store remainder to $t7
	beqz	$t7, nextd	# Value of 0 means $sp is a factor of 4 (i.e. aligned)
	addi	$sp, $sp, 1	# Increment stack pointer
	b 	d		# Loop until $sp is aligned
nextd:	lw	$a0, 0($sp)	# Read int from stack
	addi	$sp, $sp, 4	# Increment 4 bytes to stack pointer
	li	$v0, 1		# Print int system call
	syscall
	b	read
f:
	sub	$t9, $sp, $s0	# Find the "location" of $sp from base stack
	ori	$t8, $zero, 8	# 8 (bytes) will be used for modulo division
	div	$t9, $t8	# Determine alignment of current $sp
	mfhi	$t7		# Store remainder to $t7
	beqz	$t7, nextf	# Value of 0 means $sp is a factor of 8 (i.e. aligned)
	addi	$sp, $sp, 1	# Increment stack pointer
	b 	f		# Loop until $sp is aligned
nextf:	ldc1	$f12, 0($sp)	# Read double from stack
	addi	$sp, $sp, 8	# Increment 8 bytes to stack pointer
	li	$v0, 3		# Print double system call
	syscall
	b	read
endprintf:
	or	$a0, $zero, $s1	# Restore format string pointer
	or	$sp, $zero, $s0	# Restore stack pointer
	jr	$ra