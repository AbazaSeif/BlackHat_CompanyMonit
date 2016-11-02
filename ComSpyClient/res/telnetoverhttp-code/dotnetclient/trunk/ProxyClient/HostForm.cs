//System References
using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace Soht.Client.DotNet.Client
{
	/// <summary>
	/// Summary description for HostForm.
	/// </summary>
	public class HostForm : System.Windows.Forms.Form
	{
		#region Form Variables

		private System.Windows.Forms.Label localPortLabel;
		private System.Windows.Forms.Label remoteHostLabel;
		private System.Windows.Forms.Label remotePortLabel;
		private System.Windows.Forms.Button okButton;
		private System.Windows.Forms.Button cancelButton;
		private System.Windows.Forms.TextBox localPortTextBox;
		private System.Windows.Forms.TextBox remotePortTextBox;
		private System.Windows.Forms.TextBox remoteHostTextBox;
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		#endregion

		#region Constructor

		public HostForm()
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();
		}

		#endregion

		#region Parameter Access Methods

		public int LocalPort
		{
			get
			{
				return Convert.ToInt32( localPortTextBox.Text );
			}
			set
			{
				localPortTextBox.Text = value.ToString();
			}
		}

		public String RemoteHost
		{
			get
			{
				return remoteHostTextBox.Text;
			}
			set
			{
				remoteHostTextBox.Text = value;
			}
		}

		public int RemotePort
		{
			get
			{
				return Convert.ToInt32( remotePortTextBox.Text );
			}
			set
			{
				remotePortTextBox.Text = value.ToString();
			}
		}

		#endregion
		
		#region Form Methods

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

		#endregion

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.localPortLabel = new System.Windows.Forms.Label();
			this.remoteHostLabel = new System.Windows.Forms.Label();
			this.remotePortLabel = new System.Windows.Forms.Label();
			this.localPortTextBox = new System.Windows.Forms.TextBox();
			this.remotePortTextBox = new System.Windows.Forms.TextBox();
			this.remoteHostTextBox = new System.Windows.Forms.TextBox();
			this.okButton = new System.Windows.Forms.Button();
			this.cancelButton = new System.Windows.Forms.Button();
			this.SuspendLayout();
			// 
			// localPortLabel
			// 
			this.localPortLabel.Location = new System.Drawing.Point(8, 8);
			this.localPortLabel.Name = "localPortLabel";
			this.localPortLabel.Size = new System.Drawing.Size(64, 24);
			this.localPortLabel.TabIndex = 0;
			this.localPortLabel.Text = "Local Port:";
			// 
			// remoteHostLabel
			// 
			this.remoteHostLabel.Location = new System.Drawing.Point(8, 40);
			this.remoteHostLabel.Name = "remoteHostLabel";
			this.remoteHostLabel.Size = new System.Drawing.Size(80, 16);
			this.remoteHostLabel.TabIndex = 0;
			this.remoteHostLabel.Text = "Remote Host:";
			// 
			// remotePortLabel
			// 
			this.remotePortLabel.Location = new System.Drawing.Point(8, 64);
			this.remotePortLabel.Name = "remotePortLabel";
			this.remotePortLabel.Size = new System.Drawing.Size(72, 16);
			this.remotePortLabel.TabIndex = 0;
			this.remotePortLabel.Text = "Remote Port:";
			// 
			// localPortTextBox
			// 
			this.localPortTextBox.Location = new System.Drawing.Point(80, 10);
			this.localPortTextBox.Name = "localPortTextBox";
			this.localPortTextBox.Size = new System.Drawing.Size(48, 20);
			this.localPortTextBox.TabIndex = 1;
			this.localPortTextBox.Text = "";
			// 
			// remotePortTextBox
			// 
			this.remotePortTextBox.Location = new System.Drawing.Point(88, 62);
			this.remotePortTextBox.Name = "remotePortTextBox";
			this.remotePortTextBox.Size = new System.Drawing.Size(48, 20);
			this.remotePortTextBox.TabIndex = 3;
			this.remotePortTextBox.Text = "";
			// 
			// remoteHostTextBox
			// 
			this.remoteHostTextBox.Location = new System.Drawing.Point(88, 38);
			this.remoteHostTextBox.Name = "remoteHostTextBox";
			this.remoteHostTextBox.Size = new System.Drawing.Size(192, 20);
			this.remoteHostTextBox.TabIndex = 2;
			this.remoteHostTextBox.Text = "";
			// 
			// okButton
			// 
			this.okButton.DialogResult = System.Windows.Forms.DialogResult.OK;
			this.okButton.Location = new System.Drawing.Point(120, 96);
			this.okButton.Name = "okButton";
			this.okButton.TabIndex = 5;
			this.okButton.Text = "Ok";
			// 
			// cancelButton
			// 
			this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
			this.cancelButton.Location = new System.Drawing.Point(208, 96);
			this.cancelButton.Name = "cancelButton";
			this.cancelButton.TabIndex = 6;
			this.cancelButton.Text = "Cancel";
			// 
			// HostForm
			// 
			this.AcceptButton = this.okButton;
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.CancelButton = this.cancelButton;
			this.ClientSize = new System.Drawing.Size(292, 128);
			this.Controls.Add(this.okButton);
			this.Controls.Add(this.localPortTextBox);
			this.Controls.Add(this.remotePortTextBox);
			this.Controls.Add(this.remoteHostTextBox);
			this.Controls.Add(this.localPortLabel);
			this.Controls.Add(this.remoteHostLabel);
			this.Controls.Add(this.remotePortLabel);
			this.Controls.Add(this.cancelButton);
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
			this.Name = "HostForm";
			this.Text = "Edit Mapping";
			this.ResumeLayout(false);

		}
		#endregion

	}
}
