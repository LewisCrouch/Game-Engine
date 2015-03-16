package lewiscrouch.lib.validation;

/**
 * Used to apply constraints to a number and validate those constraints.
 * @author Lewis
 *
 */
public class NumberConstraints
{
	private int min;
	private int max;
	private boolean odd;
	private boolean hasMin;
	private boolean hasMax;
	private boolean isOddOrEven;

	public NumberConstraints()
	{
		this.min = 0;
		this.max = 0;
		this.odd = true;
		this.hasMin = false;
		this.hasMax = false;
		this.isOddOrEven = false;
	}

	/**
	 * Validate the number, ensuring that it matches each constraint applicable.
	 * @param n
	 * @return
	 */
	public boolean validate(int n)
	{
		if(this.hasMin)
		{
			if(n < this.min) return false;
		}
		if(this.hasMax)
		{
			if(n > this.max) return false;
		}
		if(this.isOddOrEven)
		{
			if((n & 1) == 0)
			{
				if(!this.isEven()) return false;
			}
			else
			{
				if(!this.isOdd()) return false;
			}
		}
		return true;
	}

	public int getMin()
	{
		return this.min;
	}

	public void setMin(int min)
	{
		this.hasMin = true;
		this.min = min;
	}

	public boolean hasMin()
	{
		return this.hasMin;
	}

	public void setHasMin(boolean hasMin)
	{
		this.hasMin = hasMin;
	}

	public int getMax()
	{
		return this.max;
	}

	public void setMax(int max)
	{
		this.hasMax = true;
		this.max = max;
	}

	public boolean hasMax()
	{
		return this.hasMax;
	}

	public void setHasMax(boolean hasMax)
	{
		this.hasMax = hasMax;
	}

	public boolean isOdd()
	{
		return this.odd;
	}

	public void setOdd()
	{
		this.isOddOrEven = true;
		this.odd = true;
	}

	public boolean isEven()
	{
		return !this.odd;
	}

	public void setEven()
	{
		this.isOddOrEven = true;
		this.odd = false;
	}

	public boolean isOddOrEven()
	{
		return this.isOddOrEven;
	}

	public void setIsOddOrEven(boolean isOddOrEven)
	{
		this.isOddOrEven = isOddOrEven;
	}
}