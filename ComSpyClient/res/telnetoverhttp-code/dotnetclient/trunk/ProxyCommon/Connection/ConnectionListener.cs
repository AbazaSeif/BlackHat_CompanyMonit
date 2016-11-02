//System References
using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

//Local References
using Soht.Client.DotNet.Common.Configuration;

namespace Soht.Client.DotNet.Common.Connection
{
	/// <summary>
	/// Handles incoming connections for a specific local port.
	/// </summary>
	public class ConnectionListener : IConnectionListener
	{

		#region Variables

		private ConfigurationInfo _configurationInfo;
		private HostInfo _hostInfo;
		private TcpListener _listener;
		private bool _isRunning;

		#endregion

		#region Constructor 

		/// <summary>
		/// Constructor
		/// </summary>
		/// <param name="localPort"></param>
		/// <param name="host"></param>
		/// <param name="hostPort"></param>
		public ConnectionListener( ConfigurationInfo configurationInfo, HostInfo hostInfo )
		{
			_configurationInfo = configurationInfo;
			_hostInfo = hostInfo;
			IPAddress ipAddress = Dns.Resolve("localhost").AddressList[0];
			_listener = new TcpListener( ipAddress, hostInfo.LocalPort );
			// Bind to the port and start accepting connections.
			_listener.Start();			
		}

		#endregion

		#region Parameter Access

		public bool IsRunning
		{
			get
			{
				return _isRunning;
			}
		}

		public HostInfo HostInfo
		{
			get
			{
				return _hostInfo;
			}
		}

		#endregion

		#region Public Methods

		/// <summary>
		/// Enters an endless loop of accepting new connections
		/// and creating new threads to manager them.
		/// </summary>
		public void Listen() 
		{
			_listener.Start();
			Socket socket = null;
			_isRunning = true;
			while( _isRunning ) 
			{
				try 
				{					
					socket = _listener.AcceptSocket();
					
					Uri uri = new Uri( _configurationInfo.URL );

					//Setup connection to HTTP Service
					WebRequest request = WebRequest.Create( uri );
					request.Method = "POST";
					request.ContentType = "application/x-www-form-urlencoded";

					if( _configurationInfo.UseProxy )
					{
						WebProxy proxy = new WebProxy( _configurationInfo.ProxyAddress );
						request.Proxy = proxy;

						NetworkCredential credentials = new NetworkCredential( _configurationInfo.ProxyUsername, _configurationInfo.ProxyPassword );
						request.Credentials = credentials;
					}

					StringBuilder parameters = new StringBuilder();
					parameters.Append( "action=open" );
					parameters.Append( "&" );
					parameters.Append( "host=" );
					parameters.Append( _hostInfo.RemoteHost );
					parameters.Append( "&" );
					parameters.Append( "port=" );
					parameters.Append( _hostInfo.RemotePort );
					// Specify the password if required.
					if( _configurationInfo.LoginRequired )
					{
						parameters.Append( "&" );
						parameters.Append( "username=" );
						parameters.Append( _configurationInfo.Username );
						parameters.Append( "&" );
						parameters.Append( "password=" );
						parameters.Append( _configurationInfo.Password );
					}
					
					byte[] encodedParams = Encoding.UTF8.GetBytes( parameters.ToString() );
					request.ContentLength = encodedParams.Length;

					//Open Connection
					Stream requestStream = request.GetRequestStream();
					requestStream.Write( encodedParams, 0, encodedParams.Length );
					requestStream.Close();

					StreamReader webReader = new StreamReader( request.GetResponse().GetResponseStream() );

					String result = webReader.ReadLine();
					
					if( result.StartsWith( "SUCCESS" ) ) 
					{
						String connectionId = webReader.ReadLine();

						NetworkStream stream = new NetworkStream( socket, FileAccess.ReadWrite );
						
						// If it is a stateless connection, use one thread.
						if( _configurationInfo.Stateless )
						{
							ConnectionReadWrite connectionReadWrite = new ConnectionReadWrite( socket, stream, uri, connectionId );
							Thread readWriteThread = new Thread( new ThreadStart( connectionReadWrite.ReadWrite ) );
							readWriteThread.Start();
						}
						// Use two threads.
						else
						{
							BinaryReader reader = new BinaryReader( stream );
							BinaryWriter writer = new BinaryWriter( stream );
						
							ConnectionWriter connectionWriter = new ConnectionWriter( writer, uri, connectionId );
							Thread writerThread = new Thread( new ThreadStart( connectionWriter.Write ) );
							writerThread.Name = "WriterThread ID#" + connectionId;
							writerThread.Start();

							ConnectionReader connectionReader = new ConnectionReader( reader, uri, connectionId );
							Thread readerThread = new Thread( new ThreadStart( connectionReader.Read ) );
							readerThread.Name = "ReaderThread ID#" + connectionId;
							readerThread.Start();
						}
					}
				}
				//Thrown if there is a problem connecting to the remote
				//HTTP Service.
				catch( WebException ) 
				{
					try 
					{
						//Print an error message to the client connection
						if( socket != null ) 
						{
							NetworkStream stream = new NetworkStream( socket, FileAccess.ReadWrite );
							StreamWriter writer = new StreamWriter( stream );
							writer.WriteLine( "Unable to contact Proxy Server.  Closing Connection." );
							writer.Flush();
						}
					}
					catch( Exception )
					{
						//Nothing to do here.
					}
					//Make sure the socket gets closed.
					finally 
					{
						socket.Close();
					}
				}
				//Some unknown exception occured.  Clean up open
				//resources.
				catch( Exception ) 
				{
					try 
					{
						if( socket != null ) 
						{
							socket.Close();
						}
					}
					catch( Exception )
					{
						//Nothing to do here.
					}
				}
			}
		}
        
		/// <summary>
		/// Stops the listener from accepting new connections.  All
		/// connections in progress will continue to work until closed.
		/// </summary>
		public void Stop()
		{
			_isRunning = false;
			_listener.Stop();
		}
		
		#endregion
	}
}
