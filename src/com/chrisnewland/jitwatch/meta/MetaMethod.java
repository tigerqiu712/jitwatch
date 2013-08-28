package com.chrisnewland.jitwatch.meta;

import java.lang.reflect.Method;

public class MetaMethod extends AbstractMetaMember implements Comparable<MetaMethod>
{
	private Method method;

	public MetaMethod(Method method, MetaClass methodClass)
	{
		this.method = method;
		this.methodClass = methodClass;
	}

	@Override
	public String toString()
	{
		String methodSigWithoutThrows = method.toString();

		int closingParentheses = methodSigWithoutThrows.indexOf(')');

		if (closingParentheses != methodSigWithoutThrows.length() - 1)
		{
			methodSigWithoutThrows = methodSigWithoutThrows.substring(0, closingParentheses + 1);
		}

		return methodSigWithoutThrows;
	}

	@Override
	public String getSignatureRegEx()
	{
		String unqualifiedSig = makeUnqualified(method.toString());

		return unqualifiedSig;
	}

	@Override
	public String getSignatureForBytecode()
	{
		String ts = method.toString();

		int openParams = ts.lastIndexOf('(');

		if (openParams != -1)
		{
			int pos = openParams;

			int lastDot = -1;

			while (pos-- > 0)
			{
				if (ts.charAt(pos) == '.' && lastDot == -1)
				{
					lastDot = pos;
				}

				if (ts.charAt(pos) == ' ')
				{
					break;
				}
			}

			StringBuilder builder = new StringBuilder(ts);
			builder.delete(pos + 1, lastDot + 1);
			ts = builder.toString();

		}

		return ts;
	}

	@Override
	public int compareTo(MetaMethod o)
	{
		if (o == null)
		{
			return -1;
		}
		else
		{
			return toString().compareTo(o.toString());
		}
	}
}