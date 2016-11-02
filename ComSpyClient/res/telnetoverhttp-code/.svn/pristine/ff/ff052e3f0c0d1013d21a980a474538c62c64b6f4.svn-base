using System;
using System.Text;

namespace Soht.Client.DotNet.Common.Connection
{
	/// <summary>
	/// Base class for Connection classes.
	/// </summary>
	public abstract class AbstractConnection
	{
		protected String Encode( byte[] bytes, int length )
		{
			StringBuilder encodedData = new StringBuilder();

			for( int index = 0; index < length; index++ )
			{
				if( NeedsEncoding( bytes[index] ) )
				{
					encodedData.Append( Encode( bytes[index] ) );
				}
				else
				{
					encodedData.Append( (char) bytes[index] );
				}
			}

			return encodedData.ToString();
		}

		protected bool NeedsEncoding( byte data )
		{
			bool result = true;
			if( data >= 33 && data <= 126 )
			{
				result = (data == '%' || data == '?' || data == '@' ||
					data == '&' || data == '=' || data == '+' || data == ':' || data == '#' );
			}
			return result;
		}

		protected string Encode( byte data )
		{
			StringBuilder result = new StringBuilder( "#", 3 );

			string hexString = data.ToString( "x" );
			if( hexString.Length == 1 )
			{
				hexString = "0" + hexString;
			}

			result.Append( hexString );
			
			return result.ToString();
		}
	}
}
