/** ***************************************************************************
 * File: MultiThreaded_IV.java
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
import javax.swing.event.*;
import java.lang.Thread ;

/** *************************************************
 *  Template for all good multi-threaded applications
 *  @author Ronald S. Holland
 *****************************************************/
public class MultiThreaded_IV extends JFrame  implements  ActionListener {

   protected Thread tT1[]  ;
   private double sleepTime[] , waitTime[]  = { 0,  0,  0,  0,  0 } ;
   private JLabel hello1 ,  hello2 ;
   private JLabel title[] ,  names[] , startTimes[] ,
                elaspedTimes[] , finalPriority[] , Priority[] ,
                setThePriority , priorityInfo , priorityInfo2 ,
                runTimes[] , stopThread[]  ;
   private JCheckBox stopped[] , SetPri[] , susPend[] ;
   private int priorities[] = { 5,  5,  5,  5,  5 } ;
   private boolean isStopped[] , suspended[] , SETPriority[] , debug  = true ;
   private long elapsedTime ;
   private long startTime[]  = { 0,  0 ,  0 ,  0 ,  0 } ;
   private GregorianCalendar cal;
   private int hrs , mins , secs ;
   private int width = 600, height = 300;
   private JFrame appMain ;
   private MultiThreaded_IV appMT ;
   private Container c ;
   private JPanel mainPanel , helloPanel , priorityPanel ;
   private int numbers[] = { 1,  2,  3,  4,  5,  6,  7,  8,  9, 10,
                            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                            21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                            31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
                            41, 42, 43, 44, 44, 46, 47, 48, 49, 50 ,
                            51, 52, 53, 54, 55, 56, 57, 58, 59, 50,
                            61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
                            71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
                            81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
                            91, 92, 93, 94, 95, 96, 97, 98, 99, 100 } ;
   private JSlider prioritySlider;
   private final static int LEN = 5  ;
   private static int OneNumber ;

   /** *************************************************************
    * Provides a way for author to associate information with
    * an application. Can be used for copyright, contact information, etc.
    * @return credits
    *****************************************************************/
   public String getApplicationInfo() {
      StringBuffer s = new StringBuffer();

      s.append("Applet Template\n");
      s.append((char)   169);            // Copyright symbol//  s.append((char)0x2122);            // Trademark symbol
      s.append("2002-2007 TAW Incorported, All Rights Reserved\n");

      return s.toString();
   }


   /** ******************************************************
    *   This is the constructor for the MultiThreaded_IV()
    *   class that is used for initialization.
    *********************************************************/
   public MultiThreaded_IV() {

      c = getContentPane() ;

      sysPrint( "We are in the MultiThreaded_IV() constructor for Initialization." );

      SetupMenu() ;

      SetupToolbar() ;

      SetupPanels() ;

      SetupArrays() ;

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
    * The SetupPanels() method is called by the init() method
    * to:
    * 1- Create the JPanels
    * 2- Set up the Layout Managers to be used with JPanel
    * 3- Add the JPanels to the content pane
    *********************************************************/
   public void SetupPanels() {
      mainPanel     = new JPanel() ;
      helloPanel    = new JPanel() ;
      priorityPanel = new JPanel() ;
      mainPanel.setLayout(  new GridLayout( 6, 10, 5, 5 ) );
      helloPanel.setLayout( new GridLayout( 2, 1, 5, 5 ) );

      c.add( mainPanel  , BorderLayout.CENTER ) ;
      c.add( helloPanel , BorderLayout.NORTH ) ;
      c.add( priorityPanel , BorderLayout.SOUTH ) ;
   }


   /** ******************************************************
    * The SetupArrays() method is called by the init() method
    * to create the
    *  1- elaspedTimes[] contains the start times minus the
    *     stop times
    *  2- finalPriority was used to show the final priority.
    *     It is no longer used.
    *  3- isStopped[] shows whether a thread has been stopped
    *  4- names[] contains the thread names
    *  5- priorities[] contains the thread's current priority
    *  6- Priority[] is used to display the thread's current priority
    *  7- runTimes[] is the time the tread is not waiting/sleeping
    *  8- SetPri[] indicates that the user wants to change the thread's
    *     priority
    *  9- SETPriority[] contains the flags indicating the priority is
    *     undergoing a change
    * 10- startTimes[] contains each thread's start time
    * 11- stopped[] indicates the thread has been stopped
    * 12- stopThread[] shows that a thread has been stopped
    * 13- susPend[] contains indicators that the user wants to suspend
    *     a thread
    * 14- suspended[] contains flags indicating a thread is suspended
    * 15- tT1[] contains the IDs for the threads
    ***********************************************************/
   public void SetupArrays() {
      elaspedTimes   = new JLabel[ LEN ];
      finalPriority  = new JLabel[ LEN ];
      isStopped      = new boolean[ LEN ] ;
      names          = new JLabel[ LEN ] ;
      priorities     = new int[ LEN ] ;
      Priority       = new JLabel[ LEN ] ;
      //threadPriority = new JLabel[ LEN ] ;
      runTimes       = new JLabel[ LEN ] ;
      SetPri         = new JCheckBox[ LEN ] ;
      SETPriority    = new boolean[ LEN ] ;
      startTimes     = new JLabel[ LEN ];
      stopped        = new JCheckBox[ LEN ] ;
      stopThread     = new JLabel[ LEN ] ;
      susPend        = new JCheckBox[ LEN ] ;
      suspended      = new boolean[ LEN ] ;
      tT1            = new Thread[ LEN ] ;
      sleepTime      = new double[ LEN ] ;
   }

   /** ******************************************************
    * The SetupButtons() method is called by the MultiThreaded_IV
    * constructor to create the
    * 1- Push buttons
    *********************************************************/
   public void SetupButtons() {

   }

   /** ******************************************************
    * The Setup() method is called by the init() method to
    *  1- Create the GregorianCalendar() variable cal
    *  2- Get the current hour, minute, second
    *  3- Set the width and height
    *  4- Create the JLabels for the hello greeting
    *  5- Add the hello panel to the content panel
    *  6- Create the title array
    *  7- Set the text for the title
    *  7- Set up the GUI
    *  8- Add the ActionListeners
    *  9- Add the rest of the GUI to the content pane
    * 10- Create the priority JLabel and JSlider
    * 11- Add the JLabel and JSlider to the priority Panel
    *********************************************************/
   public void Setup() {
      cal = new GregorianCalendar()  ;
      hrs  = cal.get( cal.HOUR )   ;
      mins = cal.get( cal.MINUTE ) ;
      secs = cal.get( cal.SECOND ) ;
      width = 1000 ;
      height = 400 ;

      hello1 = new JLabel("                   Hello, from Ronald S. Holland" )  ;
      hello2 = new JLabel("                   at Total Application Works (TAW)"  ) ;
      helloPanel.add( hello1 ) ;
      helloPanel.add( hello2 ) ;
      title           = new JLabel[ 9 ];
      for ( int iii = 0 ; iii < 9 ; iii++ ) {
         title[ iii ]      = new JLabel() ;
         mainPanel.add( title[ iii ] ) ;
      }
      title[ 0 ].setText( "THREAD ID" ) ;
      title[ 1 ].setText( "SUSPENDED" ) ;
      title[ 2 ].setText( "PRIORITY" ) ;
      title[ 3 ].setText( "SET PRIORITY" ) ;
      title[ 4 ].setText( "START TIME" ) ;
      title[ 5 ].setText( "ELASPED TIME" ) ;
      title[ 6 ].setText( "RUN TIME" ) ;
      title[ 7 ].setText( "STOPPED" ) ;
      title[ 8 ].setText( "STOP" ) ;

      for ( int iii = 0 ; iii < LEN ; iii++ ) {

         sleepTime[ iii ] = 0.0 ;

         names[ iii ]          = new JLabel( "Thread"  ) ;
         names[ iii ].setOpaque( true ) ;
         names[ iii ].setBackground( Color.WHITE ) ;
         mainPanel.add( names[ iii ] ) ;

         SetPri[ iii ] = new JCheckBox( "Set Priority" ) ;
         susPend[ iii ] = new JCheckBox( "Suspended" ) ;
         stopped[ iii ] = new JCheckBox( "Stopped" ) ;
         startTimes[ iii ]     = new JLabel( "Start time "  ) ;
         SetPri[ iii ].addActionListener( this ) ;
         susPend[ iii ].addActionListener( this ) ;
         stopped[ iii ].addActionListener( this ) ;
         mainPanel.add( susPend[ iii ] ) ;
         Priority[ iii ] = new JLabel( "Current priority" ) ;
         runTimes[ iii ] = new JLabel( " " ) ;
         //threadPriority[ iii ] = new JLabel( "Current priority" ) ;
         elaspedTimes[ iii ]   = new JLabel( "Elasped Time "  ) ;
         finalPriority[ iii ] = new JLabel( "Final Priority "  ) ;
         stopThread[ iii ] = new JLabel("Stop Thread" ) ;
         stopThread[ iii ].setOpaque( true ) ;
         stopThread[ iii ].setBackground( Color.WHITE ) ;
         isStopped[ iii ] = false ;
         mainPanel.add( Priority[ iii ] ) ;
         mainPanel.add( SetPri[ iii ] ) ;
         mainPanel.add( startTimes[ iii ] ) ;
         mainPanel.add( elaspedTimes[ iii ] ) ;
         mainPanel.add( runTimes[ iii ] ) ;
         mainPanel.add( stopThread[ iii ] ) ;
         mainPanel.add( stopped[ iii ] ) ;
      }

      // Create the instructions for how to change the priority
      setThePriority = new JLabel( "To set The Priority" ) ;
      priorityInfo   = new JLabel( "change slider position, then click on Set Priority to change the priority." ) ;

      // Create the priority JSlider - start the priority off at 1
      prioritySlider = new JSlider( JSlider.HORIZONTAL,
                              1, 10, 1 );
      prioritySlider.setMajorTickSpacing( 1 );
      prioritySlider.setPaintTicks( true );
      prioritySlider.addChangeListener(
         new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
               setThePriority.setText( "To set The Priority  " + prioritySlider.getValue() ) ;
               OneNumber = prioritySlider.getValue()  ;
            }
         }
      );

      // Add the instructions and JSlider to the north panel
      priorityPanel.add( setThePriority ) ;
      priorityPanel.add( prioritySlider ) ;
      priorityPanel.add( priorityInfo ) ;


      setSize( width , height ) ;

      setTitle( "Multithreaded Program IV" ) ;


   }

   /** ******************************************************
    *   Called by the MultiThreaded_IV() constructor to start
    *   creating the five Threads. The logic is
    *   1- Create the five threads
    *   2- Call each thread's start method
    *********************************************************/
   public void startThreads() {

      tT1    = new Thread[ LEN]  ;
      tT1[0] = null ;
      //output = new String[3][6]  ;

      if (tT1[0] == null) {
         sysPrint( "startThreads() 1: create to a new Thread." ) ;

         for ( int iii = 0 ; iii < LEN ; iii++ ) {
            tT1[ iii ] = new MyThread( this , iii );
            tT1[ iii ].start();
            sysPrint( "startThreads() 2:  start the new Thread" + iii );
         }
      }
   }

   /** **********************************************************
    *  The actionPerformed() is called to handle button events. It
    *  is called to respond to pressing the
    *  1- Suspend checkbox
    *  2- Set priority checkbox
    *  3- Stopped checkbox
    *************************************************************/
   public synchronized void actionPerformed( ActionEvent e )
   {
      for ( int ii = 0; ii < LEN; ii++ ) {

         if ( e.getSource() == susPend[ ii ] ) {
            if( !isStopped[ ii ] ) { // Do nothing if thread is stopped
               suspended[ ii ] = !suspended[ ii ];
               if ( suspended[ ii ] ) // start wait clock if suspended
                  waitTime[ ii ] = System.currentTimeMillis() ;

               // change label color on suspend/resume
               names[ ii ].setBackground(
                  suspended[ ii ] ? Color.RED : Color.WHITE );

               // if thread resumed, make sure it starts executing
               if ( !suspended[ ii ] ) {
                  notifyAll();
               // Calculate the time thread has been suspended
                  sleepTime[ ii ] = sleepTime[ ii ] +
                         ( System.currentTimeMillis() - waitTime[ ii ] ) / 1000  ;
               }

               ii = ( susPend.length + 1 );
            }
            else {
               JOptionPane.showMessageDialog(null,
                          "You have tried to suspend a thread that has been stopped.\n" +
                          "This action is not allowed.",
                          "Thread is stopped",
                          JOptionPane.INFORMATION_MESSAGE );
               susPend[ ii ].setSelected( false ) ;
            }
         }
         else if ( e.getSource() == SetPri[ ii ] ) {
            SETPriority[ ii ] = !SETPriority[ ii ];
            if( SETPriority[ ii ] ) {
               sysPrint( "The value of SetPriority[ ii ] is " +  SETPriority[ ii ]  ) ;

               priorities[ ii ] = OneNumber ;

               try {
                  Thread ct = Thread.currentThread() ;
                  sysPrint( "The value of priorities[ ii ] is " +  priorities[ ii ] ) ;
                  tT1[ ii ].sleep( ( int ) ( 1000 ) );
                  sleepTime[ ii ] = sleepTime[ ii ] + 1.0 ;
                  sysPrint( "The value of tT1[ ii ] is " +  tT1[ ii ]  + " ii is " + ii  ) ;
                  if( priorities[ ii ] < 1 )
                      priorities[ ii ] = 1  ;
                  ct.setPriority( priorities[ ii ] ) ;
                  Priority[ ii ].setText( "Current priority " +
                              priorities[ ii ] ) ;
                  sysPrint( "The value of ct.getPriority() is " +  ct.getPriority() ) ;
               }
               catch( InterruptedException exception ) {
                  exception.printStackTrace();
               }

               SetPri[ ii ].setSelected( false );

               // if thread resumed, make sure it starts executing
               if ( !SETPriority[ ii ]  )
                  notifyAll();
               SETPriority[ ii ] = !SETPriority[ ii ];
            }
            else {
               SETPriority[ ii ] = !SETPriority[ ii ];
            }
         }
         else if ( e.getSource() == stopped[ ii ] ) {
            displayElapsedTime ("Thread" + ii + " has finished  with an elapsed time of " , ii , 1 );
            stop( ii , tT1[ ii ] ) ;

            // change label color on stopped thread
            stopThread[ ii ].setBackground(
               isStopped[ ii ] ?  Color.WHITE : Color.RED ) ;
            isStopped[ ii ] = true ;
         }
      }

   }
   /***************** End of actionPerformed() method **************/



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
    *  Called by the displayElapsedTime() method to inform this
    *  application that it should stop the thread indicated by
    *  the thread id specified by ii.
    *  1- ii = Thread ID
    *  2- currentThread = thread reference
    *********************************************************/
   public void stop( int ii , Thread currentThread ) {

      sysPrint( "We are in the stop() method to stop running Thread" + ii ) ;

      destroy( ii ) ;
      tT1[ ii ] = null ;
   }


   /** ******************************************************
    *  Called by the stop() method to inform Java runtime
    *  that thread is no longer being used and it is ready to
    *  be reclaimed and that it should     *  destroy any
    *  resources that it has allocated. Set each
    *  thread to null to cause the garbage collector to reclaim
    *  its resources.
    *********************************************************/
   public void destroy( int ii ) {

      sysPrint( "We are in the destroy() method to clean up before being removed." );

      tT1[ ii ] = null ;

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

      String str  = "" , str2 , str3 ;
      int subIndex = 0 ;
      elapsedTime = System.currentTimeMillis() - startTime[ ii ] ;

      sysPrint ( message + " at " +
               ( elapsedTime / 1000.0) + " seconds");

      if ( aa == 0 ) {
         startTime[ ii ] = System.currentTimeMillis() ;
         names[ ii ].setText( "Thread" + ii ) ;
         startTimes[ ii ].setText( cal.get( cal.HOUR )  + ":" +
                          cal.get( cal.MINUTE ) + ":" +  cal.get( cal.SECOND ) ) ;
         sysPrint ( "displayElapsedTime() 1: method  " +
                          "Thread" + ii + " Start time is " + cal.get( cal.HOUR )  + ":" +
                          cal.get( cal.MINUTE ) + ":" +
                          cal.get( cal.SECOND ) + "\n\n" +
                          "Thread" + ii + "\n\n" +
                          "The Thread priority is " + tT1[ ii ].getPriority() + "\n") ;

         Priority[ ii ].setText( ""  + tT1[ ii ].getPriority() ) ;
      }
      else if ( aa == 1 ) {
            elaspedTimes[ ii ].setText( "" +  ( elapsedTime / 1000.0) + " secs"  ) ;
            sysPrint ( "displayElapsedTime() 2: the elapsed running time.\n" +
                          "Elapsed time is " + ( elapsedTime / 1000.0) +
                          " for Thread" + ii + "\n\n" +
                          "The Thread priority is " + tT1[ ii ].getPriority() + "\n" ) ;
            Priority[ ii ].setText( "" + tT1[ ii ].getPriority() ) ;
            str = "" + (( elapsedTime / 1000.0) - sleepTime[ ii ] ) ;
                    //    sysPrint ( "The value of str is " + str ) ;
            subIndex  = str.indexOf( ".") ;
                    //    sysPrint ( "The value of subIndex is " + subIndex ) ;
            str2 = str.substring( subIndex , subIndex+3 ) ;
                    //   sysPrint ( "The value of str2 is " + str2 ) ;
            str3 = str.substring( 0 , subIndex ) ;
            sysPrint ( "The value of sleepTime[ ii ] is " + sleepTime[ ii ] ) ;
            runTimes[ ii ].setText( str3 + str2 + " secs") ;
      }
   }

   /** ***********************************************************
    * The sysExit() method is called in response to a close
    * application event.
    ************************************************************* */
   public void sysExit( int ext ) {
      for( int ii = 0 ; ii < LEN ; ii++ ) {
         tT1[ ii ] = null ;
      }

      System.exit( ext ) ;
   }

   /**  ****************************************************
    * Set the width of frame to 600 if w less than 0
    ***************************************************** */
   public void setWidth( int w )
      { width = ( w >= 0 ? w : 600 ); }

   /** ****************************************************
    * Set the height to 600 if h less than 0
    ***************************************************** */
   public void setappMT( MultiThreaded_IV app_MT )
      { appMT = app_MT ; }

   /** ****************************************************
    * Set the height to 600 if h less than 0
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
      final MultiThreaded_IV app = new MultiThreaded_IV();
      app.setappMT( app ) ;

      app.addWindowListener(
         new WindowAdapter() {
            public void windowClosing( WindowEvent e )  {
               app.sysExit( 0 );
            }
         } // End of WindowAdapter()
      );  // End of addWindowListener
   }

   /** *********************************************************
    * A thread is a thread of execution in a program. The Java
    * Virtual Machine allows an application to have multiple
    * threads of execution running concurrently.
    **************************************************************/
   public class MyThread extends Thread {
      MultiThreaded_IV multi ;
      int index ;
      boolean isAlive = true ;

      public MyThread( MultiThreaded_IV MTL_C , int in_dex ) {
         multi =  MTL_C ;
         index =  in_dex ;
         sysPrint( "MyThread() 1: for Thread " + index ) ;
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
      public void run() {
         int ii = 0 ;

         final Thread currentThread = Thread.currentThread() ;


         for ( ii = 0; ii < LEN ; ii++ ) {
            if ( currentThread == tT1[ ii ] ) {
               sysPrint( "run() 1: The value of ii is " + ii ) ;
               break ;
            }
         }


         final int index = ii ;

         displayElapsedTime( currentThread.getName() + " has started." , ii , 0 );
         sysPrint ( "run() 2: method to start running a Thread" + index  + "\n" ) ;

         // By default, update display every half second
         while (currentThread == tT1[ index  ] && ii < LEN ) {
            repaint();
            tT1[ index  ] = currentThread ;
            try {
               sysPrint ( "run() 3: method to cause the current Thread" + index + " to sleep.\n" ) ;

               double st = ( double )( Math.random() * 1000 )     ;
               tT1[ index ].sleep( (long)st );
               sleepTime[ index ] = sleepTime[ index ] + st / 1000 ;

               sysPrint ( "run() 4: Thread" + index + " Waking up from." )  ;
               sysPrint ( "run() 4b: Thread" + index + "  tT1[ index ] " + tT1[ index ] )  ;

               sysPrint ( "run() 5: Thread" + index + " sleep time is " + (double)( st / 1000 )  + " seconds." )  ;

               //ii = 10 ;
            }
            catch (InterruptedException e) {
            }

            ii++ ;
            sysPrint( "run() 6: Loop counter is " + ii + " for Thread" + index ) ;
         }   // End of while loop
      }
   }
}

