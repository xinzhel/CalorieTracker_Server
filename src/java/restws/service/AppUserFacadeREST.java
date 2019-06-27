/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
@Path("restws.appuser")
public class AppUserFacadeREST extends AbstractFacade<AppUser> {

    @PersistenceContext(unitName = "CalorieTrackerPU")
    private EntityManager em;

    public AppUserFacadeREST() {
        super(AppUser.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(AppUser entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, AppUser entity) {
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
    public AppUser find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("findByName/{name}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AppUser> findByName(@PathParam("name") String name) {
        TypedQuery<AppUser> q = em.createNamedQuery("AppUser.findByName", AppUser.class);
        q.setParameter("name", name);
        return q.getResultList();
    }

    @GET
    @Path("findBySurname/{surname}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AppUser> findBySurname(@PathParam("surname") String surname) {
        TypedQuery<AppUser> q = em.createNamedQuery("AppUser.findBySurname", AppUser.class);
        q.setParameter("surname", surname);
        return q.getResultList();
    }

    @GET
    @Path("findByEmail/{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AppUser> findByEmail(@PathParam("email") String email) {
        TypedQuery<AppUser> q = em.createNamedQuery("AppUser.findByEmail", AppUser.class);
        q.setParameter("email", email);
        return q.getResultList();
    }

    @GET
    @Path("findByDob/{dob}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AppUser> findByDob(@PathParam("dob") String dob) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dob);

        TypedQuery<AppUser> q = em.createNamedQuery("AppUser.findByDob", AppUser.class);
        q.setParameter("dob", d);
        return q.getResultList();
    }

    @GET
    @Path("findByHeight/{height}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AppUser> findByHeight(@PathParam("height") double height) {
        TypedQuery<AppUser> q = em.createNamedQuery("AppUser.findByHeight", AppUser.class);
        q.setParameter("height", height);
        return q.getResultList();
    }

    @GET
    @Path("findByWeight/{weight}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AppUser> findByWeight(@PathParam("weight") double weight) {
        System.out.println("here");
        TypedQuery<AppUser> q = em.createNamedQuery("AppUser.findByWeight", AppUser.class);
        q.setParameter("weight", weight);
        return q.getResultList();
    }

    @GET
    @Path("findByGender/{gender}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AppUser> findByGender(@PathParam("gender") String gender) {
        TypedQuery<AppUser> q = em.createNamedQuery("AppUser.findByGender", AppUser.class);
        q.setParameter("gender", gender.charAt(0));
        return q.getResultList();
    }

    @GET
    @Path("findByAddress/{address}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AppUser> findByAddress(@PathParam("address") String address) {
        TypedQuery<AppUser> q = em.createNamedQuery("AppUser.findByAddress", AppUser.class);
        q.setParameter("address", address);
        return q.getResultList();
    }

    @GET
    @Path("findByPostcode/{postcode}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AppUser> findByPostcode(@PathParam("postcode") String postcode) {
        TypedQuery<AppUser> q = em.createNamedQuery("AppUser.findByPostcode", AppUser.class);
        q.setParameter("postcode", postcode);
        return q.getResultList();
    }

    @GET
    @Path("findByLevelOfActivity/{levelOfActivity}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AppUser> findByLevelOfActivity(@PathParam("levelOfActivity") int levelOfActivity) {
        TypedQuery<AppUser> q = em.createNamedQuery("AppUser.findByLevelOfActivity", AppUser.class);
        q.setParameter("levelOfActivity", levelOfActivity);
        return q.getResultList();
    }

    @GET
    @Path("findByStepsPerMile/{stepsPerMile}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AppUser> findByStepsPerMile(@PathParam("stepsPerMile") int stepsPerMile) {
        TypedQuery<AppUser> q = em.createNamedQuery("AppUser.findByStepsPerMile", AppUser.class);
        q.setParameter("stepsPerMile", stepsPerMile);
        return q.getResultList();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AppUser> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AppUser> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("caloriesBurnedPerSteps/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String caloriesBurnedPerSteps(@PathParam("id") Integer id) {
        AppUser user = super.find(id);
        double weight = user.getWeight();
        int steps_per_mile = user.getStepsPerMile();
        double calories_burned_per_steps = (weight * 2.205 * 0.49) / steps_per_mile;

        return Double.toString(calories_burned_per_steps);
    }

    @GET
    @Path("bmr/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String bmr(@PathParam("id") Integer id) {
        AppUser user = super.find(id);
        double weight = user.getWeight();
        double height = user.getHeight();

        LocalDate curDate = LocalDate.now();
        LocalDate dob = user.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int age = Period.between(dob, curDate).getYears();

        Character gender = user.getGender();

        double bmr = 0;
        if (gender == 'M') {
            bmr = 13.75 * weight + 5.003 * height - 6.755 * age + 66.5;
        } else {
            bmr = 9.563 * weight + 1.85 * height - 4.676 * age + 655.1;
        }
        return Double.toString(bmr);
    }

    @GET
    @Path("caloriesBurnedAtRest/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String caloriesBurnedAtRest(@PathParam("id") Integer id) {

        double bmr = Double.valueOf(bmr(id));

        double total_calories_burned = 0;

        AppUser user = super.find(id);
        int levelOfActivity = user.getLevelOfActivity();

        if (levelOfActivity == 1) {
            total_calories_burned = bmr * 1.2;
        } else if (levelOfActivity == 2) {
            total_calories_burned = bmr * 1.375;
        } else if (levelOfActivity == 3) {
            total_calories_burned = bmr * 1.55;
        } else if (levelOfActivity == 4) {
            total_calories_burned = bmr * 1.725;
        } else {
            total_calories_burned = bmr * 1.9;
        }

        return Double.toString(total_calories_burned);
    }

    @GET
    @Path("dailyCaloriesConsumed/{userId}/{date}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String dailyCaloriesConsumed(@PathParam("userId") Integer userId, @PathParam("date") String date) throws ParseException {

        double bmr = Double.valueOf(bmr(userId));

        int total_calories_consumed = 0;
        AppUser user = super.find(userId);
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//        List<Consumption> consumptions = new ConsumptionFacadeREST().findByUsedIdANDDate(userId, date);
        TypedQuery<Consumption> queryConsumption = em.createNamedQuery("Comsumption.findByUsedIdANDDate", Consumption.class);
        queryConsumption.setParameter("userId", user);
        queryConsumption.setParameter("date", d);
        List<Consumption> consumptions = queryConsumption.getResultList();

//        TypedQuery<Food> queryFood = em.createQuery("SELECT f FROM Food f WHERE f.foodId = :foodId", Food.class);
        for (Consumption consumption : consumptions) {
            int calorie = consumption.getFoodId().getCalorie();
            int servings = consumption.getServings();
            total_calories_consumed += calorie * servings;
        }
        return Integer.toString(total_calories_consumed);
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON})
    public Object register(@FormParam("name") String name,
            @FormParam("surname") String surname,
            @FormParam("email") String email,
            @FormParam("dob") String dob,
            @FormParam("height") int height,
            @FormParam("weight") int weight,
            @FormParam("gender") String gender,
            @FormParam("address") String address,
            @FormParam("postcode") String postcode,
            @FormParam("levelOfActivivty") int levelOfActivity,
            @FormParam("stepsPerMile") int stepsPerMile,
            @FormParam("username") String username,
            @FormParam("hash") String hash) throws ParseException {
        int code = 200;
        String message = "Success";
        Logger logger = Logger.getLogger(getClass().getName());
        
        // check username
        TypedQuery<Credential> qc = em.createNamedQuery("Credential.findByUsername", Credential.class);
        qc.setParameter("username", username);
        if (!qc.getResultList().isEmpty()) {
            logger.info("name: " + qc.getResultList().get(0).getUsername());
            code = 400;
            message = "The username has been used.";
            return Json.createObjectBuilder().add("code", code)
                    .add("message", message).build();
        }

        // check email
        TypedQuery<AppUser> qu = em.createNamedQuery("AppUser.findByEmail", AppUser.class);
        qu.setParameter("email", email);
        if (!qu.getResultList().isEmpty()) {
            code = 400;
            message = "The email has been registered.";
            return Json.createObjectBuilder().add("code", code)
                    .add("message", message).build();
        }

        // add to AppUser table
        AppUser u = new AppUser();
        u.setName(name);
        u.setSurname(surname);
        u.setEmail(email);
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
        u.setDob(d);
        u.setHeight(height);
        u.setWeight(weight);
        u.setGender(gender.charAt(0));
        u.setPostcode(postcode);
        u.setAddress(address);
        u.setLevelOfActivity(levelOfActivity);
        u.setStepsPerMile(levelOfActivity);
        getEntityManager().persist(u);

        // add to Credential table
        Date date = new Date();
        Credential c = new Credential();
        c.setUsername(username);
        c.setUserId(u);
        c.setSignUpDate(date);
        c.setHash(hash);
        logger.info("name: " + u.getName());
        logger.info("username: " + username + "date: " + date + "hash:" + hash);
        getEntityManager().persist(c);

        JsonObject result = Json.createObjectBuilder().
                add("code", code).add("message", message).build();
        return result;
    }
    
    @GET
    @Path("findByUsername/{username}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JsonObject findByUsername(@PathParam("username") String username) {
        TypedQuery<Credential> qc = em.createNamedQuery("Credential.findByUsername", Credential.class);
        qc.setParameter("username", username);
        AppUser user = qc.getResultList().get(0).getUserId();
        
        
        String address = user.getAddress();
        String firstname = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();
        int userId = user.getUserId();
        JsonObject result = Json.createObjectBuilder().
                add("firstname", firstname)
                .add("surname", surname)
                .add("address", address)
                .add("email", email)
                .add("userId",userId).build();
        return result;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
