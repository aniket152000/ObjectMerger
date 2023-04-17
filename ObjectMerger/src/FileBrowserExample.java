import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;    
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.Font;

public class FileBrowserExample extends JFrame implements ActionListener {

  private JButton fileButton1, fileButton2;
  private JFileChooser fileChooser;
  private String filePath1, filePath2;
  private JButton btnNewButton;

  public FileBrowserExample() {
    super("File Browser Example");

    // Create buttons and file chooser
    fileButton1 = new JButton("Select File 1");
    fileButton1.setFont(new Font("Tahoma", Font.PLAIN, 18));
    fileButton1.setBounds(93, 33, 162, 31);
    fileButton2 = new JButton("Select File 2");
    fileButton2.setFont(new Font("Tahoma", Font.PLAIN, 18));
    fileButton2.setBounds(323, 33, 143, 31);
    fileChooser = new JFileChooser();
    
    // Add action listeners to buttons
    fileButton1.addActionListener(this);
    fileButton2.addActionListener(this);
    
    // Add buttons to panel
    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.add(fileButton1);
    panel.add(fileButton2);
    
    // Add panel to frame                    
    Container contentPane = getContentPane();
    contentPane.add(panel, BorderLayout.CENTER);
    
    btnNewButton = new JButton("submit");
    btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
    btnNewButton.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		merge();
    	}
    });
    btnNewButton.setBounds(232, 124, 143, 31);
    panel.add(btnNewButton);

    // Set size and visibility
    setSize(550, 231);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == fileButton1) {
      int result = fileChooser.showOpenDialog(this);
      if (result == JFileChooser.APPROVE_OPTION) {
        filePath1 = fileChooser.getSelectedFile().getAbsolutePath();
        System.out.println("File 1 selected: " + filePath1);
      }
    } else if (event.getSource() == fileButton2) {
      int result = fileChooser.showOpenDialog(this);
      if (result == JFileChooser.APPROVE_OPTION) {
        filePath2 = fileChooser.getSelectedFile().getAbsolutePath();
        System.out.println("File 2 selected: " + filePath2);
      }
    }
  }
  
  public void merge() {   //merge function
	  try {
          //File filePath1 = new File("file1.xml");
          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          Document doc = dBuilder.parse(filePath1);
          doc.getDocumentElement().normalize();
          
          // Replace the text content of the specified node 
          String oldLine = "Old line content";               
          String newLine = "New line content";
          NodeList nodeList = doc.getElementsByTagName("myNode"); 
          for (int i = 0; i < nodeList.getLength(); i++) {  
              Node node = nodeList.item(i);
              if (node.getNodeType() == Node.ELEMENT_NODE) {
                  Element element = (Element) node;    
                  if (element.getTextContent().equals(oldLine)) {    
                      element.setTextContent(newLine);
                  }
              }
          }
                                                                          
          // Write the updated document to a file
          TransformerFactory transformerFactory = TransformerFactory.newInstance();
          Transformer transformer = transformerFactory.newTransformer();
          DOMSource source = new DOMSource(doc);
          //StreamResult result = new StreamResult(new File("file2.xml"));
          StreamResult result = new StreamResult(filePath2);
          transformer.transform(source, result);
          
          System.out.println("file 2 updated successfully...");  
          JOptionPane.showMessageDialog(this, "done......");
          
      } 
      catch (Exception e){
          e.printStackTrace();
      }
  }

  public static void main(String[] args) {
	  			
    new FileBrowserExample();	
    							
  }

}
