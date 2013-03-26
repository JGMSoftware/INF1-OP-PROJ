/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edinburgh;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Jamie
 */
public class MainClient extends javax.swing.JFrame {

    //Declare the server socket and communication variables
    private static Socket requestSocket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static MessageObj message;
    //A condition to check whether or not the client is connected before it sends a message when the user presses the button
    public Boolean isConnected;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private static String username = "";
    private static String timestamp;
    private static Calendar time;
    private static MessageObj incoming;

    /**
     * Creates new form GUI
     */
    public MainClient() {
        initComponents();
        chatText.setEditable(false);
        messageTextBox.requestFocus();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        messageTextBox = new javax.swing.JTextPane();
        sendButton = new javax.swing.JButton();
        titleLbl = new javax.swing.JLabel();
        wordCountLbl = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        chatText = new javax.swing.JTextArea();
        wordCountNum = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        exitApp = new javax.swing.JMenuItem();
        getUserApp = new javax.swing.JMenuItem();
        changeNameApp = new javax.swing.JMenuItem();
        appPreferences = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("INF1-OP Project");
        setBackground(new java.awt.Color(255, 255, 255));

        messageTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageTextBoxKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(messageTextBox);

        sendButton.setText("SEND");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        titleLbl.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        titleLbl.setForeground(new java.awt.Color(102, 102, 102));
        titleLbl.setText("Chat Client");

        wordCountLbl.setText("Word Count:");

        chatText.setColumns(20);
        chatText.setRows(5);
        jScrollPane3.setViewportView(chatText);

        wordCountNum.setText("0");

        jMenu1.setText("File");



        exitApp.setText("Exit");
        exitApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitAppActionPerformed(evt);
            }
        });
        jMenu1.add(exitApp);

        changeNameApp.setText("Change username");
        changeNameApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeNameAppActionPerformed(evt);
            }
        });
        jMenu1.add(changeNameApp);
        getUserApp.setText("Who is in?");

        getUserApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getUserAppActionPerformed(evt);
            }
        });
        jMenu1.add(getUserApp);

        jMenuBar1.add(jMenu1);

        appPreferences.setText("Edit");

        jMenuItem2.setText("Preferences");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        appPreferences.add(jMenuItem2);

        jMenuBar1.add(appPreferences);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendButton))
                .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(titleLbl)
                .addGroup(layout.createSequentialGroup()
                .addComponent(wordCountLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wordCountNum)))
                .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(titleLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jScrollPane2)
                .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(wordCountLbl)
                .addComponent(wordCountNum))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
    }// </editor-fold>                        

    //Update the word count every time a new word is entered into the text box
    private void messageTextBoxKeyPressed(java.awt.event.KeyEvent evt) {
        String message = messageTextBox.getText();
        int wordCount = WordCounter.wordCount(message);
        wordCountNum.setText(wordCount + "");
    }

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not yet implemented.");
    }

    private void exitAppActionPerformed(java.awt.event.ActionEvent evt) {
        closeWindow();
    }

    private void getUserAppActionPerformed(java.awt.event.ActionEvent evt) {
        getUsers();
    }

    private void changeNameAppActionPerformed(java.awt.event.ActionEvent evt) {
        String newName;
        newName = JOptionPane.showInputDialog("Choose a new username");
        while (newName.equals("")) {
            newName = JOptionPane.showInputDialog("You must enter a name!");
        }
        username = newName;
        titleLbl.setText("Chat Client" + "  -  " + username);
        changeName(username);
    }

    private void closeWindow() {
        //Send a disconnect message to the server
        MessageObj disconnect = new MessageObj(2, null, null, null);
        sendMessage(disconnect);
        //Exit the app
        System.exit(0);
    }

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
        time = Calendar.getInstance();
        timestamp = timeFormat.format(time.getTime());
        //Create a message from user details and message text
        MessageObj message = new MessageObj(1, messageTextBox.getText(), timestamp, username);
        //Send the message
        sendMessage(message);
        messageTextBox.setText("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainClient().setVisible(true);
            }
        });

        //Prompt for username until at lest one character is entered
        username = JOptionPane.showInputDialog("Enter username:");
        int asked = 1;
        while (username.equals("")) {
            username = JOptionPane.showInputDialog("You must choose a username!");
        }

        //Open a new socket to the server
        connect();
        login();
        listen();

    }

    private void changeName(String username) {
        MessageObj namechanger = new MessageObj(3, null, null, username);
        sendMessage(namechanger);
    }

    private void getUsers() {
        MessageObj nameRequester = new MessageObj(0, null, null, null);
        sendMessage(nameRequester);
    }

    private static void login() {
        MessageObj loginMsg = new MessageObj(4, null, null, username);
        sendMessage(loginMsg);
    }

    private static void listen() throws IOException {

        while (true) {
            try {
                incoming = (MessageObj) in.readObject();
                chatText.append(incoming.makeString() + "\n");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainClient.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    //Method to connect to the server
    public static void connect() {
        try {
            //1. creating a socket to connect to the server
            requestSocket = new Socket("localhost", 2004);
            System.out.println("Connected to localhost in port 2004");
            //2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());

        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            in.close();
            out.close();
            requestSocket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void sendMessage(MessageObj msg) {
        try {
            out.writeObject(msg);
            out.flush();
            //chatText.append(msg.makeString() + "\n");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    // Variables declaration - do not modify                     
    private javax.swing.JMenu appPreferences;
    private static javax.swing.JTextArea chatText;
    private javax.swing.JMenuItem exitApp;
    private javax.swing.JMenuItem getUserApp;
    private javax.swing.JMenuItem changeNameApp;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane messageTextBox;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel titleLbl;
    private javax.swing.JLabel wordCountLbl;
    private javax.swing.JLabel wordCountNum;
    // End of variables declaration                   
}
