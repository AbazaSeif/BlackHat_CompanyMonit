/** ***************************************************************************
 * File: MultiThreaded_III.java
 *
 * Copyright (c) 2002-2007 Advanced Applications Total Applications Works.
 * (AATAW)  all Rights Reserved.
 *
 * AATAW grants you ("licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) licensee does not utilize the software in a manner
 * which is disparaging to AATAW.
 *
 * This software is provided "as IS," without a warranty of any kind. all
 * express or implied conditions, representations and warranties, including any
 * implied warranty of merchantability, fitness for a particular purpose or
 * non-infringement, are hereby excluded. AATAW and its licensors shall not be
 * liable for any damages suffered by licensee as a result of using, modifying
 * or distributing the software or its derivatives. in no event will AATAW or its
 * licensors be liable for any lost revenue, profit or data, or for direct,
 * indirect, special, consequential, incidental or punitive damages, however
 * caused and regardless of the theory or liability arisinG out of the use of
 * or inability TO use software, even if AATAW Has been advised of the
 * possibility of such damages.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 ****************************************************************************/

import java.awt.*;
import javax.swing.*;
import javax.swing.JPanel.*;
import java.util.GregorianCalendar;

import java.awt.event.*;
import java.util.*;
import javax.swing.JOptionPane ;

/** *************************************************
 *  Template for all good multi-threaded applications
 *  @author Ronald S. Holland
 *****************************************************/
public class MultiThreaded_III extends JFrame {

   protected Thread tT1[]  ;
   private String output[][] ;
   private JLabel hello1 ,  hello2 ;
   private JLabel title[] ,  names[] , startTimes[] , threadPriority[] ,
                elaspedTimes[] , finalPriority[];
   private boolean debug  = false ;
   private long startTime = System.currentTimeMillis();
   private long elapsedTime ;
   private GregorianCalendar cal;  
   private int hrs , mins , secs ;
   private int width = 700, height = 400;
   private JFrame appMain ;
   private MultiThreaded_III appMT ;
   private Container c ;
   private JPanel mainPanel , helloPanel ;

   /** *************************************************************
    * Provides a way for author to associate information with
    * an application. Can be used for copyright, contact information, etc.
    * @return credits
    ******************************************************************/
   public String getApplicationInfo() {
      StringBuffer s = new StringBuffer();

      s.append("Applet Template\n");
      s.append((char)   169);            // Copyright symbol//  s.append((char)0x2122);            // Trademark symbol
      s.append("2002-2007 TAW Incorported, All Rights Reserved\n");

      return s.toString();
   }


   /** ******************************************************
    *   Called initiated by the main(0 method when it creates 
    *   a new MultiThreaded_III() objects. This informs this
    *   constructor to start its initialization process.
    *********************************************************/
   public MultiThreaded_III() {

      c = getContentPane() ;

      sysPrint( "We are in the init() method for Initialization." );

      SetupMenu() ;

      SetupToolbar() ;

      SetupButtons() ; 

      Setup() ;      

      startThreads() ;

      show() ;

   }

   /** ******************************************************
    * The SetupMenu() method is called by the init() method
    * to create the 
    * 1- Menubar
    * 2- Menus
    * 3- Menuitems
    *********************************************************/
   public void SetupMenu() {

   }  

   /** ******************************************************
    * The SetupToolbar() method is called by the init() method
    * to create the 
    * 1- Toolbar
    * 2- Toolbar buttons
    *********************************************************/
   public void SetupToolbar() {

   }  

   /** ******************************************************
    * The SetupButtons() method is called by the init() method
    * to create the 
    * 1- Push buttons
    *********************************************************/
   public void SetupButtons() {

   }  

   /** ******************************************************
    * The Setup() method is called by the init() method to  
    *  1- Create the GregorianCalendar() variable cal 
    *  2- Get the current hour, minute, second
    *  3- Set the width and height
    *  4- Create String arrays
    *  5- Create the JPanels
    *  6- Create the JLabels
    *  7- Create the JLabels
    *  8- Set the layout managers for the JPanels  
    *  9- Create the hello greeting and add it to the main Frame  
    * 10- Add the JPanels to the main frame
    *********************************************************/
   public void Setup() {
      cal = new GregorianCalendar()  ; 
      hrs  = cal.get( cal.HOUR )   ;
      mins = cal.get( cal.MINUTE ) ;
      secs = cal.get( cal.SECOND ) ;
      width = 600 ;
      height = 400 ;   
      mainPanel     = new JPanel() ;    
      helloPanel    = new JPanel() ; 
      mainPanel.setLayout(  new GridLayout( 4, 5, 5, 5 ) );   
      helloPanel.setLayout( new GridLayout( 2, 1, 5, 5 ) );   
      hello1 = new JLabel("                   Hello, from Ronald S. Holland" )  ;
      hello2 = new JLabel("                   at Total Application Works (TAW)"  ) ;
      helloPanel.add( hello1 ) ;
      helloPanel.add( hello2 ) ;
      title           = new JLabel[ 5 ];
      for ( int iii = 0 ; iii < 5 ; iii++ ) {
         title[ iii ]      = new JLabel() ;
         mainPanel.add( title[ iii ] ) ; 
      }
      title[ 0 ].setText( "THREAD ID" ) ;   
      title[ 1 ].setText( "START TIME" ) ;   
      title[ 2 ].setText( "START PRORITY" ) ;   
      title[ 3 ].setText( "ELASPED TIME" ) ;  
      title[ 4 ].setText( "FINAL PRORITY" ) ; ;
      names           = new JLabel[ 3 ];
      startTimes      = new JLabel[ 3 ];
      threadPriority  = new JLabel[ 3 ];
      elaspedTimes    = new JLabel[ 3 ];
      finalPriority   = new JLabel[ 3 ];

      for ( int iii = 0 ; iii < 3 ; iii++ ) {
         names[ iii ]          = new JLabel( "Thread"  ) ;
         startTimes[ iii ]     = new JLabel( "Start time "  ) ;
         threadPriority[ iii ] = new JLabel( "Start Priority "  ) ;
         elaspedTimes[ iii ]   = new JLabel( "Elasped Time "  ) ;
         finalPriority[ iii ] = new JLabel( "Final Priority "  ) ;
         mainPanel.add( names[ iii ] ) ;
         mainPanel.add( startTimes[ iii ] ) ;
         mainPanel.add( threadPriority[ iii ] ) ;
         mainPanel.add( elaspedTimes[ iii ] ) ;
         mainPanel.add( finalPriority[ iii ] )  ;
      } 

      c.add( mainPanel  , BorderLayout.CENTER ) ;
      c.add( helloPanel , BorderLayout.NORTH ) ;    

      setSize( width , height ) ;   

      setTitle( "Multithread Program III" ) ;


   }  

   /** ******************************************************
    *   Called by the MultiThreaded_III() constructor to inform 
    *   this application that it should start its execution. 
    *   The logic is 
    *   1- Create the three threads
    *   2- Call the thread's start method
    *********************************************************/
   public void startThreads() {

      tT1    = new Thread[3]  ;
      tT1[0] = null ;
      output = new String[3][6]  ;

      sysPrint( "We are in the start() method to start running the applet." ) ;
      if (tT1[0] == null) {
         sysPrint( "We are in the start() method to create to a new Thread." ) ;

         tT1[0] = new MyThread( this , 0 );
         tT1[1] = new MyThread( this , 1 );
         tT1[2] = new MyThread( this , 2 );


         sysPrint( "We are in the start() method to start the new Threads."  );

         tT1[0].start();
         tT1[1].start();
         tT1[2].start();
      }
   }


   /** ******************************************************
    *  The sysPrint() method is used to display debugging 
    *  messages.
    *********************************************************/
   public void sysPrint( String str ) {
      if ( debug ) {
         System.out.println( str ) ;
      }
   }


   /** ******************************************************
    *  Called by the Thread's run() method to inform this
    *  application that it should stop its execution.
    *  1- ii = Thread ID
    *  2- currentThread = thread reference
    *********************************************************/
   public void stop( int ii , Thread currentThread ) {
       
      sysPrint( "We are in the stop() method to stop running Thread" + ii ) ;

      tT1[ ii ] = null;
   }


   /** ******************************************************
    *  Called by the sysExit() method to inform this
    *  application that it is being reclaimed and that it should
    *  destroy any resources that it has allocated. Set each
    *  thread to null to cause the garbage collector to reclaim
    *  its resources.
    *********************************************************/
   public void destroy() {

      sysPrint( "We are in the destroy() method to clean up before being removed." );

      tT1[ 0 ] = null ;
      tT1[ 1 ] = null ;
      tT1[ 2 ] = null ;

   }


   /** ******************************************************
    *  Paints the container. This forwards the paint to any
    *  lightweight components that are children of this
    *  container.
    *********************************************************/
   public void paint(Graphics g) {
      super.paint (g);

      //g.drawString( "Hello, from Ronald S. Holland" , 10, 50 ) ;
      //g.drawString( "at Total Application Works (TAW)" , 10, 70 ) ;
      //JOptionPane.showMessageDialog(null,
                         // "We are in the paint() method to start drawing the applet content.",
                         // "Redraw",
                         // JOptionPane.INFORMATION_MESSAGE );
   }

   /** *********************************************************
    *  The displayElapsedTime() method displays the time elapsed 
    *  since this program began executing.
    *  1- message - is the message to be displayed
    *  2- ii - is the thread ID
    *  3- aa
    *     0- means display the start time message 
    *     1- means display the elasped time message 
    **************************************************************/
   public void displayElapsedTime( String message , int ii , int aa ) {

      elapsedTime = System.currentTimeMillis() - startTime ;

      sysPrint ( message + " at " +
               ( elapsedTime / 1000.0) + " seconds");

      if ( aa == 0 ) {
         names[ ii ].setText( "Thread" + ii ) ;
         startTimes[ ii ].setText( cal.get( cal.HOUR )  + ":" +
                          cal.get( cal.MINUTE ) + ":" +  cal.get( cal.SECOND ) ) ;
         sysPrint ( "We are in the displayElapsedTime() method  " + 
                          "Thread" + ii + " Start time is " + cal.get( cal.HOUR )  + ":" +
                          cal.get( cal.MINUTE ) + ":" + 
                          cal.get( cal.SECOND ) + "\n\n" + 
                          "Thread" + ii + "\n\n" + "Close the applet after the last Thread to exit.\n " +  
                          "The Thread priority is " + tT1[ ii ].getPriority() + "\n") ;

         threadPriority[ ii ].setText( ""  + tT1[ ii ].getPriority() ) ;
      }
      else if ( aa == 1 ) {
            elaspedTimes[ ii ].setText( "" +  ( elapsedTime / 1000.0) ) ; 
            sysPrint ( "We are in the displayElapsedTime method to measure the elapsed running time.\n" +
                          "Elapsed time is " + ( elapsedTime / 1000.0) +
                          "Thread" + ii + "\n\n" + "Close the applet after the last Thread to exit.\n " +  
                          "The Thread priority is " + tT1[ ii ].getPriority() + "\n" ) ;
            finalPriority[ ii ].setText( "" + tT1[ ii ].getPriority() ) ; 
      } 
   }

   /** ***********************************************************
    * The sysExit() method is called in response to a close
    * application event.
    ************************************************************* */
   public void sysExit( int ext ) {

      destroy() ;
      System.exit( ext ) ;
   }

   /**  ****************************************************
    * Set the width of frame to 600 if w less than 0
    ***************************************************** */
   public void setWidth( int w )
      { width = ( w >= 0 ? w : 600 ); }

   /** ****************************************************
    * Set the MultiThreaded_III reference
    ***************************************************** */
   public void setappMT( MultiThreaded_III app_MT )
      { appMT = app_MT ; }

   /** ****************************************************
    * Set the height to 400 if h less than 0
    ***************************************************** */
   public void setHeight( int h )
      { height = ( h >= 0 ? h : 400 ); }

   /** ********************************************************************
    *  main(String args[] ) : this is the entry point for this application
    *                         that Java calls when starting this program
    *                         execution when it runs as an application.
    *********************************************************************/
   public static void main( String args[] )
   { 

      /** create one application instance */
      final MultiThreaded_III app = new MultiThreaded_III();
      app.setappMT( app ) ;  

      app.addWindowListener(
         new WindowAdapter() {
            public void windowClosing( WindowEvent e )
            {
              app.sysExit( 0 );
            }
         }
      );

      
   }

   /** *********************************************************
    * A thread is a thread of execution in a program. The Java 
    * Virtual Machine allows an application to have multiple 
    * threads of execution running concurrently.
    **************************************************************/
   public class MyThread extends Thread {
      MultiThreaded_III multi ;
      int index ;
      boolean isAlive = true ;

      public MyThread( MultiThreaded_III MTL_C , int in_dex ) {
         multi =  MTL_C ;
         index =  in_dex ;
      }

      /** *********************************************************
       * The run() method's logic is:
       * 1- Show a JOptionPane.showMessageDialog
       * 2- Obtain the current thread reference
       * 3- Determine which thread initiated a call to the run() method
       * 4- Repaint the screen
       * 5- Put the current thread to sleep
       * 6- Stop the current thread
       **************************************************************/
      int ii = 0 ; 

      public void run() {

         final Thread currentThread = Thread.currentThread() ;


         for ( ii = 0; ii < tT1.length; ii++ ) {
            if ( currentThread == tT1[ ii ] ) {
               sysPrint( "run() 2: The value of ii is " + ii ) ;
               break ;
            }
         }


         final int index = ii ; 

         displayElapsedTime( currentThread.getName() + " has started." , ii , 0 );
         sysPrint ( "We are in the run() method to start running a Thread" + index  + "\n" ) ;

         // By default, update display every half second
         while (currentThread == tT1[ index  ] && ii < 3 ) {
            repaint();
            try {
               sysPrint ( "We are in the run() method to cause the current Thread" + index + " to sleep.\n" ) ;

               int st = ( int )( Math.random() * 1000 )     ;     
               tT1[ index ].sleep( st );
               synchronized( MyThread.this ) {
	          tT1[ index ].setPriority( index+2 ) ;
                  MyThread.this.yield() ;
               } // End of synchronized block

               sysPrint ( "Thread" + index + " Waking up from sleep." )  ;

               sysPrint ( "Thread" + index + " sleep time is ." + (st)  + " seconds" )  ;

               displayElapsedTime ("Thread" + index + " has finished  with an elapsed time of " , index , 1 );
               multi.stop( ii , currentThread ) ;
               tT1[ index ] = null ;
               ii = 10 ;
            }
            catch (InterruptedException e) {
            }

            ii++ ;            
            sysPrint( "Loop counter is " + ii + " for Thread" + index ) ;
         }
      }
   }
}

