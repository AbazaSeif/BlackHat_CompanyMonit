using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;


namespace Soht.Client.DotNet.Common.Connection
{
	/// <summary>
	/// Summary description for ConnectionReadWrite.
	/// </summary>
	public class ConnectionReadWrite : AbstractConnection
	{
		#region Constants

		/// <summary>
		/// The initial time to sleep between reads, in seconds.
		/// </summary>
		private static readonly int DEFAULT_SLEEP_TIME = 100;
		private static readonly int MAX_SLEEP_TIME = 1000;

		#endregion

		#region Variables

		private Socket _socket;
		private NetworkStream _stream;		
		private BinaryWriter _writer;
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
		public ConnectionReadWrite( Socket socket, NetworkStream stream, Uri uri, String id )
		{			
			_socket = socket;
			_stream = stream;
			_writer = new BinaryWriter( stream );
			_uri = uri;
			_id = id;
		}

		#endregion

		#region Public Methods

		public void ReadWrite() 
		{
			bool isRunning = true;
			int sleepTime = DEFAULT_SLEEP_TIME;
			DateTime wakeTime;

			
			byte[] bytes = new byte[1024];
			int bytesRead = 0;

			/*ASync Method variables
			bool asyncReadStarted = false;
			IAsyncResult asyncResult = null;
			*/

			try 
			{
				// Initialize the socket to timeout 
				_socket.SetSocketOption( SocketOptionLevel.Socket, SocketOptionName.ReceiveTimeout, 100 );
				_socket.Blocking = false;
				while( isRunning )
				{

					// wakeTime = DateTime.Now.AddSeconds( sleepTime );
					wakeTime = DateTime.Now.AddMilliseconds(sleepTime);
					do
					{
						try
						{
							bytesRead = _stream.Read(bytes, 0, bytes.Length);
							if (bytesRead == 0) throw new IOException("0 Bytes Read from Client");
						}
						catch( IOException ioException )
						{
							bool error = true;
							SocketException socketException = ioException.InnerException as SocketException;
							if( socketException != null )
							{
								// Socket Timed out...
								if( socketException.ErrorCode == 10060 )
								{		
									error = false;
								}
								if (socketException.ErrorCode == 10035)
								{
									Thread.Sleep(100);
								}
							}

							// If we don't know what error it is, rethrow it.
							if( error ) throw ioException;
						}
					}
					while( bytesRead == 0 && wakeTime > DateTime.Now );

					// Double the sleep time if we are here because of a timeout.
					if( bytesRead == 0 )
					{
						sleepTime = sleepTime *2;
						if (sleepTime > MAX_SLEEP_TIME)
						{
							sleepTime = MAX_SLEEP_TIME;
						}
					}

					/*
					 * This solution should work as an alternative to the above code,
					 * but 
					// Start an async read if one is not pending.
					if( !asyncReadStarted )
					{
						asyncReadStarted = true;
						bytesRead = 0;
						asyncResult = _stream.BeginRead( bytes, 0, bytes.Length, null, null );
					}

					// Wait until data is received or the timeout elsapses.  If data is read, 
					// handle it.
					if( asyncResult.AsyncWaitHandle.WaitOne( sleepTime, false ) )
					{
						asyncReadStarted = false;
						bytesRead = _stream.EndRead( asyncResult );
					
						Console.WriteLine( "Bytes Read: " + bytesRead );
						Console.Write( "Data: " );
						for( int index = 0; index < bytesRead; index++ )
						{
							Console.Write( bytes[index] + "," );
						}
						Console.WriteLine();

						// The socket is closed.
						if( bytesRead == 0 )
						{
							break;
						}
					}
					// Increase the sleep time if the local client has no new data.
					else
					{
						sleepTime = sleepTime * 2;
					}
					*/

					HttpWebRequest request = (HttpWebRequest) WebRequest.Create( _uri );					
					request.Method = "POST";
					request.ContentType = "application/x-www-form-urlencoded";
					StringBuilder parameters = new StringBuilder();
					parameters.Append( "action=readwrite" );
					parameters.Append( "&" );
					parameters.Append( "id=" + _id );
					parameters.Append( "&" );
					parameters.Append( "datalength=" );
					parameters.Append( bytesRead );
					parameters.Append( "&" );
					parameters.Append( "data=" );
					parameters.Append( Encode( bytes, bytesRead ) );
			
					byte[] encodedParams = Encoding.UTF8.GetBytes( parameters.ToString() );
					request.ContentLength = encodedParams.Length;
					Stream requestStream = request.GetRequestStream();
					requestStream.Write( encodedParams, 0, encodedParams.Length );
					requestStream.Close();

					// Write finished, start Read
					WebResponse webResponse = request.GetResponse();
					BinaryReader webReader = new BinaryReader( webResponse.GetResponseStream() );

					bool isFirst = true;
					while( true ) 
					{
						bytesRead = webReader.Read( bytes, 0, bytes.Length );
            
						//A count of 0 indicates that the stream has been closed.
						if( bytesRead == 0 ) 
						{
							break;
						}

						int startIndex = isFirst ? 1 : 0;
						if( bytesRead > startIndex )
						{
							_writer.Write( bytes, startIndex, bytesRead - startIndex );
						}
						isFirst = false;
					}

					webReader.Close();
					webResponse.Close();
				}
			}
			catch( IOException e )
			{
				Console.WriteLine( "Connection Closed due to IO Exception." + e.ToString() );				
			}
			catch( WebException webException )
			{				
				Console.WriteLine( "WebException: " + webException );
			}
			finally 
			{
				//Tell the HTTP Proxy Service to close it's connections.
				WebRequest closeRequest = WebRequest.Create( _uri );
				closeRequest.Method = "POST";
				closeRequest.ContentType = "application/x-www-form-urlencoded";
				StringBuilder closeParameters = new StringBuilder();
				closeParameters.Append( "action=close" );
				closeParameters.Append( "&" );
				closeParameters.Append( "id=" + _id );
				byte[] closeEncodedParams = Encoding.UTF8.GetBytes( closeParameters.ToString() );
				closeRequest.ContentLength = closeEncodedParams.Length;
				Stream closeRequestStream = closeRequest.GetRequestStream();
				closeRequestStream.Write( closeEncodedParams, 0, closeEncodedParams.Length );
				closeRequestStream.Close();
			}
		}
		#endregion

	}	
}
