//System References
using System;
using System.Collections;
using System.IO;
using System.Net;
using System.Xml;
using System.Xml.Serialization;

//Proxy References
using Soht.Client.DotNet.Common.Configuration;

namespace Soht.Client.DotNet.Common.Configuration
{
	/// <summary>
	/// Summary description for ConfigurationManager.
	/// </summary>
	public class ConfigurationManager
	{
		#region Variables

		/// <summary>
		/// Singleton Instance
		/// </summary>
		private static ConfigurationManager _instance;
		
		private ConfigurationInfo _configurationInfo;

		private String _fileName;

		#endregion
		
		#region Constructor

		/// <summary>
		/// Private constructor to enforce singleton pattern.
		/// </summary>
		private ConfigurationManager( string fileName )
		{
			// Initialize the numer of connections to allow more than 2
			// HTTP Connections to the server.
			ServicePointManager.DefaultConnectionLimit = 100;

			_fileName = fileName;
			// If the file does not exist, load a default configuration.
			if( !File.Exists( _fileName ) )
			{
				_configurationInfo = new ConfigurationInfo();
			}
			else
			{
				XmlDocument xmlDocument = new XmlDocument();
				xmlDocument.Load( fileName );

				XmlSerializer serializer = new XmlSerializer( typeof( ConfigurationInfo ) );
				XmlNodeReader nodeReader = new XmlNodeReader( xmlDocument );

				_configurationInfo = (ConfigurationInfo) serializer.Deserialize( nodeReader );
			}
		}

		#endregion

		#region Static Methods

		/// <summary>
		/// Provides access to the singleton instance.
		/// </summary>
		/// <returns>Instance of the ConfigurationManager singleton.</returns>
		public static ConfigurationManager GetInstance()
		{
			if( _instance == null ) 
			{
                throw new Exception( "Configuration Manager must be Initialized before use!" );
			}
			return _instance;
		}

		/// <summary>
		/// Should only be called, when the 
		/// </summary>
		/// <param name="filename"></param>
		public static void Initialize( String filename ) 
		{
			_instance = new ConfigurationManager( filename );
		}

		#endregion

		#region Parameter Access Methods

		public ConfigurationInfo ConfigurationInfo
		{
			get
			{
				return _configurationInfo;
			}
		}

		#endregion
		
		#region Public Methods

		public void AddHost( HostInfo hostInfo ) 
		{
			if( hostInfo != null ) 
			{
				lock( _configurationInfo ) 
				{
					HostInfo[] oldHosts = ConfigurationInfo.Hosts;
					HostInfo[] newHosts = new HostInfo[ oldHosts.Length + 1 ];
					Array.Copy( oldHosts, newHosts, oldHosts.Length );
					newHosts[ newHosts.Length - 1 ] = hostInfo;
					_configurationInfo.Hosts = newHosts;
				}
			}			
		}

		public void RemoveHost( HostInfo host )
		{
			lock( _configurationInfo.Hosts ) 
			{
				HostInfo[] oldHosts = _configurationInfo.Hosts;
				HostInfo[] newHosts = new HostInfo[ oldHosts.Length - 1 ];
				int index = 0;
				foreach( HostInfo currentHost in oldHosts )
				{
					if( !currentHost.Equals( host ) )
					{
						newHosts[index++] = currentHost;
					}
				}
				_configurationInfo.Hosts = newHosts;				
			}			
		}

		public void Save()
		{
			XmlSerializer serializer = new XmlSerializer( typeof( ConfigurationInfo ) );
			TextWriter writer = new StreamWriter( _fileName );
			serializer.Serialize(writer, _configurationInfo);
			writer.Close();
		}

		#endregion
	}
}
