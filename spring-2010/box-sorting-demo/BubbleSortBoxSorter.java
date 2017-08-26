/*
 * This class has a method that overrides the method in
 * the BoxSorter interface. It performs BUBBLE SORT.
 * 
 * @author Dinia Grace Gepte
 * CSE114 HW6
 * L03 TA Yifu Ren
 */
public class BubbleSortBoxSorter implements BoxSorter
{	
	public void sortBoxes(ColoredBox[] boxes)
	{
		int i = 0, j = 0;
		ColoredBox tempBox = new ColoredBox(boxes[i].getNum(), boxes[i].getColor());
		for (j = boxes.length - 2; j > 0; j--)
		{
			for (i = 0; i <= j; i++)
			{
				if (boxes[i].compareTo(boxes[i+1]) == 1)
				{
					tempBox = boxes[i];
					boxes[i] = boxes[i+1];
					boxes[i+1] = tempBox;
					BoxSortingDemo.updateDisplay();
				}
			}
		}
	}
}
