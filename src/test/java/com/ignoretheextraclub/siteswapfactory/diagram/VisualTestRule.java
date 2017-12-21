package com.ignoretheextraclub.siteswapfactory.diagram;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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

	public void test(final Class<?> clazz, final TestName testName, final SVGGraphics2D svgGraphics2D) throws URISyntaxException, IOException
	{
		final String expectedContent = svgGraphics2D.getSVGDocument();
		final File existingFile = getFile(clazz, testName);

		if (existingFile.exists())
		{
			final String existingContent = FileUtils.readFileToString(existingFile, StandardCharsets.UTF_8);
			assertThat(existingContent).as("existing content did not match: " + existingFile.toURI().toURL() + " please inspect visually.").isEqualTo(expectedContent);
		}
		else
		{
			FileUtils.writeStringToFile(existingFile, expectedContent, StandardCharsets.UTF_8);
			fail("File did not exist - please view to verify visually test has passed: " +
				existingFile.toURI().toURL() // See IntelliJ plugin https://plugins.jetbrains.com/plugin/7183-output-link-filter for clickable links
			);
		}
	}

	private File getFile(final Class<?> clazz, final TestName testName)
	{
		final String testDirectoryName = clazz.getSimpleName();
		final String fileName = testName.getMethodName();

		final String uri = VISUAL_TESTS_DIRECTORY + File.separator + testDirectoryName;

		final File file = new File(uri);
		return new File(file, fileName + XML_EXTENSION);
	}
}
