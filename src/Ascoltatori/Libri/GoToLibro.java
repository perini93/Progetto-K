/*
* Ascoltatore dedicato all'apertura del libro selezionato
* In base al libro selezionato, al corso e alla facoltà viene mandata in 
* esecuzione una query che carica i dati relativi a tale libro
* e vengono visualizzati in un pannello
*/
package Ascoltatori.Libri;

import Application.Applicazione;
import Grafica.Grafica;
import Database.InfoQuery;
import Grafica.LibroPanel;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import javax.swing.JLabel;

/**
 *
 * @author Te4o
 */
public class GoToLibro implements MouseListener{
    
    private Applicazione applicazione = Applicazione.getInstance();
    
    //dichiarazione variabili
    private String corso;
    private String facoltà;
    private int id;
    private String nomeLibro;
    private JLabel libri;
    
    private LibroPanel libro;
    
    public GoToLibro(String corso, String facoltà, int id,JLabel libri) {
        this.corso = corso;
        this.facoltà = facoltà;
        this.id = id;
        this.libri = libri;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        try {
            applicazione.back.add("libro");
            
            JLabel label = (JLabel)e.getComponent();
            nomeLibro = label.getText();
            
            applicazione.libroAttuale.setTitolo(nomeLibro);
            
            InfoQuery.caricaInfoLibro(corso, facoltà, id);
            
            applicazione.facoltàAttuale.setNome(facoltà);
            applicazione.corsoAttuale.setNome(corso);
            
            libro = new LibroPanel();
            Grafica.container.add(libro, "libro");
            Grafica.card.show(Grafica.container, "libro");
            
        } catch (SQLException ex) {
            System.out.println("Errore durante il caricamento deli dati del libro");
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        
        libri.setForeground(new Color(3,201,169));
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        libri.setForeground(null);
    }
    
    
}
