/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Die.java
 *  Purpose       :  Provides a class describing a single die that can be rolled
 *  @author       :  A. Volsoin
 *  Date          :  2020-02-03
 *  Description   :  This class provides the data fields and methods to describe a single game die.  A
 *                   die can have "N" sides.  Sides are randomly assigned sequential pip values, from 1
 *                   to N, with no repeating numbers.  A "normal" die would thus has six sides, with the
 *                   pip values [spots] ranging in value from one to six.  Includes the following:
 *                   public Die( int nSides );                  // Constructor for a single die with "N" sides
 *                   public int roll();                         // Roll the die and return the result
 *                   public int getValue()                      // get the value of this die
 *                   public void setSides()                     // change the configuration and return the new number of sides
 *                   public String toString()                   // Instance method that returns a String representation
 *                   public static String toString()            // Class-wide method that returns a String representation
 *                   public static void main( String args[] );  // main for testing porpoises
 *
 *  Notes         :  Restrictions: no such thing as a "two-sided die" which would be a coin, actually.
 *                   Also, no such thing as a "three-sided die" which is a physical impossibility without
 *                   having it be a hollow triangular prism shape, presenting an argument as to whether
 *                   the inner faces are faces which then should be numbered.  Just start at four for
 *                   minimum number of faces.  However, be aware that a four-sided die dosn't have a top
 *                   face to provide a value, since it's a tetrahedron [pyramid] so you'll have to figure
 *                   out a way to get the value, since it won't end up on its point.
 *
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the number of sides or pips is out of range
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-06  B.J. Johnson  Initial writing and release
 *  @version 1.1.0  2017-02-17  B.J. Johnson  Filled in method code
 *  @version 2.0.0  2020-02-03  A. Volosin    Update naming conventions and method descriptions.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
public class Die {

  /**
   * private instance data
   */
   private int sides;
   private int pips;      // The value on the face-up side of the Die
   private final int MINIMUM_SIDES = 4;

   // public constructor:
  /**
   * constructor
   * @param nSides int value containing the number of sides to build on THIS Die
   * @throws       IllegalArgumentException
   * Note: parameter must be checked for validity; invalid value must throw "IllegalArgumentException"
   */
   public Die( int nSides ) {
      if (nSides < MINIMUM_SIDES) {
        throw new IllegalArgumentException("Your die needs to have at least 4 sides");
      } else {
        this.sides = nSides;
        this.pips = nSides;
      }
   }

  /**
   * Roll THIS die and return the result
   * @return  integer value of the result of the roll, randomly selected
   */
   public int roll() {
      this.pips = 1 + (int)(Math.random() * this.sides);
      return this.pips;
   }

  /**
   * Get the value of THIS die to return to the caller; note that the way
   *  the count is determined is left as a design decision to the programmer
   *  For example, what about a four-sided die - which face is considered its
   *  "value"?
   * @return the pip count of THIS die instance
   */
   public int getValue() {
      return this.pips;
   }

   public int getSides() {
      return this.sides;
   }

  /**
   * @param  int  the number of sides to set/reset for this Die instance
   * @return      The new number of sides, in case anyone is looking
   * @throws      IllegalArgumentException
   */
   public void setSides( int sides ) {
      this.sides = sides;
   }

  /**
   * Public Instance method that returns a String representation of THIS die instance
   * @return String representation of this Die
   */
   public String toString() {
      return "[" + this.pips + "]";
   }

  /**
   * Class-wide method that returns a String representation of THIS die instance
   * @return String representation of this Die
   */
   public static String toString( Die d ) {
      return d.toString();
   }

  /**
   * A little test main to check things out
   */
   public static void main( String[] args ) {
      Die testDie = null;
      //Checking invalid inputs
      System.out.println("Testing invalid inputs for Die");
      try {
        testDie = new Die(-2);
      } catch ( IllegalArgumentException iae) {System.out.println(" This test works. \n");}
      try {
        testDie = new Die(2);
      } catch ( IllegalArgumentException iae) {System.out.println(" This test works. \n \n");}
      
      //roll method tests
      System.out.println("Testing roll method for Die");
      try {
        testDie = new Die(8);
        System.out.println( " Rolling 8 sided die: " + testDie.roll() + "\n");
      } catch( IllegalArgumentException iae) {System.out.println( "Invalid input");}
      try {
        testDie = new Die(4);
        System.out.println( " Rolling 4 sided die: " + testDie.roll() + "\n \n");
      } catch( IllegalArgumentException iae) {System.out.println( "Invalid input");}

      //getValue method tests
      System.out.println("Testing getValue method for Die");
      try {
        testDie = new Die(4);
        System.out.println( " The value of this dice is: " + testDie.getValue() + "\n");
      } catch( IllegalArgumentException iae) {System.out.println( "Invalid input");}
      try {
        testDie = new Die(8);
        testDie.roll();
        System.out.println( " The value of this dice is: " + testDie.getValue() + "\n \n");
      } catch( IllegalArgumentException iae) {System.out.println( "Invalid input");}

      
      //getSides method tests
      System.out.println("Testing getSides method for Die");
      try {
        testDie = new Die(4);
        System.out.println( " The number of sides on this dice is: " + testDie.getSides() + "\n");      
      } catch( IllegalArgumentException iae) {System.out.println( "Invalid input");}

      try {
        testDie = new Die(20);
        System.out.println( " The value of this dice is: " + testDie.getValue() + "\n \n");
      } catch( IllegalArgumentException iae) {System.out.println( "Invalid input");}


      //setSides method tests
      System.out.println("Testing setSides method for Die");
      try {
        testDie = new Die(4);
        System.out.println( " The number of sides on this dice before setSides is: " + testDie.getSides());
        System.out.println( " Setting sides to 20");
        testDie.setSides(20);
        System.out.println( " The number of sides on this dice after setSides is: " + testDie.getSides() + "\n ");
      } catch( IllegalArgumentException iae) {System.out.println( "Invalid input");}

      try {
        testDie = new Die(20);
        System.out.println( " The number of sides on this dice before setSides is: " + testDie.getSides());
        System.out.println( " Setting sides to 40");
        testDie.setSides(40);
        System.out.println( " The number of sides on this dice after setSides is: " + testDie.getSides() + "\n \n");
      } catch( IllegalArgumentException iae) {System.out.println( "Invalid input");}


      //toString method tests
      System.out.println("Testing toString method for Die");
      try {
        testDie = new Die(10);
        System.out.println(" Testing toString method for 10 side die: \n" + testDie.toString() + "\n");
      } catch( IllegalArgumentException iae) {System.out.println( "Invalid input");}
        
      try {
        testDie = new Die(6);
        System.out.println(" Testing toString method for 6 side die: \n" + testDie.toString());
      } catch( IllegalArgumentException iae) {System.out.println( "Invalid input");}
      System.out.println("\n End of Die tests!");
        


   } //end of main

} //end of class