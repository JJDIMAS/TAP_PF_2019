
package com.tap_u3pf;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;


public class Lugar extends javax.swing.JFrame {
private String urlWS="http://localhost:8080/TAP_U3MPF/webresources/bd/";
private String us="",ID="",ID_U="";
    public Lugar() {
       
       
    }
  public Lugar(String us,String ID,String nombre,String foto,String direccion,String horario){
        initComponents();
       this.us=us;
       this.ID=ID;
        jLabel4.setText(nombre);
        jLabel10.setText(horario);
        jLabel12.setText(direccion);
        this.setLocationRelativeTo(null);
        ver_mas.setOpaque(false);
        ver_mas.setContentAreaFilled(false);
        ver_mas.setBorderPainted(false);
        jLabel6.setVisible(false);
        jButton1.setOpaque(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setBorderPainted(false);
        tips();
        foto(foto);
        calificacion();
        
        ////SOLICITUD/////
         String ans="";
        JSONObject jobj= new JSONObject();
        jobj.put("usuario", us);
        
        //System.out.println(jobj.toString());
        String jsonEnviar = jobj.toString().replace("{","%7B").replace("}","%7D");
       // System.out.println(jsonEnviar);
         Client client =  ClientBuilder.newClient();
         WebTarget target = client.target(urlWS);
         String respuesta= target.path("GetId").queryParam("log",jsonEnviar).request("application/json").get(String.class);
       
         try{
    JSONObject jsonrespuesta=(JSONObject)(new JSONParser()).parse(respuesta);
 ans=""+jsonrespuesta.get("id");
    }catch(Exception e){e.printStackTrace();}
         this.ID_U=ans;
  }
  void tips(){
      jTextArea1.setText("");
  String ans="";
        JSONObject jobj= new JSONObject();
        jobj.put("id",ID);  
        String jsonEnviar = jobj.toString().replace("{","%7B").replace("}","%7D");
        Client client =  ClientBuilder.newClient();
        WebTarget target = client.target(urlWS);
    String respuesta= target.path("TIPS").queryParam("log",jsonEnviar).request(MediaType.APPLICATION_JSON).get(String.class);
    JSONArray jar=new JSONArray();
    try {      
        JSONObject jsr=(JSONObject)(new JSONParser()).parse(respuesta);
        jar=(JSONArray)jsr.get("resultado");
        if(!jar.isEmpty()){
      for(int i=0;i<jar.size();i++){
        JSONObject obj=(JSONObject)jar.get(i);
        //JButton button = new JButton();
        //JButton button2=new JButton();
        //JTextArea jt= new JTextArea();
           //button.setBackground(Color.GREEN);
          // button.setBackground(new java.awt.Color(51, 204, 255));
          // button.setSize(100,50);
          // jt.setBackground(new java.awt.Color(51, 204, 255));
          // jt.setSize(344,100);
           //jt.setText(obj.get("tip").toString());
            //button2.setBackground(new java.awt.Color(51, 204, 255));
           //button2.setSize(344,100);
           //button.setText(obj.get("id").toString());
           //jPanel1.add(button);
           //button2.setText(obj.get("tip").toString());
           //jPanel1.add(jt);
           //jPanel1.updateUI();
           //jPanel1.repaint();
           String t=jTextArea1.getText();
           String tip=obj.get("tip").toString();
           if(tip.length()>43){
               String tip2="";
           for(int i2=0;i2<tip.length();i2++){
           char c=tip.charAt(i2);
           if(i2==40){tip2+="\n";tip2+=c;}
           else{tip2+=c;}
           }
           tip=tip2;
           }
          jTextArea1.setText(t+obj.get("id").toString()+"\n"+tip+"\n\n");
      }
    
        
    }
    
     
    } catch (ParseException ex) {
        Logger.getLogger(Decidir.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  void foto(String foto){
  try {
      String f=foto.replace('*', '/');
      //System.out.println(f);
            URL url = new URL(f);
            BufferedImage bi = ImageIO.read(url); // original
            ImageIcon icon = new ImageIcon(bi);
            // Se agrega al label
            //labelImg.setIcon(icon);
            // Se agrega al panelImage
            //panelImage1.setIcon(icon);
            //panelImage1.updateUI();
            /*
                Se obtiene la imagen de una url externa re-sized 100 x 100
            */
            BufferedImage bi2 = new BufferedImage(465, 265, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = bi2.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(icon.getImage(), 0, 0, 465, 265, null);
            graphics.dispose();
            
            ImageIcon iconResized = new ImageIcon(bi2);
            // Se agrega al label
            //jLabel13.setIcon(iconResized);
            // Se agrega al panelImage
            panelImage1.setIcon(iconResized);
            panelImage1.updateUI();
            panelImage1.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
  }
  void calificacion(){
   JSONObject jobj= new JSONObject();
        jobj.put("id",ID);  
        String jsonEnviar = jobj.toString().replace("{","%7B").replace("}","%7D");
        Client client =  ClientBuilder.newClient();
        WebTarget target = client.target(urlWS);
    String respuesta= target.path("Calificacion").queryParam("log",jsonEnviar).request(MediaType.APPLICATION_JSON).get(String.class);
    JSONArray jar=new JSONArray();
    int suma=0;
    try {      
        JSONObject jsr=(JSONObject)(new JSONParser()).parse(respuesta);
        jar=(JSONArray)jsr.get("resultado");
        if(!jar.isEmpty()){
      for(int i=0;i<jar.size();i++){
        JSONObject obj=(JSONObject)jar.get(i);
        suma=suma+Integer.parseInt(obj.get("calificacion").toString());
      }
    jLabel8.setText(String.valueOf(suma/jar.size()));
        
    }
    
     
    } catch (ParseException ex) {
        Logger.getLogger(Decidir.class.getName()).log(Level.SEVERE, null, ex);
    }
  
  }
  
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        buttonTask1 = new org.edisoncor.gui.button.ButtonTask();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ver_mas = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Code Bold", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("VER MÁS LUGARES");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 470, 200, 30));

        jLabel2.setFont(new java.awt.Font("Code Bold", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tips:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, 80, 30));

        jLabel10.setFont(new java.awt.Font("Code Bold", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Agregar Horario");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 320, 70));

        jLabel11.setFont(new java.awt.Font("Code Bold", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Dirección");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 270, 30));

        jLabel12.setFont(new java.awt.Font("Code Bold", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Agregar dirección");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 480, 30));

        jLabel9.setFont(new java.awt.Font("Code Bold", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Horario");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 270, 30));

        jLabel4.setFont(new java.awt.Font("Code Bold", 0, 18)); // NOI18N
        jLabel4.setText("Inserte el nombre aquí");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 270, 30));

        buttonTask1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/plos.png"))); // NOI18N
        buttonTask1.setText("Agregar Tip");
        buttonTask1.setDescription("Presione para agregar");
        buttonTask1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTask1ActionPerformed(evt);
            }
        });
        getContentPane().add(buttonTask1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 390, -1, 60));

        jButton2.setBackground(new java.awt.Color(51, 204, 255));
        jButton2.setFont(new java.awt.Font("Code Bold", 0, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Agregar Calificación");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 471, 170, 40));

        jLabel8.setFont(new java.awt.Font("Code Bold", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("0");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 150, 30, 30));

        jLabel6.setFont(new java.awt.Font("Code Bold", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 0, 0));
        jLabel6.setText("Agregado a favoritos");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 230, 220, 30));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/FavL.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 190, 50, 50));

        jLabel7.setFont(new java.awt.Font("Code Bold", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Caificación :");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 150, 120, 30));

        ver_mas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/blue-arrow-png-22.png"))); // NOI18N
        ver_mas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ver_masActionPerformed(evt);
            }
        });
        getContentPane().add(ver_mas, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 460, 100, 50));

        jLabel5.setFont(new java.awt.Font("Code Bold", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Agregar a favoritos");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 270, 30));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 280, 350, 100));

        panelImage1.setBackground(new java.awt.Color(0, 102, 102));
        panelImage1.setForeground(new java.awt.Color(0, 102, 102));

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );

        getContentPane().add(panelImage1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 270));

        jLabel1.setBackground(new java.awt.Color(51, 51, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/lugares.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ver_masActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ver_masActionPerformed
        // BOTON PARA VER MÁS LUGARES
        this.dispose();
    }//GEN-LAST:event_ver_masActionPerformed

    private void buttonTask1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTask1ActionPerformed
        // Agregar TIP
        String tip=JOptionPane.showInputDialog(null,"Ingrese su tip");
          JSONObject jobj= new JSONObject();
        jobj.put("id_usuario",ID_U);
        
        jobj.put("id_lugar",ID);
        
        jobj.put("tip",tip);
        
        String jsonEnviar = jobj.toString().replace("{","%7B").replace("}","%7D");
        Client client =  ClientBuilder.newClient();
        WebTarget target = client.target(urlWS);
    target.path("RegTip").queryParam("log",jsonEnviar).request(MediaType.APPLICATION_JSON).get(String.class);
    tips();
    }//GEN-LAST:event_buttonTask1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Agregar a favoritos
        jLabel6.setVisible(true);
          JSONObject jobj= new JSONObject();
        jobj.put("id_usuario",ID_U);
        jobj.put("id_lugar",ID);
        String jsonEnviar = jobj.toString().replace("{","%7B").replace("}","%7D");
        Client client =  ClientBuilder.newClient();
        WebTarget target = client.target(urlWS);
    target.path("AgregarFav").queryParam("log",jsonEnviar).request(MediaType.APPLICATION_JSON).get(String.class);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Agregar calificacion
        String c=JOptionPane.showInputDialog(null,"Ingrese su calificación");
        int x=Integer.parseInt(c);
        JSONObject jobj= new JSONObject();
        jobj.put("id_usuario",ID_U);
        //System.out.println(ID_U);
        jobj.put("id_lugar",ID);
        //System.out.println(ID);
        jobj.put("calificacion",x);
        //System.out.println(x);
        String jsonEnviar = jobj.toString().replace("{","%7B").replace("}","%7D");
        Client client =  ClientBuilder.newClient();
        WebTarget target = client.target(urlWS);
    target.path("RegCal").queryParam("log",jsonEnviar).request(MediaType.APPLICATION_JSON).get(String.class);
    }//GEN-LAST:event_jButton2ActionPerformed

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lugar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonTask buttonTask1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    private javax.swing.JButton ver_mas;
    // End of variables declaration//GEN-END:variables
}
