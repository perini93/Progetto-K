/*
* Ascoltatore dedicato alla modifica del numero di like della domanda attuale
*/
package Ascoltatori.QeA;

import Application.Applicazione;
import Grafica.Grafica;
import Database.ControlloQuery;
import Database.DeleteQuery;
import Database.InfoQuery;
import Database.InsertQuery;
import Database.ListeQuery;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author adrian
 */
public class AggiungiLikeDomanda implements ActionListener{
    
    private Applicazione applicazione = Applicazione.getInstance();
    
    //dichiarazione oggetti
    private JButton like;
    private JLabel Nlike;
    
    public AggiungiLikeDomanda(JButton like, JLabel Nlike) {
        this.like = like;
        this.Nlike = Nlike;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        try {
            
            if(!ControlloQuery.controlloLikeDomanda()){
                
                InfoQuery.caricaInfoDomanda(applicazione.corsoAttuale.getNome(), applicazione.facoltàAttuale.getNome());
                DeleteQuery.decrementaLikeDomanda();
                DeleteQuery.eliminaLikeDomanda();
                applicazione.domandaAttuale.setLike(applicazione.domandaAttuale.getLike()-1);
                Nlike.setText(applicazione.domandaAttuale.getLike()+" likes");
                like.setIcon(new ImageIcon(this.getClass().getResource("/immagini/thumbup.png")));
                
            }else{
                
                InfoQuery.caricaInfoDomanda(applicazione.corsoAttuale.getNome(), applicazione.facoltàAttuale.getNome());
                InsertQuery.inserisciLikeDomanda();
                InsertQuery.updateLikeDomanda();
                applicazione.domandaAttuale.setLike(applicazione.domandaAttuale.getLike()+1);
                Nlike.setText(applicazione.domandaAttuale.getLike()+" likes");
                like.setIcon(new ImageIcon(this.getClass().getResource("/immagini/thumbupON.png")));
            }
           
            applicazione.svuotaRisposte();
            
            ListeQuery.caricaRisposteDomanda();
            Grafica.container.add(GoToDomanda.getDomanda(), "domanda");
            Grafica.card.show(Grafica.container, "domanda");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Errore durante l'aggiunta del like", "Impossibile completare l'operazione", JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
