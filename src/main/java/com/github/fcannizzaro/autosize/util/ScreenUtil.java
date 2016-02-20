package com.github.fcannizzaro.autosize.util;

import java.awt.*;

/**
 * @author Francesco Cannizzaro (fcannizzaro)
 */
public class ScreenUtil {

    public static int width;
    public static int height;

    public static void init() {
        DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        width = displayMode.getWidth();
        height = displayMode.getHeight();
    }
}
