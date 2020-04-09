package br.infnet.edu.jdbc;

import br.infnet.edu.propriedades.LeitorDePropriedadesJDBC;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    private static void setProperties(){
        LeitorDePropriedadesJDBC props = new LeitorDePropriedadesJDBC("/conexao.properties");
        url = props.getValor("url");
        username = props.getValor("username");
        password = props.getValor("password");
        driver = props.getValor("driver");
    }

    public static Connection conectar(){
        setProperties();
        Connection retorno = null;

        try{
            Class.forName(driver);
            retorno = DriverManager.getConnection(url, username, password);

        } catch (SQLException | NullPointerException | ClassNotFoundException ex) {
            System.out.println("ERRO: Não foi possível conectar.");
        }

        return retorno;
    }
}
