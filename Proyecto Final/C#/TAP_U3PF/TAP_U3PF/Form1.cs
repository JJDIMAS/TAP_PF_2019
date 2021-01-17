using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TAP_U3PF
{
    public partial class Form1 : Form
    {
        public String usr ="";
        public Form1()
        {
            InitializeComponent();
            timer1.Start();
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            JObject json = new JObject();
            json.Add("estado","1");
            
            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            string respuesta = client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/verificar");
            JObject jobj = (JObject)JToken.Parse(respuesta);
            String ans = "" + jobj["usuario"];
            usr = ans;
            if (ans.Equals("Incorrecto"))
            {
                this.Hide();
                new Login2().Visible = true;
            }
            else {
                this.Hide();
                new Decidir(usr).Visible = true;
            }
            
            timer1.Stop();
        }
    }
}
