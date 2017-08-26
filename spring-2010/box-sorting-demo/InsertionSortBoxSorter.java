/*
 * This class has a method that overrides the method in
 * the BoxSorter interface. It performs INSERTION SORT.
 * 
 * @author Dinia Grace Gepte
 * CSE114 HW6
 * L03 TA Yifu Ren
 */
public class InsertionSortBoxSorter implements BoxSorter
{
	public void sortBoxes(ColoredBox[] boxes)
	{		
		int i = 0;
		ColoredBox tempBox = new ColoredBox(boxes[i].getNum(), boxes[i].getColor());
		while (i < boxes.length - 1)
		{
			if (i != 0)
			{
				// VARIABLE j IS USED TO DETERMINE WHERE
				// THE BOX WITH INDEX i+1 IS SUPPOSED TO BE PLACED
				for (int j = 0; j <= i; j++)
				{
					// EXECUTES DURING COMPARISON BETWEEN BOX WITH
					// INDEX i+1 AND THE FIRST BOX OF THE boxes ARRAY
					if (j == 0)
					{
						// EXECUTES WHEN BOX WITH INDEX i+1 IS SMALLER
						// THAN THE FIRST BOX
						if (boxes[i+1].compareTo(boxes[j]) == -1)
						{
							tempBox = boxes[i+1];
							// VARIABLE k IS USED TO DETERMINE HOW MANY
							// SLIDING OF BOXES WILL TAKE PLACE
							for (int k = i ; k >= 0; k--)
							{
								boxes[k+1] = boxes[k];
								BoxSortingDemo.updateDisplay();
							}
							boxes[0] = tempBox;
							BoxSortingDemo.updateDisplay();
						}
					}
					else
					{
						if ( (boxes[i+1].compareTo(boxes[j]) != 1) &&
								(boxes[i+1].compareTo(boxes[j-1]) != -1) )
						{
							tempBox = boxes[i+1];
							for (int k = i; k >= j; k--)
							{
								boxes[k+1] = boxes[k];
								BoxSortingDemo.updateDisplay();
							}
							boxes[j] = tempBox;
							BoxSortingDemo.updateDisplay();
						}
					}
				}
			}
			// OCCURS AT THE BEGINNING OF THE SORTING
			else
			{
				if (boxes[0].compareTo(boxes[1]) == 1)
				{
					tempBox = boxes[1];
					boxes[1] = boxes[0];
					boxes[0] = tempBox;
					BoxSortingDemo.updateDisplay();
				}
			}
			i++;
		}
		
	}
}