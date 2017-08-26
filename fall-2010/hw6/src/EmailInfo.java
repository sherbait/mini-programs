/**
 * This <CODE>EmailInfo</CODE> class represents an email. It contains variables
 * that represent the recipient, author, date received, subject, and message of
 * the email.  
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #6 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class EmailInfo
{
	// Instance variables
	private String to;
	private String from;
	private String date;
	private String subject;
	private String message;
	
	/**
	 * Constructor for the <CODE>EmailInfo</CODE> class that constructs
	 * an instance of this class.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> will be created without any values to the
	 *   instance variables. 
	 */
	public EmailInfo()		{	}
	
	/**
	 * Sets the email recipient variable to the given parameter.
	 * @param t
	 *   - <CODE>String</CODE> value to be set as the recipient
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> will have a recipient as the parameter.
	 */
	public void setTo(String t)			{	to = t;		}
	
	/**
	 * Sets the email author variable to the given parameter. 
	 * @param f
	 *   - <CODE>String</CODE> value to be set as the author
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> will have an author as the parameter.
	 */
	public void setFrom(String f)		{	from = f;	}
	
	/**
	 * Sets the email receive date variable to the given parameter.
	 * @param d
	 *   - <CODE>String</CODE> value to be set as the date
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> will have a date as the parameter.
	 */
	public void setDate(String d)		{	date = d;	}
	
	/**
	 * Sets the email subject variable to the given parameter.
	 * @param s
	 *   - <CODE>String</CODE> value to be set as the subject
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> will have a subject as the parameter.
	 */
	public void setSubject(String s)	{	subject = s;	}
	
	/**
	 * Sets the email message variable to the given parameter.
	 * @param m
	 *   - <CODE>String</CODE> value to be set as the message
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> will have a message as the parameter.
	 */
	public void setMessage(String m)	{	message = m;	}
	
	/**
	 * Returns a <CODE>String</CODE> value of the email's recipient.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> will not be modified.
	 * @return
	 *   - the email's recipient.
	 */
	public String getTo()		{	return to;	}
	
	/**
	 * Returns a <CODE>String</CODE> value of the email's author.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> will not be modified.
	 * @return
	 *   - the email's author.
	 */
	public String getFrom()		{	return from;	}
	
	/**
	 * Returns a <CODE>String</CODE> value of the email's receive date.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> will not be modified.
	 * @return
	 *   - the email's receive date
	 */
	public String getDate()		{	return date;	}
	
	/**
	 * Returns a <CODE>String</CODE> value of the email's subject.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> will not be modified.
	 * @return
	 *   - the email's subject.
	 */
	public String getSubject()	{	return subject;	}
	
	/**
	 * Returns a <CODE>String</CODE> value of the email's message.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>EmailInfo</CODE> will not be modified.
	 * @return
	 *   - the email's message.
	 */
	public String getMessage()	{	return message;	}
}
