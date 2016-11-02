//System References
using System;
using System.Xml.Serialization;

namespace Soht.Client.DotNet.Common.Configuration
{
	/// <summary>
	/// Summary description for HostInfo.
	/// </summary>;
	public class HostInfo
	{
		#region Variables

		private int _localPort;
		private string _remoteHost;
		private int _remotePort;

		#endregion

		#region Constructor

		public HostInfo()
		{
		}

		#endregion
		
		#region Parameter Access Methods

		[XmlElementAttribute( "localport" )]
		public int LocalPort
		{
			get 
			{
				return _localPort;
			}
			set
			{
				_localPort = value;
			}
		}

		
		[XmlElementAttribute( "remotehost" )]
		public String RemoteHost
		{
			get 
			{
				return _remoteHost;
			}
			set
			{
				_remoteHost = value;
			}
		}

		[XmlElementAttribute( "remoteport" )]
		public int RemotePort
		{
			get 
			{
				return _remotePort;
			}
			set
			{
				_remotePort = value;
			}
		}

		#endregion	

		#region ToString

		public override string ToString()
		{
			return _localPort + " -> " + _remoteHost + ":" + _remotePort;
		}

		#endregion

		#region Equals/Hash Method

		public override int GetHashCode()
		{
			return LocalPort + RemoteHost.GetHashCode() + RemotePort;
		}


		public override bool Equals(object obj)
		{
			if( obj == null )
			{
				return false;
			}

			HostInfo host = (HostInfo) obj;

			return( 
				this.LocalPort == host.LocalPort &&
				this.RemoteHost.Equals( host.RemoteHost ) &&
				this.RemotePort == host.RemotePort
				);
 
		}


		#endregion
	}
}
