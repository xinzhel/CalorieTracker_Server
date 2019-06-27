/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws.service;

import java.text.ParseException;
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
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restws.Food;

/**
 *
 * @author home
 */
@Stateless
@Path("restws.food")
public class FoodFacadeREST extends AbstractFacade<Food> {

    @PersistenceContext(unitName = "CalorieTrackerPU")
    private EntityManager em;

    public FoodFacadeREST() {
        super(Food.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Food entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Food entity) {
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
    public Food find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    
    @GET
    @Path("findByFoodName/{foodName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findByName(@PathParam("foodName") String foodName) {
        TypedQuery<Food> q = em.createNamedQuery("Food.findByFoodName", Food.class);
        q.setParameter("foodName", foodName);
        return q.getResultList();
    }
    
    @GET
    @Path("findByCategory/{category}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findByCategory(@PathParam("category") String category) {
        TypedQuery<Food> q = em.createNamedQuery("Food.findByCategory", Food.class);
        q.setParameter("category", category);
        return q.getResultList();
    }
    
    @GET
    @Path("findByCalorie/{calorie}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findByCalorie(@PathParam("calorie") int calorie) {
        TypedQuery<Food> q = em.createNamedQuery("Food.findByCalorie", Food.class);
        q.setParameter("calorie", calorie);
        return q.getResultList();
    }

    @GET
    @Path("findByServingUnit/{servingUnit}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findByServingUnit(@PathParam("servingUnit") String servingUnit) {
        TypedQuery<Food> q = em.createNamedQuery("Food.findByServingUnit", Food.class);
        q.setParameter("servingUnit", servingUnit);
        return q.getResultList();
    }
    
    @GET
    @Path("findByServingAmount/{servingAmount}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findByServingAmount(@PathParam("servingAmount") double servingAmount) {
        TypedQuery<Food> q = em.createNamedQuery("Food.findByServingAmount", Food.class);
        q.setParameter("servingAmount", servingAmount);
        return q.getResultList();
    }
    
    @GET
    @Path("findByFat/{fat}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findByFat(@PathParam("fat") double fat) {
        TypedQuery<Food> q = em.createNamedQuery("Food.findByFat", Food.class);
        q.setParameter("fat", fat);
        return q.getResultList();
    }
       
    @GET
    @Path("findByCalorieANDFat/{calorie}/{fat}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findByCalorieANDFat(@PathParam("calorie") int calorie, @PathParam("fat") double fat) {
        TypedQuery<Food> q = em.createQuery("SELECT f FROM Food f WHERE f.calorie <= :calorie AND f.fat <= :fat", Food.class);
        q.setParameter("fat", fat);
        q.setParameter("calorie", calorie);
        return q.getResultList();
    }

    @POST
    @Path("saveFood")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON})
    public Object saveFood(@FormParam("foodName") String foodName, @FormParam("category") String category, @FormParam("calorie") String calorie, 
            @FormParam("serving_unit") String serving_unit, @FormParam("serving_amount") String serving_amount, @FormParam("fat") String fat){
        int code = 200;
        String message = "Success";
        Logger logger = Logger.getLogger(getClass().getName());
        logger.info("FOODNAME--------------------------------------!!!!!" +foodName );
  
       TypedQuery<Food> q = em.createNamedQuery("Food.findByFoodName", Food.class);
        q.setParameter("foodName", foodName);
        if (!q.getResultList().isEmpty()) {
            code = 401;
            message = "Food already exists";
            return Json.createObjectBuilder().
                add("code", code).add("message", message).build();
        }
        
        Food food = new Food();
        food.setFoodName(foodName);
        food.setFat(Double.parseDouble(fat));
        calorie = calorie.indexOf(".") < 0 ? calorie : calorie.replaceAll("0*$", "").replaceAll("\\.$", "");
        food.setCalorie(Integer.parseInt(calorie));
        food.setCategory(category);
        food.setServingUnit(serving_unit);
        food.setServingAmount(Double.parseDouble(serving_amount));
        getEntityManager().persist(food);
        JsonObject result = Json.createObjectBuilder().
                add("code", code).add("message", message).build();
        return result;
    }
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
