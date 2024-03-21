/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.common.utility;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author Iroshan
 */
public class WindowLocation
{

    public static void setWindowPosition (JFrame frame)
    {

        Dimension dimension = Toolkit.getDefaultToolkit ().getScreenSize ();
        int x = (int) ((dimension.getWidth () - frame.getWidth ()) / 2);
        int y = (int) ((dimension.getHeight () - frame.getHeight ()) / 2);
        frame.setLocation (x, y);

    }

    public static void setJinternalFrameToCenter (JDesktopPane desktopPane, JInternalFrame frameToCenter)
    {
        Dimension desktopSize = desktopPane.getSize ();
        Dimension jInternalFrameSize = frameToCenter.getSize ();
        frameToCenter.setLocation ((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
    }

    public static void setJinternalFrameToCenter (JDesktopPane desktopPane, JDialog dialogCenter)
    {
        Dimension desktopSize = desktopPane.getSize ();
        Dimension jInternalFrameSize = dialogCenter.getSize ();
        dialogCenter.setLocation ((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
    }

    public static void setJDialogToCenter (JFrame jframe, JDialog dialogCenter)
    {

        int x = jframe.getX ();
        int y = jframe.getY ();

        Dimension desktopSize = jframe.getSize ();
        Dimension jInternalFrameSize = dialogCenter.getSize ();
        dialogCenter.setLocation ((((desktopSize.width - jInternalFrameSize.width) / 2) + x), (((desktopSize.height - jInternalFrameSize.height) / 2) + y));

    }


    public static void setMaiximizedFrame (JFrame framePara)
    {
        JFrame frame = framePara;
        frame.setExtendedState (JFrame.MAXIMIZED_BOTH);

        Dimension dimension = Toolkit.getDefaultToolkit ().getScreenSize ();
        int x = (int) ((dimension.getWidth () - frame.getWidth ()) / 2);
        int y = (int) ((dimension.getHeight () - frame.getHeight ()) / 2);


//        
//        Dimension desktopSize = desktopPane.getSize ();
//        Dimension jInternalFrameSize = dialogCenter.getSize ();
//        dialogCenter.setLocation ((desktopSize.width - jInternalFrameSize.width) / 2,
//                (desktopSize.height - jInternalFrameSize.height) / 2);

    }

public static void setMaiximizedMainFrame (JFrame framePara)
    {
        JFrame frame = framePara;
        frame.setExtendedState (JFrame.MAXIMIZED_BOTH);

        Dimension dimension = Toolkit.getDefaultToolkit ().getScreenSize ();
        int x = (int) ((dimension.getWidth () - frame.getWidth ()) / 2);
        int y = (int) ((dimension.getHeight () - frame.getHeight ()) / 2);


//        
//        Dimension desktopSize = desktopPane.getSize ();
//        Dimension jInternalFrameSize = dialogCenter.getSize ();
//        dialogCenter.setLocation ((desktopSize.width - jInternalFrameSize.width) / 2,
//                (desktopSize.height - jInternalFrameSize.height) / 2);

    }


}
