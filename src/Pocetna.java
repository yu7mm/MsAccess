
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
        lblStatus.setText("Učitavam bazu podataka, molim sačekajte...");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSinhronizuj, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92)
                        .addComponent(btnZgrade, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblStatus)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSinhronizuj, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnZgrade, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void extPrikazInfo(String poruka) {
        taText.append(poruka + "\n");
    }
    
    public void extKrajSync() {
        btnSinhronizuj.setEnabled(true);
        taText.append("Kraj sinhronizacije!\n");
        lblStatus.setForeground(Color.blue);
        lblStatus.setText("Sinhronizacija završena!");
    }
    
    
    private void btnSinhronizujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSinhronizujActionPerformed
        btnSinhronizuj.setEnabled(false);
        taText.setText("Počinje sinhrnizacija...\n");
        lblStatus.setForeground(Color.red);
        lblStatus.setText("Započinjem sinhronizaciju...");

        //this.invalidate();
        //this.validate();
        //this.repaint();
        //this.repaint();
        //this.validate();
        //this.revalidate();
        //JOptionPane.showMessageDialog(null, "Sinhronizacija započeta!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);

        MsAccessDatabaseConnectionInJava8 accbaza = new MsAccessDatabaseConnectionInJava8();
        accbaza.setPocetna(this);
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
        MsAccessDatabaseConnectionInJava8.izlaz();
    }//GEN-LAST:event_formWindowClosing

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
        MsAccessDatabaseConnectionInJava8.init();
        p.btnSinhronizuj.setEnabled(true);
        p.btnZgrade.setEnabled(true);
        p.lblStatus.setForeground(new java.awt.Color(0, 195, 0));
        p.lblStatus.setText("Baza učitana!");
        //JOptionPane.showMessageDialog(null, "Baza učitana", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
        DefaultCaret caret = (DefaultCaret)p.taText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSinhronizuj;
    private javax.swing.JButton btnZgrade;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTextArea taText;
    // End of variables declaration//GEN-END:variables

}
