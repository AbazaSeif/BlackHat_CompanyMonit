//System References
using System;
using System.Xml.Serialization;

namespace Soht.Client.DotNet.Common.Configuration
{
	/// <summary>
	/// Summary description for ConfigurationInfo.
	/// </summary>
	[XmlRoot(ElementName="configuration")]
	public class ConfigurationInfo
	{
		#region Variables

		private int _adminPort = 8121;
		private string _url;
		private bool _loginRequired;
		private string _username;
		private string _password;
		private bool _stateless;
		private bool _useProxy;		
		private string _proxyAddress;
		private string _proxyUsername;
		private string _proxyPassword;
		private HostInfo[] _hosts;

		#endregion
		
		#region Constructor

		/// <summary>
		/// Default Constructor
		/// </summary>
		public ConfigurationInfo()
		{
		}

		#endregion

		#region Parameter Access Methods

		[XmlElementAttribute( "adminport" )]
		public int AdminPort
		{
			get
			{
				return _adminPort;
			}
			set
			{
				_adminPort = value;
			}
		}

		[XmlElementAttribute( "url" )]
		public string URL 
		{
			get
			{
				if( _url == null )
				{
					return "";
				}
				return _url;
			}
			set 
			{
				_url = value;
			}
		}

		[XmlElementAttribute( "loginrequired" )]
		public bool LoginRequired
		{
			get
			{
				return _loginRequired;
			}
			set
			{
				_loginRequired = value;
			}
		}

		[XmlElementAttribute( "server-username" )]
		public string Username
		{
			get
			{
				if( _username == null )
				{
					return "";
				}
				return _username;
			}
			set
			{
				_username = value;
			}
		}

		[XmlElementAttribute( "server-password" )]
		public string Password
		{
			get
			{
				if( _password == null )
				{
					return "";
				}
				return _password;
			}
			set
			{
				_password = value;
			}
		}

		[XmlElementAttribute( "stateless" )]
		public bool Stateless
		{
			get
			{
				return _stateless;
			}
			set
			{
				_stateless = value;
			}
		}

		[XmlElementAttribute( "useproxy" )]
		public bool UseProxy
		{
			get
			{
				return _useProxy;
			}
			set
			{
				_useProxy = value;
			}
		}

		[XmlElementAttribute( "proxyaddress" )]
		public String ProxyAddress
		{
			get
			{
				if( _proxyAddress == null )
				{
					return "";
				}
				return _proxyAddress;
			}
			set
			{
				_proxyAddress = value;
			}
		}

		[XmlElementAttribute( "proxy-username" )]
		public string ProxyUsername
		{
			get
			{
				if( _proxyUsername == null )
				{
					return "";
				}
				return _proxyUsername;
			}
			set
			{
				_proxyUsername = value;
			}
		}

		[XmlElementAttribute( "proxy-password" )]
		public string ProxyPassword
		{
			get
			{
				if( _proxyPassword == null )
				{
					return "";
				}
				return _proxyPassword;
			}
			set
			{
				_proxyPassword = value;
			}
		}

		[XmlElementAttribute( "host" )]
		public HostInfo[] Hosts
		{
			get
			{
				return _hosts;
			}
			set
			{
				_hosts = value;
			}
		}

		#endregion
	}
}
