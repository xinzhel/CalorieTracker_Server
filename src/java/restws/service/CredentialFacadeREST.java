/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restws.AppUser;
import restws.Credential;

/**
 *
 * @author home
 */
@Stateless
@Path("restws.credential")
public class CredentialFacadeREST extends AbstractFacade<Credential> {

    @PersistenceContext(unitName = "CalorieTrackerPU")
    private EntityManager em;

    public CredentialFacadeREST() {
        super(Credential.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Credential entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Credential entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Credential find(@PathParam("id") String id) {
        return super.find(id);
    }

    
    @GET
    @Path("findByUsername/{username}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findByUsername(@PathParam("username") String username) {
        TypedQuery<Credential> q = em.createNamedQuery("Credential.findByUsername", Credential.class);
        q.setParameter("username", username);
        return q.getResultList();
    }
    
    @GET
    @Path("findByHash/{hash}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findByHash(@PathParam("hash") String hash) {
        TypedQuery<Credential> q = em.createNamedQuery("Credential.findByHash", Credential.class);
        q.setParameter("hash", hash);
        return q.getResultList();
    }
    
    @GET
    @Path("findBySignUpDate/{signUpDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findBySignUpDate(@PathParam("signUpDate") String signUpDate) throws ParseException {
        
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(signUpDate); 
        TypedQuery<Credential> q;
        q = em.createNamedQuery("Credential.findBySignUpDate", Credential.class);
        q.setParameter("signUpDate", d);
        return q.getResultList();
    }
    
    @GET
    @Path("findByUserId/{userId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findByUserId(@PathParam("userId") int userId) {
        TypedQuery<Credential> q = em.createNamedQuery("Credential.findByUserId", Credential.class);
        q.setParameter("userId", userId);
        return q.getResultList();
    }
    
    @GET
    @Path("checkByUsernameAndPwdHash/{username}/{pwdHash}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object checkByUsernameAndPwdHash(@PathParam("username") String username, @PathParam("pwdHash") String pwdHash) {
        TypedQuery<Credential> q = em.createNamedQuery("Credential.findByUsername", Credential.class);
        q.setParameter("username", username);
        List<Credential> credentail = q.getResultList();
        String isSame = "false";
        if (username.equals(credentail.get(0).getUsername()) && pwdHash.equals(credentail.get(0).getHash())) {
            isSame = "true";
        }
        JsonObject result = Json.createObjectBuilder().
                add("result", isSame ).build();
        return result;
    }
    
//@POST
//@Path("register")
//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//@Produces({MediaType.APPLICATION_JSON})
//public Object register(@FormParam("username") String username,
//                @FormParam("email") String email,
//                @FormParam("hash") String hash) throws Exception {
//    
//        TypedQuery<AppUser> q = em.createNamedQuery("AppUser.findByEmail", AppUser.class);
//        q.setParameter("email", email);
//        try {
//            AppUser u = q.getResultList().get(0);
//            String password = "";
//            Date date = new Date();
//            Credential c = new Credential();
//
//            c.setUsername(username);
//            c.setUserId(u);
//            c.setSignUpDate(date);
//            c.setHash(hash);
//
//
//            getEntityManager().persist(c);
//            
//            
//        
//            String isSame = "true";
//            JsonObject result = Json.createObjectBuilder().
//                    add("result", isSame ).build();
//            return result;
//        } catch (Exception e) {
//            return "Cannot find the user";
//        }
//        
//    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
