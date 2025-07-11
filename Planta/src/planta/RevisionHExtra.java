/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package planta;

import javax.swing.JOptionPane;

/**
 *
 * @author XPC
 */
public class RevisionHExtra extends javax.swing.JFrame {

    private String[] opcionesConsulta;
    private boolean banderaHorasExtra = false;
    /**
     * Creates new form Marcas
     */
    public RevisionHExtra() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        LblMarcas = new javax.swing.JLabel();
        TxtFieldCEmpleado = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        comboConsultaExtras = new javax.swing.JComboBox<>();
        BTNAceptarExtra = new javax.swing.JButton();
        BTNRechaExtra = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaConsulta = new javax.swing.JTextArea();
        BTNVolver = new javax.swing.JButton();
        BTNAceptarTodos = new javax.swing.JButton();
        BTNRechaTodos = new javax.swing.JButton();
        BTNConsultarExtras = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        LblMarcas.setFont(new java.awt.Font("Bell MT", 0, 36)); // NOI18N
        LblMarcas.setForeground(new java.awt.Color(0, 0, 0));
        LblMarcas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LblMarcas.setText("Revisión de Extras");

        TxtFieldCEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtFieldCEmpleadoActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Código de Supervisor:");

        BTNAceptarExtra.setBackground(new java.awt.Color(0, 0, 0));
        BTNAceptarExtra.setForeground(new java.awt.Color(255, 255, 255));
        BTNAceptarExtra.setText("Aceptar Extra");
        BTNAceptarExtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNAceptarExtraActionPerformed(evt);
            }
        });

        BTNRechaExtra.setBackground(new java.awt.Color(0, 0, 0));
        BTNRechaExtra.setForeground(new java.awt.Color(255, 255, 255));
        BTNRechaExtra.setText("Rechazar Extra");
        BTNRechaExtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNRechaExtraActionPerformed(evt);
            }
        });

        txtAreaConsulta.setColumns(20);
        txtAreaConsulta.setRows(5);
        jScrollPane1.setViewportView(txtAreaConsulta);

        BTNVolver.setBackground(new java.awt.Color(0, 0, 0));
        BTNVolver.setForeground(new java.awt.Color(255, 255, 255));
        BTNVolver.setText("Volver");
        BTNVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNVolverActionPerformed(evt);
            }
        });

        BTNAceptarTodos.setBackground(new java.awt.Color(0, 0, 0));
        BTNAceptarTodos.setForeground(new java.awt.Color(255, 255, 255));
        BTNAceptarTodos.setText("ACEPTAR TODOS");
        BTNAceptarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNAceptarTodosActionPerformed(evt);
            }
        });

        BTNRechaTodos.setBackground(new java.awt.Color(0, 0, 0));
        BTNRechaTodos.setForeground(new java.awt.Color(255, 255, 255));
        BTNRechaTodos.setText("RECHAZAR TODOS");
        BTNRechaTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNRechaTodosActionPerformed(evt);
            }
        });

        BTNConsultarExtras.setBackground(new java.awt.Color(0, 0, 0));
        BTNConsultarExtras.setForeground(new java.awt.Color(255, 255, 255));
        BTNConsultarExtras.setText("CONSULTAR EXTRAS");
        BTNConsultarExtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNConsultarExtrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(BTNAceptarTodos)
                .addGap(77, 77, 77)
                .addComponent(BTNRechaTodos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(231, 231, 231)
                                .addComponent(LblMarcas, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtFieldCEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                        .addComponent(BTNVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(BTNConsultarExtras)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BTNAceptarExtra)
                                .addGap(60, 60, 60)
                                .addComponent(BTNRechaExtra)
                                .addGap(137, 137, 137)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(comboConsultaExtras, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(206, 206, 206))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LblMarcas, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BTNVolver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(13, 13, 13)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtFieldCEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(62, 62, 62)
                .addComponent(comboConsultaExtras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BTNRechaExtra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BTNAceptarExtra, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BTNConsultarExtras, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTNAceptarTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BTNRechaTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtFieldCEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtFieldCEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtFieldCEmpleadoActionPerformed

    private void BTNConsultarExtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNConsultarExtrasActionPerformed
        // TODO add your handling code here:
        // verificar que el text field de supervisor tenga un número
        actualizarComboBoxYTextArea();
    }//GEN-LAST:event_BTNConsultarExtrasActionPerformed

    private void BTNAceptarExtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNAceptarExtraActionPerformed
        // TODO add your handling code here:
        try {
            int idSeleccionado = Integer.parseInt(opcionesConsulta[0].split(",")[comboConsultaExtras.getSelectedIndex()]);
            PlantaFunciones.cambiarRevisionDHoraExtra(idSeleccionado, "A");

            actualizarComboBoxYTextArea();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No hay horas extra para aceptar", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_BTNAceptarExtraActionPerformed

    private void BTNRechaExtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNRechaExtraActionPerformed
        // TODO add your handling code here:
        try {
            int idSeleccionado = Integer.parseInt(opcionesConsulta[0].split(",")[comboConsultaExtras.getSelectedIndex()]);
            PlantaFunciones.cambiarRevisionDHoraExtra(idSeleccionado, "R");

            actualizarComboBoxYTextArea();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No hay horas extra para rechazar", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_BTNRechaExtraActionPerformed

    private void BTNAceptarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNAceptarTodosActionPerformed
        // TODO add your handling code here:
        String idSupervisorStr = TxtFieldCEmpleado.getText();
        if (!PlantaFunciones.isNumeric(idSupervisorStr)) {
            JOptionPane.showMessageDialog(this, "Favor ingresar un número en ID Supervisor", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int idSupervisor = Integer.parseInt(idSupervisorStr);
        if (!PlantaFunciones.VerificarIdEmpleadoEmpleados(idSupervisor)) {
            JOptionPane.showMessageDialog(this, "Id de supervisor incorrecto, no existe como empleado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!banderaHorasExtra) {
            JOptionPane.showMessageDialog(this, "No hay horas extra para aceptar", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        
        PlantaFunciones.cambiarRevisionDHoraExtraTodos(idSupervisor, "A");
        
        actualizarComboBoxYTextArea();
    }//GEN-LAST:event_BTNAceptarTodosActionPerformed

    private void BTNRechaTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNRechaTodosActionPerformed
        // TODO add your handling code here:
        String idSupervisorStr = TxtFieldCEmpleado.getText();
        if (!PlantaFunciones.isNumeric(idSupervisorStr)) {
            JOptionPane.showMessageDialog(this, "Favor ingresar un número en ID Supervisor", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int idSupervisor = Integer.parseInt(idSupervisorStr);
        if (!PlantaFunciones.VerificarIdEmpleadoEmpleados(idSupervisor)) {
            JOptionPane.showMessageDialog(this, "Id de supervisor incorrecto, no existe como empleado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!banderaHorasExtra) {
            JOptionPane.showMessageDialog(this, "No hay horas extra para rechazar", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        PlantaFunciones.cambiarRevisionDHoraExtraTodos(idSupervisor, "R");
        
        actualizarComboBoxYTextArea();
    }//GEN-LAST:event_BTNRechaTodosActionPerformed

    private void BTNVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNVolverActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        MenuPrincipal MPrincipal = new MenuPrincipal();
        MPrincipal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BTNVolverActionPerformed

    private void actualizarComboBoxYTextArea(){
        // verificar que el text field de supervisor tenga un número
        String idSupervisorStr = TxtFieldCEmpleado.getText();
        if (!PlantaFunciones.isNumeric(idSupervisorStr)) {
            JOptionPane.showMessageDialog(this, "Favor ingresar un número en ID Supervisor", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int idSupervisor = Integer.parseInt(idSupervisorStr);
        if (!PlantaFunciones.VerificarIdEmpleadoEmpleados(idSupervisor)) {
            JOptionPane.showMessageDialog(this, "Id de supervisor incorrecto, no existe como empleado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        opcionesConsulta = PlantaFunciones.TraerInfoHorasExtra(idSupervisor);
        
        String[] opcionesIds = opcionesConsulta[0].split(",");
        String[] opcionesInfo = opcionesConsulta[1].split(",");
        if (opcionesIds[0].equals("")) {
            banderaHorasExtra = false;
        } else {
            banderaHorasExtra = true;
        }
        actualizarCombosBox();
        
        String strTextArea = "IdTupla \t IdEmpleado \t Nombre \t Fecha \t Horas Extra \n";
        for (int i = 0; i < opcionesInfo.length; i++) {
            strTextArea += opcionesIds[i] + "\t" + opcionesInfo[i] + "\n";
        }
        
        txtAreaConsulta.setText(strTextArea);
    }
    
    private void actualizarCombosBox(){
        comboConsultaExtras.removeAllItems();
        try {
            String[] opciones = opcionesConsulta[0].split(",");
            for (int i = 0; i < opciones.length; i++) {
                
                comboConsultaExtras.addItem(opciones[i]);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * @param args the command line arguments
     */
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RevisionHExtra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RevisionHExtra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RevisionHExtra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RevisionHExtra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RevisionHExtra().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNAceptarExtra;
    private javax.swing.JButton BTNAceptarTodos;
    private javax.swing.JButton BTNConsultarExtras;
    private javax.swing.JButton BTNRechaExtra;
    private javax.swing.JButton BTNRechaTodos;
    private javax.swing.JButton BTNVolver;
    private javax.swing.JLabel LblMarcas;
    private javax.swing.JTextField TxtFieldCEmpleado;
    private javax.swing.JComboBox<String> comboConsultaExtras;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtAreaConsulta;
    // End of variables declaration//GEN-END:variables
}
