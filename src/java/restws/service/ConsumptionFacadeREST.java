/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restws.Consumption;


import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restws.AppUser;
import restws.Consumption;
import restws.Credential;
import restws.Food;

/**
 *
 * @author home
 */
@Stateless
@Path("restws.consumption")
public class ConsumptionFacadeREST extends AbstractFacade<Consumption> {

    @PersistenceContext(unitName = "CalorieTrackerPU")
    private EntityManager em;

    public ConsumptionFacadeREST() {
        super(Consumption.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Consumption entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Consumption entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Consumption find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("findByDate/{date}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findByDate(@PathParam("date") String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date); 
        TypedQuery<Consumption> q = em.createNamedQuery("Consumption.findByDate", Consumption.class);
        q.setParameter("date", d);
        return q.getResultList();
    }
    
    @GET
    @Path("findByServings/{servings}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findByServings(@PathParam("servings") int servings) {
        TypedQuery<Consumption> q = em.createNamedQuery("Consumption.findByServings", Consumption.class);
        q.setParameter("servings", servings);
        return q.getResultList();
    }
    
    @GET
    @Path("findByUserId/{userId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findByUserId(@PathParam("userId") int userId) {
        TypedQuery<Consumption> q = em.createNamedQuery("Consumption.findByUserId", Consumption.class);
        q.setParameter("userId", userId);
        return q.getResultList();
    }
    
    @GET
    @Path("findByEmailANDDate/{email}/{date}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findByUsedIdANDDate(@PathParam("email") String email, @PathParam("date") String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date); 
        TypedQuery<Consumption> q = em.createNamedQuery("Comsumption.findByEmailANDDate", Consumption.class);
        q.setParameter("email", email);
        q.setParameter("date", d);
        return q.getResultList();
    }
    
    
    @POST
    @Path("saveComsumption")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON})
    public Object saveComsumption(@FormParam("email") String email, @FormParam("foodName") String foodName) throws ParseException {
        int code = 200;
        String message = "Success";
        Logger logger = Logger.getLogger(getClass().getName());
        logger.info("FOODNAME!!!!!" + foodName);
        
        
       // get date
       Date date = new Date();
        
        // check if there exists the food in the same date for the same user
        
        TypedQuery<Consumption> qc = em.createNamedQuery("Comsumption.findByEmailANDDateANDFoodName", Consumption.class);
        qc.setParameter("foodName", foodName);
        qc.setParameter("email", email);
        qc.setParameter("date", date);
        
        if (!qc.getResultList().isEmpty()) {
            Consumption consumption = qc.getResultList().get(0);
            consumption.setServings(consumption.getServings() + 1);
            getEntityManager().merge(consumption);
        } else {
             // get food
            TypedQuery<Food> qf = em.createNamedQuery("Food.findByFoodName", Food.class);
            qf.setParameter("foodName", foodName);
            Food food = qf.getResultList().get(0);


            // get user
            TypedQuery<AppUser> qu = em.createNamedQuery("AppUser.findByEmail", AppUser.class);
            qu.setParameter("email", email);
            AppUser user = qu.getResultList().get(0);
            
            // add to Comsumption table
            Consumption c = new Consumption();
            c.setDate(date);
            logger.info("date.////////////////////////////////////////////////////!!!!!" + date);
            logger.info("food.////////////////////////////////////////////////////!!!!!" + food);
            logger.info("user./////////////////////////////////////////////////////!!!!" + user);
            c.setFoodId(food);
            c.setUserId(user);
            c.setServings(1);
            getEntityManager().persist(c);
            
        }
        JsonObject result = Json.createObjectBuilder().
                add("code", code).add("message", message).build();
        return result;
    }
    
  
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
