package lewiscrouch.lib.console;

import lewiscrouch.lib.validation.NumberConstraints;
import lewiscrouch.lib.validation.StringConstraints;

public class Test
{
	public static void main(String[] args)
	{
		Output.writeTitle("MyGame");
		Output.newLine();

		String[] answers = new String[] { "Red", "Green", "Blue", "Yellow" };

		Test.outputFormatting(answers);
		Test.input();
		Test.stringConstraints(answers);
		Test.numberConstraints(answers);
	}

	public static void outputFormatting(String[] arr)
	{
		Output.writeLine("Some colors:");
		Output.writeArr(arr);
		Output.newLine();
	}

	public static void input()
	{
		Output.writeLine("Say something!");
		Output.writeLine("You said: " + Input.readStr());
		Output.newLine();
	}

	public static void stringConstraints(String[] answers)
	{
		StringConstraints sc = new StringConstraints();
		sc.setMinLength(1);
		sc.setMaxLength(1);
		sc.setAllowedChars(new char[] { 'a', 'b', 'c', 'd', 'A', 'B', 'C', 'D' });

		Output.writeLine("What colour is grass?");
		Output.writeLetteredArr(answers);
		String choice = Input.forceReadStr(sc, "Enter A, B, C or D!");
		String answer = "b";
		if(answer.equalsIgnoreCase(choice))
		{
			Output.writeLine("Correct!");
		}
		else
		{
			Output.writeLine("Incorrect!");
		}
		Output.newLine();
	}

	public static void numberConstraints(String[] answers)
	{
		NumberConstraints nc = new NumberConstraints();
		nc.setMin(1);
		nc.setMax(4);

		Output.writeLine("What colour is grass?");
		Output.writeNumberedArr(answers);
		int c = Input.forceReadNum(nc, "Enter 1, 2, 3 or 4!");
		int a = 2;
		if(c == a)
		{
			Output.writeLine("Correct!");
		}
		else
		{
			Output.writeLine("Incorrect!");
		}
		Output.newLine();
	}
}
