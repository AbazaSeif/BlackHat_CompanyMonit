//System References
using System;
using System.IO;
using System.Net;
using System.Text;

namespace Soht.Client.DotNet.Common.Connection
{
	/// <summary>
	/// Reads Data from the HTTP Proxy service and writes
	/// it to the attached telnet client.
	/// </summary>
	public class ConnectionWriter
	{
		#region Constructor

		/// <summary>
		/// Setup the initial parameters for this connection.
		/// </summary>
		/// <param name="writer"></param>
		/// <param name="uri"></param>
		/// <param name="id"></param>
		public ConnectionWriter( BinaryWriter writer, Uri uri, String id )
		{
			_writer = writer;
			_uri = uri;
			_id = id;
		}

		#endregion
	
		#region Public Methods

		public void Write() 
		{
			WebRequest request = WebRequest.Create( _uri );
			request.Method = "POST";
			request.ContentType = "application/x-www-form-urlencoded";
			StringBuilder parameters = new StringBuilder();
			parameters.Append( "action=read" );
			parameters.Append( "&" );
			parameters.Append( "id=" + _id );
			byte[] encodedParams = Encoding.UTF8.GetBytes( parameters.ToString() );
			request.ContentLength = encodedParams.Length;
			Stream requestStream = request.GetRequestStream();
			requestStream.Write( encodedParams, 0, encodedParams.Length );
			requestStream.Close();

			BinaryReader webReader = new BinaryReader( request.GetResponse().GetResponseStream() );

			byte[] bytesRead = new byte[1024];
			int count;
			bool isFirst = true;
			while( true ) 
			{
				count = webReader.Read( bytesRead, 0, bytesRead.Length );

				//A count of 0 indicates that the stream has been closed.
				if( count == 0 ) 
				{
					break;
				}

                try
                {
                    int startIndex = isFirst ? 1 : 0;
                    _writer.Write(bytesRead, startIndex, count - startIndex);
                    isFirst = false;
                }
                catch (IOException)
                {
                    Console.WriteLine("Error writing data to local client.  Closing Connection");
                    try { webReader.Close(); }
                    catch (Exception) { }
                    try { _writer.Close(); }
                    catch (Exception) { }
                    break;
                }
			}
		}

		#endregion

		#region Variables

		private BinaryWriter _writer;
		private Uri _uri;
		private String _id;		

		#endregion
	}
}
