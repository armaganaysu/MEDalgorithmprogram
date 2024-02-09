import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class driver extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField wordField1;
    private JTextField wordField2;
    private JTextField wordField;
    private JButton submitButton1;
    private JButton submitButton2;
    private JTable closestWordsTable;
    private JTable resultTable;
    private JTable matrixTable;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;
    private JScrollPane scrollPane3;
    private String[] dictionary;
    private JPanel panel; // Declare panel as a field of the driver class
    private JLabel label_time_partOne;
    private JLabel label_time_partTwo;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JSeparator separator;

    public driver() {
        super("MED Algorithm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);

        // Group 1
        JLabel wordLabel1 = new JLabel("Enter first word:");
        wordLabel1.setBounds(579, 73, 166, 14);
        panel.add(wordLabel1);

        wordField1 = new JTextField(20);
        wordField1.setBounds(579, 98, 166, 20);
        panel.add(wordField1);

        JLabel wordLabel2 = new JLabel("Enter second word:");
        wordLabel2.setBounds(831, 73, 166, 14);
        panel.add(wordLabel2);

        wordField2 = new JTextField(20);
        wordField2.setBounds(831, 98, 166, 20);
        panel.add(wordField2);

        submitButton1 = new JButton("Find MED");
        submitButton1.setBounds(1061, 97, 77, 23);
        submitButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Use invokeLater() to schedule the findMED() method to run on a separate thread
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String word1 = wordField1.getText();
                        String word2 = wordField2.getText();
                        findMED(word1, word2);
                    }
                });
            }
        });
        panel.add(submitButton1);

        resultTable = new JTable();
        resultTable.setModel(new DefaultTableModel());
        resultTable.setDefaultEditor(Object.class, null);
        scrollPane2 = new JScrollPane(resultTable);
        scrollPane2.setBounds(888, 222, 452, 402);
        panel.add(scrollPane2);

        matrixTable = new JTable();
        matrixTable.setModel(new DefaultTableModel());
        matrixTable.setDefaultEditor(Object.class, null);
        scrollPane3 = new JScrollPane(matrixTable);
        scrollPane3.setBounds(416, 222, 452, 402);
        panel.add(scrollPane3);

        // Group 2
        JLabel wordLabel = new JLabel("Enter a word:");
        wordLabel.setBounds(23, 61, 166, 14);
        panel.add(wordLabel);

        wordField = new JTextField(20);
        wordField.setBounds(23, 86, 166, 20);
        panel.add(wordField);

        submitButton2 = new JButton("Find Closest Words");
        submitButton2.setBounds(23, 115, 125, 23);
        submitButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Use invokeLater() to schedule the findClosestWords() method to run on a separate thread
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String word = wordField.getText();
                        findClosestWords(word);
                    }
                });
            }
        });
        panel.add(submitButton2);

        // Other Components
        getContentPane().add(panel);

        

        
        closestWordsTable = new JTable();
        closestWordsTable.setModel(new DefaultTableModel());
        closestWordsTable.setDefaultEditor(Object.class, null);
        scrollPane1 = new JScrollPane(closestWordsTable);
        scrollPane1.setBounds(23, 222, 263, 402);
        panel.add(scrollPane1);
        
        label_time_partOne = new JLabel("Time Elapsed for Part 1: ");
        label_time_partOne.setFont(new Font("Tahoma", Font.BOLD, 11));
        label_time_partOne.setForeground(new Color(255, 0, 0));
        label_time_partOne.setVerticalAlignment(SwingConstants.TOP);
        label_time_partOne.setBounds(23, 162, 341, 33);
        panel.add(label_time_partOne);
        
        label_time_partTwo = new JLabel("Time Elapsed for Part 2: ");
        label_time_partTwo.setFont(new Font("Tahoma", Font.BOLD, 11));
        label_time_partTwo.setVerticalAlignment(SwingConstants.TOP);
        label_time_partTwo.setForeground(new Color(255, 0, 0));
        label_time_partTwo.setBounds(579, 162, 559, 33);
        panel.add(label_time_partTwo);
        
        lblNewLabel = new JLabel("PART 1 : Find Closest 5 Words");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel.setForeground(new Color(255, 0, 0));
        lblNewLabel.setBounds(21, 11, 268, 39);
        panel.add(lblNewLabel);
        
        lblNewLabel_1 = new JLabel("PART 2 : MED OPERATIONS AND TABLE");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel_1.setForeground(new Color(255, 0, 0));
        lblNewLabel_1.setBounds(416, 11, 924, 39);
        panel.add(lblNewLabel_1);
        
        separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setForeground(new Color(192, 192, 192));
        separator.setBounds(350, 73, 2, 570);
        panel.add(separator);

        setVisible(true);

        try {
            dictionary = readDictionary("vocabulary_tr.txt");
            System.out.println("");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading dictionary file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findMED(String word1, String word2) {
    	
    	long runtimeStart_findMED = System.currentTimeMillis();   
        resultTable.setModel(new DefaultTableModel());

        int m = word1.length();
        int n = word2.length();
        int dp[][] = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0)
                    dp[i][j] = j;
                else if (j == 0)
                    dp[i][j] = i;
                else if (word1.charAt(i - 1) == word2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    dp[i][j] = 1 + min(dp[i][j - 1], dp[i - 1][j], dp[i - 1][j - 1]);
            }
        }

        // Create a table model to display the results
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Operation");
        tableModel.addColumn("Word 1");
        tableModel.addColumn("Word 2");
        tableModel.addColumn("MED");

        // Traceback through the DP table to find the operations needed to transform word1 to word2
        int i = m;
        int j = n;
        while (i > 0 && j > 0) {
            if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                // No operation needed
                tableModel.addRow(new Object[] {"No operation", word1.substring(0, i), word2.substring(0, j), dp[i][j]});
                i--;
                j--;
            } else if (dp[i][j] == dp[i - 1][j - 1] + 1) {
                // Substitution
                tableModel.addRow(new Object[] {"Replacement", word1.substring(0, i), word2.substring(0, j), dp[i][j]});
                i--;
                j--;
            } else if (dp[i][j] == dp[i][j - 1] + 1) {
                // Insertion
                tableModel.addRow(new Object[] {"Insertion", word1.substring(0, i), word2.substring(0, j), dp[i][j]});
                j--;
            } else if (dp[i][j] == dp[i - 1][j] + 1) {
                // Deletion
                tableModel.addRow(new Object[] {"Deletion", word1.substring(0, i), word2.substring(0, j), dp[i][j]});
                i--;
            }
        }

        // Add the remaining operations to the table
        while (i > 0) {
            tableModel.addRow(new Object[] {"Deletion", word1.substring(0, i), word2.substring(0, j), dp[i][j]});
            i--;
        }
        while (j > 0) {
            tableModel.addRow(new Object[] {"Insertion", word1.substring(0, i), word2.substring(0, j), dp[i][j]});
            j--;
        }

        // Set the table model
        resultTable.setModel(tableModel);

        // Create a model to display the MED distance matrix
        DefaultTableModel matrixModel = new DefaultTableModel();

        // Add column headers to the model
        matrixModel.addColumn(" ");
        for (int k = 0; k <= word2.length(); k++) {
            matrixModel.addColumn(String.valueOf(k));
        }

        // Add rows to the model
        for (int k = 0; k <= word1.length(); k++) {
            matrixModel.addRow(new Object[word2.length() + 1]);
            matrixModel.setValueAt(k, k, 0);
            for (int l = 0; l <= word2.length(); l++) {
                matrixModel.setValueAt(dp[k][l], k, l + 1);
            }
        }
        double runtime_findMED_InSeconds = (System.currentTimeMillis() - runtimeStart_findMED);
        label_time_partTwo.setText("Time Elapsed for Part 2:   " + runtime_findMED_InSeconds + " miliseconds");
        // Set the table model
        matrixTable.setModel(matrixModel);
        
        
    }

    private void findClosestWords(String word) {
    	long runtimeStart_ClosestWords = System.currentTimeMillis();
        // Create an array to store the top 5 closest words
        String[] closestWords = new String[5];

        // Create a map to store the distances of each word in the dictionary from the given word
        Map<String, Integer> distances = new HashMap<>();

        for (String dictWord : dictionary) {
            int distance = med(word, dictWord);
            distances.put(dictWord, distance);
        }

        // Convert the distances map to an array of Map.Entry objects
        Map.Entry<String, Integer>[] sortedDistances = distances.entrySet().toArray(new Map.Entry[distances.size()]);

        // Sort the array by distance in ascending order
        Arrays.sort(sortedDistances, Map.Entry.comparingByValue());

        // Keep only the top 5 closest words
        if (sortedDistances.length > 5) {
            sortedDistances = Arrays.copyOfRange(sortedDistances, 0, 5);
        }

        // Extract the words from the sorted array and store them in the array
        for (int i = 0; i < sortedDistances.length; i++) {
            closestWords[i] = sortedDistances[i].getKey();
        }

        // Create a table model to display the results
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Rank");
        tableModel.addColumn("Word");
        tableModel.addColumn("Distance");

        // Add the closest words to the table model
        for (int i = 0; i < closestWords.length; i++) {
            tableModel.addRow(new Object[] {i + 1, closestWords[i], sortedDistances[i].getValue()});
        }
        double runtime_ClosestWords_InSeconds = (System.currentTimeMillis() - runtimeStart_ClosestWords);
        label_time_partOne.setText("Time Elapsed for Part 1:   " + runtime_ClosestWords_InSeconds + " miliseconds");
        
        // Set the table model
        closestWordsTable.setModel(tableModel);
    }

    private int med(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int dp[][] = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0)
                    dp[i][j] = j;
                else if (j == 0)
                    dp[i][j] = i;
                else if (str1.charAt(i - 1) == str2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    dp[i][j] = 1 + min(dp[i][j - 1], dp[i - 1][j], dp[i - 1][j - 1]);
            }
        }
        return dp[m][n];
    }

    private int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    private String[] readDictionary(String filename) throws IOException {
	   InputStream inputStream = driver.class.getResourceAsStream("/resources/" + filename);
	   BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-9"));
       
        ArrayList<String> words = new ArrayList<>();

        try {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } finally {
            br.close();
        }

        return words.toArray(new String[0]);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new driver();
            }
        });
    }
}