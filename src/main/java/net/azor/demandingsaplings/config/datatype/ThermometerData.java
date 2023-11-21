package net.azor.demandingsaplings.config.datatype;

public class ThermometerData {

    private boolean preciseReading; // If true, the thermometer will display simple temperature readings, eg. "Freezing", "Cold", "Hot",
    //otherwise, it displays precise values depending on readingMode, either celsius, fahrenheit, or both.
    private ReadingMode readingMode;

    public ThermometerData (boolean preciseReading, ReadingMode readingMode) {
        this.preciseReading = preciseReading;
        this.readingMode = readingMode;
    }

    public boolean getPreciseReading() {
        return preciseReading;
    }

    public ReadingMode getReadingMode() {
        return readingMode;
    }

    public enum ReadingMode {
        BOTH,
        CELSIUS,
        FAHRENHEIT
    }
}
