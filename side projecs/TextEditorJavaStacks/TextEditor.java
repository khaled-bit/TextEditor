
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Stack;

public class TextEditor {
    public static void main(String[] args) {

        Stack<String> undoStack = new Stack<>();
        Stack<String> redoStack = new Stack<>();

        JFrame frame = new JFrame("Text Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JPanel Panel = new JPanel();
        JButton  RedoButton = new JButton ("Redo");
        JButton  UndoButton = new JButton ("Undo");

        Panel.add(RedoButton);
        Panel.add(UndoButton);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.add(Panel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        undoStack.push("");

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String currentText = textArea.getText();
                if (undoStack.isEmpty() || !undoStack.peek().equals(currentText)) {
                    undoStack.push(currentText);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                undoStack.add(textArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        UndoButton.addActionListener(e->{
            if(!undoStack.isEmpty()){
                redoStack.push(textArea.getText());
                undoStack.pop();
                textArea.setText(undoStack.peek());
            }
        });
        RedoButton.addActionListener(e->{
            if(!redoStack.isEmpty()){
                undoStack.push(textArea.getText());
                textArea.setText((String) redoStack.peek());
            }
        });

    }
}
