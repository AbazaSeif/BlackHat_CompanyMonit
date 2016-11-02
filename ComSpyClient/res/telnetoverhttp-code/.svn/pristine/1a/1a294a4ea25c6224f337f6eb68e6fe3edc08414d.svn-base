//System References
using System;
using System.Collections;
using System.Threading;

//Proxy References
using Soht.Client.DotNet.Common.Configuration;
using Soht.Client.DotNet.Common.Connection;

namespace Soht.Client.DotNet.Common
{
	/// <summary>
	/// Provides the entrypoint for the actual Proxy
	/// client implementation.  All variations of the 
	/// .NET Proxy clients should actually use this
	/// class to handle the proxy implementation.
	/// </summary>
	public class Proxy
	{
		#region Variables

		private static Proxy _instance = null;		
		private Hashtable _listeners = new Hashtable();
		
		#endregion

		#region Constructor

		/// <summary>
		/// Creates a Proxy instance, if none exist.  Throws a 
		/// SystemException if a Proxy has already been created.
		/// </summary>
		private Proxy()
		{
			if( _instance != null )
			{
				throw new SystemException( "Multiple Proxy instances created!" );
			}
			_instance = this;
		}

		#endregion

		#region Parameter Access Methods

		/// <summary>
		/// Returns the singleton instance of the Proxy class.
		/// </summary>
		/// <returns></returns>
		public static Proxy GetInstance()
		{
			if( _instance == null )
			{
				_instance = new Proxy();
			}
			return _instance;
		}

		/// <summary>
		/// Returns all the ConnectionListeners that are
		/// currently active.
		/// </summary>
		public ConnectionListener[] ConnectionListeners
		{
			get
			{
				//TODO: There has to be a better way to do this.
				ConnectionListener[] listenerArray = new ConnectionListener[ _listeners.Count ];
				int index = 0;
				foreach( ConnectionListener listener in _listeners.Values )
				{
					listenerArray[index++] = listener;
				}

				return listenerArray;
			}
		}

		#endregion

		#region Public Methods

		/// <summary>
		/// Entry point for the Proxy.
		/// </summary>
		public void Start( ConfigurationInfo configurationInfo ) 
		{
			foreach( HostInfo host in configurationInfo.Hosts ) 
			{
				AddListener( configurationInfo, host );
			}
		}

		public void Stop()
		{
			StopListeners();
		}

		public void AddMapping( HostInfo host )
		{
			AddListener( ConfigurationManager.GetInstance().ConfigurationInfo, host );
		}

		/// <summary>
		/// Stops the listener on the specified local port.
		/// Any connections already in progress will continue
		/// to function.
		/// </summary>
		/// <param name="localPort">The local port for the Listener to stop.</param>
		public void RemoveMapping( int localPort )
		{
			StopListener( localPort );
		}

		#endregion

		#region Private Methods

		/// <summary>
		/// Creates a new ConnectionListener and adds it to the _listeners
		/// hashtable.
		/// </summary>
		/// <param name="localPort"></param>
		/// <param name="host"></param>
		/// <param name="hostPort"></param>
		private bool AddListener( ConfigurationInfo configurationInfo, HostInfo hostInfo ) 
		{

			if( !_listeners.Contains( hostInfo.LocalPort ) ) 
			{
				// Start the Listener as a new Thread.
				try
				{
					ConnectionListener listener = new ConnectionListener( configurationInfo, hostInfo );
					Thread localThread = new Thread( new ThreadStart( listener.Listen ) ); 			
					localThread.Name = "Listener " + hostInfo.LocalPort + " to " + hostInfo.RemoteHost + ":" + hostInfo.RemotePort;
					localThread.Start();
					_listeners.Add( hostInfo.LocalPort, listener );
					return true;
				}
				catch( Exception exception ) 
				{
					throw new SystemException( "Error starting listener on port: " + hostInfo.LocalPort + ".  " + exception.Message );
				}
			}
			else
			{
				return false;
			}
		}

		/// <summary>
		/// Stops the listener on the specified local port.
		/// Any connections already in progress will continue
		/// to function.
		/// </summary>
		/// <param name="localPort">The local port for the Listener to stop.</param>
		private void StopListener( int localPort )
		{
			ConnectionListener listener = (ConnectionListener) _listeners[localPort];
			if( listener != null )
			{
				listener.Stop();
				_listeners.Remove(localPort);
			}
		}

		/// <summary>
		/// Stops all configured listeners.
		/// </summary>
		private void StopListeners()
		{
			Hashtable listeners = (Hashtable) _listeners.Clone();
			foreach( int localPort in listeners.Keys )
			{
				StopListener( localPort );
			}
		}

		#endregion

	}
}
