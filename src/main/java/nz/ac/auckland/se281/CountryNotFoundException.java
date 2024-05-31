package nz.ac.auckland.se281;

/**
 * Custom exception for when a country is not found in the graph.
 */
public class CountryNotFoundException extends Exception {

  /**
   * Constructs a new exception with the country that was not found.
   * 
   * @param country The name of the country that was not found.
   */
  public CountryNotFoundException(String country) {
    super("Country not found: " + country);
  }
}
