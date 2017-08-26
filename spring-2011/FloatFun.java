/**
 * This class has methods to extract the three fields of a <CODE>float</CODE>
 * (i.e. IEEE 754 single precision) value. <br> It also has an inverse mapping
 * method that returns the corresponding floating-point value.
 * 
 * @author Dinia Gepte
 * <br>CSE220 Homework 3
 */
public class FloatFun
{
	public FloatFun()	{	}
	
	static int getSign(float value)
	{
		Float mask = new Float(-0.0);
		Float valueWrap = new Float(value);
		
		if ((valueWrap.floatToIntBits(value) & mask.floatToIntBits(mask)) == mask)
			return 0;
		else
			return 1;
	}
	
	static int getExponent(float value)
	{
		Float valueWrap = new Float(value);
		FloatFun testForSign = new FloatFun();
		
		// Getting the absolute value of the float number
		if (testForSign.getSign(valueWrap) == 1)
			valueWrap = valueWrap.intBitsToFloat((valueWrap.floatToIntBits(valueWrap) << 1) >>> 1);
		
		return (valueWrap.floatToIntBits(valueWrap) >>> 23) - 127;
	}
	
	static int getFraction(float value)
	{
		Float mask = new Float(1.0);
		Float valueWrap = new Float(value);
		
		// Getting the fractional part of the floating-point
		valueWrap = valueWrap.intBitsToFloat((valueWrap.floatToIntBits(valueWrap) << 9) >>> 9);
		// Normalization
		valueWrap = valueWrap.intBitsToFloat(valueWrap.floatToIntBits(valueWrap)
		  | mask.floatToIntBits(mask));
		
		// Multiplication by 2^23
		for (int i = 0; i < 23; i++)
			valueWrap *= 2;
		
		return valueWrap.intValue();
	}
	
	static float makeFloat(int sign, int exp, int frac)
	{
		Float signWrap, fracWrap = (float)frac;
		Float floatNum = new Float(0);
		
		// SIGN bit
		if (sign == 1)
			signWrap = new Float(-0.0);
		else
			signWrap = new Float(0.0);
		
		// EXPONENT bits
		exp = (exp + 127) << 23;
		
		// FRACTION bits
		for (int i = 0; i < 23; i++)
			fracWrap /= 2;
		fracWrap = fracWrap.intBitsToFloat((fracWrap.floatToIntBits(fracWrap) << 9) >>> 9);
		
		return floatNum.intBitsToFloat(floatNum.floatToIntBits(signWrap) | exp
		  | floatNum.floatToIntBits(fracWrap));
	}
}
