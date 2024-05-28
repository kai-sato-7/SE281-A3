package nz.ac.auckland.se281;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {

  private Map<String, Country> countries = new HashMap<>();
  private Map<String, List<String>> graph = new HashMap<>();

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    for (String line : countries) {
      String[] parts = line.split(",");
      this.countries.put(parts[0], new Country(parts[0], parts[1], Integer.parseInt(parts[2])));
    }
  }

  public boolean isCountry(String country) {
    return countries.containsKey(country);
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    String country = Utils.scanner.nextLine();

    if (isCountry(country)) {
      Country c = countries.get(country);
      MessageCli.COUNTRY_INFO.printMessage(c.getName(), c.getContinent(), String.valueOf(c.getTax()));
    } else {
      MessageCli.INVALID_COUNTRY.printMessage(country);
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
  }
}
