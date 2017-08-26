import java.awt.Color;
/*
 * This class has a method that overrides the method in
 * the BoxSorter interface. It performs RADIX SORT.
 * 
 * @author Dinia Grace Gepte
 * CSE114 HW6
 * L03 TA Yifu Ren
 */
public class RadixSortBoxSorter implements BoxSorter
{
	public void sortBoxes(ColoredBox[] boxes)
	{
		// count ARRAY USED TO TALLY THE NUMBER OF BOXES
		// WITH THE SAME COLOR VALUE BETWEEN 0-255
		int[] count = new int[256];
		for (int i = 0; i < boxes.length; i++)
		{
			// THE BOX COLOR VALUE CORRESPONDS TO THE
			// count ARRAY INDEX NUMBER
			int countIndex = boxes[i].getNum();
			count[countIndex] = count[countIndex] + 1;
		}
		// VARIABLE j IS THE INDEX OF THE CURRENT BOX
		// IN THE boxes ARRAY TO BE "OVERLAPPED" BY THE
		// NEW BOXES
		int j = 0;
		while (j < boxes.length)
		{
			// VARIABLE k IS THE CURRENT count INDEX
			for (int k = 0; k < count.length; k++)
			{
				// EXECUTES WHEN THERE'S AN ACTUAL TALLY VALUE
				// IN THE count VARIABLE WITH INDEX k
				while (count[k] > 0)
				{
					Color c = new Color(k, 0, 0);
					boxes[j] = new ColoredBox(k, c);
					BoxSortingDemo.updateDisplay();
					j++;
					count[k]--;
				}
			}
		}
	}
}
