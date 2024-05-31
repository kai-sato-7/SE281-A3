package nz.ac.auckland.se281;

/**
 * Represents a country in the map.
 */
public class Country {
  private String name;
  private String continent;
  private int tax;

  /**
   * Constructs a new country with the given name, continent, and tax.

   * @param name      The name of the country.
   * @param continent The continent where the country is located.
   * @param tax       The tax rate for the country.
   */
  public Country(String name, String continent, int tax) {
    this.name = name;
    this.continent = continent;
    this.tax = tax;
  }

  /**
   * Gets the name of the country.

   * @return The name of the country.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the continent where the country is located.

   * @return The continent where the country is located.
   */
  public String getContinent() {
    return continent;
  }

  /**
   * Gets the tax rate for the country.

   * @return The tax rate for the country.
   */
  public int getTax() {
    return tax;
  }

  /**
   * Sets the name of the country.

   * @param name The name of the country.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the continent where the country is located.

   * @param continent The continent where the country is located.
   */
  public void setContinent(String continent) {
    this.continent = continent;
  }

  /**
   * Sets the tax rate for the country.

   * @param tax The tax rate for the country.
   */
  public void setTax(int tax) {
    this.tax = tax;
  }

  /**
   * Checks if this country is equal to another country.

   * @param country The country to compare.
   * @return True if the countries are equal, false otherwise.
   */
  @Override
  public boolean equals(Object country) {
    Country other = (Country) country;
    return this.name.equals(other.name) && this.continent.equals(other.continent);
  }
}
