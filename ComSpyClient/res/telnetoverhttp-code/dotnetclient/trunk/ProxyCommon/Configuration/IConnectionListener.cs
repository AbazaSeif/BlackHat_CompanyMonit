// System References
using System;

namespace Soht.Client.DotNet.Common.Configuration
{
	/// <summary>
	/// Summary description for IConnectionListener.
	/// </summary>
	public interface IConnectionListener
	{

		bool IsRunning
		{
			get;
		}

		HostInfo HostInfo
		{
			get;
		}
	}
}
