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
      graph.putIfAbsent(parts[0], new ArrayList<>());
    }

    for (String line : adjacencies) {
      String[] parts = line.split(",");
      for (int i = 1; i < parts.length; i++) {
        graph.get(parts[0]).add(parts[i]);
      }
    }
  }

  public boolean isCountry(String country) {
    return countries.containsKey(country);
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    while (true) {
      String countryName = Utils.scanner.nextLine();
      countryName = Utils.capitalizeFirstLetterOfEachWord(countryName);
      if (isCountry(countryName)) {
        Country country = countries.get(countryName);
        MessageCli.COUNTRY_INFO.printMessage(country.getName(), country.getContinent(),
            String.valueOf(country.getTax()));
        break;
      } else {
        MessageCli.INVALID_COUNTRY.printMessage(countryName);
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
  }
}
