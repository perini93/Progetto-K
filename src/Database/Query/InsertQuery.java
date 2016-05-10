/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Query;

import Application.Controller.Applicazione;
import Università.Corsi.Ascoltatori.CaricaCorsi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSlider;
import javax.swing.JTextArea;

/**
 *
 * @author te4o
 */
public class InsertQuery {

    public static void inserisciLibro(String titolo, String descrizione, int prezzo, String telefono){

        String insertLibro = "INSERT INTO libri VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        
        try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(insertLibro);
                ps1.clearParameters();
                ps1.setInt(1, prossimoID("libri"));
                ps1.setString(2, titolo);
                ps1.setString(3, descrizione);
                ps1.setString(4, Applicazione.facoltàAttuale.getNome());
                ps1.setString(5, Applicazione.corsoAttuale.getNome());
                ps1.setString(6, Applicazione.guest.getEmail());
                ps1.setString(7, telefono);
                ps1.setInt(8, prezzo);
                
                ps1.execute();
                
            }   catch (SQLException ex) {   
            Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public static void inserisciAppunto(String nome, String descrizione){
        
        String insertAppunto = "INSERT INTO appunti VALUES (?, ?, ?, ?, ?);";
        
        try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(insertAppunto);
                ps1.clearParameters();
                ps1.setString(1, nome);
                ps1.setString(2, descrizione);
                ps1.setString(3, Applicazione.guest.getEmail());              
                ps1.setString(4, Applicazione.corsoAttuale.getNome());
                ps1.setString(5, Applicazione.facoltàAttuale.getNome());
                ps1.execute();
                
                }   catch (SQLException ex) {   
                Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public static int prossimoID(String tabella){
    
        String tabellaQuery = tabella.replaceAll("'", "\\\\'");
        
        int prossimoID = 0;
        
        String selectId = "select max(id) as massimo from "+tabellaQuery+"";
        
        try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(selectId);
                
                ResultSet rs = ps1.executeQuery();

                if(rs.next()) {

                    prossimoID = rs.getInt("massimo");

                }
          
            }   catch (SQLException ex) {   
            Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return prossimoID+1;
    }
    
    public static void inserisciDomanda(String titolo, String domanda){
        
        String insertDomanda= "INSERT INTO domande VALUES (?, ?, ?, 0, ?, ?);";
        
        try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(insertDomanda);
                ps1.clearParameters();
                ps1.setString(1, titolo);
                ps1.setString(2, domanda);
                ps1.setString(3, Applicazione.guest.getEmail());              
                ps1.setString(4, Applicazione.corsoAttuale.getNome());
                ps1.setString(5, Applicazione.facoltàAttuale.getNome());
                
                ps1.execute();
                
            }   catch (SQLException ex) {   
            Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
    public static void inserisciRisposta(String risposta){

   
        String insertRisposta = "INSERT INTO risposte VALUES (?, ?, ?, ?, 0, 0);";
        try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(insertRisposta);
                ps1.clearParameters();
                ps1.setString(1, Applicazione.domandaAttuale.getTitolo());
                ps1.setString(2, Applicazione.guest.getEmail());
                ps1.setInt(3, prossimoID("risposte"));
                ps1.setString(4, risposta);              

                
                ps1.execute();
                
            }   catch (SQLException ex) {   
            Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public static void inserisciValutazione(JTextArea commento, JSlider punteggio){
        
        String inserisciValutazione = "INSERT INTO valutazioni VALUES (?, ?, ?, ?, ?, ?)";
        
        try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(inserisciValutazione);
                ps1.clearParameters();
                ps1.setString(1, Applicazione.appuntoAttuale.getNome());
                ps1.setString(2, Applicazione.guest.getEmail());              
                ps1.setString(3, commento.getText());
                ps1.setInt(4, punteggio.getValue());
                ps1.setString(5, Applicazione.facoltàAttuale.getNome());
                ps1.setString(6, Applicazione.corsoAttuale.getNome());
                
                ps1.execute();
                
            }   catch (SQLException ex) {   
            Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    }
    
    public static void inserisciLikeDomanda(){
        
        String insertLikeDomanda = "INSERT INTO likeDomanda VALUES (?, ?, ?, ?)";
        
        try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(insertLikeDomanda);
                ps1.clearParameters();
                ps1.setString(1, Applicazione.corsoAttuale.getNome());
                ps1.setString(2, Applicazione.domandaAttuale.getTitolo());
                ps1.setString(3, Applicazione.guest.getEmail());
                ps1.setString(4, Applicazione.facoltàAttuale.getNome());
                
                ps1.execute();
                
            }   catch (SQLException ex) {   
                    Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public static void updateTelefono(String telefono){
        
        String sql = "update studenti set telefono='"+telefono+"' where email='"+Applicazione.guest.getEmail()+"'";
        
         try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(sql);
                
                ps1.execute();
                
            }   catch (SQLException ex) {   
                    Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public static void inserisciFacoltàPreferita(){
        
        String insertFacoltàPreferita = "INSERT INTO facoltàPreferite VALUES (?, ?, ?)";
        
        try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(insertFacoltàPreferita);
                ps1.clearParameters();
                ps1.setString(1, Applicazione.facoltàAttuale.getNome());
                ps1.setString(2, Applicazione.facoltàAttuale.getRamo());
                ps1.setString(3, Applicazione.guest.getEmail());
                
                ps1.execute();
                
            }   catch (SQLException ex) {   
                    Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public static void inserisciCorsoPreferito(){
        
        String insertCorsoPreferito = "INSERT INTO corsiPreferiti VALUES (?, ?, ?, ?)";
        
        try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(insertCorsoPreferito);
                ps1.clearParameters();
                ps1.setString(1, Applicazione.guest.getEmail());
                ps1.setString(2, Applicazione.corsoAttuale.getNome());
                ps1.setString(3, Applicazione.facoltàAttuale.getNome());
                ps1.setInt(4, Applicazione.corsoAttuale.getAnno());
                
                ps1.execute();
                
            }   catch (SQLException ex) {   
                    Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public static void inserisciAppuntoPreferito(){
        
        String insertAppuntoPreferito = "INSERT INTO appuntiPreferiti VALUES (?, ?, ?, ?, ?, ?)";
        
        try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(insertAppuntoPreferito);
                ps1.clearParameters();
                ps1.setString(1, Applicazione.guest.getEmail());
                ps1.setString(2, Applicazione.appuntoAttuale.getNome());
                ps1.setString(3, Applicazione.appuntoAttuale.getDescrizione());
                ps1.setString(4, Applicazione.appuntoAttuale.getStudente());
                ps1.setString(5, Applicazione.corsoAttuale.getNome());
                ps1.setString(6, Applicazione.facoltàAttuale.getNome());
                
                ps1.execute();
                
            }   catch (SQLException ex) {   
                    Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public static void inserisciLibroPreferito(){
        
        String insertLibroPreferito = "INSERT INTO libriPreferiti VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(insertLibroPreferito);
                ps1.clearParameters();
                ps1.setString(1, Applicazione.guest.getEmail());
                ps1.setInt(2, Applicazione.libroAttuale.getID());
                ps1.setString(3, Applicazione.libroAttuale.getTitolo());
                ps1.setString(4, Applicazione.libroAttuale.getDescrizione());
                ps1.setString(5, Applicazione.libroAttuale.getTelefono());
                ps1.setInt(6, Applicazione.libroAttuale.getPrezzo());
                ps1.setString(7, Applicazione.libroAttuale.getStudente());
                ps1.setString(8, Applicazione.corsoAttuale.getNome());
                ps1.setString(9, Applicazione.facoltàAttuale.getNome());
                
                ps1.execute();
                
            }   catch (SQLException ex) {   
                    Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public static void inserisciDomandaPreferita(){
        
        String insertDomandaPreferita = "INSERT INTO domandePreferite VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(insertDomandaPreferita);
                ps1.clearParameters();
                ps1.setString(1, Applicazione.guest.getEmail());
                ps1.setString(2, Applicazione.domandaAttuale.getTitolo());
                ps1.setString(3, Applicazione.domandaAttuale.getDomanda());
                ps1.setInt(4, Applicazione.domandaAttuale.getLike());
                ps1.setString(5, Applicazione.domandaAttuale.getStudente());
                ps1.setString(6, Applicazione.corsoAttuale.getNome());
                ps1.setString(7, Applicazione.facoltàAttuale.getNome());
                
                ps1.execute();
                
            }   catch (SQLException ex) {   
                    Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
        public static void inserisciLikeRisposta(int id, int like){
        
        String sql = "INSERT INTO likeRisposte VALUES (?, ?, ?)";
        
        try{
                PreparedStatement ps1 = Applicazione.DBconnection.prepareStatement(sql);
                ps1.clearParameters();
                ps1.setString(1, Applicazione.guest.getEmail());
                ps1.setInt(2, id);
                ps1.setInt(3, like);
                
                ps1.execute();
                
            }   catch (SQLException ex) {   
                    Logger.getLogger(CaricaCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
