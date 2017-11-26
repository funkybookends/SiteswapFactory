package com.ignoretheextraclub.siteswapfactory.diagram.causal;

public class CausalDiagramProperties
{
    // Length Properties
    private final int minNumThrowsDisplayed;
    private final int maxMunThrowsDisplayed;
    private final int minNumRepititions;
    private final int preferredNumThrows;

    // Seperation Properties
    private final int pixelsPerBeat;
    private final int pixelsPerJuggler;

    // Line Style
    private final int lineWidth;
    private final boolean arrowAlways;

    // Landing Style
    private final boolean circle;
    private final boolean handAlways;

    private CausalDiagramProperties(final int minNumThrowsDisplayed,
                                    final int maxMunThrowsDisplayed,
                                    final int minNumRepititions,
                                    final int preferredNumThrows,
                                    final int pixelsPerBeat,
                                    final int pixelsPerJuggler,
                                    final int lineWidth,
                                    final boolean arrowAlways,
                                    final boolean circle,
                                    final boolean handAlways)
    {
        this.minNumThrowsDisplayed = minNumThrowsDisplayed;
        this.maxMunThrowsDisplayed = maxMunThrowsDisplayed;
        this.minNumRepititions = minNumRepititions;
        this.preferredNumThrows = preferredNumThrows;
        this.pixelsPerBeat = pixelsPerBeat;
        this.pixelsPerJuggler = pixelsPerJuggler;
        this.lineWidth = lineWidth;
        this.arrowAlways = arrowAlways;
        this.circle = circle;
        this.handAlways = handAlways;
    }

    public CausalDiagramProperties()
    {
        this
            (
                8,
                22,
                2,
                15,
                15,
                10,
                1,
                false,
                false,
                false
            );
    }

    public int getPixelsPerBeat()
    {
        return pixelsPerBeat;
    }

    public int getMinNumThrowsDisplayed()
    {
        return minNumThrowsDisplayed;
    }

    public int getMaxMunThrowsDisplayed()
    {
        return maxMunThrowsDisplayed;
    }

    public int getMinNumRepititions()
    {
        return minNumRepititions;
    }

    public int getPreferredNumThrows()
    {
        return preferredNumThrows;
    }

    public int getLineWidth()
    {
        return lineWidth;
    }

    public boolean isArrowAlways()
    {
        return arrowAlways;
    }

    public boolean isCircle()
    {
        return circle;
    }

    public boolean isHandAlways()
    {
        return handAlways;
    }

    public CausalDiagramProperties setMinNumThrowsDisplayed(final int minNumThrowsDisplayed)
    {
        return new CausalDiagramProperties(minNumThrowsDisplayed,
            maxMunThrowsDisplayed,
            minNumRepititions,
            preferredNumThrows,
            pixelsPerBeat,
            pixelsPerJuggler,
            lineWidth,
            arrowAlways,
            circle,
            handAlways);
    }

    public CausalDiagramProperties setMaxMunThrowsDisplayed(final int maxMunThrowsDisplayed)
    {
        return new CausalDiagramProperties(minNumThrowsDisplayed,
            maxMunThrowsDisplayed,
            minNumRepititions,
            preferredNumThrows,
            pixelsPerBeat,
            pixelsPerJuggler,
            lineWidth,
            arrowAlways,
            circle,
            handAlways);
    }

    public CausalDiagramProperties setMinNumRepititions(final int minNumRepititions)
    {
        return new CausalDiagramProperties(minNumThrowsDisplayed,
            maxMunThrowsDisplayed,
            minNumRepititions,
            preferredNumThrows,
            pixelsPerBeat,
            pixelsPerJuggler,
            lineWidth,
            arrowAlways,
            circle,
            handAlways);
    }

    public CausalDiagramProperties setPreferredNumThrows(final int preferredNumThrows)
    {
        return new CausalDiagramProperties(minNumThrowsDisplayed,
            maxMunThrowsDisplayed,
            minNumRepititions,
            preferredNumThrows,
            pixelsPerBeat,
            pixelsPerJuggler,
            lineWidth,
            arrowAlways,
            circle,
            handAlways);
    }

    public CausalDiagramProperties setLineWidth(final int lineWidth)
    {
        return new CausalDiagramProperties(minNumThrowsDisplayed,
            maxMunThrowsDisplayed,
            minNumRepititions,
            preferredNumThrows,
            pixelsPerBeat,
            pixelsPerJuggler,
            lineWidth,
            arrowAlways,
            circle,
            handAlways);
    }

    public CausalDiagramProperties setArrowAlways(final boolean arrowAlways)
    {
        return new CausalDiagramProperties(minNumThrowsDisplayed,
            maxMunThrowsDisplayed,
            minNumRepititions,
            preferredNumThrows,
            pixelsPerBeat,
            pixelsPerJuggler,
            lineWidth,
            arrowAlways,
            circle,
            handAlways);
    }

    public CausalDiagramProperties setAircle(final boolean circle)
    {
        return new CausalDiagramProperties(minNumThrowsDisplayed,
            maxMunThrowsDisplayed,
            minNumRepititions,
            preferredNumThrows,
            pixelsPerBeat,
            pixelsPerJuggler,
            lineWidth,
            arrowAlways,
            circle,
            handAlways);
    }

    public CausalDiagramProperties setHandAlways(final boolean handAlways)
    {
        return new CausalDiagramProperties(minNumThrowsDisplayed,
            maxMunThrowsDisplayed,
            minNumRepititions,
            preferredNumThrows,
            pixelsPerBeat,
            pixelsPerJuggler,
            lineWidth,
            arrowAlways,
            circle,
            handAlways);
    }
}
