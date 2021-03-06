/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  BrobInt.java
 * Purpose    :  Learning exercise to implement arbitrarily large numbers and their operations
 * @author    :  A. Volosin
 * Date       :  2019-03-01
 * Description:  @see <a href='http://volosin.lmu.build/alissa-volosin/cmsi-186-programming-lab/cmsi-186-assignments/assignment-06/'>Assignment 06</a>
 * Notes      :  None
 * Warnings   :  None
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Revision History
 * ================
 *   Ver      Date     Modified by:  Reason for change or modification
 *  -----  ----------  ------------  ---------------------------------------------------------------------
 *  1.0.0  2019-03-01  A. Volosin    Initial writing and begin coding
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Arrays;
import java.io.IOException;
import java.io.*;

public class BrobInt {

   /** constant for "zero" */
   public static final BrobInt ZERO     = new BrobInt(  "0" ); 
   /** constant for "one" */
   public static final BrobInt ONE      = new BrobInt(  "1" );
   /** Constant for "two" */
   public static final BrobInt TWO      = new BrobInt(  "2" );   
   /** Constant for "three" */  
   public static final BrobInt THREE    = new BrobInt(  "3" );
   /** Constant for "four" */
   public static final BrobInt FOUR     = new BrobInt(  "4" );
   /** Constant for "five" */
   public static final BrobInt FIVE     = new BrobInt(  "5" );
   /** Constant for "six" */
   public static final BrobInt SIX      = new BrobInt(  "6" );
   /** Constant for "seven" */
   public static final BrobInt SEVEN    = new BrobInt(  "7" );
   /** Constant for "eight" */
   public static final BrobInt EIGHT    = new BrobInt(  "8" );
   /** Constant for "nine" */
   public static final BrobInt NINE     = new BrobInt(  "9" );
   /** Constant for "ten" */
   public static final BrobInt TEN      = new BrobInt( "10" );
   
  /// These are the internal fields
  /** internal String representation of this BrobInt */
   public  String internalValue = "";        // internal String representation of this BrobInt
   /** "0" is positive, "1" is negative */
   public  byte   sign          = 0;         // "0" is positive, "1" is negative
   /** the backwards version of the internal String representation
 */
   private String reversed      = "";           
   /** byte representation of this BrobInt */
   public  byte[] byteVersion;               // byte representation of this BrobInt

   private static final boolean DEBUG_ON = false;
   private static final boolean INFO_ON  = false;

  /**
   *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
   *   and handles that accordingly;  it then checks to see if it's all valid digits, and reverses it
   *   for later use. The contstructor also assigns the reversed value to a byte array for later use.
   *  @param  value  String value to make into a BrobInt
   */
   public BrobInt( String value ) {

         if ( value.charAt(0) == '-' ) {
            this.sign = 1;
            this.internalValue = value.substring(1);
         } else if ( value.charAt(0) == '+' ) {
            this.sign = 0;
            this.reversed = value.substring(1);
         } else {
            this.internalValue = value;
         }
      this.validateDigits(internalValue);
      this.reversed = new String(new StringBuffer(internalValue).reverse());
   
      byteVersion = new byte[this.reversed.length()];
      for ( int i = 0; i < this.reversed.length(); i++ ) {
         byteVersion[i] = (byte)Character.getNumericValue(reversed.charAt(i));
      }
   }

  
   /**  
   *  Method to validate that all the characters in the value are valid decimal digits
   *  @return  boolean  true if all digits are good
   *  @throws  IllegalArgumentException if something is hinky
   *  note that there is no return false, because of throwing the exception
   *  note also that this must check for the '+' and '-' sign digits
   */
   public boolean validateDigits(String args) {
      int i;
      for (i = 0; i < args.length(); i++ ) {
         try {
            Integer.parseInt(String.valueOf(args.charAt(i)));
         } catch (Exception e) {
            throw new IllegalArgumentException("\n    Something is wrong with the input." );
         }
      }
      return true;
   }


  /**
   *  Method to add the value of a BrobInt passed as argument to this BrobInt
   *  @param  bint         BrobInt to add to this
   *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
   */
   public BrobInt add( BrobInt bint ) {
      int i = 0;            // variable to keep indexes
      int j = 0;            // variable to keep indexes
      byte carry = 0;       // indicates a carry
      byte[] longBrob;      // byte array for long Brobint
      byte[] shortBrob;     // byte array for short Brobint
      StringBuffer newString = new StringBuffer(); // string version of new Brobint
      boolean unequalSigns = false;    // indicates equal/unequal signs

      // checking which number is longer
      if ( this.compareTo(bint) >= 0) {
         longBrob = this.byteVersion;
         shortBrob = bint.byteVersion;
      }

      // assign Brobints to 'long' or 'short' based on length
      if (bint.reversed.length() > this.reversed.length()) {
         longBrob = new byte[reversed.length()];
         longBrob = bint.byteVersion;
         shortBrob = new byte[this.reversed.length()];
         shortBrob = this.byteVersion;
      } else if (this.reversed.length() > bint.reversed.length() ) {
         longBrob = new byte[this.reversed.length()];
         longBrob = this.byteVersion;
         shortBrob = new byte[bint.reversed.length()];
         shortBrob = bint.byteVersion;
      } else {
         longBrob = new byte[reversed.length()];
         longBrob = bint.byteVersion;
         shortBrob = new byte[this.reversed.length()];
         shortBrob = this.byteVersion;
      }

      // new byte array for BrobInt that will be returned
      byte[] newBrob = new byte[Math.max(longBrob.length,shortBrob.length) + 2];
      
      // addition while in the length of shortBrob
      if (this.sign == bint.sign ) {
         while ( i < shortBrob.length) {
            newBrob[i] = (byte)(shortBrob[i] + longBrob[i] + carry);
            if ( newBrob[i] > 9 ) {
               newBrob[i] = (byte)(newBrob[i] - 10);
               carry = 1;
            } else {
               carry = 0;
            }
            i++;
         }

      // addition while in the length of longBrob
      while ( i < longBrob.length ) {
         newBrob[i] += longBrob[i] + carry;
         if ( newBrob[i] > 9 ) {
            newBrob[i] = (byte) (newBrob[i] - 10);
            carry = 1;
         } else {
            carry = 0;
         } 
         i++;
      }
      if (carry == 1) {
         newBrob[i] += 1;
      }

      // check if each BrobInt is positive/negative of each other, returns BrobInt = 0
   } else if ( this.sign != bint.sign ) {
      if ( this.internalValue == bint.internalValue ) {
         return new BrobInt("0");
      }
      unequalSigns = true;

   // addition for numbers with different signs
      while ( i < shortBrob.length ) {
         newBrob[i] = (byte)( longBrob[i] - shortBrob[i] - carry );
         if (newBrob[i] < 0) {
            newBrob[i] += 10;
            carry = 1;
         } else {
            carry = 0;
         }
         i++;
      }

      while ( i < longBrob.length ) {
         newBrob[i] = (byte)(longBrob[i] - carry);
         if ( newBrob[i] < 0 ) {
            newBrob[i] += 10;
            carry = 1;
         } else {
            carry = 0;
         }
         i++;
      }
   }

   // new String result
   for ( j = 0; j < newBrob.length; j++ ) {
      newString.insert( 0 , String.valueOf(newBrob[j]) );
   }

   // constructing new BrobInt from String obtained
      BrobInt addedBrob = new BrobInt( (newString.toString()) );
      if ( this.sign == bint.sign ) {
         addedBrob.sign = this.sign;

      } else if (( this.compareTo(bint) > 0) && ( unequalSigns )) {
         addedBrob.sign = this.sign;
      } else if (( this.compareTo(bint) < 0) && ( unequalSigns )) {
         addedBrob.sign = bint.sign;
      }
      addedBrob = removeLeadingZeros(addedBrob);
      return addedBrob;
   }

  /** 
   *  Method to subtract the value of a BrobInt passed as argument to this BrobInt
   *  @param  bint         BrobInt to subtract from this
   *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
   */
   public BrobInt subtract( BrobInt bint ) {
      if ( this.equals(bint)) { 
         return new BrobInt( "0" );
      }
      
      // checking signs so we can use add instead of designing new function
      if ( ( this.sign != bint.sign ) && (bint.sign == 0 )) {
         bint.sign = 1;
         return this.add(bint);
      } else if ((this.sign != bint.sign) && (bint.sign == 1)) {
         bint.sign = 0;
         return this.add(bint);
      }
      if ( this.sign == 0 ) {
         if ( (this.compareTo(bint) > 0 ) && (bint.sign == 1)) {
            bint.sign = 0;
            return this.add(bint);
         } else if (( this.compareTo(bint) > 0) && (bint.sign == 0)) {
            bint.sign = 1;
            return this.add(bint);
         }
         if ((this.compareTo(bint) < 0) && (this.sign == 1)) {
            this.sign = 0;
            return this.add(bint);
         } else if ((this.compareTo(bint) < 0) && (this.sign == 0)) {
            this.sign = 1;
            return this.add(bint);
         }
      } else {
         if (( this.compareTo(bint) < 0) && (bint.sign == 1)) {
            bint.sign = 0;
            return this.add(bint);
         } else if (( this.compareTo(bint) < 0 ) && (bint.sign == 0)) {
            bint.sign = 1;
            return this.add(bint);
         }

         if (( this.compareTo(bint) > 0 ) && (this.sign == 1)) {
            this.sign = 0;
            return this.add( bint );
         } else if (( this.compareTo(bint) > 0 ) && (this.sign == 0)) {
            this.sign = 1;
            return this.add(bint);
         }
      }
      return null;
   }

  /** 
   *  Method to multiply the value of a BrobInt passed as argument to this BrobInt
   *  @param  bint         BrobInt to multiply this by
   *  @return BrobInt that is the product of the value of this BrobInt and the one passed in
   */
   public BrobInt multiply( BrobInt bint ) {
      int count = 1; 
      BrobInt newBrobInt = new BrobInt(this.internalValue);
      while (count < Integer.valueOf(bint.internalValue)) {
         newBrobInt = add(newBrobInt);
         count = count+1;
      }
      return newBrobInt;
   }

  /** 
   *  Method to divide the value of this BrobInt by the BrobInt passed as argument
   *  @param  bint         BrobInt to divide this by
   *  @return BrobInt that is the dividend of this BrobInt divided by the one passed in
   */
   public BrobInt divide( BrobInt bint ) {
      BrobInt newBrob = null;
      int i = 0;
      byte tempSign;
      if ( this.sign != bint.sign ) {
         if (this.sign == 1) {
            tempSign = this.sign;
            this.sign = bint.sign;
         } else {
            tempSign = bint.sign;
            bint.sign = this.sign;
         }
      }

      if ( this.equals(bint) ) {
         return new BrobInt( "1" );
      } else if (0 > this.compareTo(bint)) {
         return new BrobInt("0");
      }

      newBrob = new BrobInt (bint.toString());
      while ( 0 <= this.compareTo(newBrob)) {
         newBrob.internalValue = (
            new BrobInt(newBrob
            .toString())
            .add(bint))
            .toString();
            i++;
         for ( int j = 0; j < newBrob.reversed.length(); j++ ) {
         newBrob.byteVersion[j] = (byte)Character.getNumericValue(reversed.charAt(j));
         }  
      }
      BrobInt dividend = new BrobInt (String.valueOf( i ));
      if ( this.sign == bint.sign ) {
         dividend.sign = 0;
      } else {
         dividend.sign = 1;
      }
      return dividend;
   }

  /** 
   *  Method to get the remainder of division of this BrobInt by the one passed as argument
   *  DOES NOT WORK
   *  @param  bint         BrobInt to divide this one by
   *  @return BrobInt that is the remainder of division of this BrobInt by the one passed in
   */
   public BrobInt remainder( BrobInt bint ) {
      BrobInt a = null;
      this.sign = bint.sign;
      if ((this.equals(bint) || (0 > this.compareTo(bint)))) {
         return new BrobInt( "0" );
      }
      a = new BrobInt( bint.toString() );
      System.out.println("a: " + a.toString());
      while ( 0 <= this.compareTo(a)) {
         a.internalValue = ( new BrobInt(a.toString()).add(bint)).toString();
         for ( int j = 0; j < a.reversed.length(); j++ ) {
            a.byteVersion[j] = (byte)Character.getNumericValue(reversed.charAt(j));
            } 
      }
      System.out.println( "a internal val 354: " + a.internalValue);
      a.internalValue = (new BrobInt(a.toString()).subtract(bint)).toString();
      for ( int j = 0; j < a.reversed.length(); j++ ) {
         a.byteVersion[j] = (byte)Character.getNumericValue(reversed.charAt(j));
         } 
         System.out.println("Brobbbb: " + this.internalValue);
         System.out.println(" a: " + a.internalValue);
      return new BrobInt( this.subtract( a ).toString());
   }

  /** 
   *  Method to compare a BrobInt passed as argument to this BrobInt
   *  @param  bint  BrobInt to compare to this
   *  @return int   that is one of neg/0/pos if this BrobInt precedes/equals/follows the argument
   *  NOTE: this method does not do a lexicographical comparison using the java String "compareTo()" method
   *        It takes into account the length of the two numbers, and if that isn't enough it does a
   *        character by character comparison to determine
   */
   public int compareTo( BrobInt bint ) {

     // remove any leading zeros because we will compare lengths
      String me  = removeLeadingZeros( this ).toString();
      String arg = removeLeadingZeros( bint ).toString();

     // handle the signs here
      if( 1 == sign && 0 == bint.sign ) {
         return -1;
      } else if( 0 == sign && 1 == bint.sign ) {
         return 1;
      } else if( 0 == sign && 0 == bint.sign ) {
        // the signs are the same at this point ~ both positive
        // check the length and return the appropriate value
        //   1 means this is longer than bint, hence larger positive
        //  -1 means bint is longer than this, hence larger positive
         if( me.length() != arg.length() ) {
            return (me.length() > arg.length()) ? 1 : -1;
         }
      } else {
        // the signs are the same at this point ~ both negative
         if( me.length() != arg.length() ) {
            return (me.length() > arg.length()) ? -1 : 1;
         }
      }

     // otherwise, they are the same length, so compare absolute values
      for( int i = 0; i < me.length(); i++ ) {
         Character a = Character.valueOf( me.charAt(i) );
         Character b = Character.valueOf( arg.charAt(i) );
         if( Character.valueOf(a).compareTo( Character.valueOf(b) ) > 0 ) {
            return 1;
         } else if( Character.valueOf(a).compareTo( Character.valueOf(b) ) < 0 ) {
            return (-1);
         }
      }
      return 0;
   }

  /**
   *  Method to check if a BrobInt passed as argument is equal to this BrobInt
   *  @param  bint     BrobInt to compare to this
   *  @return boolean  that is true if they are equal and false otherwise
   */
   public boolean equals( BrobInt bint ) {
      return ( (this.sign == bint.sign) && (this.toString().equals( bint.toString() )) );
   }

  /**
   *  Method to return a BrobInt given a long value passed as argument
   *  @param  value    long type number to make into a BrobInt
   *  @return BrobInt  which is the BrobInt representation of the long
   */
   public static BrobInt valueOf( long value ) throws NumberFormatException {
      BrobInt bi = null;
      try { bi = new BrobInt( Long.valueOf( value ).toString() ); }
      catch( NumberFormatException nfe ) { throw new NumberFormatException( "\n  Sorry, the value must be numeric of type long." ); }
      return bi;
   }

  /**
   *  Method to return a String representation of this BrobInt
   *  @return String  which is the String representation of this BrobInt
   */
   public String toString() {
      return internalValue;
   }

  /**
   *  Method to remove leading zeros from a BrobInt passed as argument
   *  @param  bint     BrobInt to remove zeros from
   *  @return BrobInt that is the argument BrobInt with leading zeros removed
   *  Note that the sign is preserved if it exists
   */
   public BrobInt removeLeadingZeros( BrobInt bint ) {
      Character sign = null;
      String returnString = bint.toString();
      int index = 0;

      if( allZeroDetect( bint ) ) {
         return bint;
      }
      if( ('-' == returnString.charAt( index )) || ('+' == returnString.charAt( index )) ) {
         sign = returnString.charAt( index );
         index++;
      }
      if( returnString.charAt( index ) != '0' ) {
         return bint;
      }

      while( returnString.charAt( index ) == '0' ) {
         index++;
      }
      returnString = bint.toString().substring( index, bint.toString().length() );
      if( sign != null ) {
         returnString = sign.toString() + returnString;
      }
      return new BrobInt( returnString );

   }

  /**
   *  Method to return a boolean if a BrobInt is all zeros
   *  @param  bint     BrobInt to compare to this
   *  @return boolean  that is true if the BrobInt passed is all zeros, false otherwise
   */
   public boolean allZeroDetect( BrobInt bint ) {
      for( int i = 0; i < bint.toString().length(); i++ ) {
         if( bint.toString().charAt(i) != '0' ) {
            return false;
         }
      }
      return true;
   }

  /**
   *  Method to display an Array representation of this BrobInt as its bytes
   *  @param   d  byte array from which to display the contents
   *  NOTE: may be changed to int[] or some other type based on requirements in code above
   */
   public void toArray( byte[] d ) {
      System.out.println( "Array contents: " + Arrays.toString( d ) );
   }


  /**
   *  the main method redirects the user to the test class
   *  @param  args  String array which contains command line arguments
   *  NOTE:  we don't really care about these, since we test the BrobInt class with the BrobIntTester
   */
   public static void main( String[] args ) {
      System.out.println( "\n  Hello, world, from the BrobInt program!!\n" );
      System.out.println( "\n   You should run your tests from the BrobIntTester...\n" );

      BrobInt b1 = null;
      try { System.out.println( "   Making a new BrobInt: " ); b1 = new BrobInt( "-147258369789456123" ); }
      catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
      try { System.out.println( "   expecting: 147258369789456123\n     and got: " + b1.toString());}
      catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
      System.out.println( "\n    Multiplying 82832833 by 3: " );
      try { System.out.println( "      expecting: 248498499\n        and got: " + new BrobInt("82832833").multiply( BrobInt.THREE ) ); }
      catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
      System.out.println( "\n    Multiplying asdf by 3: " );
      System.out.println( "\n    Multiplying 3 by 82832833 and adding 1: " );
      try { System.out.println( "      expecting: 1240\n        and got: " + BrobInt.THREE.multiply( new BrobInt( "413" ) ).add( BrobInt.ONE ) ); }
      catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
      try {System.out.println( " expecting 2\n                  and got: " + (new BrobInt("200").subtract(new BrobInt("98"))));
      } catch (Exception e ) {System.out.println (" Exception thrown asdlhagl;jga;sdfa\n");}
      try { System.out.println( "      expecting: 3\n           and got: " + (new BrobInt("7")).remainder(new BrobInt("4")));
      } catch (Exception e ) { System.out.println(" Exception thrown: ");
      }
      try { System.out.println( "      expecting: 7\n           and got: " + (new BrobInt("4")).divide(BrobInt.TWO));
      } catch (Exception e ) { System.out.println(" Exception thrown: ");
      }
      System.out.println( "\n Subtracting 234567 - (-10) " );
      try {System.out.println( " expecting 234577\n         and got: " + (new BrobInt("234567")).subtract(new BrobInt("-10")) );}
      catch (Exception e) {System.out.println( "Exception thrown: ");}
      System.exit( 0 );

   }
}
