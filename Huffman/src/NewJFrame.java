import java.io.File;
import java.util.*;
import javax.swing.JOptionPane;


/**
 *Name: Eslam Alaa Eldin Ibrahim.
 * ID: 20110022
 * Group: 3Cs_G1.
 *
 */
public class NewJFrame extends javax.swing.JFrame {

    public NewJFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jRadioButton1.setText("compression");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("Decompression");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Huffman Code");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton2)
                .addGap(28, 28, 28))
            .addGroup(layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(86, 86, 86)
                .addComponent(jButton1)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

     public  List <Character> contact_c  =new ArrayList();
    public  List <String> contact_d  =new ArrayList();
     public  String compress = new String();
    
     public  class Node{
      public char c;
      public double prob;
      public Node right;
      public Node left;
      Node(char c,double prob){
      this.c=c;
      this.prob=prob;
      }
    }
  
    public  class Tree {
        public Node root;
        // adding for compresssion.
        public void add(char c1,double p1,char c2,double p2){
            if (root ==null){
                root=new Node(' ',p1+p2);
                root.left=new Node(c1,p1);
                root.right=new Node(c2,p2); 
            }
            else{
               if(p1 == root.prob){ 
               Node l=root;
                   root=new Node(' ',p1+p2);
               root.right=new Node(c2,p2);
               root.left=l;
               
               }
               else if(p2==root.prob){
                    Node l=root;
               root=new Node(' ',p1+p2);
               root.right=new Node(c1,p1);
               root.left=l;
               
                     }
               else{
                   Node newer;
                 newer=new Node(' ',p1+p2);
                newer.left=new Node(c1,p1);
                newer.right=new Node(c2,p2); 
             
               Node l=root;
               root=new Node(' ',newer.prob+root.prob);
               root.right=newer;
               root.left=l;
               
               }
            } 
        }
        public void print(Node r,String d){
            if (r!=null){
            print(r.right,d+"1");
            if(r.c!=' '){
            //System.out.println(r.c+"  "+d);
            contact_c.add(r.c);
            contact_d.add(d);
            }
            print(r.left,d+"0");
            }
        }
    }
  public   void Sort( List <Character>arr_c,List<Double> arr_p){
    
    //Sorting by prob.
         for(int i=0;i<arr_p.size();i++){
             for(int j=i+1;j<arr_p.size();j++){
             if(arr_p.get(i)>=arr_p.get(j)){
                 Collections.swap(arr_p, i, j);
                 Collections.swap(arr_c, i, j);
             } 
                 }
         }
     
    }
  
  public  void Write_on_file(){
      Formatter F=null;
      try{
            F=new Formatter("codes.txt");
   
            F.format("%s%n",compress);
            for(int i=0;i<contact_c.size();i++)
                F.format("%s%s%s%n",contact_c.get(i)," ",contact_d.get(i));
            F.close();
      }catch(Exception e){
          System.out.println("Error!");
      }
      
  }
  
  public static String Read_from_file(){
      Scanner input=null;
      String word = new String();
      try{
            input=new Scanner(new File("input.txt"));
        
            while(input.hasNext())
 
                word=input.next(); 
            
            input.close();
            
      }catch(Exception e){
          System.out.println("Error!");
      }
    
        
        return word;
  }
     public  void Compression(){
            Tree t=new Tree();
        String word=Read_from_file();
      List <Character> arr_c  =new ArrayList();
      List <Double> arr_p  =new ArrayList();
        //get the occerance of char in string.
      for(int i=0;i<word.length();i++){
          double count=0;
          if(arr_c.isEmpty()||!arr_c.contains(word.charAt(i))){
              for(int j=i;j<word.length();j++){
               if(word.charAt(i)==word.charAt(j))
                  count++;
              }
             arr_c.add(word.charAt(i));
             arr_p.add(count/word.length());
          }
        }
         Sort(arr_c,arr_p);
      
         while(arr_p.size()>1){
             t.add(arr_c.get(0),arr_p.get(0), arr_c.get(1),arr_p.get(1));
             arr_c.add(' ');
            arr_p.add(arr_p.get(0)+arr_p.get(1));
            arr_c.remove(arr_c.get(0));arr_c.remove(arr_c.get(0));arr_p.remove(arr_p.get(0));arr_p.remove(arr_p.get(0));
            Sort(arr_c,arr_p);
         }
         
       t.print(t.root,"");     
        //compress word
         for(int i=0;i<word.length();i++){
          int indx=contact_c.indexOf(word.charAt(i));
          compress+=contact_d.get(indx);
         }             
       Write_on_file();
     }
    public  void Decompression(){
     
                //Read from file codes.txt.
      String digits = new String();
      try{
          Scanner input=new Scanner(new File("codes.txt"));
               digits=input.nextLine();
               input.useDelimiter(" ");
            while(input.hasNext()){
             String x= input.next();
                contact_c.add(0,x.charAt(0));
                contact_d.add(0,input.nextLine());
                contact_d.set(0,contact_d.get(0).substring(1));
            }
            input.close();
            
      }catch(Exception e){
          System.out.println("Error!");
      }
     
     
         String d ="",word="";
      //Looping to get char (the original massage).
      for(int i=0;i<digits.length();i++){
          d+=digits.charAt(i);
         
           if(contact_d.contains(d)){
              for(int j=0;j<contact_d.size();j++){
                  if(contact_d.get(j).equals(d)){
                      
                      word+=contact_c.get(j);  
                  break;
                  }
              }
              d="";
          }
      }
         
       System.out.println(word);  
          
      //write on file decomposition
      Formatter F=null;
      try{
            F=new Formatter("decompressed.txt.txt");
  
            F.format("%s",word);
            F.close();
      }catch(Exception e){
          System.out.println("Error!");
      }
  
    } 

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
               jRadioButton1.setSelected(true);
        jRadioButton2.setSelected(false);

    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
                jRadioButton2.setSelected(true);
                jRadioButton1.setSelected(false);

    }//GEN-LAST:event_jRadioButton2ActionPerformed

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

    
    public static void main(String args[]) {
    
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new NewJFrame().setVisible(true);
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
