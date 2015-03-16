package lewiscrouch.lib.validation;

/**
 * Used to apply constraints to a string and validate those constraints.
 * @author Lewis
 *
 */
public class StringConstraints
{
	private int minLength;
	private int maxLength;
	private char[] illegalChars;
	private char[] allowedChars;

	public StringConstraints()
	{
		this.minLength = 0;
		this.maxLength = 0;
		this.illegalChars = new char[] { };
		this.allowedChars = new char[] { };
	}

	/**
	 * Validate the string, ensuring that it matches each constraint applicable.
	 * @param str
	 * @return
	 */
	public boolean validate(String str)
	{
		if(this.hasMinLength())
		{
			if(str.length() < this.minLength) return false;
		}
		if(this.hasMaxLength())
		{
			if(str.length() > this.maxLength) return false;
		}
		if(this.hasAllowedChars())
		{
			for(char c : str.toCharArray())
			{
				boolean allowed = false;
				for(char c1 : this.allowedChars)
				{
					if(c == c1) allowed = true;
				}

				if(!allowed) return false;
			}
		}
		if(this.hasIllegalChars())
		{
			for(char c : str.toCharArray())
			{
				for(char c1 : this.illegalChars)
				{
					if(c == c1) return false;
				}
			}
		}
		return true;
	}

	public int getMinLength()
	{
		return this.minLength;
	}

	public void setMinLength(int minLength)
	{
		this.minLength = minLength;
	}

	public boolean hasMinLength()
	{
		return this.minLength != 0;
	}

	public int getMaxLength()
	{
		return this.maxLength;
	}

	public void setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
	}

	public boolean hasMaxLength()
	{
		return this.maxLength != 0;
	}

	public char[] getIllegalChars()
	{
		return this.illegalChars;
	}

	public void setIllegalChars(char[] illegalChars)
	{
		this.illegalChars = illegalChars;
	}

	public boolean hasIllegalChars()
	{
		return this.illegalChars.length != 0;
	}

	public char[] getAllowedChars()
	{
		return this.allowedChars;
	}

	public void setAllowedChars(char[] allowedChars)
	{
		this.allowedChars = allowedChars;
	}

	public boolean hasAllowedChars()
	{
		return this.allowedChars.length != 0;
	}
}