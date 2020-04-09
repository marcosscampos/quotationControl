package br.infnet.edu.propriedades;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LeitorDePropriedadesJDBC {
    private final Properties props;

   public LeitorDePropriedadesJDBC(String arquivo) {
       props = new Properties();
       InputStream stream = getClass().getResourceAsStream(arquivo);

       try {
           props.load(stream);
           stream.close();
       } catch (IOException ex) {
           ex.printStackTrace();
       }
   }

    public String getValor(String chave) {
        return props.getProperty(chave);
    }
}
