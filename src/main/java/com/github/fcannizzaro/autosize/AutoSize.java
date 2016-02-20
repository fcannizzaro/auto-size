package com.github.fcannizzaro.autosize;

import com.github.fcannizzaro.autosize.annotations.*;
import com.github.fcannizzaro.autosize.util.ScreenUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.*;

/**
 * @author Francesco Cannizzaro (fcannizzaro)
 */
@SuppressWarnings("unused")
public class AutoSize {

    private static double scaleFactor = 1;
    private static Logger logger = Logger.getLogger("AutoSize");

    public static void bind(Object instance) {

        Field[] fields = instance.getClass().getDeclaredFields();

        AutoConstruct construct = instance.getClass().getAnnotation(AutoConstruct.class);

        for (Field field : fields) {

            field.setAccessible(true);

            Ignore ignore = field.getAnnotation(Ignore.class);
            AutoScale scale = field.getAnnotation(AutoScale.class);
            AutoFont font = field.getAnnotation(AutoFont.class);
            AutoValue value = field.getAnnotation(AutoValue.class);
            AutoMargin margin = field.getAnnotation(AutoMargin.class);

            try {

                // if is a value scale it
                if (value != null) {
                    annotateAutoValue(field, instance);
                    continue;
                }

                // if class construction annotation is present create a new instance
                if (construct != null && ignore == null && Component.class.isAssignableFrom(field.getType()))
                    field.set(instance, field.getType().newInstance());

                // if is not a component subclass skip field
                if (!Component.class.isAssignableFrom(field.getType()))
                    continue;

                Component component = (Component) field.get(instance);

                // apply dimension scaling
                if (scale != null)
                    annotateAutoScale(component, scale);

                // apply font scaling
                if (font != null)
                    annotateAutoFont(component, font);

                // apply margin if class <= JPanel
                if (margin != null && component instanceof JPanel)
                    annotateAutoMargin((JComponent) component, margin);

            } catch (Exception e) {
                logger.log(Level.WARNING, "Cannot bind this field : " + field.getName());
            }

        }

    }

    /**
     * Get scaled dimension
     */
    private static int scaled(int dimension) {
        return (int) (ceil(dimension * scaleFactor));
    }

    private static int scaled(double dimension) {
        return (int) (ceil(dimension * scaleFactor));
    }

    private static int scaled(float dimension) {
        return (int) (ceil(dimension * scaleFactor));
    }

    private static int scaled(long dimension) {
        return (int) (ceil(dimension * scaleFactor));
    }

    /**
     * Scale component by factor
     */
    private static void annotateAutoScale(Component component, AutoScale scale) {

        Dimension dimension = new Dimension(scaled(scale.width()), scaled(scale.height()));

        char[] as = scale.as().toCharArray();

        if (as[0] == '1')
            component.setMinimumSize(dimension);

        if (as[1] == '1')
            component.setPreferredSize(dimension);

        if (as[2] == '1')
            component.setMaximumSize(dimension);

        component.setSize(dimension);

    }

    /**
     * Scale font by factor
     */
    private static void annotateAutoFont(Component component, AutoFont font) {
        float original = (float) font.value();
        component.setFont(component.getFont().deriveFont((float) ceil(scaleFactor * original)));
    }

    /**
     * Scale font by factor
     */
    private static void annotateAutoValue(Field field, Object instance) {

        try {

            Class type = field.getType();
            Object value = field.get(instance);

            if (int.class.isAssignableFrom(type))
                field.set(instance, scaled((Integer) value));

            else if (float.class.isAssignableFrom(type))
                field.set(instance, scaled((Float) value));

            else if (double.class.isAssignableFrom(type))
                field.set(instance, scaled((Double) value));

            else if (long.class.isAssignableFrom(type))
                field.set(instance, scaled((Long) value));

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * Scale and apply margin with empty border
     */
    private static void annotateAutoMargin(JComponent component, AutoMargin annotation) {

        int margin = (int) (annotation.value() * scaleFactor);

        Border empty = new EmptyBorder(margin, margin, margin, margin);

        if (margin == 0)
            empty = new EmptyBorder(annotation.t(), annotation.l(), annotation.b(), annotation.r());

        component.setBorder(BorderFactory.createCompoundBorder(empty, component.getBorder()));

    }

    /**
     * Define initial resolution (workspace) and calc scale factor
     */
    public static void from(int initialWidth, int initialHeight) {

        // get screen resolution
        ScreenUtil.init();

        // get diagonal
        double originalDiagonal = sqrt(pow(initialWidth, 2) + pow(initialHeight, 2));
        double finalDiagonal = sqrt(pow(ScreenUtil.width, 2) + pow(ScreenUtil.height, 2));

        // get scaling factor
        scaleFactor = finalDiagonal / originalDiagonal;

    }

}
