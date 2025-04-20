// JPI (c) 2025 Baltasar MIT License <baltasarq@gmail.com>


package com.devbaltasarq.jpi.ui;


import java.awt.Frame;
import java.awt.Panel;
import java.awt.Label;
import java.awt.Button;
import java.awt.Color;
import java.awt.TextField;
import java.awt.ScrollPane;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import java.awt.event.WindowAdapter;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import java.util.concurrent.Executors;
import java.net.URL;
import java.io.IOException;

import com.devbaltasarq.jpi.core.PICalculator;


public class MainWindow extends Frame {
    public MainWindow()
    {
        final Dimension SIZE = new Dimension( 600, 400 );
        final int RADIUS = CircleDrawer.DEFAULT_RADIUS;
        
        this.setMinimumSize( SIZE );
        this.setSize( SIZE );
        this.piCalc = new PICalculator(
                            CircleDrawer.DEFAULT_RADIUS,
                            (int) ( RADIUS * RADIUS * Math.PI ));
        
        this.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit( 0 );
            }
        });
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
               MainWindow.this.calculatePI();
            }
            
            @Override
            public void componentResized(ComponentEvent e) {
                MainWindow.this.calculatePI();
            }
        });
        
        this.build();        
    }
    
    /** Retrieve the icon from jar and sets as the app's icon. */
    private void buildIcon()
    {
        URL url;

        try {
            url = this.getClass().getClassLoader().getResource( "pi.png" );
            this.setIconImage( ImageIO.read( url ) );
        } catch(IOException exc)
        {
            System.err.println( "Warning: failed reading icon from jar: "
                                    + exc.getMessage() );
        } catch(IllegalArgumentException | NullPointerException exc) {
            System.err.println( "Warning: icon not found in jar: "
                                    + exc.getMessage() );
        }
    }
    
    private void build()
    {
        this.pnlOps = new Panel();
        this.pnlDrw = new ScrollPane();
        this.pnlDrw.setBackground( Color.gray );
                
        this.add( BorderLayout.CENTER, this.pnlDrw );
        this.add( BorderLayout.NORTH, this.pnlOps );
        
        this.buildOptionsPanel();
        this.buildCanvasPanel();
        this.buildIcon();
        
        this.pack();
    }
    
    private void buildCanvasPanel()
    {
        this.drawer = new CircleDrawer( this.pnlDrw );
        this.pnlDrw.add( BorderLayout.CENTER, this.drawer );
    }
    
    private void buildOptionsPanel()
    {
        final Label LBL_RADIUS = new Label( "Radius" );
        final Label LBL_PI = new Label( "PI" );
        
        this.edRadius = new TextField( "" + CircleDrawer.DEFAULT_RADIUS, 20 );
        this.btDraw = new Button( "Draw" );
        this.edResPI = new TextField( "3", 20 );
        this.edResPI.setEditable( false );
        
        this.btDraw.addActionListener( (ActionEvent e) -> {
            this.calculatePI();
        });
        
        this.pnlOps.setLayout( new FlowLayout() );
        this.pnlOps.add( LBL_RADIUS );
        this.pnlOps.add( this.edRadius );
        this.pnlOps.add( this.btDraw );
        this.pnlOps.add( LBL_PI );
        this.pnlOps.add( this.edResPI );
    }
    
    public void calculatePI()
    {
        int radius;
            
        // Get the radius from the text field
        try {
            radius = Integer.parseInt( this.edRadius.getText() );
        } catch(NumberFormatException exc)
        {
            radius = CircleDrawer.DEFAULT_RADIUS;
        }

        final int R = radius;
        final MainWindow SELF = this;
        
        SELF.edResPI.setText( "Calculating..." );
        
        Executors.newSingleThreadExecutor().execute( () -> {
            // Draw the circle
            SELF.drawer.setRadius( R );

            // Calculate PI
            final int A = SELF.drawer.calculateCircleArea();

            SELF.piCalc.setCircleAreaInPixels( A );
            SELF.piCalc.setCircleRadius( SELF.drawer.getRealRadius() );
            SELF.edResPI.setText( Double.toString( SELF.piCalc.calculate() ));
        });
    }
    
    private Panel pnlOps;
    private ScrollPane pnlDrw;
    private CircleDrawer drawer;
    private Button btDraw;
    private TextField edResPI;
    private TextField edRadius;
    
    private final PICalculator piCalc;
}
