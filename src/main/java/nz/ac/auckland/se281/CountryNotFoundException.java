package nz.ac.auckland.se281;

public class CountryNotFoundException extends Exception {
    public CountryNotFoundException(String country) {
        super("Country not found: " + country);
    }
}
