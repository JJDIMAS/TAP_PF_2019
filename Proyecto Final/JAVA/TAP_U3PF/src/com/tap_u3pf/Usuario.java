
package com.tap_u3pf;


public class Usuario {
        Usuario(){}
        public Usuario(String usr){
        this.usr=usr;
        }
  public Usuario(String id,String usr, String pass, int activo) {
        this.usr = usr;
        this.pass = pass;
        this.activo = activo;
        this.id = id;
        
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

   

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
    String id;
    String usr;
   String pass;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
   int activo;  
}
