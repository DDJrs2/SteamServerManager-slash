/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamservermanager.view;


import javax.swing.text.DefaultCaret;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import steamservermanager.interfaces.serverrunner.ServerGameMessageReceiver;
import steamservermanager.interfaces.serverrunner.ServerMessageDispatcher;
import steamservermanager.interfaces.serverrunner.ServerProperties;

/**
 *
 * @author pen
 */
public class ServerGameConsole extends javax.swing.JFrame {

   private ServerProperties serverProperties;
   private ServerMessageDispatcher serverMessageDispatcher;
   private StandardOutputInterfaceImpl standardOutputInterfaceImpl;
    
    /**
     * Creates new form ServerGameConsole
     */
    public ServerGameConsole(ServerProperties serverProperties) {
        initComponents();
        this.serverProperties = serverProperties;
        this.standardOutputInterfaceImpl = new StandardOutputInterfaceImpl();
        this.serverMessageDispatcher = serverProperties.setListener(standardOutputInterfaceImpl);
        setTitle(serverProperties.getServerGame().getServerName());
        
        DefaultCaret caret = (DefaultCaret) jTextAreaSteamCMD.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);  
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaSteamCMD = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();

        jTextAreaSteamCMD.setEditable(false);
        jTextAreaSteamCMD.setBackground(new java.awt.Color(80, 80, 80));
        jTextAreaSteamCMD.setColumns(20);
        jTextAreaSteamCMD.setForeground(new java.awt.Color(102, 204, 0));
        jTextAreaSteamCMD.setRows(5);
        jScrollPane2.setViewportView(jTextAreaSteamCMD);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 965, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
    	
        String command = jTextField1.getText();
        
        if(command.length() > 0 && java.awt.event.KeyEvent.VK_ENTER == evt.getKeyCode()){
            serverMessageDispatcher.send(command);
            jTextField1.setText("");
        }  
    }//GEN-LAST:event_jTextField1KeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaSteamCMD;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    public ServerProperties getServerProperties(){
        return serverProperties;
    }
    
    public void setServerProperties(ServerProperties serverProperties){
        this.serverProperties = serverProperties;
        this.serverMessageDispatcher = serverProperties.setListener(standardOutputInterfaceImpl);
    }
    
    public void forceStop(){
        serverMessageDispatcher.stop();
    }
    
    class StandardOutputInterfaceImpl implements ServerGameMessageReceiver{

        CircularFifoQueue<String> mensagemFifo = new CircularFifoQueue<>(500);
        
        @Override
        public void onOutput(String msg) {
            
            mensagemFifo.add(msg + "\n");
            
            StringBuilder stringBuilder = new StringBuilder();
            
            for(String msgFifo : mensagemFifo){
                stringBuilder.append(msgFifo);
            }

            jTextAreaSteamCMD.setText(stringBuilder.toString());  
        }
    }
}
