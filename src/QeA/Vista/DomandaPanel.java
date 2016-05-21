 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QeA.Vista;

import QeA.Ascoltatori.EliminaDomanda;
import QeA.Ascoltatori.AggiungiRisposta;
import Application.Controller.Applicazione;
import Database.Query.InfoQuery;
import Database.Query.ControlloQuery;
import Header.Vista.TopPanel;
import Preferiti.Ascoltatori.AggiungiDomandaPreferita;
import Preferiti.Ascoltatori.RimuoviDomandaPreferita;
import QeA.Ascoltatori.AggiungiLike;
import QeA.Ascoltatori.AggiungiLikeRisposta;
import Utils.Vista.CustomScrollBar;
import Utils.Vista.ScrollBarUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

/**
 *
 * @author cl410688
 */
public class DomandaPanel extends JPanel{

    private JButton rispondi, elimina, like2, likeRisposta, dislikeRisposta, preferitiOn,
            preferitiOff;
    private TopPanel top;
    private JPanel panel, pannelloRisposta, pannelloLike, pannelloDislike, preferitiPanel, descrizionePanel, rispostePanel, rispostaPanel;
    private JLabel email, Nlike, nomeRisposta, numeroLikeRisposta,numeroDislikeRisposta;
    private JTextArea descrizione, rispondiArea;
    private JTextArea risposte2 ;
    private JScrollPane scrollPanel, scrollPanel1, scrollPanel3, scrollPanel4, scrollPanel5;
    private AggiungiRisposta risposta;
    private GridBagConstraints gbcRisposte;
    private ImageIcon rispondiNormal, rispondiHover, rispondiPressed, eliminaNormal,eliminaHover, eliminaPressed;
    private static int i;
    private int valoreLike;
    private AggiungiDomandaPreferita aggiungiDomandaPreferita;
    private RimuoviDomandaPreferita rimuoviDomandaPreferito;
    private AggiungiLike aggiungiLike;
    private EliminaDomanda eliminaDomanda;
    
    public DomandaPanel() {
        
        setBackground(Color.white);
        
        top = new TopPanel(Applicazione.domandaAttuale.getTitolo());      
        top.setBackground(Color.white);
        
        panel = new JPanel();
        panel.setBackground(Color.white);
        
        preferitiPanel = new JPanel();
        preferitiPanel.setBackground(Color.white);
        preferitiPanel.setPreferredSize(new Dimension(650, 35));
        
        rispostePanel = new JPanel();
        rispostePanel.setBackground(Color.white);
        
        rispostaPanel = new JPanel();
        rispostaPanel.setBackground(Color.white);
        
        //preferito
        preferitiOn = new JButton(new ImageIcon(this.getClass().getResource("/immagini/preferitiOn.png")));
        preferitiOn.setBackground(Color.white);
        preferitiOn.setBorder(new LineBorder(Color.white, 1, true));
        
        preferitiOff = new JButton(new ImageIcon(this.getClass().getResource("/immagini/preferitiOff.png")));
        preferitiOff.setBackground(Color.white);
        preferitiOff.setBorder(new LineBorder(Color.white, 1, true));
        
        aggiungiDomandaPreferita = new AggiungiDomandaPreferita();
        preferitiOff.addActionListener(aggiungiDomandaPreferita);
        
        rimuoviDomandaPreferito = new RimuoviDomandaPreferita();
        preferitiOn.addActionListener(rimuoviDomandaPreferito);
        
        try {
            if (ControlloQuery.controlloDomandaPreferita()) {
                preferitiPanel.add(preferitiOff);
            }
            else {
                preferitiPanel.add(preferitiOn);
            }
        } catch (SQLException ex) {
            System.out.println("Errore durante il controllo dell'appunto preferito");
        }//fine zona preferito
        
        this.build();

        scrollPanel1 = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel1.setPreferredSize(new Dimension(650, 400));
        scrollPanel1.setBackground(Color.white);
        scrollPanel1.setBorder(new LineBorder(Color.white));
        scrollPanel1.setVerticalScrollBar(new CustomScrollBar());
        
        add(top);
        add(preferitiPanel);
        add(scrollPanel1);
    }
    
    public void build(){
        
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
                
        gbcRisposte = new GridBagConstraints(); 
        
        email = new JLabel("<html><b>Caricata da: </b>"+Applicazione.domandaAttuale.getStudente()+"</html>");
        email.setFont(new Font("Century Gothic", Font.PLAIN, 15));
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.insets = new Insets(10, 0, 0, 0);
	gbc.anchor = GridBagConstraints.CENTER;
	panel.add(email, gbc);
        
        descrizionePanel = new JPanel();
        descrizionePanel.setBackground(Color.white);
        descrizionePanel.setBorder(BorderFactory.createTitledBorder("Descrizione"));
        
        descrizione = new JTextArea(Applicazione.domandaAttuale.getDomanda(),5,25);
        descrizione.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        descrizione.setBackground(new Color(239,242,243));
        descrizione.setEditable(false);
        descrizione.setLineWrap(true);
        descrizione.setWrapStyleWord(true);
        
        scrollPanel = new JScrollPane();
        scrollPanel.setViewportView(descrizione);
        scrollPanel.setWheelScrollingEnabled(true);
        scrollPanel.setVerticalScrollBar(new CustomScrollBar());
	gbc.gridx = 0;
	gbc.gridy = 1;
	gbc.insets = new Insets(10, 0, 0, 0);
	gbc.anchor = GridBagConstraints.CENTER;
        descrizionePanel.add(scrollPanel);
	panel.add(descrizionePanel, gbc);

        //like
        like2 = new JButton(new ImageIcon(this.getClass().getResource("/immagini/thumbup.png")));
        like2.setBackground(Color.white);
        like2.setPreferredSize(new Dimension(30, 30));
        like2.setBorder(new LineBorder(Color.white, 1));
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.insets = new Insets(10, 0, 0, 0);
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(like2, gbc);
        try {
            if(!ControlloQuery.controlloLikeDomanda()){
                like2.setIcon(new ImageIcon(this.getClass().getResource("/immagini/thumbupON.png")));
            }
        } catch (SQLException ex) {
            System.out.println("Errore durante il controllo del like della domanda");
        }
        
        Nlike = new JLabel();
        Nlike.setText(Applicazione.domandaAttuale.getLike()+" likes");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 100, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(Nlike, gbc);

        aggiungiLike = new AggiungiLike(like2, Nlike);
        like2.addActionListener(aggiungiLike);
        //fine zona like
        
        pannelloRisposta = new JPanel(new GridBagLayout());        

        for(i = 0; i < Applicazione.listaRisposteAttuali.size(); i++){    

            setRisposte2(Applicazione.listaRisposteAttuali.get(i).getTitolo(), i, Applicazione.listaRisposteAttuali.get(i).getNickname());
        
        }

        scrollPanel3 = new JScrollPane(pannelloRisposta,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel3.setPreferredSize(new Dimension(480 , 250));
        scrollPanel3.getVerticalScrollBar().setUnitIncrement(10);
        scrollPanel3.setWheelScrollingEnabled(true);
        scrollPanel3.setVerticalScrollBar(new CustomScrollBar());
	gbc.gridx = 0;
	gbc.gridy = 3;
	gbc.insets = new Insets(10, 0, 0, 0);
	gbc.anchor = GridBagConstraints.CENTER;
        rispostePanel.setBorder(BorderFactory.createTitledBorder("Risposte"));
        rispostePanel.add(scrollPanel3);
	panel.add(rispostePanel, gbc);
        
        rispondiArea = new JTextArea(4,20);
        rispondiArea.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        rispondiArea.setLineWrap(true);
        rispondiArea.setWrapStyleWord(true);
        
        scrollPanel4 = new JScrollPane();
        scrollPanel4.setViewportView(rispondiArea);
        scrollPanel4.setWheelScrollingEnabled(true);
        scrollPanel4.setBorder(new LineBorder(Color.white));
        scrollPanel4.setVerticalScrollBar(new CustomScrollBar());
	gbc.gridx = 0;
	gbc.gridy = 4;
	gbc.insets = new Insets(10, 0, 30, 0);
	gbc.anchor = GridBagConstraints.CENTER;
        rispostaPanel.setBorder(BorderFactory.createTitledBorder("Inserisci qui una risposta"));
        rispostaPanel.add(scrollPanel4);
	panel.add(rispostaPanel, gbc);
        
        rispondiNormal = new ImageIcon(this.getClass().getResource("/immagini/buttonNormal.png"));
        rispondi = new JButton(rispondiNormal);
        rispondi.setBorder(BorderFactory.createEmptyBorder());
        rispondi.setContentAreaFilled(false);
        rispondiHover = new ImageIcon(this.getClass().getResource("/immagini/buttonHover.png"));
        rispondi.setRolloverIcon(rispondiHover);
        rispondiPressed = new ImageIcon(this.getClass().getResource("/immagini/buttonPressed.png"));
        rispondi.setPressedIcon(rispondiPressed);
        rispondi.setText("RISPONDI");
        rispondi.setFont(new Font("Century Gothic", Font.PLAIN, 15));
        rispondi.setForeground(Color.white);
        rispondi.setIconTextGap(-85);
        rispondi.setPreferredSize(new Dimension(110, 40));
        gbc.gridx = 0;
	gbc.gridy = 4;
	gbc.insets = new Insets(10, 400, 30, 0);
	gbc.anchor = GridBagConstraints.CENTER;
        
        risposta = new AggiungiRisposta(rispondiArea);
        rispondi.addActionListener(risposta);
	panel.add(rispondi, gbc);
        
        if (Applicazione.domandaAttuale.getStudente().equals(Applicazione.guest.getEmail())) {
            
            eliminaDomanda = new EliminaDomanda();
            eliminaNormal = new ImageIcon(this.getClass().getResource("/immagini/deleteNormal.png"));
            elimina = new JButton(eliminaNormal);
            elimina.setBorder(BorderFactory.createEmptyBorder());
            elimina.setContentAreaFilled(false);
            eliminaHover = new ImageIcon(this.getClass().getResource("/immagini/deleteHover.png"));
            elimina.setRolloverIcon(eliminaHover);
            eliminaPressed = new ImageIcon(this.getClass().getResource("/immagini/deletePressed.png"));
            elimina.setPressedIcon(eliminaPressed);
            elimina.setText("ELIMINA");
            elimina.setFont(new Font("Century Gothic", Font.PLAIN, 15));
            elimina.setForeground(Color.white);
            elimina.setIconTextGap(-81);
            elimina.setPreferredSize(new Dimension(110, 40));
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.insets = new Insets(0, 0, 30, 0);
            gbc.anchor = GridBagConstraints.CENTER;
            elimina.addActionListener(eliminaDomanda);
            panel.add(elimina, gbc);
        }
    }

    public void setNomeRisposta(JLabel nomeRisposta) {
        this.nomeRisposta = nomeRisposta;
    }

    public void setRisposte2(String risposta, int i, String nome) {
        try {
            valoreLike = ControlloQuery.controlloLikeRisposta(Applicazione.listaRisposteAttuali.get(i).getId());
        } catch (SQLException ex) {
            Logger.getLogger(DomandaPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
            nomeRisposta = new JLabel();
            nomeRisposta.setFont(new Font("Century Gothic", Font.BOLD, 13));
            nomeRisposta.setText(nome);
            gbcRisposte.gridx = 0;
            gbcRisposte.gridy = i;
            gbcRisposte.insets = new Insets(15, 5, 0, 10);
            gbcRisposte.anchor = GridBagConstraints.LINE_START;
            pannelloRisposta.add(this.nomeRisposta, gbcRisposte);

            risposte2 = new JTextArea(4,15);
            risposte2.setFont(new Font("Century Gothic", Font.PLAIN, 13));
            risposte2.setText(risposta);
            risposte2.setEditable(false);
            risposte2.setLineWrap(true);
            risposte2.setWrapStyleWord(true);
            scrollPanel5 = new JScrollPane(risposte2,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPanel5.setWheelScrollingEnabled(true);
            scrollPanel5.setVerticalScrollBar(new CustomScrollBar());
            gbcRisposte.gridx = 1;
            gbcRisposte.gridy = i;
            gbcRisposte.insets = new Insets(10, 5, 10, 10);
            gbcRisposte.anchor = GridBagConstraints.LINE_START;
            pannelloRisposta.add(scrollPanel5, gbcRisposte);
            
            pannelloDislike = new JPanel();
            pannelloLike = new JPanel();
            
            //all interno dell pannello like
            int likelike;
            
            try {
                likelike = InfoQuery.likeRisposta(Applicazione.listaRisposteAttuali.get(i).getId(), 1);
                numeroLikeRisposta = new JLabel(""+likelike);
                pannelloLike.add(numeroLikeRisposta);
                likeRisposta = new JButton();
                if(valoreLike==0){
                 
                    likeRisposta.setIcon(new ImageIcon(this.getClass().getResource("/immagini/thumbup.png")));
                }else{
                    if(valoreLike==1){
                        
                        likeRisposta.setIcon(new ImageIcon(this.getClass().getResource("/immagini/thumbupON.png")));
                    }else{
                        
                        likeRisposta.setIcon(new ImageIcon(this.getClass().getResource("/immagini/thumbup.png")));
                    }
                    
                }
               
                likeRisposta.setName("like");
                likeRisposta.setBackground(new Color(239,242,243));
                likeRisposta.setPreferredSize(new Dimension(30, 30));
                likeRisposta.setBorder(new LineBorder(new Color(239,242,243), 1));
            } catch (SQLException ex) {
                System.out.println("Errore durante il caricamento dei like della risposta");
            }
            
            pannelloLike.add(likeRisposta);
                     
            gbcRisposte.gridx = 3;
            gbcRisposte.gridy = i;
            gbcRisposte.insets = new Insets(15, 5, 0, 0);
            gbcRisposte.anchor = GridBagConstraints.LINE_START;
            pannelloRisposta.add(pannelloLike, gbcRisposte);
            
            //all interno dell pannello dislike
            int dislikelike;
            
            try {
                dislikelike = InfoQuery.likeRisposta(Applicazione.listaRisposteAttuali.get(i).getId(), -1);
            
                numeroDislikeRisposta = new JLabel(""+dislikelike);
                pannelloDislike.add(numeroDislikeRisposta);
                dislikeRisposta = new JButton();
                if(valoreLike==0){
                    dislikeRisposta.setIcon(new ImageIcon(this.getClass().getResource("/immagini/thumbdown.png")));
                }else{
                    if(valoreLike==1){
                        dislikeRisposta.setIcon(new ImageIcon(this.getClass().getResource("/immagini/thumbdown.png")));
                    }else{
                        dislikeRisposta.setIcon(new ImageIcon(this.getClass().getResource("/immagini/thumbdownON.png")));
                    }
                    
                }
               
                dislikeRisposta.setName("dislike");
                dislikeRisposta.setBackground(new Color(239,242,243));
                dislikeRisposta.setPreferredSize(new Dimension(30, 30));
                dislikeRisposta.setBorder(new LineBorder(new Color(239,242,243), 1));
            } catch (SQLException ex) {
                System.out.println("Errore durante il caricamento dei like della risposta");
            }
            
            pannelloDislike.add(dislikeRisposta);
            AggiungiLikeRisposta alr = new AggiungiLikeRisposta(i,Applicazione.listaRisposteAttuali.get(i).getId(), numeroLikeRisposta, numeroDislikeRisposta, likeRisposta, dislikeRisposta);
            likeRisposta.addMouseListener(alr);
            dislikeRisposta.addMouseListener(alr);
            gbcRisposte.gridx = 4;
            gbcRisposte.gridy = i;
            gbcRisposte.insets = new Insets(15, -5, 0, 0);
            gbcRisposte.anchor = GridBagConstraints.LINE_START;
            pannelloRisposta.add(pannelloDislike, gbcRisposte);
             
  
    }

    public static void setI(int i) {
        DomandaPanel.i = i;
    }

    public static int getI() {
        return i;
    }
    
}
    



