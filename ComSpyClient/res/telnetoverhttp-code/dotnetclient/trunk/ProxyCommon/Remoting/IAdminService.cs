//System References
using System;

//Proxy References
using Soht.Client.DotNet.Common.Configuration;

namespace Soht.Client.DotNet.Common.Remoting
{
	/// <summary>
	/// Summary description for IAdminService.
	/// </summary>
	public interface IAdminService
	{
		/// <summary>
		/// Returns the total number of active listeners.
		/// </summary>
		/// <returns></returns>
		int GetListenerCount();

		/// <summary>
		/// Returns an array of active ConnectionListener instances.
		/// </summary>
		/// <returns></returns>
		IConnectionListener[] GetActiveConnectionListeners();

	}
}
