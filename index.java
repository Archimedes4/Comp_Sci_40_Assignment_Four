/*
  Andrew Mainella
  Ms Latimer
  27 November 2023

  Assignment Four: Financial Calculator with methods
  Level: Gold
*/

import javax.swing.*;
import java.awt.event.*;

//Position class that holds position data for when rendering field.
class Position {
  public int xPos;
  public int yPos;
  public int width;
  public int height;
  public Position(int xPosIn, int yPosIn, int widthIn, int heightIn) {
    xPos = xPosIn;
    yPos = yPosIn;
    width = widthIn;
    height = heightIn;
  }
}

class Index {
  public static boolean isInvestment = true;
  public static boolean isRendered = false;
  //Main Field holds a label and a field. Returns the field 
  static JTextField mainField(JFrame frame, String label, String text, Position labelPos, Position textPos) {
    JLabel mainLabel = new JLabel(label);
    mainLabel.setBounds(labelPos.xPos, labelPos.yPos, labelPos.width, labelPos.height);
    frame.add(mainLabel);
    JTextField mainField = new JTextField(text, 16);
    mainField.setBounds(textPos.xPos, textPos.yPos, textPos.width, textPos.height);
    frame.add(mainField);
    return mainField;
  }
  //Checking that number from textfield is valid
  static Double verifyNumber(String number) throws Exception {
    try {
      Double numberOut = Double.parseDouble(number);
      return numberOut;
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Please enter a valid number!", "Error!", JOptionPane.ERROR_MESSAGE);
      throw new Exception();
    }
  }
  //Calculate savings
  static Double calculateSavings(String pString, String rString, String nString) throws Exception {
    try {
      Double p = verifyNumber(pString);
      Double r = verifyNumber(rString);
      Double n = verifyNumber(nString);
      //Save Money for retierment
      /*Calculation for payment amount per period or a
              ( r(1 + r)^n    )
        A = P (---------------)
              ( (1 + r)^n - 1 )
      */    
      System.out.println(p + " " + r + " " + n);
      Double ratePowPayment = Math.pow((1 + r), n);
      System.out.print("Rpow:" + ratePowPayment);
      Double aTop = r * ratePowPayment;
      System.out.print("top:" + aTop);
      Double aBottom = ratePowPayment - 1;
      Double a = (aTop/aBottom) * p;
      System.out.print("a:" + a);
      Double aResult = (Math.round(a * 100.0)/100.0);
      return aResult;
    } catch (Exception e) {
      throw new Exception();
    }
  }
  //Main savings function hold logic and the ui
  static void savingsCalculator() {
    JFrame frame = new JFrame();
    frame.setSize(300, 550);
    //Button to investments
    JButton savingsButton = new JButton("Go To Investments");
    savingsButton.addActionListener(new ActionListener(){  
      //On Press
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        //Refresh content
        frame.setVisible(false);
        isInvestment = true;
        isRendered = false;
      }  
    });
    savingsButton.setBounds(0, 0, 300, 100);
    frame.add(savingsButton);
    //Main fields
    JTextField principleField = mainField(frame, "Principle Amount", "principle", new Position(0, 100, 300, 50), new Position(0, 150, 300, 50));
    JTextField rateField = mainField(frame, "Interest Rate", "rate", new Position(0, 200, 300, 50), new Position(0, 250, 300, 50));
    JTextField numberOfPaymentsField = mainField(frame, "Number of Payments", "# of payments", new Position(0, 300, 300, 50), new Position(0, 350, 300, 50));

    JLabel resultLabel = new JLabel("Result: Please input a number");
    resultLabel.setBounds(0, 400, 300, 50);
    frame.add(resultLabel);

    //Main calculate button
    JButton calculateButton = new JButton("Calculate");
    calculateButton.addActionListener(new ActionListener(){  
      public void actionPerformed(ActionEvent e){  
        try {
          Double result = calculateSavings(principleField.getText(), rateField.getText(), numberOfPaymentsField.getText());
          resultLabel.setText("Result: $" + result);
        } catch (Exception ex) {
          //Do nothing already handeled
        }
      }  
    });
    calculateButton.setBounds(0, 450, 300, 50);
    frame.add(calculateButton);

    //rendering frame
    JPanel container = new JPanel();
    frame.add(container);
    frame.setVisible(true);
  }
  //Calculates interest
  static Double calculateInterest(String iString, String nString, String yString, String FVString) throws Exception {
    Double i = verifyNumber(iString);
    Double n = verifyNumber(nString);
    Double y = verifyNumber(yString);
    Double FV = verifyNumber(FVString);
    /*
                (     1     ) ^ n * y
      PV = FV X ( --------- ) 
                ( 1 + (i/n) )

      PV = present value of the amount
      FV = future value of the amount
      i = interest rate (in percentage terms)
      n = Number of compounding periods per year
      y = Number of years
    */

    Double PV = FV * (1.0/Math.pow((1 + (i/n)), (n * y)));

    Double PVRounded = Math.round(PV*100.0)/100.0;
    return PVRounded;
  }
  //Main investment function hold logic and the ui
  static void investmentCalculator() {
    //Interest Calculator Method
    JFrame frame = new JFrame();
    frame.setSize(300, 650);
    //Savings
    JButton investmentButton = new JButton("Go to Savings");
    investmentButton.addActionListener(new ActionListener(){  
      //On Press
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        //Reseting frame
        frame.setVisible(false);
        isInvestment = false;
        isRendered = false;
      }  
    });
    investmentButton.setBounds(0, 0, 300, 100);
    frame.add(investmentButton);
    //Loading conetnet
    JTextField interestField = mainField(frame, "Interest", "Interest in %", new Position(0, 100, 300, 50), new Position(0, 150, 300, 50));
    JTextField numberOfPaymentsField = mainField(frame, "Number of Payments", "# of payments", new Position(0, 200, 300, 50), new Position(0, 250, 300, 50));
    JTextField yearsField = mainField(frame, "Number of Years", "# of years", new Position(0, 300, 300, 50), new Position(0, 350, 300, 50));
    JTextField futureValueField = mainField(frame, "Future Value Amount", "Future Value", new Position(0, 400, 300, 50), new Position(0, 450, 300, 50));    

    //result label
    JLabel resultLabel = new JLabel("Result: Please input a number");
    resultLabel.setBounds(0, 500, 300, 50);
    frame.add(resultLabel);

    //calculate calls main function
    JButton calculateButton = new JButton("Calculate");
    calculateButton.addActionListener(new ActionListener(){  
      public void actionPerformed(ActionEvent e){  
        try {
          Double result = calculateInterest(interestField.getText(), numberOfPaymentsField.getText(), yearsField.getText(), futureValueField.getText());
          resultLabel.setText("Result: $" + result);
        } catch (Exception ex) {
          //Do nothing already handeled
        }
      }  
    });
    calculateButton.setBounds(0, 550, 300, 50);
    frame.add(calculateButton);
    //Render main function
    JPanel container = new JPanel();
    frame.add(container);
    frame.setVisible(true);
  }
  //Main Method holding the options logic
  public static void main(String[] args) {
    //Choose program option holds code
    //Reason for loop and rendered state is to stop unnessesary rerenders.
    while (true) {
      if (!isRendered) {
        if (isInvestment) {
          investmentCalculator();
        } else {
          savingsCalculator();
        }
        isRendered = true;
      }
    }
  }
}