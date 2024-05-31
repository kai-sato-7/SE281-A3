package nz.ac.auckland.se281;

import java.util.*;

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

    // Add the adjacencies to the graph.
    for (String line : adjacencies) {
      String[] parts = line.split(",");
      for (int i = 1; i < parts.length; i++) {
        graph.get(parts[0]).add(parts[i]);
      }
    }
  }

  /**
   * Checks if a country exists in the map.

   * @param country The name of the country to check.
   */
  public boolean isCountry(String country) throws CountryNotFoundException {
    if (countries.containsKey(country)) {
      return true;
    }
    throw new CountryNotFoundException(country);
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    while (true) {
      String countryName = Utils.scanner.nextLine();
      countryName = Utils.capitalizeFirstLetterOfEachWord(countryName);
      try {
        if (isCountry(countryName)) {
          // Prints the country information.
          Country country = countries.get(countryName);
          MessageCli.COUNTRY_INFO.printMessage(country.getName(), country.getContinent(),
              String.valueOf(country.getTax()));
          break;
        }
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(countryName);
      }
    }
  }

  /**
   * Finds the shortest path between two countries.

   * @param root   The name of the starting country.
   * @param target The name of the ending country.
   * @return The shortest path between the two countries.
   */
  public List<String> breathFirstTraverse(String root, String target) {
    Queue<String> queue = new LinkedList<>();
    Map<String, List<String>> routes = new HashMap<>();
    queue.add(root);
    routes.put(root, new ArrayList<>());
    routes.get(root).add(root);
    while (!queue.isEmpty()) {
      String node = queue.poll();
      // Iterates over the adjacent countries.
      for (String country : graph.get(node)) {
        if (!routes.containsKey(country)) {
          queue.add(country);
          routes.put(country, new ArrayList<>(routes.get(node)));
          routes.get(country).add(country);
        }
        if (country.equals(target)) {
          return routes.get(target);
        }
      }
    }
    return null;
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    // Gets the starting country input from the user.
    Country startCountry;
    while (true) {
      String startCountryName = Utils.scanner.nextLine();
      startCountryName = Utils.capitalizeFirstLetterOfEachWord(startCountryName);
      try {
        if (isCountry(startCountryName)) {
          startCountry = countries.get(startCountryName);
          break;
        }
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(startCountryName);
      }
    }

    // Gets the ending country input from the user.
    Country endCountry;
    while (true) {
      String endCountryName = Utils.scanner.nextLine();
      endCountryName = Utils.capitalizeFirstLetterOfEachWord(endCountryName);
      try {
        if (isCountry(endCountryName)) {
          endCountry = countries.get(endCountryName);
          break;
        }
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(endCountryName);
      }
    }

    // Prints the route information.
    if (startCountry.equals(endCountry)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
    } else {
      List<String> route = breathFirstTraverse(startCountry.getName(), endCountry.getName());
      MessageCli.ROUTE_INFO.printMessage(route.toString());
      Set<String> continents = new LinkedHashSet<>();
      int taxTotal = -startCountry.getTax();
      for (String country : route) {
        String continent = countries.get(country).getContinent();
        if (!continents.contains(continent)) {
          continents.add(continent);
        }
        taxTotal += countries.get(country).getTax();
      }
      MessageCli.CONTINENT_INFO.printMessage(continents.toString());
      MessageCli.TAX_INFO.printMessage(String.valueOf(Integer.toString(taxTotal)));
    }
  }
}
