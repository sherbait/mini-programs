import java.awt.Color;

public class ColoredBox implements Comparable
{
	private int num;
	private Color color;
	
	public ColoredBox(int initNum, Color initColor)
	{
		num = initNum;
		color = initColor;
	}
	
	public int getNum() 	{ return num; 	}
	public Color getColor() { return color; }
	
	public int compareTo(Object obj)
	{
		ColoredBox otherBox = (ColoredBox)obj;
		if (num < otherBox.num)
			return -1;
		else if (num == otherBox.num)
			return 0;
		else
			return 1;
	}
}
