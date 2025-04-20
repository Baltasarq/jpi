// JPI (c) 2025 Baltasar MIT License <baltasarq@gmail.com>


package com.devbaltasarq.jpi.ui;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;


/** Canvas specialization to draw circles. */
public class CircleDrawer extends Canvas {
    public static int DEFAULT_RADIUS = 100;
    public static int CIRCLE_FRACTION = 4;
    
    /** Create a new circle drawer, inside a container.
      * It actually does only draw a fraction of the circle.
      * @param owner the container to create the drawer in.
      */
    public CircleDrawer(Container owner)
    {
        super();
        
        this.owner = owner;
        this.radius = DEFAULT_RADIUS;
        
        this.setBackground( Color.white );
        this.setForeground( Color.black );
        this.resize( 100, 100 );
        this.setVisible( true );
    }
    
    /** @return the radius of the circle being drawn. */
    public int getRadius()
    {
        return this.radius;
    }
    
    /** @return the real radius of the drawn circle. */
    public int getRealRadius()
    {
        return this.realRadius;
    }
            
    /** Changes the value of the circle to draw, triggering a redraw.
      * @param r the new radius of the circle.
      */
    public void setRadius(int r)
    {
        this.radius = r;
        this.repaint();
    }
    
    /** Does the actual drawing of a fraction of a circle. 
      * @param G the graphics to use.
      * @see CIRCLE_FRACTION
      */
    @Override
    public void paint(Graphics G)
    {
        final int R = this.getRadius();
        final int CNVS_WIDTH = Math.max( R, this.owner.getWidth() );
        final int CNVS_HEIGHT = Math.max( R, this.owner.getHeight() );
        
        this.resize( CNVS_WIDTH, CNVS_HEIGHT );
        G.clearRect( 0, 0, CNVS_WIDTH, CNVS_HEIGHT );
        G.fillArc( 0, 0, R, R, 90, 360 / CIRCLE_FRACTION );
    }
    
    /** Calculates the area of the whole circle, parting from the 1/4 circle.
      * @return The number of pixels inside the circle.
      *         this is the correct area, since it takes into account
      *         the fraction of the circle being drawn.
      */
    public int calculateCircleArea()
    {
       final StringBuffer SB = new StringBuffer( this.radius * this.radius );
       int toret = 0;

       if ( this.getWidth() > 0 ) {
           final BufferedImage IMAGE = new BufferedImage(
                                                this.radius,
                                                this.radius,
                                                BufferedImage.TYPE_BYTE_GRAY );
           final Graphics G = IMAGE.getGraphics();
           
           this.paint( G );
           
           // Loop through each pixel and count black pixels
           final Raster RASTER = IMAGE.getData();
           final int[] PIXEL = new int[ 1 ];
           int pixelsInRow;
           
           this.realRadius = 0;
           for (int i = 0; i < IMAGE.getHeight(); i += 1) {
               pixelsInRow = 0;
               
               for (int j = 0; j < IMAGE.getWidth(); j += 1) {
                   RASTER.getPixel( i, j, PIXEL );
                   
                   if ( PIXEL[ 0 ] > 0 ) {
                       toret += 1;
                       pixelsInRow += 1;
                       SB.append( '*' );
                   } else {
                       SB.append( ' ' );
                   }
               }
               
               SB.append( '\n' );
               this.realRadius = Math.max( pixelsInRow, this.realRadius );
           }
       }

       return toret * CIRCLE_FRACTION;
    }

    private int radius;
    private int realRadius;
    private final Container owner;
}
