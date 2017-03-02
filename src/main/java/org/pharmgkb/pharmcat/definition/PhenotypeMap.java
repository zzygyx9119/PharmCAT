package org.pharmgkb.pharmcat.definition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import org.pharmgkb.pharmcat.definition.model.GenePhenotype;


/**
 * This class loads and manages the data from the gene phenotypes file
 *
 * @author Ryan Whaley
 */
public class PhenotypeMap {

  private List<GenePhenotype> m_genes;

  /**
   * public constructor, loads the data from a local file
   */
  public PhenotypeMap() throws Exception {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("gene.phenotypes.json")))) {

      Gson gson = new Gson();
      m_genes = Arrays.asList(gson.fromJson(reader, GenePhenotype[].class));

    } catch (IOException e) {
      throw new Exception("Error reading phenotype definitions", e);
    }
  }

  protected List getGenes() {
    return m_genes;
  }

  /**
   * Lookup and return the GenePhenotype object for the given gene symbol
   * @param gene an HGNC gene symbol
   */
  public GenePhenotype lookup(String gene) {
    return m_genes.stream()
        .filter(p -> gene.equals(p.getGene()))
        .reduce((a, b) -> {
          throw new RuntimeException("more than one gene found");
        }).orElse(null);
  }
}