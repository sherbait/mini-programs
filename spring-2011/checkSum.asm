#
# checkSum: Test driver for cksum, setsum subroutines.
#
# @author Dinia Gepte, 107092681
# Assignment:
# 	Homework #9 in CSE220
#
main:	la	$a0, data1	# First buffer
	la	$a1, 4
	jal	setsum
	la	$a0, data1
	jal	cksum
	bnez	$v0, bad
	la	$a0, data2	# Second buffer
	la	$a1, 15
	jal	setsum
	la	$a0, data2
	jal	cksum
	bnez	$v0, bad
	la	$a0, data3	# Third buffer
	la	$a1, 20
	jal	setsum
	la	$a0, data3
	jal	cksum
	bnez	$v0, bad
good:	la	$a0, msg1
	la	$v0, 4		# Print string system call
	syscall
	b 	end
bad:	la	$a0, msg2
	la	$v0, 4		# Print string system call
	syscall
	b	end
end:	la	$v0, 10
	syscall
	.data
data1:	.byte	'a', 'b', 'c'
space1:	.space	1
data2:	.asciiz "Hello, world!"
space2:	.space	1
data3:	.space	20
msg1:	.asciiz	"Data transmission successful!"
msg2:	.asciiz	"Data transmission failed!"

#
# cksum: Subroutine that calculates the sum of N bytes in a buffer.
#	 Takes the following arguments:
#		$a0	Starting address of a buffer in the data segment
#		$a1	Size of the buffer, in bytes
#	 Returns:
#		$v0	Sum of all bytes in the buffer
#
	.text
cksum:	la	$v0, 0		# $v0: sum of all bytes
	la	$t2, ($a1)	# $t2: counter
loop1:	lb	$t1, 0($a0)	# Load next byte
	addi	$v0, $v0, 1	# Add byte to sum
	addi	$a0, $a0, 1	# Advance to next byte
	addi	$t2, $t2, -1	# Decrement counter
	bgt	$t2, 1, loop1	# Loop while counter > 1
done1:	lb	$t1, 0($a0)	# Load the last byte
	add	$v0, $v0, $t1	# Add (sum of N-1) and (N)
	jr	$ra

#
# setsum: Subroutine that stores the last byte of the buffer the negative
#	  of the sum of all the preceding bytes.
#	  Takes the following arguments:
#		$a0	Starting address of a buffer in the data segment
#		$a1	Size of the buffer, in bytes
#
setsum:	la	$t0, 0		# $t0: sum of N-1 bytes
	la	$t2, ($a1)	# $t2: counter
loop2:	lb	$t1, 0($a0)	# Load next byte
	addi	$t0, $t0, 1	# Add byte to sum
	addi	$a0, $a0, 1	# Advance to next byte
	addi	$t2, $t2, -1	# Decrement counter
	bgt	$t2, 1, loop2	# Loop while counter > 1
done2:	div	$t0, $t0, -1	# Negative sum of N-1 bytes
	sb	$t0, 0($a0)	# Set the last byte of the buffer to the negative sum
	jr	$ra