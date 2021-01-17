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
using iTextSharp.text;
using iTextSharp.text.pdf;
using System.IO;

namespace TAP_U3PF
{
    public partial class Decidir : Form
    {
        private String us = "";
        private int size=0;
        private FileInfo fileImg;

        public Decidir()
        {
            //InitializeComponent();
        }
        public Decidir(string usr) {
            this.us = usr;
            InitializeComponent();
        }

        private void button6_Click(object sender, EventArgs e)
        {
            //Boton para cerra sesión
            JObject json = new JObject();
            json.Add("usuario", us);
 

            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/LogOut");
            this.Hide();
            new Form1().Visible = true;

        }

        private void button9_Click(object sender, EventArgs e)
        {
            //Boton para registrar un nuevp lugar DENTRO DEL PANEL
            JObject json = new JObject();
            json.Add("id",textBox1.Text);
            json.Add("nombre", textBox2.Text);
            json.Add("foto", textBox3.Text);
            json.Add("direccion", textBox4.Text);
            json.Add("horario", textBox5.Text);
            json.Add("categoria", textBox6.Text);

            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/NuevoLugar");
            textBox1.Text = "";
            textBox2.Text = "";
            textBox3.Text = "";
            textBox4.Text = "";
            textBox5.Text = "";
            textBox6.Text = "";
        }

        private void button8_Click(object sender, EventArgs e)
        {
            //Abrir el panel de registro de lugares
            panel1.Visible = true;
        }

        private void button7_Click(object sender, EventArgs e)
        {
            //Boton para abrir el panel de busqueda
            panel2.Visible = true;
        }

        private void button10_Click(object sender, EventArgs e)
        {
            //Boton buscar para palabras clave uwu
            JObject json = new JObject();
            json.Add("palabras", textBox7.Text);
          
            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            String respuesta=client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/Buscar");
            //Limpiar la caja
            textBox7.Text = "";

            String n="", f="";
            int s = 0, r = 0;
            for (int i = 0; i < respuesta.Length; i++)
            {
                if (respuesta[i].Equals('*')){ s++;continue; }
                if (s==0) { n += respuesta[i];}
                if (s==1) { if (respuesta[i].Equals('!')) { } else { f += respuesta[i]; } }
                if (respuesta[i].Equals('!')) { s = 0;

                    dataGridView1.Rows.Add();
                    dataGridView1.Rows[r].Cells[0].Value = n;
                    dataGridView1.Rows[r].Cells[1].Value = f;
                    r++;
                    n = "";f = "";size++;

                }

            }
        }

        private void textBox7_KeyPress(object sender, KeyPressEventArgs e)
        {
            dataGridView1.Rows.Clear();
            size = 0;
        }

        private void button11_Click(object sender, EventArgs e)
        {
            panel2.Visible = false;
        }

        private void button12_Click(object sender, EventArgs e)
        {
            //BTN para generar el PDF

            Document doc = new Document();
            try
            {
                PdfWriter.GetInstance(doc, new FileStream("lugares.pdf", FileMode.Create));
                doc.Open();

                //Imagen
                iTextSharp.text.Image img = iTextSharp.text.Image.GetInstance(label13.Text);
                //img.ScaleToFit(327, 100);
                // Texto
                Paragraph parrafo = new Paragraph("Gracias por utilizar Sherlock");
                Paragraph parrafo2 = new Paragraph("\nResultados de la búsqueda:");
                Paragraph parrafo3 = new Paragraph("\n");
                // Tabla
                PdfPTable table = new PdfPTable(2); // 3 columnas
                // Theader
                //table.AddCell(new Phrase("Img", new iTextSharp.text.Font(iTextSharp.text.Font.FontFamily.COURIER, 18, iTextSharp.text.Font.BOLD)));
                table.AddCell(new Phrase("Nombre", new iTextSharp.text.Font(iTextSharp.text.Font.FontFamily.COURIER, 18, iTextSharp.text.Font.BOLD)));
                table.AddCell(new Phrase("Dirección", new iTextSharp.text.Font(iTextSharp.text.Font.FontFamily.COURIER, 18, iTextSharp.text.Font.BOLD)));
                // Tbody
                //ciclo para las weas
                for (int i = 0; i < size; i++)
                {
                    //necesitamos el ws colegas
                  //  JObject json = new JObject();
                   // json.Add("nombre", dataGridView1.Rows[i].Cells[0].Value.ToString());

                    //Se realiza la petición al WS
                    //WebClient client = new WebClient();
                    //client.QueryString.Add("log", json.ToString());
                    //String respuesta = client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/imagen");
                    //iTextSharp.text.Image img2 = iTextSharp.text.Image.GetInstance(respuesta);

                    //table.AddCell(img2);
                    table.AddCell(dataGridView1.Rows[i].Cells[0].Value.ToString());
                    table.AddCell(dataGridView1.Rows[i].Cells[1].Value.ToString());
                }
                

                
                // Agregar todo
                doc.Add(img);
                doc.Add(parrafo);
                doc.Add(parrafo2);
                doc.Add(parrafo3);
                doc.Add(table);
                doc.Close();
                // Success
                MessageBox.Show("Generado");
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
                MessageBox.Show("No se genero");
            }

        }

        private void button5_Click(object sender, EventArgs e)
        {
            //Ver favoritos
            panel3.Visible = true;
            JObject json = new JObject();
            json.Add("usuario", us);
            Console.WriteLine(us);

            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            String respuesta = client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/VerFavs2");
            JArray jar = (JArray)JToken.Parse(respuesta);
            for (int i = 0; i < jar.Count; i++)
            {
                JObject jobj = (JObject)JToken.Parse(jar[i].ToString());
               // Console.WriteLine(jobj);
                dataGridView2.Rows.Add();
                dataGridView2.Rows[i].Cells[0].Value = jobj["nombre"];
                dataGridView2.Rows[i].Cells[1].Value = jobj["dire"];
            }
        }

        private void button14_Click(object sender, EventArgs e)
        {
            panel3.Visible = false;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            //BTN DIVERSION
            JObject json = new JObject();
            json.Add("categoria", "Diversion");

            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            string respuesta = client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/cate");
            JObject jobj = (JObject)JToken.Parse(respuesta);
            JArray jar = (JArray)JToken.Parse(jobj["resultado"].ToString());
            if (jar.Count != 0)
            {
                for (int i = 0; i < jar.Count; i++)
                {
                    JObject job = (JObject)JToken.Parse(jar[i].ToString());
                    new Lugar(us,job["id"].ToString(),job["nombre"].ToString(), job["foto"].ToString(), job["direccion"].ToString(), job["horario"].ToString()).Visible = true;
                }
            }
            else { MessageBox.Show("Sin resultados");}
        }

        private void button3_Click(object sender, EventArgs e)
        {
            //BTN Deportes
            JObject json = new JObject();
            json.Add("categoria", "Deportes");

            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            string respuesta = client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/cate");
            JObject jobj = (JObject)JToken.Parse(respuesta);
            JArray jar = (JArray)JToken.Parse(jobj["resultado"].ToString());
            if (jar.Count != 0)
            {
                for (int i = 0; i < jar.Count; i++)
                {
                    JObject job = (JObject)JToken.Parse(jar[i].ToString());
                    new Lugar(us, job["id"].ToString(), job["nombre"].ToString(), job["foto"].ToString(), job["direccion"].ToString(), job["horario"].ToString()).Visible = true;
                }
            }
            else { MessageBox.Show("Sin resultados"); }
        }

        private void button4_Click(object sender, EventArgs e)
        {
            //BTN Cultura
            JObject json = new JObject();
            json.Add("categoria", "Cultura");

            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            string respuesta = client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/cate");
            JObject jobj = (JObject)JToken.Parse(respuesta);
            JArray jar = (JArray)JToken.Parse(jobj["resultado"].ToString());
            if (jar.Count != 0)
            {
                for (int i = 0; i < jar.Count; i++)
                {
                    JObject job = (JObject)JToken.Parse(jar[i].ToString());
                    new Lugar(us, job["id"].ToString(), job["nombre"].ToString(), job["foto"].ToString(), job["direccion"].ToString(), job["horario"].ToString()).Visible = true;
                }
            }
            else { MessageBox.Show("Sin resultados"); }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            //BTN Gastronomia
            JObject json = new JObject();
            json.Add("categoria", "Comida");

            //Se realiza la petición al WS
            WebClient client = new WebClient();
            client.QueryString.Add("log", json.ToString());
            string respuesta = client.DownloadString("http://localhost:8080/TAP_U3MPF/webresources/bd/cate");
            JObject jobj = (JObject)JToken.Parse(respuesta);
            JArray jar = (JArray)JToken.Parse(jobj["resultado"].ToString());
            if (jar.Count != 0)
            {
                for (int i = 0; i < jar.Count; i++)
                {
                    JObject job = (JObject)JToken.Parse(jar[i].ToString());
                    new Lugar(us, job["id"].ToString(), job["nombre"].ToString(), job["foto"].ToString(), job["direccion"].ToString(), job["horario"].ToString()).Visible = true;
                }
            }
            else { MessageBox.Show("Sin resultados"); }
        }

        private void button13_Click(object sender, EventArgs e)
        {
            panel1.Visible = false;
        }
    }
}
