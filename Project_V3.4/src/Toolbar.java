import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;


public class Toolbar extends JPanel {
	
	private JButton resetButton;
	public static JComboBox functionSelection; 
	public static JComboBox functionSelection2; 
	public static JComboBox operationSelection; 
	private JButton plusButton = new JButton("+");
	private JButton minusButton = new JButton("-");
	private JButton colorButton = new JButton("Colour");
	
	public Toolbar(ActionListener a, ActionListener scaleListener) {
		resetButton = new JButton("Reset");
		String[] list = {"z","z_sq","1/z","Exp z", "Sin z","Cos z","Tan z", "Log z"};
		String[] list2 = {"None","+", "-","*","/"};
		functionSelection = new JComboBox(list);
		functionSelection.addActionListener(a);
		operationSelection = new JComboBox(list2);
		operationSelection.addActionListener(a);
		functionSelection2 = new JComboBox(list);
		functionSelection2.addActionListener(a);
		
		plusButton.addActionListener(scaleListener);
		minusButton.addActionListener(scaleListener);
		colorButton.addActionListener(scaleListener);
		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(functionSelection);
		add(operationSelection);

		add(functionSelection2);

		add(resetButton);
		add(plusButton);
		add(minusButton);
		add(colorButton);
			}
	

}
