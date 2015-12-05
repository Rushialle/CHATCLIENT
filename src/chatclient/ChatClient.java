/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;
import static java.awt.SystemColor.text;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 *
 * @author rushikesh1
 */
public class ChatClient {

    String  name = null;
    BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);
 public ChatClient() {

        // Layout GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();
textField.addActionListener(new ActionListener() {
 public void actionPerformed(ActionEvent e) {
                out.println(name +":"+textField.getText());
                textField.setText("");
            }
        });
    }
 
  private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }

   private String getName() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE);
    }
  private boolean isLeadingDigit(final String value){
    final char c = value.charAt(0);
    return (c >= '0' && c <= '9');
}
 private void run() throws IOException {

        // Make connection and initialize streams
        String serverAddress = getServerAddress();
        Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Process all messages from server, according to the protocol.
        while (true) {
            String line = in.readLine();
           /* Pattern pattern = Pattern.compile("\\s\\s");
            Matcher matcher = pattern.matcher(line); 
            line = matcher.replaceAll(" ");*/
            System.out.println("THE LINE SIZE :" + line.length() + "  " + line);
            //char c = line.charAt(0);
            if(line.length() == 0)
            {
                //skip it baby !!
            }
            else if (line.startsWith("GIMMENAME")) {
                
                out.println(name = getName());
                
            } else if (line.startsWith("ACCEPTED")) {
                System.out.println("In the accpets ");
                textField.setEditable(true);
            } else if (line.startsWith("MESSAGE")) {
                messageArea.append(line.substring(7) + "\n");
            }
            else if ( Character.isDigit(line.charAt(0))  )
            {
                System.out.print("fetching it ??");
                messageArea.append(line.substring(2) + "\n");
            }     
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
    ChatClient client = new ChatClient();
    client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    client.frame.setVisible(true);
    client.run();

    
    }
    
    
}
