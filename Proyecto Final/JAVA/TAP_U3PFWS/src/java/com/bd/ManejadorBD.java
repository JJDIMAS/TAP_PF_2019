/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bd;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Shimishumi
 */
public class ManejadorBD {
    private Connection conexion;
    private Statement sentencia;
    //importante que la libreria sea la shida.
    public ManejadorBD()throws Exception{
    Class.forName("com.mysql.jdbc.Driver");
    conexion= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sherlock","root",null);
    sentencia = (Statement) conexion.createStatement();
    }
   public ArrayList<String>muestraCatalogo()throws Exception{
   String qry= "SELECT * FROM catalogo";
   ResultSet rs= sentencia.executeQuery(qry);
   ArrayList<String> resultados= new ArrayList<>();
   while(rs.next()){
   resultados.add(rs.getInt("ID")+"-"+rs.getString("descripcion"));
   }
  return resultados; }
}
