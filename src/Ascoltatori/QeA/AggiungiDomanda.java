/*
* Ascoltatore dedicato al caricamento di una nuova domanda all'interno
* del corso
*/
package Ascoltatori.QeA;

import Database.InsertQuery;
import Application.Applicazione;
import Vista.Grafica;
import Database.ControlloQuery;
import Database.ListeQuery;
import Vista.AggiungiDomandaPanel;
import Vista.ListaDomandePanel;
import utility.Ordina;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author te4o
 */
public class AggiungiDomanda implements ActionListener{
    
    private Applicazione applicazione = Applicazione.getInstance();
    
    //dichiarazione
    private JTextArea titolo;
    private JTextArea descrizione;
    
    ListaDomandePanel domande;
    
    public AggiungiDomanda(JTextArea titolo, JTextArea descrizione) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if((!titolo.getText().equals(""))&&(!descrizione.getText().equals(""))){
            if((titolo.getText().length()<100)&&(descrizione.getText().length()<500)){
                try {
                    if(ControlloQuery.controlloTitoloDomanda(titolo.getText())){
                        
                        try {
                            InsertQuery.inserisciDomanda(titolo.getText(), descrizione.getText());
                            
                            JOptionPane.showMessageDialog(null, "Domanda aggiunta correttamente.", "Operazione avvenuta con successo", JOptionPane.INFORMATION_MESSAGE);
                            
                            applicazione.svuotaDomande();
                            
                            ListeQuery.caricaDomande();
                            
                            Ordina.Domande();
                            
                            applicazione.back.remove(applicazione.back.size()-1);
                            
                            domande = new ListaDomandePanel();
                            Grafica.container.add(domande, "domande");
                            Grafica.card.show(Grafica.container, "domande");
                            
                            AggiungiDomandaPanel.clearForm();
                            
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Errore durante l'aggiunta della domanda", "Impossibile completare l'operazione", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Una domanda con lo stesso titolo è già presente all'interno \ndi '"+applicazione.facoltàAttuale.getNome()+">"+applicazione.corsoAttuale.getNome()+"', verifica "
                                + "che non sia \nla stessa e riprova cambiando titolo.","Impossibile caricare domanda" , JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    System.out.println("Errore durante il controllo del titolo della domanda");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Titolo(max 100 caratteri) e/o descrizione(max 500 caratteri) troppo lunghi", "Impossibile completare l'operazione", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Titolo e/o descrizione non validi", "Impossibile completare l'operazione", JOptionPane.ERROR_MESSAGE);
        }
    }
}
