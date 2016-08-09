/**
 * A class that represents a rational number. 
 * 
 * @author Charles Hoot 
 * @version 4.0
*/

import static java.lang.Math.abs;

public class Rational
{
    private int num;
    private int den;

    /**
     * The default constructor for objects of class Rational.  Creates the rational number 1.
     */
    public Rational()
    {       
        num = 1;
        den = num;
    }

    /**
     * The alternate constructor for objects of class Rational.  Creates a rational number equivalent to n/d.
     * @param n The numerator of the rational number.
     * @param d The denominator of the rational number.
     */    
    public Rational(int n, int d)
    {
        if(d!=0){
            if(n==0)
                d=1;
            
            num = n;
            den = d;
            normalize();
        } else {
            throw new ZeroDenominatorException("Exception");
        }
    }
    
    /**
     * Get the value of the Numerator
     *
     * @return     the value of the numerator
     */
    public int getNumerator()
    {
        return num;
    }
    
    /**
     * Get the value of the Denominator
     *
     * @return     the value of the denominator
     */
    public int getDenominator()
    {
        return den;
    }


    /**
     * Negate a rational number r
     * 
     * @return a new rational number that is negation of this number -r
     */    
    public Rational negate()
    {               
        // CHANGE THE RETURN TO SOMETHING APPROPRIATE
        return new Rational(-num,den);
    }


    /**
     * Invert a rational number r 
     * 
     * @return a new rational number that is 1/r.
     */    
    public Rational invert()
    {               
        // CHANGE THE RETURN TO SOMETHING APPROPRIATE
        int sNum = num;
        int sDen = den;
        num = den;
        den = sNum;
        normalize();
        int n = num;
        int d = den;
        num = sNum;
        den = sDen;
        
        return new Rational(n,d);
    }

    /**
     * Add two rational numbers
     *
     * @param other the second argument of the add
     * @return a new rational number that is the sum of this and the other rational
     */    
    public Rational add(Rational other)
    {
        int sNum = other.getNumerator();
        int sDen = other.getDenominator();
        Rational added = new Rational(sNum*den+num*sDen,sDen*den);
        added.normalize();
        // ADD NEW CODE AND CHANGE THE RETURN TO SOMETHING APPROPRIATE
        return added;
    }
    
     /**
     * Subtract a rational number t from this one r
     *
     * @param other the second argument of subtract
     * @return a new rational number that is r-t
     */    
    public Rational subtract(Rational other)
    {               
        // CHANGE THE RETURN TO SOMETHING APPROPRIATE
        return add(new Rational(-other.getNumerator(),other.getDenominator()));
    }

    /**
     * Multiply two rational numbers
     *
     * @param other the second argument of multiply
     * @return a new rational number that is the sum of this object and the other rational.
     */    
    public Rational multiply(Rational other)
    {
        Rational t = new Rational(num*other.getNumerator(),den*other.getDenominator());
        t.normalize();
        // ADD NEW CODE AND CHANGE THE RETURN TO SOMETHING APPROPRIATE
        return t;
    }
        
 
     /**
     * Divide this rational number r by another one t
     *
     * @param other the second argument of divide
     * @return a new rational number that is r/t
     */    
    public Rational divide(Rational other)
    {
        Rational div = new Rational(other.getDenominator(),other.getNumerator());
        // CHANGE THE RETURN TO SOMETHING APPROPRIATE
        return div.multiply(this);
    }
     
 
 
 /**
     * Put the rational number in normal form where the numerator
     * and the denominator share no common factors.  Guarantee that only the numerator
     * is negative.
     *
     */
    private void normalize()
    {
        int g = gcd(abs(num),abs(den));
        if(num < 0 && den < 0){
            num = abs(num);
            den = abs(den);
        } else if (num >= 0 && den < 0){
            num = -num;
            den = abs(den);
        }
        if(num == 0){
            den = 1;
        }
        num = num/g;
        den = den/g;
    }
    
    public String toString(){
        return num+"/"+den;
    }

    public boolean equals(Rational other){
        Rational n = new Rational(other.getNumerator(),other.getDenominator());
        n.normalize();
        if(n.getNumerator()==num && n.getDenominator()==den)
            return true;
        else
            return false;
    }
    
    /**
     * Recursively compute the greatest common divisor of two positive integers
     *
     * @param a the first argument of gcd
     * @param b the second argument of gcd
     * @return the gcd of the two arguments
     */
    private int gcd(int a, int b)
    {
        int result = 0;
        if(a<b)
            result = gcd(b,a);
        else if(b==0)
            result = a;
        else
        {
            int remainder = a % b;
            result = gcd(b, remainder);
        }
        return result;
    }
   
    
    
}
