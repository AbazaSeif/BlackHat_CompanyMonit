//System References
using System;
using System.IO;
using System.Net;
using System.Text;

namespace Soht.Client.DotNet.Common.Connection
{
	/// <summary>
	/// Reads data from the attached telnet client and writes 
	/// it to the HTTP proxy service.
	/// </summary>
	public class ConnectionReader : AbstractConnection
	{
		#region Variables

		private BinaryReader _reader;
		private Uri _uri;
		private String _id;

		#endregion

		#region Constructor

		/// <summary>
		/// Setup the initial parameters for this connection.
		/// </summary>
		/// <param name="reader"></param>
		/// <param name="uri"></param>
		/// <param name="id"></param>
		public ConnectionReader( BinaryReader reader, Uri uri, String id )
		{
			_reader = reader;
			_uri = uri;
			_id = id;
		}

		#endregion

		#region Public Methods

		public void Read() 
		{
			byte[] bytes = new byte[1024];
			int count;
			try 
			{
				while( true ) 
				{
                    count = _reader.Read(bytes, 0, bytes.Length);
                    //Stop the loop if there were 0 bytes read.
                    //This indicates that the connection was closed.
                    if (count == 0)
                    {
                        break;
                    }

                    bool success = false;
                    int attempt = 1;
                    do
                    {
                        try
                        {
                            WriteBytes(bytes, count);
                            success = true;
                        }
                        catch (WebException we)
                        {
                            Console.WriteLine("Error writing bytes to server.  Attempt #:" + attempt);
                            Console.WriteLine("Error: " + we.ToString());
                            attempt++;
                            success = false;
                            System.Threading.Thread.Sleep(50);
                        }
                    }
                    while (success == false && attempt < 6);

                    if (!success) Console.WriteLine("Error writing data to server.  Retried " + (attempt - 1) + " times.");

                    // Fail if the bytes could not be written.
                    if(!success) throw new IOException();
				}
			}
			catch( IOException )
			{
				Console.WriteLine( "Connection Closed due to IO Exception." );				
			}
			finally 
			{
                try
                {
                    //Tell the HTTP Proxy Service to close it's connections.
                    WebRequest closeRequest = WebRequest.Create(_uri);
                    closeRequest.Method = "POST";
                    closeRequest.ContentType = "application/x-www-form-urlencoded";
                    StringBuilder closeParameters = new StringBuilder();
                    closeParameters.Append("action=close");
                    closeParameters.Append("&");
                    closeParameters.Append("id=" + _id);
                    byte[] closeEncodedParams = Encoding.UTF8.GetBytes(closeParameters.ToString());
                    closeRequest.ContentLength = closeEncodedParams.Length;
                    Stream closeRequestStream = closeRequest.GetRequestStream();
                    closeRequestStream.Write(closeEncodedParams, 0, closeEncodedParams.Length);
                    closeRequestStream.Close();
                    // Wait for the server response before continuing.
                    closeRequest.GetResponse();
                }
                catch (Exception) { }
                _reader.Close();
			}
		}

        private void WriteBytes(byte[] bytes, int count)
        {
            Uri myUri = new Uri(_uri.AbsoluteUri);
            WebRequest request = WebRequest.Create(myUri);
            request.Timeout = 1000;
            request.Method = "POST";
            request.ContentType = "application/x-www-form-urlencoded";
            StringBuilder parameters = new StringBuilder();
            parameters.Append("action=write");
            parameters.Append("&");
            parameters.Append("id=" + _id);
            parameters.Append("&");
            parameters.Append("datalength=");
            parameters.Append(count);
            parameters.Append("&");
            parameters.Append("data=");
            parameters.Append(Encode(bytes, count));

            byte[] encodedParams = Encoding.UTF8.GetBytes(parameters.ToString());
            request.ContentLength = encodedParams.Length;
            Stream requestStream = request.GetRequestStream();
            requestStream.Write(encodedParams, 0, encodedParams.Length);
            requestStream.Close();
            // Wait for the server response before continuing.
            request.GetResponse();
        }

		#endregion		
	}
}
