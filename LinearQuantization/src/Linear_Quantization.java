
import javax.swing.JOptionPane;

import java.util.*;
import java.io.*;

public class Linear_Quantization extends javax.swing.JFrame {
    public static List<Integer>arr =new ArrayList();
    double MSE=0;
    public static class Range{
       String code;
        public int low;
       public int high;
        Range(String c,int l,int h){
            code=c;
            low=l;
            high=h;
        }
    }
     public static void Read_from_file(){
      Scanner input=null;
      String w = new String();
      try{
            input=new Scanner(new File("input.txt"));
            while(input.hasNextLine())
                w=input.nextLine(); 
            
            input.close();
            
      }catch(Exception e){
          System.out.println("Error!");
      }
        String []s=w.split(" ");
        for(int i=0;i<s.length;i++)
        arr.add(Integer.parseInt(s[i]));
       
  }

  public void  Quantization(){
      Scanner cin=new Scanner(System.in);
        ArrayList<Range>range=new ArrayList();
        Read_from_file();

      int num_bits=Integer.parseInt(jTextField1.getText());
      int num_of_level=(int) Math.pow(2,num_bits);
      int step=(Collections.max(arr)+Collections.min(arr))/num_of_level;
     int indx=Collections.min(arr)-1,code=0,l=0,h=0;
      while(indx<Collections.max(arr)){// Make range until the max_element.
          l=++indx; indx+=step; h=indx;
          Range r=new Range(Integer.toBinaryString(code),l,h);
          range.add(r);
          code++;
      }
      ////////////////// add code for each number.
      ArrayList code_list=new ArrayList();
      for(int i=0;i<arr.size();i++){
          for(int j=0;j<range.size();j++){
              if(arr.get(i)>=range.get(j).low && arr.get(i)<=range.get(j).high){
                  code_list.add(range.get(j).code);
                  int Q=(range.get(j).low+range.get(j).high)/2;
                  MSE+=Math.pow((arr.get(i)-Q),2);
                  break;
              }
          }
      }
      MSE/=arr.size();
      ///////////////////////////////////////////////////////////////////////////
      //write list of code on file
      Formatter F;
      try{
         
            F=new Formatter("codes.txt");
           for(int i=0;i<code_list.size();i++)
            F.format("%s%s",code_list.get(i)," ");
          F.format("%n");
           for(int i=0;i<range.size();i++)
           F.format("%s%s%s%s%s%n",range.get(i).code," ",range.get(i).low," ",range.get(i).high);
               F.close();
      }catch(Exception e){
          System.out.println("Error!");
      }
      ///////////////////////////////
  }
  public void De_quantization(){
      Scanner input=null;
      String codes = new String(),r="";
       ArrayList<Range>range=new ArrayList();
       //////////////Read from file
      try{
            input=new Scanner(new File("codes.txt"));
            codes=input.nextLine();
            while(input.hasNextLine()){
                r=input.nextLine();
                String []s=r.split(" ");
                String c=s[0];
                int l=Integer.parseInt(s[1]);
                int h=Integer.parseInt(s[2]);
                Range r_list=new Range(c,l,h);
                range.add(r_list);
            }
            
            input.close();
            
      }catch(Exception e){
          System.out.println("Error!");
      }
         
      //////////////////////////////
     ArrayList num=new ArrayList();
     String []c=codes.split(" ");
      for(int i=0;i<c.length;i++){
           for(int j=0;j<range.size();j++){
          if(range.get(j).code.equals(c[i])){
           num.add((range.get(j).low+range.get(j).high)/2);
           break;
          }
        }   
      }
          
      
    //////////////////////////////////////////////
      Formatter F;
      try{
         
            F=new Formatter("De_quantization.txt");
           for(int i=0;i<num.size();i++)
            F.format("%s%s",num.get(i)," ");
          
           F.format("%n%s",MSE);
           F.close();
      }catch(Exception e){
          System.out.println("Error!");
      }
      ////////////////////////////////////////////
    }
    public Linear_Quantization() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Linear Quantization");

        jRadioButton1.setText("Quantization");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("De-quantization");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Num Of Bits:");

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(19, 19, 19))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(jRadioButton2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        jRadioButton1.setSelected(true);
        jRadioButton2.setSelected(false);
        jTextField1.setEnabled(true);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        jRadioButton2.setSelected(true);
        jRadioButton1.setSelected(false);
        jTextField1.setEnabled(false);
    }//GEN-LAST:event_jRadioButton2ActionPerformed
 
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     if(jRadioButton1.isSelected()){
                Quantization();
                JOptionPane.showMessageDialog(null,"   Done!,Please check the Codes.txt");
        }
       
        else if(jRadioButton2.isSelected()){
            De_quantization();
            JOptionPane.showMessageDialog(null,"   Done!,Please check the de_quantization.txt");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    
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
            java.util.logging.Logger.getLogger(Linear_Quantization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Linear_Quantization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Linear_Quantization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Linear_Quantization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Linear_Quantization().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
