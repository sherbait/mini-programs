import java.io.Serializable;
import java.util.Hashtable;

/**
 * This <CODE>SpamFilter</CODE> class does the work of a spam filter. It
 * contains a <CODE>Hashtable</CODE> instance variable that stores the "bad"
 * words.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #6 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class SpamFilter implements Serializable
{
	// Instance variable
	private Hashtable badWords;
	
	/**
	 * Constructor for the <CODE>SpamFilter</CODE> class that constructs
	 * an instance of this class.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>SpamFilter</CODE> will have an empty hash table.
	 */
	public SpamFilter()
	{
		badWords = new Hashtable();
	}
	
	/**
	 * Inserts a word into the <CODE>Hashtable</CODE>.
	 * @param word
	 *   - <CODE>String</CODE> value to be inserted into the
	 *   <CODE>Hashtable</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>SpamFilter</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>SpamFilter</CODE> will have an element added to its
	 *   <CODE>Hashtable</CODE>.
	 */
	public void insert(String word)
	{
		badWords.put(word.toUpperCase().hashCode(), word);
	}
	
	/**
	 * Removes a word (case insensitive) from the <CODE>Hashtable</CODE>. If
	 * the<CODE>String</CODE> is not in the <CODE>Hashtable</CODE>, an
	 * exception will be thrown.
	 * @param word
	 *   - <CODE>String</CODE> value to be removed from the
	 *   <CODE>Hashtable</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>SpamFilter</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>SpamFilter</CODE> will have an element removed from its
	 *   <CODE>Hashtable</CODE> if it is in the hash table prior to execution.
	 * @throws ElementNotFoundException
	 *   Indicates that the parameter is not in the <CODE>Hashtable</CODE>.
	 */
	public void remove(String word) throws ElementNotFoundException
	{
		if (badWords.get(word.toUpperCase().hashCode()) == null)
			throw new ElementNotFoundException();
		else
			badWords.remove(word.toUpperCase().hashCode());
	}
	
	/**
	 * Checks if the parameter (case insensitive) is found in the
	 * <CODE>Hashtable</CODE>.
	 * @param checkMe
	 *   - <CODE>String</CODE> value to be checked in the
	 *   <CODE>Hashtable</CODE>
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>SpamFilter</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>SpamFilter</CODE> will not be modified.
	 * @return
	 *   <CODE>true</CODE> if the given <CODE>String</CODE> is found within the
	 *   <CODE>Hashtable</CODE>; <CODE>false</CODE> otherwise.
	 */
	public boolean isBadWord(String checkMe)
	{
		return (badWords.get(checkMe.toUpperCase().hashCode()) != null);
	}
	
	/**
	 * Checks if the given message is to be considered spam. It returns the
	 * percentage of words that were found in the <CODE>Hashtable</CODE>.
	 * @param message
	 *   - <CODE>String</CODE> value to be checked for <CODE>Hashtable</CODE>
	 *   element hits
	 * @return
	 *   the percentage of words that were hit.
	 */
	public float checkEmail(String message)
	{
		int badWordsCount = 0;
		String[] splitMessage = message.split(" ");
		
		for (int i = 0; i < splitMessage.length; i++)
			if (badWords.get(splitMessage[i].toUpperCase().hashCode()) != null)
				badWordsCount++;
		
		return (float)badWordsCount/splitMessage.length;
	}
}
