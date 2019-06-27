/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws.service;


import java.text.DateFormat;
import javax.ws.rs.core.GenericEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
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
import javax.ws.rs.core.Response;
import restws.AppUser;
import restws.Report;

/**
 *
 * @author home
 */
@Stateless
@Path("restws.report")
public class ReportFacadeREST extends AbstractFacade<Report> {

    @PersistenceContext(unitName = "CalorieTrackerPU")
    private EntityManager em;

    public ReportFacadeREST() {
        super(Report.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Report entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Report entity) {
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
    public Report find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    
    @GET
    @Path("findByDate/{date}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findByDate(@PathParam("date") String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date); 
        TypedQuery<Report> q = em.createNamedQuery("Report.findByDate", Report.class);
        q.setParameter("date", d);
        return q.getResultList();
    }
    
    @GET
    @Path("findByCaloriesConsumed/{caloriesConsumed}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findByCaloriesConsumed(@PathParam("caloriesConsumed") int caloriesConsumed) {
        TypedQuery<Report> q = em.createNamedQuery("Report.findByCaloriesConsumed", Report.class);
        q.setParameter("caloriesConsumed", caloriesConsumed);
        return q.getResultList();
    }
      
    @GET
    @Path("findByCaloriesBurned/{caloriesBurned}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findByCaloriesBurned(@PathParam("caloriesBurned") int caloriesBurned) {
        TypedQuery<Report> q = em.createNamedQuery("Report.findByCaloriesBurned", Report.class);
        q.setParameter("caloriesBurned", caloriesBurned);
        return q.getResultList();
    }
    
    @GET
    @Path("findBySteps/{steps}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findByHash(@PathParam("steps") int steps) {
        TypedQuery<Report> q = em.createNamedQuery("Report.findBySteps", Report.class);
        q.setParameter("steps", steps);
        return q.getResultList();
    }
    
    @GET
    @Path("findByCalorieGoal/{calorieGoal}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findByCalorieGoal(@PathParam("calorieGoal") int calorieGoal) {
        TypedQuery<Report> q = em.createNamedQuery("Report.findByCalorieGoal", Report.class);
        q.setParameter("calorieGoal", calorieGoal);
        return q.getResultList();
    }
    
    @GET
    @Path("findByUserId/{userId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findByUserId(@PathParam("userId") int userId) {
        TypedQuery<Report> q = em.createNamedQuery("Report.findByUserId", Report.class);
        q.setParameter("userId", userId);
        return q.getResultList();
    }

    
    @GET
    @Path("findByEmailANDDate/{email}/{date}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findByEmailANDDate(@PathParam("email") String email, @PathParam("date") String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        TypedQuery<Report> q = em.createQuery("SELECT r FROM Report r WHERE r.userId.email = :email AND r.date = :date", Report.class);
        q.setParameter("email", email);
        q.setParameter("date", d);
        
        return q.getResultList();
    }
    
    @GET
    @Path("dailyReport/{userId}/{date}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Object dailyReport(@PathParam("userId") Integer userId, @PathParam("date") String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        TypedQuery<Object []> q = em.createQuery("SELECT r.caloriesConsumed, r.caloriesBurned, r.calorieGoal FROM Report r WHERE r.userId.userId = :userId AND r.date = :date", Object[].class);
        q.setParameter("userId", userId);
        q.setParameter("date", d);
        List<Object[]> list = q.getResultList();
        int caloriesConsumed =  Integer.parseInt(list.get(0)[0].toString());
        int caloriesBurned = Integer.parseInt(list.get(0)[1].toString());
        int calorieGoal = Integer.parseInt(list.get(0)[2].toString());
        int remaining_calorie =  caloriesConsumed -  caloriesBurned - calorieGoal;
        
        JsonObject result = Json.createObjectBuilder().
                add("caloriesConsumed", caloriesConsumed )
                .add("caloriesBurned", caloriesBurned)
                .add("caloriesRemaining", remaining_calorie).build();
        
        return result;
    }
    
    @GET
    @Path("periodReport/{userId}/{start_date}/{end_date}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Object periodReport(@PathParam("userId") Integer userId, @PathParam("start_date") String start_date,  @PathParam("end_date") String end_date) throws ParseException {
        Date start_d = new SimpleDateFormat("yyyy-MM-dd").parse(start_date);
        Date end_d = new SimpleDateFormat("yyyy-MM-dd").parse(end_date);
        TypedQuery<Report> q = em.createQuery("SELECT r FROM Report r WHERE r.userId.userId = :userId AND r.date between :start_date and :end_date", Report.class);
        q.setParameter("userId", userId);
        q.setParameter("start_date", start_d);
        q.setParameter("end_date", end_d);
        
        List<Report> report_list = q.getResultList();
        
        int total_calories_consumed = 0;
        int total_calories_burned = 0;
        int total_steps = 0;
        for (Report report: report_list)
        {
            total_calories_consumed += report.getCaloriesConsumed();
            total_calories_burned += report.getCaloriesBurned();
            total_steps += report.getSteps();
        }
        
        JsonObject result = Json.createObjectBuilder().
                add("caloriesConsumed", total_calories_consumed )
                .add("caloriesBurned", total_calories_burned)
                .add("toal steps", total_steps).build();
        
        return result;
        
    }
    
    @GET
    @Path("periodReportPerDay/{userId}/{start_date}/{end_date}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Object periodReportPerDay(@PathParam("userId") Integer userId, @PathParam("start_date") String start_date,  @PathParam("end_date") String end_date) throws ParseException {
        Date start_d = new SimpleDateFormat("yyyy-MM-dd").parse(start_date);
        Date end_d = new SimpleDateFormat("yyyy-MM-dd").parse(end_date);
        TypedQuery<Report> q = em.createQuery("SELECT r FROM Report r WHERE r.userId.userId = :userId AND r.date between :start_date and :end_date", Report.class);
        q.setParameter("userId", userId);
        q.setParameter("start_date", start_d);
        q.setParameter("end_date", end_d);
        
        List<Report> report_list = q.getResultList();
        
        int calories_consumed = 0;
        int calories_burned = 0;
        int steps = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Report report: report_list)
        {
            calories_consumed = report.getCaloriesConsumed();
            calories_burned = report.getCaloriesBurned();
            steps = report.getSteps();
            String strDate = dateFormat.format(report.getDate());
            JsonObject jsonObject = Json.createObjectBuilder().
                add("caloriesConsumed", calories_consumed )
                .add("caloriesBurned", calories_burned)
                .add("steps", steps)
                .add("date", strDate).build();
            
            builder.add(jsonObject);
            
        }
        
        
        
        return builder.build();
        
    }
    
    @POST
    @Path("saveReport")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON})
    public Object saveReport(@FormParam("email") String email, @FormParam("date") String date, @FormParam("caloriesConsumed") int calories_consumed, 
            @FormParam("caloriesBurned") int calories_burned, @FormParam("steps") int steps, @FormParam("calorieGoal") int calorie_goal) throws ParseException{
        int code = 200;
        String message = "Success";
        Logger logger = Logger.getLogger(getClass().getName());
        logger.info("FOODNAME--------------------------------------!!!!!" +date );
  
       // get user
       TypedQuery<AppUser> qu = em.createNamedQuery("AppUser.findByEmail", AppUser.class);
       qu.setParameter("email", email);
       AppUser user = qu.getResultList().get(0);
        
        Report report = new Report();
        report.setUserId(user);
        report.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        report.setCaloriesConsumed(calories_consumed);
        report.setCaloriesBurned(calories_burned);
        report.setSteps(steps);
        report.setCalorieGoal(calorie_goal);
        
        getEntityManager().persist(report);
        JsonObject result = Json.createObjectBuilder().
                add("code", code).add("message", message).build();
        return result;
    }
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
