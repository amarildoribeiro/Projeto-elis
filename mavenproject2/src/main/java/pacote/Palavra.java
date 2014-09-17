/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacote;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Amarildo Ribeiro
 */
public class Palavra {
    
    private int tipo_consulta;
    private String palavra_pesquisa;
    private String palavra_pt;
    private String palavra_elis;
    
    
    
    public Palavra() {
    }
    
    public Palavra(int tipo_consulta, String palavra_pesquisa ,String palavra, String definicao) {
    this.tipo_consulta = tipo_consulta;    
    this.palavra_pesquisa = palavra_pesquisa;
    this.palavra_pt = palavra;
    this.palavra_elis = definicao;
    }

    public String getPalavra_pt() {
        return palavra_pt;
    }

    public void setPalavra_pt(String palavra_pt) {
        this.palavra_pt = palavra_pt;
    }

    public String getPalavra_elis() {
        return palavra_elis;
    }

    public void setPalavra_elis(String palavra_elis) {
        this.palavra_elis = palavra_elis;
    }
    
    public JSONObject pesquisa (String p_palavra, int p_tipo_consulta) throws JSONException{
       
        JSONObject json = new JSONObject();
        
        List<String> lista_pt = new ArrayList<String>();
        
        List<String> lista_elis = new ArrayList<String>();
        
        if (p_tipo_consulta == 1) { 
            try 
            {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Amarildo Ribeiro\\Documents\\NetBeansProjects\\ElisNew1.db");

                String query = "select palavra_pt, palavra_elis from dicionario where (palavra_pt like '%"+p_palavra+"%' or '"+p_palavra+"' is null)";

                Statement st = connection.createStatement();

                ResultSet rs = st.executeQuery(query);

                while(rs.next())
                 {  
                    lista_pt.add(rs.getString("palavra_pt"));
                    lista_elis.add(rs.getString("palavra_elis"));
                 }


            } 
            catch (Exception e) 
            {
               return json;
            }  
        }
       
        
       if (p_tipo_consulta == 2) { // consulta definição em portugues, passando palavra_pt em ingles
          try 
          {
             Class.forName("org.sqlite.JDBC");
             Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Amarildo Ribeiro\\Documents\\NetBeansProjects\\ElisNew1.db");

             String query = "select palavra_pt, palavra_elis from dicionario where (palavra_elis like '%"+p_palavra+"%' or '"+p_palavra+"' is null)";

             Statement st = connection.createStatement();

             ResultSet rs = st.executeQuery(query);

             while(rs.next())
              {  
                 lista_pt.add(rs.getString("palavra_pt"));
                 lista_elis.add(rs.getString("palavra_elis"));
              }
             st.close();

             connection.close();

         } 
         catch (Exception e) 
         {
            return json;
         }  
        }
       
       json.put("palavra_elis",lista_elis); // de array para Strig
       json.put("palavra_pt",lista_pt); // de array para Strig
       
       
       return json;
        
    }
    
    
    public String AdicionaPalavra (String p_palavra_pt, String p_palavra_elis){
    try 
        {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Amarildo Ribeiro\\Documents\\NetBeansProjects\\Elis.db");

            String query = "insert into dicionario(palavra_pt, palavra_elis) values("+p_palavra_pt+","+p_palavra_elis+")";

            Statement st = connection.createStatement();
            
            st.executeUpdate(query);
            
            st.close();
           
            connection.close();
        
        } 
        catch (Exception e) 
        {
           return "Erro ao inserir dados "+e;        
        }  
        
    return "Dados inseridos com sucesso.";
    }
       
      
    
     public String remove_palavra (String p_palavra_pt, String p_palavra_elis){
       
        if (p_palavra_elis.length() > 0 && p_palavra_pt.length() == 0) {  // exclui palavra_pt passando como parametro palavra_pt em ingles
            try 
            {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Amarildo Ribeiro\\Documents\\NetBeansProjects\\Elis.db");

                String query = "delete from dicionario where palavra_elis = "+p_palavra_elis;

                Statement st = connection.createStatement();

                st.executeUpdate(query);
            
                st.close();
                
                connection.close();

            } 
            catch (Exception e) 
            {
               return "Erro. "+e;
            }  
        }
       
        
       if (p_palavra_pt.length() > 0 && p_palavra_elis.length()== 0) { // exclui palavra_pt passando como parametro palavra_pt em portugues
           try 
            {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Amarildo Ribeiro\\Documents\\NetBeansProjects\\Elis.db");

                
                String query = "delete from dicionario where palavra_pt = "+p_palavra_pt;
                
                Statement st = connection.createStatement();

                st.executeUpdate(query);
            
                st.close();
                
                connection.close();
                

            } 
            catch (Exception e) 
            {
               return "Erro. "+e;
            }  
        }
       
       
       if (p_palavra_elis.length() > 0 && p_palavra_pt.length() > 0) { // exclui palavra_pt passando como parametro palavra_pt em portugues
           try 
            {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Amarildo Ribeiro\\Documents\\NetBeansProjects\\Dicionario.db");
                
                String query = "delete from dicionario where palavra_pt = "+p_palavra_pt+" 'and palavra_elis ="+p_palavra_elis+";";
                
                Statement st = connection.createStatement();

                st.executeUpdate(query);
            
                st.close();
                
                connection.close();

            } 
            catch (Exception e) 
            {
               return "Erro. "+e;
            }  
        }
     
       return "Exclusão realizada com sucesso.";
     }
    
    
}
