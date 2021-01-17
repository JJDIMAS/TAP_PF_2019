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
    public partial class Lugar : Form
    {
        private String usr = "", ID = "", ID_U = "";
        public Lugar()
        {
          //  InitializeComponent();
        }
        public Lugar(String us, String id, String nombre, String foto, String direccion, String horario )
        {
            InitializeComponent();
            this.usr = us;
            this.ID = id;
            label10.Text = horario;
            label11.Text = direccion;
            label2.Text = nombre;
            pictureBox1.SizeMode = PictureBoxSizeMode.StretchImage;
            pictureBox1.Load(foto.Replace('*','/'));

            //Conseguir el ID real del usuario
            JObject json = new JObject();
            json.Add("usuario", us);

            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            string respuesta = client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/GetId");
            JObject jobj = (JObject)JToken.Parse(respuesta);
            this.ID_U = jobj["id"].ToString();
            //Cargar los TIPS y esas weas

            Tips();
            calif();

        }
        void Tips() {
            richTextBox1.Text = "";
            JObject json = new JObject();
            json.Add("id", ID);

            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            string respuesta = client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/TIPS");
            JObject jobj = (JObject)JToken.Parse(respuesta);
            JArray jar = (JArray)JToken.Parse(jobj["resultado"].ToString());
            if(jar.Count!=0){
                for (int i = 0; i < jar.Count; i++)
                {
                    JObject job = (JObject)JToken.Parse(jar[i].ToString());
                    String t = richTextBox1.Text;
                    String tip = job["tip"].ToString();
                    if (tip.Length>43) {
                        String tip2="";
                        for (int i2 = 0; i2 < tip.Length; i2++)
                        {
                            if (i2 == 40) { tip2 += "\n"; tip2 += tip[i2]; } else { tip2 += tip[i2]; }
                        }
                        tip = tip2;
                    }
                    richTextBox1.Text = t + job["id"].ToString() + "\n"+tip+"\n\n";
                }
            }
        }
        void calif() {
            JObject json = new JObject();
            json.Add("id", ID);

            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            string respuesta = client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/Calificacion");
            JObject jobj = (JObject)JToken.Parse(respuesta);
            JArray jar = (JArray)JToken.Parse(jobj["resultado"].ToString());
            if (jar.Count!=0)
            {
                int suma = 0;
                for (int i = 0; i < jar.Count; i++)
                {
                    JObject job = (JObject)JToken.Parse(jar[i].ToString());
                    suma = suma + int.Parse(job["calificacion"].ToString());

                }
                label1.Text = (suma/jar.Count).ToString();
            }
            
        }

        private void label7_Click(object sender, EventArgs e)
        {

        }

        private void button3_Click(object sender, EventArgs e)
        {
            label7.Visible = true;
            //Agregar a favoritos
            JObject json = new JObject();
            json.Add("id_usuario", ID_U);
            json.Add("id_lugar", ID);
            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            string respuesta = client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/AgregarFav");
            
        }

        private void button1_Click(object sender, EventArgs e)
        {
            String tip = Microsoft.VisualBasic.Interaction.InputBox("Nombre");
            //Agregar TIPS
            JObject json = new JObject();
            json.Add("id_usuario", ID_U);
            json.Add("id_lugar", ID);
            json.Add("tip",tip);
            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/RegTip");
            Tips();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void button2_Click(object sender, EventArgs e)
        {

            //BTOTN VER MAS
            this.Dispose();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            //Agregar calificacion
            String calificacion = Microsoft.VisualBasic.Interaction.InputBox("Ingrese la calificación: ");
            JObject json = new JObject();
            int c = int.Parse(calificacion);
            json.Add("id_usuario",ID_U);
            json.Add("id_lugar", ID);
            json.Add("calificacion",c);

            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            string respuesta = client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/RegCal");
            calif();
        }
    }
}
