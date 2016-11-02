//System References
using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.IO;
using System.Windows.Forms;

//Proxy References
using Soht.Client.DotNet.Common;
using Soht.Client.DotNet.Common.Configuration;

namespace Soht.Client.DotNet.Client
{
	/// <summary>
	/// Summary description for ProxyClient.
	/// </summary>
	public class ProxyClient : System.Windows.Forms.Form
	{
		#region Constants

		private String DEFAULT_CONFIGURATION_FILE = "soht.xml";

		#endregion

		#region Variables

		private ConfigurationManager _configurationManager;
		private Proxy _proxy;

		#endregion

		#region Form Variables

		private System.Windows.Forms.Label statusLabel;
		private System.Windows.Forms.TextBox statusTextBox;
		private System.Windows.Forms.GroupBox mappingGroupBox;
		private System.Windows.Forms.Button addMappingButton;
		private System.Windows.Forms.Button editMappingButton;
		private System.Windows.Forms.Button removeMappingButton;
		private System.Windows.Forms.ListBox mappingListBox;
		private System.Windows.Forms.Button startStopButton;
		private System.Windows.Forms.Button saveButton;
		private System.Windows.Forms.CheckBox loginRequiredCheckbox;
		private System.Windows.Forms.Label usernameLabel;
		private System.Windows.Forms.Label passwordLabel;
		private System.Windows.Forms.TextBox usernameTextBox;
		private System.Windows.Forms.TextBox passwordTextBox;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.TextBox proxyPasswordTextBox;
		private System.Windows.Forms.TextBox proxyUsernameTextBox;
		private System.Windows.Forms.CheckBox proxyCheckBox;
		private System.Windows.Forms.Label proxyAddressLabel;
		private System.Windows.Forms.GroupBox proxyGroupBox;
		private System.Windows.Forms.Label serverLabel;
		private System.Windows.Forms.GroupBox loginGroupBox;
		private System.Windows.Forms.Label copyrightLabel;
		private System.Windows.Forms.Label label3;
		private System.Windows.Forms.Label label4;
		private System.Windows.Forms.Label label5;
		private System.Windows.Forms.TextBox proxyAddressTextBox;
		private System.Windows.Forms.TextBox serverURLTextBox;
		private System.Windows.Forms.CheckBox statelessCheckbox;
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		#endregion

		public ProxyClient()
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();
			
			// Initialize the proxy.
			_proxy = Proxy.GetInstance();

			// Initialize the Configuration
			InitializeConfiguration();

			ConfigurationInfo configurationInfo = _configurationManager.ConfigurationInfo;

			serverURLTextBox.DataBindings.Add( "Text", configurationInfo, "URL" );

			proxyCheckBox.DataBindings.Add( "Checked", configurationInfo, "UseProxy" );
			proxyAddressTextBox.DataBindings.Add( "Text", configurationInfo, "ProxyAddress" );
			proxyUsernameTextBox.DataBindings.Add( "Text", configurationInfo, "ProxyUsername" );
			proxyPasswordTextBox.DataBindings.Add( "Text", configurationInfo, "ProxyPassword" );
			proxyCheckBox_CheckedChanged( null, null );

			statelessCheckbox.DataBindings.Add( "Checked", configurationInfo, "Stateless" );

			loginRequiredCheckbox.DataBindings.Add( "Checked", configurationInfo, "LoginRequired" );			
			usernameTextBox.DataBindings.Add( "Text", configurationInfo, "Username" );
			passwordTextBox.DataBindings.Add( "Text", configurationInfo, "Password" );
			loginRequiredCheckbox_CheckedChanged( null, null );

            if (configurationInfo.Hosts != null)
            {
                foreach (HostInfo host in configurationInfo.Hosts)
                {
                    mappingListBox.Items.Add(host);
                }
            }
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.statusLabel = new System.Windows.Forms.Label();
			this.statusTextBox = new System.Windows.Forms.TextBox();
			this.mappingGroupBox = new System.Windows.Forms.GroupBox();
			this.mappingListBox = new System.Windows.Forms.ListBox();
			this.addMappingButton = new System.Windows.Forms.Button();
			this.editMappingButton = new System.Windows.Forms.Button();
			this.removeMappingButton = new System.Windows.Forms.Button();
			this.proxyAddressLabel = new System.Windows.Forms.Label();
			this.proxyAddressTextBox = new System.Windows.Forms.TextBox();
			this.startStopButton = new System.Windows.Forms.Button();
			this.saveButton = new System.Windows.Forms.Button();
			this.loginRequiredCheckbox = new System.Windows.Forms.CheckBox();
			this.usernameLabel = new System.Windows.Forms.Label();
			this.passwordLabel = new System.Windows.Forms.Label();
			this.usernameTextBox = new System.Windows.Forms.TextBox();
			this.passwordTextBox = new System.Windows.Forms.TextBox();
			this.label1 = new System.Windows.Forms.Label();
			this.proxyPasswordTextBox = new System.Windows.Forms.TextBox();
			this.label2 = new System.Windows.Forms.Label();
			this.proxyCheckBox = new System.Windows.Forms.CheckBox();
			this.proxyUsernameTextBox = new System.Windows.Forms.TextBox();
			this.proxyGroupBox = new System.Windows.Forms.GroupBox();
			this.serverLabel = new System.Windows.Forms.Label();
			this.serverURLTextBox = new System.Windows.Forms.TextBox();
			this.loginGroupBox = new System.Windows.Forms.GroupBox();
			this.copyrightLabel = new System.Windows.Forms.Label();
			this.label3 = new System.Windows.Forms.Label();
			this.label4 = new System.Windows.Forms.Label();
			this.label5 = new System.Windows.Forms.Label();
			this.statelessCheckbox = new System.Windows.Forms.CheckBox();
			this.mappingGroupBox.SuspendLayout();
			this.proxyGroupBox.SuspendLayout();
			this.loginGroupBox.SuspendLayout();
			this.SuspendLayout();
			// 
			// statusLabel
			// 
			this.statusLabel.Location = new System.Drawing.Point(8, 10);
			this.statusLabel.Name = "statusLabel";
			this.statusLabel.Size = new System.Drawing.Size(40, 16);
			this.statusLabel.TabIndex = 0;
			this.statusLabel.Text = "Status:";
			// 
			// statusTextBox
			// 
			this.statusTextBox.Location = new System.Drawing.Point(56, 8);
			this.statusTextBox.Name = "statusTextBox";
			this.statusTextBox.ReadOnly = true;
			this.statusTextBox.Size = new System.Drawing.Size(64, 20);
			this.statusTextBox.TabIndex = 1;
			this.statusTextBox.TabStop = false;
			this.statusTextBox.Text = "stopped";
			// 
			// mappingGroupBox
			// 
			this.mappingGroupBox.Controls.Add(this.mappingListBox);
			this.mappingGroupBox.Controls.Add(this.addMappingButton);
			this.mappingGroupBox.Controls.Add(this.editMappingButton);
			this.mappingGroupBox.Controls.Add(this.removeMappingButton);
			this.mappingGroupBox.Location = new System.Drawing.Point(416, 8);
			this.mappingGroupBox.Name = "mappingGroupBox";
			this.mappingGroupBox.Size = new System.Drawing.Size(304, 248);
			this.mappingGroupBox.TabIndex = 6;
			this.mappingGroupBox.TabStop = false;
			this.mappingGroupBox.Text = "Port Mappings";
			// 
			// mappingListBox
			// 
			this.mappingListBox.Location = new System.Drawing.Point(16, 56);
			this.mappingListBox.Name = "mappingListBox";
			this.mappingListBox.Size = new System.Drawing.Size(272, 186);
			this.mappingListBox.TabIndex = 9;
			this.mappingListBox.DoubleClick += new System.EventHandler(this.mappingListBox_DoubleClick);
			// 
			// addMappingButton
			// 
			this.addMappingButton.Location = new System.Drawing.Point(16, 24);
			this.addMappingButton.Name = "addMappingButton";
			this.addMappingButton.TabIndex = 6;
			this.addMappingButton.Text = "Add";
			this.addMappingButton.Click += new System.EventHandler(this.addMappingButton_Click);
			// 
			// editMappingButton
			// 
			this.editMappingButton.Location = new System.Drawing.Point(112, 24);
			this.editMappingButton.Name = "editMappingButton";
			this.editMappingButton.TabIndex = 7;
			this.editMappingButton.Text = "Edit";
			this.editMappingButton.Click += new System.EventHandler(this.editMappingButton_Click);
			// 
			// removeMappingButton
			// 
			this.removeMappingButton.Location = new System.Drawing.Point(208, 24);
			this.removeMappingButton.Name = "removeMappingButton";
			this.removeMappingButton.TabIndex = 8;
			this.removeMappingButton.Text = "Remove";
			this.removeMappingButton.Click += new System.EventHandler(this.removeMappingButton_Click);
			// 
			// proxyAddressLabel
			// 
			this.proxyAddressLabel.Location = new System.Drawing.Point(16, 56);
			this.proxyAddressLabel.Name = "proxyAddressLabel";
			this.proxyAddressLabel.Size = new System.Drawing.Size(88, 16);
			this.proxyAddressLabel.TabIndex = 3;
			this.proxyAddressLabel.Text = "Proxy Address:";
			// 
			// proxyAddressTextBox
			// 
			this.proxyAddressTextBox.Location = new System.Drawing.Point(104, 54);
			this.proxyAddressTextBox.Name = "proxyAddressTextBox";
			this.proxyAddressTextBox.Size = new System.Drawing.Size(288, 20);
			this.proxyAddressTextBox.TabIndex = 2;
			this.proxyAddressTextBox.Text = "";
			// 
			// startStopButton
			// 
			this.startStopButton.Location = new System.Drawing.Point(136, 7);
			this.startStopButton.Name = "startStopButton";
			this.startStopButton.TabIndex = 1;
			this.startStopButton.Text = "Start";
			this.startStopButton.Click += new System.EventHandler(this.startStopButton_Click);
			// 
			// saveButton
			// 
			this.saveButton.Location = new System.Drawing.Point(280, 8);
			this.saveButton.Name = "saveButton";
			this.saveButton.Size = new System.Drawing.Size(112, 23);
			this.saveButton.TabIndex = 200;
			this.saveButton.Text = "Save Configuration";
			this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
			// 
			// loginRequiredCheckbox
			// 
			this.loginRequiredCheckbox.Location = new System.Drawing.Point(8, 48);
			this.loginRequiredCheckbox.Name = "loginRequiredCheckbox";
			this.loginRequiredCheckbox.Size = new System.Drawing.Size(144, 24);
			this.loginRequiredCheckbox.TabIndex = 3;
			this.loginRequiredCheckbox.Text = "Server Requires Login";
			this.loginRequiredCheckbox.CheckedChanged += new System.EventHandler(this.loginRequiredCheckbox_CheckedChanged);
			// 
			// usernameLabel
			// 
			this.usernameLabel.Location = new System.Drawing.Point(8, 80);
			this.usernameLabel.Name = "usernameLabel";
			this.usernameLabel.Size = new System.Drawing.Size(64, 16);
			this.usernameLabel.TabIndex = 9;
			this.usernameLabel.Text = "Username:";
			// 
			// passwordLabel
			// 
			this.passwordLabel.Location = new System.Drawing.Point(8, 104);
			this.passwordLabel.Name = "passwordLabel";
			this.passwordLabel.Size = new System.Drawing.Size(64, 16);
			this.passwordLabel.TabIndex = 0;
			this.passwordLabel.Text = "Password:";
			// 
			// usernameTextBox
			// 
			this.usernameTextBox.Location = new System.Drawing.Point(72, 78);
			this.usernameTextBox.Name = "usernameTextBox";
			this.usernameTextBox.Size = new System.Drawing.Size(120, 20);
			this.usernameTextBox.TabIndex = 4;
			this.usernameTextBox.Text = "";
			// 
			// passwordTextBox
			// 
			this.passwordTextBox.Location = new System.Drawing.Point(72, 102);
			this.passwordTextBox.Name = "passwordTextBox";
			this.passwordTextBox.PasswordChar = '*';
			this.passwordTextBox.Size = new System.Drawing.Size(120, 20);
			this.passwordTextBox.TabIndex = 5;
			this.passwordTextBox.Text = "";
			// 
			// label1
			// 
			this.label1.Location = new System.Drawing.Point(16, 80);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(64, 16);
			this.label1.TabIndex = 9;
			this.label1.Text = "Username:";
			// 
			// proxyPasswordTextBox
			// 
			this.proxyPasswordTextBox.Location = new System.Drawing.Point(104, 102);
			this.proxyPasswordTextBox.Name = "proxyPasswordTextBox";
			this.proxyPasswordTextBox.PasswordChar = '*';
			this.proxyPasswordTextBox.Size = new System.Drawing.Size(120, 20);
			this.proxyPasswordTextBox.TabIndex = 5;
			this.proxyPasswordTextBox.Text = "";
			// 
			// label2
			// 
			this.label2.Location = new System.Drawing.Point(16, 104);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(64, 16);
			this.label2.TabIndex = 0;
			this.label2.Text = "Password:";
			// 
			// proxyCheckBox
			// 
			this.proxyCheckBox.Location = new System.Drawing.Point(16, 24);
			this.proxyCheckBox.Name = "proxyCheckBox";
			this.proxyCheckBox.Size = new System.Drawing.Size(144, 24);
			this.proxyCheckBox.TabIndex = 3;
			this.proxyCheckBox.Text = "Use Local HTTP Proxy";
			this.proxyCheckBox.CheckedChanged += new System.EventHandler(this.proxyCheckBox_CheckedChanged);
			// 
			// proxyUsernameTextBox
			// 
			this.proxyUsernameTextBox.Location = new System.Drawing.Point(104, 78);
			this.proxyUsernameTextBox.Name = "proxyUsernameTextBox";
			this.proxyUsernameTextBox.Size = new System.Drawing.Size(120, 20);
			this.proxyUsernameTextBox.TabIndex = 4;
			this.proxyUsernameTextBox.Text = "";
			// 
			// proxyGroupBox
			// 
			this.proxyGroupBox.Controls.Add(this.proxyCheckBox);
			this.proxyGroupBox.Controls.Add(this.label1);
			this.proxyGroupBox.Controls.Add(this.proxyUsernameTextBox);
			this.proxyGroupBox.Controls.Add(this.proxyAddressLabel);
			this.proxyGroupBox.Controls.Add(this.proxyAddressTextBox);
			this.proxyGroupBox.Controls.Add(this.proxyPasswordTextBox);
			this.proxyGroupBox.Controls.Add(this.label2);
			this.proxyGroupBox.Location = new System.Drawing.Point(8, 208);
			this.proxyGroupBox.Name = "proxyGroupBox";
			this.proxyGroupBox.Size = new System.Drawing.Size(400, 136);
			this.proxyGroupBox.TabIndex = 201;
			this.proxyGroupBox.TabStop = false;
			this.proxyGroupBox.Text = "Local HTTP Proxy";
			// 
			// serverLabel
			// 
			this.serverLabel.Location = new System.Drawing.Point(8, 24);
			this.serverLabel.Name = "serverLabel";
			this.serverLabel.Size = new System.Drawing.Size(72, 16);
			this.serverLabel.TabIndex = 3;
			this.serverLabel.Text = "Server URL:";
			// 
			// serverURLTextBox
			// 
			this.serverURLTextBox.Location = new System.Drawing.Point(80, 22);
			this.serverURLTextBox.Name = "serverURLTextBox";
			this.serverURLTextBox.Size = new System.Drawing.Size(312, 20);
			this.serverURLTextBox.TabIndex = 2;
			this.serverURLTextBox.Text = "";
			// 
			// loginGroupBox
			// 
			this.loginGroupBox.Controls.Add(this.statelessCheckbox);
			this.loginGroupBox.Controls.Add(this.loginRequiredCheckbox);
			this.loginGroupBox.Controls.Add(this.usernameTextBox);
			this.loginGroupBox.Controls.Add(this.usernameLabel);
			this.loginGroupBox.Controls.Add(this.passwordTextBox);
			this.loginGroupBox.Controls.Add(this.passwordLabel);
			this.loginGroupBox.Controls.Add(this.serverURLTextBox);
			this.loginGroupBox.Controls.Add(this.serverLabel);
			this.loginGroupBox.Location = new System.Drawing.Point(8, 40);
			this.loginGroupBox.Name = "loginGroupBox";
			this.loginGroupBox.Size = new System.Drawing.Size(400, 160);
			this.loginGroupBox.TabIndex = 202;
			this.loginGroupBox.TabStop = false;
			this.loginGroupBox.Text = "SOHT Server";
			// 
			// copyrightLabel
			// 
			this.copyrightLabel.AutoSize = true;
			this.copyrightLabel.Location = new System.Drawing.Point(472, 264);
			this.copyrightLabel.Name = "copyrightLabel";
			this.copyrightLabel.Size = new System.Drawing.Size(160, 16);
			this.copyrightLabel.TabIndex = 203;
			this.copyrightLabel.Text = "SOHT .NET Client, Version 0.4";
			// 
			// label3
			// 
			this.label3.AutoSize = true;
			this.label3.Location = new System.Drawing.Point(472, 280);
			this.label3.Name = "label3";
			this.label3.Size = new System.Drawing.Size(199, 16);
			this.label3.TabIndex = 203;
			this.label3.Text = "http://www.ericdaugherty.com/dev/soht";
			// 
			// label4
			// 
			this.label4.AutoSize = true;
			this.label4.Location = new System.Drawing.Point(472, 312);
			this.label4.Name = "label4";
			this.label4.Size = new System.Drawing.Size(162, 16);
			this.label4.TabIndex = 203;
			this.label4.Text = "Copyright Eric Daugherty, 2003";
			// 
			// label5
			// 
			this.label5.AutoSize = true;
			this.label5.Location = new System.Drawing.Point(472, 296);
			this.label5.Name = "label5";
			this.label5.Size = new System.Drawing.Size(130, 16);
			this.label5.TabIndex = 203;
			this.label5.Text = "soht@ericdaugherty.com";
			// 
			// statelessCheckbox
			// 
			this.statelessCheckbox.Location = new System.Drawing.Point(8, 128);
			this.statelessCheckbox.Name = "statelessCheckbox";
			this.statelessCheckbox.Size = new System.Drawing.Size(312, 24);
			this.statelessCheckbox.TabIndex = 10;
			this.statelessCheckbox.Text = "Use stateless connection (less efficient but more reliable)";
			// 
			// ProxyClient
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(730, 368);
			this.Controls.Add(this.copyrightLabel);
			this.Controls.Add(this.loginGroupBox);
			this.Controls.Add(this.proxyGroupBox);
			this.Controls.Add(this.saveButton);
			this.Controls.Add(this.startStopButton);
			this.Controls.Add(this.mappingGroupBox);
			this.Controls.Add(this.statusTextBox);
			this.Controls.Add(this.statusLabel);
			this.Controls.Add(this.label3);
			this.Controls.Add(this.label4);
			this.Controls.Add(this.label5);
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
			this.MaximizeBox = false;
			this.Name = "ProxyClient";
			this.Text = "SOHT Client";
			this.Closing += new System.ComponentModel.CancelEventHandler(this.formClosing);
			this.mappingGroupBox.ResumeLayout(false);
			this.proxyGroupBox.ResumeLayout(false);
			this.loginGroupBox.ResumeLayout(false);
			this.ResumeLayout(false);

		}
		#endregion

		/// <summary>
		/// The main entry point for the application.
		/// </summary>
		[STAThread]
		static void Main() 
		{			
			Application.Run(new ProxyClient());
		}

		#region Private Control Methods

		private void StartProxy()
		{
			Cursor = Cursors.WaitCursor;

			try
			{
				_proxy.Start( _configurationManager.ConfigurationInfo );
			
				statusTextBox.Text = "running";
				startStopButton.Text = "Stop";
				
				loginGroupBox.Enabled = false;
				proxyGroupBox.Enabled = false;

			}
			catch( Exception exception )
			{
				MessageBox.Show( this, "Error starting Proxy: " + exception.Message, "Proxy Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation );
			}
			
			Cursor = Cursors.Default;
		}

		private void StopProxy()
		{
			Cursor = Cursors.WaitCursor;

			_proxy.Stop();

			statusTextBox.Text = "stopped";
			startStopButton.Text = "Start";
			
			loginGroupBox.Enabled = true;
			proxyGroupBox.Enabled = true;

			Cursor = Cursors.Default;
		}

		private void AddHost()
		{
			HostForm hostForm = new HostForm();
			if( DialogResult.OK == hostForm.ShowDialog() )
			{
				HostInfo host = new HostInfo();
				host.LocalPort = hostForm.LocalPort;
				host.RemoteHost = hostForm.RemoteHost;
				host.RemotePort = hostForm.RemotePort;				

				Cursor = Cursors.WaitCursor;

				mappingListBox.Items.Add( host );
				_configurationManager.AddHost( host );
				if( statusTextBox.Text.Equals( "running" ) )
				{
					_proxy.AddMapping( host );
				}

				Cursor = Cursors.Default;
			}
		}

		private void EditHost( HostInfo host )
		{
			bool localPortChanged = false;

			HostForm hostForm = new HostForm();
			hostForm.LocalPort = host.LocalPort;			
			hostForm.RemoteHost = host.RemoteHost;
			hostForm.RemotePort = host.RemotePort;
			
			if( DialogResult.OK == hostForm.ShowDialog() )
			{
				localPortChanged = hostForm.LocalPort != host.LocalPort;
				host.LocalPort = hostForm.LocalPort;
				host.RemoteHost = hostForm.RemoteHost;
				host.RemotePort = hostForm.RemotePort;

				if( localPortChanged && statusTextBox.Text.Equals( "running" ) )
				{
					MessageBox.Show( this, "Local Port Change will not take effect until you Restart the Proxy!", "Warning", MessageBoxButtons.OK, MessageBoxIcon.Warning );
				}
			}

			mappingListBox.Items[ mappingListBox.SelectedIndex ] = host;
		}

		private void RemoveHost()
		{
			Cursor = Cursors.WaitCursor;

			HostInfo host = (HostInfo) mappingListBox.SelectedItem;
			if( host != null )
			{
				mappingListBox.Items.Remove( host );
				_configurationManager.RemoveHost( host );
				_proxy.RemoveMapping( host.LocalPort );
			}
			Cursor = Cursors.Default;
		}

		private void SaveConfiguration()
		{
			Cursor = Cursors.WaitCursor;

			_configurationManager.Save();

			Cursor = Cursors.Default;
		}
		
		#endregion

		#region UI Methods

		private void InitializeConfiguration()
		{
			String configurationFile = DEFAULT_CONFIGURATION_FILE;

			if( !File.Exists( configurationFile ) )
			{
				bool isLoaded = false;
				while( !isLoaded )
				{
					// Show a Dialog to Open or Create a config file.
					DialogResult dialogResult = MessageBox.Show( "Unable to load default configuration file.  Would you like to specify a different configuration file?", "Load Configuration File", MessageBoxButtons.YesNo );
					if( dialogResult == DialogResult.Yes )
					{
						OpenFileDialog openFileDialog = new OpenFileDialog();
						if( openFileDialog.ShowDialog( this ) == DialogResult.OK )
						{
							configurationFile = openFileDialog.FileName;
							isLoaded = true;
						}
					}
					else
					{
						// Use the default file to create a new Configuration.
						isLoaded = true;
					}
				}
			}

			ConfigurationManager.Initialize( configurationFile );
			_configurationManager = ConfigurationManager.GetInstance();
						
		}
	 
		#endregion

		#region Event Handler Methods

		private void formClosing(object sender, System.ComponentModel.CancelEventArgs e)
		{
			try
			{
				StopProxy();
			}
			catch
			{
				Application.Exit();
			}
		}

		private void editMappingButton_Click(object sender, System.EventArgs e)
		{
			HostInfo host = (HostInfo) mappingListBox.SelectedItem;
			if( host != null )
			{
				EditHost( host );
			}
			else
			{
				MessageBox.Show( this, "You must select a mapping to edit!", "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation );
			}
		}

		private void mappingListBox_DoubleClick(object sender, System.EventArgs e)
		{
			EditHost( (HostInfo) mappingListBox.SelectedItem );
		}

		private void startStopButton_Click(object sender, System.EventArgs e)
		{
			if( statusTextBox.Text.Equals( "stopped" ) )
			{
				StartProxy();
			}
			else
			{
				StopProxy();
			}
		}

		private void removeMappingButton_Click(object sender, System.EventArgs e)
		{
			RemoveHost();
		}

		private void addMappingButton_Click(object sender, System.EventArgs e)
		{
			AddHost();
		}

		private void saveButton_Click(object sender, System.EventArgs e)
		{
			SaveConfiguration();
		}

		private void loginRequiredCheckbox_CheckedChanged(object sender, System.EventArgs e)
		{
			usernameTextBox.Enabled = loginRequiredCheckbox.Checked;
			passwordTextBox.Enabled = loginRequiredCheckbox.Checked;
		}
		
		private void proxyCheckBox_CheckedChanged(object sender, System.EventArgs e)
		{
			proxyAddressTextBox.Enabled = proxyCheckBox.Checked;
            proxyUsernameTextBox.Enabled = proxyCheckBox.Checked;
			proxyPasswordTextBox.Enabled = proxyCheckBox.Checked;
		}

		#endregion		
	}
}
