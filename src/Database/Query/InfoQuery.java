/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Query;

import Appunti.Appunto;
import Application.Controller.Applicazione;
import Libri.Libro;
import QeA.Domanda;
import Università.Corsi.Ascoltatori.CaricaCorsi;
import Università.Corsi.Corso;
import Università.Facolta.Facoltà;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author te4o
 */
public class InfoQuery {
    
    
    public static void caricaInfoFacoltà(){
    
        String selectInfoFacoltà = "select * from facoltà where nome=?";
        
        try{
           PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(selectInfoFacoltà);
           ps1.clearParameters();
           ps1.setString(1, Applicazione.facoltàAttuale.getNome());

           ResultSet rs = ps1.executeQuery();

           while(rs.next()){

               String nome = rs.getString("nome");
               String ramo = rs.getString("ramo");

               Applicazione.facoltàAttuale = new Facoltà(nome, ramo);

           }
           }   catch (SQLException ex) {   
           Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
    
    public static void caricaInfoCorso(String facoltà){
    
        String selectInfoCorso = "select * from corsi where nome=? and facoltà=?";
        
         try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(selectInfoCorso);
                ps1.clearParameters();
                ps1.setString(1, Applicazione.corsoAttuale.getNome());
                ps1.setString(2, facoltà);

                ResultSet rs = ps1.executeQuery();

                while(rs.next()){

                    String nome= rs.getString("nome");
                    String fac = rs.getString("facoltà");
                    int anno = rs.getInt("anno");

                    Applicazione.corsoAttuale = new Corso(nome, anno, fac);

                }
                }   catch (SQLException ex) {   
                Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public static void caricaInfoLibro(String corso, String facoltà, int id){
    
        String selectInfoLibro = "select * from libri where facoltà=? and corso=? and id=?";
        
         try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(selectInfoLibro);
                ps1.clearParameters();
                ps1.setString(1, facoltà);
                ps1.setString(2, corso);
                ps1.setInt(3, id);

                ResultSet rs = ps1.executeQuery();

                while(rs.next()){

                    String nomeLibro = rs.getString("titolo");
                    String descrizioneLibro = rs.getString("descrizione");
                    int idLibro = rs.getInt("id");
                    String emailLibro = rs.getString("studente");
                    String telefonoLibro = rs.getString("telefono");
                    int prezzoLibro = rs.getInt("prezzo");
                    String cor = rs.getString("corso");
                    String fac = rs.getString("facoltà");
                
                    Applicazione.libroAttuale = new Libro(nomeLibro, descrizioneLibro, idLibro, telefonoLibro,emailLibro,  prezzoLibro, cor, fac);

                }
                }   catch (SQLException ex) {   
                Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public static void caricaInfoDomanda(String corso, String facoltà){
    
        String selectInfoDomanda = "select * from domande where facoltà=? and corso=? and titolo=?";
        
         try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(selectInfoDomanda);
                ps1.clearParameters();
                ps1.setString(1, facoltà);
                ps1.setString(2, corso);
                ps1.setString(3,Applicazione.domandaAttuale.getTitolo());

                ResultSet rs = ps1.executeQuery();

                while(rs.next()){

                    String titoloDomanda = rs.getString("titolo");
                    String testoDomanda = rs.getString("domanda");
                    String studenteDomanda = rs.getString("studente");
                    int likeDomanda = rs.getInt("like");
                    String cor = rs.getString("corso");
                    String fac = rs.getString("facoltà");

                    Applicazione.domandaAttuale = new Domanda(titoloDomanda, testoDomanda, studenteDomanda, likeDomanda, cor, fac);

                }
                }   catch (SQLException ex) {   
                Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public static void caricaInfoAppunto(String corso, String facoltà){
    
        String selectInfoDomanda = "select * from appunti where facoltà=? and corso=? and nome=?";
        
         try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(selectInfoDomanda);
                ps1.clearParameters();
                ps1.setString(1, facoltà);
                ps1.setString(2, corso);
                ps1.setString(3,Applicazione.appuntoAttuale.getNome());

                ResultSet rs = ps1.executeQuery();

                while(rs.next()){

                    String nomeAppunto = rs.getString("nome");
                    String descrizioneAppunto = rs.getString("descrizione");
                    String emailAppunto = rs.getString("studente");
                    String cor = rs.getString("corso");
                    String fac = rs.getString("facoltà");

                    Applicazione.appuntoAttuale = new Appunto(nomeAppunto, descrizioneAppunto, emailAppunto, cor, fac);

                }
                }   catch (SQLException ex) {   
                Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public static float mediaAppunto(){
    
        float media = 0;
        
        String selectMedia = "SELECT avg(punteggio) as media FROM valutazioni where facoltà=? and corso=? and appunto=?";
        
         try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(selectMedia);
                ps1.clearParameters();
                ps1.setString(1, Applicazione.facoltàAttuale.getNome());
                ps1.setString(2, Applicazione.corsoAttuale.getNome());
                ps1.setString(3,Applicazione.appuntoAttuale.getNome());

                ResultSet rs = ps1.executeQuery();

                while(rs.next()){

                    media = rs.getFloat("media");

                }
                }   catch (SQLException ex) {   
                Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
                }
         return media;
        
    }
    
    public static int likeDomanda(){
    
        int like = 0;
        
        String selectMedia = "SELECT * FROM likeDomanda where facoltà=? and corso=? and domanda=?";
        
         try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(selectMedia);
                ps1.clearParameters();
                ps1.setString(1, Applicazione.facoltàAttuale.getNome());
                ps1.setString(2, Applicazione.corsoAttuale.getNome());
                ps1.setString(3,Applicazione.domandaAttuale.getTitolo());

                ResultSet rs = ps1.executeQuery();

                while(rs.next()){

                    like++;

                }
                }   catch (SQLException ex) {   
                Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
                }
         return like;
    }
    

}
