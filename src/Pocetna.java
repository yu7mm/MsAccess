
import java.awt.Color;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Desetka
 */
public class Pocetna extends javax.swing.JFrame {

    public Pocetna() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSinhronizuj = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taText = new javax.swing.JTextArea();
        btnZgrade = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblRemoteStatus = new javax.swing.JLabel();
        btnMarkirajFalse = new javax.swing.JButton();
        btnMarkirajTrue = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sinhronizacija baze podataka");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btnSinhronizuj.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSinhronizuj.setText("Sinhronizuj");
        btnSinhronizuj.setEnabled(false);
        btnSinhronizuj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSinhronizujActionPerformed(evt);
            }
        });

        lblStatus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 0, 0));
        lblStatus.setText("Učitavam lokalnu bazu podataka, molim sačekajte...");

        taText.setColumns(20);
        taText.setRows(5);
        jScrollPane1.setViewportView(taText);

        btnZgrade.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnZgrade.setText("Prikaži zgrade");
        btnZgrade.setEnabled(false);
        btnZgrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZgradeActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField1.setText("600");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Max. broj redova u upitu");

        lblRemoteStatus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblRemoteStatus.setForeground(new java.awt.Color(255, 0, 0));
        lblRemoteStatus.setText(" ");

        btnMarkirajFalse.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMarkirajFalse.setText("Markiraj false");
        btnMarkirajFalse.setEnabled(false);
        btnMarkirajFalse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarkirajFalseActionPerformed(evt);
            }
        });

        btnMarkirajTrue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMarkirajTrue.setText("Markiraj true");
        btnMarkirajTrue.setEnabled(false);
        btnMarkirajTrue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarkirajTrueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnMarkirajFalse, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnMarkirajTrue)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSinhronizuj, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92)
                                .addComponent(btnZgrade, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblRemoteStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblStatus)
                .addGap(8, 8, 8)
                .addComponent(lblRemoteStatus)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSinhronizuj, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnZgrade, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnMarkirajFalse, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMarkirajTrue, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void extPrikazInfo(String poruka) {
        taText.append(poruka + "\n");
    }
    
    public void extRemoteConn(String poruka, java.awt.Color boja) {
        lblRemoteStatus.setText(poruka);
        lblRemoteStatus.setForeground(boja);
    }
    
    public void extKrajSync() {
        btnSinhronizuj.setEnabled(true);
        btnZgrade.setEnabled(true);
        taText.append("\nKRAJ SINHRONIZACIJE!\n");
        lblStatus.setForeground(Color.blue);
        lblStatus.setText("Sinhronizacija završena!");
    }
    
    private void prikaziMemoriju() {
        Runtime rt = Runtime.getRuntime();
        extPrikazInfo("Alocirano memorije: " + rt.maxMemory());
        extPrikazInfo("Slobodno memorije: " + rt.freeMemory());
    }
    
    private void btnSinhronizujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSinhronizujActionPerformed
        btnSinhronizuj.setEnabled(false);
        btnZgrade.setEnabled(false);
        taText.setText("Počinje sinhronizacija...\n");
        lblStatus.setForeground(Color.red);
        lblStatus.setText("Sinhronizacija u toku...");
        MsAccessDatabaseConnectionInJava8 accbaza = new MsAccessDatabaseConnectionInJava8();
        
        try {
            accbaza.maxRedova = Integer.parseInt(jTextField1.getText());
        }
        catch (NumberFormatException ex) {
            
        }

        //this.invalidate();
        //this.validate();
        //this.repaint();
        //this.repaint();
        //this.validate();
        //this.revalidate();
        //JOptionPane.showMessageDialog(null, "Sinhronizacija započeta!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
        
        //accbaza.setPocetna(this);
        Thread tr = new Thread(accbaza);
        tr.start();
    }//GEN-LAST:event_btnSinhronizujActionPerformed


    private void btnZgradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZgradeActionPerformed
        String zgrade = MsAccessDatabaseConnectionInJava8.prikaziZgrade();
        lblStatus.setText("Spreman za rad");
        lblStatus.setForeground(new java.awt.Color(0, 195, 0));
        taText.setText(zgrade);
    }//GEN-LAST:event_btnZgradeActionPerformed


    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        taText.append("Zatvaranje baza podataka...");
        MsAccessDatabaseConnectionInJava8.izlaz();
    }//GEN-LAST:event_formWindowClosing

    private void btnMarkirajFalseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarkirajFalseActionPerformed
        MsAccessDatabaseConnectionInJava8.markirajCeluBazu(false);
    }//GEN-LAST:event_btnMarkirajFalseActionPerformed

    private void btnMarkirajTrueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarkirajTrueActionPerformed
        MsAccessDatabaseConnectionInJava8.markirajCeluBazu(true);
    }//GEN-LAST:event_btnMarkirajTrueActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pocetna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        Pocetna p = new Pocetna();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                p.setVisible(true);

            }
        });
        p.prikaziMemoriju();
        MsAccessDatabaseConnectionInJava8.setPocetna(p);
        MsAccessDatabaseConnectionInJava8.init();
        p.btnSinhronizuj.setEnabled(true);
        p.btnZgrade.setEnabled(true);
        p.lblStatus.setForeground(new java.awt.Color(0, 195, 0));
        p.lblStatus.setText("Baza učitana!");
        p.prikaziMemoriju();
        //JOptionPane.showMessageDialog(null, "Baza učitana", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
        DefaultCaret caret = (DefaultCaret)p.taText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMarkirajFalse;
    private javax.swing.JButton btnMarkirajTrue;
    private javax.swing.JButton btnSinhronizuj;
    private javax.swing.JButton btnZgrade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblRemoteStatus;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTextArea taText;
    // End of variables declaration//GEN-END:variables

}
