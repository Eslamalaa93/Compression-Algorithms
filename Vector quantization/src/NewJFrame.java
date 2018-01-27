
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eslam
 */
public class NewJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
    }
     int arr[][];
     ArrayList<ArrayList<Integer>> Vector = new ArrayList();
     ArrayList<ArrayList<Integer>> avg = new ArrayList< ArrayList< Integer>>();
     ArrayList<String> bit_level = new ArrayList<String>();
     int level=0;
     int v_r=0,v_c=0; 
     int[][] readimage(String filename) throws IOException {
        File file = new File(filename);
        BufferedImage image = ImageIO.read(file);
        int pixel;
        int[][] img = new int[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                pixel = image.getRGB(j, i);
                pixel = (pixel >> 16) & 0xff;
                img[i][j] = pixel;
            }
        }
        return img;
    }

     void writeImg(int[][] list) {
        int imageHeight = list.length;
        int imageWidth = list[0].length;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                int pixel = list[i][j];
                int RGB = ((pixel << 16) & 0x00FF0000) | ((pixel << 8) & 0x0000FF00) | (pixel & 0x000000FF);

                image.setRGB(j, i, RGB);
            }
        }
        try {
            ImageIO.write(image, "bmp", new File("image.jpg"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Vector.clear();
        avg.clear();
        bit_level.clear();
        level=v_r=v_c=0;
    }

     void compress() {

        int c = 0;
        while (c < (level/2)) {
            ArrayList<ArrayList<Integer>> sum_range = new ArrayList(avg.size());//sum of each range
            ArrayList<Integer> counter = new ArrayList<Integer>(avg.size());//count num of elemnt in each range
            //////////////////////////////////////////////////////////// 
            for (int i = 0; i < avg.size(); i++) {
                ArrayList<Integer> initialize = new ArrayList();
                for (int j = 0; j < Vector.get(0).size(); j++) {
                    initialize.add(0);
                }

                sum_range.add(initialize);
                counter.add(0);
            }
            /////////////////////////////////////////////
            for (int i = 0; i < Vector.size(); i++) {
                int square_error = (int) 1e8, pos = 0;
                for (int j = 0; j < avg.size(); j++) {
                    int MSE = 0;
                    for (int k = 0; k < avg.get(j).size(); k++) {
                        MSE += Math.pow(avg.get(j).get(k) - Vector.get(i).get(k), 2);
                    }
                    if (square_error > MSE) {
                        square_error = MSE;
                        pos = j;
                    }
                }

                //       System.out.println(pos+"  "+Vector.get(i));
                /////////////////////////////////////////////////////////////
                ArrayList av = new ArrayList(Vector.get(0).size());
                for (int r = 0; r < sum_range.get(pos).size(); r++) {
                    av.add(sum_range.get(pos).get(r) + Vector.get(i).get(r));
                }
                sum_range.set(pos, av);

                counter.set(pos, counter.get(pos) + 1);
            }
            ///////////////////////////////////////////////////////////
            avg.clear();

            for (int i = 0; i < sum_range.size(); i++) {
                ArrayList<Integer> av = new ArrayList<Integer>();
                for (int j = 0; j < sum_range.get(i).size(); j++) {
                   
                    av.add(sum_range.get(i).get(j) / counter.get(i));
                       
                }
                avg.add(av);
            }
            //////////////////////////////////////////////////
           if(c+1<(level/2)){
                for (int i = 0; i < avg.size(); i += 2) {
                    ArrayList<Integer> s1 = new ArrayList<Integer>();
                    ArrayList<Integer> s2 = new ArrayList<Integer>();

                    for (int j = 0; j < avg.get(i).size(); j++) {
                        s1.add(avg.get(i).get(j) - 1);
                    }

                    for (int j = 0; j < avg.get(i).size(); j++) {
                        s2.add(avg.get(i).get(j) + 1);
                    }
                    avg.remove(i);
                    avg.add(i, s1);
                    avg.add(i + 1, s2);
                }
                /////////////////////////////////////////////////
           }
            c++;
            /*for (int i = 0; i < avg.size(); i++) {
                System.out.print(avg.get(i) + "   ");
            }
            System.out.println();*/
        }
    }

     void set_Range() {
        ArrayList<Integer> s = new ArrayList<Integer>();
        ArrayList<Integer> s1 = new ArrayList<Integer>();
        ArrayList<Integer> s2 = new ArrayList<Integer>();
        for (int i = 0; i < Vector.get(0).size(); i++) {
            int av = 0;
            for (int j = 0; j < Vector.size(); j++) {
                av += Vector.get(j).get(i);
            }
            s.add(av / Vector.size());
        }

        for (int i = 0; i < s.size(); i++) {
            s1.add(s.get(i) - 1);
        }

        avg.add(s1);
        for (int i = 0; i < s.size(); i++) {
            s2.add(s.get(i) + 1);
        }

        avg.add(s2);

    }

     void bits(String bit, int level) {
        if (level == 0) {
            bit_level.add(bit);
        } else {
            bits(bit + "0", level - 1);
            bits(bit + "1", level - 1);
        }
    }

     void write_to_file() {
        Formatter F;
        try {
            String code = "";
            F = new Formatter("codes.txt");
            ArrayList<Integer> y = new ArrayList<Integer>();
            y.add(0);
            avg.add(0, y);
            ArrayList<Integer> y2 = new ArrayList<Integer>();
            y2.add(10000);
            avg.add(y2);

            bit_level.add(0,bit_level.get(0));

            for (int i = 0; i < Vector.size(); i++) {
                for (int j = 0; j < avg.size() - 1; j++) {
                    if ((Vector.get(i).get(0) >= avg.get(j).get(0)) && (Vector.get(i).get(0) < avg.get(j + 1).get(0))) {
                        code += bit_level.get(j).toString() + "  ";
                        break;
                    }
                }
            }
            F.format("%s%n", code);
            F.close();
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

     void read() {
        Scanner input = null;
        String w = "";
        try {
            input = new Scanner(new File("codes.txt"));
            while (input.hasNextLine()) {
                w += input.nextLine();
            }
            bit_level.remove(0);
            avg.remove(0);
            String ar[] = w.split("  ");
            int list2[][]= new int [arr.length][arr[0].length];
            System.out.println(ar.length);
            int[][] list = new int[ar.length][avg.get(0).size()];
            for (int i = 0; i < ar.length; i++) {
                for (int j = 0; j < bit_level.size(); j++) {
                    if (bit_level.get(j).equals(ar[i])) {
                        for (int k = 0; k < avg.get(0).size(); k++) {
                            list[i][k] = avg.get(j).get(k);
                        }
                        break;
                    }
                }
            }
          int  s1=0,s2=0;
            for (int i = 0; i < arr.length; i += v_r) {
            for (int j = 0; j < arr[i].length; j += v_c) {
                
                for (int k = i,r=0; r < v_r && k < arr.length; r++, k++) {
                    for (int l = j,c=0; c < v_c && l < arr[k].length; c++, l++) {
                        list2[k][l]=list[s1][s2];
                    s2++;
                    }
                }
                s1++;s2=0;
            }
        }
            
            
            
            
            
            //System.out.println(list.length);
            writeImg(list2);
            input.close();
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Vector size");

        jLabel2.setText("num of level");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jRadioButton1.setText("Compress");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("De-compress");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(51, 51, 51)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                    .addComponent(jTextField1)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jRadioButton2))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(65, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(16, 16, 16)
                .addComponent(jButton1)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            try {
                arr = readimage("google pic.jpg");
            } catch (IOException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            String vector_size=jTextField1.getText();
            System.out.print(vector_size);
            String v[]=vector_size.split("x");
     v_r=Integer.parseInt(v[0]);v_c =Integer.parseInt(v[1]);
        level=Integer.parseInt(jTextField2.getText());
        /////////////////////////////////// Generate Vectors.
        for (int i = 0; i < arr.length; i += v_r) {
            for (int j = 0; j < arr[i].length; j += v_c) {
                ArrayList y = new ArrayList();
                for (int k = i, r = 0; r < v_r && k < arr.length; r++, k++) {
                    for (int l = j, c = 0; c < v_c && l < arr[k].length; c++, l++) {
                        y.add(arr[k][l]);
                    }
                }
                Vector.add(y);
            }
        }

        /////////////////////////////////// padding zero for lower size.
        for (int i = 0; i < Vector.size(); i++) {
            if (Vector.get(i).size() < v_r * v_c) {
                for (int j = 0; j <= v_r * v_c - Vector.get(i).size(); j++) {
                    Vector.get(i).add(0);
                }
            }
        }
        ///////////////////////////////////////////

        set_Range();
        compress();
        bits("", (level/2));
        write_to_file();
                JOptionPane.showMessageDialog(null,"   Done!,Please check the Codes.txt");
        }
       
        else if(jRadioButton2.isSelected()){
            read();
            JOptionPane.showMessageDialog(null,"   Done!,Please check the image");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
