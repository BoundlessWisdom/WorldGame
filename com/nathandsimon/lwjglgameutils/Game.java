package com.nathandsimon.lwjglgameutils;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;
/**
 * The basic framework for use with my LWJGL tutorials.
 *
 * @author Sri Harsha Chilakapati
 */
public abstract class Game
{
    // Single instance is allowed
    private static Game instance;
    // Delta time
    private static long delta;
    private ArrayList<EngineComponent> components = new ArrayList<EngineComponent>();
    private ArrayList<IObject> objs = new ArrayList<IObject>();
    private PhysicsEngine phys = new PhysicsEngine();
    private RenderingEngine ren;
    /**
     * A simple Game
     */
    public Game()
    {
        try
        {
            instance = this;
            Display.create();
            setDisplayMode(800, 600);
            Display.setVSyncEnabled(true);
            Display.setResizable(true);
            gameLoop();
        }
        catch (LWJGLException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    public static Game getInstance()
    {
    	return instance;
    }
    // The gameloop. Runs at 60 fps
    private void gameLoop()
    {
        long lastFrame = getCurrentTime();
        long thisFrame = getCurrentTime();

        init();

        while (!Display.isCloseRequested())
        {
            thisFrame = getCurrentTime();

            setDelta(thisFrame - lastFrame);

            update(getDelta());

            Display.update();

            if (Display.wasResized())
            {
                resized();
            }

            Display.sync(60);

            lastFrame = thisFrame;
        }

        end();
    }

    /**
     * Switch the fullscreen state.
     */
    public static void switchFullscreen()
    {
        setFullscreen(!Display.isFullscreen());
    }

    /**
     * Sets the fullscreen state.
     */
    public static void setFullscreen(boolean fullscreen)
    {
        setDisplayMode(Display.getDisplayMode(), fullscreen);
    }

    /**
     * Sets a DisplayMode.
     *
     * @param mode       The DisplayMode.
     * @param fullscreen The fullscreen state.
     */
    public static boolean setDisplayMode(DisplayMode mode, boolean fullscreen)
    {
        return setDisplayMode(mode.getWidth(), mode.getHeight(), fullscreen);
    }

    /**
     * Sets a DisplayMode.
     */
    public static boolean setDisplayMode(DisplayMode mode)
    {
        return setDisplayMode(mode, false);
    }

    /**
     * Sets a windowed DisplayMode.
     *
     * @param width  The width of the display.
     * @param height The height of the display.
     */
    public static boolean setDisplayMode(int width, int height)
    {
        return setDisplayMode(width, height, false);
    }

    /**
     * Sets a DisplayMode after selecting for a better one.
     *
     * @param width      The width of the display.
     * @param height     The height of the display.
     * @param fullscreen The fullscreen mode.
     *
     * @return True if switching is successful. Else false.
     */
    public static boolean setDisplayMode(int width, int height, boolean fullscreen)
    {
        // return if requested DisplayMode is already set
        if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height) && (Display.isFullscreen() == fullscreen))
            return true;

        try
        {
            // The target DisplayMode
            DisplayMode targetDisplayMode = null;

            if (fullscreen)
            {
                // Gather all the DisplayModes available at fullscreen
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                // Iterate through all of them
                for (DisplayMode current : modes)
                {
                    // Make sure that the width and height matches
                    if ((current.getWidth() == width) && (current.getHeight() == height))
                    {
                        // Select the one with greater frequency
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq))
                        {
                            // Select the one with greater bits per pixel
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel()))
                            {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }

                        // if we've found a match for bpp and frequency against
                        // the
                        // original display mode then it's probably best to go
                        // for this one
                        // since it's most likely compatible with the monitor
                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency()))
                        {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            }
            else
            {
                // No need to query for windowed mode
                targetDisplayMode = new DisplayMode(width, height);
            }

            if (targetDisplayMode == null)
            {
                System.err.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
                return false;
            }

            // Set the DisplayMode we've found
            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);

            System.out.println("Selected DisplayMode: " + targetDisplayMode.toString());

            // Generate a resized event
            instance.resized();

            return true;
        }
        catch (LWJGLException e)
        {
            System.err.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
        }

        return false;
    }

    /**
     * @return Current time in milliseconds.
     */
    public static long getCurrentTime()
    {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    /**
     * Properly terminate the game.
     */
    public static void end()
    {
        instance.dispose();
        instance = null;
        Display.destroy();
        System.exit(0);
    }

    /**
     * Load any resources here.
     */
    public void init()
    {
    	ren = new RenderingEngine();
    	addEngineComponent(phys);
		addEngineComponent(ren);
    }

    /**
     * Update the logic of the game.
     *
     * @param elapsedTime Time elapsed since last frame.
     */
    public void update(long elapsedTime)
    {
    	phys.run(elapsedTime);
    	ren.run(elapsedTime);
    }

    /**
     * Display is resized
     */
    public void resized()
    {
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
    }

    /**
     * Dispose created resources.
     */
    public void dispose()
    {
    	for(EngineComponent c : getEngineComponents())
		{
			c.dispose();
		}
    }

    public static long getDelta()
    {
        return delta;
    }

    public static void setDelta(long delta)
    {
        Game.delta = delta;
    }
    
    public void addObject(IObject o)
    {
    	for(EngineComponent e : components)
    	{
    		e.add(o);
    	}
    	objs.add(o);
    }
    public void removeObject(IObject o)
    {
    	for(EngineComponent e : components)
    	{
    		e.remove(o);
    	}
    	objs.remove(o);
    }
	public ArrayList<EngineComponent> getEngineComponents()
	{
		return components;
	}
	public void addEngineComponent(EngineComponent component)
	{
		components.add(component);
	}
	public ArrayList<IObject> getObjects()
	{
		return objs;
	}
	public PhysicsEngine getPhysicsEngine()
	{
		return phys;
	}
	public RenderingEngine getRenderingEngine()
	{
		return ren;
	}
}
