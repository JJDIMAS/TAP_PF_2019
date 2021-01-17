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
    public partial class Login2 : Form
    {
        public Login2()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            JObject json = new JObject();
            json.Add("usuario", textBox1.Text.ToString());
            json.Add("pass",textBox2.Text.ToString());

            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            string respuesta = client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/login");
            JObject jobj = (JObject)JToken.Parse(respuesta);
            String ans = "" + jobj["mensaje"];
            if (ans.Equals("Correcto")) {
                this.Dispose();
                new Decidir(textBox1.Text.ToString()).Visible = true;
            } else {
                label3.Visible = true;
            }
        }

        private void textBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            label3.Visible = false;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            //Boton de registro juar juar
            panel1.Visible = true;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (!checkBox1.Checked) { label10.Visible = true; } else {
                //Se hace el registro y se cierra la wea
                JObject json = new JObject();
                json.Add("id", textBox3.Text);
                json.Add("usr", textBox4.Text);
                json.Add("pass", textBox5.Text);

                //Se realiza la petición al WS
                WebClient client = new WebClient();
                client.QueryString.Add("log", json.ToString());
                client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/registrar");
                textBox3.Text = "";
                textBox4.Text = "";
                textBox5.Text = "";
                textBox6.Text = "";
                textBox7.Text = "";
                checkBox1.Checked = false;
                panel1.Visible = false;

            }
        }
    }
}
