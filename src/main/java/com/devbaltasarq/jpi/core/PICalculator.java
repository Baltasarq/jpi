// JPI (c) 2025 Baltasar MIT License <baltasarq@gmail.com>


package com.devbaltasarq.jpi.core;


/** Calculator for the value of PI. */
public class PICalculator {
    /** Creates a new calculator for PI, with initial values.
      * @param circleRadiusInPixels the radius of the circle, in pixels.
      * @param circleAreaInPixels the area of the circle, in pixels.
      */
    public PICalculator(int circleRadiusInPixels, int circleAreaInPixels)
    {
        this.circleRadiusInPixels = circleRadiusInPixels;
        this.circleAreaInPixels = circleAreaInPixels;
    }
    
    /** @return the area of the circle, in pixels. */
    public int getCircleAreaInPixels()
    {
        return this.circleAreaInPixels;
    }
    
    /** @return the radius of the circle, in pixels. */
    public int getCircleRadiusInPixels()
    {
        return this.circleRadiusInPixels;
    }
    
    /** Sets the area of the circle, in pixels.
      * @param pixels the area of the circle, in pixels.
      */
    public void setCircleAreaInPixels(int pixels)
    {
        this.circleAreaInPixels = pixels;
    }
    
    /** Sets the radius of the circle, in pixels.
      * @param r the radius of the circle, in pixels.
      */
    public void setCircleRadius(int r)
    {
        this.circleRadiusInPixels = r;
    }
    
    /** Calculates PI
      * the equation for the circle's of radius r and area A is A = PI R R,
      * which means that PI can be calculated as PI = A / (R R)
      * @return the value of PI (should be near to 3.14).
      */
    public double calculate()
    {
        final double A = this.getCircleAreaInPixels();
        final double R = this.getCircleRadiusInPixels();
        
        return A / ( R * R );
    }
    
    private int circleRadiusInPixels;
    private int circleAreaInPixels;
}
