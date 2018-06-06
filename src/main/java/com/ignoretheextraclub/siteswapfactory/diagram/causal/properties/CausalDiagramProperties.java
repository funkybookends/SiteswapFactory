package com.ignoretheextraclub.siteswapfactory.diagram.causal.properties;

import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

public class CausalDiagramProperties
{
	private static final double FACTOR = 5;

	/**
	 * The maximum number of throws to display horizontally by a single juggler. This should be respected even if the {@link #MIN_NUM_HALF_ROTATIONS} has not been met.
	 */
	public static final String MAX_NUM_THROWS_DISPLAYED = "MAX_NUM_THROWS_DISPLAYED";

	private static int DEFAULT_MAX_NUM_THROWS_DISPLAYED = 15;

	/**
	 * The minimum number of half rotations to display.
	 */
	public static final String MIN_NUM_HALF_ROTATIONS = "MIN_NUM_HALF_ROTATIONS";

	private static int DEFAULT_MIN_NUM_HALF_ROTATIONS = 2;

	/**
	 * The preferred number of throws to display per juggler. If all minimums have been met, then once this number has been reached, it is deemed that no more should be displayed.
	 */
	public static final String PREFERRED_NUM_THROWS = "PREFERRED_NUM_THROWS";

	private static int DEFAULT_PREFERRED_NUM_THROWS = 10;

	/**
	 * The distance between a single beat. This is a double, but the interpretation is left to the implementer.
	 */
	public static final String DISTANCE_BETWEEN_BEATS = "DISTANCE_BETWEEN_BEATS";

	private static double DEFAULT_DISTANCE_BETWEEN_BEATS = 15 * FACTOR;

	/**
	 * The distance between jugglers. This is a double, but the interpretation is left to the implementer.
	 */
	public static final String DISTANCE_BETWEEN_JUGGLERS = "DISTANCE_BETWEEN_JUGGLERS";

	private static double DEFAULT_DISTANCE_BETWEEN_JUGGLERS = 12 * FACTOR;

	/**
	 * The length of the arrow head. This is a double, but the interpretation is left to the implementer.
	 */
	public static final String DISTANCE_FOR_ARROW_BEND = "DISTANCE_FOR_ARROW_BEND";

	private static double DEFAULT_DISTANCE_FOR_ARROW_BEND = 8 * FACTOR;

	/**
	 * The distance to translate two swaps that overlap. This is a double, but the interpretation is left to the implementer.
	 */
	public static final String DISTANCE_BETWEEN_OVERLAPPING_SWAPS = "DISTANCE_BETWEEN_OVERLAPPING_SWAPS";

	private static double DEFAULT_DISTANCE_BETWEEN_OVERLAPPING_SWAPS = FACTOR;

	/**
	 * The width of any lines in the diagram, such as the cause, and swap circle {@link #SWAP_DRAW_CIRCLE}
	 */
	public static final String LINE_WIDTH = "LINE_WIDTH";

	private static double DEFAULT_LINE_WIDTH = 0.25 * FACTOR;

	/**
	 * The length of the arrow head. This is a double, but the interpretation is left to the implementer.
	 */
	public static final String ARROW_HEAD_LENGTH = "ARROW_HEAD_LENGTH";

	private static double DEFAULT_ARROW_HEAD_LENGTH = 2 * FACTOR;

	/**
	 * The pointyness of the arrowhead. If the slides of a slice of cake were used to draw the arrow, then this number represents the number of slices of cake. A higher value makes them more pointy.
	 */
	public static final String ARROW_HEAD_POINTYNESS = "ARROW_HEAD_POINTYNESS";

	private static double DEFAULT_ARROW_HEAD_POINTYNESS = 8.0;

	/**
	 * If a circle should be drawn around the swap label.
	 */
	public static final String SWAP_DRAW_CIRCLE = "SWAP_DRAW_CIRCLE";

	private static boolean DEFAULT_SWAP_DRAW_CIRCLE = true;

	/**
	 * The distance between the swap center and the connection point.
	 *
	 * @see com.ignoretheextraclub.siteswapfactory.diagram.causal.graphics.SwapGraphic#getConnectionPointFor(Point2D)
	 */
	public static final String DISTANCE_BETWEEN_ARROW_AND_SWAP_CENTER = "DISTANCE_BETWEEN_ARROW_AND_SWAP_CENTER";

	private static double DEFAULT_DISTANCE_BETWEEN_ARROW_AND_SWAP_CENTER = 2 * FACTOR;

	/**
	 * The {@link Font} to use for the label.
	 */
	public static final String LABEL_FONT = "LABEL_FONT";

	private static Font DEFAULT_LABEL_FONT = new Font("Arial", Font.PLAIN, (int) (3 * FACTOR));

	/**
	 * The offset to move the label in the y axis from the center.
	 */
	public static final String LABEL_YOFFSET_DISTANCE = "LABEL_YOFFSET_DISTANCE";

	private static double DEFAULT_LABEL_YOFFSET_DISTANCE = 1 * FACTOR;

	/**
	 * The offset to move the label in the x axis from the center.
	 */
	public static final String LABEL_XOFFSET_DISTANCE = "LABEL_XOFFSET_DISTANCE";

	private static double DEFAULT_LABEL_XOFFSET_DISTANCE = -1.1 * FACTOR;

	/**
	 * The border between the left hand side of the image and the first swap.
	 */
	public static final String LEFT_BORDER_DISTANCE = "LEFT_BORDER_DISTANCE";

	private static double DEFAULT_LEFT_BORDER_DISTANCE = 8 * FACTOR;

	/**
	 * The border between the top of the image and the first juggler.
	 */
	public static final String TOP_BORDER_DISTANCE = "TOP_BORDER_DISTANCE";

	private static double DEFAULT_TOP_BORDER_DISTANCE = 8 * FACTOR;

	/**
	 * If the full rotation marker should be drawn.
	 */
	public static final String DRAW_FULL_ROTATION_MARKER = "DRAW_FULL_ROTATION_MARKER";

	private static boolean DEFAULT_DRAW_FULL_ROTATION_MARKER = true;

	/**
	 * If the half rotation marker should be drawn.
	 */
	public static final String DRAW_HALF_ROTATION_MARKER = "DRAW_HALF_ROTATION_MARKER";

	private static boolean DEFAULT_DRAW_HALF_ROTATION_MARKER = true;

	/**
	 * The color of the full rotation marker
	 */
	public static final String FULL_ROTATION_MARKER_COLOR = "FULL_ROTATION_MARKER_COLOR";

	private static final Paint DEFAULT_FULL_ROTATION_MARKER_COLOR = new Color(162, 162, 162);

	/**
	 * The color of the half rotation marker
	 */
	public static final String HALF_ROTATION_MARKER_COLOR = "HALF_ROTATION_MARKER_COLOR";

	private static final Paint DEFAULT_HALF_ROTATION_MARKER_COLOR = new Color(190, 190, 190);

	private final Map<String, Object> properties = new HashMap<>();

	public CausalDiagramProperties()
	{
		Arrays.stream(this.getClass().getDeclaredFields())
			.filter(field -> isStatic(field.getModifiers()) && field.getType() == String.class && isPublic(field.getModifiers()))
			.forEach(this::addPropertyToProperties);
	}

	private void addPropertyToProperties(final Field field)
	{
		try
		{
			final Field defaultValueField = this.getClass().getDeclaredField("DEFAULT_" + field.getName());
			properties.put(field.getName(), defaultValueField.get(this));
		}
		catch (NoSuchFieldException | IllegalAccessException e)
		{
			//ignored
		}
	}

	public double getDouble(final String key)
	{
		return (double) properties.get(key);
	}

	public boolean is(final String key)
	{
		return (boolean) properties.get(key);
	}

	public Font getFont(final String key)
	{
		return (Font) properties.get(key);
	}

	public int getInt(final String key)
	{
		return (int) properties.get(key);
	}

	public Object get(final String key)
	{
		return properties.get(key);
	}

	public void set(final String property, final Object value)
	{
		properties.put(property, value);
	}
}
