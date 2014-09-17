/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacote;


import java.io.IOException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author Amarildo Ribeiro
 */
@Path("/elis")

public class RecursoWeb {
    
    
    // metodo de consulta de palavras
    @GET
    @Path("/consulta")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPalavra(@QueryParam("palavra") String p_palavra, 
                             @QueryParam("tipo_consulta") int p_consulta) throws IOException, JSONException {
       
        Palavra p = new Palavra();
        
        JSONObject json = new JSONObject();
        
        json = p.pesquisa(p_palavra,p_consulta);
        
       
       return json.toString();
    } 
    
    // metodo de inserção de palavras
    
    @POST
    @Path("/addpalavra")
    @Produces("application/json")
    
    public String setPalavra(@FormParam("p_palavra_pt") String p_palavra_pt,
                             @FormParam("p_palavra_elis") String p_palavra_elis){
       
        Palavra p = new Palavra();
        
        String retorno = p.AdicionaPalavra(p_palavra_pt, p_palavra_elis);
        
       return "resultado: "+retorno;
    } 
    @POST
    @Path("/removepalavra")
    @Produces("application/json")
    
    public String removepalavra(@FormParam("p_palavra_pt") String p_palavra_pt,
                                @FormParam("p_palavra_elis") String p_palavra_elis){
       
        Palavra p = new Palavra();
        
        String retorno = p.remove_palavra(p_palavra_pt, p_palavra_elis);
        
        return "resultado: "+retorno;
    }
}
