/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package arithmatic.code;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Eslam
 */
public class Arithimatic extends javax.swing.JFrame {
            ArrayList <character> arr=new ArrayList();
            ArrayList arr_c=new ArrayList();
            double compress_value=0.0;
            String word=new String();
            
            public static class character{
           public char c;
           public double low_range;
           public double high_range;
          public  character(char c,double low_range,double high_range){
               this.c=c;
               this.low_range=low_range;
               this.high_range=high_range;
           }

            }
    
    public  void Write_on_file(){
      Formatter F=null;
      try{
            F=new Formatter("codes.txt");
   
            F.format("%s%n",compress_value);
            F.format("%s%n",word.length());
            for(int i=0;i<arr.size();i++)
                F.format("%s%s%s%s%s%n",arr.get(i).c,"  ",arr.get(i).low_range,"  ",arr.get(i).high_range);
            F.close();
      }catch(Exception e){
          System.out.println("Error!");
      }
      
  }
  
  public static String Read_from_file(){
      Scanner input=null;
      String w = new String();
      try{
            input=new Scanner(new File("input.txt"));
        
            while(input.hasNext())
                w=input.next(); 
            
            input.close();
            
      }catch(Exception e){
          System.out.println("Error!");
      }
        
        return w;
  }

        public  void Compression(){
         double low=0,high=0;
         word=Read_from_file();
        char []s=word.toCharArray();
        Arrays.sort(s);
        String sorted = new String(s);
       for(int i=0;i<sorted.length();i++){
          double count=0;
          if(arr_c.isEmpty()||!arr_c.contains(sorted.charAt(i))) {
              for(int j=i;j<sorted.length();j++){
               if(sorted.charAt(i)==sorted.charAt(j))
                  count++;
              }
              arr_c.add(sorted.charAt(i));
              high=(count/sorted.length())+low;
              character c=new character(sorted.charAt(i),low,high);
              arr.add(c);
              low=high;
          }
        }
       /*for(int i=0;i<arr.size();i++){
        System.out.println(arr.get(i).c+"   "+arr.get(i).low_range+"     "+arr.get(i).high_range);
       } */
       double Lower=0,Upper=1,L=0,U=1;
       for(int i=0;i<word.length();i++){
           
          int indx=arr_c.indexOf(word.charAt(i));
          L=Lower+((Upper-Lower)*arr.get(indx).low_range);     
          U=Lower+((Upper-Lower)*arr.get(indx).high_range); 
          Lower=L;Upper=U;
       }
       compress_value=(Upper+Lower)/2;
       Write_on_file();
        }
        public  void Decompression(){
           
            double de=0.0;
            int length=0;
            
          String temp="",w;
          Scanner input;
        try {
            input = new Scanner(new File("codes.txt"));
               temp=input.nextLine();
               temp.replaceAll(" ", ".");
               de=Double.parseDouble(temp);
         
               temp=input.nextLine();
               length=Integer.parseInt(temp);
            while(input.hasNextLine()){
             temp=input.nextLine();
            String []s=temp.split("  ");
             char c=s[0].charAt(0);
            double l=Double.parseDouble(s[1]);       
            double h=Double.parseDouble(s[2]);
            character ch=new character(c,l,h);
            arr.add(ch);
            }
            input.close();
            
        }catch (Exception e) {        
          System.out.println("Error!");
        }
      int size=0;
      String original_msg="";
        while(size!=length){
        for(int i=0;i<arr.size();i++){
            if(de>=arr.get(i).low_range && de<=arr.get(i).high_range){
               original_msg+=arr.get(i).c;
                System.out.print(arr.get(i).c);
                de=(de-arr.get(i).low_range)/(arr.get(i).high_range-arr.get(i).low_range);
            break;
            }
        }
        size++;
        } 
         Formatter F=null;
      try{
            F=new Formatter("decompressed.txt.txt");
  
            F.format("%s",original_msg);
            F.close();
      }catch(Exception e){
          System.out.println("Error!");
      }
        
        }
        
    public Arithimatic() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jRadioButton1.setText("Compression");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("Decompression");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Arthimiatic Algorithm.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton2)
                .addGap(36, 36, 36))
            .addGroup(layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(32, 32, 32)
                .addComponent(jButton1)
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
             if(jRadioButton1.isSelected()){
                Compression();
                JOptionPane.showMessageDialog(null,"   Done!,Please check the Codes.txt");
        }
       
        else if(jRadioButton2.isSelected()){
            Decompression();
            JOptionPane.showMessageDialog(null,"   Done!,Please check the decompression.txt");
        }
  
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
               jRadioButton1.setSelected(true);
               jRadioButton2.setSelected(false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
               jRadioButton2.setSelected(true);
               jRadioButton1.setSelected(false);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Arithimatic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Arithimatic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Arithimatic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Arithimatic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Arithimatic().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    // End of variables declaration//GEN-END:variables
}
