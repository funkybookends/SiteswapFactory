package com.ignoretheextraclub.siteswapfactory.diagram;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestName;

public class VisualTestRule extends ExternalResource
{
	private static final String VISUAL_TESTS_DIRECTORY = "src/test/resources/visualtests";
	private static final String XML_EXTENSION = ".html";
	private final File visualTestDirectory;

	public VisualTestRule()
	{
		visualTestDirectory = new File(VISUAL_TESTS_DIRECTORY);
	}

	@Override
	protected void before() throws Throwable
	{
		if (!visualTestDirectory.exists())
		{
			visualTestDirectory.mkdir();
		}
	}

	public void save(final Class<?> clazz, final TestName testName, final SVGGraphics2D svgGraphics2D) throws URISyntaxException, IOException
	{
		final String testDirectoryName = clazz.getSimpleName();
		final String fileName = testName.getMethodName();
		final String content = svgGraphics2D.getSVGDocument();

		final String uri = VISUAL_TESTS_DIRECTORY + File.separator + testDirectoryName;

		final File file = new File(uri);
		file.mkdirs();

		final File testContentFile = new File(file, fileName + XML_EXTENSION);
		testContentFile.createNewFile();

		try (final FileOutputStream fileOutputStream = new FileOutputStream(testContentFile))
		{
			fileOutputStream.write(content.getBytes());
		}
		catch (final IOException cause)
		{
			throw new RuntimeException(cause);
		}
	}
}
