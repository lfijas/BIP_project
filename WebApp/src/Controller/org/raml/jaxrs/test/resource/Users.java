
package Controller.org.raml.jaxrs.test.resource;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;


/**
 * Collection containing list of supermarket customers
 * 
 */
@Path("users")
public interface Users {


    /**
     * Creates a new user
     * 
     * @param phone
     *     Customer's phone number e.g. 604788987
     * @param sex
     *     Sex of a customer
     * @param username
     *     Username assigned to a customer of a sumermarket e.g. lukasz
     * @param address
     *     Address of a customer e.g. Mejia Lequerica 26
     * @param email
     *     Email of a customer e.g. john@smith.com
     * @param age
     *     Age of a customer e.g. 34
     * @param birthdate
     *     Customer's birth date e.g. 1991-12-23
     * @param password
     *     Secret customer's password e.g. admin1234
     * @param country
     *     Country where customer lives e.g. Spain
     * @param city
     *     City where customer lives e.g. Barcelona
     */
    @POST
    @Produces({
        "application/json"
    })
    Users.PostUsersResponse postUsers(
        @QueryParam("username")
        String username,
        @QueryParam("password")
        String password,
        @QueryParam("sex")
        Users.Sex sex,
        @QueryParam("age")
        BigDecimal age,
        @QueryParam("email")
        String email,
        @QueryParam("address")
        String address,
        @QueryParam("city")
        String city,
        @QueryParam("country")
        String country,
        @QueryParam("birthdate")
        Date birthdate,
        @QueryParam("phone")
        String phone)
        throws Exception
    ;

    /**
     * Retrives user information
     * 
     * @param userID
     *     
     */
    @GET
    @Path("{userID}")
    @Produces({
        "application/json"
    })
    Users.GetUsersByUserIDResponse getUsersByUserID(
        @PathParam("userID")
        String userID)
        throws Exception
    ;

    /**
     * Removes a user
     * 
     * @param userID
     *     
     */
    @DELETE
    @Path("{userID}")
    @Produces({
        "application/json"
    })
    Users.DeleteUsersByUserIDResponse deleteUsersByUserID(
        @PathParam("userID")
        String userID)
        throws Exception
    ;

    /**
     * Changes user password
     * 
     * @param userID
     *     
     * @param pass
     *     Secret customer's password e.g. admin1234
     */
    @PUT
    @Path("{userID}/password")
    @Produces({
        "application/json"
    })
    Users.PutUsersByUserIDPasswordResponse putUsersByUserIDPassword(
        @PathParam("userID")
        String userID,
        @QueryParam("pass")
        String pass)
        throws Exception
    ;

    /**
     * Creates a new user purchase
     * 
     * @param barcodes
     *     Array of barcodes of products associated with a purchase e.g. 20000233
     * @param userID
     *     
     */
    @POST
    @Path("{userID}/purchases")
    @Produces({
        "application/json"
    })
    Users.PostUsersByUserIDPurchasesResponse postUsersByUserIDPurchases(
        @PathParam("userID")
        String userID,
        @QueryParam("barcodes[]")
        BigDecimal barcodes)
        throws Exception
    ;

    /**
     * Retrieves all purchases done by the user
     * 
     * @param userID
     *     
     */
    @GET
    @Path("{userID}/purchases")
    @Produces({
        "application/json"
    })
    Users.GetUsersByUserIDPurchasesResponse getUsersByUserIDPurchases(
        @PathParam("userID")
        String userID)
        throws Exception
    ;

    /**
     * Retrieves all products in a purchase
     * 
     * @param userID
     *     
     * @param purchaseID
     *     
     */
    @GET
    @Path("{userID}/purchases/{purchaseID}/products")
    @Produces({
        "application/json"
    })
    Users.GetUsersByUserIDPurchasesByPurchaseIDProductsResponse getUsersByUserIDPurchasesByPurchaseIDProducts(
        @PathParam("purchaseID")
        String purchaseID,
        @PathParam("userID")
        String userID)
        throws Exception
    ;

    /**
     * Retrieves purchases done by a user on a specific data
     * 
     * @param userID
     *     
     * @param date
     *     
     */
    @GET
    @Path("{userID}/purchases/date/{date}")
    @Produces({
        "application/json"
    })
    Users.GetUsersByUserIDPurchasesDateByDateResponse getUsersByUserIDPurchasesDateByDate(
        @PathParam("date")
        String date,
        @PathParam("userID")
        String userID)
        throws Exception
    ;

    public class DeleteUsersByUserIDResponse
        extends Controller.org.raml.jaxrs.test.support.ResponseWrapper
    {


        private DeleteUsersByUserIDResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         * "success": true,
         * "status": 200
         * }
         * 
         * 
         * @param entity
         *     {
         *     "success": true,
         *     "status": 200
         *     }
         *     
         */
        public static Users.DeleteUsersByUserIDResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.DeleteUsersByUserIDResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         * "success": false,
         * "status": 400
         * }
         * 
         * 
         * @param entity
         *     {
         *     "success": false,
         *     "status": 400
         *     }
         *     
         */
        public static Users.DeleteUsersByUserIDResponse jsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.DeleteUsersByUserIDResponse(responseBuilder.build());
        }

		@Override
		public boolean bufferEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Set<String> getAllowedMethods() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, NewCookie> getCookies() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getDate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EntityTag getEntityTag() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getHeaderString(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Locale getLanguage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getLastModified() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLength() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Link getLink(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Builder getLinkBuilder(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Link> getLinks() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public URI getLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MediaType getMediaType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusType getStatusInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MultivaluedMap<String, String> getStringHeaders() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasLink(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> T readEntity(Class<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(Class<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

    }

    public class GetUsersByUserIDPurchasesByPurchaseIDProductsResponse
        extends Controller.org.raml.jaxrs.test.support.ResponseWrapper
    {


        private GetUsersByUserIDPurchasesByPurchaseIDProductsResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         *   "data": [
         *     {
         *     "barcode": 20000233,
         *     "food_group_id": 6,
         *     "product_name": "Grana Padano",
         *     "price": 3.16,
         *     "quantity_number": 100,
         *     "unit": "gr",
         *     "serving_size": null,
         *     "calories": 567 , 
         *     "proteins": 4.5,
         *     "carbohydrates": 23,
         *     "sugar": 2.3,
         *     "fat": null,
         *     "saturated_fat": null,
         *     "fiber": 0.9,
         *     "cholesterol": 2.4,
         *     "sodium": 0.04,
         *     "calcium": 0.56,
         *     "iron": null,
         *     "vitamin_a": null,
         *     "vitamin_c": null
         *     },
         *     {
         *     "barcode": 20000456,
         *     "food_group_id": 3,
         *     "product_name": "Soja Nature",
         *     "price": 34.12,
         *     "quantity_number": 500,
         *     "unit": "gr",
         *     "serving_size": null,
         *     "calories": 867 , 
         *     "proteins": 56,
         *     "carbohydrates": 123,
         *     "sugar": 4.6,
         *     "fat": 12.34,
         *     "saturated_fat": 9.8,
         *     "fiber": 0.5,
         *     "cholesterol": 0,
         *     "sodium": 0.23,
         *     "calcium": 5.78,
         *     "iron": null,
         *     "vitamin_a": 0,
         *     "vitamin_c": 0
         *     }
         *   ],
         *   "success": true,
         *   "status": 200
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data": [
         *         {
         *         "barcode": 20000233,
         *         "food_group_id": 6,
         *         "product_name": "Grana Padano",
         *         "price": 3.16,
         *         "quantity_number": 100,
         *         "unit": "gr",
         *         "serving_size": null,
         *         "calories": 567 , 
         *         "proteins": 4.5,
         *         "carbohydrates": 23,
         *         "sugar": 2.3,
         *         "fat": null,
         *         "saturated_fat": null,
         *         "fiber": 0.9,
         *         "cholesterol": 2.4,
         *         "sodium": 0.04,
         *         "calcium": 0.56,
         *         "iron": null,
         *         "vitamin_a": null,
         *         "vitamin_c": null
         *         },
         *         {
         *         "barcode": 20000456,
         *         "food_group_id": 3,
         *         "product_name": "Soja Nature",
         *         "price": 34.12,
         *         "quantity_number": 500,
         *         "unit": "gr",
         *         "serving_size": null,
         *         "calories": 867 , 
         *         "proteins": 56,
         *         "carbohydrates": 123,
         *         "sugar": 4.6,
         *         "fat": 12.34,
         *         "saturated_fat": 9.8,
         *         "fiber": 0.5,
         *         "cholesterol": 0,
         *         "sodium": 0.23,
         *         "calcium": 5.78,
         *         "iron": null,
         *         "vitamin_a": 0,
         *         "vitamin_c": 0
         *         }
         *       ],
         *       "success": true,
         *       "status": 200
         *     }
         *     
         */
        public static Users.GetUsersByUserIDPurchasesByPurchaseIDProductsResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.GetUsersByUserIDPurchasesByPurchaseIDProductsResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         * "success": false,
         * "status": 400
         * }
         * 
         * 
         * @param entity
         *     {
         *     "success": false,
         *     "status": 400
         *     }
         *     
         */
        public static Users.GetUsersByUserIDPurchasesByPurchaseIDProductsResponse jsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.GetUsersByUserIDPurchasesByPurchaseIDProductsResponse(responseBuilder.build());
        }

		@Override
		public boolean bufferEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Set<String> getAllowedMethods() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, NewCookie> getCookies() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getDate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EntityTag getEntityTag() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getHeaderString(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Locale getLanguage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getLastModified() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLength() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Link getLink(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Builder getLinkBuilder(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Link> getLinks() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public URI getLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MediaType getMediaType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusType getStatusInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MultivaluedMap<String, String> getStringHeaders() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasLink(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> T readEntity(Class<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(Class<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

    }

    public class GetUsersByUserIDPurchasesDateByDateResponse
        extends Controller.org.raml.jaxrs.test.support.ResponseWrapper
    {


        private GetUsersByUserIDPurchasesDateByDateResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         *   "data": 
         * {
         *   "_links": 
         *   {
         *     "self": 
         *     {
         *       "href":"/users/15/purchases/dates/2014-11-17"
         *     },
         *     "curies": [
         *       {
         *         "name": "nu",
         *         "href": "http://54.149.71.241/api/users/{rel}",
         *         "templated": true
         *       }                    
         *     ],
         *     "nu:products": [
         *       {
         *         "href": "/users/15/purchases/1/products"
         *       }
         *     ]
         *   },
         *   "_embedded": {
         *     "nu:purchase":[
         *       {
         *       "_links": {
         *         "self": {
         *           "href": "/users/15/purchases/1"
         *         }
         *       },
         *       "date": "2014-11-17 18:47:00.000",
         *       "branch": "Les Corts Branch",
         *       "branch_district": "Les Corts",
         *       "branch_city": "Barcelona",
         *       "branch_country": "Spain"
         *       }
         *     ]
         *   }
         * },
         *   "success": true,
         *   "status": 200
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data": 
         *     {
         *       "_links": 
         *       {
         *         "self": 
         *         {
         *           "href":"/users/15/purchases/dates/2014-11-17"
         *         },
         *         "curies": [
         *           {
         *             "name": "nu",
         *             "href": "http://54.149.71.241/api/users/{rel}",
         *             "templated": true
         *           }                    
         *         ],
         *         "nu:products": [
         *           {
         *             "href": "/users/15/purchases/1/products"
         *           }
         *         ]
         *       },
         *       "_embedded": {
         *         "nu:purchase":[
         *           {
         *           "_links": {
         *             "self": {
         *               "href": "/users/15/purchases/1"
         *             }
         *           },
         *           "date": "2014-11-17 18:47:00.000",
         *           "branch": "Les Corts Branch",
         *           "branch_district": "Les Corts",
         *           "branch_city": "Barcelona",
         *           "branch_country": "Spain"
         *           }
         *         ]
         *       }
         *     },
         *       "success": true,
         *       "status": 200
         *     }
         *     
         */
        public static Users.GetUsersByUserIDPurchasesDateByDateResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.GetUsersByUserIDPurchasesDateByDateResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         * "success": false,
         * "status": 400
         * }            
         * 
         * 
         * @param entity
         *     {
         *     "success": false,
         *     "status": 400
         *     }            
         *     
         */
        public static Users.GetUsersByUserIDPurchasesDateByDateResponse jsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.GetUsersByUserIDPurchasesDateByDateResponse(responseBuilder.build());
        }

		@Override
		public boolean bufferEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Set<String> getAllowedMethods() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, NewCookie> getCookies() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getDate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EntityTag getEntityTag() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getHeaderString(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Locale getLanguage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getLastModified() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLength() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Link getLink(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Builder getLinkBuilder(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Link> getLinks() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public URI getLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MediaType getMediaType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusType getStatusInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MultivaluedMap<String, String> getStringHeaders() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasLink(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> T readEntity(Class<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(Class<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

    }

    public class GetUsersByUserIDPurchasesResponse
        extends Controller.org.raml.jaxrs.test.support.ResponseWrapper
    {


        private GetUsersByUserIDPurchasesResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         *   "data": 
         *   {
         *     "_links": 
         *     {
         *       "self": 
         *       {
         *         "href":"/users/15/purchases"
         *       },
         *       "curies": [
         *         {
         *           "name": "nu",
         *           "href": "http://54.149.71.241/api/users/{rel}",
         *           "templated": true
         *         }                    
         *       ],
         *       "nu:products": [
         *         {
         *           "href": "/users/15/purchases/1/products"
         *         },
         *         {
         *           "href": "/users/15/purchases/2/products"
         *         }
         *       ],
         *       "nu:date": [
         *         {
         *           "href": "/users/15/purchases/date/2014-11-17"
         *         }
         *       ]
         *     },
         *     "_embedded": {
         *       "nu:purchase":[
         *         {
         *         "_links": {
         *           "self": {
         *             "href": "/users/15/purchases/1"
         *           }
         *         },
         *         "date": "2014-11-17 18:47:00.000",
         *         "branch": "Les Corts Branch",
         *         "branch_district": "Les Corts",
         *         "branch_city": "Barcelona",
         *         "branch_country": "Spain"
         *         },
         *         {
         *         "_links": {
         *           "self": {
         *             "href": "/users/15/purchases/2"
         *           }
         *         },
         *         "date": "2014-11-18 19:17:00.000",
         *         "branch": "Central Branch",
         *         "branch_district": "Placa Catalunya",
         *         "branch_city": "Barcelona",
         *         "branch_country": "Spain"
         *         }
         *       ]
         *     }
         *   },
         *   "success": true,
         *   "status": 200
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data": 
         *       {
         *         "_links": 
         *         {
         *           "self": 
         *           {
         *             "href":"/users/15/purchases"
         *           },
         *           "curies": [
         *             {
         *               "name": "nu",
         *               "href": "http://54.149.71.241/api/users/{rel}",
         *               "templated": true
         *             }                    
         *           ],
         *           "nu:products": [
         *             {
         *               "href": "/users/15/purchases/1/products"
         *             },
         *             {
         *               "href": "/users/15/purchases/2/products"
         *             }
         *           ],
         *           "nu:date": [
         *             {
         *               "href": "/users/15/purchases/date/2014-11-17"
         *             }
         *           ]
         *         },
         *         "_embedded": {
         *           "nu:purchase":[
         *             {
         *             "_links": {
         *               "self": {
         *                 "href": "/users/15/purchases/1"
         *               }
         *             },
         *             "date": "2014-11-17 18:47:00.000",
         *             "branch": "Les Corts Branch",
         *             "branch_district": "Les Corts",
         *             "branch_city": "Barcelona",
         *             "branch_country": "Spain"
         *             },
         *             {
         *             "_links": {
         *               "self": {
         *                 "href": "/users/15/purchases/2"
         *               }
         *             },
         *             "date": "2014-11-18 19:17:00.000",
         *             "branch": "Central Branch",
         *             "branch_district": "Placa Catalunya",
         *             "branch_city": "Barcelona",
         *             "branch_country": "Spain"
         *             }
         *           ]
         *         }
         *       },
         *       "success": true,
         *       "status": 200
         *     }
         *     
         */
        public static Users.GetUsersByUserIDPurchasesResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.GetUsersByUserIDPurchasesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         * "success": false,
         * "status": 400
         * }  
         * 
         * 
         * @param entity
         *     {
         *     "success": false,
         *     "status": 400
         *     }  
         *     
         */
        public static Users.GetUsersByUserIDPurchasesResponse jsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.GetUsersByUserIDPurchasesResponse(responseBuilder.build());
        }

		@Override
		public boolean bufferEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Set<String> getAllowedMethods() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, NewCookie> getCookies() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getDate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EntityTag getEntityTag() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getHeaderString(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Locale getLanguage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getLastModified() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLength() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Link getLink(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Builder getLinkBuilder(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Link> getLinks() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public URI getLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MediaType getMediaType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusType getStatusInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MultivaluedMap<String, String> getStringHeaders() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasLink(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> T readEntity(Class<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(Class<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

    }

    public class GetUsersByUserIDResponse
        extends Controller.org.raml.jaxrs.test.support.ResponseWrapper
    {


        private GetUsersByUserIDResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         *   "data":
         *     {
         *     "_links": {
         *       "self": {
         *         "href":"/users/15"
         *       },
         *       "curies": [
         *         {
         *           "name": "nu",
         *           "href": "http://54.149.71.241/api/users/{rel}",
         *           "templated": true
         *         }                    
         *       ],
         *       "nu:password": [
         *         {
         *           "href": "/users/15/password"
         *         }
         *       ],
         *       "nu:purchases": [
         *         {
         *           "href": "/users/15/purchases"
         *         }
         *       ],
         *     },
         *     "username": "lukasz",
         *     "email": "john@smith.com",
         *     "phone":"608567890",
         *     "address": "Mejia Lequerica 26",
         *     "city": "Barcelona",
         *     "country": "Spain",
         *     "birthdate": "1990-12-23",
         *     "sex": "M"
         *     },
         *   "success": true,
         *   "status": 200
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data":
         *         {
         *         "_links": {
         *           "self": {
         *             "href":"/users/15"
         *           },
         *           "curies": [
         *             {
         *               "name": "nu",
         *               "href": "http://54.149.71.241/api/users/{rel}",
         *               "templated": true
         *             }                    
         *           ],
         *           "nu:password": [
         *             {
         *               "href": "/users/15/password"
         *             }
         *           ],
         *           "nu:purchases": [
         *             {
         *               "href": "/users/15/purchases"
         *             }
         *           ],
         *         },
         *         "username": "lukasz",
         *         "email": "john@smith.com",
         *         "phone":"608567890",
         *         "address": "Mejia Lequerica 26",
         *         "city": "Barcelona",
         *         "country": "Spain",
         *         "birthdate": "1990-12-23",
         *         "sex": "M"
         *         },
         *       "success": true,
         *       "status": 200
         *     }
         *     
         */
        public static Users.GetUsersByUserIDResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.GetUsersByUserIDResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         * "success": false,
         * "status": 400
         * }
         * 
         * 
         * @param entity
         *     {
         *     "success": false,
         *     "status": 400
         *     }
         *     
         */
        public static Users.GetUsersByUserIDResponse jsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.GetUsersByUserIDResponse(responseBuilder.build());
        }

		@Override
		public boolean bufferEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Set<String> getAllowedMethods() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, NewCookie> getCookies() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getDate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EntityTag getEntityTag() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getHeaderString(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Locale getLanguage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getLastModified() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLength() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Link getLink(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Builder getLinkBuilder(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Link> getLinks() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public URI getLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MediaType getMediaType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusType getStatusInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MultivaluedMap<String, String> getStringHeaders() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasLink(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> T readEntity(Class<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(Class<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

    }

    public class PostUsersByUserIDPurchasesResponse
        extends Controller.org.raml.jaxrs.test.support.ResponseWrapper
    {


        private PostUsersByUserIDPurchasesResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         * "success": true,
         * "status": 200
         * }
         * 
         * 
         * @param entity
         *     {
         *     "success": true,
         *     "status": 200
         *     }
         *     
         */
        public static Users.PostUsersByUserIDPurchasesResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.PostUsersByUserIDPurchasesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         * "success": false,
         * "status": 400
         * }
         * 
         * 
         * @param entity
         *     {
         *     "success": false,
         *     "status": 400
         *     }
         *     
         */
        public static Users.PostUsersByUserIDPurchasesResponse jsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.PostUsersByUserIDPurchasesResponse(responseBuilder.build());
        }

		@Override
		public boolean bufferEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Set<String> getAllowedMethods() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, NewCookie> getCookies() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getDate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EntityTag getEntityTag() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getHeaderString(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Locale getLanguage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getLastModified() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLength() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Link getLink(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Builder getLinkBuilder(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Link> getLinks() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public URI getLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MediaType getMediaType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusType getStatusInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MultivaluedMap<String, String> getStringHeaders() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasLink(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> T readEntity(Class<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(Class<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

    }

    public class PostUsersResponse
        extends Controller.org.raml.jaxrs.test.support.ResponseWrapper
    {


        private PostUsersResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         * "success": true,
         * "status": 200
         * }
         * 
         * 
         * @param entity
         *     {
         *     "success": true,
         *     "status": 200
         *     }
         *     
         */
        public static Users.PostUsersResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.PostUsersResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         * "success": false,
         * "status": 400
         * }
         * 
         * 
         * @param entity
         *     {
         *     "success": false,
         *     "status": 400
         *     }
         *     
         */
        public static Users.PostUsersResponse jsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.PostUsersResponse(responseBuilder.build());
        }

		@Override
		public boolean bufferEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Set<String> getAllowedMethods() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, NewCookie> getCookies() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getDate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EntityTag getEntityTag() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getHeaderString(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Locale getLanguage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getLastModified() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLength() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Link getLink(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Builder getLinkBuilder(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Link> getLinks() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public URI getLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MediaType getMediaType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusType getStatusInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MultivaluedMap<String, String> getStringHeaders() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasLink(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> T readEntity(Class<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(Class<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

    }

    public class PutUsersByUserIDPasswordResponse
        extends Controller.org.raml.jaxrs.test.support.ResponseWrapper
    {


        private PutUsersByUserIDPasswordResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         * "success": true,
         * "status": 200
         * }
         * 
         * 
         * @param entity
         *     {
         *     "success": true,
         *     "status": 200
         *     }
         *     
         */
        public static Users.PutUsersByUserIDPasswordResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.PutUsersByUserIDPasswordResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         * "success": false,
         * "status": 400
         * }
         * 
         * 
         * @param entity
         *     {
         *     "success": false,
         *     "status": 400
         *     }
         *     
         */
        public static Users.PutUsersByUserIDPasswordResponse jsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Users.PutUsersByUserIDPasswordResponse(responseBuilder.build());
        }

		@Override
		public boolean bufferEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Set<String> getAllowedMethods() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, NewCookie> getCookies() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getDate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EntityTag getEntityTag() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getHeaderString(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Locale getLanguage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getLastModified() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLength() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Link getLink(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Builder getLinkBuilder(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Link> getLinks() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public URI getLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MediaType getMediaType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusType getStatusInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MultivaluedMap<String, String> getStringHeaders() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasLink(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> T readEntity(Class<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(Class<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

    }

    public enum Sex {

        M,
        F;

    }

}
